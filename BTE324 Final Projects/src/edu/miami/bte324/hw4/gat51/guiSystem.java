package edu.miami.bte324.hw4.gat51;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;


import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * @author Grant Thorburn
 *
 */
public class guiSystem extends JFrame {

	private JPanel contentPane;
	private SchedulerData sData = new SchedulerData(); //initialize
	private SchedulerData hsData = new SchedulerData(); //initialize
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiSystem frame = new guiSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public guiSystem() {
		
		//Initialize data, IO XML processing. 
		try {
			sData = SchedulerXMLReadTest.initializeXML();
			SchedulerData.initSchedulerData(sData); //keep records in the class.
		} catch (XMLStreamException | IOException e) {e.printStackTrace();}

		//SchedulerData temp = SchedulerData.getScheduleData();
		//Now ready to set the guiSystem. 
		
		setForeground(Color.WHITE);
		setBackground(SystemColor.menu);
		setTitle("Thorburn - Scheduling System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
	
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("");
		menuBar.add(menu);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mnFile);
		
		JMenu mnAdd_1 = new JMenu("Add");
		mnFile.add(mnAdd_1);
		
		JMenuItem mntmAddPatient = new JMenuItem("Add Patient");
		mntmAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//run the class. 
				AddPatientGUI a = new AddPatientGUI();
				//need to wait till user exits program
			}//end action performed
		});
		mnAdd_1.add(mntmAddPatient);
		
		JMenuItem mntmAddDoctor = new JMenuItem("Add Doctor");
		mntmAddDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddDoctorGUI d = new AddDoctorGUI();
			}
		});
		mnAdd_1.add(mntmAddDoctor);
		
		JMenuItem mntmAddVisit = new JMenuItem("Add Visit");
		mnAdd_1.add(mntmAddVisit);
		
		JMenu mnRemove_1 = new JMenu("Remove");
		mnFile.add(mnRemove_1);
		
		JMenuItem mntmRemovePatient = new JMenuItem("Remove Patient");
		mnRemove_1.add(mntmRemovePatient);
		
		JMenuItem mntmRemoveDoctor = new JMenuItem("Remove Doctor");
		mnRemove_1.add(mntmRemoveDoctor);
		
		JMenuItem mntmRemoveVisit = new JMenuItem("Remove Visit");
		mnRemove_1.add(mntmRemoveVisit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmPatientList = new JMenuItem("Patient List");
		mnView.add(mntmPatientList);
		
		JMenuItem mntmDoctorList = new JMenuItem("Doctor List");
		mnView.add(mntmDoctorList);
		
		JMenuItem mntmVisitList = new JMenuItem("Visit List");
		mnView.add(mntmVisitList);
		
		JButton btnMain = new JButton("Main");
		btnMain.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnMain.setBackground(new Color(192, 192, 192));
		btnMain.setForeground(Color.BLACK);
		menuBar.add(btnMain);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}//end guiSystem method
	

	public void transferPatient(Patient p){
		sData.addPatient(sData,p);}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}//end guiSystem class
