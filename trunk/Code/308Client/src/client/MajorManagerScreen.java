package client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import server.Commands;
import server.Course;
import server.CourseGroup;
import server.Department;
import server.Grade;
import server.Major;
import server.Requirement;
import server.User;

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
    private JTable majors;
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
    //upload button
    private JButton upload;
    //download button
    private JButton download;
    //error button
    private JLabel error;
    //log out button
    private JButton logout;
    //Department Combo Box
    private JComboBox currentDepartment;
    //Label for department combo box
    private JLabel departmentLabel;

    private DeptActList deptComboActList;
    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public MajorManagerScreen(ClientGUI gui) {
        super(gui);
        addScreen = new AddMajScreen(gui);
        editScreen = new EditMajScreen(gui);
        remScreen = new RemoveMajScreen(gui);
        deptComboActList = new DeptActList();
        initGUI();
    }
    /*
     * lays out the GUI including action listneners
     */
    private void initGUI() {
        adminLabel = new JLabel("Department Administrator - " + frame.getCurrentDepartment());
        adminLabel.setFont(new Font("Times New Roman", 1, 72));
        String[] columnNames = {"Major"};

        //Object[][] data = {{"CSE"}, {"ISE"}};
        Object [][] data = {};

        majors = new JTable();
        majors.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        majors.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(majors);
        majors.setModel(new DefaultTableModel() {

            public boolean isCellEditable(int row, int col) {
                return false;
            }
            @Override
            public java.lang.Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
            /**
             *
             */
            private static final long serialVersionUID = -7810042264203030452L;
        });
        DefaultTableModel model = (DefaultTableModel) majors.getModel();
        for (String s : columnNames) {
            model.addColumn(s);
        }
        for (Object[] o : data) {
            model.addRow(o);
        }

        addButton = new JButton("Add Major");
        addButton.setToolTipText("Add A Major");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s = JOptionPane.showInputDialog("Input Major Name");
                if(s==null||s.length()!=3)
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
                m.setId(s);
                m.setDepartment(frame.getCurrentDepartment());
                frame.addMajor(m);
                Department d = frame.getDepartment(frame.getCurrentDepartment());
                Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                getScreen(o);
            }
        });
        editButton = new JButton("Edit Major");
        editButton.setToolTipText("Edit Major's Name");
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
             try {
                Major m = frame.getMajor((String)majors.getModel().getValueAt(majors.getSelectedRow(), 0));
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
                frame.editMajor(m,s);
                Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                getScreen(o);
             }
             catch(ArrayIndexOutOfBoundsException aioobe){
                    JOptionPane.showMessageDialog(null, "You need to select a major to edit");
             }
        }});
        removeButton = new JButton("Remove Major");
        removeButton.setToolTipText("Remove Selected Major From Table");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              try   {
                frame.removeMajor((String)majors.getModel().getValueAt(majors.getSelectedRow(), 0));
                Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                getScreen(o);
              }
              catch(ArrayIndexOutOfBoundsException aioobe){
                    JOptionPane.showMessageDialog(null, "You need to select a major to remove");
              }
            }
        });
        editMajor = new JButton("Edit Major Requiremnts");
        editMajor.setToolTipText("Go To Requirements Page");
        editMajor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateForm())
                {
                    Major m = frame.getMajor((String)majors.getModel().getValueAt(majors.getSelectedRow(), 0));
                    frame.changeMajor(m);
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, m);
                }
            }
        });

        upload = new JButton("Upload Requirements");
        upload.setToolTipText("Upload Requirements From A File");
        upload.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = getFile(true);
                    if(f!=null) {
                        frame.uploadFile(f, Commands.UPLOADFILE);
                        frame.changeScreen(ClientGUI.MAJORS, frame.getDepartment(frame.getCurrentDepartment()).getMajors());
                        error.setVisible(false);
                    }
                }

            });

        download = new JButton("Download Requirements");
        download.setToolTipText("Download Requirements From A File");
        download.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                frame.downloadFile(getFile(false),Commands.DOWNLOAD_REQ);
                }

        });
        back = new JButton("Back");
        back.setToolTipText("Go Back To Welcome Screen");
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

        logout = new JButton("Log Out");
        logout.setToolTipText("Log Out And Go Back To Welcome Screen");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0){
                frame.logout();
                frame.changeScreen(ClientGUI.WELCOME, null);
            }
        });
        //logout.setFont(new Font("Times New Roman",1,12));
        departmentLabel = new JLabel();
        departmentLabel.setText("Selected Department:");
        currentDepartment = new JComboBox();
        currentDepartment.addActionListener(deptComboActList);
        Department d = frame.getDepartment(frame.getCurrentDepartment());
        if(d != null) {
            currentDepartment.setSelectedItem(d);
        }
        if (frame.getPermissions() < User.SUPER_ADMIN)  {
            currentDepartment.setVisible(false);
            departmentLabel.setVisible(false);
        }

        addJComponentToContainerUsingGBL(adminLabel, this, 1, 1, 5, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 5, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(departmentLabel, this, 4, 4, 1, 1);
        addJComponentToContainerUsingGBL(currentDepartment, this, 5, 4, 1, 1);
        addJComponentToContainerUsingGBL(editMajor, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(download, this, 2, 5, 1, 1);
        addJComponentToContainerUsingGBL(upload, this, 3,5,1,1);
        addJComponentToContainerUsingGBL(back, this, 6,6,1,1);
        addJComponentToContainerUsingGBL(logout, this, 6, 7, 1, 1);
        addJComponentToContainerUsingGBL(error, this, 1, 9, 1, 1);
    }

    @Override
    public void getScreen(Object fillWith) {
        DefaultTableModel model = (DefaultTableModel)majors.getModel();
        int rows = model.getRowCount();
        for(int i = 0; i < rows; i++){
            model.removeRow(0);
        }
        if(fillWith instanceof ArrayList){
            ArrayList<Major> major = (ArrayList<Major>) fillWith;
            for (Major m : major) {
                String[] s = {m.getId()};
                model.addRow(s);
            }
        }
        adminLabel.setText("Department Administrator - " + frame.getCurrentDepartment());
        if (frame.getPermissions() >= User.SUPER_ADMIN)  {
            currentDepartment.setVisible(true);
            departmentLabel.setVisible(true);
        }
        else    {
            currentDepartment.setVisible(false);
            departmentLabel.setVisible(false);
        }

        currentDepartment.removeActionListener(deptComboActList);
        currentDepartment.removeAllItems();
        for(Department d : frame.getDepartments()) {
            currentDepartment.addItem(d.getName());
        }
        currentDepartment.addActionListener(deptComboActList);
        Department d2 = frame.getDepartment(frame.getCurrentDepartment());
        if(d2 != null) {
            currentDepartment.setSelectedItem(d2.getName());
        }
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
        if(majors.getSelectedRow()==-1)
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
        //label for curS
        JLabel numCL;
        //spinner for currlist
        JSpinner curS;
        //checkbox to make the current sequence excluded
        JCheckBox exclude;
        //button adds current sequence the requirement
        JButton finishButton;
        //button removes the sequence
        JButton removeSButton;
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
        //spinner for year of requirement
        JSpinner yearS;
        //label for year
        JLabel yearL;
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
            addPage = new JLabel("Add/Edit Requirement Page");
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
            addButton.setToolTipText("Add Selected Course To Current Sequence");
            addButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultListModel model = (DefaultListModel)currList.getModel();
                    for(int i : courses.getSelectedRows()){
                        String s = (String)courses.getModel().getValueAt(i, 0);
                        if(!model.contains(s)){
                            model.addElement(s);
                        }
                    }
                }
            });
            remButton = new JButton("<- Remove");
            remButton.setToolTipText("Remove Selected Course From Sequence");
            remButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultListModel model = (DefaultListModel)currList.getModel();
                    if(currList.getSelectedIndex()!=-1){
                        model.remove(currList.getSelectedIndex());
                    }
                }
            });
            currL = new JLabel("Current Sequence");
            currList = new JList();
            currList.setModel(new DefaultListModel());
            JScrollPane curScroll = new JScrollPane(currList);
            curS = new JSpinner();
            numCL = new JLabel("Number Required");
            exclude = new JCheckBox("Exclude these courses");
            finishButton = new JButton("Finish ->");
            finishButton.setToolTipText("Add The Current Sequence To The Requirement Sequences");
            finishButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CourseGroup cg = new CourseGroup();

                    DefaultListModel model = (DefaultListModel)seqList.getModel();
                    for(Object o : ((DefaultListModel)currList.getModel()).toArray()){
                        cg.addCourse(frame.getCourse((String)o));
                    }
                    cg.setNumReqiured((Integer)curS.getValue());
                    model.addElement(cg);
                    ((DefaultListModel)currList.getModel()).removeAllElements();
                }
            });
            removeSButton = new JButton("Remove Sequence");
            removeSButton.setToolTipText("Remove The Selected Sequence From Requirement Sequences");
            removeSButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(seqList.getSelectedIndex()!=-1){
                        ((DefaultListModel)seqList.getModel()).remove(seqList.getSelectedIndex());
                    }
                }
            });
            seqL = new JLabel("Requirement Sequences");
            seqList = new JList();
            seqList.setModel(new DefaultListModel());
            JScrollPane seqScroll = new JScrollPane(seqList);
            minSeqL = new JLabel("Min Sequences");
            seqS = new JSpinner();
            infoL = new JLabel("Requirement Info");
            infoL.setFont(new Font("Times New Roman",1,72/2));
            nameL = new JLabel("Name");
            nameF = new JTextField(10);
            minGPAL = new JLabel("Min GPA");
            minGPAF = new JTextField(10);
            minUpperL = new JLabel("Min Credits");
            minUpperS = new JSpinner();
            minUpperS.setModel(new SpinnerNumberModel(0,0,100,1));
            yearL = new JLabel("Year");
            yearS = new JSpinner();
            yearS.setModel(new SpinnerNumberModel(2010, 2000, 2200, 1));

            ok = new JButton("OK");
            ok.setToolTipText("Add The Requirement And Go Back To Requirement Page");
            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        try{
                        String s = nameF.getText();
                        frame.addRequirement(makeReq(), s);
                        Object o = frame.getCurrentMajor();
                        Major m = (Major)o;
                        Major maj = frame.getMajor(m.getId());
                        frame.changeManageScreen(ClientGUI.CURR_EDIT, maj);
                        }catch(NumberFormatException e)
                        {
                            JOptionPane.showMessageDialog(null, "Make Sure All The Fields Are Filled Out Correctly");
                        }
                    }
                }

            });
            cancel = new JButton("Cancel");
            cancel.setToolTipText("Go Back To Requirement Page");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Object o = frame.getCurrentMajor();
                    Major m = (Major)o;
                    Major maj = frame.getMajor(m.getId());
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, maj);
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
            addJComponentToContainerUsingGBL(removeSButton, this, 5, 6, 1, 1);
            addJComponentToContainerUsingGBL(seqScroll, this, 6, 3, 1, 4);
            
            addJComponentToContainerUsingGBL(numCL, this, 3, 7, 1, 1);
            addJComponentToContainerUsingGBL(curS, this, 4, 7, 1, 1);
            
            //addJComponentToContainerUsingGBL(exclude, this, 3, 8, 1, 1);
            addJComponentToContainerUsingGBL(seqS, this, 6, 7, 1, 1);

            addJComponentToContainerUsingGBL(infoL, this, 3, 9, 2, 1);
            addJComponentToContainerUsingGBL(nameL, this, 3, 10, 1, 1);
            addJComponentToContainerUsingGBL(nameF, this, 4, 10, 1, 1);
            addJComponentToContainerUsingGBL(minGPAL, this, 3, 11, 1, 1);
            addJComponentToContainerUsingGBL(minGPAF, this, 4, 11, 1, 1);
            addJComponentToContainerUsingGBL(minUpperL, this, 3, 12, 1, 1);
            addJComponentToContainerUsingGBL(minUpperS, this, 4, 12, 1, 1);
            addJComponentToContainerUsingGBL(yearL, this, 3, 13, 1, 1);
            addJComponentToContainerUsingGBL(yearS, this, 4, 13, 1, 1);
            addJComponentToContainerUsingGBL(ok, this, 3, 14, 1, 1);
            addJComponentToContainerUsingGBL(cancel, this, 4, 14, 1, 1);


        }
        private Requirement makeReq() {
            Requirement r = new Requirement();
            int num = seqList.getModel().getSize();
            for(int i = 0; i<num; i++){
                r.addCourseGroup((CourseGroup)seqList.getModel().getElementAt(i));
            }
            r.setId(nameF.getText());
            r.setMinGPA((Double.parseDouble(minGPAF.getText())));
            r.setMinUpperDivCredits((Integer)minUpperS.getValue());
            r.setNumberOfCourses((Integer)seqS.getValue());
            r.setYear((Integer)yearS.getValue());
            return r;
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
            DefaultListModel seqM = (DefaultListModel)seqList.getModel();
            seqM.removeAllElements();
            if(fillWith instanceof Requirement){
                Requirement r = (Requirement)fillWith;
                for(CourseGroup cg : r.getPossibleCourses()){
                    seqM.addElement(cg);
                }
                nameF.setText(r.getId());
                minGPAF.setText(r.getMinGPA()+"");
                minUpperS.setValue(r.getMinUpperDivCredits());
                seqS.setValue(r.getNumberOfCourses());
                yearS.setValue(r.getYear());
            } else {
                nameF.setText("");
                minGPAF.setText("");
                minUpperS.setValue(0);
                seqS.setValue(0);
                yearS.setValue(2010);
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

            table = new JTable();
            table.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            model.addColumn(columnNames[0]);
            table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            addReq = new JButton("Add");
            addReq.setToolTipText("Go To Add/Edit Requirement Page");
            addReq.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeManageScreen(ClientGUI.CURR_ADD, null);
                }
            });
            editReq = new JButton("Edit");
            editReq.setToolTipText("Go To Add/Edit Requirement Page");
            editReq.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        String s = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
                        Requirement r = null;
                        for(Requirement rec : frame.getRequirements()){
                            if(rec.getId().equals(s)){
                                r=rec;
                                break;
                            }
                        }
                        frame.changeManageScreen(ClientGUI.CURR_ADD, r);
                    }
                }
            });
            remReq = new JButton("Remove");
            remReq.setToolTipText("Remove Selected Requirement From Table");
            remReq.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(validateForm()){
                        String s = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
                        frame.removeRequirement(s, frame.getCurrentMajor().getId());
                    }
                }
            });
            back = new JButton("Back");
            back.setToolTipText("Go Back To Department Administrator Page");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeScreen(ClientGUI.MAJORS, frame.getDepartment(frame.getCurrentDepartment()).getMajors());
                }
            });
            this.setLayout(new GridBagLayout());
            addJComponentToContainerUsingGBL(title, this, 1, 1, 5, 1);
            addJComponentToContainerUsingGBL(new JScrollPane(table), this, 1, 2, 5, 2);
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
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int n = model.getRowCount();
            for(int i = 0; i<n;i++){
                model.removeRow(0);
            }
            if(fillWith instanceof Major){
                Major m = (Major) fillWith;
                for(Requirement r : m.getRequirements()){
                    model.addRow(new String[]{r.getId()});
                }
            }
        }

        @Override
        public boolean validateForm() {
            return table.getSelectedRow()!=-1;
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
    class MajorDialog extends JDialog {
        MajorDialog(Dialog d, String t){
            super(d,t,true);
            init();
        }
        private void init() {
            this.setLayout(new GridBagLayout());
            JLabel gpaL;
            JLabel minLocalL;
            //JLabel

            //JLabel gpaF;

        }
    }
    private class DeptActList implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    frame.setCurrentDepartment(currentDepartment.getSelectedItem().toString());
                    Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                    frame.changeScreen(ClientGUI.MAJORS, o);
                }
    }
}
