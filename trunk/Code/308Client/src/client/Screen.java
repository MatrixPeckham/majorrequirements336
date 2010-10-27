package client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Abstract Screen class
 * 
 * @author Bill
 * 
 */
public abstract class Screen extends JPanel {
	/**
	 * just a serial version id that eclipse wants in swing classes
	 */
	private static final long serialVersionUID = -3109942549502954841L;
	// holds the GUI
	protected ClientGUI frame;

	/**
	 * Constructor
	 * 
	 * @param gui
	 *            the frame
	 */
	public Screen(ClientGUI gui) {
		frame = gui;
		this.setLayout(new GridBagLayout());
		// TODO just here to make a warning go away
		if (frame == null)
			return;
	}

	/**
	 * at the time of fleshing out the classes i'm not sure what this method is
	 * supposed to do
	 */
	public abstract void getScreen();

	public void addJComponentToContainerUsingGBL(JComponent jc, Container c,
			int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
}
