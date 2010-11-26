package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.ComboBoxModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.Commands;
import server.CourseRecord;
import server.Grade;
import server.Major;
import server.Requirement;
import server.Schedule;
import server.User;

/**
 * Classes manager screen
 * @author Bill
 *
 */
public class ClassesManagerScreen extends Screen implements ManagerScreen {

    /**
     * serial version ID that eclipse wants in all swing classes
     */
    private static final long serialVersionUID = -747564907385911673L;
    //these three are for returning from the manager screen
    private AddClasScreen addScreen;
    private EditClasScreen editScreen;
    private JComboBox major;
    private RemoveClasScreen remScreen;
    //title screen
    private JLabel studentPage;
    //coruses table
    private JTable courses;
    //add button
    private JButton addButton;
    //edit button
    private JButton editButton;
    //remove button
    private JButton removeButton;
    //upload button
    private JButton uploadButton;
    //download button
    private JButton downloadButton;
    //check button
    private JButton checkButton;
    //generate schedule button
    private JButton generateButton;
    //back button
    private JButton backButton;
    //error label
    private JLabel error;

    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public ClassesManagerScreen(ClientGUI gui) {
        super(gui);
        addScreen = new AddClasScreen(gui);
        editScreen = new EditClasScreen(gui);
        remScreen = new RemoveClasScreen(gui);
        initGUI();
    }
    //sets up GUI includding actio listeners

    private void initGUI() {
        studentPage = new JLabel("Student Page");
        studentPage.setFont(new Font("Times New Roman", 1, 72));
        String[] columnNames = {"Course", "Grade", "Transfer"};
        Object[][] data = {};
        courses = new JTable();
        courses.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        courses.setFillsViewportHeight(true);
        courses.setModel(new DefaultTableModel() {

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
        DefaultTableModel model = (DefaultTableModel) courses.getModel();
        for (String s : columnNames) {
            model.addColumn(s);
        }
        for (Object[] o : data) {
            model.addRow(o);
        }

        JScrollPane scrollPane = new JScrollPane(courses);

        addButton = new JButton("Add Course");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeManageScreen(ClientGUI.CURR_ADD, null);
            }
        });
        editButton = new JButton("Edit Course");
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    String str = (String) courses.getModel().getValueAt(courses.getSelectedRow(),0);
                    CourseRecord cr = frame.getCourseRecord(str);
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, cr);
                }
            }
        });
        removeButton = new JButton("Remove Course");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(validateForm()){
                    String str = (String) courses.getModel().getValueAt(courses.getSelectedRow(),0);
                    boolean b = frame.removeCourseRecord(str);
                    if(b){
                        Object o = frame.getStudentInfo();
                        frame.changeScreen(ClientGUI.CLASSES, o);
                    }
                }
            }
        });
        uploadButton = new JButton("Upload Courses");
        uploadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.uploadFile(getFile(true), Commands.UPLOADFILE);
                frame.changeScreen(ClientGUI.CLASSES, null);
            }
        });
        downloadButton = new JButton("Dowload Courses");
        downloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dowloadFile(getFile(false),Commands.DOWNLOAD_SCHED);
            }
        });
        checkButton = new JButton("Check Requirements");
        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Requirement> o = frame.checkSchedule();
                frame.changeScreen(ClientGUI.CHECK, o);
            }
        });
        generateButton = new JButton("Generate Schedule");
        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Schedule o = frame.generateSchedule();
                frame.changeScreen(ClientGUI.SCHEDULE, o);
            }
        });
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.WELCOME, null);

            }
        });

        error = new JLabel("Error: Please select a course from the above table");
        error.setFont(new Font("Times New Roman", 1, 12));
        error.setVisible(false);
        error.setForeground(Color.red);
        this.setLayout(new GridBagLayout());
        JLabel majorLabel=new JLabel();
        majorLabel.setText("Current Major:");
        major=new JComboBox();
        major.addItem(new Major("Undecided",0,0));
        for(Major m : frame.getAllMajors()) {
            major.addItem(m);
        }
        major.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.changeMajor((Major)major.getSelectedItem());

                }
        });
        Major m2=frame.getCurrentMajor();
        if(m2!=null) {
            major.setSelectedItem(m2);
        } else {
        major.setSelectedIndex(0);
        }
        addJComponentToContainerUsingGBL(studentPage, this, 1, 1, 5, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 5, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(uploadButton, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(downloadButton, this, 3, 5, 1, 1);
        addJComponentToContainerUsingGBL(checkButton, this, 4, 4, 1, 1);
        addJComponentToContainerUsingGBL(generateButton, this, 4, 5, 1, 1);
        addJComponentToContainerUsingGBL(majorLabel, this, 1, 6, 1, 1);
        addJComponentToContainerUsingGBL(major, this, 2, 6, 2, 1);
        addJComponentToContainerUsingGBL(backButton, this, 6, 7, 1, 1);
        addJComponentToContainerUsingGBL(error, this, 5, 6, 1, 1);
    }

    @Override
    public void getScreen(Object fillWith) {
       DefaultTableModel m=((DefaultTableModel)courses.getModel());
       for(int i=0; i<m.getRowCount(); i++) {
           m.removeRow(0);
       }

        TreeMap<String,CourseRecord> t=frame.getStudentInfo().getCourses();
            for(CourseRecord r : t.values()) {
                for(Grade g : r.getGrades()) {
                    Object[] o=new Object[3];
                    o[0]=r.getCourse().getId();
                    o[1]=g.getGrade();
                    //JCheckBox j=new JCheckBox();
                    //j.setSelected();
                    o[2]=r.getTransfer();
                    ((DefaultTableModel)courses.getModel()).addRow(o);
                }
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
        if(courses.getSelectedRow() == -1)
        {
            error.setVisible(true);
            return false;
        }
        error.setVisible(false);
        return true;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for adding Classess
     * @author Bill
     *
     */
    private class AddClasScreen extends Screen {
        //title label

        JLabel addPage;
        //courses table
        JTable courses;
        //grade lable
        JLabel gradeL;
        //grade combo box
        JComboBox gradeBox;
        //transfer box
        JCheckBox transBox;
        //ok button
        JButton ok;
        //cancel button
        JButton cancel;

        //constructor
        public AddClasScreen(ClientGUI gui) {
            super(gui);
            initGUI();
        }
        //sets up GUI including action listeners

        private void initGUI() {
            addPage = new JLabel("Add Courses Page");
            addPage.setFont(new Font("Times New Roman", 1, 72));
            String[] columnNames = {"Department", "ID", "Credits", "Prereq"};
            Object[][] data = {
                {"CSE", "308", "3", "CSE 219"}, {"CSE", "381", "3", "CSE 219"},
                {"CSE", "380", "3", "CSE 219"}, {"CSE", "220", "3", "CSE 219"},
                {"CSE", "114", "3", "CSE 219"}, {"CSE", "215", "3", "CSE 219"},
                {"CSE", "219", "3", "CSE 219"}, {"CSE", "110", "3", "CSE 219"},
                {"MAT", "127", "3", "CSE 219"}};
            courses = new JTable();
            courses.setPreferredScrollableViewportSize(new Dimension(1000, 100));
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

            JScrollPane scrollPane = new JScrollPane(courses);
            gradeL = new JLabel("Grade");
            gradeBox = new JComboBox(new String[]{"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F", "I"});
            transBox = new JCheckBox("Transfer");
            ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        if(addPage.getText().startsWith("Add")){
                            CourseRecord r = makeRecord();
                            frame.addCourseRecord(r);
                        } else {
                            CourseRecord r = makeRecord();
                            frame.editCourseRecord(r);
                        }
                        Object o = frame.getStudentInfo();
                        frame.changeScreen(ClientGUI.CLASSES, o);
                    }
                }
            });
            cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Object o = frame.getStudentInfo();
                    frame.changeScreen(ClientGUI.CLASSES, o);
                }
            });
            setLayout(new GridBagLayout());
            addJComponentToContainerUsingGBL(addPage, this, 1, 1, 4, 1);
            addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
            addJComponentToContainerUsingGBL(gradeL, this, 1, 4, 1, 1);
            addJComponentToContainerUsingGBL(transBox, this, 3, 4, 1, 1);
            addJComponentToContainerUsingGBL(gradeBox, this, 2, 4, 1, 1);
            addJComponentToContainerUsingGBL(ok, this, 1, 5, 1, 1);
            addJComponentToContainerUsingGBL(cancel, this, 3, 5, 1, 1);
        }

        private CourseRecord makeRecord() {
            return null;
        }
        @Override
        public void getScreen(Object fillWith) {

        }

        @Override
        public boolean validateForm() {
            return true;
        }

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = 4099112134368019743L;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for Editing Classess
     * @author Bill
     *
     */
    private class EditClasScreen extends AddClasScreen {
        //sets up the GUI

        public EditClasScreen(ClientGUI gui) {
            super(gui);
            addPage.setText("Edit Course Page");
        }

        @Override
        public void getScreen(Object fillWith) {

        }

        @Override
        public boolean validateForm() {
            //JP leave this one alone
            return super.validateForm();
        }
        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3153834364861607293L;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for removing Classes
     * @author Bill
     *
     */
    private class RemoveClasScreen extends Screen {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3920339673618333223L;

        public RemoveClasScreen(ClientGUI gui){
            super(gui);
        }

        @Override
        public void getScreen(Object fillWith) {

        }

        @Override
        public boolean validateForm() {
            return false;
        }
    }
}
