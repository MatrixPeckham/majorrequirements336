package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TestGUI extends JFrame
{
	private JLabel registrarAdminPage;
	private JButton viewCourses;
	private JButton addDepart;
	private JButton removeDepart;
	
	TestGUI()
	{
		super("Log In");
		this.setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		layoutGUI();
	}
	
	public void layoutGUI()
	{
		registrarAdminPage = new JLabel("Registrar Admin Page");
		registrarAdminPage.setFont(new Font("Times New Roman",1,72));
		viewCourses = new JButton();
		viewCourses.setFont(new Font("Times New Roman",1,24));
		addDepart = new JButton();
		addDepart.setFont(new Font("Times New Roman",1,24));
		removeDepart = new JButton();
		addDepart.setFont(new Font("Times New Roman",1,24));
		
		String[] columnNames = {"Department"};

		Object[][] data = {
				{"Math"},{"Computer Science"},{"Journalism"},{"Information And Technology"},
				{"History"},{"Women Studies"},{"Biology"},{"Chemistry"},{"Physcis"}	
		};
		
		  final JTable table = new JTable(data, columnNames);
	      table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
	      table.setFillsViewportHeight(true);
	      
	      JScrollPane scrollPane = new JScrollPane(table);
	      add(scrollPane);
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		addJComponentToContainerUsingGBL(registrarAdminPage, this, 1, 1, 3, 2);
		addJComponentToContainerUsingGBL(scrollPane, this, 1, 3, 4, 3);
		addJComponentToContainerUsingGBL(viewCourses, this, 1, 10, 1, 1);
		//addJComponentToContainerUsingGBL(addDepart, this, 2, 20, 11, 11);
		//addJComponentToContainerUsingGBL(removeDepart, this, 3, 20, 11, 11);
	}
	
	public void addJComponentToContainerUsingGBL(JComponent jc, Container c,
			int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	public static void main(String[]args)
	{
		TestGUI frame = new TestGUI();
		frame.setVisible(true);
	}
}
