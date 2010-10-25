package client;

import javax.swing.JPanel;
/**
 * Abstract Screen class
 * @author Bill
 *
 */
public abstract class Screen extends JPanel {
	/**
	 * just a serial version id that eclipse wants in swing classes
	 */
	private static final long serialVersionUID = -3109942549502954841L;
	//holds the GUI
	private ClientGUI frame;
	/**
	 * Constructor
	 * @param gui the frame
	 */
	public Screen(ClientGUI gui){
		frame=gui;
		//TODO just here to make a warning go away
		if(frame==null)return;
	}
	/**
	 * at the time of fleshing out the classes
	 * i'm not sure what this method is supposed
	 * to do
	 */
	public abstract void getScreen();
	
	
}
