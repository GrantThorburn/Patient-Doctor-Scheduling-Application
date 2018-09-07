package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */

import java.util.Date;

public class DoctorImpl extends PersonImpl implements Doctor{
	private int doctorID;
	private Specialty dept;
	private static int maxInheritID = 0; //initialize
	
	public DoctorImpl() {}
	
	public DoctorImpl(String fullName, String SSN, Date dob, int dID, String doctorDepartment) {
		super(fullName, SSN, dob); //call the personImpl
		doctorID = dID;
		dept=Specialty.getFromString(doctorDepartment); 
		if(doctorID>maxInheritID) { maxInheritID = doctorID;}
	}//end DoctorImpl
	
	public DoctorImpl(String fullName, String SSN, Date dob, int dID, Specialty doctorDepartment) {
		super(fullName, SSN, dob); //call the personImpl
		doctorID = dID;
		dept=doctorDepartment;
		if(doctorID>maxInheritID) { maxInheritID = doctorID;}
	}//end DoctorImpl
	
	//for ease of user input
	public DoctorImpl(String fullName, String SSN, String dob, int dID, String doctorDepartment) {
		super(fullName, SSN, dob); //call the personImpl
		doctorID = dID;
		dept=Specialty.getFromString(doctorDepartment);
		if(doctorID>maxInheritID) { maxInheritID = doctorID;}
	}//end DoctorImpl
	
	//for UI
	public DoctorImpl(String fullName, String SSN, Date dob, Specialty doctorDepartment) {
		super(fullName, SSN, dob); //call the personImpl
		dept=doctorDepartment;
		maxInheritID++; //increment one plus before assigning
		doctorID=maxInheritID;
	}//end DoctorImpl
	
	
	//Interface Getters and Setters
	public int getDoctorID() { return doctorID;}
	public Specialty getDept() {return dept;}
	
	//equals and hashCode. Cannot use doctorID, but can use dept + Person super class. 
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + doctorID;  
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof DoctorImpl))
			return false;
		DoctorImpl other = (DoctorImpl) obj;
		if (dept != other.dept)
			return false;
		if (doctorID != other.doctorID)
			return false;
		return true;
	}

	
}//end doctorImpl
