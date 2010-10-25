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
	public JPanel getEditScreen();
	/**
	 * Gets the GUI for adding an element
	 * @return JPanel containing all the needed components to add add element
	 */
	public JPanel getAddScreen();
	/**
	 * Gets the GUI for removing elements
	 * @return JPanel containing all the needed components to remove elements
	 */
	public JPanel getRemoveScreen();
}
