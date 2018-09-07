package edu.miami.bte324.hw4.gat51;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLStreamException;

/**
 * @author Grant Thorburn
 *
 */
public class SchedulerXMLReadTest {
	
	private final static String INPUT_FILE = "resources\\schedulerData.xml";
	private final static String OUTPUT_FILE = "resources\\scheduleOut.xml";
	
	//XMLStreamException: "The base exception for unexpected processing errors."
	//IOException: "Signals that an I/O exception of some sort has occurred."
	public static SchedulerData initializeXML() throws XMLStreamException, IOException {
		SchedulerData sData = ScheduleXMLReaderUtils.readSchedulerData(INPUT_FILE);
		//input file can be processed and read again in readSchedulerData. Nothing is wrong with it.
		SchedulerXMLWriteTest.writeSchedulerList(OUTPUT_FILE, sData); //written out for xml output initialization
		//send it 
		Print.formatC(sData);
		return sData;} //return to the guiSystem.

	public static void updateOutputFile(SchedulerData obj) throws XMLStreamException, IOException {
		SchedulerXMLWriteTest.writeSchedulerList(OUTPUT_FILE, obj);}
	public static void updateOutputFile(String output, SchedulerData obj) throws XMLStreamException, IOException {
		SchedulerXMLWriteTest.writeSchedulerList(output, obj);}
	public static void updateOutputFile() throws XMLStreamException, IOException {
		SchedulerData scheduleData = SchedulerData.getScheduleData();
		SchedulerXMLWriteTest.writeSchedulerList(OUTPUT_FILE, scheduleData);}
		
}//end SchedulerData









		//Random Notes

		//Print.formatC(sData);

		/*
		//format: must enter a patient and doctor into system before adding a visit!
		//test: adding doctor and patient before entering a visit.
		Doctor d = new DoctorImpl("John Smith","445-25-3382", "06/03/1941", 1, "general_medicine");
		sData.addDoctor(sData.getDoctorList(), d);
		List<Doctor> d2 = new ArrayList<Doctor>(sData.getDoctorList()); //this will add it to List. 
		System.out.println(d2.get(4).getAge());
		sData.removeDoctor(sData, d);
		List<Doctor> d4 = new ArrayList<Doctor>(sData.getDoctorList()); //this will add it to List.
		System.out.println(d4.get(3).getAge());
		*/
		
		//could be cool to make a login and embed the 2 factor google authentication. 
		
		//make today's date, rather than May 8th, for each entity. 
		
		//have a Report class, similair to a print class, taking the visit list, todays date, doctorId mmap, and other shit.
		
		//use JFrame 
		//desktop paine, myDesktopPain with JDesktopPain. Menu bar at top. options.. remove and unremove. 
		//when you exit, write data and pass to output file. 
		//in main, open input file. if it does not exist, have an empty object!
		//set a counter for doctors and patients for ids. Build report lists. 
		
		//report center to retrieve specified formats. Top left bars should be Patient, Doctor, Visit, Reports, Exit. 
		//there is a design studio for Swing. Swing package can download into Eclipse. 
		//Google how to do the drag and drop, swing is outdated though. At least something. 
		//for web application, have to do applets. Could use any web application to grab data from xml.
		//Some sort of GUI. 
		
		
		//a couple of screens, input and output. The logic of files. Create a backup xml data. 1)Save record. 
		//most of the code is generated. Really have to add what you want it to do. 
		//Just have a few screens. 