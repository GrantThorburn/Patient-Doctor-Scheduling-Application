package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */

//Try to return a Specialty enum value. If it doesn't work, throw a IllegalArgumentException for that 

public enum Specialty {
	GENERAL_MEDICINE, PEDIATRICS, ONCOLOGY;
	
	public static Specialty getFromString(String m) {
		try{
			return valueOf(m.toUpperCase()); }
		catch (Exception e) {
			System.out.println("Enum Specialty for Doctor data incorrect for String '" + m + "'\n");
			throw new IllegalArgumentException(m);}	
	} 
}

	

