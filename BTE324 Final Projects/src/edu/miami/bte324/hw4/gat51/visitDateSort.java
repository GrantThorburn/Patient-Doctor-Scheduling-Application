package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
import java.util.Comparator;
import java.util.Date;

public class visitDateSort implements Comparator<Visit<Integer,Integer>>{
	public int compare(Visit<Integer,Integer> arg0, Visit<Integer,Integer> arg1) {
		// TODO Auto-generated method stub
		Date d1 = arg0.getVisitDate();
		Date d2 = arg1.getVisitDate();
		return d1.after(d2) ? 1 : -1; //if d1 is after d2, return 1, else return -1 (d2 is after d1). 
	}//end compare
}//end visitDateSort
