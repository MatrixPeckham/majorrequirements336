package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 * Classes manager screen
 * @author Bill
 *
 */
public class ClassesManagerScreen extends Screen implements ManagerScreen {
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -747564907385911673L;
	private AddClasScreen addScreen = new AddClasScreen();
	private EditClasScreen editScreen = new EditClasScreen();
	private RemoveClasScreen remScreen = new RemoveClasScreen();
	
	
	private JLabel studentPage;
	private JTable courses;
	private JButton addButton;
	private JButton editButton;
	private JButton removeButton;
	private JButton uploadButton;
	private JButton downloadButton;
	private JButton checkButton;
	private JButton generateButton;
	private JButton backButton;
	
	
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public ClassesManagerScreen(ClientGUI gui) {
		super(gui);
		initGUI();
	}

	private void initGUI(){
		studentPage=new JLabel("Student Page");
		studentPage.setFont(new Font("Times New Roman",1,72));
		String[] columnNames = {"Course","Grade","Transfer"};
		Object[][] data = {
				{"CSE 308","A",new Boolean(false)},{"CSE 381","B",new Boolean(false)},{"CSE 380","C",new Boolean(false)},{"CSE 220","A-",new Boolean(false)},
				{"CSE 114","I",new Boolean(false)},{"CSE 215","A",new Boolean(false)},{"CSE 219","C+",new Boolean(false)},{"CSE 110","B+",new Boolean(false)},
				{"MAT 127","D",new Boolean(false)}};
		courses = new JTable();
		courses.setPreferredScrollableViewportSize(new Dimension(1000, 100));
	    courses.setFillsViewportHeight(true);
	    courses.setModel(new DefaultTableModel(){
	    	@Override
	    	public java.lang.Class<?> getColumnClass(int columnIndex) {
	    		return getValueAt(0, columnIndex).getClass();
	    	}
			/**
			 * 
			 */
			private static final long serialVersionUID = -7810042264203030452L;
	    	
	    });
	    DefaultTableModel model = (DefaultTableModel)courses.getModel();
	    for(String s : columnNames){
	    	model.addColumn(s);
	    }
	    for(Object[] o : data){
	    	model.addRow(o);
	    }
	    
	    JScrollPane scrollPane = new JScrollPane(courses);
	    
	    addButton = new JButton("Add Course");
	    addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_ADD);
			}
		});
	    editButton = new JButton("Edit Course");
	    editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_EDIT);
			}
		});
	    removeButton = new JButton("Remove Course");
	    removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//frame.changeScreen(ClientGUI.CLASSES);
			}
		});
	    uploadButton = new JButton("Upload Courses");
	    
	    downloadButton = new JButton("Dowload Courses");
	    
	    checkButton = new JButton("Check Requirements");
	    
	    generateButton = new JButton("Generate Schedule");
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.WELCOME);
				
			}
		});
		this.setLayout(new GridBagLayout());
		addJComponentToContainerUsingGBL(studentPage, this, 1, 1, 4, 1);
		addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
		addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
		addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
		addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
		addJComponentToContainerUsingGBL(uploadButton, this, 1, 5, 1, 1);
		addJComponentToContainerUsingGBL(downloadButton, this, 3, 5, 1, 1);
		addJComponentToContainerUsingGBL(checkButton, this, 1, 6, 1, 1);
		addJComponentToContainerUsingGBL(generateButton, this, 3, 6, 1, 1);
		addJComponentToContainerUsingGBL(backButton, this, 5, 7, 1, 1);
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
	 * it will be the screen for adding Classess
	 * @author Bill
	 *
	 */
	//TODO make this into the add screen GUI
	private class AddClasScreen extends JPanel{

		JLabel addPage;
		JTable courses;
		JLabel gradeL;
		JComboBox gradeBox;
		JCheckBox transBox;
		JButton ok;
		JButton cancel;
		
		public AddClasScreen(){
			initGUI();
		}
		private void initGUI(){
			addPage = new JLabel("Add Courses Page");
			addPage.setFont(new Font("Times New Roman",1,72));
			String[] columnNames = {"Department","ID","Credits","Prereq"};
			Object[][] data = {
					{"CSE","308","3","CSE 219"},{"CSE","381","3","CSE 219"},{"CSE","380","3","CSE 219"},{"CSE","220","3","CSE 219"},
					{"CSE","114","3","CSE 219"},{"CSE","215","3","CSE 219"},{"CSE","219","3","CSE 219"},{"CSE","110","3","CSE 219"},
					{"MAT","127","3","CSE 219"}};
			courses = new JTable();
			courses.setPreferredScrollableViewportSize(new Dimension(1000, 100));
		    courses.setFillsViewportHeight(true);
		    courses.setModel(new DefaultTableModel(){
		    	@Override
		    	public java.lang.Class<?> getColumnClass(int columnIndex) {
		    		return getValueAt(0, columnIndex).getClass();
		    	}
				/**
				 * 
				 */
				private static final long serialVersionUID = -7810042264203030452L;
		    	
		    });
		    DefaultTableModel model = (DefaultTableModel)courses.getModel();
		    for(String s : columnNames){
		    	model.addColumn(s);
		    }
		    for(Object[] o : data){
		    	model.addRow(o);
		    }
		    
		    JScrollPane scrollPane = new JScrollPane(courses);
			gradeL = new JLabel("Grade");
			gradeBox = new JComboBox(new String[]{"A","A-","B+","B","B-","C+","C","C-","D+","D","D-","F","I"});
			transBox = new JCheckBox("Transfer");
			ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.changeScreen(ClientGUI.CLASSES);
				}
			});
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.changeScreen(ClientGUI.CLASSES);
				}
			});
			setLayout(new GridBagLayout());
			addJComponentToContainerUsingGBL(addPage, this, 1, 1, 4, 1);
			addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
			addJComponentToContainerUsingGBL(gradeL, this, 1, 4, 1, 1);
			addJComponentToContainerUsingGBL(transBox, this, 3, 4, 1, 1);
			addJComponentToContainerUsingGBL(gradeBox, this, 2, 4, 1, 1);
			addJComponentToContainerUsingGBL(ok, this, 1, 5, 1, 1);
			addJComponentToContainerUsingGBL(cancel, this, 3, 5, 1, 1);
		}
		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = 4099112134368019743L;
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing Classess
	 * @author Bill
	 *
	 */
	//TODO make this into the edit screen GUI
	private class EditClasScreen extends AddClasScreen{

		public EditClasScreen(){
			super();
			addPage.setText("Edit Course Page");
		}
		
		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153834364861607293L;
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing Classes
	 * @author Bill
	 *
	 */
	//TODO make this into the remove screen GUI
	private class RemoveClasScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618333223L;
		
	}
}