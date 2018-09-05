package travel;

	import javax.swing.JComboBox;
	import javax.swing.JTextField;

	// Class Queries containing Constructor for the search method and methods for adding selected info. from ComboBoxes 
	public class Queries {								
		private String strCustomer;						
		private String strCountry;
		private String strCity;
		private String sqlquery = "";					// Initializing SQLquery 
		private JComboBox customer;
		private JComboBox country;
		private JComboBox city;
		
		
		public Queries(JComboBox customer, JComboBox country, JComboBox city){
			
			this.customer = customer;
			this.country = country;
			this.city = city;
			
			
		}
		
		// Method search, returning a String and taking inputs returned from Method specifics below
		public String search(){
			
			strCustomer = specifics(customer);	 
			strCountry = specifics(country);
			strCity = specifics(city);
			
			if(strCustomer !=null){
				
				sqlquery = "SELECT * FROM Customers WHERE Surname = '";
				sqlquery += strCustomer;
				sqlquery += "'";
				
			}
			if (strCountry !=null){
				
				if(sqlquery.isEmpty()){
					
					sqlquery = "SELECT * FROM Trips WHERE Country = '";
					sqlquery += strCountry;
					sqlquery += "' ";
				}
				else{
					
					sqlquery = "Select Surname, Name, Trips.'TripID', Country, City, Start, Trips.'Return' From Customers JOIN Bookings JOIN Trips ON Customers.'CustomerID'=Bookings.'Customer ID' Where Surname ='";
					sqlquery += strCustomer;
					sqlquery += "' ";
					sqlquery += "AND Country ='";
					sqlquery += strCountry;
					sqlquery += "' ";
					sqlquery += "And Bookings.'Trip ID' = Trips.'TripID' ";
				}
			}
			
			if (strCity !=null){
				
				if(sqlquery.isEmpty()){
					
					sqlquery = "SELECT * FROM Trips WHERE City = '";
					sqlquery += strCity;
					sqlquery += "' ";
				}
				else{
					
					sqlquery = "Select Surname, Name,Trips.'TripID', Country, City, Start, Trips.'Return' From Customers JOIN Bookings JOIN Trips ON Customers.'CustomerID' = Bookings.'Customer ID' Where Surname ='";
					sqlquery += strCustomer;
					sqlquery += "' ";
					sqlquery += "AND Country ='";
					sqlquery += strCountry;
					sqlquery += "' ";
					sqlquery += "AND City ='";
					sqlquery += strCity;
					sqlquery += "' ";
					sqlquery += "And Bookings.'Trip ID'=Trips.'TripID' ";
					
				}
			}
			
		
			
			return sqlquery;
		}
		
		// Method specifics, retrieves selected info. from the Combo-boxes and returns it as a String
		public String specifics(JComboBox selected){
			
			if (!selected.getSelectedItem().equals("Customer") && !selected.getSelectedItem().equals("Country") && !selected.getSelectedItem().equals("City"))
			{
				return selected.getSelectedItem().toString();
			}
			else{
				return null;
			}
		}
		

	}




