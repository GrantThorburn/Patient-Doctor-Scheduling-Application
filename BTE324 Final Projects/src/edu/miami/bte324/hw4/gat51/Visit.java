package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
import java.util.Date;

public interface Visit<V,T> {
	public V getVisitor();
	public T getHost();
	public Date getVisitDate();
}
