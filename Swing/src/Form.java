package src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *  Main For Creating a JFrame with JTabbsPane
 * and passing to other specified classes
 * @author ignas rocas

 */
public class Form extends JFrame {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				//create JFrame
				JFrame form = new JFrame("Customer");
				form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				form.setSize(800, 430);
				form.setVisible(true);
				form.setResizable(false);
				form.setLocation(400, 200);
				//create J
				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();
				JTabbedPane tabs = new JTabbedPane();

				new CustomerForm(p1);
				tabs.add("Customer Form", p1);// passing JPanel into CustomerForm class

				new AddProduct(p2);
				tabs.add("Product Form", p2);// passing JPanel into AddProduct class

				new OrderForm(p3);
				tabs.add("Order Form", p3);// passing JPanel into OrderForm class
				
				new invoice(p4);
				tabs.add("Invoice Form", p4);// passing JPanel into invoice class
				
				new DisplayQueryResults(p5);
				tabs.add("Queries Form", p5);// passing JPanel into DisplayQueryResults class
				form.add(tabs);
			}
		});

	}

}
