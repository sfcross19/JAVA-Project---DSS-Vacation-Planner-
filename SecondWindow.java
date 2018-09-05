package travel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

// Class SecondWindow, invoked on cell-selection, containing methods to display relevant information about a specific customer or trip
public class SecondWindow {

	JFrame frame2;
	private JTextArea info;
	private String str = "CustomerID";
	private String str2 = "TripID";
	private String Input;
	private JTable table_Bookings;
	private JScrollPane scrollPane;

	Connection conn3; // Declaring Connection conn3

	// Creating second window on event of mouse-clicking the first row
	public SecondWindow(int row, JTable table) {

		// Adapting the frame to the target environment
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		conn3 = DBconnection.dbconnect();

		JFrame frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame2.setTitle((String) table.getModel().getValueAt(row, 1));
		frame2.setBounds(100, 100, 600, 600);

		JTextArea info = new JTextArea();
		info.setFont(new Font("Arial Unicode MS", Font.PLAIN, 24));
		info.setVisible(true);
		info.setEditable(false);

		// Getting relevant information from the table and displaying it on the JTextArea info
		int columnIndex = 0;
		int columnEnd = table.getColumnCount();
		
		do {
			info.append("  " + table.getColumnName(columnIndex) + ": " + table.getModel().getValueAt(row, columnIndex)
					+ "\n ");
			columnIndex++;
		} while (columnIndex < columnEnd);

		frame2.getContentPane().setLayout(null);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(info); // Adding a JScrollpane
		scrollPane.setBounds(0, 0, 600, 440);
		frame2.getContentPane().add(scrollPane);

		// Adding JButton btnBookings with an ActionLister that performs a query
		// based on the selected result (Customer or Trip) and updates the table_Bookings
		JButton btnBookings = new JButton("Bookings");
		btnBookings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					String query = ""; // Initializing query

					// Checking if the information requested is for a Customer or a Trip,
					// by comparing the column selected (from tableResults earlier) to String str and String str2

					// If the information is requested for a Customer, Customer ID is used
					if (str.contentEquals(table.getColumnName(0))) {

						// Adding the value at (selected row, column 0) to String Input, added later to query
						Input = table.getValueAt(row, 0).toString();

						query = "Select Surname, Name, Trips.'TripID', Country, City, Start, Return, Trips.'Price' From Customers JOIN Bookings JOIN Trips ON Customers.'CustomerID' = Bookings.'Customer ID' Where CustomerID = '";
						query += Input;
						query += "'";
						query += " And Bookings.'Trip ID' = Trips.'TripID' ";

						// If information is requested for a trip, Trip ID is used, thereby altering the query and giving a different result set
					} else if (str2.contentEquals(table.getColumnName(0))) {

						// Adding the value at (selected row, column 0) to String Input, added later to query
						Input = table.getValueAt(row, 0).toString();

						query = "Select CustomerID, Surname, Name, Contact From Customers JOIN Bookings JOIN Trips On Customers.CustomerID = Bookings.'Customer ID' Where TripID = '";
						query += Input;
						query += "'";
						query += "And Bookings.'Trip ID' = Trips.'TripID' ";

					}
					PreparedStatement pst = conn3.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table_Bookings.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnBookings.setBounds(10, 527, 89, 23);
		frame2.getContentPane().add(btnBookings);

		// JScrollPane surrounding JTable table_Bookings
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(106, 450, 468, 100);
		frame2.getContentPane().add(scrollPane_2);

		// JTable showing result set with relevant information for a Customer or
		// a Trip
		table_Bookings = new JTable();
		scrollPane_2.setViewportView(table_Bookings);
		table_Bookings.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Customer ID", "Surname",
				"Name", "Trip ID", "Country", "City", "Start", "Return", "Price" }));

		// Visibility parameters for btnBookings and scrollPane_2
		if (str.contentEquals(table.getColumnName(0))) {

			btnBookings.setVisible(true);
			scrollPane_2.setVisible(true);

		} else if (str2.contentEquals(table.getColumnName(0))) {

			btnBookings.setVisible(true);
			scrollPane_2.setVisible(true);

		} else {
			btnBookings.setVisible(false);
			scrollPane_2.setVisible(false);
		}
	}

}
