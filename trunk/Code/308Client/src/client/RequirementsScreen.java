package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Screen for requirements check requiremnts
 * @author JP
 *
 */
//TODO not sure where the admin changes major requriments
public class RequirementsScreen extends Screen {
	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -4598402945518018282L;
        //title label
	private JLabel checkRequirementsLabel;
        private JLabel htmlLabel;
        //table that holds the requirements
        private JScrollPane table1;
        //table for the specifics of the requirements
        private JTable table2;
        //back button
        private JButton back;
        //label for the requirements information
        private JLabel requirementInfo;
        //button to go to the suggest schedule page
        private JButton suggestSched;

	/**
	 * Constructor, lays out GUI including action listeners
	 * @param gui GUI for the parent screen
	 */
	public RequirementsScreen(ClientGUI gui) {
		super(gui);
		checkRequirementsLabel = new JLabel("Check Requirements Page");
                checkRequirementsLabel.setFont(new Font("Times New Roman",1,72));
                htmlLabel=new JLabel();
		
	    JScrollPane scrollPane = new JScrollPane(htmlLabel);


            requirementInfo = new JLabel("Required Information ");
            requirementInfo.setFont(new Font("Times New Roman",1,32));
            back = new JButton("Back");
            back.setFont(new Font("Times New Roman",1,24));
            back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeScreen(ClientGUI.CLASSES, frame.getStudentInfo());
			}
		});

            suggestSched = new JButton("Suggest Schedule");
            suggestSched.setFont(new Font("Times New Roman",1,24));

             suggestSched.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                            Object o = frame.generateSchedule();
				frame.changeScreen(ClientGUI.SCHEDULE, o);

			}
		});


            this.setLayout(new BorderLayout());
            JPanel south=new JPanel();
            south.setLayout(new GridBagLayout());
            this.add(checkRequirementsLabel, BorderLayout.NORTH);
            this.add(scrollPane,BorderLayout.CENTER);
            //addJComponentToContainerUsingGBL(requirementInfo, this, 1, 10, 1, 1);
            addJComponentToContainerUsingGBL(back, south, 1, 1, 1, 1);
            addJComponentToContainerUsingGBL(suggestSched, south, 2, 1, 1, 1);
            this.add(south, BorderLayout.SOUTH);
        }


	@Override
	public void getScreen(Object fillWith) {
            if(fillWith!=null)
		htmlLabel.setText(fillWith.toString());
            else
                htmlLabel.setText("Error");

	}

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
