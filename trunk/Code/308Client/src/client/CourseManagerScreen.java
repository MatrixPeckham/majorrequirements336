package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Manager for the courses
 * @author Bill
 *
 */
public class CourseManagerScreen extends Screen implements ManagerScreen {
	
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -747564902385911678L;
	private AddCouScreen addScreen = new AddCouScreen();
	private EditCouScreen editScreen = new EditCouScreen();
	private RemoveCouScreen remScreen = new RemoveCouScreen();
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
	
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public CourseManagerScreen(ClientGUI gui) {
		super(gui);
		layoutGUI();
	}
	
	public void layoutGUI()
	{

		editDepartmentPage = new JLabel("Edit Department Page");
		editDepartmentPage.setFont(new Font("Times New Roman",1,72));
		coursesForDepartment = new JLabel("Courses For Department");
		edit = new JButton("Edit Course");
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_EDIT);
			}
		});
		remove = new JButton("Remove Course");
		add = new JButton("Add Course");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_ADD);
			}
		});
		uploadCourses = new JButton("Upload Courses");
		browse = new JButton("Browse");
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.WELCOME);
			}
		});
		registrarAdminPage = new JButton("Registrar Admin Page");
		registrarAdminPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.DEPARTMENTS);
			}
		});
		textField = new JTextField(20);

		
		String[] columnNames = {"Courses"};

		Object[][] data = {
				{"CSE 308"},{"CSE 381"},{"CSE 380"},{"CSE 220"},
				{"CSE 114"},{"CSE 215"},{"CSE 219"},{"CSE 110"},
				{"MAT 127"}};
		
		table = new JTable(data, columnNames);
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
	 * it will be the screen for adding Courses
	 * @author Bill
	 *
	 */
	//TODO make this into the add screen GUI
	private class AddCouScreen extends JPanel{
		
		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = 4099112134368019743L;
		protected JLabel addL;
		private JLabel nameL;
		private JTextField nameF;
		private JLabel numL;
		private JTextField numF;
		private JLabel deptL;
		private JTextField deptF;
		private JLabel descL;
		private JTextArea descF;
		private JTable prereq;
		protected JButton ok;
		private JButton back;
		
		public AddCouScreen(){
			initGUI();
		}
		
		private void initGUI(){
			addL = new JLabel("Add Course Page");
			addL.setFont(new Font("Times New Roman", 1, 72));
			nameL = new JLabel("Name");
			nameF = new JTextField(10);
			numL = new JLabel("Number");
			numF = new JTextField(10);
			deptL = new JLabel("Department");
			deptF = new JTextField(10);
			descL = new JLabel("Description");
			descF = new JTextArea(10, 5);
			ok = new JButton("Add");
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.changeScreen(ClientGUI.COURSES);
				}
			});
			back = new JButton("Cancel");
			back.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.changeScreen(ClientGUI.COURSES);
				}
			});
			String[] columnNames = {"Course","Select"};
			Object[][] data = {
					{"CSE 308",new Boolean(false)},{"CSE 381",new Boolean(false)},{"CSE 380",new Boolean(false)},{"CSE 220",new Boolean(false)},
					{"CSE 114",new Boolean(false)},{"CSE 215",new Boolean(false)},{"CSE 219",new Boolean(false)},{"CSE 110",new Boolean(false)},
					{"MAT 127",new Boolean(false)}};
			prereq = new JTable();
			prereq.setPreferredScrollableViewportSize(new Dimension(200, 400));
		    prereq.setFillsViewportHeight(true);
		    prereq.setModel(new DefaultTableModel(){
		    	@Override
		    	public java.lang.Class<?> getColumnClass(int columnIndex) {
		    		return getValueAt(0, columnIndex).getClass();
		    	}
				/**
				 * 
				 */
				private static final long serialVersionUID = -7810042264203030452L;
		    	
		    });
		    DefaultTableModel model = (DefaultTableModel)prereq.getModel();
		    for(String s : columnNames){
		    	model.addColumn(s);
		    }
		    for(Object[] o : data){
		    	model.addRow(o);
		    }
		    this.setLayout(new GridBagLayout());
		    JScrollPane scrollPane = new JScrollPane(prereq);
		    
		    addJComponentToContainerUsingGBL(addL, this, 1, 1, 6, 1);
		    addJComponentToContainerUsingGBL(nameL, this, 1, 2, 1, 1);
		    addJComponentToContainerUsingGBL(nameF, this, 2, 2, 1, 1);
		    addJComponentToContainerUsingGBL(numL, this, 1, 3, 1, 1);
		    addJComponentToContainerUsingGBL(numF, this, 2, 3, 1, 1);
		    addJComponentToContainerUsingGBL(deptL, this, 1, 4, 1, 1);
		    addJComponentToContainerUsingGBL(deptF, this, 2, 4, 1, 1);
		    addJComponentToContainerUsingGBL(descL, this, 1, 5, 1, 1);
		    addJComponentToContainerUsingGBL(new JScrollPane(descF), this, 2, 5, 2, 1);
		    addJComponentToContainerUsingGBL(ok, this, 2, 6, 1, 1);
		    addJComponentToContainerUsingGBL(back, this, 3, 6, 1, 1);
		    addJComponentToContainerUsingGBL(scrollPane, this, 4, 2, 2, 5);


		}
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing Courses
	 * @author Bill
	 *
	 */
	//TODO make this into the edit screen GUI
	private class EditCouScreen extends AddCouScreen{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861602293L;
		public EditCouScreen(){
			super();
			addL.setText("Edit Course Screen");
			ok.setText("Save");
		}
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing Courses
	 * @author Bill
	 *
	 */
	//TODO make this into the remove screen GUI
	private class RemoveCouScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618233723L;
		
	}

}
