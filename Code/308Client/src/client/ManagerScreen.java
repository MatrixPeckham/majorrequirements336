package client;

import javax.swing.JPanel;

/**
 * Interface for all stuff that needs
 * edit, add and remove screens
 * @author Bill
 *
 */
public interface ManagerScreen {
	/**
	 * Gets the GUI for editing an element
	 * @return JPanel containing all the needed components to edit an element
	 */
	public Screen getEditScreen();
	/**
	 * Gets the GUI for adding an element
	 * @return JPanel containing all the needed components to add add element
	 */
	public Screen getAddScreen();
	/**
	 * Gets the GUI for removing elements
         * not used often
	 * @return JPanel containing all the needed components to remove elements
	 */
	public Screen getRemoveScreen();
}
