package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * Manager screen for the majors
 * @author Bill
 *
 */
public class MajorManagerScreen extends Screen implements ManagerScreen {

	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -747564907385911678L;
	private AddMajScreen addScreen = new AddMajScreen();
	private EditMajScreen editScreen = new EditMajScreen();
	private RemoveMajScreen remScreen = new RemoveMajScreen();
	
	private JLabel adminLabel;
	private JTable table;
	private JButton addButton;
	private JButton editButton;
	private JButton removeButton;
	private JButton editMajor;
	private JButton back;
	
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public MajorManagerScreen(ClientGUI gui) {
		super(gui);
		initGUI();
	}
	
	private void initGUI(){
		adminLabel = new JLabel("Department Administrator");
		adminLabel.setFont(new Font("Times New Roman",1,72));
		String[] columnNames = {"Major"};

		Object[][] data = {{"CSE"},{"ISE"}};
		
		table = new JTable(data, columnNames);
	    table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
	    table.setFillsViewportHeight(true);
	    JScrollPane scrollPane = new JScrollPane(table);
	    
	    addButton = new JButton("Add Major");
	    addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showInputDialog("Input Major Name");
			}
		});
	    editButton = new JButton("Edit Major");
	    editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Change Major Name");
			}
		});
	    removeButton = new JButton("Remove Major");
	    removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	    editMajor = new JButton("Edit Major Requiremnts");
	    editMajor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
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
	 * it will be the screen for adding Majors
	 * @author Bill
	 *
	 */
	//TODO make this into the add screen GUI
	private class AddMajScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = 4099112134368019745L;
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing Majors
	 * @author Bill
	 *
	 */
	//TODO make this into the edit screen GUI
	private class EditMajScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861607293L;
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for removing Majors
	 * @author Bill
	 *
	 */
	//TODO make this into the remove screen GUI
	private class RemoveMajScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3920339673618333723L;
		
	}
}
