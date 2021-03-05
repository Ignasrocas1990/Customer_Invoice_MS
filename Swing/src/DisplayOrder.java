package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 * Class used for Query database's
 * funtions include:(Query database's)
 * @author Diarmuid Brennan's
 */
public class DisplayOrder extends JFrame{
	private ResultSetTableModel tableModel;
	private JTable displayOrder ;
	public String sql = "" ;
	/**
	 * 
	 * @param orderID String for creating view of a table
	 */
	public DisplayOrder(String orderID) {
		//create frame
		JPanel frame = new JPanel(new FlowLayout());
		frame.setPreferredSize(new Dimension(750, 350));
		
		//create sql query for table display
		setResizable(false);
		setTitle("View Order");
		sql = "SELECT Name, Description, Price, OrderedQuantity, orderdetails.OrderID FROM project.orderdetails "
				+ "INNER JOIN project.products ON orderdetails.ProductID = products.ProductID WHERE orderdetails.OrderID = "+ orderID;
		
		try {
			tableModel = new ResultSetTableModel(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//add frame and table
		displayOrder = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		displayOrder.setRowSorter(sorter);
		JScrollPane tableLayout = new JScrollPane(displayOrder);
		tableLayout.setPreferredSize(new Dimension(500, 110));
		frame.add(tableLayout) ;
		getContentPane().add(frame);
	}
}
