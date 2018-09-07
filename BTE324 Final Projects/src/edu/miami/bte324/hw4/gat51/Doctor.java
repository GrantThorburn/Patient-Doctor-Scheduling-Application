package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
public interface Doctor extends Person {
	//in assign 4, no longer extending patient interface. Does extend the Person interface
	public int getDoctorID();
	public Specialty getDept();
}
