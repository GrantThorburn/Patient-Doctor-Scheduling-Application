package edu.miami.bte324.hw4.gat51;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;


/**
 * @author Grant Thorburn
 *
 */
public class AddPatientGUI extends JFrame {
	private JTextField firstName;
	private JTextField lastName;
	private JTextField SSN;
	private JTextField DayDOB;
	private JTextField MonthDOB;
	private JTextField YearDOB;
	
	private String fnString;
	private String lnString;
	private String fullNameString;
	private String ssnString;
	private String dayString;
	private String monthString;
	private String yearString;
	private Date dobDate;
	private static ArrayList<Patient> transferList;
	protected static boolean closedBool = false;
	protected Patient p;
	private final static String OUTPUT_FILE = "resources\\scheduleOut.xml";
	
	public AddPatientGUI() {
	
			ArrayList<Patient> pList = new ArrayList<Patient>();
			transferList = pList; //clear it, just in case left over data 
			
			setSize(400,430);
			setLocation(600,300);
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			
			JLabel lblFirstName = new JLabel("First Name:");
			lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblFirstName.setBounds(28, 68, 90, 14);
			getContentPane().add(lblFirstName);
			
			JLabel lblLastName = new JLabel("Last Name:");
			lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblLastName.setBounds(28, 106, 80, 14);
			getContentPane().add(lblLastName);
			
			JLabel lblDob = new JLabel("DOB:");
			lblDob.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDob.setBounds(28, 174, 49, 14);
			getContentPane().add(lblDob);
			
			JLabel lblNewLabel = new JLabel("Day:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setBounds(69, 199, 49, 23);
			getContentPane().add(lblNewLabel);
			
			JLabel lblSsn = new JLabel("SSN:");
			lblSsn.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSsn.setBounds(28, 141, 49, 14);
			getContentPane().add(lblSsn);
			
			JLabel lblMonth = new JLabel("Month:");
			lblMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMonth.setBounds(69, 233, 65, 23);
			getContentPane().add(lblMonth);
			
			JLabel lblYear = new JLabel("Year:");
			lblYear.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblYear.setBounds(69, 269, 65, 23);
			getContentPane().add(lblYear);
			
			JLabel lblAddPatient = new JLabel("Add Patient");
			lblAddPatient.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblAddPatient.setBounds(124, 11, 250, 46);
			getContentPane().add(lblAddPatient);
			
			firstName = new JTextField();
			firstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			firstName.setBounds(124, 67, 108, 20);
			getContentPane().add(firstName);
			firstName.setColumns(10);
			
			lastName = new JTextField();
			lastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lastName.setColumns(10);
			lastName.setBounds(124, 105, 108, 20);
			getContentPane().add(lastName);
			
			SSN = new JTextField();
			SSN.setColumns(10);
			SSN.setBounds(124, 140, 108, 20);
			getContentPane().add(SSN);
			
			DayDOB = new JTextField();
			DayDOB.setColumns(10);
			DayDOB.setBounds(124, 202, 80, 20);
			getContentPane().add(DayDOB);
			
			MonthDOB = new JTextField();
			MonthDOB.setColumns(10);
			MonthDOB.setBounds(124, 237, 80, 20);
			getContentPane().add(MonthDOB);
			
			YearDOB = new JTextField();
			YearDOB.setColumns(10);
			YearDOB.setBounds(124, 272, 80, 20);
			getContentPane().add(YearDOB);
			
			
			String sucessString = firstName + " " + lastName + " added sucessfully.";
			final JLabel sucess = new JLabel(sucessString);
			sucess.setForeground(new Color(0, 128, 0));
			sucess.setFont(new Font("Tahoma", Font.BOLD, 14));
			sucess.setBounds(69, 320, 97, 37);
			
			
			JButton btnNewButton = new JButton("ENTER");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if((!firstName.getText().isEmpty()) && (!lastName.getText().isEmpty()) 
							&& (!SSN.getText().isEmpty()) && (!DayDOB.getText().isEmpty())
							&& (!MonthDOB.getText().isEmpty()) && (!YearDOB.getText().isEmpty())) {
						
						fnString = firstName.getText(); fnString.replaceAll("\\s+","");
						lnString = lastName.getText(); lnString.replaceAll("\\s+","");
						ssnString = SSN.getText(); ssnString.replaceAll("\\s+","");
						dayString = DayDOB.getText(); dayString.replaceAll("\\s+","");
						monthString = MonthDOB.getText(); monthString.replaceAll("\\s+","");
						yearString = YearDOB.getText(); yearString.replaceAll("\\s+","");
						
						//data is in each category.
						fullNameString = fnString + " " + lnString;
						String dateFormat = "yyyy/MM/dd";
						String dateString = yearString + "/" + monthString + "/" + dayString;				
						dobDate = Util.stringToDate(dateString, dateFormat); 
						if(ssnString.matches("\\d{3}-\\d{2}-\\d{4}")) {
							if(dobDate!=null) {
								//good to go
								//instantiate a patient object. 
								Patient p = new PatientImpl(fullNameString, ssnString, dobDate);
								pList.add(p);
								SchedulerData.addPatient(p);
								try {
									SchedulerData scheduleData = SchedulerData.getScheduleData();
									SchedulerXMLReadTest.updateOutputFile(scheduleData);	
								} catch (XMLStreamException | IOException e) {e.printStackTrace();}
								
								//guiSystem.transferPatient(p);
								//give a success message, since it went through, to confirm to user in nice way it's been saved.
									//reset fields
									firstName.setText("");
									lastName.setText("");
									SSN.setText("");
									DayDOB.setText("");
									MonthDOB.setText("");
									YearDOB.setText("");
									MessageDialog.infoBox("Patient Sucessfully Added", "Add Patient Confirmation");
								//ready to enter data again, refresh?
							} else {MessageDialog.infoBox("Error: Please Check DOB Validity", "Entry Error");}
						} else {MessageDialog.infoBox("Error: SSN format not valid format", "Entry Error");}
					} else { 
						//show an error message, keep data there to resubmit
						MessageDialog.infoBox("Null Data Detected, Please Retry Data Entry", "Add Patient Error");
					}//end else
				}//end action performed
			});
			btnNewButton.setForeground(new Color(0, 128, 0));
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnNewButton.setBounds(124, 312, 97, 37);
			getContentPane().add(btnNewButton);
			
			/*
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		        	transferList = pList;
		        	closedBool=true;
		            //System.out.println("In shutdown hook");
		        }
		    }, "Shutdown-thread"));
		    */
			
			addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	transferList = pList;
		        	closedBool=true;
	            	//System.out.println("Closed");
	                e.getWindow().dispose();
	            }
	        });
	
		
	}//end addPatientGUI

	public static List<Patient> getTransferList() {
		return transferList;
	}


	
	
	
	
	
}//end class
