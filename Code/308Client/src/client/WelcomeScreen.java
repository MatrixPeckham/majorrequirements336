package client;

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

	/**
	 * Constructor
	 * @param gui GUI for parent Screen
	 */
	public WelcomeScreen(ClientGUI gui) {
		super(gui);
		// TODO Auto-generated constructor stub
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
