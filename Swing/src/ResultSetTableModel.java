package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 * Class used for puting data into JTables and updating tables after new information
 * @author Jason
 */
public class ResultSetTableModel extends AbstractTableModel {

	//private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet ;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	private boolean connectedToDatabase = false;
	static final String DATABASE_URL = "jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false";
	String DEFAULT_QUERY;
	
	/**
	 * @param sql String SQL Statements String 
	 * @throws SQLException  dont show sql error
	 */
	public ResultSetTableModel(String sql)throws SQLException {
		DEFAULT_QUERY = sql;
		connection=DriverManager.getConnection(DATABASE_URL,"root","root");//root,password(root)
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
		connectedToDatabase = true;
		update();
		
	}
	public Class getColumnClass( int column ) throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ( "Not Connected to Database");
		try{
		String className = metaData.getColumnClassName( column + 1 );
		// return Class object that represents className
		return Class . forName( className );
		}
		catch ( Exception exception ) {
		exception.printStackTrace();
		}
		return Object.class ; // if problems occur above, assume type Object
		}
	
	public int getColumnCount() throws IllegalStateException{
		if(!connectedToDatabase)throw new IllegalStateException("Not Connected to Database");
		try {
			return metaData.getColumnCount();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return 0; // if problems occur above, return 0 for number of columns
	}
	public String getColumnName( int column ) throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ( "Not Connected to Database");
		try {
		return metaData.getColumnName( column + 1 );
		}
		catch ( SQLException sqlException ){
		sqlException . printStackTrace () ;
		}
		return ""; // if problems, return empty string for column name
	}
	public int getRowCount() throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ("Not Connected to Database" );
		return numberOfRows;
		}
	
	@Override
	public Object getValueAt(int row, int column) {
		if ( !connectedToDatabase ) throw new IllegalStateException ( "Not Connected to Database" );
				try {
				resultSet . absolute ( row + 1 );
				return resultSet .getObject( column + 1 );
				}
				catch ( SQLException sqlException ){
				sqlException.printStackTrace();
				}
				return ""; // if problems, return empty string object
	}
	public void update() throws SQLException, IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ( "Not Connected to Database" );
			resultSet = statement.executeQuery(DEFAULT_QUERY);
			metaData = resultSet.getMetaData();
			resultSet.last() ;
			numberOfRows = resultSet.getRow();
			fireTableStructureChanged();
		}
	/**
	 * 
	 * @param query String  SQL Statements String
	 * @throws SQLException  dont show SQL error
	 * @throws IllegalStateException  dont show error
	 */
	public void setQuery( String query ) throws SQLException, IllegalStateException{ 
		if ( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" ); 
		resultSet = statement.executeQuery( query ); 
		metaData = resultSet.getMetaData(); 
		resultSet . last (); 
		numberOfRows = resultSet.getRow(); 
		fireTableStructureChanged();
		}
	
	
	public void disconnectFromDatabase(){
		if (connectedToDatabase){
		try{
		resultSet.close() ;
		statement.close() ;
		connection.close() ;
		}
		catch (SQLException sqlException) {
		sqlException.printStackTrace() ;
		}
		finally {
		connectedToDatabase = false;
		}
	}	
}//end disconnect
	

}//end class

