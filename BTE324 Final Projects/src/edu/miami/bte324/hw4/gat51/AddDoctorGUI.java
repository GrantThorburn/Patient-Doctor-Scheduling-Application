package edu.miami.bte324.hw4.gat51;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

/**
 * @author Grant Thorburn
 *
 */
public class AddDoctorGUI extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField SSN;
	private JTextField DayDOB;
	private JTextField MonthDOB;
	private JTextField YearDOB;
	private JComboBox Department;
	
	private String fnString;
	private String lnString;
	private String fullNameString;
	private String ssnString;
	private String dayString;
	private String monthString;
	private String yearString;
	private Date dobDate;
	private Specialty dept;
	private static ArrayList<Doctor> transferList;
	protected static boolean closedBool = false;
	protected Doctor d;
	
	public AddDoctorGUI() {
		setSize(400,450);
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
		
		JLabel lblAddPatient = new JLabel("Add Doctor");
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
		
		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSpecialty.setBounds(28, 303, 80, 23);
		getContentPane().add(lblSpecialty);
		
		Department = new JComboBox();
		Department.setModel(new DefaultComboBoxModel(Specialty.values()));
		Department.setBounds(124, 303, 160, 22);
		getContentPane().add(Department);
		
		JButton btnNewButton = new JButton("ENTER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((!firstName.getText().isEmpty()) && (!lastName.getText().isEmpty()) 
						&& (!SSN.getText().isEmpty()) && (!DayDOB.getText().isEmpty())
						&& (!MonthDOB.getText().isEmpty()) && (!YearDOB.getText().isEmpty()
								&& (Department.getSelectedIndex()!=-1))) {
					
					fnString = firstName.getText(); fnString.replaceAll("\\s+","");
					lnString = lastName.getText(); lnString.replaceAll("\\s+","");
					ssnString = SSN.getText(); ssnString.replaceAll("\\s+","");
					dayString = DayDOB.getText(); dayString.replaceAll("\\s+","");
					monthString = MonthDOB.getText(); monthString.replaceAll("\\s+","");
					yearString = YearDOB.getText(); yearString.replaceAll("\\s+","");
					
					//data is in each category. Selected Index already noted for Specialty. 
					fullNameString = fnString + " " + lnString;
					String dateFormat = "yyyy/MM/dd";
					String dateString = yearString + "/" + monthString + "/" + dayString;				
					dobDate = Util.stringToDate(dateString, dateFormat); 
					dept = (Specialty) Department.getSelectedItem();
					if(ssnString.matches("\\d{3}-\\d{2}-\\d{4}")) {
						if(dobDate!=null) {
							//good to go
							//instantiate a patient object. 
							Doctor d = new DoctorImpl(fullNameString, ssnString, dobDate, dept);
							SchedulerData.addDoctor(d);
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
								Department.setSelectedIndex(0); //resets to no choice. 
								MessageDialog.infoBox("Doctor Sucessfully Added", "Add Doctor Confirmation");
							//ready to enter data again, refresh?
						} else {MessageDialog.infoBox("Error: Please Check DOB Validity", "Entry Error");}
					} else {MessageDialog.infoBox("Error: SSN format not valid format", "Entry Error");}
				} else { 
					//show an error message, keep data there to resubmit
					MessageDialog.infoBox("Null Data Detected, Please Retry Data Entry", "Add Doctor Error");
				}//end else
			}//end action performed
		});
		btnNewButton.setForeground(new Color(0, 128, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(124, 345, 97, 37);
		getContentPane().add(btnNewButton);
		

	}//END ADD DOCTOR GUI
}//END CLASS
