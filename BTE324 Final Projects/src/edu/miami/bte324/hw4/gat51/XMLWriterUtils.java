package edu.miami.bte324.hw4.gat51;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

/**
 * @author Grant Thorburn
 *
 */


public class XMLWriterUtils {
	
	public static Characters getIndentation(XMLEventFactory eventFactory, int level) {
		// returns an object with as many tabs as needed to indent to the value specified by the input parameter
		char[] tabs = new char[level];
		Arrays.fill(tabs, '\t'); // fill the number of tabs
		return eventFactory.createIgnorableSpace(String.valueOf(tabs)); // and create an ignorable space
	}

	public static void writeNode(XMLEventFactory eventFactory, XMLEventWriter eventWriter, 
			String name, String value, int level) throws XMLStreamException {
		// Create Start node
		eventWriter.add(getIndentation(eventFactory, level));
		StartElement startElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(startElement);
		// Create Content
		Characters charValue = eventFactory.createCharacters(value);
		eventWriter.add(charValue);
		// Create End node
		EndElement endElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(endElement);
		// line feed
		eventWriter.add(eventFactory.createIgnorableSpace("\n"));
	}

	public static String xmlStandardDateFormat = "yyyy-MM-dd"; // ignore time zones for simplicity
	private final static String UTC_TIME = "yyyy-MM-dd HH:mm";

	public static void writeDate(XMLEventFactory eventFactory, XMLEventWriter eventWriter, String name, Date date, int level) throws XMLStreamException {
		// write the date in the specific date format required by XML Schema
		DateFormat df = new SimpleDateFormat(xmlStandardDateFormat); // ignore time zones for simplicity
		//DateFormat df = new SimpleDateFormat(UTC_TIME); // ignore time zones for simplicity
		String dateStr = df.format(date);
		//String dateStr = df.format(date.getTime());
		writeNode(eventFactory, eventWriter, name, dateStr, level);
	}
	
	public static void writeVisitDate(XMLEventFactory eventFactory, XMLEventWriter eventWriter, String name, Date date, int level) throws XMLStreamException {
		// write the date in the specific date format required by XML Schema
		DateFormat df = new SimpleDateFormat(UTC_TIME); // ignore time zones for simplicity
		//DateFormat df = new SimpleDateFormat(UTC_TIME); // ignore time zones for simplicity
		String dateStr = df.format(date);
		//String dateStr = df.format(date.getTime());
		writeNode(eventFactory, eventWriter, name, dateStr, level);
	}
	


}
