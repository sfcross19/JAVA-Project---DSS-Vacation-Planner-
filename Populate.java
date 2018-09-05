package travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import travel.DBconnection;

public class Populate {
	ArrayList<Object> data = new ArrayList<Object>(); // Create an ArrayList of Objects and call it
													  // "data"

	// Adds Objects to the ArrayList Data from the database and adds it to the
	// combo-box customer
	public ArrayList<Object> customer() {
		Connection conn2;
		conn2 = DBconnection.dbconnect();
		data.add("Customer");

		try {
			String query1 = "Select DISTINCT Surname FROM Customers";
			PreparedStatement pst = conn2.prepareStatement(query1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				data.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;

	}

	// Adds values to combo-box Country
	public ArrayList<Object> country() {
		Connection conn2;
		conn2 = DBconnection.dbconnect();
		data.add("Country");

		try {
			String query1 = "Select DISTINCT Country FROM Trips";
			PreparedStatement pst = conn2.prepareStatement(query1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				data.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;

	}

	// Adds values to combo-box City
	public ArrayList<Object> city() {
		Connection conn2;
		conn2 = DBconnection.dbconnect();
		data.add("City");

		try {
			String query1 = "Select DISTINCT City from Trips ";
			PreparedStatement pst = conn2.prepareStatement(query1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				data.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;
	}

	public void emptyList() { // Empty ArrayList Data
		data.clear();
	}

}
