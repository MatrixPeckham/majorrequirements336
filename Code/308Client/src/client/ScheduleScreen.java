package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.Course;
import server.Schedule;
import server.Semester;

/**
 * Screen to view generated schedule
 * @author Bill
 *
 */
public class ScheduleScreen extends Screen {

    //table for schedule
    private JTable table;
    //back button
    private JButton back;
    //title label
    private JLabel sched;
    /**
     * serial version ID that eclipse wants in swing classes
     */
    private static final long serialVersionUID = 6345886273148921957L;

    /**
     * Takes a ClientGUI for the super class
     * @param gui GUI for Screen
     */
    public ScheduleScreen(ClientGUI gui) {
        super(gui);
        initGUI();
    }
    /*
     * lays out GUI including action listeners
     */
    private void initGUI() {
        this.setLayout(new BorderLayout());
        sched = new JLabel("Remaining Schedule");
        sched.setFont(new Font("Times New Roman",1,72));
        String[] columnNames = {"Semester","Class","Credits"};
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        table.setFillsViewportHeight(true);
        /*table.setModel(new DefaultTableModel(){
            @Override
            public java.lang.Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
        });*/

        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (String s : columnNames) {
                    model.addColumn(s);
                }
        JScrollPane scrollPane = new JScrollPane(table);
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.changeScreen(ClientGUI.CLASSES, frame.getStudentInfo());
            }
        });
        add(sched,BorderLayout.NORTH);
        add(scrollPane);
        JPanel p = new JPanel();
        p.add(back);
        add(p,BorderLayout.SOUTH);
    }

    @Override
    public void getScreen(Object fillWith) {
        DefaultTableModel m=((DefaultTableModel)table.getModel());
        int num = m.getRowCount();
       for(int i=0; i<num; i++) {
           m.removeRow(0);
       }
        if(fillWith instanceof Schedule){
            Schedule s = (Schedule) fillWith;
            TreeMap<Semester,Vector<Course>> sched = s.getSchedule();

            Set<Semester> semesters = sched.keySet();
            for(Semester str : semesters){
                Vector<Course> courses = sched.get(str);
                Object[] sem = {str.toString()};
                ((DefaultTableModel)table.getModel()).addRow(sem);
                for(Course c : courses){
                    Object[] obs = {"",c.getId(),c.getCredits()};
                    ((DefaultTableModel)table.getModel()).addRow(obs);
                }
            }
        }
    }

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
