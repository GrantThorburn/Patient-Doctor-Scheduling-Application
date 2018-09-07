package edu.miami.bte324.hw4.gat51;

import java.util.Date;

/**
 * @author Grant Thorburn
 *
 */
public class TempPersonalData {
	String ssn;
	Date dob;
	
	TempPersonalData(String ssn, Date dob){
		this.ssn=ssn;
		this.dob=dob;}

	public String getSSN() {return ssn;}
	public Date getDob() {return dob;}
	
}//end class

