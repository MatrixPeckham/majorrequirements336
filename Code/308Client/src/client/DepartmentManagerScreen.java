package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DepartmentManagerScreen extends Screen implements ManagerScreen {
	
	private JLabel registrarAdminPage;
	private JButton viewCourses;
	private JButton addDepart;
	private JButton removeDepart;

	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = 2263628448346062920L;
	private AddDeptScreen addScreen = new AddDeptScreen();
	private EditDeptScreen editScreen = new EditDeptScreen();
	private RemoveDeptScreen remScreen = new RemoveDeptScreen();
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public DepartmentManagerScreen(ClientGUI gui) {
		super(gui);
		layoutGUI();
	}
	
	public void layoutGUI()
	{
		registrarAdminPage = new JLabel("Registrar Admin Page");
		registrarAdminPage.setFont(new Font("Times New Roman",1,72));
		viewCourses = new JButton("View/Edit Courses");
		viewCourses.setFont(new Font("Times New Roman",1,24));
		addDepart = new JButton("Add Department");
		addDepart.setFont(new Font("Times New Roman",1,24));
		removeDepart = new JButton("Remove Department");
		removeDepart.setFont(new Font("Times New Roman",1,24));
		
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
		addJComponentToContainerUsingGBL(addDepart, this, 2, 10, 1, 1);
		addJComponentToContainerUsingGBL(removeDepart, this, 3, 10, 1, 1);
	}

	@Override
	public void getScreen() {
		// TODO Auto-generated method stub
	}

	@Override
	public JPanel getAddScreen() {
		return addScreen;
	}

	@Override
	public JPanel getEditScreen() {
		return editScreen;
	}

	@Override
	public JPanel getRemoveScreen() {
		return remScreen;
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for adding departments
	 * @author Bill
	 *
	 */
	//TODO make this into the add screen GUI
	private class AddDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = 4099112144368019745L;
		
		private JLabel addDepartmentPage;
		private JLabel name;
		private JTextField nameField;
		private JButton add;
		
		AddDeptScreen()
		{
			layoutGUI();
		}
		
		public void layoutGUI()
		{
			addDepartmentPage = new JLabel("Add Department Page");
			addDepartmentPage.setFont(new Font("Times New Roman",1,72));
			name = new JLabel("Enter Name Of Department");
			name.setFont(new Font("Times New Roman",1,24));
			nameField = new JTextField(20);
			add = new JButton("Add");
			add.setFont(new Font("Times New Roman",1,24));
			
			GridBagLayout gbl = new GridBagLayout();
			this.setLayout(gbl);
			
			addJComponentToContainerUsingGBL(addDepartmentPage, this, 1, 1, 2, 1);
			addJComponentToContainerUsingGBL(name, this, 1, 10, 1, 1);
			addJComponentToContainerUsingGBL(nameField, this, 2, 10, 1, 1);
			addJComponentToContainerUsingGBL(add, this, 1, 20, 1, 1);
		}
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing departments
	 * @author Bill
	 *
	 */
	//TODO make this into the edit screen GUI
	private class EditDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861607293L;
		
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
		
		EditDeptScreen()
		{
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
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing departments
	 * @author Bill
	 *
	 */
	//TODO make this into the remove screen GUI
	private class RemoveDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618343723L;
		
	}
}
