package client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Abstract Screen class all GUI screens extend it
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
	 * Constructor that makes sure all subclasses will
         * pass a client GUI to this one
	 * 
	 * @param gui
	 *            the frame
	 */
	public Screen(ClientGUI gui) {
		frame = gui;
		this.setLayout(new GridBagLayout());
	}

	/**
	 * at the time of fleshing out the classes i'm not sure what this method is
	 * supposed to do it's just a placeholder
	 */
	public abstract void getScreen(Object fillWith);

        /**
         * Helper method that is used to add a component
         * to a container using grid bag layout
         * @param jc component to add
         * @param c container to add to
         * @param x gridx of the component
         * @param y gridy of the component
         * @param w width in grid cells of the component
         * @param h height in grid cells of the component
         */
	public static void addJComponentToContainerUsingGBL(JComponent jc, Container c,
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
        JFileChooser fc = new JFileChooser();
        public File getFile(boolean open){
            int i = open ? fc.showOpenDialog(frame) : fc.showSaveDialog(frame);
            if(i==JFileChooser.APPROVE_OPTION){
                return fc.getSelectedFile();
            }
            return null;
        }
        /**
         * validates the form fields
         * @return true if form is correctly filled out false otherwise
         */
        public abstract boolean validateForm();
}
