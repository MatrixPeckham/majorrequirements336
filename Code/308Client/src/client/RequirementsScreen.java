package client;

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
        //table that holds the requirements
        private JTable table1;
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
                String[] columnNames = {"Requirement Name", "Minimum GPA", "Upper Division Credits", "Minimum Resident Credits",  "Completed"};

                Object[][] data = {
				{"Introduction",'C',"100","50",new Boolean(false)},{"Writing",'C',"32","32",new Boolean(false)}
                ,{"Math Courses",'C',"40","30",new Boolean(false)},{"Science Courses",'C',"40","30",new Boolean(false)},{"Upper Division",'C',"32","29",new Boolean(false)}};
                table1 = new JTable();
		table1.setPreferredScrollableViewportSize(new Dimension(1000, 100));
                table1.setFillsViewportHeight(true);
                table1.setModel(new DefaultTableModel(){
	    	@Override
	    	public java.lang.Class<?> getColumnClass(int columnIndex) {
	    		return getValueAt(0, columnIndex).getClass();
	    	}
			/**
			 *
			 */
			private static final long serialVersionUID = -7810042264203030452L;

	    });
              DefaultTableModel model = (DefaultTableModel)table1.getModel();
	    for(String s : columnNames){
	    	model.addColumn(s);
	    }
	    for(Object[] o : data){
	    	model.addRow(o);
	    }

	    JScrollPane scrollPane = new JScrollPane(table1);

             String[] columnNames2 = {"Course Name","Grade Earned","Completed"};

             Object[][] data2 = {
				{"CSE 308","A",new Boolean(false)},{"CSE 381","B",new Boolean(false)},{"CSE 380","C",new Boolean(false)},{"CSE 220","A-",new Boolean(false)},
				{"CSE 114","I",new Boolean(false)},{"CSE 215","A",new Boolean(false)},{"CSE 219","C+",new Boolean(false)},{"CSE 110","B+",new Boolean(false)},
				{"MAT 127","D",new Boolean(false)}};

              table2 = new JTable();
		table2.setPreferredScrollableViewportSize(new Dimension(500, 50));
                table2.setFillsViewportHeight(true);
                table2.setModel(new DefaultTableModel(){
	    	@Override
	    	public java.lang.Class<?> getColumnClass(int columnIndex) {
	    		return getValueAt(0, columnIndex).getClass();
	    	}
			/**
			 *
			 */
			private static final long serialVersionUID = -7810042264203030452L;

	    });
              DefaultTableModel model2 = (DefaultTableModel)table2.getModel();
	    for(String s : columnNames2){
	    	model2.addColumn(s);
	    }
	    for(Object[] o : data2){
	    	model2.addRow(o);
	    }

	    JScrollPane scrollPane2 = new JScrollPane(table2);

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


            this.setLayout(new GridBagLayout());
            addJComponentToContainerUsingGBL(checkRequirementsLabel, this, 1, 1, 4, 1);
            addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
            addJComponentToContainerUsingGBL(requirementInfo, this, 1, 10, 1, 1);
            addJComponentToContainerUsingGBL(scrollPane2, this, 1, 20, 1, 2);
            addJComponentToContainerUsingGBL(back, this, 3, 30, 1, 1);
            addJComponentToContainerUsingGBL(suggestSched, this, 4, 30, 1, 1);
        }


	@Override
	public void getScreen(Object fillWith) {
		// TODO Auto-generated method stub

	}

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
