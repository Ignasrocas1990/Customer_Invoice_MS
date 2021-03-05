package src;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Class used for getting info from database to Java
 * @author Diarmuid
 * @author Conor 
 * @author Igns
 */
public class Insert {
	final String DATABASE_URL = "jdbc:mysql://localhost/project";
	//final String DATABASE_URL = "jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false";
	Connection connection = null;
	Statement statement = null;

// constructor
	public Insert() {
	}

//------------------------Methods using Customer table -------------------------
// insert into Customer table method
	/**
	 * @param firstname customer name
	 * @param lastname customer last name
	 * @param gender customer gender
	 * @param number customer phone number
	 * @param Address customer Address
	 * @param postCode customer post code
	 * @param ageGroup customer Age Group
	 */
	public void InsertTo(String firstname, String lastname, String gender, String number, String Address,
			String postCode, String ageGroup) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)

			PreparedStatement pstat = connection.prepareStatement(
					"INSERT INTO customer(firstName, lastName,gender,phNumber,address,postcode,ageGroup) VALUES(?,?,?,?,?,?,?)");
			pstat.setString(1, firstname);
			pstat.setString(2, lastname);
			pstat.setString(3, gender);
			pstat.setString(4, number);
			pstat.setString(5, Address);
			pstat.setString(6, postCode);
			pstat.setString(7, ageGroup);
			pstat.executeUpdate();

			
			
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}
/**
 * 
 * @param cell CustomerID from selected row in JTable
 */
// Delete customer method
	public void Delete(String cell) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM customer WHERE CustomerID='" + cell + "'");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}

	/**
	 * @param firstname customer name
	 * @param lastname customer last name
	 * @param gender customer gender
	 * @param number customer phone number
	 * @param Address customer Address
	 * @param postCode customer post code
	 * @param ageGroup customer Age Group
	 * @param id CustomerID from selected row in JTable
	 */
	// update Customer table method
	public void Update(String firstname, String lastname, String gender, String number, String Address, String postCode,
			String ageGroup, String id) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE customer SET firstName=?, lastName=?, gender=?, phNumber=?, address=?, postcode=?, ageGroup=? WHERE CustomerID="
					+ id;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setString(1, firstname);
			pstat.setString(2, lastname);
			pstat.setString(3, gender);
			pstat.setString(4, number);
			pstat.setString(5, Address);
			pstat.setString(6, postCode);
			pstat.setString(7, ageGroup);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}
/**
 * @param customerID value from selected row in JTable
 * @return true if its value found 
 */
// check if customer exist
	public boolean check(String customerID) {
		String sqlCheck = "SELECT * FROM customer WHERE CustomerID = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, customerID);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param firstName customer first name selected row in JTable
	 * @param lastName customer last name selected row in JTable
	 * @return true if value's found
	 */
	//not allow duplicate's of customer
	public boolean duplicate(String firstName, String lastName ) {
		String sqlCheck = "SELECT * FROM customer WHERE firstName = ? AND lastName = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();// return true if it exist

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//------------------------methods using Order table ------------------------
	/**
	 * 
	 * @param totalPrice value entered from user
	 * @param selectedDate Date picked by the user
	 * @param customerID Value entered by the user
	 */
// insert to order method
	public void InsertOrder(Double totalPrice, Date selectedDate, String customerID) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			PreparedStatement pstat = connection
					.prepareStatement("INSERT INTO project.order(TotalPrice, OrderDate, CustomerID) VALUES(?,?,?)");
			pstat.setDouble(1, totalPrice);
			pstat.setDate(2, selectedDate);
			pstat.setString(3, customerID);
			pstat.executeUpdate();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}
/**
 * 
 * @param cell OrderID String selected by the user from JTable
 */
//Remove Order method
	public void Remove(String cell) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM project.order WHERE OrderID='" + cell + "'");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}
/**
 * 
 * @param customerID String entered by the user
 * @return Int largest orderID
 */
// gets empty orderID so u can add items to it
	public int getOrderid(String customerID) {
		String sqlCheck = "SELECT MAX(OrderID) FROM project.order WHERE CustomerID = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, customerID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
/**
 * 
 * @param totalPrice String entered by the user
 * @param orderDate type 'Date' selected by the user from date picker
 * @param id OrderId String selected from the JTable's row
 */
//update's order table
	public void UpdateOrder(String totalPrice, Date orderDate, String id) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE project.order SET TotalPrice=?, OrderDate=? WHERE OrderID=" + id;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setDouble(1, Double.parseDouble(totalPrice));
			pstat.setDate(2, orderDate);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}
	/**
	 * 
	 * @param totalPrice double Price entered bu the user
	 * @param id orderID integer selected the user from JTable
	 */

//update order total price
	public void UpdateOrderPrice(double totalPrice, int id) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE project.order SET TotalPrice=? WHERE OrderID=" + id;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setDouble(1, totalPrice);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}
/**
 * 
 * @param orderID integer selected by the user from JTable's row
 */
//Delete's empty order from order table (and it auto cascade currentOrder table)
	public void clearCurrentOrder(int orderID) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)

			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM project.order WHERE OrderID='" + orderID + "'");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}

//--------------------------------- Methods using orderdetails Table --------------------------
// Delete item from current order method
	/**
	 * 
	 * @param productID value selected by the user from JTable's row
	 * @param orderID value selected by the user from JTable's row
	 */
	public void DeleteItem(String productID, String orderID) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM project.orderdetails WHERE OrderID='" + orderID + "' AND ProductID='"
					+ productID + "'");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}

//update current Order Quantity
	/**
	 * 
	 * @param quantity integer value entered by the user
	 * @param productid String selected by the user from JTable's row
	 * @param orderid String selected by the user from JTable's row
	 */
	public void UpdateCurrentOrder(int quantity, String productid, String orderid) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE project.orderdetails SET OrderedQuantity=? WHERE OrderID=" + orderid
					+ " AND ProductID=" + productid;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setInt(1, quantity);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}
/**
 * 
 * @param quantity integer value entered by the user
 * @param productid String selected by the user from JTable's row
 * @param OrderID value selected by the user from JTable's row
 */
// method to add items to current order
	public void InCurrentOrder(int quantity, String productid, int OrderID) {

		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)

			PreparedStatement pstat = connection.prepareStatement(
					"INSERT INTO project.orderdetails(OrderedQuantity, ProductID,OrderID) VALUES(?,?,?)");
			pstat.setInt(1, quantity);
			pstat.setInt(2, Integer.parseInt(productid));
			pstat.setInt(3, OrderID);
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}

// ------------------------- Methods using products Table ---------------------------
// update Product Quantity
	/**
	 * 
	 * @param quantity - int value  update after adding new products to current order
	 * @param productid - String value update after adding new products to current order
	 */
	public void UpdateProductQuantity(int quantity, String productid) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE project.products SET Quantity=? WHERE ProductID=" + productid;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setInt(1, quantity);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	}
/**
 * 
 * @param productID String selected by the user from JTable's row
 * @return Quantity int from database
 */
	// gets product Quantity
	public int GetProductQuantity(String productID) {
		String sqlCheck = "SELECT Quantity FROM project.products WHERE ProductID = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, productID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
/**
 * @param name String entered by the user
 * @param type String selected by the user from radiovuttons
 * @param description String entered by the user
 * @param price double entered by the user
 * @param quantity int entered by the user
 */
	public void InsertProduct(String name, String type, String description, double price, int quantity) //was InsertTo 
	{
		try {
			connection = DriverManager.getConnection(DATABASE_URL,"root","root");//root,password(root)

			
			//insert Data into database
			//statement.executeUpdate("INSERT INTO Authors (FistName,LastName)"+"VALUES"+"("+firstnane+","+lastname+")");
			
			PreparedStatement pstat = connection.prepareStatement("INSERT INTO products(Name, Type, Description, Price ,Quantity) VALUES(?,?,?,?,?)");
			pstat.setString(1, name);
			pstat.setString(2, type);
			pstat.setString(3, description);
			pstat.setDouble(4, price);
			pstat.setInt(5, quantity);
			pstat.executeUpdate();
				
		}
		catch(SQLException sqlException ) 
		{
			sqlException.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch(SQLException exception) {
				exception.printStackTrace();
			}
			
		}
	}//end class
/**
 * 
 * @param cell ProductID String selected by the user from JTable's row
 */
	public void DeleteProduct(String cell) {//was Delete

	try {
		connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
		// establish connection to database
		statement = connection.createStatement();

		// Delete Data into database
		statement.executeUpdate("DELETE FROM products WHERE ProductID='" + cell + "'");

	} catch (SQLException sqlException) {
		sqlException.printStackTrace();
	} finally {
		try {
			statement.close();
			connection.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}
}
	/**
	 * @param name String entered by the user
	 * @param type String selected by the user from radiovuttons
	 * @param description String entered by the user
	 * @param price double entered by the user
	 * @param quantity int entered by the user
	 * @param id String selected by the user from JTable's row
	 */
	public void UpdateProduct(String name, String type, String description, double price, int quantity, String id) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			String sql = "UPDATE products SET Name=?, Type=?, Description=?, Price=?, Quantity=? WHERE ProductID="
					+ id;
			PreparedStatement pstat = connection.prepareStatement(sql);

			pstat.setString(1, name);
			pstat.setString(2, type);
			pstat.setString(3, description);
			pstat.setDouble(4, price);
			pstat.setInt(5, quantity);
			pstat.executeLargeUpdate();
		}

		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
	
	}
	/**
	 * 
	 * @param name String entered by the user to check dubplicates
	 * @return boolean true if name found
	 */
	public boolean duplicateProd(String name ) {
		String sqlCheck = "SELECT * FROM products WHERE name = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();// return true if it exist

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param date Date type selected by the user from DatePicker
	 * @param status String selected by the user
	 * @param orderID int selected by the user from JTable's row
	 */
	//insert data in invoice table
	public void InsertInvoice(Date date, String status, int orderID) //was InsertTo 
	{

		try {
			connection = DriverManager.getConnection(DATABASE_URL,"root","root");//root,password(root)

			PreparedStatement pstat = connection.prepareStatement("INSERT INTO invoice(InvoiceDate, Status, Order_ID) VALUES(?,?,?)");
			pstat.setDate(1, date);
			pstat.setString(2, status);
			pstat.setInt(3, orderID);
			pstat.executeUpdate();
				
		}
		catch(SQLException sqlException ) 
		{
			sqlException.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch(SQLException exception) {
				exception.printStackTrace();
			}
			
		}
	}//end class
/**
 * 
 * @param cell String InvoiceId selected from JTable row
 */
	//delete data from invoice table
	public void DeleteInvoice(String cell) {//was Delete1

	try {
		connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
		// establish connection to database
		statement = connection.createStatement();

		// Delete Data into database
		statement.executeUpdate("DELETE FROM invoice WHERE InvoiceID='" + cell + "'");

	} catch (SQLException sqlException) {
		sqlException.printStackTrace();
	} finally {
		try {
			statement.close();
			connection.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}
}
	/**
	 * 
	 * @param date type'date' selected by the user from DatePicker
	 * @param status String from user selected radio button
	 * @param id - String InvoiceId selected by the user from JTable row
	 */
	//update a row in the database table
	public void UpdateInvoice(Date date, String status, String id)
	{

		try {
			connection = DriverManager.getConnection(DATABASE_URL,"root","root");//root,password(root)
			String sql = "UPDATE invoice SET InvoiceDate=?, Status=? WHERE InvoiceID = "
					+ id;
			PreparedStatement pstat = connection.prepareStatement(sql);
			
			//insert Data into database
			pstat.setDate(1, date);
			pstat.setString(2, status);
			pstat.executeLargeUpdate();
				
		}
		catch(SQLException sqlException ) 
		{
			sqlException.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch(SQLException exception) {
				exception.printStackTrace();
			}
			
		}
	}//end class
	/**
	 * 
	 * @param orderID String selected from JTable row
	 * @return boolean if its found or not in database
	 */
	//check if orderid exists in the order table
	public boolean checkOrderID(String orderID) {
		String sqlCheck = "SELECT * FROM project.order WHERE OrderID = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, orderID);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param orderID String entered by user
	 * @return boolean if its Id alrady exist's or not
	 */
	//test to see if an invoice has already been created for the chosen order
	public boolean duplicateOrderID(String orderID) {
		String sqlCheck = "SELECT * FROM project.invoice WHERE Order_ID = ?";
		PreparedStatement preparedStatement;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");// root,password(root)
			preparedStatement = connection.prepareStatement(sqlCheck);
			preparedStatement.setString(1, orderID);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
