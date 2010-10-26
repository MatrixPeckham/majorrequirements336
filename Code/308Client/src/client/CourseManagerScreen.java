package client;

import javax.swing.JPanel;

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
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public CourseManagerScreen(ClientGUI gui) {
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
		
	}
	/**
	 * Inner class the the outside need know nothing about
	 * it will be the screen for Editing Courses
	 * @author Bill
	 *
	 */
	//TODO make this into the edit screen GUI
	private class EditCouScreen extends JPanel{

		/**
		 * serial version ID that eclipse wants in all swing classes
		 */
		private static final long serialVersionUID = -3153844264861602293L;
		
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
