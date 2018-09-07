package edu.miami.bte324.hw4.gat51;

import java.util.Date;

/**
 * @author Grant Thorburn
 *
 */
public abstract class PersonImpl implements Person {
	//every Person has a name (first, last, full), an SSN (US citizen assumption), dob, and age
	private String fullName, firstName, lastName, SSN;
	private int	age;
	private Date DOB; 
	
	public PersonImpl() {} //empty default constructor
	
	//xml data sends a firstName, lastName, SSN, and date dob
	public PersonImpl(String fullName, String SSN, Date dob) {
		DOB = dob; //assign xml Date dob of a Person as birthday. 
		this.SSN=SSN;
		this.fullName=fullName;
		firstName=Util.nameSplitFirst(fullName);
		lastName = Util.nameSplitLast(fullName);
		age = Util.ageCalc(dob); //use Date to int ageCalc utility function
		
	}//end main PersonImpl constructor
	
	//string date conversion (for user input). 
	public PersonImpl(String fullName, String SSN, String dob) {
		Date DOB = Util.stringToDate(dob);
		this.SSN=SSN;
		this.fullName=fullName;
		firstName=Util.nameSplitFirst(fullName);
		lastName = Util.nameSplitLast(fullName);
		age = Util.ageCalc(DOB); //use Date to int ageCalc utility function
		
	}//end main PersonImpl constructor
	

	//Getters, as required from Patient Interface:
	public String getFullName() {return fullName;}
	public int getAge() {return age;}
	public String getSSN() {return SSN;}
	public Date getDOB() {return DOB;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}

	//equals and hashCode. To check if person is duplicate... 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SSN == null) ? 0 : SSN.hashCode());
		result = prime * result + age;
		result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PersonImpl))
			return false;
		PersonImpl other = (PersonImpl) obj;
		if (SSN == null) {
			if (other.SSN != null)
				return false;
		} else if (!SSN.equals(other.SSN))
			return false;
		if (age != other.age)
			return false;
		if (DOB == null) {
			if (other.DOB != null)
				return false;
		} else if (!DOB.equals(other.DOB))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
	
}//end PersonImpl
