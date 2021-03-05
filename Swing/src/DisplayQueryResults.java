package src;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event. ActionListener ; 
import java.awt.event.ActionEvent; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java. sql .SQLException; 
import java. util .regex.PatternSyntaxException;
import javax.swing.JFrame; 
import javax.swing.JTextArea; 
import javax.swing.JScrollPane; 
import javax.swing.ScrollPaneConstants; 
import javax.swing.JTable; 
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel; 
import javax.swing.JTextField; 
import javax.swing.RowFilter; 
import javax.swing.table .TableRowSorter;

import javax.swing.table .TableModel;
import javax.swing.SwingConstants;
/**
 * this class used to view order selected
 * funtions include:(Query database's)
 * @author Diarmuid Brennan's
 *
 */
public class DisplayQueryResults extends JFrame {
	String DEFAULT_QUERY = "SELECT * FROM products"; 
	private static ResultSetTableModel tableModel; 
	private static JTable resultTable;
	private JTextArea queryArea;
	private String tables[] 
	        = { "products", "customer", "project.order", "invoice", "orderdetails"};
	private JComboBox jtfTables  = new JComboBox ();
	private JLabel heading = new JLabel() ;
	/**S
	 * @param p5 - JPanel passdown from the main
	 */
	public DisplayQueryResults(JPanel p5){
			
		//outer panel
			JPanel screen = new JPanel(new BorderLayout()); 
			
			//heading panel
			JPanel headinglayout = new JPanel();
			headinglayout.setPreferredSize(new Dimension(20, 50));
			
			//add label to heading panel
			heading = new JLabel("Query Database Tables"); 
			heading.setHorizontalAlignment(SwingConstants.CENTER);
		    heading.setFont(new Font("Arial", Font.BOLD, 14));
			headinglayout.setLayout(new BorderLayout(0, 0));
			
			//add icon
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));
			headinglayout.add(label, BorderLayout.WEST);
		       
		    //inner frame   
			JPanel frame = new JPanel(new BorderLayout());
			frame.setPreferredSize(new Dimension(750, 300));
			
			//sql query text area
			queryArea = new JTextArea( DEFAULT_QUERY, 3, 100 ); 
			queryArea.setWrapStyleWord( true ); 
			queryArea.setLineWrap( true ); 
			JScrollPane scrollPane = new JScrollPane( queryArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ); 
			JButton submitButton = new JButton( "Submit Query" );
			
			//add components to north label
			Box boxNorth = Box.createHorizontalBox();
			jtfTables = new JComboBox(tables);
			boxNorth.add( jtfTables );
			boxNorth.add( scrollPane ); 
			boxNorth.add( submitButton ); 
			
			//create table from sql query
			try {
				tableModel = new ResultSetTableModel(DEFAULT_QUERY);
			} catch ( SQLException sqlException ){ 
				JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE ); 
				
				}
			resultTable = new JTable( tableModel );
			TableRowSorter< TableModel > sorter =new TableRowSorter< TableModel >( tableModel ); 
			resultTable .setRowSorter( sorter ); 
			JScrollPane tableLayout = new JScrollPane(resultTable);
			tableLayout.setPreferredSize(new Dimension(535, 400));
			
			//create a south label for buttons
			Box boxSouth = Box.createHorizontalBox(); 
			//add buttons
			JButton selectAll = new JButton( "Select *" );
			JButton select = new JButton( "Select" );
			JButton innerJoin = new JButton( "Inner Join" );
			JButton on = new JButton( "On" );
			JButton where = new JButton( "Where" );
			boxSouth.add( selectAll ); 
			boxSouth.add( select ); 
			boxSouth.add( innerJoin );
			boxSouth.add( on ); 
			boxSouth.add( where );
			
			//add action listeners
			QueryHandler handleQueries = new QueryHandler() ;
			submitButton.addActionListener(handleQueries);
			selectAll.addActionListener(handleQueries);
			select.addActionListener(handleQueries);
			innerJoin.addActionListener(handleQueries);
			on.addActionListener(handleQueries);
			where.addActionListener(handleQueries);
			
			
			//add panels to inner frame
			frame.add(boxNorth, BorderLayout.NORTH);
			frame.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			frame.add( boxSouth, BorderLayout.SOUTH);
			headinglayout.add(heading, BorderLayout.CENTER);
			//add inner frame and heading to outer panel
			screen.add(headinglayout, BorderLayout.NORTH) ;
			screen.add(frame, BorderLayout.CENTER) ;
			
			p5.add(screen);
			
	}
	/**
	 * this method used for updating table displayed
	 */
	public static void update() {
			try {
				tableModel.update();
				resultTable.setModel(tableModel);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		

	}
			
	class QueryHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String tableChoice = (String) jtfTables.getSelectedItem();
			//submit query from text area 
			if(event.getActionCommand().equals("Submit Query")){
				try{//return the table of the query
					tableModel.setQuery( queryArea.getText() );
					} 
				catch ( SQLException sqlException ) { 
					JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE ); 
				}
			}
			//set query to select all from chosen table
			else if (event.getActionCommand().equals("Select *")) {
				try{
					DEFAULT_QUERY = "SELECT * FROM " + tableChoice ;
					queryArea.setText(DEFAULT_QUERY); 
					} 
				catch( Exception exception ) {
					exception.printStackTrace();
					}		
			}
			//set query to select all from chosen table showing column headings
			else if (event.getActionCommand().equals("Select")) {
				try{
					if(tableChoice.equals("products"))
					DEFAULT_QUERY = "SELECT ProductID, Name, Type, Description, Price, Quantity FROM " + tableChoice ;
					else if(tableChoice.equals("customer"))
					DEFAULT_QUERY = "SELECT CustomerID, firstName, lastName, gender, phNumber, address, postcode, ageGroup FROM " + tableChoice ;
					else if(tableChoice.equals("project.order"))
					DEFAULT_QUERY = "SELECT OrderID, TotalPrice, OrderDate, CustomerID FROM " + tableChoice ;
					else if(tableChoice.equals("orderdetails"))
					DEFAULT_QUERY = "SELECT OrderedQuantity, ProductID, OrderID FROM " + tableChoice ;
					else if(tableChoice.equals("invoice"))
					DEFAULT_QUERY = "SELECT InvoiceID, InvoiceDate, Status, Order_ID FROM " + tableChoice ;
					
					
					queryArea.setText(DEFAULT_QUERY); 
					} 
				catch( Exception exception ) {
					exception.printStackTrace();
					}
			}
			//add text inner join to the query
			else if (event.getActionCommand().equals("Inner Join")) {
				try{
					DEFAULT_QUERY = queryArea.getText() + " INNER JOIN" ;
					queryArea.setText( DEFAULT_QUERY); 
					} 
				catch( Exception exception ) {
					exception.printStackTrace();
					}
			}
			//add text on to the query
			else if (event.getActionCommand().equals("On")) {
				try{
					DEFAULT_QUERY = queryArea.getText() + " ON" ;
					queryArea.setText( DEFAULT_QUERY); 
					} 
				catch( Exception exception ) {
					exception.printStackTrace();
					}
			}
			//add text where to the query
			else if (event.getActionCommand().equals("Where")) {
				try{
					DEFAULT_QUERY = queryArea.getText() + " WHERE" ;
					queryArea.setText( DEFAULT_QUERY); 
					} 
				catch( Exception exception ) {
					exception.printStackTrace();
					}
			}
		}
	}
	}

