package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Screen to view generated schedule
 * @author Bill
 *
 */
public class ScheduleScreen extends Screen {

    private JTable table;
    private JButton back;
    private JLabel sched;
    /**
     * serial version ID that eclipse wants in swing classes
     */
    private static final long serialVersionUID = 6345886273148921957L;

    /**
     * Takes a ClientGUI for the super class
     * should take a schedule object, but that doesn't
     * exist at the time of writing this.
     * @param gui GUI for Screen
     * TODO add schedule parameter because Diagram says to
     * not sure it needs it though
     */
    public ScheduleScreen(ClientGUI gui) {
        super(gui);
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        sched = new JLabel("Remaining Schedule");
        sched.setFont(new Font("Times New Roman",1,72));
        String[] columnNames = {"Class","Semester","Requirement","Credits"};
        Object[][] data = {};
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               frame.changeScreen(ClientGUI.CLASSES);
            }
        });
        add(sched,BorderLayout.NORTH);
        add(scrollPane);
        JPanel p = new JPanel();
        p.add(back);
        add(p,BorderLayout.SOUTH);
    }

    @Override
    public void getScreen() {
        // TODO Auto-generated method stub
    }
}
