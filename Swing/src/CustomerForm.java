package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
/**
 * Class used for Manage Customers
 * funtions include:(Add,Delete,Update,double click to retrieve)
 * @author Ignas Rocas
 *
 */
public class CustomerForm extends JFrame {
	
	//obects initialization
	private ResultSetTableModel tableModel;
	DataCheck datacheck = new DataCheck();
	ButtonListener listener = new ButtonListener();
	TextListner textListener = new TextListner();
	Insert database = new Insert();

	//components initialization
	private JTextField fnameText,lnameText,numText,addressText,codeText;
	private JButton addBtn,updateBtn,deleteBtn;
	private JTable resultTable;
	private JRadioButton maleRadioBtn,femaleRadioBtn;
	private ButtonGroup genderGroup;
	private JComboBox<String> ageCombo;
	DefaultComboBoxModel<String> ageModel;
	
	//variables initialization
	String DEFAULT_QUERY = "SELECT * FROM customer";
	private String name,lname,phNum,address,code,gender,ageGroup,nameAlert,name2Alert;
	/**
	 * 
	 * @param p1 - JPanel passdown from the main
	 */
	public CustomerForm(JPanel p1) {

		// main panel,layout
		JPanel frame = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(750, 300));

		// details panel,layout
		JPanel detailsLayout = new JPanel(new GridLayout(8, 2, 5, 10));
		detailsLayout.setPreferredSize(new Dimension(200, 90));
		// buttons panel
		JPanel buttonsLayout = new JPanel();
		buttonsLayout.setPreferredSize(new Dimension(0, 40));

		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 50));

		// set up table
		try {
			tableModel = new ResultSetTableModel(DEFAULT_QUERY);// gets data from database
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		resultTable = new JTable(tableModel);// sets data to JTable
		resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultTable.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(resultTable);
		tableLayout.setPreferredSize(new Dimension(535, 200));
		frame.add(tableLayout, BorderLayout.EAST);

		// set up normal textboxes & lables
		JLabel fnameLabel = new JLabel("First name:");
		fnameText = new JTextField();
		fnameText.setToolTipText("");
		fnameText.setBorder(new LineBorder(Color.BLACK));
		fnameText.addKeyListener(textListener);

		JLabel lnameLabel = new JLabel("Last name:");
		lnameText = new JTextField();
		lnameText.setBorder(new LineBorder(Color.BLACK));
		lnameText.addKeyListener(textListener);

		JLabel numlabel = new JLabel("Ph.number:");
		numText = new JTextField();
		numText.setBorder(new LineBorder(Color.BLACK));
		numText.addKeyListener(textListener);

		JLabel addressLabel = new JLabel("Address:");
		addressText = new JTextField();
		addressText.setBorder(new LineBorder(Color.BLACK));

		JLabel codeLabel = new JLabel("Postcode:");
		codeText = new JTextField();
		codeText.setBorder(new LineBorder(Color.BLACK));

		// sut up radio buttons and its group & lables
		JLabel genderLabel = new JLabel("Gender:");
		maleRadioBtn = new JRadioButton("male");
		maleRadioBtn.setActionCommand("male");
		maleRadioBtn.setSelected(true);
		JLabel genderLabel2 = new JLabel();
		femaleRadioBtn = new JRadioButton("female");
		femaleRadioBtn.setActionCommand("female");
		genderGroup = new ButtonGroup();
		genderGroup.add(femaleRadioBtn);
		genderGroup.add(maleRadioBtn);

		// set up combo box
		JLabel ageLabel = new JLabel("Age Group:");
		ageCombo = new JComboBox<String>();
		ageModel = new DefaultComboBoxModel<String>();
		ageModel.addElement(" 18 - 25");
		ageModel.addElement(" 25 - 45");
		ageModel.addElement("over 65");
		ageCombo.setModel(ageModel);

		// buttons
		addBtn = new JButton("Add Customer");
		addBtn.addActionListener(listener);
		
		updateBtn = new JButton("Update Customer");
		updateBtn.addActionListener(listener);
		updateBtn.setEnabled(false);
		
		deleteBtn = new JButton("Delete Customer");
		deleteBtn.addActionListener(listener);
		
		//add components to there layouts
		detailsLayout.add(fnameLabel);
		detailsLayout.add(fnameText);

		detailsLayout.add(lnameLabel);
		detailsLayout.add(lnameText);

		detailsLayout.add(genderLabel);
		detailsLayout.add(maleRadioBtn);
		detailsLayout.add(genderLabel2);
		detailsLayout.add(femaleRadioBtn);

		detailsLayout.add(numlabel);
		detailsLayout.add(numText);

		detailsLayout.add(addressLabel);
		detailsLayout.add(addressText);

		detailsLayout.add(codeLabel);
		detailsLayout.add(codeText);

		detailsLayout.add(ageLabel);
		detailsLayout.add(ageCombo);

		buttonsLayout.add(addBtn);
		buttonsLayout.add(updateBtn);
		buttonsLayout.add(deleteBtn);

		// add containers to the main page
		frame.add(buttonsLayout, BorderLayout.SOUTH);
		frame.add(detailsLayout, BorderLayout.WEST);
		p1.add(frame);

		frame.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel headingLabel = new JLabel("Manage Customers");
		topPanel.add(headingLabel, BorderLayout.CENTER);
		headingLabel.setFont(new Font("Arial", Font.BOLD, 14));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
					
		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon("C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));
		topPanel.add(iconLabel, BorderLayout.WEST);

		resultTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			}
		});
		// double click retrievs info
		resultTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2) {
					// your valueChanged overridden method
					if (row != -1) {
						TableModel updateModel = resultTable.getModel();
						fnameText.setText(updateModel.getValueAt(row, 1).toString());
						lnameText.setText(updateModel.getValueAt(row, 2).toString());

						String gender = (updateModel.getValueAt(row, 3).toString());
						if (gender.equals("male")) {
							maleRadioBtn.setSelected(true);
						} else {
							femaleRadioBtn.setSelected(true);
						}
						numText.setText(updateModel.getValueAt(row, 4).toString());
						addressText.setText(updateModel.getValueAt(row, 5).toString());
						codeText.setText(updateModel.getValueAt(row, 6).toString());
						updateBtn.setEnabled(true);
					}
				}
			}
		});

	}

	// TextListner for not allowing letter's in Quantity fields
	class TextListner extends KeyAdapter implements KeyListener {

		// allows only letter's (allows backspace (8) & Shift/CapsLock(65535))
		public void keyPressed(KeyEvent e) {
			boolean onlyLetters = false, specialkeys = false, onlyNumbers = false;
			if (e.getSource() == fnameText) {
				onlyLetters = datacheck.onlyLetters(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyLetters == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only letters allowed", "Error", JOptionPane.ERROR_MESSAGE);
					fnameText.setText("");
				}
			} else if (e.getSource() == lnameText) {
				onlyLetters = datacheck.onlyLetters(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyLetters == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only letters allowed", "Error", JOptionPane.ERROR_MESSAGE);
					lnameText.setText("");
				}
			} else if (e.getSource() == numText) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "Only digits allowed", "Error", JOptionPane.ERROR_MESSAGE);
					numText.setText("");
				}
			}
		}
	}

	// Listener for buttons method
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String buttonLabel = evt.getActionCommand();
			boolean duplicate = true;
// Add customer button pressed
			if (buttonLabel.equals("Add Customer")) {
				name = fnameText.getText();
				lname = lnameText.getText();
				duplicate = database.duplicate(name, lname);
				if (duplicate == true) {
					JOptionPane.showMessageDialog(null, "Customer already exists", "Error", JOptionPane.ERROR_MESSAGE);

				} else {
					phNum = numText.getText();
					address = addressText.getText();
					code = codeText.getText();
					gender = genderGroup.getSelection().getActionCommand();
					ageGroup = (String) ageCombo.getSelectedItem();

					if (name.equals("") || lname.equals("") || phNum.equals("") || address.equals("")
							|| code.equals("")) {
						JOptionPane.showMessageDialog(null, "Some fields left empty", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						database.InsertTo(name, lname, gender, phNum, address, code, ageGroup);
						DisplayQueryResults.update();
						fnameText.setText("");
						lnameText.setText("");
						numText.setText("");
						addressText.setText("");
						codeText.setText("");
						try {
							
							tableModel.update();
							resultTable.setModel(tableModel);
							JOptionPane.showMessageDialog(null, "Successfully " + name + " " + lname + " Record Added",
									"Alert", JOptionPane.WARNING_MESSAGE);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
// delete button plessed
			} else if (buttonLabel.equals("Delete Customer")) {
				int row = resultTable.getSelectedRow();
				if (row != -1) {
					String cell = resultTable.getModel().getValueAt(row, 0).toString();
					nameAlert = resultTable.getModel().getValueAt(row, 1).toString();
					name2Alert = resultTable.getModel().getValueAt(row, 2).toString();
					int answer = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete " + nameAlert + " " + name2Alert + " record", "Alert",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						database.Delete(cell);
						OrderForm.setAfterOrder();
						fnameText.setText("");
						lnameText.setText("");
						numText.setText("");
						addressText.setText("");
						codeText.setText("");
						try {

							tableModel.update();
							resultTable.setModel(tableModel);
							OrderForm.setAfterOrder();
							invoice.setAfterOrder();
							DisplayQueryResults.update();


						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
//update button pressed
			} else if (buttonLabel.equals("Update Customer")) {

				int row = resultTable.getSelectedRow();
				if (row != -1) {
					String selection = resultTable.getModel().getValueAt(row, 0).toString();
					name = fnameText.getText();
					lname = lnameText.getText();
					phNum = numText.getText();
					address = addressText.getText();
					code = codeText.getText();
					gender = genderGroup.getSelection().getActionCommand();
					ageGroup = (String) ageCombo.getSelectedItem();

					if (name.equals("") || lname.equals("") || phNum.equals("") || address.equals("")
							|| code.equals("")) {
						JOptionPane.showMessageDialog(null, "Some fields left empty", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						database.Update(name, lname, gender, phNum, address, code, ageGroup, selection);
						fnameText.setText("");
						lnameText.setText("");
						numText.setText("");
						addressText.setText("");
						codeText.setText("");
						updateBtn.setEnabled(false);
						//update's other form jTables
						DisplayQueryResults.update();
						invoice.setAfterOrder();
						OrderForm.setAfterOrder();
						
						try {
							tableModel.update();
							resultTable.setModel(tableModel);
							nameAlert = resultTable.getModel().getValueAt(row, 1).toString();
							name2Alert = resultTable.getModel().getValueAt(row, 2).toString();

							JOptionPane.showMessageDialog(null,
									nameAlert + " " + name2Alert + " Record Successfully Updated", "Alert",
									JOptionPane.WARNING_MESSAGE);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						invoice.setAfterOrder();
						DisplayQueryResults.update();


					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

		}// end of actionPerformed
	}// end of button listner method
}// end of class
