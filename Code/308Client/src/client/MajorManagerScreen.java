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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.postgresql.jdbc3.Jdbc3SimpleDataSource;

/**
 * Manager screen for the majors
 * @author Bill
 *
 */
public class MajorManagerScreen extends Screen implements ManagerScreen {

    /**
     * serial version ID that eclipse wants in all swing classes
     */
    private static final long serialVersionUID = -747564907385911678L;
    private AddMajScreen addScreen = new AddMajScreen();
    private EditMajScreen editScreen = new EditMajScreen();
    private RemoveMajScreen remScreen = new RemoveMajScreen();
    private JLabel adminLabel;
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton editMajor;
    private JButton back;

    /**
     * constructor
     * @param gui passed to super class Screen
     */
    public MajorManagerScreen(ClientGUI gui) {
        super(gui);
        initGUI();
    }

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
                JOptionPane.showInputDialog("Input Major Name");
            }
        });
        editButton = new JButton("Edit Major");
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showInputDialog("Change Major Name");
            }
        });
        removeButton = new JButton("Remove Major");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            }
        });
        editMajor = new JButton("Edit Major Requiremnts");
        editMajor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeManageScreen(ClientGUI.CURR_EDIT);
            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.changeScreen(ClientGUI.WELCOME);
            }
        });
        addJComponentToContainerUsingGBL(adminLabel, this, 1, 1, 4, 1);
        addJComponentToContainerUsingGBL(scrollPane, this, 1, 2, 4, 2);
        addJComponentToContainerUsingGBL(addButton, this, 1, 4, 1, 1);
        addJComponentToContainerUsingGBL(editButton, this, 2, 4, 1, 1);
        addJComponentToContainerUsingGBL(removeButton, this, 3, 4, 1, 1);
        addJComponentToContainerUsingGBL(editMajor, this, 1, 5, 1, 1);
        addJComponentToContainerUsingGBL(back, this, 3, 5, 1, 1);
    }

    @Override
    public void getScreen() {
        // TODO Auto-generated method stub
    }

    @Override
    public JPanel getAddScreen() {
        return addScreen;
    }

    @Override
    public JPanel getEditScreen() {
        return editScreen;
    }

    @Override
    public JPanel getRemoveScreen() {
        return remScreen;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for adding Majors
     * @author Bill
     *
     */
    //TODO make this into the add screen GUI
    private class AddMajScreen extends JPanel {

        JLabel addPage;
        JLabel courseL;
        JTable courses;
        JButton addButton;
        JButton remButton;
        JLabel currL;
        JList currList;
        JCheckBox exclude;
        JButton finishButton;
        JLabel seqL;
        JList seqList;
        JLabel minSeqL;
        JSpinner seqS;
        JLabel infoL;
        JLabel nameL;
        JTextField nameF;
        JLabel minGPAL;
        JTextField minGPAF;
        JLabel minUpperL;
        JSpinner minUpperS;
        JButton ok;
        JButton cancel;

        public AddMajScreen() {
            initGUI();
        }

        private void initGUI() {
            addPage = new JLabel("Add Requirement Page");
            addPage.setFont(new Font("Times New Roman", 1, 72));
            courseL = new JLabel("Courses");
            String[] columnNames = {"Department", "ID", "Credits", "Prereq"};
            Object[][] data = {
                {"CSE", "308", "3", "CSE 219"}, {"CSE", "381", "3", "CSE 219"}, {"CSE", "380", "3", "CSE 219"}, {"CSE", "220", "3", "CSE 219"},
                {"CSE", "114", "3", "CSE 219"}, {"CSE", "215", "3", "CSE 219"}, {"CSE", "219", "3", "CSE 219"}, {"CSE", "110", "3", "CSE 219"},
                {"MAT", "127", "3", "CSE 219"}};
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

                }
            });
            remButton = new JButton("<- Remove");
            remButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

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
                    frame.changeManageScreen(ClientGUI.CURR_EDIT);
                }
            });
            cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.changeManageScreen(ClientGUI.CURR_EDIT);
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
        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = 4099112134368019745L;
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for Editing Majors
     * @author Bill
     *
     */
    //TODO make this into the edit screen GUI
    private class EditMajScreen extends JPanel {

        JLabel title;
        JTable table;
        JButton addReq;
        JButton editReq;
        JButton remReq;
        JButton back;

        public EditMajScreen() {
            initGUI();
        }

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
                    frame.changeManageScreen(ClientGUI.CURR_ADD);
                }
            });
            editReq = new JButton("Edit");
            editReq.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.changeManageScreen(ClientGUI.CURR_ADD);
                }
            });
            remReq = new JButton("Remove");
            back = new JButton("Back");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.changeScreen(ClientGUI.MAJORS);
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
    }

    /**
     * Inner class the the outside need know nothing about
     * it will be the screen for removing Majors
     * @author Bill
     *
     */
    //TODO make this into the remove screen GUI
    private class RemoveMajScreen extends JPanel {

        /**
         * serial version ID that eclipse wants in all swing classes
         */
        private static final long serialVersionUID = -3920339673618333723L;
    }
}
