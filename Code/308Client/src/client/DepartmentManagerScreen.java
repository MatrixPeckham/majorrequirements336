package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import server.Department;
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
        //error label for remove
        private JLabel error;
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = 2263628448346062920L;

	//these three are screens to return from manager screen methods
        private AddDeptScreen addScreen;
	private EditDeptScreen editScreen;
	private RemoveDeptScreen remScreen;

        /**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public DepartmentManagerScreen(ClientGUI gui) {
		super(gui);
                addScreen = new AddDeptScreen(gui);
                editScreen = new EditDeptScreen(gui);
                remScreen = new RemoveDeptScreen(gui);
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
                            frame.changeScreen(ClientGUI.COURSES, frame.getDepartment((String)table.getModel().getValueAt(table.getSelectedRow(), 0)));
			}
		});
		viewCourses.setFont(new Font("Times New Roman",1,24));
		addDepart = new JButton("Add Department");
		addDepart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeManageScreen(ClientGUI.CURR_ADD, null);
			}
		});
		addDepart.setFont(new Font("Times New Roman",1,24));
		removeDepart = new JButton("Remove Department");
		removeDepart.setFont(new Font("Times New Roman",1,24));
                removeDepart.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.removeDepartment((String)table.getModel().getValueAt(table.getSelectedRow(), 0));
                        getScreen(frame.getDepartments());
                    }
                });
		back=new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.changeScreen(ClientGUI.WELCOME, null);
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
	      
                table.setModel(new DefaultTableModel() {

                    @Override
                    public java.lang.Class<?> getColumnClass(int columnIndex) {
                        return getValueAt(0, columnIndex).getClass();
                    }
                    /**
                     *
                     */
                    private static final long serialVersionUID = -7810042264203030452L;
                });
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (String s : columnNames) {
                    model.addColumn(s);
                }
                for (Object[] o : data) {
                    model.addRow(o);
                }
                JScrollPane scrollPane = new JScrollPane(table);
                add(scrollPane);

                error = new JLabel("Error: Select Department To Remove From The"
                        + "Above Table");
                error.setFont(new Font("Times New Roman",1,12));
                error.setForeground(Color.red);
                error.setVisible(false);
		
                GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		addJComponentToContainerUsingGBL(registrarAdminPage, this, 1, 1, 3, 2);
		addJComponentToContainerUsingGBL(scrollPane, this, 1, 3, 4, 3);
		addJComponentToContainerUsingGBL(viewCourses, this, 1, 10, 1, 1);
		addJComponentToContainerUsingGBL(addDepart, this, 2, 10, 1, 1);
		addJComponentToContainerUsingGBL(removeDepart, this, 3, 10, 1, 1);
		addJComponentToContainerUsingGBL(back, this, 4, 10, 1, 1);
                addJComponentToContainerUsingGBL(error, this, 1, 15, 1, 1);
	}

	@Override
	public void getScreen(Object fillWith) {
            if(fillWith instanceof ArrayList){
                ArrayList<Department> depos = (ArrayList<Department>) fillWith;
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                int rows = model.getRowCount();
                for(int i = 0; i < rows; i++){
                    model.removeRow(0);
                }
                for (Department d : depos) {
                    String[] s = {d.getName()};
                    model.addRow(s);
                }


                
            }
        }

	@Override
	public Screen getAddScreen() {
		return addScreen;
	}

	@Override
	public Screen getEditScreen() {
		return editScreen;
	}

	@Override
	public Screen getRemoveScreen() {
		return remScreen;
	}

    @Override
    public boolean validateForm() {
        if(table.getSelectedRow()==-1)
        {
            error.setVisible(true);
            return false;
        }
        error.setVisible(false);
        return true;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for adding departments
	 * @author Bill
	 *
	 */
	private class AddDeptScreen extends Screen{

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
                //error label
                private JLabel error;

                /**
                 * constructor
                 */
		AddDeptScreen(ClientGUI gui)
		{
                    super(gui);
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
                                    if(validateForm()){
                                        Department d = new server.Department();
                                        d.setName(nameField.getText());
                                        frame.addDepartment(nameField.getName(), d);
                                        Object o = frame.getDepartments();
                                        frame.changeScreen(ClientGUI.DEPARTMENTS, o);
                                    }
				}
			});
                        error = new JLabel("Error: Please Enter Only Letters For The Department Name");
                        error.setFont(new Font("Times New Roman",1,12));
                        error.setForeground(Color.red);
                        error.setVisible(false);
			GridBagLayout gbl = new GridBagLayout();
			this.setLayout(gbl);
			
			addJComponentToContainerUsingGBL(addDepartmentPage, this, 1, 1, 2, 1);
			addJComponentToContainerUsingGBL(name, this, 1, 10, 1, 1);
			addJComponentToContainerUsingGBL(nameField, this, 2, 10, 1, 1);
			addJComponentToContainerUsingGBL(add, this, 1, 20, 1, 1);
                        addJComponentToContainerUsingGBL(error, this, 1, 30, 1, 1);
		}

        @Override
        public void getScreen(Object fillWith) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean validateForm() {
            String s = nameField.getText();
            for(int i = 0; i<s.length(); i++)
            {
                if(s.charAt(i)!=' ')
                {
                    if(s.charAt(i)<'A')
                    {
                        error.setVisible(true);
                        return false;
                    }
                    else if(s.charAt(i) > 'Z' && s.charAt(i) < 'a')
                    {
                        error.setVisible(true);
                        return false;
                    }
                    else if(s.charAt(i)>'z')
                    {
                        error.setVisible(true);
                        return false;
                    }
                }
            }
            error.setVisible(false);
            return true;
            //throw new UnsupportedOperationException("Not supported yet.");
        }
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing departments
	 * @author Bill
	 *
	 */
	private class EditDeptScreen extends Screen{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861607293L;

                EditDeptScreen(ClientGUI gui){
                    super(gui);
                }

        @Override
        public void getScreen(Object fillWith) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean validateForm() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing departments
	 * @author Bill
	 *
	 */
	private class RemoveDeptScreen extends Screen{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618343723L;

                RemoveDeptScreen(ClientGUI gui){
                    super(gui);
                }

        @Override
        public void getScreen(Object fillWith) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean validateForm() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
		
	}
}
