package travel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	static Connection conn;

	public static Connection dbconnect() {
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:X:/TravelAgency");

			System.out.println("Loading driver...");
			// here in the constructor we load the driver
			Class.forName("org.sqlite.JDBC");
			System.out.println("Driver loaded...");
			return conn;

		} catch (ClassNotFoundException ex) {
			// handle errors for Class.forName here
			System.out.println("\n" + "Could not load driver..." + "\n");
			System.out.println(ex);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}
}
