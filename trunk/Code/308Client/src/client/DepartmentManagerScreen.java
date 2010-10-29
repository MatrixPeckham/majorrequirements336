package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
/**
 *
 * @author JP & Bill
 */
public class DepartmentManagerScreen extends Screen implements ManagerScreen {
        //title label
	private JLabel registrarAdminPage;
        //view courses button
	private JButton viewCourses;
        //add department button
	private JButton addDepart;
        //remove department button
	private JButton removeDepart;
        // back button
	private JButton back;
        //table for departments
	private JTable table;
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = 2263628448346062920L;

	//these three are screens to return from manager screen methods
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
        /**
         * lays out GUI including action listeners
         */
	private void layoutGUI()
	{
		registrarAdminPage = new JLabel("Registrar Admin Page");
		registrarAdminPage.setFont(new Font("Times New Roman",1,72));
		viewCourses = new JButton("View/Edit Courses");
		viewCourses.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.COURSES);
			}
		});
		viewCourses.setFont(new Font("Times New Roman",1,24));
		addDepart = new JButton("Add Department");
		addDepart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_ADD);
			}
		});
		addDepart.setFont(new Font("Times New Roman",1,24));
		removeDepart = new JButton("Remove Department");
		removeDepart.setFont(new Font("Times New Roman",1,24));
		back=new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.changeScreen(ClientGUI.WELCOME);
			}
		});
		back.setFont(new Font("Times New Roman",1,24));
		
		String[] columnNames = {"Department"};

		Object[][] data = {
				{"Math"},{"Computer Science"},{"Journalism"},{"Information And Technology"},
				{"History"},{"Women Studies"},{"Biology"},{"Chemistry"},{"Physcis"}	
		};
		
		  table = new JTable(data, columnNames);
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
		addJComponentToContainerUsingGBL(back, this, 4, 10, 1, 1);
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
	private class AddDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = 4099112144368019745L;

                //title label
		private JLabel addDepartmentPage;
                //name label
		private JLabel name;
                //name field
		private JTextField nameField;
                //add button
		private JButton add;

                /**
                 * constructor
                 */
		AddDeptScreen()
		{
			layoutGUI();
		}

                //lays out GUI including action listeners
		public void layoutGUI()
		{
			addDepartmentPage = new JLabel("Add Department Page");
			addDepartmentPage.setFont(new Font("Times New Roman",1,72));
			name = new JLabel("Enter Name Of Department");
			name.setFont(new Font("Times New Roman",1,24));
			nameField = new JTextField(20);
			add = new JButton("Add");
			add.setFont(new Font("Times New Roman",1,24));
			add.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					DepartmentManagerScreen.this.frame.changeScreen(ClientGUI.DEPARTMENTS);
				}
			});
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
	private class EditDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861607293L;

	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing departments
	 * @author Bill
	 *
	 */
	private class RemoveDeptScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618343723L;
		
	}
}
