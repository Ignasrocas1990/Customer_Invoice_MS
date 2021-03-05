package src;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 * this class used to view order selected
 * @author ignas rocas
 */
public class ViewOrder extends JFrame {
	public String sql2;
	private ResultSetTableModel tableModel;
	private JTable currentOrdTable;

	/**
	 * 
	 * @param title String title of a fram
	 * @param orderID String Value used for getting data from database
	 */
	public ViewOrder(String title, String orderID) {
		setTitle(title);
		sql2 = "SELECT Name,OrderedQuantity,Price FROM (project.orderdetails "
				+ "INNER JOIN project.products ON orderdetails.ProductID = products.ProductID) "
				+ "WHERE orderdetails.OrderID = " + orderID;
			setLayout(new FlowLayout());
			setResizable(false);
		// set up current order table
				try {
					tableModel = new ResultSetTableModel(sql2);

				} catch (SQLException e) {
					e.printStackTrace();
				}
				currentOrdTable = new JTable(tableModel);
				TableRowSorter<TableModel> sorter1 = new TableRowSorter<TableModel>(tableModel);
				currentOrdTable.setRowSorter(sorter1);
				currentOrdTable.setPreferredSize(new Dimension(100, 250));
				JScrollPane tableLayout2 = new JScrollPane(currentOrdTable);
				tableLayout2.setPreferredSize(new Dimension(450, 170));
				add(tableLayout2);
		
				
	}

}
