package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
/**
 * Class used for Managing Order
 * funtions include:(Create Order,update Order,Delete Order,Edit Order)
 * double click to retrieve
 * @author ignas
 *
 */
public class OrderForm extends JFrame {
	//obects initialization
	public UtilDateModel model,editModel;
	private static ResultSetTableModel tableModel;
	public Properties p;
	public JDatePanelImpl datePanel,editDatePanel;
	public JDatePickerImpl datePicker,editDatePicker;
	ButtonListener listener = new ButtonListener();
	Insert database = new Insert();
	DataCheck datacheck = new DataCheck();
	TextListner textListener = new TextListner();


	//components initialization
	private static JTable resultTable;
	private JLabel idLabel;
	private JTextField totalTextF,idTextF;
	private JButton deleteButton,updateButton,addOrderBtn,viewButton;
	
	//variables initialization
	private String totalPrice,orderDate,customerID,sql = "SELECT * FROM project.order";
/**
 * @param p3 -JPanel passed from main
 */
	public OrderForm(JPanel p3) {

		// main panel,layout
		JPanel frame = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(750, 300));

		// left side panel,layout
		JPanel detailsLayout = new JPanel(new GridLayout(6, 2, 1, 10));
		detailsLayout.setPreferredSize(new Dimension(230, 300));

		// bottom layout
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(0, 50));
		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 50));
		topPanel.setLayout(new BorderLayout(0, 0));

		// set up table
		try {
			tableModel = new ResultSetTableModel(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		resultTable = new JTable(tableModel);
		resultTable.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(resultTable);
		tableLayout.setPreferredSize(new Dimension(535, 200));

		// set date picker fore new entry's
		model = new UtilDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		model.setSelected(true);

		// create other components
		idLabel = new JLabel("            Customer ID:");
		idTextF = new JTextField();
		idTextF.addKeyListener(textListener);

		JLabel newDateLabel = new JLabel("      New Order Date:");
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));
		
		deleteButton = new JButton("Delete Selected Record");
		deleteButton.addActionListener(listener);
		addOrderBtn = new JButton("Create Order");
		addOrderBtn.addActionListener(listener);
		
		JLabel totalpriceLabel = new JLabel("       Edit Total Price:");
		JLabel editDateLabel = new JLabel("       Edit Order Date:");
		updateButton = new JButton("Update");
		
		JLabel headingLabel = new JLabel("Manage orders");
		headingLabel.setFont(new Font("Arial", Font.BOLD, 14));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

		totalTextF = new JTextField(10);
		totalTextF.addKeyListener(textListener);
		totalTextF.setEnabled(false);
		viewButton = new JButton("View order");
		viewButton.addActionListener(listener);

		// set date picker
		editModel = new UtilDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		editDatePanel = new JDatePanelImpl(editModel, p);
		editDatePicker = new JDatePickerImpl(editDatePanel, new DateLabelFormatter());
		editDatePicker.getComponent(1).setEnabled(false);
		model.setSelected(true);
		JLabel spaceLabel = new JLabel("");

		// add components to there panels
		detailsLayout.add(idLabel);
		detailsLayout.add(idTextF);
		detailsLayout.add(newDateLabel);
		detailsLayout.add(datePicker);

		bottomPanel.add(deleteButton);
		detailsLayout.add(spaceLabel);
		detailsLayout.add(addOrderBtn);

		detailsLayout.add(totalpriceLabel);
		detailsLayout.add(totalTextF);
		detailsLayout.add(editDateLabel);
		detailsLayout.add(editDatePicker);
		detailsLayout.add(viewButton);
		detailsLayout.add(updateButton);
		updateButton.addActionListener(listener);
		updateButton.setEnabled(false);
		
		topPanel.add(headingLabel, BorderLayout.CENTER);
		topPanel.add(label, BorderLayout.WEST);

		// add panels to frame
		frame.add(detailsLayout, BorderLayout.WEST);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.add(tableLayout, BorderLayout.CENTER);
		
		p3.add(frame);

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
						totalTextF.setText(updateModel.getValueAt(row, 1).toString());
						editDatePicker.getComponent(1).setEnabled(true);
						totalTextF.setEnabled(true);
						updateButton.setEnabled(true);
						java.sql.Date sqlDate = (java.sql.Date) updateModel.getValueAt(row, 2);// get's selected row's
																								// date
						editModel.setValue(sqlDate);// set value of a text box for a date selected
					}
				}
			}
		});

	}// end of contructor
	class TextListner extends KeyAdapter implements KeyListener {

		// allows only letter's (allows backspace (8) & Shift/CapsLock(65535))
		public void keyPressed(KeyEvent e) {
			boolean specialkeys = false, onlyNumbers = false;
			if ((e.getSource() == idTextF) || (e.getSource() == totalTextF)) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only digits allowed", "Error", JOptionPane.ERROR_MESSAGE);
					idTextF.setText("");
					totalTextF.setText("");
				}
			}
			
			
		}
	}

	/**
	 * this method updates Jtable
	 * and update's Product,Query JTables
	 */
	// update order table after new order
	public static void setAfterOrder() {
		try {
			tableModel.update();
			resultTable.setModel(tableModel);
			AddProduct.setAfterOrder();
			DisplayQueryResults.update();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

//button class
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String buttonLabel = e.getActionCommand();//get's label from button pressed
			boolean exist = false;
//create Order button pressed
			if (buttonLabel.equals("Create Order")) {
				customerID = idTextF.getText();
				if (customerID.equals("")) {
					JOptionPane.showMessageDialog(null, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					exist = database.check(customerID);
					
					if (exist == false) {
						JOptionPane.showMessageDialog(null, "Customer Does not exist or date field is empty", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// convert util.date to Sql.date
						Date selectedDate = (Date) datePicker.getModel().getValue();
						java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

						database.InsertOrder(0.0, sqlDate, customerID);
						
						int orderID = database.getOrderid(customerID);
						

						JFrame Orderform = new currentOrderForm("Create an order", customerID, orderID);
						Orderform.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						Orderform.setSize(850, 400);
						Orderform.setVisible(true);
						Orderform.setLocation(400, 200);

					}

				}

// update button pressed
			} else if (buttonLabel.equals("Update")) {

				int row = resultTable.getSelectedRow();
				if (row != -1) {
					String selection = resultTable.getModel().getValueAt(row, 0).toString();
					totalPrice = totalTextF.getText();
					// convert util.date to Sql.date
					Date selectedDate = (Date) editDatePicker.getModel().getValue();
					java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

					if (totalPrice.equals("")) {
						JOptionPane.showMessageDialog(null, "Some fields left empty", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {//update's order table and clear other fields
						database.UpdateOrder(totalPrice, sqlDate, selection);
						totalTextF.setText("");
						updateButton.setEnabled(false);
						editDatePicker.getComponent(1).setEnabled(false);
						totalTextF.setEnabled(false);
						DisplayQueryResults.update();

						try {//update jtable
							tableModel.update();
							resultTable.setModel(tableModel);

							JOptionPane.showMessageDialog(null, selection + " Order Successfully Updated", "Alert",
									JOptionPane.WARNING_MESSAGE);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
//Delete button pressed
			} else if (buttonLabel.equals("Delete Selected Record")) {
				int row = resultTable.getSelectedRow();//gets selected row
				if (row != -1) {
					String cell = resultTable.getModel().getValueAt(row, 0).toString();
					int answer = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete Order" + cell + " record", "Alert",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {//send to database where its excuted
						database.Remove(cell);
						totalTextF.setText("");
						//update's other form tables
						invoice.setAfterOrder();
						DisplayQueryResults.update();
						try {								//update's JTable
							tableModel.update();
							resultTable.setModel(tableModel);
							invoice.setAfterOrder();


						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
//View order button pressed
			}else if (buttonLabel.equals("View order")) {
				
				int row = resultTable.getSelectedRow();//gets value row selected
				if (row != -1) {//gets row id, creat's viewOrder object Form and pass in to it
					String orderID = resultTable.getModel().getValueAt(row, 0).toString();
				JFrame ViewOrder = new ViewOrder("View order", orderID);
				ViewOrder.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				ViewOrder.setSize(500, 200);
				ViewOrder.setVisible(true);
				ViewOrder.setLocation(550, 450);
				}
				else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}

		}

	}// end of button listner

	//format class for date picker
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
}// end of the class
