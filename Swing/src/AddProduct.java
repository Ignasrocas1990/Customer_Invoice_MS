
package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField ;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


//import Swing.src.form.String;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton ;
import javax.swing.JComboBox;
import java.awt.event.*;
import javax.swing.ImageIcon;
/**
 * Class used for Manage products
 * funtions include:(Add,Delete,Update)
 * @author Diarmuid Brennan's
 *
 */
public class AddProduct extends JFrame{
	private static ResultSetTableModel tableModel;
	public String sql = "SELECT * FROM products";
	private JLabel heading;
	private JLabel pName ;
	private JLabel pType  ;
	private JLabel pDsc ; 
	private JLabel pPrice ; 
	private JLabel pQuantity  ;
	private JRadioButton Sotfware; 
    private JRadioButton Hardware;
    private JRadioButton Other;
    private ButtonGroup gengp;
    private static JTable resultTable;
    private String nameAlert;
 
    private JTextField jtfpName ;
	private JTextField jtfpType ;
	private JTextField jtfpDsc  ;
	private JTextField jtfpPrice ;
	private JTextField jtfpQuantity ;
	private JButton updateBtn ;
	
	DataCheck datacheck = new DataCheck();
	inputListener textListener = new inputListener();
	
	Insert database = new Insert();
	
	/**
	 * @param p2 - JPanel passed from main
	 */
	public AddProduct(JPanel p2) {
		//set up frame and panels for project layout
		JPanel frame = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(750, 300));
		//set up heading panel
		JPanel headingLayout = new JPanel();
		headingLayout.setPreferredSize(new Dimension(20, 50));
		// details panel,layout
		JPanel detailsLayout = new JPanel(new GridLayout(6, 2, 10, 15));
		detailsLayout.setPreferredSize(new Dimension(200, 90));
		// buttons panel
		JPanel buttonsLayout = new JPanel();
		buttonsLayout.setPreferredSize(new Dimension(150, 60));
		// call SQL table class
		try {
			tableModel = new ResultSetTableModel(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// set up product table retrieved from database
		resultTable = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		resultTable.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(resultTable);
		tableLayout.setPreferredSize(new Dimension(535, 400));
		
		//if row on table is double clicked retrieve row data from database
		resultTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		            // your valueChanged overridden method
						if (row != -1) {
							TableModel updateModel = resultTable.getModel();
							jtfpName.setText(updateModel.getValueAt(row, 1).toString());

							String type = (updateModel.getValueAt(row, 2).toString());
							if (type.equals("software")) {
								Sotfware.setSelected(true);
							} else if (type.equals("hardware")) {
								Hardware.setSelected(true);
							}else {
								Other.setSelected(true);
							}
							jtfpDsc.setText(updateModel.getValueAt(row, 3).toString());
							jtfpPrice.setText(updateModel.getValueAt(row, 4).toString());
							jtfpQuantity.setText(updateModel.getValueAt(row, 5).toString());
							updateBtn.setEnabled(true);

						} else {
							JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
						}
		        }
		    }}
		);
		
	   //labels, buttons, radio buttons and textfields
		//label for heading panel
       heading = new JLabel("Manage Products"); 
       heading.setFont(new Font("Arial", Font.BOLD, 14)); 
       heading.setHorizontalAlignment(SwingConstants.CENTER);
   
        //label and textfield for product name
        pName = new JLabel("Name:") ;  
        jtfpName  = new JTextField(20);  
        jtfpName.setBorder(new LineBorder(Color.BLACK));
        jtfpName.setToolTipText("Enter Product name") ;
        
        //label and radio buttons for product type
        pType = new JLabel("Type:") ;  
        Sotfware = new JRadioButton("Sotfware"); 
        Sotfware.setSelected(false); 
    
        Hardware = new JRadioButton("Hardware"); 
        Hardware.setSelected(false); 
      
        Other = new JRadioButton("Other");  
        Other.setSelected(false); 
        
        gengp = new ButtonGroup(); 
        gengp.add(Sotfware); 
        gengp.add(Hardware);
        gengp.add(Other);
        
        //label and textfield for product description
        pDsc = new JLabel("Description:"); 
        jtfpDsc  = new JTextField();  
        jtfpDsc.setBorder(new LineBorder(Color.BLACK)); 
        jtfpDsc.setToolTipText("Enter Product description") ;
       
        //label and textfield for product price
        pPrice = new JLabel("Price:"); 
        jtfpPrice  = new JTextField(20);
        jtfpPrice.setBorder(new LineBorder(Color.BLACK));
        jtfpPrice.setToolTipText("Enter Product price($0.00)") ;
        jtfpPrice.addKeyListener(textListener);//add key listener
        
      //label and textfield for product quantity
        pQuantity = new JLabel("Quantity:") ;   
        jtfpQuantity = new JTextField(20);
        jtfpQuantity.setBorder(new LineBorder(Color.BLACK));
        jtfpQuantity.setToolTipText("Enter Product quantity in stock") ;
        jtfpQuantity.addKeyListener(textListener);//add key listener
        
      //add labels and listeners for buttons
        ButtonHandler handler = new ButtonHandler() ;
        JButton addBtn = new JButton("Add Product"); 
        addBtn.addActionListener(handler);
		updateBtn = new JButton("Update Product");
		updateBtn.addActionListener(handler);
		updateBtn.setEnabled(false);
		JButton deleteBtn = new JButton("Delete Product");
		deleteBtn.addActionListener(handler);
		
		//set layout for heading and add label
		headingLayout.setLayout(new BorderLayout(0, 0));
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\ignas\\Desktop\\College\\New 2nd year files\\object oriantation\\customer-invoice-management-system\\Untitled.png"));
		headingLayout.add(label,BorderLayout.WEST);
		
  
		// add swing components to panes
		headingLayout.add(heading);
		
		
		detailsLayout.add(pName);
		detailsLayout.add(jtfpName);

		detailsLayout.add(pType);
		detailsLayout.add(Sotfware);
		detailsLayout.add(Hardware);
		detailsLayout.add(Other);

		detailsLayout.add(pDsc);
		detailsLayout.add(jtfpDsc);

		detailsLayout.add(pPrice);
		detailsLayout.add(jtfpPrice);

		detailsLayout.add(pQuantity);
		detailsLayout.add(jtfpQuantity);

		buttonsLayout.add(addBtn);
		buttonsLayout.add(updateBtn);
		buttonsLayout.add(deleteBtn);

		// add containers to the main page
		frame.add(headingLayout, BorderLayout.NORTH);
		frame.add(buttonsLayout, BorderLayout.SOUTH);
		frame.add(detailsLayout, BorderLayout.WEST);
		frame.add(tableLayout, BorderLayout.EAST);
		p2.add(frame);
        
	}
	// update product table after new order
	/**
	 * this method update's JTable after new order
	 */
	public static void setAfterOrder() {
			try {
				tableModel.update();
				resultTable.setModel(tableModel);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		

	}
	
	//keylistener class for prduct price and quantity
	class inputListener extends KeyAdapter implements KeyListener {

		// allows only numbers (allows backspace (8), '.' & Shift/CapsLock(65535))
		public void keyPressed(KeyEvent e) {
			boolean onlyLetters = false, specialkeys = false, onlyNumbers = false;
			if (e.getSource() == jtfpPrice) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "You must enter a product price($0.00)", "Error", JOptionPane.ERROR_MESSAGE);
					jtfpPrice.setText("");
				}
			} else if (e.getSource() == jtfpQuantity) {
				onlyNumbers = datacheck.onlyNumbers(e.getKeyChar());
				specialkeys = datacheck.specialkeys((int) e.getKeyChar());
				if (onlyNumbers == false && specialkeys == false) {
					JOptionPane.showMessageDialog(null, "You must enter a quantity", "Error", JOptionPane.ERROR_MESSAGE);
					jtfpQuantity.setText("");
				}
			} 
			}
		}
	
	//actions performed once a button has been seleted
	class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			boolean duplicate = true;
			//pass textfields to string variables
			String type = " ";   
            // If condition to check which jRadioButton is selected. 
            if (Sotfware.isSelected()) { 
           	 type = "Software"; 
            } 
            else if (Hardware.isSelected()) { 
           	 type = "Hardware"; 
            } 
            else { 
           	 type = "Other"; 
            } 			
			String description = jtfpDsc.getText() ;
			String price = jtfpPrice.getText() ;
			String quantity = jtfpQuantity.getText() ;
			
			//if add product is selected
			if(event.getActionCommand().equals("Add Product")){
				String name = jtfpName.getText();
				//check if product has already been entered
				duplicate = database.duplicateProd(name);
				if (duplicate == true) {
					JOptionPane.showMessageDialog(null, name + " has already been entered in the system", "Error", JOptionPane.ERROR_MESSAGE);

				}
				else {
					if (name.equals("") || type.equals("") || description.equals("") || price.equals("")|| quantity.equals("")) {
						JOptionPane.showMessageDialog(null, "Some fields left empty", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						double convertprice = Double.parseDouble(jtfpPrice.getText());
						int convertquantity = Integer.parseInt(jtfpQuantity.getText()) ;
						database.InsertProduct(name, type, description, convertprice, convertquantity);
						jtfpName.setText("");
						Sotfware.setSelected(false);
						Hardware.setSelected(false);
						Other.setSelected(false);
						jtfpDsc.setText("");
						jtfpPrice.setText("");
						jtfpQuantity.setText("");
						DisplayQueryResults.update();
						try {
							tableModel.update();
							resultTable.setModel(tableModel);
							DisplayQueryResults.update();

						} catch (SQLException e) {
							e.printStackTrace();
						}	
					}
				}
				
			}
			
			//if delete button is selected
			else if (event.getActionCommand().equals("Delete Product")) {
				int row = resultTable.getSelectedRow();
				if (row != -1) {//if row is found
					String cell = resultTable.getModel().getValueAt(row, 0).toString();
					nameAlert = resultTable.getModel().getValueAt(row, 1).toString();
				
					int answer = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete " + nameAlert  + " record", "Alert",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						database.DeleteProduct(cell);
						jtfpName.setText("");
						Sotfware.setSelected(false);
						Hardware.setSelected(false);
						Other.setSelected(false);
						jtfpDsc.setText("");
						jtfpPrice.setText("");
						jtfpQuantity.setText("");
						DisplayQueryResults.update();
						try {

							tableModel.update();
							resultTable.setModel(tableModel);
							DisplayQueryResults.update();

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			 
			//if update button is selected
				else if (event.getActionCommand().equals("Update Product")) {

						int row = resultTable.getSelectedRow();
						if (row != -1) {
							String selection = resultTable.getModel().getValueAt(row, 0).toString();
							String name = jtfpName.getText();
							
						
							if (name.equals("") || type.equals("") || description.equals("") ||price.equals("")|| quantity.equals("")) {
								JOptionPane.showMessageDialog(null, "Some fields left empty", "Error", JOptionPane.ERROR_MESSAGE);
							} else {
								double convertprice = Double.parseDouble(jtfpPrice.getText());
								int convertquantity = Integer.parseInt(jtfpQuantity.getText()) ;
								database.UpdateProduct(name, type, description, convertprice, convertquantity, selection);
								jtfpName.setText("");
								Sotfware.setSelected(false);
								Hardware.setSelected(false);
								Other.setSelected(false);
								jtfpDsc.setText("");
								jtfpPrice.setText("");
								jtfpQuantity.setText("");
								updateBtn.setEnabled(false);
								DisplayQueryResults.update();

								
								try {
									tableModel.update();
									resultTable.setModel(tableModel);
									nameAlert = resultTable.getModel().getValueAt(row, 1).toString();
									DisplayQueryResults.update();
									
									
									JOptionPane.showMessageDialog(null, nameAlert+" Record Successfully Updated", "Alert",
											JOptionPane.WARNING_MESSAGE);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
						}

					}
			
			
		}
	
	}	
}
