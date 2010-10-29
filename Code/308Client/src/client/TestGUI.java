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
/**
 * Test class that was used to test screen layouts
 * before screen changing was implemented
 *
 * WILL LIKELY BE DELETED BEFORE DEPLOYMENT
 * @author Johm paul
 */
public class TestGUI extends JFrame
{
	private JLabel editDepartmentPage;
	private JLabel coursesForDepartment;
	private JTable table;
	private JButton edit;
	private JButton remove;
	private JButton add;
	private JButton uploadCourses;
	private JButton browse;
	private JButton back;
	private JButton registrarAdminPage;
	private JTextField textField;
	
	TestGUI()
	{
		super("RUM");
		this.setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		layoutGUI();
	}
	
	public void layoutGUI()
	{

		editDepartmentPage = new JLabel("Edit Department Page");
		editDepartmentPage.setFont(new Font("Times New Roman",1,72));
		coursesForDepartment = new JLabel("Courses For Department");
		edit = new JButton("Edit Course");
		remove = new JButton("Remove Course");
		add = new JButton("Add Course");
		uploadCourses = new JButton("Upload Courses");
		browse = new JButton("Browse");
		back = new JButton("Back");
		registrarAdminPage = new JButton("Registrar Admin Page");
		textField = new JTextField(20);

		
		String[] columnNames = {"Courses"};

		Object[][] data = {
				{"CSE 308"},{"CSE 381"},{"CSE 380"},{"CSE 220"},
				{"CSE 114"},{"CSE 215"},{"CSE 219"},{"CSE 110"},
				{"MAT 127"}};
		
		final JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
	    table.setFillsViewportHeight(true);
	      
	    JScrollPane scrollPane = new JScrollPane(table);
	    add(scrollPane);
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		

		addJComponentToContainerUsingGBL(editDepartmentPage, this, 1, 1, 4, 1);
		addJComponentToContainerUsingGBL(coursesForDepartment, this, 1, 2, 1, 1);
		addJComponentToContainerUsingGBL(scrollPane, this, 1, 3, 4, 5);
		addJComponentToContainerUsingGBL(add, this, 1, 10, 1, 1);
		addJComponentToContainerUsingGBL(remove, this, 2,10,1,1);
		addJComponentToContainerUsingGBL(edit, this, 3, 10, 1, 1);
		addJComponentToContainerUsingGBL(uploadCourses, this, 1, 20, 1, 1);
		addJComponentToContainerUsingGBL(textField, this, 2, 20, 1, 1);
		addJComponentToContainerUsingGBL(browse, this, 3, 20, 1, 1);
		addJComponentToContainerUsingGBL(back, this, 5, 30, 1, 1);
		addJComponentToContainerUsingGBL(registrarAdminPage, this, 6, 30, 1, 1);
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
