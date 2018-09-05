package travel;



import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import travel.DBconnection;
import travel.SecondWindow;
import travel.SecondWindow;
import travel.Queries;
import net.proteanit.sql.DbUtils;
import travel.Populate;
import travel.Queries;

// Class TravelAgency containing UI Elements for the program
public class TravelAgency {

	JFrame frmTravelagency;
	private JTable tableResults;
	private JComboBox comboBox_Customer;
	private JComboBox comboBox_Country;
	private JComboBox comboBox_City;
	private JScrollPane scrollPane;
	
	Connection conn	= null;										// Declaring connection conn
	
	
	
	

	/**
	 * Create the application.
	 */
	
	public TravelAgency() {
		
		// Adapts the appearance of the program according to the target environment
		try 
		{
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		initialize();
		this.frmTravelagency.setVisible(true);
		
		
		conn = DBconnection.dbconnect();							// Calling connect method from ConnectDB
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTravelagency = new JFrame();
		frmTravelagency.setTitle("TravelAgency");
		frmTravelagency.setBounds(100, 100, 643, 298);
		frmTravelagency.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTravelagency.getContentPane().setLayout(null);
		
		// Creating object Populate of the Class Populate in order to utilise its methods at filling and emptying data
		Populate p = new Populate();	
		
		// Declaring, Adding a JButton called "Search" with an ActionListener that updates the table according to the given query when called
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				DefaultTableModel model = (DefaultTableModel) tableResults.getModel();
				model.setRowCount(1);
				tableResults.setModel(model);
				System.out.println("Hej!");
				
				// Using the constructor from Class Queries to form "sqlq" and gain access to all relevant methods in the class
				Queries sqlq = new Queries(comboBox_Customer, comboBox_Country, comboBox_City);	
					
					
				try {
					String query = sqlq.search();								// Calling search() from class Queries	
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					tableResults.setModel(DbUtils.resultSetToTableModel(rs));	// Using an external JAR (rs2xml) to alter the table structure 
																				// according to the result set
					
				
				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(10, 227, 89, 23);
		frmTravelagency.getContentPane().add(btnSearch);
		
		// Declaring, Adding combo-box for Customers showing Surnames
		comboBox_Customer = new JComboBox(p.customer().toArray());			// Calling object "p", declared earlier as of Class Populate, to fill the combo-box with values from an ArrayList
		comboBox_Customer.setMaximumRowCount(10);
		comboBox_Customer.setBounds(10, 71, 89, 20);
		frmTravelagency.getContentPane().add(comboBox_Customer);
		p.emptyList();														// Emptying the ArrayList in order to allow for the next set of values be introduced
		
		// Combo-box for Country
		comboBox_Country = new JComboBox(p.country().toArray());			// Populate "p" again, fill combo-box with Country names from the array
		comboBox_Country.setBounds(10, 137, 89, 20);
		frmTravelagency.getContentPane().add(comboBox_Country);
		p.emptyList();														// Emptying the ArrayList
		
		// Combo-box displaying Cities
		comboBox_City = new JComboBox(p.city().toArray());					// Fill combo-box with names of Cities
		comboBox_City.setBounds(10, 176, 89, 20);
		comboBox_City.addActionListener(comboBox_Country);
		frmTravelagency.getContentPane().add(comboBox_City);
		p.emptyList();														// Empty ArrayList
		
		// Add JLable "Customer" 
		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(10, 46, 46, 14);
		frmTravelagency.getContentPane().add(lblCustomer);
		
		// Add Jlabel "Destination"
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setBounds(10, 113, 89, 14);
		frmTravelagency.getContentPane().add(lblDestination);
		
		// JScrollPane surrounding the JTable tableResults
		scrollPane = new JScrollPane();
		scrollPane.setBounds(111, 11, 506, 239);
		frmTravelagency.getContentPane().add(scrollPane);
		
		// JTable tableResults for displaying search results  
		tableResults = new JTable();
		tableResults.addMouseListener(new MouseAdapter() {
			@Override
			// Adding Mouse-click event for row-selection
				public void mouseClicked(MouseEvent e) {
				int row = tableResults.getSelectedRow();
				int column = tableResults.getSelectedColumn();
				
				// If the mouse-click occurs on the first-column of results, class SecondWindow is invoked, 
				// the current row and the entire tableResults are passed on as inputs for the constructor 	
					if (column == 0) {
						new SecondWindow(row, tableResults);
					}

			}
		});
		tableResults.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Address", "Contact", "Customer ID", "Name", "Surname"
			}
		));
		

		scrollPane.setViewportView(tableResults);
		
	

		
	}
	}