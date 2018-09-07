package edu.miami.bte324.hw4.gat51;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

//if you move different packages, import here by name of package and specific files

/**
 * @author Grant Thorburn
 *
 */

public final class ScheduleXMLReaderUtils extends SchedulerReadWriteUtils {
	/*
	//read/write utils 
	//Basics
	protected final static String SCHEDULE_DATA = "root"; 
	protected final static String PATIENT = "patient";
	protected final static String DOCTOR = "doctor";
	protected final static String VISIT = "visit";
	protected final static String ID = "id";
	//Name
	protected final static String NAME = "name";
	protected final static String FIRST_NAME = "firstName"; 
	protected final static String LAST_NAME = "lastName";
	//Data
	protected final static String DATA = "data";
	protected final static String SSN = "SSN"; //SSN, not ssn. 
	protected final static String DOB = "dob";
	protected final static String SPECIALTY = "specialty";
	//Visit Map
	protected final static String DOCTOR_ID = "doctorId";
	protected final static String PATIENT_ID = "patientId";
	protected final static String VISIT_DATE = "visitDate";
	
	*/
	private final static String DOB_FORMAT = "yyyy-MM-dd";
	private final static String VISIT_DATE_FORMAT = "yyyy-MM-dd HH:mm"; //allow update to hour and time. 
	

	public static String readCharacters(XMLEventReader eventReader, String elementName) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a " + elementName + " but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(elementName)) {
			throw new IllegalStateException("Attempting to read a " + elementName + " at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		String chars = eventReader.nextEvent().asCharacters().getData();
		return chars;
	}
	
	public static Date readDate(XMLEventReader eventReader, String elementName, String dateFormat) throws XMLStreamException {
		//XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		//System.out.println(elementName);
		/*
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a " + elementName + " but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(elementName)) {
			throw new IllegalStateException("Attempting to read a " + elementName + " at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		String dateStr = eventReader.nextEvent().asCharacters().getData();
		*/
		//System.out.println(dateStr);
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			//df.parse(dateStr);
			df.parse(elementName);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cDate = df.getCalendar();
		Date date =  cDate.getTime();
		//System.out.println(date);
		return date;
	}//end read Date

	public static Name readName(XMLEventReader eventReader) throws XMLStreamException {
		// enter this method at the startElement of name
		// read until the endElement
		// should be called right before reading the start element for name (use peek to make sure it is the right element)
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a name but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(NAME)) {
			throw new IllegalStateException("Attempting to read a name at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		Name name = null;
		boolean finished = false;
		String firstName = null, lastName = null;
		while (!finished) {
			XMLEvent event = eventReader.nextEvent();
			// check the start elements for the nested elements inside NAME
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart().equals(FIRST_NAME)) {
					event = eventReader.nextEvent();
					firstName = event.asCharacters().getData();
				}
				else if (startElement.getName().getLocalPart().equals(LAST_NAME)) {
					event = eventReader.nextEvent();
					lastName = event.asCharacters().getData();
				}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName());
				}
			}
			// check the end elements to find where the name element is closed, </name>
			else if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				// when the end element is the name element, create the name return object;
				if (endElement.getName().getLocalPart().equals(NAME)) {
					// Schema makes these required, so they must exist
					// would be a good practice to check for existence anyways
					name = new Name(firstName, lastName); 
					finished = true;
				}
			}
			else {
				// ignore other events, such as character events with line feeds and tabs
			}
		}
		return name;
	}

	public static TempPersonalData readTempPersonalData(XMLEventReader eventReader) throws XMLStreamException{
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a data element but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(DATA)) {
			throw new IllegalStateException("Attempting to read a data element at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		TempPersonalData pData = null;
		String ssn = null;
		//String stringDOB = null; //get a string
		Date dob = null; //convert to a Date. 
		//date is in year - month - day, following format of stringToDate
		//SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		boolean finished = false;
		while (!finished) {
			XMLEvent event = eventReader.nextEvent();
			// check the start elements for the nested elements inside NAME
			
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart().equals(DOB)) {
					event = eventReader.nextEvent();
					//now with a string DOB, convert to a Date, dob. Specific method.
					//dob = readDate(eventReader, DOB, DOB_FORMAT);
					String dobString = event.asCharacters().getData(); //this transfers correctly
					//correct dobString transferred. 
					dob = readDate(eventReader, dobString, DOB_FORMAT);
				}
				else if (startElement.getName().getLocalPart().equals(SSN)) {
					event = eventReader.nextEvent();
					ssn = event.asCharacters().getData();}
				else {System.err.println("Unrecognized element, ignoring: " + startElement.getName());}
			}
			// check the end elements to find where the name element is closed
			else if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				// when the end element is the end, </data>, skip otherwise. 
				//endElement.getName().getLocalPart().equals(PATIENT)|| endElement.getName().getLocalPart().equals(DOCTOR) || endElement.getName().getLocalPart().equals(VISIT)
				//data is the end object, of course. 
				if (endElement.getName().getLocalPart().equals(DATA)) {
					pData = new TempPersonalData(ssn, dob); 
					finished = true;
			}
			else {
				// ignore other events, such as character events with line feeds and tabs
			}
		}
		}
		return pData;
	}//end TempPersonalData
	
	public static Specialty readMedSpecialty(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of dept specialty 
		if (!firstEvent.isStartElement()) { //this could be issue, possibly. 
			throw new IllegalStateException("Attempting to read a specialty element but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(DATA)) {
			throw new IllegalStateException("Attempting to read a specialty element at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		Specialty dept = null;
		boolean finished = false;
		while (!finished) {
			XMLEvent event = eventReader.nextEvent(); //since we know start element is Specialty, 
			//just by calling this method, next event should be characters. 
			dept = Specialty.getFromString(event.asCharacters().getData()); //set a dept, see if end element is next (to return and finish).
			event = eventReader.nextEvent();
			// check the start elements for the nested elements inside NAME
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart().equals(SPECIALTY)) {
					event = eventReader.nextEvent();} //If error, just go to next and see if right. 
				else {System.err.println("Unrecognized element, ignoring: " + startElement.getName());}
			}
			// check the end elements to find where the name element is closed, </specialty>
			else if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				// when the end element is the name element, create the name return object;
				if (endElement.getName().getLocalPart().equals(SPECIALTY)) {
					//The dept is already set, since now the looped dept is right before end element. 
					finished = true;}
			}
			else {
				// ignore other events, such as character events with line feeds and tabs
			}
		}//end while loop
		return dept; //return dept Specialty enum. 
	}//end readMedSpecialty 
	
	public static Patient readPatient(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event, outside of <patient> start
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a patient but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(PATIENT)) {
			throw new IllegalStateException("Attempting to read a patient at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		// now we read the patient
		// first, read the attribute of patient ID
		int pID = 0; //initialize a local patient ID to zero
		//@SuppressWarnings("unchecked") // getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if (attribute.getName().getLocalPart().equals(PATIENT_ID)) {
				pID = Integer.valueOf(attribute.getValue()); // we know it is an integer from the schema
			}
			else {
				System.err.println("Found unknown attribute, ignoring; found: " + attribute.getName());
			}
		}
		// now we read the next events until we find the end event
		Patient patient = null;
		Name name = null;
		TempPersonalData pData = null;
		boolean finished = false;
		
		while (!finished) {
			XMLEvent event = eventReader.peek(); // peek to have the event reader remain before the next start element
			// check the start elements for the nested elements inside the patient
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				//either NAME or DATA sections. 
				if (startElement.getName().getLocalPart().equals(NAME)) {
					name = readName(eventReader);
				}
				else if (startElement.getName().getLocalPart().equals(DATA)) {
					pData = readTempPersonalData(eventReader);
					//System.out.println(pData.getDob());
					//System.out.println(pData.getSSN());
				}
				else {
					System.err.println("Unrecognized element within Patient class, ignoring: " + startElement.getName());
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}
			// check the end elements to find where the name element is closed
			else if (event.isEndElement()) {
				event = eventReader.nextEvent(); // retrieve the event
				EndElement endElement = event.asEndElement();
				// when the end element is the name element, create the name return object;
				if (endElement.getName().getLocalPart().equals(PATIENT)) {
					// Schema makes these required, so they must exist
					// would be a good practice to check for existence anyways
					//make name class type a string to inherit. 
					patient = new PatientImpl(name.toString(), pData.getSSN(), pData.getDob(), pID); //add here accordingly
					finished = true;
				}
			}
			else {
				// ignore other events, such as character events
				event = eventReader.nextEvent(); // skip this event and read the next
			}
		}
		return patient; //return .isEndElement construction. 
	}
	
	public static Doctor readDoctor(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event, outside of <patient> start
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a doctor but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(DOCTOR)) {
			throw new IllegalStateException("Attempting to read a doctor at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		// now we read the patient
		// first, read the attribute of doctor ID
		int dID = 0; //initialize a local doctor ID to zero
		//@SuppressWarnings("unchecked") // getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if (attribute.getName().getLocalPart().equals(DOCTOR_ID)) {
				dID = Integer.valueOf(attribute.getValue()); // we know it is an integer from the schema
			}
			else {
				System.err.println("Found unknown attribute, ignoring; found: " + attribute.getName());
			}
		}
		// now we read the next events until we find the end event
		Doctor doctor = null;
		Name name = null;
		Specialty dept = null; //dept is medical specialty enum. 
		TempPersonalData dData = null;
		
		boolean finished = false;
		
		while (!finished) {
			XMLEvent event = eventReader.peek(); // peek to have the event reader remain before the next start element
			// check the start elements for the nested elements inside the doctor
			//addition of medical specialty node. 
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				//either NAME or DATA sections. 
				if (startElement.getName().getLocalPart().equals(NAME)) {
					name = readName(eventReader);}
				else if (startElement.getName().getLocalPart().equals(DATA)) {
					dData = readTempPersonalData(eventReader);}
				else if(startElement.getName().getLocalPart().equals(SPECIALTY)) {
					//dept = readMedSpecialty(eventReader);
					String specDept = readCharacters(eventReader, SPECIALTY);
					dept = Specialty.valueOf(specDept); // needs error checking to ensure that the dept string exists
				}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName());
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}//end if start 
			// check the end elements to find where the name element is closed
			else if (event.isEndElement()) {
				event = eventReader.nextEvent(); // retrieve the event
				EndElement endElement = event.asEndElement();
				// when the end element is the name element, create the name return object;
				if (endElement.getName().getLocalPart().equals(DOCTOR)) {
					// Schema makes these required, so they must exist
					// would be a good practice to check for existence anyways
					//make name class type a string to inherit. 
					doctor = new DoctorImpl(name.toString(), dData.getSSN(), dData.getDob(), dID, dept); //add here accordingly
					finished = true;
				}
			}
			else {
				// ignore other events, such as character events
				event = eventReader.nextEvent(); // skip this event and read the next
			}
		}
		return doctor; //return .isEndElement construction. 
	}//end readDoctor
	
	public static Visit<Integer,Integer> readVisit (XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event, outside of <patient> start
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a patient but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(VISIT)) {
			throw new IllegalStateException("Attempting to read a patient at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		// now we read the patient
		// first, read the attribute of patient ID
		Integer pID = 0;//initialize a local patient ID to zero
		Integer dID = 0;//initialize a local doctor ID to zero
		//String visitDateString = null;
		//Date visitDate = null;
		Visit<Integer,Integer> visitObj = null;
		//@SuppressWarnings("unchecked") // getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if (attribute.getName().getLocalPart().equals(PATIENT_ID)) {
				pID = Integer.valueOf(attribute.getValue());} 
			else if (attribute.getName().getLocalPart().equals(DOCTOR_ID)) {
				dID = Integer.valueOf(attribute.getValue());} 
			/*
			else if (attribute.getName().getLocalPart().equals(VISIT_DATE)) {
				visitDateString = String.valueOf(attribute.getValue());
				visitDate = readDate(eventReader, visitDateString, VISIT_DATE_FORMAT);}
			*/ 
			else {System.err.println("Found unknown attribute, ignoring; found: " + attribute.getName());}
		}//End attribute while loop
		
		//read visitDate attribute
		Date visitDate = null;
		boolean finished = false;
		
		while (!finished) {
			XMLEvent event = eventReader.peek(); // peek to have the event reader remain before the next start element
			// check the start elements for the nested elements inside the doctor
			//addition of medical specialty node. 
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				//either NAME or DATA sections. 
				if (startElement.getName().getLocalPart().equals(VISIT_DATE)) {
					event = eventReader.nextEvent();
					String visitDateString = eventReader.nextEvent().asCharacters().getData();
					//correct dobString transferred. 
					visitDate = readDate(eventReader, visitDateString, VISIT_DATE_FORMAT);}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName());
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}//end if start 
			// check the end elements to find where the name element is closed
			else if (event.isEndElement()) {
				event = eventReader.nextEvent(); // retrieve the event
				EndElement endElement = event.asEndElement();
				// when the end element is the name element, create the name return object;
				if (endElement.getName().getLocalPart().equals(VISIT)) {
					// Schema makes these required, so they must exist
					// would be a good practice to check for existence anyways
					//make name class type a string to inherit. 
					//doctor = new DoctorImpl(name.toString(), dData.getSSN(), dData.getDob(), dID, dept); //add here accordingly
					
					//COMBINE
					visitObj = new VisitImpl<Integer,Integer>(pID, dID, visitDate);
					finished = true;
				}
			}
			else {
				// ignore other events, such as character events
				event = eventReader.nextEvent(); // skip this event and read the next
			}
		}
		
		return visitObj; //return .isEndElement construction.
	}
	
	public static SchedulerData readSchedulerData(String xmlFile) throws XMLStreamException, IOException {
		//creating a single SchedulerData object to return
		//this would allow multiple objects to be created (for example, for multiple doctor offices). 
		//SchedulerData sData = new SchedulerData(); 
		
		//Initialize three parameters of SchedulerData class. Construct at end once filled. 
		List<Patient> pList = new ArrayList<Patient>(); 
		List<Doctor> dList = new ArrayList<Doctor>();
		List<Visit<Integer,Integer>> vList = new ArrayList<Visit<Integer,Integer>>();
		
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		
		// Setup a new eventReader
		Path xmlFilePath = Paths.get(xmlFile); //inheret the xmlFile from main class INPUT
		Reader in = Files.newBufferedReader(xmlFilePath, StandardCharsets.UTF_8);
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		
		// Read the XML document
		while (eventReader.hasNext()) {
			// peek the next event
			// use peek so that we can actually read and check the next start element as it happens
			XMLEvent event = eventReader.peek(); 
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart() == (SCHEDULE_DATA)) {
					// just read the next event, which should be a patient or doctor
					event = eventReader.nextEvent(); // skip this event and read the next
				}
				// if we are at the top element for an student
				else if (startElement.getName().getLocalPart() == (PATIENT)) {
					Patient p = readPatient(eventReader);
					pList.add(p); //add patient object to patient list
				}
				else if (startElement.getName().getLocalPart() == (DOCTOR)) {
					Doctor d = readDoctor(eventReader);
					dList.add(d);
					
				}
				else if (startElement.getName().getLocalPart() == (VISIT)) {
					Visit<Integer,Integer> v = readVisit(eventReader);
					vList.add(v);
					//System.out.println(v.getVisitor() + " " + v.getHost() + " " + v.getVisitDate());
				}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName());
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}
			else {
				event = eventReader.nextEvent(); // skip this event and read the next
			}
		}//END WHILE LOOP
		
		//read until the end. 
		eventReader.close(); //close reader. 
		//initialize SchedulerData object with patient list, doctor list, and and visit list (vList). 
		SchedulerData sData = new SchedulerData(pList, dList, vList); 
		return sData;
	}//end readSchedulerData

}//end ScheduleXMLReaderUtils
