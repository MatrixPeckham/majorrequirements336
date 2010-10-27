package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Welcome screen
 * @author Bill
 *
 */
public class WelcomeScreen extends Screen implements ManagerScreen {
	/**
	 * serial version ID that eclispe wants in all swing classes
	 */
	private static final long serialVersionUID = 4768193727341498557L;
	
	private JButton studentButton;
	private JButton adminButton;
	private JButton superAdminButton;
	
	/**
	 * Constructor
	 * @param gui GUI for parent Screen
	 */
	public WelcomeScreen(ClientGUI gui) {
		super(gui);
		initGUI();
	}
	
	private void initGUI(){
		studentButton = new JButton("Student");
		studentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.CLASSES);
			}
		});
		adminButton = new JButton("Department Administrator");
		adminButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.DEPARTMENTS);
			}
		});
		superAdminButton = new JButton("Registrar Administrator");
		superAdminButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.COURSES);
			}
		});
		addJComponentToContainerUsingGBL(studentButton, this, 1, 2, 1, 1);
		addJComponentToContainerUsingGBL(adminButton, this, 2, 2, 1, 1);
		addJComponentToContainerUsingGBL(superAdminButton, this, 3, 2, 1, 1);
	}

	@Override
	public void getScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public JPanel getAddScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel getEditScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel getRemoveScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}
