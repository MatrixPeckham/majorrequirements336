package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import server.Commands;
import server.Course;
import server.CourseGroup;
import server.Department;

/**
 * Manager for the courses
 * @author JP & Bill
 *
 */
public class CourseManagerScreen extends Screen implements ManagerScreen {

    /**
     * serial version ID that eclipse wants in all swing classes
     */
    private static final long serialVersionUID = -747564902385911678L;
    //these three are for returning from the manager screen methods
    private AddCouScreen addScreen;
    private EditCouScreen editScreen;
    private RemoveCouScreen remScreen;
    //title label
    private JLabel editDepartmentPage;
    //courses lable
    private JLabel coursesForDepartment;
    //table for courses
    private JTable table;
    //edit button
    private JButton edit;
    //remove button
    private JButton remove;
    //add button
    private JButton add;
    //ubload button
    private JButton uploadCourses;
    //browse button
    private JButton browse;
    //back button
    private JButton back;
    //to registar admin button
    private JButton registrarAdminPage;
    //file URL text field
    private JTextField textField;

    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public CourseManagerScreen(ClientGUI gui) {
        super(gui);
        addScreen = new AddCouScreen(gui);
        editScreen = new EditCouScreen(gui);
        remScreen = new RemoveCouScreen(gui);
        layoutGUI();
    }

    /**
     * sets up GUI, including most action listeners
     */
    private void layoutGUI() {

        editDepartmentPage = new JLabel("Edit Department Page");
        editDepartmentPage.setFont(new Font("Times New Roman", 1, 72));
        coursesForDepartment = new JLabel("Courses For Department");
        edit = new JButton("Edit Course");
        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    Course c = frame.getCourse((String)table.getModel().getValueAt(table.getSelectedRow(), 0));
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, c);
                }
            }
        });
        remove = new JButton("Remove Course");
        remove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.removeCourse((String)table.getModel().getValueAt(table.getSelectedRow(), 0));
                Object o = frame.getDepartment(frame.getCurrentDepartment());
                getScreen(o);
            }
        });
        add = new JButton("Add Course");
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeManageScreen(ClientGUI.CURR_ADD, null);
            }
        });
        uploadCourses = new JButton("Upload Courses");
        uploadCourses.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.uploadFile(new File(textField.getText()), Commands.UPLOAD_COURSE_DATA);
            }
        });
        browse = new JButton("Browse");
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File f = getFile(true);
                textField.setText(f.getAbsolutePath());
            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.WELCOME, null);
            }
        });
        registrarAdminPage = new JButton("Registrar Admin Page");
        registrarAdminPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = frame.getDepartments();
                frame.changeScreen(ClientGUI.DEPARTMENTS, o);
            }
        });
        textField = new JTextField(20);


        String[] columnNames = {"Courses"};

        Object[][] data = {
            {"CSE 308"}, {"CSE 381"}, {"CSE 380"}, {"CSE 220"},
            {"CSE 114"}, {"CSE 215"}, {"CSE 219"}, {"CSE 110"},
            {"MAT 127"}};

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(){
            @Override
                    public java.lang.Class<?> getColumnClass(int columnIndex) {
                        return getValueAt(0, columnIndex).getClass();
                    }

        });
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (String s : columnNames) {
                    model.addColumn(s);
                }
                for (Object[] o : data) {
                    model.addRow(o);
                }


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);


        addJComponentToContainerUsingGBL(editDepartmentPage, this, 1, 1, 4, 1);
        addJComponentToContainerUsingGBL(coursesForDepartment, this, 1, 2, 1, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 3, 4, 5);
        addJComponentToContainerUsingGBL(add, this, 1, 10, 1, 1);
        addJComponentToContainerUsingGBL(remove, this, 2, 10, 1, 1);
        addJComponentToContainerUsingGBL(edit, this, 3, 10, 1, 1);
        addJComponentToContainerUsingGBL(uploadCourses, this, 1, 20, 1, 1);
        addJComponentToContainerUsingGBL(textField, this, 2, 20, 1, 1);
        addJComponentToContainerUsingGBL(browse, this, 3, 20, 1, 1);
        addJComponentToContainerUsingGBL(back, this, 5, 30, 1, 1);
        addJComponentToContainerUsingGBL(registrarAdminPage, this, 6, 30, 1, 1);
    }

    @Override
    public void getScreen(Object fillWith) {
            if(fillWith instanceof ArrayList){
                //Department d = (Department) fillWith;
                ArrayList<Course> courses = (ArrayList<Course>)fillWith;
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                int rows = model.getRowCount();
                for(int i = 0; i < rows; i++){
                    model.removeRow(0);
                }
                for (Course c : courses) {
                    String[] s = {c.getId()};
                    model.addRow(s);
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
        return true;//throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for adding Courses
     * @author Bill
     *
     */
    private class AddCouScreen extends Screen {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = 4099112134368019743L;
        //add label
        protected JLabel addL;
        //name label
        private JLabel nameL;
        //name text field
        private JTextField nameF;
        //number lable
        private JLabel numL;
        //number text field
        private JTextField numF;
        //department label
        private JLabel deptL;
        //department text field
        private JTextField deptF;
        //description label
        private JLabel descL;
        //description text area
        private JTextArea descF;
        //table for prereqs
        private JTable prereq;
        //ok button
        protected JButton ok;
        //cancel button
        private JButton back;

        //constructor
        public AddCouScreen(ClientGUI gui) {
            super(gui);
            initGUI();
        }
        //sets up GUI including action lsiteners

        private void initGUI() {
            addL = new JLabel("Add Course Page");
            addL.setFont(new Font("Times New Roman", 1, 72));
            nameL = new JLabel("Name");
            nameF = new JTextField(10);
            numL = new JLabel("Number");
            numF = new JTextField(10);
            deptL = new JLabel("Department");
            deptF = new JTextField(10);
            descL = new JLabel("Description");
            descF = new JTextArea(10, 5);
            descF.setLineWrap(true);
            descF.setWrapStyleWord(true);
            ok = new JButton("Add");
            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(validateForm()){
                        frame.addCourse(makeCourse(), frame.getCurrentDepartment());
                        frame.changeScreen(ClientGUI.COURSES, frame.getDepartment(frame.getCurrentDepartment()));
                    }
                }
            });
            back = new JButton("Cancel");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeScreen(ClientGUI.COURSES, frame.getDepartment(frame.getCurrentDepartment()));
                }
            });
            String[] columnNames = {"Course", "Select"};
            Object[][] data = {
                {"CSE 308", new Boolean(false)}, {"CSE 381", new Boolean(false)}, {"CSE 380", new Boolean(false)}, {"CSE 220", new Boolean(false)},
                {"CSE 114", new Boolean(false)}, {"CSE 215", new Boolean(false)}, {"CSE 219", new Boolean(false)}, {"CSE 110", new Boolean(false)},
                {"MAT 127", new Boolean(false)}};
            prereq = new JTable();
            prereq.setPreferredScrollableViewportSize(new Dimension(200, 400));
            prereq.setFillsViewportHeight(true);
            prereq.setModel(new DefaultTableModel() {

                @Override
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }
                /**
                 *
                 */
                private static final long serialVersionUID = -7810042264203030452L;
            });
            DefaultTableModel model = (DefaultTableModel) prereq.getModel();
            for (String s : columnNames) {
                model.addColumn(s);
            }
            for (Object[] o : data) {
                model.addRow(o);
            }
            this.setLayout(new GridBagLayout());
            JScrollPane scrollPane = new JScrollPane(prereq);

            addJComponentToContainerUsingGBL(addL, this, 1, 1, 6, 1);
            addJComponentToContainerUsingGBL(nameL, this, 1, 2, 1, 1);
            addJComponentToContainerUsingGBL(nameF, this, 2, 2, 1, 1);
            addJComponentToContainerUsingGBL(numL, this, 1, 3, 1, 1);
            addJComponentToContainerUsingGBL(numF, this, 2, 3, 1, 1);
            addJComponentToContainerUsingGBL(deptL, this, 1, 4, 1, 1);
            addJComponentToContainerUsingGBL(deptF, this, 2, 4, 1, 1);
            addJComponentToContainerUsingGBL(descL, this, 1, 5, 1, 1);
            addJComponentToContainerUsingGBL(new JScrollPane(descF), this, 2, 5, 2, 1);
            addJComponentToContainerUsingGBL(ok, this, 2, 6, 1, 1);
            addJComponentToContainerUsingGBL(back, this, 3, 6, 1, 1);
            addJComponentToContainerUsingGBL(scrollPane, this, 4, 2, 2, 5);


        }

        private Course makeCourse() {
            return null;
        }
        @Override
        public void getScreen(Object fillWith) {
            Course cou=null;
            ArrayList<Course> prereqlist = new ArrayList<Course>();
            if(fillWith instanceof Course){
                cou = (Course)fillWith;
                ArrayList<CourseGroup> prereqs = new ArrayList<CourseGroup>(cou.getPrereqs());
                for(CourseGroup cg : prereqs){
                    prereqlist.addAll(cg.getCourses());
                }
            }
            ArrayList<Course> courses = frame.getAllCourses();
            DefaultTableModel model = (DefaultTableModel)prereq.getModel();
            int num = model.getRowCount();
            for(int i = 0;i<num;i++){
                model.removeRow(0);
            }
            if(courses!=null) {
                for(Course c : courses){
                    Object[] o = {c.getId(),prereqlist.contains(c)};
                    model.addRow(o);
                }
            }
        }

        @Override
        public boolean validateForm() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for Editing Courses
     * @author Bill
     *
     */
    private class EditCouScreen extends AddCouScreen {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3153844264861602293L;

        //sets up GUI
        public EditCouScreen(ClientGUI gui) {
            super(gui);
            addL.setText("Edit Course Screen");
            ok.setText("Save");
        }
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for removing Courses
     * @author Bill
     *
     */
    private class RemoveCouScreen extends Screen {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3920339673618233723L;

        public RemoveCouScreen(ClientGUI gui){
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
