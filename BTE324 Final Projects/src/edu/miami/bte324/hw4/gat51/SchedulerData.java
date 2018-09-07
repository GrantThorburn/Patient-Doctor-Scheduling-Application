package edu.miami.bte324.hw4.gat51;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Grant Thorburn
 *
 */
public class SchedulerData {
	
	//perhaps make generic

	//List Variables
	private static List<Patient> patientList; //added at end, once List built via patientImpl
	private static List<Doctor> doctorList; //add at end, once List built via doctorImpl
	private static Map<Integer,Patient> pMap; //just to search through
	private static Map<Integer,Doctor> dMap; //just to search through
	private static List<Visit<Integer,Integer>> visitList; //made via util fillVisitArray, sending a visit set at end.
	private static SchedulerData scheduleData;
	private static SchedulerData scheduleDataHist;
	
	public SchedulerData() {} //to initialize 
	
	public SchedulerData(List<Patient> patientList, List<Doctor> doctorList, List<Visit<Integer,Integer>> visitList ) {
		//initialize a SchedulerData object. A single object! A single grouped entity, already established.  
		this.patientList = patientList;
		this.doctorList = doctorList;
		this.visitList = visitList;
		//fill the Maps. Lists already filled. 
		pMap = fillPatientIdMap(patientList);
		dMap = fillDoctorIdMap(doctorList);
	}//end SchedulerData constructor
	
	//try and initialize the XML data object. Keep an accessible class in SchedulerData. 
	//only once.
	public static void initSchedulerData(SchedulerData obj) { 
		scheduleData = obj;
		scheduleDataHist = obj;
	}//end SchedulerData constructor
	
	/*
	public SchedulerData(List<Patient> patientList, SchedulerData obj){
		
	}
	*/
	
	//getters
	public List<Patient> getPatientList() {return patientList;}
	public List<Doctor> getDoctorList() {return doctorList;}
	public List<Visit<Integer, Integer>> getVisitList() {return visitList;}
	public Map<Integer, Patient> getpMap() {return pMap;}
	public Map<Integer, Doctor> getdMap() {return dMap;}
	public static SchedulerData getScheduleData() {return scheduleData;}
	public static SchedulerData getScheduleDataHist() {return scheduleDataHist;}
	
	//add patient and doctor functions. Updates the dMap and pMap. 
	//duplicates -> check the appropriate integer map. If key is present, send out message 
	//you would not add a new doctor without knowing the ID. plain and simple. Can still check for duplicate. 
	//leaving this open for an infinite loop, not ceasing functions but simply asking the user again. 
	
	//figure out what to do with a new patient's ID. might make sense to have a unique number counter. 
	//Maybe need it for doctors as well. Setting up both implement classes to allow doctor and patient to be 
	//added, and then to assign a patientID, doctorID. 
	
	

	public void addPatient(List<Patient> patientList, Patient p) {
		if(!pMap.containsKey(p.getPatientID())){ //if it does not contain int value, add	
			patientList.add(p); int tempID = p.getPatientID(); pMap.put(tempID, p);
		} else { System.out.println("Duplicate found for PatientId " + p.getPatientID() + ", " + p.getFullName() + ", did not add");}
	}//end addPatient
	public void addDoctor(List<Doctor> doctorList, Doctor d) {
		if(!dMap.containsKey(d.getDoctorID())) {
			doctorList.add(d); int tempID = d.getDoctorID(); dMap.put(tempID, d);
		} else {System.out.println("Duplicate found for DoctorId " + d.getDoctorID() + ", " + d.getFullName() + ", did not add");}
	}//end addDoctor
	public void addPatient(SchedulerData obj, Patient p) {
		if(!pMap.containsKey(p.getPatientID())){
			patientList = obj.getPatientList();
			patientList.add(p);
			int tempID = p.getPatientID(); pMap.put(tempID, p);	
		} else {System.out.println("Duplicate found for PatientId " + p.getPatientID() + ", " + p.getFullName() + ", did not add");}
	}//end addPatient
	public void addDoctor(SchedulerData obj, Doctor d) {
		if(!dMap.containsKey(d.getDoctorID())) {
			doctorList = obj.getDoctorList();
			doctorList.add(d);
			int tempID = d.getDoctorID(); dMap.put(tempID, d);
		} else {System.out.println("Duplicate found for DoctorId " + d.getDoctorID() + ", " + d.getFullName() + ", did not add");}
	}//end addDoctor
	public static void addPatient(Patient p) {
		if(!pMap.containsKey(p.getPatientID())){ //if it does not contain int value, add	
			patientList.add(p); int tempID = p.getPatientID(); pMap.put(tempID, p);
			//scheduleData.patientList.add(p);
		} else { System.out.println("Duplicate found for PatientId " + p.getPatientID() + ", " + p.getFullName() + ", did not add");}
	}//end addPatient
	public static void addDoctor(Doctor d) {
		if(!dMap.containsKey(d.getDoctorID())) {
			doctorList.add(d); int tempID = d.getDoctorID(); dMap.put(tempID, d);
			//scheduleData.doctorList.add(d);
		} else {System.out.println("Duplicate found for DoctorId " + d.getDoctorID() + ", " + d.getFullName() + ", did not add");}
	}//end addDoctor
	
	public void removePatient(List<Patient> patientList, Patient p) {
		int tempID = p.getPatientID(); patientList.remove(p); pMap.remove(tempID);} //only need ID to find and remove quickly
	public void removeDoctor(List<Doctor> doctorList, Doctor d) {
		int tempID = d.getDoctorID(); doctorList.remove(d); dMap.remove(tempID);}
	public void removePatient(SchedulerData obj, Patient p) {
		patientList = obj.getPatientList();
		int tempID = p.getPatientID();
		patientList.remove(p); pMap.put(tempID, p);}
	public void removeDoctor(SchedulerData obj, Doctor d) {
		doctorList = obj.getDoctorList();
		int tempID = d.getDoctorID();
		doctorList.remove(d); dMap.put(tempID, d);}
	
	//add and remove visit functions. Can work for either 2 ID's, or actual Patient and Doctor instantiation. 
	public void addVisitInt(List<Visit<Integer,Integer>> visitList, Visit<Integer,Integer> v) {visitList.add(v);}
	public void addVisitPD(List<Visit<Integer,Integer>> visitList, Visit<Patient,Doctor> v) {
		//search for duplicate within the 
		Boolean pFound = false;
		Boolean dFound = false;
		if(pMap.containsValue(v.getHost())) {
			
		}
		
	}
	
	
	
	//Order does not matter, need fastest get() performance to match. 
	//Better to include this method within class vs Util, just for applicability to inherent functions of class. 
	public static Map<Integer,Patient> fillPatientIdMap(List<Patient> pList){
		Set<Patient> tempSet = new HashSet<Patient>(pList); 
		HashMap<Integer, Patient> tempMap = new HashMap<Integer, Patient>();
		Iterator<Patient> IT1 = tempSet.iterator(); //iterate through tempSet. 
		while (IT1.hasNext()) {
			Patient p = (Patient) IT1.next();//whole List iterator.
			tempMap.put(p.getPatientID(), p);
		}//end while	
		return tempMap;	
	}//end fillIDmap. 
	
	public static Map<Integer,Doctor> fillDoctorIdMap(List<Doctor> dList){
		Set<Doctor> tempSet = new HashSet<Doctor>(dList); 
		HashMap<Integer, Doctor> tempMap = new HashMap<Integer, Doctor>();
		Iterator<Doctor> IT1 = tempSet.iterator(); //iterate through tempSet. 
		while (IT1.hasNext()) {
			Doctor d = (Doctor) IT1.next();//whole List iterator.
			tempMap.put(d.getDoctorID(), d);
		}//end while	
		return tempMap;	
	}//end fillIDmap. 

	
	
	
	
	
}//end SchedulerData
