package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
        Object[][] data = {
            {"CSE 308", "A", new Boolean(false)},
            {"CSE 381", "B", new Boolean(false)},
            {"CSE 380", "C", new Boolean(false)},
            {"CSE 220", "A-", new Boolean(false)},
            {"CSE 114", "I", new Boolean(false)},
            {"CSE 215", "A", new Boolean(false)},
            {"CSE 219", "C+", new Boolean(false)},
            {"CSE 110", "B+", new Boolean(false)},
            {"MAT 127", "D", new Boolean(false)}};
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
                frame.changeManageScreen(ClientGUI.CURR_EDIT, null);
            }
        });
        removeButton = new JButton("Remove Course");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //frame.changeScreen(ClientGUI.CLASSES);
            }
        });
        uploadButton = new JButton("Upload Courses");

        downloadButton = new JButton("Dowload Courses");

        checkButton = new JButton("Check Requirements");
        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.CHECK, null);
            }
        });

        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.CHECK, null);
            }
        });
        generateButton = new JButton("Generate Schedule");
        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.SCHEDULE, null);
            }
        });
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.WELCOME, null);

            }
        });
        this.setLayout(new GridBagLayout());
        addJComponentToContainerUsingGBL(studentPage, this, 1, 1, 4, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(uploadButton, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(downloadButton, this, 3, 5, 1, 1);
        addJComponentToContainerUsingGBL(checkButton, this, 1, 6, 1, 1);
        addJComponentToContainerUsingGBL(generateButton, this, 3, 6, 1, 1);
        addJComponentToContainerUsingGBL(backButton, this, 5, 7, 1, 1);
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
                    frame.changeScreen(ClientGUI.CLASSES, null);
                }
            });
            cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.changeScreen(ClientGUI.CLASSES, null);
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

        @Override
        public void getScreen(Object fillWith) {

        }

        @Override
        public boolean validateForm() {
            return false;
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
            return false;
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
