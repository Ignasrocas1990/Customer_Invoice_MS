
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

/**
 * Class used for Compile an Order funtions include:(Add to order,Delete from
 * ,Update an order)
 * 
 * @author ignas rocas
 */
public class currentOrderForm extends JFrame {
	
	// obects initialization
	private ResultSetTableModel tableModel;
	private ResultSetTableModel tableModel2;
	ButtonListener listener = new ButtonListener();
	TextListner textListener = new TextListner();
	DataCheck datacheck = new DataCheck();
	Insert database = new Insert();
	
	// components initialization
	private JTable productTable, currentOrdTable;
	private JTextField numtextField, totalPriceTextF, editQntTextF;
	private JButton cancelButton, removeButton, completeButton, editButton, btnNewButton;

	// variable,arrays initialization
	private double newTotalItemPrice, oldTotalItemP, currentPrice, totalPrice = 0.0;
	private int orderQuantity = 0, oldQuntity = 0, productQuantity = 0, numElements = 0, orderID;
	private String prIdArray[] = new String[20];
	private int prQuantityArray[] = new int[20];
	public String sql = "SELECT * FROM project.products";
	private String sql2 = "";

	/**
	 * @param title      - Heading
	 * @param customerID - existing customer ID entered in orderForm.
	 * @param orderID    - Row's Value seleted from orderForm. jTable
	 */
	public currentOrderForm(String title, String customerID, int orderID) {

		setTitle(title);
		setOrderID(orderID);
		sql2 = "SELECT Name,OrderedQuantity,Price,orderdetails.ProductID,orderdetails.OrderID FROM (project.orderdetails "
				+ "INNER JOIN project.products ON orderdetails.ProductID = products.ProductID) "
				+ "WHERE orderdetails.OrderID = " + orderID;
		setResizable(false);
// main panel,layout
		JPanel cFrame = new JPanel(new BorderLayout());
		cFrame.setPreferredSize(new Dimension(700, 350));

// set up current order table

		try {
			tableModel2 = new ResultSetTableModel(sql2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		currentOrdTable = new JTable(tableModel2);
		TableRowSorter<TableModel> sorter1 = new TableRowSorter<TableModel>(tableModel2);
		currentOrdTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currentOrdTable.setRowSorter(sorter1);
		currentOrdTable.setPreferredSize(new Dimension(0, 150));
		JScrollPane tableLayout2 = new JScrollPane(currentOrdTable);

// set up products table
		try {
			tableModel = new ResultSetTableModel(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		productTable = new JTable(tableModel);
		productTable.setPreferredSize(new Dimension(400, 200));
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		productTable.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(productTable);

// create Panels and set there size

		JPanel tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension(400, 92));
		tablePanel.add(tableLayout2);
		JPanel leftPanel = new JPanel();
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel innerLeftpanel = new JPanel(new GridLayout(4, 3, 5, 10));
		innerLeftpanel.setPreferredSize(new Dimension(385, 140));
		tableLayout.setPreferredSize(new Dimension(430, 0));
		tableLayout2.setPreferredSize(new Dimension(710, 80));
		leftPanel.setPreferredSize(new Dimension(390, 0));
		topPanel.setPreferredSize(new Dimension(0, 62));

// create labels,texfields,buttons
		JLabel lblCurrentOrder = new JLabel("Customer " + customerID + " Order " + orderID);
		lblCurrentOrder.setFont(new Font("Arial", Font.BOLD, 14));
		lblCurrentOrder.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel headingLabel3 = new JLabel("Product Table                                           ");
		headingLabel3.setFont(new Font("Tahoma", Font.BOLD, 12));
		headingLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel editLabel = new JLabel("Edit Quantity:");
		JLabel totalpriceLabel = new JLabel("Total price");
		JLabel quantityLabel = new JLabel("Number Items:");

		numtextField = new JTextField(10);
		numtextField.addKeyListener(textListener);

		editQntTextF = new JTextField(10);
		editQntTextF.addKeyListener(textListener);
		editQntTextF.setEnabled(false);

		totalPriceTextF = new JTextField(5);
		totalPriceTextF.setEditable(false);
		totalPriceTextF.setText("0.0");
		btnNewButton = new JButton("Add to Order");
		btnNewButton.addActionListener(listener);

		removeButton = new JButton("Remove Item");
		removeButton.addActionListener(listener);
		removeButton.setEnabled(false);

		editButton = new JButton("Update");
		editButton.setEnabled(false);
		editButton.addActionListener(listener);

		cancelButton = new JButton("Cancel Order");
		cancelButton.addActionListener(listener);
		JLabel emptyLabel = new JLabel("");

		JLabel label = new JLabel(" ");
		label.setIcon(new ImageIcon(
				"C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));

// add Components to there Panels

		innerLeftpanel.add(quantityLabel);
		innerLeftpanel.add(numtextField);
		innerLeftpanel.add(btnNewButton);
		innerLeftpanel.add(editLabel);
		innerLeftpanel.add(editQntTextF);
		innerLeftpanel.add(editButton);
		innerLeftpanel.add(totalpriceLabel);
		innerLeftpanel.add(totalPriceTextF);

		topPanel.add(lblCurrentOrder, BorderLayout.CENTER);
		topPanel.add(headingLabel3, BorderLayout.SOUTH);
		topPanel.add(label, BorderLayout.WEST);
		leftPanel.add(innerLeftpanel);
		innerLeftpanel.add(emptyLabel);
		
				completeButton = new JButton("Complete Order");
				completeButton.setEnabled(false);
				completeButton.addActionListener(listener);
				
						innerLeftpanel.add(completeButton);
		innerLeftpanel.add(cancelButton);
		innerLeftpanel.add(removeButton);

// add Panels to frame
		cFrame.add(topPanel, BorderLayout.NORTH);
		cFrame.add(leftPanel, BorderLayout.WEST);
		JPanel headingPanel = new JPanel();
		
				headingPanel.setPreferredSize(new Dimension(385, 56));
				JLabel headingLabel2 = new JLabel("Current Order Table");
				headingLabel2.setFont(new Font("Tahoma", Font.BOLD, 12));
				headingLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
				
						headingPanel.setLayout(new BorderLayout(0, 0));
						headingPanel.add(headingLabel2, BorderLayout.SOUTH);
						leftPanel.add(headingPanel);
		cFrame.add(tablePanel, BorderLayout.SOUTH);
		cFrame.add(tableLayout, BorderLayout.EAST);
		getContentPane().add(cFrame);

//add tablse to mouseListner
		productTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			}
		});
		currentOrdTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}
		});
// on exit button pressed window closed and order cleared.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				database.clearCurrentOrder(orderID);
				for (int i = 0; i < numElements; i++) {
					database.UpdateProductQuantity(prQuantityArray[i], prIdArray[i]);
				}
				dispose();
				OrderForm.setAfterOrder();
			}

		});
// double click to retrieve info to boxes so it can be edited
		currentOrdTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2) {
					// your valueChanged overridden method
					if (row != -1) {
						editButton.setEnabled(true);
						editQntTextF.setEnabled(true);
						oldQuntity = (int) currentOrdTable.getModel().getValueAt(row, 1);
						editQntTextF.setText(Integer.toString(oldQuntity));
					}
				}
			}
		});
	}// end of constructor

	// TextListner for not allowing letter's in Quantity fields
	class TextListner extends KeyAdapter implements KeyListener {

		public void keyPressed(KeyEvent e) {// allows only number's (allows backspace (8))
			boolean onlyNumbers = false;
			boolean specialkeys = false;
			if (e.getSource() == numtextField) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only digits allowed", "Error", JOptionPane.ERROR_MESSAGE);
					numtextField.setText("");
				}
			} else if (e.getSource() == editQntTextF) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only digits allowed", "Error", JOptionPane.ERROR_MESSAGE);
					editQntTextF.setText("");
				}
			}

		}
	}

//getter,setters
	/**
	 * 
	 * @return -Integer passdown from orderForm
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * 
	 * @param orderID - Integer passdown from orderForm
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

// button listner class
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// local variables
			String quantityText, productid, orderid, buttonLabel = e.getActionCommand();
			int selectedRow;

//Add to Order button pressed (gets selected row's productID/price, calculate total price
// and update's database and JTable)
			if (buttonLabel.equals("Add to Order")) {

				int row = productTable.getSelectedRow();
				quantityText = numtextField.getText();
				if (quantityText.equals("") || (row == -1)) {
					JOptionPane.showMessageDialog(null, "No record selected or Quantity empty", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (numElements == 0) {// if there is products inside current order

					productid = productTable.getModel().getValueAt(row, 0).toString();

					currentPrice = (double) productTable.getModel().getValueAt(row, 4);
					productQuantity = (int) productTable.getModel().getValueAt(row, 5);
					orderQuantity = Integer.parseInt(quantityText);

					// saves old product Quantity incase of order cancelation
					prIdArray[numElements] = productid;
					prQuantityArray[numElements] = productQuantity;
					numElements++;

					// update product quantity table and JTable
					database.UpdateProductQuantity(productQuantity -= orderQuantity, productid);
					try {
						tableModel.update();
						productTable.setModel(tableModel);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					totalPrice += (currentPrice * orderQuantity);// calculate total price
					totalPriceTextF.setText(Double.toString(totalPrice));// sets total price text field to total price

					// update's Current order table
					database.InCurrentOrder(orderQuantity, productid, getOrderID());
					numtextField.setText("");
					try {// update's JTable
						tableModel2.update();
						currentOrdTable.setModel(tableModel2);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					productTable.getSelectionModel().clearSelection();
					completeButton.setEnabled(true);
					removeButton.setEnabled(true);
				} else {
					productid = productTable.getModel().getValueAt(row, 0).toString();
					boolean existElement = false;
					for (int i = 0; i < numElements; i++) {
						if (prIdArray[i].equals(productid)) {
							existElement = true;
							i = numElements;// break
						}
					}
					if (existElement == true) {
						JOptionPane.showMessageDialog(null, "Product already exist in the order", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						currentPrice = (double) productTable.getModel().getValueAt(row, 4);
						productQuantity = (int) productTable.getModel().getValueAt(row, 5);
						orderQuantity = Integer.parseInt(quantityText);

						// saves old product Quantity incase of order cancelation
						prIdArray[numElements] = productid;
						prQuantityArray[numElements] = productQuantity;
						numElements++;

						// update product quantity table and JTable
						database.UpdateProductQuantity(productQuantity -= orderQuantity, productid);
						try {
							tableModel.update();
							productTable.setModel(tableModel);

						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						totalPrice += (currentPrice * orderQuantity);// calculate total price
						totalPriceTextF.setText(Double.toString(totalPrice));// sets total price text field to total
																				// price

						database.InCurrentOrder(orderQuantity, productid, getOrderID());// update's Currentorder table
						numtextField.setText("");
						try {// update's JTable

							tableModel2.update();
							currentOrdTable.setModel(tableModel2);

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						productTable.getSelectionModel().clearSelection();
						cancelButton.setEnabled(true);
					}

				}
//Remove Item button presses(gets selected row's orderID/productID 
//delete's from database/updates Jtable and updates TotalPrice)
			} else if (buttonLabel.equals("Remove Item")) {
				selectedRow = currentOrdTable.getSelectedRow();
				if (selectedRow != -1) {
					productid = currentOrdTable.getModel().getValueAt(selectedRow, 3).toString();
					orderid = currentOrdTable.getModel().getValueAt(selectedRow, 4).toString();
					orderQuantity = (int) currentOrdTable.getModel().getValueAt(selectedRow, 1);

					if (numElements == 1) {// checks if its last product.
						prIdArray[0] = "";
						prQuantityArray[0] = 0;
						numElements = 0;
						completeButton.setEnabled(false);
						removeButton.setEnabled(false);
						editButton.setEnabled(false);

					} else {// if there is more then 1 product,search product array and deleted if exist
						for (int i = 0; i < numElements; i++) {
							if (prIdArray[i].equals(productid)) {
								for (int a = i; a < numElements - 1; a++) {
									prIdArray[a] = prIdArray[a + 1];
									prQuantityArray[a] = prQuantityArray[a + 1];
								}
								i = numElements;// break;
							}
						}
						prIdArray[numElements] = "";
						prQuantityArray[numElements] = 0;
						numElements--;
					}
					// update product quantity table and JTable

					productQuantity = database.GetProductQuantity(productid);
					database.UpdateProductQuantity(productQuantity += orderQuantity, productid);
					try {
						tableModel.update();
						productTable.setModel(tableModel);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					// recalculate the total order
					orderQuantity = Integer.parseInt(currentOrdTable.getModel().getValueAt(selectedRow, 1).toString());
					double price = (double) currentOrdTable.getModel().getValueAt(selectedRow, 2);
					double totalItemPrice = (orderQuantity * price);
					totalPrice = totalPrice - totalItemPrice;
					totalPriceTextF.setText(Double.toString(totalPrice));// sets total price text field to total price

					// Delete's items from current order table and update's JTable
					database.DeleteItem(productid, orderid);
					try {
						tableModel2.update();
						currentOrdTable.setModel(tableModel2);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					currentOrdTable.getSelectionModel().clearSelection();
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
//Complete Order pressed (update order table with TotalPrice and exit the currentOrder window)
			} else if (buttonLabel.equals("Complete Order")) {
				if (totalPrice != 0) {
					database.UpdateOrderPrice(totalPrice, orderID);
					dispose();
					OrderForm.setAfterOrder();
				}
//update button presses (updates what is in fethed quantity textfield and in side the table (also updates totalprice))
			} else if (buttonLabel.equals("Update")) {
				selectedRow = currentOrdTable.getSelectedRow();
				if (selectedRow != -1) {
					quantityText = editQntTextF.getText();
					if (quantityText.equals("") || Integer.parseInt(quantityText) == 0) {
						JOptionPane.showMessageDialog(null, "Quantity Field is empty or its 0", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						// get tables ID's so u can find record in the table and update it

						productid = currentOrdTable.getModel().getValueAt(selectedRow, 3).toString();
						orderid = currentOrdTable.getModel().getValueAt(selectedRow, 4).toString();

						// calculate old price and new total item price

						orderQuantity = Integer.parseInt(quantityText);
						currentPrice = (double) currentOrdTable.getModel().getValueAt(selectedRow, 2);
						oldTotalItemP = (oldQuntity * currentPrice);
						newTotalItemPrice = (orderQuantity * currentPrice);

						// check what one total price is bigger and update total if needed(also update
						// table)

						if (oldTotalItemP == newTotalItemPrice) {
							JOptionPane.showMessageDialog(null, " Quantity is the same", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else if (oldTotalItemP > newTotalItemPrice) {// new quantity is smaller then old one

							totalPrice -= (oldTotalItemP - newTotalItemPrice);
							database.UpdateCurrentOrder(orderQuantity, productid, orderid);
							editQntTextF.setText("");
							editButton.setEnabled(false);
							editQntTextF.setEnabled(false);
							totalPriceTextF.setText(Double.toString(totalPrice));

							// calculate left over and update it to table
							// update's Product table
							int productLeftover = oldQuntity - orderQuantity;
							productQuantity = database.GetProductQuantity(productid);
							database.UpdateProductQuantity(productQuantity += productLeftover, productid);
							try {
								tableModel.update();
								productTable.setModel(tableModel);

							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							// update's currentOrder JTable
							try {
								tableModel2.update();
								currentOrdTable.setModel(tableModel2);

								JOptionPane.showMessageDialog(null, " Order Updated", "Alert",
										JOptionPane.WARNING_MESSAGE);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else {// new quantity is bigger then old one
							totalPrice += (newTotalItemPrice - oldTotalItemP);

							database.UpdateCurrentOrder(orderQuantity, productid, orderid);
							editQntTextF.setText("");
							editButton.setEnabled(false);
							editQntTextF.setEnabled(false);
							totalPriceTextF.setText(Double.toString(totalPrice));// sets total price text field to total
																					// price
							try {
								tableModel2.update();
								currentOrdTable.setModel(tableModel2);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

							// calculate addon Quantity and update product table
							int addonQuantity = orderQuantity - oldQuntity;
							productQuantity = database.GetProductQuantity(productid);
							database.UpdateProductQuantity(productQuantity -= addonQuantity, productid);
							try {
								tableModel.update();
								productTable.setModel(tableModel);

							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
//cancel button presses (clears current order and empty order inside order tables)
			} else if (buttonLabel.equals("Cancel Order")) {
				database.clearCurrentOrder(orderID);
				for (int i = 0; i < numElements; i++) {
					database.UpdateProductQuantity(prQuantityArray[i], prIdArray[i]);
				}
				dispose();
				OrderForm.setAfterOrder();

			}

		}
	}// end of button listner
}// end of class
