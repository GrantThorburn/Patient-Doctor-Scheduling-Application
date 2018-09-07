package edu.miami.bte324.hw4.gat51;

/**
 * @author Grant Thorburn
 *
 */
public interface Patient extends Person {
	
		/*
		//interface Person methods, to be extended via patient and doctor interfaces. Base interface.
		//all citizens share these traits
		public String getFullName();
		public String getSSN();
		public int getAge();
		public Date getBirthday();
		public String getFirstName();
		public String getLastName();
		*/ 
	
		//Patient methods. All patients share these traits. 
		public int getPatientID();

}//end patient interface
