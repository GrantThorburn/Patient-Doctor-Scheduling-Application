package edu.miami.bte324.hw4.gat51;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;

/**
 * @author Grant Thorburn
 *
 */
//make an output factory, take file and scheduler data object. Follow a writer, same way, until you 
//Every time you need a new line, need to make a new line. 
//write patient, write visit, write doctor (get event factory, level, under getIndentation. 
//Instatiate a name of doctor object, writerPersonalData, writeMEDIUCAL speciatly, then done to add it. 
//writeName(eventFactory, eventwruter, dName, level +1)
public final class SchedulerXMLWriteTest extends SchedulerReadWriteUtils {

	//two first lines from xml, so you can just do them
	private final static String NAMESPACE = "http://www.miami.edu/cis324/xml/scheduling"; //not sure if right
	private final static String SCHEMA_INSTANCE_PREFIX = "xsi";
	private final static String SCHEMA_INSTANCE_NS = "http://www.w3.org/2001/XMLSchema-instance";
	private final static String SCHEMA_LOCATION_ATTRNAME = "schemaLocation";
	private final static String SCHEMA_FILE_NAME = "scheduling.xsd"; //this should be right. 
	//private final static String UTC_TIME = "MM/dd/yyyy KK:mm:ss a Z";
	//private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static void writeName(XMLEventFactory eventFactory, XMLEventWriter eventWriter, String fullName, int level) throws XMLStreamException {
		// first, write as many tabs as levels needed
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		// start element
		eventWriter.add(eventFactory.createStartElement("", "", NAME));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		// first name
		String firstName = Util.nameSplitFirst(fullName);
		XMLWriterUtils.writeNode(eventFactory, eventWriter, FIRST_NAME, firstName, level+1);
		// last name
		String lastName = Util.nameSplitLast(fullName);
		XMLWriterUtils.writeNode(eventFactory, eventWriter, LAST_NAME, lastName, level+1);
		// end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level)); // also indent it
		eventWriter.add(eventFactory.createEndElement("", "", NAME));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
	}

	public static void writeData(XMLEventFactory eventFactory, XMLEventWriter eventWriter, Object t, int level) throws XMLStreamException{
		// first, write as many tabs as levels needed
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		// start element
		eventWriter.add(eventFactory.createStartElement("", "", DATA));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		if(t instanceof Patient) {
			Patient p = (Patient) t;
			// dob dateOfBirth
			XMLWriterUtils.writeDate(eventFactory, eventWriter, DOB, p.getDOB(), level+1); //might be an issue...
			//SSN 
			XMLWriterUtils.writeNode(eventFactory, eventWriter, SSN, p.getSSN(), level+1);
			eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level)); // also indent it
			eventWriter.add(eventFactory.createEndElement("", "", DATA));
			eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability	
		}
		if(t instanceof Doctor) {
			Doctor d = (Doctor) t;
			XMLWriterUtils.writeDate(eventFactory, eventWriter, DOB, d.getDOB(), level+1); //might be an issue...
			//SSN 
			XMLWriterUtils.writeNode(eventFactory, eventWriter, SSN, d.getSSN(), level+1);
			eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level)); // also indent it
			eventWriter.add(eventFactory.createEndElement("", "", DATA));
			eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		}
		
		return; //error, could also be a huge error if wrong to use...
		
	}//end writeData
	
	public static void writePatient(XMLEventFactory eventFactory, XMLEventWriter eventWriter, Patient p, int level) throws XMLStreamException {
		// writes a single patient through to the XML event writer
		// create the patient start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		StartElement patientStart = eventFactory.createStartElement("", "", PATIENT);
		eventWriter.add(patientStart);
		// create the id and visit_date attributes
	    // note the use of Integer.toString to get a string representation
	    Attribute patientId = eventFactory.createAttribute(PATIENT_ID, Integer.toString(p.getPatientID()));
	    eventWriter.add(patientId);
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		
	    // now create the nested elements
	    writeName(eventFactory, eventWriter, p.getFullName(), level + 1); //splits within writeName call. 
	    writeData(eventFactory, eventWriter, p, level + 1);
	    // create the student end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		
	    EndElement patientEnd = eventFactory.createEndElement("", "", PATIENT);
	    eventWriter.add(patientEnd);
	}//end writePatient

	public static void writeDoctor(XMLEventFactory eventFactory, XMLEventWriter eventWriter, Doctor d, int level) throws XMLStreamException {
		// writes a single doctor through to the XML event writer
		// create the doctor start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		StartElement doctorStart = eventFactory.createStartElement("", "", DOCTOR);
		eventWriter.add(doctorStart);
		// create the id attribute
		// note the use of Integer.toString to get a string representation
		Attribute doctorId = eventFactory.createAttribute(DOCTOR_ID, Integer.toString(d.getDoctorID()));
		eventWriter.add(doctorId);
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
				
		// now create the nested elements
		writeName(eventFactory, eventWriter, d.getFullName(), level + 1); //splits within writeName call. 
		writeData(eventFactory, eventWriter, d, level + 1);
		
		//node of specialty
		XMLWriterUtils.writeNode(eventFactory, eventWriter, SPECIALTY, d.getDept().toString(), level + 1);

		// create the doctor end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
				
		EndElement doctorEnd = eventFactory.createEndElement("", "", DOCTOR);
		eventWriter.add(doctorEnd);
	}//end writeDoctor

	public static void writeVisit(XMLEventFactory eventFactory, XMLEventWriter eventWriter, Visit<Integer,Integer> v, int level) throws XMLStreamException {
		// writes a single visit through to the XML event writer
		// create the visit start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
	    StartElement visitStart = eventFactory.createStartElement("", "", VISIT);
	    eventWriter.add(visitStart);
	    // create the id attribute
	    // note the use of Integer.toString to get a string representation
	    Attribute patientId = eventFactory.createAttribute(PATIENT_ID, Integer.toString(v.getVisitor()));
	    eventWriter.add(patientId);
	    Attribute doctorId = eventFactory.createAttribute(DOCTOR_ID, Integer.toString(v.getHost()));
	    eventWriter.add(doctorId);
	    //Attribute visitDate = eventFactory.createAttribute(VISIT_DATE, v.getVisitDate().toString());
	    /*
	    Date vDate = v.getVisitDate();
	    Attribute visitDate = eventFactory.createAttribute(VISIT_DATE, vDate.toString());
	    eventWriter.add(visitDate);
	    */
	    eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
	    Date vDate = v.getVisitDate();
	    XMLWriterUtils.writeVisitDate(eventFactory, eventWriter, VISIT_DATE, vDate, level+1);
	    
	    
		
	   
	    // create the visit end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
	    EndElement visitEnd = eventFactory.createEndElement("", "", VISIT);
	    eventWriter.add(visitEnd);
	}//end writeVisit

	public static void writeSchedulerList(String outFile, SchedulerData sData) throws XMLStreamException, IOException {
	    // Create a XMLOutputFactory
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    // Create XMLEventWriter
	    Path outputFilePath = Paths.get(outFile);
	    Writer outputFile = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8);
	    XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(outputFile);
	    // Create an XMLEventFactory
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    // Create and write Start Tag
	    StartDocument startDocument = eventFactory.createStartDocument("UTF-8", "1.0");
	    eventWriter.add(startDocument);
	    // put a linefeed for readability
	    eventWriter.add(eventFactory.createIgnorableSpace("\n"));
	    // create the root element
	    StartElement root = eventFactory.createStartElement("", "", SCHEDULE_DATA); //root
		eventWriter.add(root);
	    eventWriter.setDefaultNamespace(SchedulerXMLWriteTest.NAMESPACE); // set the default namespace for the root before adding it
		// add any other namespaces to the root
	    eventWriter.add(eventFactory.createNamespace(NAMESPACE));
	    eventWriter.add(eventFactory.createNamespace(SCHEMA_INSTANCE_PREFIX, SCHEMA_INSTANCE_NS));
	    // add the schema attributes to the root element 
	    String schemaLocationArg = NAMESPACE + " " + SCHEMA_FILE_NAME;
	    eventWriter.add(eventFactory.createAttribute(SCHEMA_INSTANCE_PREFIX, SCHEMA_INSTANCE_NS, SCHEMA_LOCATION_ATTRNAME, schemaLocationArg));
	    // put a linefeed for readability
	    eventWriter.add(eventFactory.createIgnorableSpace("\n"));
	    
		// iterate over each list(Patient,Doctor,Visit), create an element for each
	    List<Doctor> dArray = new ArrayList<Doctor>(sData.getDoctorList()); 
		List<Patient> pArray = new ArrayList<Patient>(sData.getPatientList());
		List<Visit<Integer,Integer>> vArray = new ArrayList<Visit<Integer,Integer>>(sData.getVisitList());
		
		Iterator<Patient> IT_P = pArray.iterator();
		while (IT_P.hasNext()){
			Patient p = IT_P.next(); 
			writePatient(eventFactory, eventWriter, p, 1);
			eventWriter.add(eventFactory.createIgnorableSpace("\n"));
		}//end while for patient
		
		Iterator<Doctor> IT_D = dArray.iterator();
		while (IT_D.hasNext()){
			Doctor d = IT_D.next(); 
			writeDoctor(eventFactory, eventWriter, d, 1);
			 eventWriter.add(eventFactory.createIgnorableSpace("\n"));
		}//end while for patient
		
		Iterator<Visit<Integer,Integer>> IT_V = vArray.iterator();
		while (IT_V.hasNext()){
			Visit<Integer,Integer> v = IT_V.next(); 
			writeVisit(eventFactory, eventWriter, v, 1);
			 eventWriter.add(eventFactory.createIgnorableSpace("\n"));
		}//end while for visit
	    
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}



}
