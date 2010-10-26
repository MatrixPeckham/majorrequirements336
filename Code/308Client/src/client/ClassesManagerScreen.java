package client;

import javax.swing.JPanel;
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
	/**
	 * constructor
	 * @param gui passed to super class Screen
	 */
	public ClassesManagerScreen(ClientGUI gui) {
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
	 * it will be the screen for adding Classess
	 * @author Bill
	 *
	 */
	//TODO make this into the add screen GUI
	private class AddClasScreen extends JPanel{

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
	private class EditClasScreen extends JPanel{

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
