package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
import java.util.Date;

public class PatientImpl extends PersonImpl implements Patient{
	private int	patientID; 
	private static int maxInheritID = 0; //initialize. 
	
	public PatientImpl() {}
	 
	public PatientImpl(String fullName, String SSN, Date dob, int pID) {
		super(fullName, SSN, dob); //call the personImpl
		//if xml already assigns patient IDs... still need to check actual person, excluding unique ID. 
		patientID = pID; 
		if(patientID>maxInheritID) { maxInheritID = patientID;}
	}//end main constructor

	//for ease of user input
	public PatientImpl(String fullName, String SSN, String dob, int pID) {
		super(fullName, SSN, dob); //call the personImpl
		//if xml already assigns patient IDs... still need to check actual person, excluding unique ID. 
		patientID = pID;
		if(patientID>maxInheritID) { maxInheritID = patientID;}
	}//end main constructor
	
	//for UI
	public PatientImpl(String fullName, String SSN, Date dob) {
		super(fullName, SSN, dob);
		//max inherited ID found, so now creating ID's we increment from this number. 
		maxInheritID++; //increment one plus before assigning
		patientID = maxInheritID;
	}//end main constructor
	

	//Getters, as required from Patient Interface:
	public int getPatientID() { return patientID;}
	
	//equals and hashCode, on person basis, entirely inherited from person class. Cannot use patientID. 
	@Override
	public int hashCode() {
		int result = super.hashCode();
		//result = prime * result + patientID; //comparing if PERSON is the same. 
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof PatientImpl))
			return false;
		PatientImpl other = (PatientImpl) obj;
		if (patientID != other.patientID)
			return false;
		return true;
	}

}//end PatientImpl

