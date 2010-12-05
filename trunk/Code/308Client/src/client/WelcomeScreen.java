package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import server.Commands;
import server.User;
/**
 * Welcome screen
 * @author Bill
 *
 */
public class WelcomeScreen extends Screen {
	/**
	 * serial version ID that eclispe wants in all swing classes
	 */
	private static final long serialVersionUID = 4768193727341498557L;

        /*
         * Button that brings user to student page
         */
	private JButton studentButton;
        /*
         * the following two buttons bring the suer
         * to the login screen followed by the
         * respecive admin screen
         */
	private JButton adminButton;
	private JButton superAdminButton;
        private JButton logoffbutton;
	
	/**
	 * Constructor sets up the GUI
	 * @param gui GUI for parent Screen
	 */
	public WelcomeScreen(ClientGUI gui) {
		super(gui);
		initGUI();
	}
	/*
         * Sets up the GUI including all action listeners
         */
	private void initGUI(){
		studentButton = new JButton("Student");
		studentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                            Object o = frame.getStudentInfo();
				frame.changeScreen(ClientGUI.CLASSES, o);
			}
		});
		adminButton = new JButton("Department Administrator");
		adminButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                            if(frame.getPermissions()<User.DEPT_ADMIN){
				frame.reg = false;
      				frame.changeScreen(ClientGUI.LOGIN, null);
                            } else if(frame.getPermissions()>User.DEPT_ADMIN) {
                                if(frame.getCurrentDepartment().equals("")) {
                                    Object o =frame.getDepartments();
                                    frame.changeScreen(ClientGUI.DEPARTMENTS, o);
                                } else {
                                    Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                                    frame.changeScreen(ClientGUI.MAJORS, o);
                                }
                            }else {
                                Object o = frame.getDepartment(frame.getCurrentDepartment()).getMajors();
                                frame.changeScreen(ClientGUI.MAJORS, o);
                            }
			}
		});
		superAdminButton = new JButton("Registrar Administrator");
		superAdminButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                            if(frame.getPermissions()<User.SUPER_ADMIN){
				frame.reg = true;
				frame.changeScreen(ClientGUI.LOGIN, null);
                            } else {
                                Object o =frame.getDepartments();
                                frame.changeScreen(ClientGUI.DEPARTMENTS, o);
                            }
			}
		});
                logoffbutton = new JButton("Log Off");
                logoffbutton.addActionListener(new ActionListener()  {

                        @Override
                        public void actionPerformed(ActionEvent e)  {
                             frame.logout();
                             frame.changeScreen(ClientGUI.WELCOME, null);
                        }
                });
                this.setLayout(new BorderLayout());
                JPanel pane = new JPanel();
                JLabel lab = new JLabel("Welcome");
                pane.add(lab);
                lab.setFont(new Font("Times New Roman",1,72));
                this.add(pane,BorderLayout.NORTH);
                pane=new JPanel();
                pane.setLayout(new GridBagLayout());
                logoffbutton.setVisible(false);
		addJComponentToContainerUsingGBL(studentButton, pane, 1, 2, 1, 1);
		addJComponentToContainerUsingGBL(adminButton, pane, 2, 2, 1, 1);
		addJComponentToContainerUsingGBL(superAdminButton, pane, 3, 2, 1, 1);
		addJComponentToContainerUsingGBL(logoffbutton, pane, 3, 4, 1, 1);
                this.add(pane);
	}

	@Override
	public void getScreen(Object fillWith) {
		if (frame.getPermissions() > User.STUDENT)
                    logoffbutton.setVisible(true);
                else
                    logoffbutton.setVisible(false);
	}

    @Override
    public boolean validateForm() {
        return true;
    }
}
