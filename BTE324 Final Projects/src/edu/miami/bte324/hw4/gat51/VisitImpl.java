package edu.miami.bte324.hw4.gat51;
/**
 * @author Grant Thorburn
 *
 */
import java.util.Date;

public class VisitImpl<V,T> implements Visit<V,T> {
	private V visitor;
	private T host;
	private Date visitDate;
	
	public VisitImpl(V visitor, T host) {
		this.visitor= visitor;
		this.host = host;
		//this.visitDate = visitDate;
	}//end constructor

	public VisitImpl(V visitor, T host, Date visitDate) {
		this.visitor= visitor;
		this.host = host;
		this.visitDate = visitDate;
	}//end constructor

	
	public VisitImpl(V visitor, T host, String aptDate) { 
		this.visitor= visitor;
		this.host = host;
		visitDate=Util.stringToDate(aptDate);
	}

	public V getVisitor() {
		return visitor;
	}

	public T getHost() {
		return host;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	@Override
	public int hashCode() {
		//Have already removed duplicates from Visit, so on a patient/doctor level it does not matter
		//now it checks for visit appointment duplicates. 
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((visitDate == null) ? 0 : visitDate.hashCode());
		result = prime * result + ((visitor == null) ? 0 : visitor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VisitImpl))
			return false;
		VisitImpl other = (VisitImpl) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		if (visitor == null) {
			if (other.visitor != null)
				return false;
		} else if (!visitor.equals(other.visitor))
			return false;
		return true;
	}
	
	
	
	
	
}//end VisitImpl
