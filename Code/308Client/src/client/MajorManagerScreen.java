package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import server.Course;
import server.Major;
import server.Requirement;

/**
 * Manager screen for the department Admin page
 * @author Bill
 *
 */
public class MajorManagerScreen extends Screen implements ManagerScreen {

    /**
     * serial version ID that eclipse wants in all swing classes
     */
    private static final long serialVersionUID = -747564907385911678L;
    //these three are for the manager screen get methods
    private AddMajScreen addScreen;
    private EditMajScreen editScreen;
    private RemoveMajScreen remScreen;
    
    //title label
    private JLabel adminLabel;
    //table of majors
    private JTable table;
    //button to add a major
    private JButton addButton;
    //button to edit the major name
    private JButton editButton;
    //button to remove a major
    private JButton removeButton;
    //button to edit major requirments
    private JButton editMajor;
    //back button
    private JButton back;
    //error button
    private JLabel error;

    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public MajorManagerScreen(ClientGUI gui) {
        super(gui);
        addScreen = new AddMajScreen(gui);
        editScreen = new EditMajScreen(gui);
        remScreen = new RemoveMajScreen(gui);
        initGUI();
    }
    /*
     * lays out the GUI including action listneners
     */
    private void initGUI() {
        adminLabel = new JLabel("Department Administrator");
        adminLabel.setFont(new Font("Times New Roman", 1, 72));
        String[] columnNames = {"Major"};

        Object[][] data = {{"CSE"}, {"ISE"}};

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        addButton = new JButton("Add Major");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s = JOptionPane.showInputDialog("Input Major Name");
                if(s.length()!=3)
                {
                    JOptionPane.showMessageDialog(frame, "Please Enter Three "
                            + "Letters To Represent The Major", "Invalid Entry",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(int i=0; i<3; i++)
                {
                    if(s.charAt(i)<'A'||s.charAt(i)>'Z')
                    {
                        JOptionPane.showMessageDialog(frame, "Please Enter Three Capital"
                            + "Letters To Represent The Major", "Invalid Entry",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Major m = new Major();
                frame.addMajor(m);
                Object o = frame.getDepartment(frame.getCurrentDepartment());
                getScreen(o);
            }
        });
        editButton = new JButton("Edit Major");
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Change Major Name");
                if(s.length()!=3)
                {
                    JOptionPane.showMessageDialog(frame, "Please Enter Three "
                            + "Letters To Represent The Major", "Invalid Entry",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(int i=0; i<3; i++)
                {
                    if(s.charAt(i)<'A'||s.charAt(i)>'Z')
                    {
                        JOptionPane.showMessageDialog(frame, "Please Enter Three Capital "
                            + "Letters To Represent The Major", "Invalid Entry",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Major m = new Major();
                frame.addMajor(m);
                Object o = frame.getDepartment(frame.getCurrentDepartment());
                getScreen(o);
            }
        });
        removeButton = new JButton("Remove Major");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.removeMajor((String)table.getModel().getValueAt(table.getSelectedRow(), 1));
                Object o = frame.getDepartment(frame.getCurrentDepartment());
                getScreen(o);
            }
        });
        editMajor = new JButton("Edit Major Requiremnts");
        editMajor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    Major m = frame.getMajor((String)table.getModel().getValueAt(table.getSelectedRow(), 0));
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, m);
                }
            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.changeScreen(ClientGUI.WELCOME, null);
            }
        });
        error = new JLabel("Error: Please select a major from the above table");
        error.setFont(new Font("Times New Roman",1,12));
        error.setForeground(Color.red);
        error.setVisible(false);
        addJComponentToContainerUsingGBL(adminLabel, this, 1, 1, 4, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(editMajor, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(back, this, 3, 5, 1, 1);
        addJComponentToContainerUsingGBL(error, this, 1, 6, 1, 1);
    }

    @Override
    public void getScreen(Object fillWith) {
        // TODO Auto-generated method stub
    }

    @Override
    public Screen getAddScreen() {
        return addScreen;
    }

    @Override
    public Screen getEditScreen() {
        return editScreen;
    }

    @Override
    public Screen getRemoveScreen() {
        return remScreen;
    }

    @Override
    public boolean validateForm() {
        if(table.getSelectedRow()==-1)
        {
            error.setVisible(true);
            return false;
        }
        error.setVisible(false);
        return true;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for adding Major requirements
     * @author Bill
     *
     */
    //TODO make this into the add screen GUI
    private class AddMajScreen extends Screen {
        //title label
        JLabel addPage;
        //label for course table
        JLabel courseL;
        //course table
        JTable courses;
        //button to add a course to working sequence
        JButton addButton;
        //button to remove a course from working sequence
        JButton remButton;
        //label for the current sequence list
        JLabel currL;
        //current sequence list
        JList currList;
        //checkbox to make the current sequence excluded
        JCheckBox exclude;
        //button adds current sequence the requirement
        JButton finishButton;
        //label for the sequences list
        JLabel seqL;
        //list for the sequences
        JList seqList;
        //min number of sequences for this requirement
        JLabel minSeqL;
        //spinner for the number of sequences for this requirement
        JSpinner seqS;
        //info title label
        JLabel infoL;
        //label for name field
        JLabel nameL;
        //name field
        JTextField nameF;
        //label for min gpa field
        JLabel minGPAL;
        //min gpa field
        JTextField minGPAF;
        //label min upper courses for requirement
        JLabel minUpperL;
        //min upper courses for requirement spinner
        JSpinner minUpperS;
        //ok button
        JButton ok;
        //cancel button
        JButton cancel;
        /*
         * default constructor
         * makes GUI
         */
        public AddMajScreen(ClientGUI gui) {
            super(gui);
            initGUI();
        }
        /*
         * sets up the GUI including at this time most action listneners
         */
        private void initGUI() {
            addPage = new JLabel("Add Requirement Page");
            addPage.setFont(new Font("Times New Roman", 1, 72));
            courseL = new JLabel("Courses");
            String[] columnNames = {"Course"};
            Object[][] data = {
                {"CSE 101"}, {"CSE 900"}, {"CSE 282"}};
            courses = new JTable();
            courses.setPreferredScrollableViewportSize(new Dimension(100, 100));
            courses.setFillsViewportHeight(true);
            courses.setModel(new DefaultTableModel() {

                @Override
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }
                /**
                 *
                 */
                private static final long serialVersionUID = -7810042264203030452L;
            });
            DefaultTableModel model = (DefaultTableModel) courses.getModel();
            for (String s : columnNames) {
                model.addColumn(s);
            }
            for (Object[] o : data) {
                model.addRow(o);
            }

            JScrollPane courseScroll = new JScrollPane(courses);

            addButton = new JButton("Add ->");
            addButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //NO COMMUNICATION
                    //DO LATER
                }
            });
            remButton = new JButton("<- Remove");
            remButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //NO COMMUNICATION
                    //DO LATER
                }
            });
            currL = new JLabel("Current Sequence");
            currList = new JList();
            JScrollPane curScroll = new JScrollPane(currList);
            exclude = new JCheckBox("Exclude these courses");
            finishButton = new JButton("Finish ->");
            seqL = new JLabel("Requirement Sequences");
            seqList = new JList();
            JScrollPane seqScroll = new JScrollPane(seqList);
            minSeqL = new JLabel("Min Sequences");
            seqS = new JSpinner();
            infoL = new JLabel("Requirement Info");
            infoL.setFont(new Font("Times New Roman",1,72/2));
            nameL = new JLabel("Name");
            nameF = new JTextField(10);
            minGPAL = new JLabel("Min GPA");
            minGPAF = new JTextField(10);
            minUpperL = new JLabel("Min Upper Division");
            minUpperS = new JSpinner();
            ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        frame.addRequirement(makeReq(), nameF.getText());
                        Object o = frame.getCurrentMajor();
                        frame.changeManageScreen(ClientGUI.CURR_EDIT, o);
                    }
                }

            });
            cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Object o = frame.getCurrentMajor();
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, null);
                }
            });
            setLayout(new GridBagLayout());

            addJComponentToContainerUsingGBL(addPage, this, 1, 1, 6, 1);
            addJComponentToContainerUsingGBL(courseL, this, 1, 2, 1, 1);
            addJComponentToContainerUsingGBL(currL, this, 3, 2, 2, 1);
            addJComponentToContainerUsingGBL(seqL, this, 6, 2, 1, 1);
            addJComponentToContainerUsingGBL(courseScroll, this, 1, 3, 1, 4);
            addJComponentToContainerUsingGBL(addButton, this, 2, 4, 1, 1);
            addJComponentToContainerUsingGBL(remButton, this, 2, 5, 1, 1);
            addJComponentToContainerUsingGBL(curScroll, this, 3, 3, 2, 4);
            addJComponentToContainerUsingGBL(finishButton, this, 5, 4, 1, 1);
            addJComponentToContainerUsingGBL(seqScroll, this, 6, 3, 1, 4);
            addJComponentToContainerUsingGBL(exclude, this, 3, 7, 1, 1);
            addJComponentToContainerUsingGBL(seqS, this, 6, 7, 1, 1);

            addJComponentToContainerUsingGBL(infoL, this, 3, 8, 2, 1);
            addJComponentToContainerUsingGBL(nameL, this, 3, 9, 1, 1);
            addJComponentToContainerUsingGBL(nameF, this, 4, 9, 1, 1);
            addJComponentToContainerUsingGBL(minGPAL, this, 3, 10, 1, 1);
            addJComponentToContainerUsingGBL(minGPAF, this, 4, 10, 1, 1);
            addJComponentToContainerUsingGBL(minUpperL, this, 3, 11, 1, 1);
            addJComponentToContainerUsingGBL(minUpperS, this, 4, 11, 1, 1);
            addJComponentToContainerUsingGBL(ok, this, 3, 12, 1, 1);
            addJComponentToContainerUsingGBL(cancel, this, 4, 12, 1, 1);


        }
        private Requirement makeReq() {
                return null;
        }
        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = 4099112134368019745L;

        @Override
        public void getScreen(Object fillWith) {
            DefaultTableModel model = (DefaultTableModel) courses.getModel();
            ArrayList<Course> courses = frame.getAllCourses();
            int rows = model.getRowCount();
            for(int i = 0; i < rows; i++){
                model.removeRow(0);
            }
            if(courses!=null){
                for(Course c : courses){
                    Object[] o = {c.getId()};
                    model.addRow(o);
                }
            }
        }

        @Override
        public boolean validateForm() {
            return true;
        }
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for Editing Major requirements
     * @author Bill
     *
     */
    private class EditMajScreen extends Screen {
        //title screen
        JLabel title;
        //table of requirements
        JTable table;
        //add requirement button
        JButton addReq;
        //edit requirement button
        JButton editReq;
        //remove requirement button
        JButton remReq;
        JButton back;
        /*
         * constructor sets up GUI
         */
        public EditMajScreen(ClientGUI gui) {
            super(gui);
            initGUI();
        }
        /*
         * sets up GUI indluding action listeners
         */
        private void initGUI() {
            title = new JLabel("Requirments");
            title.setFont(new Font("Times New Roman", 1, 72));
            String[] columnNames = {"Requirements"};

            Object[][] data = {{"REQ 1"}, {"REQ 2"}, {"..."}};

            table = new JTable(data, columnNames);
            table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            addReq = new JButton("Add");
            addReq.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeManageScreen(ClientGUI.CURR_ADD, null);
                }
            });
            editReq = new JButton("Edit");
            editReq.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        frame.changeManageScreen(ClientGUI.CURR_ADD, table.getModel().getValueAt(table.getSelectedRow(), 0));
                    }
                }
            });
            remReq = new JButton("Remove");
            back = new JButton("Back");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeScreen(ClientGUI.MAJORS, frame.getDepartment(frame.getCurrentDepartment()));
                }
            });
            this.setLayout(new GridBagLayout());
            addJComponentToContainerUsingGBL(title, this, 1, 1, 5, 1);
            addJComponentToContainerUsingGBL(table, this, 1, 2, 5, 2);
            addJComponentToContainerUsingGBL(addReq, this, 2, 4, 1, 1);
            addJComponentToContainerUsingGBL(editReq, this, 3, 4, 1, 1);
            addJComponentToContainerUsingGBL(remReq, this, 4, 4, 1, 1);
            addJComponentToContainerUsingGBL(back, this, 5, 5, 1, 1);
        }
        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3153844264861607293L;

        @Override
        public void getScreen(Object fillWith) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean validateForm() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for removing Majors
     * @author Bill
     *
     */
    private class RemoveMajScreen extends Screen {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3920339673618333723L;

        RemoveMajScreen(ClientGUI gui){
            super(gui);
        }

        @Override
        public void getScreen(Object fillWith) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean validateForm() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
