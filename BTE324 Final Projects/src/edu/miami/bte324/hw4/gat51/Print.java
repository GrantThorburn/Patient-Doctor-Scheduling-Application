package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Print {
	
	//Print class 
	
	public static void divider() {
		System.out.println("--------------------------------------------------");}
	
	//not necessary, would need a new constructor within SchedulerData for treeSet. 
	public static void syncListToTS(SchedulerData sData) {
		//make local calls for variables to utilize. 
		List<Doctor> d2 = new ArrayList<Doctor>(sData.getDoctorList()); 
		List<Patient> p2 = new ArrayList<Patient>(sData.getPatientList());
		List<Visit<Integer,Integer>> v2 = new ArrayList<Visit<Integer,Integer>>(sData.getVisitList());
		//default treeSet comparator. If unique one to be used, would edit within parameter of instantiation.
		Set<Patient> patientSet = new HashSet<Patient>(p2);
		Set<Doctor> doctorSet = new HashSet<Doctor>(d2);
		Set<Doctor> doctorTS = new TreeSet<Doctor>();
		doctorTS.addAll(doctorSet);
		Set<Patient> patientTS = new TreeSet<Patient>();
		patientTS.addAll(patientSet);
		//Specifications wanted Lists to store SchedulerData. I prefer using Sets for printing specifications. 
		Set<Visit<Integer,Integer>> visitSetObj = new HashSet<Visit<Integer,Integer>>(v2); //transfer Visit List into unsorted hashset, more efficient sorting
		Set<Visit<Integer,Integer>> visitTS = new TreeSet<Visit<Integer,Integer>>(new visitDateSort()); //sorted by date
		visitTS.addAll(visitSetObj);
		//now have a sorted visitTS to print with. 
		SchedulerData sDataTS = new SchedulerData();
		Collections.sort(v2, new Comparator<Visit<Integer,Integer>>() {
			public int compare(Visit<Integer,Integer> arg0, Visit<Integer,Integer> arg1) {
				 if (arg0.getVisitDate() == null || arg1.getVisitDate() == null)
				        return 0;
				Date d1 = arg0.getVisitDate();
			  	Date d2 = arg1.getVisitDate();
			  	return d1.after(d2) ? 1 : -1; //if d1 is after d2, return 1, else return -1 (d2 is after d1). 
			}//end compare 
		});

	}//end sync class. 
		
	//Each entity visit, sorted in upcoming visitDate order. 
	public static void formatC(SchedulerData sData) {
		//make local calls for variables to utilize. 
		List<Visit<Integer,Integer>> vArray = new ArrayList<Visit<Integer,Integer>>(sData.getVisitList());
		Map<Integer,Patient> pMap = new HashMap<Integer,Patient>(sData.getpMap());
		Map<Integer,Doctor> dMap = new HashMap<Integer,Doctor>(sData.getdMap());
		//sort date accordingly to format. 
		Collections.sort(vArray, new visitDateSort()); //now sorted by date. 
		SimpleDateFormat df1 = new SimpleDateFormat("MMMM dd, yyyy HH:mm"); //accepted below as %s for string
		
		Iterator<Visit<Integer,Integer>> IT1 = vArray.iterator();
		while (IT1.hasNext()){
			Visit<Integer,Integer> visit = IT1.next(); //each loop is a V<I,I> visit. 
			//verify
			if(visit.getVisitDate()==null) {System.out.println("Visit is null"); return;}
			if(!pMap.containsKey(visit.getVisitor())) {System.out.println("PatientID for visit " +visit.toString() + " not found. Return."); return;}
			if(!dMap.containsKey(visit.getHost())) {System.out.println("DoctorID for visit " +visit.toString() + " not found. Return."); return;}
			//assign patient object via key 
			Patient p = pMap.get(visit.getVisitor());
			Doctor d = dMap.get(visit.getHost());
			
			//Days Until. 
			Date randomDate = Util.stringToDate("2018/05/08"); //random date for 2018
			Long daysUntil = Util.getDayDiff(randomDate, visit.getVisitDate());
			
			//print
			System.out.printf(String.format("%-15s %s %s" , "Visit Date:", df1.format(visit.getVisitDate()), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Doctor:", d.getFullName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Specialty:", d.getDept(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Days Until Visit:", daysUntil, "\n"));
			System.out.printf(String.format("%-15s %s" , "Patient:", "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tFirst Name:", p.getFirstName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tLast Name:", p.getLastName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tSSN:", p.getSSN(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tAge:", p.getAge(), "\n\n"));

		}//end visit iterator. 
	}//end Print.formatC
	
	
	public static void formatD(Set<Patient> patientTS, Set<Visit<Patient,Doctor>> visitTS) {
		SimpleDateFormat df1 = new SimpleDateFormat("dd MMMM yyyy"); //accepted below as %s for string
		Iterator<Patient> IT1 = patientTS.iterator();
		while (IT1.hasNext()) {
			Patient p = (Patient) IT1.next();//whole list iterator. 
			//only print patients if they have an appointment
			//if match is true, print header, then cycle to print appointments. 
			Iterator<Visit<Patient,Doctor>> IT2 = visitTS.iterator();
			boolean match = false;
			while (IT2.hasNext()) {
				Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT2.next();//whole list iterator.
				Patient p1 = (Patient) v.getVisitor(); //temp patient becomes visitor of Visit class. 
				if(p.equals(p1)) {match = true;}	
			}//end IT2
			
			//if found, print header, then cycle through visit again to print 
			if(match==true) {
				//print header: Patient name, SSN, age
				String formatName = p.getLastName() + ", " + p.getFirstName();
				System.out.printf(String.format("%-15s %s %s" , "Patient Name:", formatName, "\n"));
				System.out.printf(String.format("%-15s %s %s" , "SSN:", p.getSSN(), "\n"));
				System.out.printf(String.format("%-15s %s %s" , "Age:", p.getAge(), "\n"));
				System.out.printf("Upcoming Visits:\n");
				
				Iterator<Visit<Patient,Doctor>> IT3 = visitTS.iterator();
				//cycle to print appointments
				while (IT3.hasNext()) {
					Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT3.next();//whole list iterator.
					Patient p1 = (Patient) v.getVisitor(); //temp patient becomes visitor of Visit class. 
					Doctor d = (Doctor) v.getHost(); //temp doctor becomes host of Visit class
					if(p.equals(p1)) {
						System.out.printf(String.format("%-15s %s %s" , "\tVisit Date:", df1.format(v.getVisitDate()), "\n"));
						System.out.printf(String.format("%-15s %s %s" , "\tDoctor:", d.getFullName(), "\n"));
						System.out.printf(String.format("%-15s %s %s" , "\tSpecialty:", d.getDept(), "\n"));
						System.out.println("\n");
					}//end equals for appointment printing
				}//end IT3
			}//end match==true
		}//end IT1
	}//end Print.formatD
	
	public static void formatI(List<Visit<Integer,Integer>> vIntArray, Map<Integer, Patient> patientIdMap,
			Map<Integer,Doctor> doctorIdMap) {
		SimpleDateFormat df1 = new SimpleDateFormat("MMMM dd, yyyy");
		Iterator<Visit<Integer,Integer>> IT1 = vIntArray.iterator(); //each iteration is a visit, with visitor, host, and apt date. 
		while (IT1.hasNext()) {
			
			Visit<Integer,Integer> v = (Visit<Integer,Integer>) IT1.next();//whole list iterator.
			
			//Assign Integer ID from Visit<Integer,Integer>
			Integer pID = (Integer) v.getVisitor(); 
			Integer dID = (Integer) v.getHost();
			Date visitDate = (Date) v.getVisitDate();

			//HashMap to quickly match the getVisitor patient ID, to the key within patientIdMap, to create a patient. Same for doctor.
			Patient p = patientIdMap.get(pID);
			Doctor d = doctorIdMap.get(dID);
			
			Date randomNov2014Date = Util.stringToDate("11/01/2014"); //visits are in 2014, so assumed "days until" from random date 
			Long daysUntil = Util.getDayDiff(randomNov2014Date, visitDate);
			
			//Print in specified format. 
			System.out.printf(String.format("%-15s %s %s" , "Visit Date:", df1.format(visitDate), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Doctor:", d.getFullName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Specialty:", d.getDept(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Days Until Visit:", daysUntil, "\n"));
			System.out.printf(String.format("%-15s %s" , "Patient:", "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tFirst Name:", p.getFirstName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tLast Name:", p.getLastName(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tEmail:", p.getPatientEmail(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tSSN:", p.getSSN(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "\tAge:", p.getAge(), "\n"));
			System.out.println("\n");
		}//end IT1 iteration
	}//end formatI printing method. 
	
	public static void patListing(Map<Patient, Set<Visit<Patient,Doctor>>> patMap) {
		SimpleDateFormat df1 = new SimpleDateFormat("dd MMMM yyyy"); //accepted below as %s for string
		for (Map.Entry<Patient, Set<Visit<Patient,Doctor>>> entry : patMap.entrySet()) {
			Patient p = entry.getKey(); //access patient via key
			//print header: Patient name, SSN, age. All patients in patMap have at least one appointment. 
			String formatName = p.getLastName() + ", " + p.getFirstName();
			System.out.printf(String.format("%-15s %s %s" , "Patient Name:", formatName, "\n"));
			System.out.printf(String.format("%-15s %s %s" , "SSN:", p.getSSN(), "\n"));
			System.out.printf(String.format("%-15s %s %s" , "Age:", p.getAge(), "\n"));
			System.out.printf("Upcoming Visits:\n\n");
			
			Set<Visit<Patient,Doctor>> tempSet = entry.getValue();
			Iterator<Visit<Patient,Doctor>> IT1 = tempSet.iterator();
			while (IT1.hasNext()) { //will only loop for amount of appointments particular person has. 
				Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT1.next();//whole list iterator
				Doctor d = (Doctor) v.getHost();
				System.out.printf(String.format("%-15s %s %s" , "\tVisit Date:", df1.format(v.getVisitDate()), "\n"));
				System.out.printf(String.format("%-15s %s %s" , "\tDoctor:", d.getFullName(), "\n"));
				System.out.printf(String.format("%-15s %s %s" , "\tSpecialty:", d.getDept(), "\n"));
				System.out.println("\n");
			}//end IT1
		}//end map for loop. 
	}//end patListing
	
	public static void docListing(Map<Doctor, Set<Visit<Patient,Doctor>>> docMap){
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd"); //accepted below as %s for string
		for (Map.Entry<Doctor, Set<Visit<Patient,Doctor>>> entry : docMap.entrySet()) {
			Doctor d = entry.getKey();
			if(d.getDept().equals(Specialty.GENERAL_MEDICINE)) {
				//print doctor name and specialty. 
		    	System.out.printf(String.format("%-15s %s %s" , "Doctor:", d.getFullName(), "\n"));
				System.out.printf(String.format("%-15s %s %s" , "Specialty:", d.getDept(), "\n"));
				System.out.printf("Upcoming Visits:\n\n");
				//print all appointments
				Set<Visit<Patient,Doctor>> tempSet = entry.getValue();
				Iterator<Visit<Patient,Doctor>> IT1 = tempSet.iterator();
				while (IT1.hasNext()) { //will only loop for amount of appointments particular person has. 
					Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT1.next();//whole list iterator
					Patient p = (Patient) v.getVisitor();
					System.out.printf(String.format("%-15s %s %s" , "\tVisit Date:", df1.format(v.getVisitDate()), "\n"));
					System.out.printf(String.format("%-15s %s %s" , "\tFirst Name:", p.getFirstName(), "\n"));
					System.out.printf(String.format("%-15s %s %s" , "\tLast Name:", p.getLastName(), "\n"));
					System.out.printf(String.format("%-15s %s %s" , "\tSSN:", p.getSSN(), "\n"));
					System.out.printf(String.format("%-15s %s %s" , "\tAge:", p.getAge(), "\n"));
					System.out.println("\n");
					
				}//end IT1 while loop
			}//end dept equality if statement. 
		}//end for loop of docMap
	}//end docListing
	
	
	
	
	
}//END PRINT CLASS
