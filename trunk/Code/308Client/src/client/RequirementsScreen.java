package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Screen for requirements check requiremnts
 * @author Bill
 *
 */
//TODO not sure where the admin changes major requriments
public class RequirementsScreen extends Screen {
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -4598402945518018282L;

	
	
	/**
	 * Constructor
	 * @param gui GUI for the parent screen
	 */
	public RequirementsScreen(ClientGUI gui) {
		super(gui);
                initGUI();
        }
        
        private void initGUI(){
            JLabel lab = new JLabel("This page needs a redesign");
            JButton back = new JButton("Back");
            back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.CLASSES);
            }
        });
        JPanel pane = new JPanel();
        pane.add(lab);
        pane.add(back);
        this.add(pane);
        }


	@Override
	public void getScreen() {
		// TODO Auto-generated method stub

	}

}
