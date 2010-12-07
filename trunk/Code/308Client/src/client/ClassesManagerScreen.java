package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import server.Commands;
import server.Course;
import server.CourseGroup;
import server.CourseRecord;
import server.Grade;
import server.Major;
import server.Schedule;
import server.Semester;
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
    private JSpinner year;
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
    private JButton uploadCSV;
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
    private MajorActionListener majorActList;
    private JButton logout;


    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public ClassesManagerScreen(ClientGUI gui) {
        super(gui);
        addScreen = new AddClasScreen(gui);
        editScreen = new EditClasScreen(gui);
        remScreen = new RemoveClasScreen(gui);
        majorActList = new MajorActionListener();
        initGUI();
    }
    //sets up GUI includding actio listeners

    private void initGUI() {
        studentPage = new JLabel("Student Page");
        studentPage.setFont(new Font("Times New Roman", 1, 72));
        String[] columnNames = {"Course", "Grade", "Transfer", "Semester", "Status"};
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
        addButton.setToolTipText("Go to Add Courses Page");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeManageScreen(ClientGUI.CURR_ADD, null);
            }
        });
        editButton = new JButton("Edit Course");
        editButton.setToolTipText("Go to Edit Courses Page");
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //if(validateForm()){
                    try{
                    String str = (String) courses.getModel().getValueAt(courses.getSelectedRow(),0);
                    CourseRecord cr = frame.getCourseRecord(str);
                    error.setVisible(false);
                    frame.changeManageScreen(ClientGUI.CURR_EDIT, cr);
                    }catch(ArrayIndexOutOfBoundsException s){
                       JOptionPane.showMessageDialog(null, "You must select a course from the table");
                    }
                //}
            }
        });
        removeButton = new JButton("Remove Course");
        removeButton.setToolTipText("Remove Selected Course");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //if(validateForm()){
                    try{
                    String str = (String) courses.getModel().getValueAt(courses.getSelectedRow(),0);
                    boolean b = frame.removeCourseRecord(str);
                    if(b){
                        Object o = frame.getStudentInfo();
                        error.setVisible(false);
                        frame.changeScreen(ClientGUI.CLASSES, o);
                        }
                    }catch(ArrayIndexOutOfBoundsException a){
                        JOptionPane.showMessageDialog(null, "You must select a course from the table");
                    }
                //}
            }
        });
        uploadButton = new JButton("Upload Courses");
        uploadButton.setToolTipText("Upload Courses From A File");
        uploadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File f=getFile(true);
                if(f!=null) {
                    
                    frame.uploadFile(f, Commands.UPLOADFILE);
                    User u=frame.getStudentInfo();
                    frame.changeScreen(ClientGUI.CLASSES, u);
                    error.setVisible(false);
                    major.setSelectedItem(u.getMajor());
                    year.getModel().setValue(u.getMajorYear());
                }
            }
        });
        downloadButton = new JButton("Dowload Courses");
        downloadButton.setToolTipText("Download Courses To A File");
        downloadButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                frame.downloadFile(getFile(false),Commands.DOWNLOAD_COURSE_DATA);
            }
        });
        checkButton = new JButton("Check Requirements");
        checkButton.setToolTipText("Go To Check Requirements Page");
        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                frame.changeScreen(ClientGUI.CHECK, frame.checkSchedule());
            }
        });
        generateButton = new JButton("Generate Schedule");
        generateButton.setToolTipText("Go To Generate Schedule Page");
        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Schedule o = frame.generateSchedule();
                error.setVisible(false);
                frame.changeScreen(ClientGUI.SCHEDULE, o);
            }
        });
        backButton = new JButton("Back");
        backButton.setToolTipText("Go Back To Welcome Screen ");
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                frame.changeScreen(ClientGUI.WELCOME, null);
            }
        });
        logout = new JButton("Log Out");
        logout.setToolTipText("Log Out");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0){
                frame.logout();
                frame.changeScreen(ClientGUI.WELCOME, null);
            }
        });
        logout.setVisible(false);
        error = new JLabel("Error: Please select a course from the above table");
        error.setFont(new Font("Times New Roman", 1, 12));
        error.setVisible(false);
        error.setForeground(Color.red);
        this.setLayout(new GridBagLayout());

        JLabel majorLabel=new JLabel();
        majorLabel.setText("Current Major:");
        major=new JComboBox();
        major.addItem(new Major("Undecided",0,0));
        major.addActionListener(majorActList);
        Major m2=frame.getCurrentMajor();
        if(m2!=null) {
            major.setSelectedItem(m2);
        } else {
        major.setSelectedIndex(0);
        }
        JLabel yearLabel=new JLabel("Requirement Year:");
        year=new JSpinner();
        SpinnerNumberModel s=new SpinnerNumberModel();
        s.setMaximum(2100);
        s.setMinimum(1900);
        s.setStepSize(1);
        s.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
               frame.changeYear((Integer)((SpinnerNumberModel)e.getSource()).getValue());
            }

        });
        s.setValue((new Date()).getYear()+1900);
        year.setModel(s);
        uploadCSV=new JButton("Upload CSV");
        uploadCSV.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File f=getFile(true);
                if(f!=null) {
                    try{
                    frame.uploadCSV(f);
                    User u=frame.getStudentInfo();
                    frame.changeScreen(ClientGUI.CLASSES, u);
                    error.setVisible(false);
                    major.setSelectedItem(u.getMajor());
                    year.getModel().setValue(u.getMajorYear());
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error uploading CSV:"+ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                }
                }
            }
        });
        addJComponentToContainerUsingGBL(studentPage, this, 1, 1, 5, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 5, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(uploadButton, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(uploadCSV, this, 1, 6, 1, 1);
        addJComponentToContainerUsingGBL(downloadButton, this, 3, 5, 1, 1);
        addJComponentToContainerUsingGBL(checkButton, this, 4, 4, 1, 1);
        addJComponentToContainerUsingGBL(generateButton, this, 4, 5, 1, 1);
        addJComponentToContainerUsingGBL(majorLabel, this, 1, 6, 1, 1);
        addJComponentToContainerUsingGBL(major, this, 2, 6, 2, 1);
        addJComponentToContainerUsingGBL(yearLabel, this, 1, 7, 1, 1);
        addJComponentToContainerUsingGBL(year, this, 2, 7, 2, 1);
        addJComponentToContainerUsingGBL(backButton, this, 6, 7, 1, 1);
        addJComponentToContainerUsingGBL(logout, this, 6, 8, 1, 1);
        addJComponentToContainerUsingGBL(error, this, 5, 6, 1, 1);
    }

    @Override
    public void getScreen(Object fillWith) {
       DefaultTableModel m=((DefaultTableModel)courses.getModel());
       int num = m.getRowCount();
       for(int i=0; i<num; i++) {
           m.removeRow(0);
       }
       major.removeActionListener(majorActList);
       major.removeAllItems();
       major.addItem(new Major("Undecided",0,0));
       for(Major mag : frame.getAllMajors()) {
           major.addItem(mag);
       }
       major.addActionListener(majorActList);
       Major m2=frame.getCurrentMajor();
       if(m2!=null) {
           major.setSelectedItem(m2);
       } else {
       major.setSelectedIndex(0);
       }
       TreeMap<String,CourseRecord> t=frame.getStudentInfo().getCourses();
           for(CourseRecord r : t.values()) {
               Object[] o=new Object[5];
               o[0]=r.getCourse().getId();
               o[1]=r.getGrades().get(r.getGrades().size()-1);
               //JCheckBox j=new JCheckBox();
               //j.setSelected();
               o[2]=r.getTransfer();
               o[3]=r.getSemester(r.getGrades().get(r.getGrades().size()-1));

               int courseStatus;
               Semester s=r.getIncompleteSemester();
               if(s==null) {
                   courseStatus=4;
               } else {
                   courseStatus=r.getCourse().canTake(s);
               }
               o[4]="";
               switch(courseStatus) {
                   case -1: o[4]="Not Offererd "+s.toString();
                   break;
                   case 0: o[4]="Tentative for "+s.toString();
                   break;
                   case 1: o[4]="Offered "+ s.toString();
                   break;
                   default: o[4]="Completed";
               }
               ((DefaultTableModel)courses.getModel()).addRow(o);
           }
            /*major.removeAllItems();
            major.addItem(new Major("Undecided",0,0));
            for(Major mag : frame.getAllMajors()) {
            major.addItem(mag);
        }*/
        if (frame.getPermissions() > User.STUDENT)
            logout.setVisible(true);
        else
            logout.setVisible(false);
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
        //grade combo box
        JComboBox gradeBox;
        //transfer box
        JCheckBox transBox;
        //Semester Box
        JComboBox semBox;
        //year spinner;
        JSpinner yearS;
        //table for grades
        JTable grades;
        //button to add a grade
        JButton addGradeB;
        //button for removing a grade
        JButton remGradeB;
        //ok button
        JButton ok;
        //cancel button
        JButton cancel;

        //error button
        JLabel error;

        //constructor
        public AddClasScreen(ClientGUI gui) {
            super(gui);
            initGUI();
        }
        //sets up GUI including action listeners

        private void initGUI() {
            addPage = new JLabel("Add Courses Page");
            addPage.setFont(new Font("Times New Roman", 1, 72));
            String[] columnNames = {"Course", "Credits", "Prereq"};
            Object[][] data = {
                {"CSE 308", "3", "CSE 219"}, {"CSE 381", "3", "CSE 219"},
                {"CSE 380", "3", "CSE 219"}, {"CSE 220", "3", "CSE 219"},
                {"CSE 114", "3", "CSE 219"}, {"CSE 215", "3", "CSE 219"},
                {"CSE 219", "3", "CSE 219"}, {"CSE 110", "3", "CSE 219"},
                {"MAT 127", "3", "CSE 219"}};
            courses = new JTable();
            courses.setPreferredScrollableViewportSize(new Dimension(1000, 100));
            courses.setFillsViewportHeight(true);
            courses.setModel(new DefaultTableModel() {

                @Override
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    try{
                        return getValueAt(0, columnIndex).getClass();
                    }catch(ArrayIndexOutOfBoundsException aioobe){
                        return String.class;
                    }
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
            Grade[] gradeL = {new Grade("A"), new Grade("A-"), new Grade("B+"), new Grade("B"), new Grade("B-"), new Grade("C+"), new Grade("C"), new Grade("C-"), new Grade("D+"), new Grade("D"), new Grade("D-"), new Grade("F"), new Grade("I")};
            gradeBox = new JComboBox(gradeL);
            ok = new JButton("OK");
            ok.setToolTipText("Add Desired Course And Go To Student Page ");
            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(validateForm()){
                        try{
                        if(addPage.getText().startsWith("Add")){
                            CourseRecord r = makeRecord();
                            frame.addCourseRecord(r);
                        } else {
                            CourseRecord r = makeRecord();
                            frame.editCourseRecord(r);
                        }
                        Object o = frame.getStudentInfo();
                        if(grades.getCellEditor()!=null){
                            grades.getCellEditor().stopCellEditing();
                        }
                        frame.changeScreen(ClientGUI.CLASSES, o);
                        }catch(ArrayIndexOutOfBoundsException e){
                        JOptionPane.showMessageDialog(null, "You must select a course to add from the table");}
                    }
                }
            });
            cancel = new JButton("Cancel");
            cancel.setToolTipText("Go Back To Student Page");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Object o = frame.getStudentInfo();
                        if(grades.getCellEditor()!=null){
                            grades.getCellEditor().stopCellEditing();
                        }
                    frame.changeScreen(ClientGUI.CLASSES, o);
                }
            });

            semBox = new JComboBox();
            BoxModel boxModel = new BoxModel();
            semBox.setModel(boxModel);
            boxModel.addElm("FALL", Semester.FALL);
            boxModel.addElm("SPRING", Semester.SPRING);
            boxModel.addElm("WINTER", Semester.WINTER);
            boxModel.addElm("Summer 1", Semester.SUMMER1);
            boxModel.addElm("Summer 1", Semester.SUMMER2);
            yearS = new JSpinner(new SpinnerNumberModel(2010,2000,2100,1));

            grades = new JTable();
            grades.setModel(new DefaultTableModel() {

                @Override
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    try{
                        return getValueAt(0, columnIndex).getClass();
                    }catch(ArrayIndexOutOfBoundsException aioobe){
                        return String.class;
                    }
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return true;
                }


                /**
                 *
                 */
                private static final long serialVersionUID = -7810042264203030452L;
            });
            grades.setFillsViewportHeight(true);

            grades.setPreferredScrollableViewportSize(new Dimension(1000, 100));
            DefaultTableModel graModel = (DefaultTableModel)grades.getModel();
            graModel.addColumn("Semester");
            graModel.addColumn("Year");
            graModel.addColumn("Grade");

            grades.setDefaultEditor(String.class, new DefaultCellEditor(semBox));
            grades.setDefaultEditor(Integer.class, new Editor(yearS));
            grades.setDefaultEditor(Grade.class, new DefaultCellEditor(gradeBox));

            Object[] o = {"FALL",2010,new Grade("A")};
            graModel.addRow(o);
            addGradeB = new JButton("Add Grade");
            addGradeB.setToolTipText("Add Grade To Course");
            addGradeB.addActionListener(new ActionListener() {
            
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel model = (DefaultTableModel)grades.getModel();
                    Object[] o = {"FALL",2010,new Grade("A")};
                    model.addRow(o);
                }
            });
            remGradeB = new JButton("Remove Grade");
            remGradeB.setToolTipText("Remove Grade From Course");
            remGradeB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int sel = grades.getSelectedRow();
                    if(sel!=-1){
                        DefaultTableModel model = (DefaultTableModel)grades.getModel();
                        if(grades.getCellEditor()!=null){
                            grades.getCellEditor().stopCellEditing();
                        }
                        model.removeRow(sel);
                    }
                }
            });
            transBox = new JCheckBox("Transfer");
            setLayout(new GridBagLayout());
            addJComponentToContainerUsingGBL(addPage, this, 1, 1, 4, 1);
            addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 5, 2);
//            addJComponentToContainerUsingGBL(gradeL, this, 1, 4, 1, 1);
            addJComponentToContainerUsingGBL(transBox, this, 3, 4, 1, 1);
//            addJComponentToContainerUsingGBL(gradeBox, this, 2, 4, 1, 1);

            addJComponentToContainerUsingGBL(new JScrollPane(grades), this, 1, 5, 5, 1);
            addJComponentToContainerUsingGBL(addGradeB, this, 1, 6, 1, 1);
            addJComponentToContainerUsingGBL(remGradeB, this, 3, 6, 1, 1);
//            addJComponentToContainerUsingGBL(sem, this, 1, 5, 1, 1);
//            addJComponentToContainerUsingGBL(semBox, this, 2, 5, 1, 1);
//            addJComponentToContainerUsingGBL(yearL, this, 3, 5, 1, 1);
//            addJComponentToContainerUsingGBL(yearS, this, 4, 5, 1, 1);

            addJComponentToContainerUsingGBL(ok, this, 1, 7, 1, 1);
            addJComponentToContainerUsingGBL(cancel, this, 3, 7, 1, 1);
        }

        private CourseRecord makeRecord() {
            String dep = (String)courses.getModel().getValueAt(courses.getSelectedRow(),0);
            Course c = frame.getCourse(dep);
            //Grade g = new Grade((String)gradeBox.getSelectedItem());
            Boolean t = transBox.isSelected();
            CourseRecord cr = new CourseRecord(c,null,t);
            DefaultTableModel model = (DefaultTableModel)grades.getModel();
            for(int i =0; i<model.getRowCount();i++){
                Semester s = new Semester();
                BoxModel mod = (BoxModel)semBox.getModel();
                int j = mod.getIndexOf(model.getValueAt(i, 0));
                s.setSeason(mod.getValAt(j));
                s.setYear((Integer)model.getValueAt(i, 1));
                cr.addGrade((Grade)model.getValueAt(i, 2), s);
            }
            return cr;
        }
        @Override
        public void getScreen(Object fillWith) {
            ArrayList<Course> allC = frame.getAllCourses();
            Set<String> taken = frame.getStudentInfo().getCourses().keySet();
            DefaultTableModel model = (DefaultTableModel)courses.getModel();
            int num = model.getRowCount();
            for(int i=0;i<num;i++){
                model.removeRow(0);
            }
            num=allC.size();
            for(int i = 0; i<num;i++){
                Object[] o = new Object[3];
                Course c = allC.get(i);
                o[0]=c.getId();
                o[1]=c.getCredits();
                String s = "";
                for(CourseGroup cg:c.getPrereqs()){
                    for(Course ci : cg.getCourses()){
                        s+=ci.getId()+", ";
                    }
                }
                o[2]=s;
                if(!taken.contains(c.getId()))
                    model.addRow(o);
            }
            DefaultTableModel gmodel = (DefaultTableModel)grades.getModel();
            num = gmodel.getRowCount();
            for(int i = 0; i<num;i++)
                gmodel.removeRow(0);
            if(fillWith==null){
                Object[] o = {"FALL",2010,new Grade("A")};
                gmodel.addRow(o);
            }
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
            super.getScreen(fillWith);
            if(fillWith instanceof CourseRecord){
                CourseRecord cr = (CourseRecord) fillWith;
                DefaultTableModel model = (DefaultTableModel)courses.getModel();
                String id = cr.getCourse().getId();
                int num = model.getRowCount();
                for(int i = 0; i <num; i++){
                    if(id.equals(model.getValueAt(i, 0))){
                        courses.clearSelection();
                        courses.setRowSelectionInterval(i, i);
                        break;
                    }
                }
                DefaultTableModel gmodel = (DefaultTableModel)grades.getModel();
                BoxModel sem = (BoxModel)semBox.getModel();
                for(Semester s : cr.getSemesters()){
                    sem.getElementAt(sem.indexOf(s.getSeason()));
                    gmodel.addRow(new Object[]{sem.getElementAt(sem.indexOf(s.getSeason())),s.getYear(),cr.getGrade(s)});
                }
            }
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

    private class MajorActionListener implements ActionListener   {
        @Override
        public void actionPerformed(ActionEvent e) {
                    frame.changeMajor((Major)major.getSelectedItem());
                }

    }
    class BoxModel extends DefaultComboBoxModel{
        Vector<Integer> nums = new Vector<Integer>();
        public void addElm(String disp, int num){
            super.addElement(disp);
            nums.add(num);
        }
        public int indexOf(int i){
            return nums.indexOf(i);
        }
        public int getValAt(int i){
            return nums.get(i);
        }
    }
    private class Editor extends AbstractCellEditor implements TableCellEditor, FocusListener{
        JSpinner yearS;
        public Editor(JSpinner s){
            yearS=s;
            yearS.addFocusListener(this);
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            yearS.setValue(value);
            return yearS;
        }

        @Override
        public Object getCellEditorValue() {
            return yearS.getValue();
        }

        @Override
        public void focusGained(FocusEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void focusLost(FocusEvent e) {
            stopCellEditing();
        }
    }
}