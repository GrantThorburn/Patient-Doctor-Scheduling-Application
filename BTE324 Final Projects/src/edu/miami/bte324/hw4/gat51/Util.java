package edu.miami.bte324.hw4.gat51;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author Grant Thorburn
 *
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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
import java.util.concurrent.TimeUnit;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

/**
 * @author Grant Thorburn
 *
 */
public class Util {

	//String name to first name
	public static String nameSplitFirst(String fullName) {
		//first name's typically are only one name, unlike last name
		String firstName;
		String nameT = fullName;
		String[] temp = nameT.split(" ");
		firstName = temp[0];
		return firstName;
		
	}//end nameSplitLast
	
	//String name to last name
	public static String nameSplitLast(String fullName) {
		//first name's typically are only one name, unlike last name
		String lastName;
		String nameT = fullName;
		String[] temp = nameT.split(" ");
		if(temp.length<=2) { //if 3 elements or more -> Else, two words. More efficient, rare to have two last names. 
			lastName = temp[1];	
		} else {
			lastName = temp[1] + " " + temp[2]; //in case their are two last names. Dash will count as temp[1]
			//ex. williams-koch as temp[1], jonathan barzaga as temp[1] and temp[2]. 
		}
		return lastName;
		
	}//end nameSplitLast
	

	//String to Date, assuming year month date. 
	public static Date stringToDate(String dateString) { //year, month, day). 
		//utilize Java Pattern class to check most common, throw an error if date is unable to be calculated. 
		String dateFormat = "yyyy/MM/dd";
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			//df.parse(dateStr);
			df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cDate = df.getCalendar();
		Date date =  cDate.getTime();
		//System.out.println(date);
		return date;
	}//end stringToDate
	
	//String to Date, custom inheritance of user. 
	public static Date stringToDate(String dateString, String dateFormat) { 
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cDate = df.getCalendar();
		Date date =  cDate.getTime();
		//System.out.println(date);
		return date;
	}//end stringToDate
	
	
	

	
	//Date to int age
	public static int ageCalc(Date dob) {
		//https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
		int age;
		LocalDate bth = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate today = LocalDate.now();	
		Period period = Period.between(bth, today);
		age = period.getYears();
		return age;
	}//end ageCalc for date	
	
	//HashSet Patient
	public static Set<Patient> fillPatientSet(String[][] patientArray) {
				HashSet<Patient> tempSet = new HashSet<Patient>();
				//HashSet runs on O(1) time, prevents duplicates. 
				//patient 
				for (int x = 0; x < patientArray.length; x ++) {
					String a = null, b =null, c = null, d = null;
				    String subArray[] = patientArray[x]; 
				    for (int y = 0; y < subArray.length; y ++) {
				        String item = subArray[y];
				        //store data here
				        if(y==0) {a = item;}
				        if(y==1) {b = item;}
				        if(y==2) {c = item;}
				        if(y==3) {d = item;}
				    }
				    tempSet.add(new PatientImpl(a,b,c,d));
				 }//end patient for
				/*
				//doctors
				for (int x = 0; x < doctorArray.length; x ++) {
					String a = null, b =null, c = null, d = null;
				    String subArray[] = doctorArray[x]; 
				    for (int y = 0; y < subArray.length-1; y ++) { //***y-1***
				        String item = subArray[y];
				        //store data here
				        if(y==0) {a = item;}
				        if(y==1) {b = item;}
				        if(y==2) {c = item;}
				        if(y==3) {d = item;}
				    }
				    System.out.println("Adding " + a); //*****************************remove after testing
				    tempSet.add(new PatientImp(a,b,c,d));
				 }//end patient for
				*/
				return tempSet;
				
	}//end fillPatientSet
	
	//HashSet Doctor
	public static Set<Doctor> fillDoctorSet(String[][] doctorArray){
		HashSet<Doctor> tempSet = new HashSet<Doctor>();
		//doctors
		for (int x = 0; x < doctorArray.length; x ++) {
			String a = null, b =null, c = null, d = null, e = null;
		    String subArray[] = doctorArray[x]; 
		    for (int y = 0; y < subArray.length; y ++) { //***y***
		        String item = subArray[y];
		        //store data here
		        if(y==0) {a = item;}
		        if(y==1) {b = item;}
		        if(y==2) {c = item;}
		        if(y==3) {d = item;}
		        if(y==4) {e = item;} //for Specialty 
		    }
		    tempSet.add(new DoctorImpl(a,b,c,d,e));
		 }//end patient for

		return tempSet;
		
	}//end fillDoctorSet
	
	//HashSet Visit
	public static Set<Visit<Patient,Doctor>> fillVisitSet(String[][] visitArray, Set<Patient> patientTS, Set<Doctor> doctorTS) {
		// TODO Auto-generated method stub
		HashSet<Visit<Patient,Doctor>> tempSet = new HashSet<Visit<Patient,Doctor>>();
		//HashSet runs on O(1) time, prevents duplicates. 
		//Visit
		for (int x = 0; x < visitArray.length; x ++) {
			Patient a = null;
			Doctor b = null;
			Date c = null;
		    String subArray[] = visitArray[x]; 
		    for (int y = 0; y < subArray.length; y ++) {
		        String item = subArray[y];
		        Util util = new Util();
		        if(y==0) { a = util.findPatientBySSN(item, patientTS);} //patient Object
		        if(y==1) { b = util.findDoctorBySSN(item, doctorTS);} //doctor object
		        if(y==2) { c = Util.stringToDate(item);} //Date
		    }
		    tempSet.add(new VisitImpl(a,b,c));
		 }//end patient for
		
		return tempSet;
	}
	
	//Get days until, from two dates. 
	public static long getDayDiff(Date earlyDate, Date lateDate) {
		//compare two dates, one today and one birthday. Return difference, using TimeUnit.	    
		long diff = lateDate.getTime() - earlyDate.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	//getInstance, hash to tree
	public static <T> Set<T> getInstance(Set<T> hashSet){
		Set<T> treeSet = new TreeSet<>();
		treeSet.addAll(hashSet);
		return treeSet;}
	
	//Match patient SSN to patientTS, via fillVisitSet
	public Patient findPatientBySSN(String SSN, Set<Patient> patientTS) {  
        Iterator<Patient> iterator = patientTS.iterator();
        while(iterator.hasNext()) {
            Patient patient = iterator.next();
            if(patient.getSSN() == SSN)             
                return patient; //return matched Patient Object
        }//end while
        return null; //Not found -> null              
    }//end findPatientBySSN
	
	//Match doctor SSN to doctorTS, via fillVisitSet 
	public Doctor findDoctorBySSN(String SSN, Set<Doctor> doctorTS) {  
        Iterator<Doctor> iterator = doctorTS.iterator();
        while(iterator.hasNext()) {
            Doctor doctor = iterator.next();
            if(doctor.getSSN() == SSN)             
                return doctor; //return matched Doctor Object
        }//end while
        return null; //Not found -> null              
    }//end findDoctorBySSN

	//Match patient SSN to patientID, via...
	public static Integer findPatientIDBySSN(String SSN, Set<Patient> patientTS) {  
		Iterator<Patient> iterator = patientTS.iterator();
		while(iterator.hasNext()) {
		     Patient patient = iterator.next();
		     if(patient.getSSN() == SSN)             
		        return patient.getPatientID(); //return matched patientID
		     }//end while
		 return null; //Not found -> null              
	}//end findPatientIDBySSN
			
	//Match doctor SSN to doctorID, via...
	public static Integer findDoctorIDBySSN(String SSN, Set<Doctor> doctorTS) {   
		Iterator<Doctor> iterator = doctorTS.iterator();
		while(iterator.hasNext()) {
		     Doctor doctor = iterator.next();
		     if(doctor.getSSN() == SSN)             
		        return doctor.getDoctorID(); //return matched doctorID
		     }//end while
		return null; //Not found -> null              
	}//end findDoctorBySSN
	
	//fill vIntArray
	public static List<Visit<Integer,Integer>> fillVisitArray(Set<Visit<Patient,Doctor>> visitTS){
		
		ArrayList<Visit<Integer,Integer>> tempArray = new ArrayList<Visit<Integer,Integer>>();
		//Fill each index with appropriate visitor and host, as well as visit date. 
		//using an Array to maintain order (sorted within treeSet)
		Iterator<Visit<Patient,Doctor>> IT1 = visitTS.iterator();
		while (IT1.hasNext()) {
			Integer pID = null; Integer dID = null; Date aptDate = null;
			Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT1.next();//whole list iterator.
			Patient p = (Patient) v.getVisitor();
			Doctor d = (Doctor) v.getHost();
			pID = p.getPatientID();
			dID = d.getDoctorID();
			aptDate = v.getVisitDate();
			tempArray.add(new VisitImpl(pID,dID,aptDate)); //goes to (I,I,Date) constructor within generic visit class. 
		}//end IT1 iterator
		return tempArray;
		
		/*
		ArrayList<Visit<Integer,Integer>> tempArray = new ArrayList<Visit<Integer,Integer>>();
		//Fill each index with appropriate visitor and host, as well as visit date. 
		//using an Array to maintain order (sorted within treeSet)
		//check to see if each arrayList is same size. Just a precaution. Loop via index. 
		if((pIDarray.size() == dIDarray.size()) && (pIDarray.size() == visitDateArray.size())) {
			for (int x = 0; x < visitDateArray.size(); x ++) {
				Integer a = null;
				Integer b = null;
				Date c = null;
			    a = pIDarray.get(x);
			    b = dIDarray.get(x);
			    c = visitDateArray.get(x);
			    tempArray.add(new VisitImpl(a,b,c));
			 }//end for loop
			
			return tempArray;
		}//end if statement to check size. 
		
		*/
		//return null;
	}//end fillVisitArray
	
	//Order does not matter, need fastest get() performance to match. 
	public static Map<Integer, Patient> fillPatientIdMap(Set<Visit<Patient,Doctor>> visitTS){
		HashMap<Integer, Patient> tempMap = new HashMap<Integer, Patient>();
		
		Iterator<Visit<Patient,Doctor>> IT1 = visitTS.iterator();
		while (IT1.hasNext()) {
			Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT1.next();//whole list iterator.
			Patient p = (Patient) v.getVisitor(); //temp patient becomes visitor of Visit class. 
			
			tempMap.put(p.getPatientID(), p);
		}//end while
		
		return tempMap;
		
	}//end fillIDmap. 
	
	
	
	public static Map<Integer, Doctor> fillDoctorIdMap(Set<Visit<Patient,Doctor>> visitTS){
		HashMap<Integer, Doctor> tempMap = new HashMap<Integer, Doctor>();
		
		Iterator<Visit<Patient,Doctor>> IT1 = visitTS.iterator();
		while (IT1.hasNext()) {
			Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT1.next();//whole list iterator.
			Doctor d = (Doctor) v.getHost(); //temp patient becomes visitor of Visit class. 
			
			tempMap.put(d.getDoctorID(), d);
		}//end while
		
		return tempMap;
		
	}//end fillIDmap. 
	
	public static Map<Patient, Set<Visit<Patient,Doctor>>> fillPatMap(Set<Patient> patientTS, Set<Visit<Patient,Doctor>> visitTS){
		//Cycle through each patient, and check if they have an appointment. If they do, add each appointment. 
		//The LinkedHashMap will maintain the sorted order. 
		Map<Patient, Set<Visit<Patient,Doctor>>> tempMap = new LinkedHashMap<Patient, Set<Visit<Patient,Doctor>>>();
		Iterator<Patient> IT1 = patientTS.iterator();
		while(IT1.hasNext()) {
			//test to see if Patient has appointment. 
			Patient p = (Patient) IT1.next();//whole list iterator. Test each patient  
			//only add patients if they have an appointment
			//if match is true, add, then cycle rest of appointments. 
			Iterator<Visit<Patient,Doctor>> IT2 = visitTS.iterator();
			boolean match = false;
			while (IT2.hasNext()) {
				Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT2.next();//whole list iterator.
				Patient pTEST = (Patient) v.getVisitor(); //temp patient becomes visitor of Visit class. 
				if(p.equals(pTEST)) {match = true;}	
			}//end IT2
				
			//if found, Patient in records has an appointment. Add them to the map.
			if(match==true) {
				Set<Visit<Patient,Doctor>> tempSet = new LinkedHashSet<Visit<Patient,Doctor>>(); //collection of appointments to add.
				Iterator<Visit<Patient,Doctor>> IT3 = visitTS.iterator();
				while (IT3.hasNext()) {
					Visit<Patient,Doctor> v1 = (Visit<Patient,Doctor>) IT3.next();//whole list iterator.
					Patient pTEST = (Patient) v1.getVisitor(); //temp patient becomes visitor of Visit class. 
					if(pTEST.equals(p)) {
						//if the pTest patient of appointment IT2 loop is equal to p patient of IT1,
						//it is indication of appointment, so add to tempMap by pushing in as an object.
						Doctor d = v1.getHost();
						Date aptDate = v1.getVisitDate();
						tempSet.add(new VisitImpl(p, d ,aptDate)); //add each Visit<Patient,Doctor>> appointment to tempSet.
					}//end patient equality test	
				}//end IT3, end of appointment cycle
				
				//by this point, for patient p, has already cycled through entire appointment list. Ready to add to tempMap
				tempMap.put(p, tempSet); //(patient, set<visit<patient,doctor>>>();
			}//end if==true

		}//end IT1
		
		//return the tempMap after IT1 loop of patient list has been completed. Now patient map matches appointments of visitTS. 
		return tempMap;
	}//end fillPatMap
	
	public static Map<Doctor, Set<Visit<Patient,Doctor>>> fillDocMap(Set<Doctor> doctorTS, Set<Visit<Patient,Doctor>> visitTS){
		//Cycle through each doctor, and check if they have an appointment. If they do, add each appointment. 
		//The LinkedHashMap will maintain the sorted order. 
		Map<Doctor, Set<Visit<Patient,Doctor>>> tempMap = new LinkedHashMap<Doctor, Set<Visit<Patient,Doctor>>>();
		Iterator<Doctor> IT1 = doctorTS.iterator();
		while(IT1.hasNext()) {
			//test to see if Doctor has appointment. 
			Doctor d = (Doctor) IT1.next();//whole list iterator. Test each doctor 
			//only add doctors if they have an appointment
			//if match is true, add, then cycle rest of appointments. 
			Iterator<Visit<Patient,Doctor>> IT2 = visitTS.iterator();
			boolean match = false;
			while (IT2.hasNext()) {
				Visit<Patient,Doctor> v = (Visit<Patient,Doctor>) IT2.next();//whole list iterator.
				Doctor dTEST = (Doctor) v.getHost(); //temp doctor becomes visitor of Visit class. 
				if(d.equals(dTEST)) {match = true;}	
			}//end IT2
				
			//if found, Doctor in records has an appointment. Add them to the map.
			if(match==true) {
				Set<Visit<Patient,Doctor>> tempSet = new LinkedHashSet<Visit<Patient,Doctor>>(); //collection of appointments to add.
				Iterator<Visit<Patient,Doctor>> IT3 = visitTS.iterator();
				while (IT3.hasNext()) {
					Visit<Patient,Doctor> v1 = (Visit<Patient,Doctor>) IT3.next();//whole list iterator.
					Doctor dTEST = (Doctor) v1.getHost(); //temp patient becomes visitor of Visit class. 
					if(dTEST.equals(d)) {
						//if the dTEST patient of appointment IT2 loop is equal to p patient of IT1,
						//it is indication of appointment, so add to tempMap by pushing in as an object.
						Patient p = v1.getVisitor();
						Date aptDate = v1.getVisitDate();
						tempSet.add(new VisitImpl(p, d ,aptDate)); //add each Visit<Patient,Doctor>> appointment to tempSet.
					}//end patient equality test	
				}//end IT3, end of appointment cycle
				
				//by this point, for doctor d, has already cycled through entire appointment list. Ready to add to tempMap
				tempMap.put(d, tempSet); //(doctor, set<visit<patient,doctor>>>();
			}//end if==true

		}//end IT1
		
		//return the tempMap after IT1 loop of doctor list has been completed. Now patient map matches appointments of visitTS. 
		return tempMap;
	}//end fillPatMap
	
	
	
	
	
	
	
}//end Util class
