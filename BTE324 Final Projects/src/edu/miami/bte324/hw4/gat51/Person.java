package edu.miami.bte324.hw4.gat51;

import java.util.Date;

/**
 * @author Grant Thorburn
 *
 */
public interface Person {
	//From assign 3 Patient interface
	//interface Person methods, to be extended via patient and doctor interfaces. Base interface.
	//all Person's share these traits
	public String getFullName();
	public String getSSN();
	public int getAge();
	public Date getDOB();
	public String getFirstName();
	public String getLastName();
}//End Person interface
