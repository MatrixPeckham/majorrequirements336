package client;

import javax.swing.JPanel;
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
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public MajorManagerScreen(ClientGUI gui) {
		super(gui);
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
