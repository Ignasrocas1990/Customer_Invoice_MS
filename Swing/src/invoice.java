package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
/**
 * Class used for Managing Invoices 
 * funtions include:(create,update,delete and view)Invoice's
 * 
 * @author Diarmuid Brennan
 * @author Conor
 */

public class invoice extends JFrame{
	private static ResultSetTableModel tableModel;
    ButtonHandler handler = new ButtonHandler() ;
	  public String sql = "SELECT InvoiceID, firstName, lastName, InvoiceDate, Status, TotalPrice, OrderID FROM (project.invoice"
			+ " INNER JOIN project.order ON invoice.Order_ID = order.OrderID INNER JOIN project.customer ON customer.CustomerID "
			+ "= project.order.CustomerID)" ;
	private JLabel orderID ; 
    private static JTable resultTable ;

	private JTextField jtforderID;
	private JRadioButton Paid;
    private JRadioButton Unpaid;
    private ButtonGroup gengp;
    private String getorderID;
	
    private JButton addBtn, deleteBtn, retrieveBtn, updateBtn ;
    
    
	public UtilDateModel model;
	public UtilDateModel editModel;
	public Properties p;
	public JDatePanelImpl datePanel;
	public JDatePickerImpl datePicker;
	public JDatePanelImpl editDatePanel;
	public JDatePickerImpl editDatePicker;
	
	Insert database = new Insert();

	/**
	 * 
	 * @param p4 -JPanel passed from main
	 */
	public invoice(JPanel p4) {
		//create frame
		JPanel frame = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(750, 350));
		
		//heading layout
		JPanel headingLayout = new JPanel();
		headingLayout.setPreferredSize(new Dimension(0, 50));
		headingLayout.setLayout(new BorderLayout(0, 0));
		// details panel,layout
		JPanel detailsLayout = new JPanel(new GridLayout(5, 2, 1, 5));
		detailsLayout.setPreferredSize(new Dimension(230, 300));
		// buttons panel
		JPanel buttonsLayout = new JPanel();
		buttonsLayout.setPreferredSize(new Dimension(750, 150));
		// call SQL table class
		try {
			tableModel = new ResultSetTableModel(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//table panel
		JPanel tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension(650, 80));
		
		
		// set up table
		resultTable = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		resultTable.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(resultTable);
		tableLayout.setPreferredSize(new Dimension(515, 300));
		
		
		JLabel heading = new JLabel("Manage Invoices");
		heading.setFont(new Font("Arial", Font.BOLD, 14));
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		
		//JLabel label = new JLabel();
		//label.setIcon(new ImageIcon("C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));
		
		// set date picker
		model = new UtilDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		model.setSelected(true);
        
		//orderID label and textfield
		orderID = new JLabel("Order ID:") ;
		jtforderID  = new JTextField() ;
		
		//label for date
		JLabel newDateLabel = new JLabel("New Invoice Date:");
		
		//label and radio buttons for status
		JLabel spaceLabel = new JLabel("");
		JLabel status = new JLabel("Status");
		Paid = new JRadioButton("Paid"); 
		Paid.setSelected(false); 
      
        Unpaid = new JRadioButton("Unpaid");  
        Unpaid.setSelected(true); 
        
        gengp = new ButtonGroup(); 
        gengp.add(Paid); 
        gengp.add(Unpaid);
       
		
		
		

        addBtn = new JButton("Create Invoice"); 
        addBtn.addActionListener(handler);
        updateBtn = new JButton("Update Invoice");
        updateBtn.addActionListener(handler);
        updateBtn.setEnabled(false);
        retrieveBtn = new JButton("View Order");
        retrieveBtn.addActionListener(handler);
		deleteBtn = new JButton("Delete Invoice");
		deleteBtn.addActionListener(handler);
  
		// add swing components to panes
		
		
		detailsLayout.add(orderID);
		detailsLayout.add(jtforderID);

		detailsLayout.add(newDateLabel);
		detailsLayout.add(datePicker);
		
		
		
		detailsLayout.add(status);
		detailsLayout.add(Paid);
		detailsLayout.add(spaceLabel);
		detailsLayout.add(Unpaid);
		
		
		headingLayout.add(heading);

		buttonsLayout.add(addBtn);
		buttonsLayout.add(updateBtn);
		buttonsLayout.add(deleteBtn);
		buttonsLayout.add(retrieveBtn);
		
		// add containers to the main page
		frame.add(headingLayout, BorderLayout.NORTH);
		frame.add(buttonsLayout, BorderLayout.SOUTH);
		frame.add(detailsLayout, BorderLayout.WEST);
		frame.add(tableLayout, BorderLayout.EAST);
		p4.add(frame);
		
		//double click on table (retrieve's info)
				resultTable.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent mouseEvent) {
						JTable table = (JTable) mouseEvent.getSource();
						Point point = mouseEvent.getPoint();
						int row = table.rowAtPoint(point);
						if (mouseEvent.getClickCount() == 2) {
							// your valueChanged overridden method
							if (row != -1) {
								TableModel updateModel = resultTable.getModel();
								jtforderID.setText(updateModel.getValueAt(row, 6).toString());
								java.sql.Date sqlDate = (java.sql.Date) updateModel.getValueAt(row, 3);// get's selected row's date	
								updateBtn.setEnabled(true);
								jtforderID.setEnabled(false);
								model.setValue(sqlDate);// set value of a text box for a date selected
								String type = (updateModel.getValueAt(row, 4).toString());
								if (type.equals("Paid")) {
									Paid.setSelected(true);
								}else {
									Unpaid.setSelected(true);
								}
							}
						}
					}
				});
        
	}
	/**
	 * this method updates Jtable
	 */
	// update order table after new order
	public static void setAfterOrder() {
		try {
			tableModel.update();
			resultTable.setModel(tableModel);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	
	//action listener for buttons
	class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String buttonLabel = event.getActionCommand();
			boolean exist = false;
			boolean duplicate = true;
			//if create invoice button is selected
			if(buttonLabel.equals("Create Invoice")){
				getorderID = jtforderID.getText() ;//get order id
				if (getorderID.equals("")) {
					JOptionPane.showMessageDialog(null, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					//test if order exists
					exist = database.checkOrderID(getorderID);
					//test if invoice was already created
					duplicate = database.duplicateOrderID(getorderID);
					if (exist == false) {
						JOptionPane.showMessageDialog(null, "Order Does not exist", "Error",
								JOptionPane.ERROR_MESSAGE);
						jtforderID.setText("");
					}
					else if (duplicate == true) {
						JOptionPane.showMessageDialog(null, "Invoice has already been created", "Error",
								JOptionPane.ERROR_MESSAGE);
						jtforderID.setText("");
					}
					else {
						int id = Integer.parseInt(jtforderID.getText()) ;
						Date selectedDate = (Date) datePicker.getModel().getValue();
						java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
						String type = " "; 
						  
			            // If condition to check if jRadioButton2 is selected. 
			            if (Paid.isSelected()) { 

			           	 type = "Paid"; 
			            } 

			            else if (Unpaid.isSelected()) { 

			           	 type = "Unpaid"; 
			            } 
			            //update database
			            database.InsertInvoice(sqlDate, type, id);
			            jtforderID.setText("");
			            Paid.setSelected(false);
			            Unpaid.setSelected(true);
			            try {
							tableModel.update();
							resultTable.setModel(tableModel);

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			//if delete button is selected
			else if (event.getActionCommand().equals("Delete Invoice")) {
				int row = resultTable.getSelectedRow();
				if (row != -1) {//if row is found
					String cell = resultTable.getModel().getValueAt(row, 0).toString();
					String nameAlert = resultTable.getModel().getValueAt(row, 1).toString();
					String nameAlert2 = resultTable.getModel().getValueAt(row, 2).toString();
				
					int answer = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete invoice record" + nameAlert + " " + nameAlert2, "Alert",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						database.DeleteInvoice(cell);
					}
				try {
					//update database
					tableModel.update();
					resultTable.setModel(tableModel);

					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
				else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
			//if view order button is selected
			else if (event.getActionCommand().equals("View Order")){
				int row = resultTable.getSelectedRow();
				if (row != -1) {
					String orderID = resultTable.getModel().getValueAt(row, 6).toString();

					JFrame DisplayOrder = new DisplayOrder(orderID);
					DisplayOrder.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					DisplayOrder.setSize(600, 150);
					DisplayOrder.setVisible(true);
					DisplayOrder.setLocation(540, 450);
			}
				else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
		}
			//if update button is selected
			else if (event.getActionCommand().equals("Update Invoice")){
			int row = resultTable.getSelectedRow();
			if (row != -1) {//if row is found
				String selection = resultTable.getModel().getValueAt(row, 0).toString();
				getorderID = jtforderID.getText() ;
				if (getorderID.equals("")) {
					JOptionPane.showMessageDialog(null, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					int id = Integer.parseInt(jtforderID.getText()) ;
					Date selectedDate = (Date) datePicker.getModel().getValue();
					java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
					String type = " "; 
					  
		            // If condition to check if which radio button was selected. 
		            if (Paid.isSelected()) { 

		           	 type = "Paid"; 
		            } 

		            else if (Unpaid.isSelected()) { 

		           	 type = "Unpaid"; 
		            }
		           //update the database with the updated data and set the fields to empty
		            database.UpdateInvoice(sqlDate, type, selection);
		            jtforderID.setText("");
		            Paid.setSelected(false);
		            Unpaid.setSelected(true);
		            //disable update button and enable order id textfield
		            updateBtn.setEnabled(false);
		            jtforderID.setEnabled(true);
		            
		            try {//update the invoice table as well as update the table in the display queries form
						tableModel.update();
						resultTable.setModel(tableModel);
						DisplayQueryResults.update();
						
						
						JOptionPane.showMessageDialog(null, "Record Successfully Updated", "Alert",
								JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e) {
						e.printStackTrace();
					}
		}}
			else {
				JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
	}
	
	}
	}
	//date formatter
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}// end of date formatter class

}