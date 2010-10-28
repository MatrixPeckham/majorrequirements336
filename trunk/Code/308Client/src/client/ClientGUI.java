package client;

import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Main Frame, and starting point of the 
 * client program
 * @author Bill
 *
 */
public class ClientGUI extends JFrame {
	/*
	 * String ID's of states
	 */
	public static final String WELCOME = "WELCOME";
	public static final String LOGIN = "LOGIN";
	public static final String CLASSES = "CLASSES";
	public static final String COURSES = "COURSES";
	public static final String DEPARTMENTS = "DEPARTMENTS";
	public static final String MAJORS = "MAJORS";
	public static final String OPTIONS = "OPTIONS";
	public static final String CHECK = "CHECK";
	public static final String SCHEDULE = "SCHEDULE";
	public static final String CURR_EDIT = "CURR_EDIT";
	public static final String CURR_ADD = "CURR_ADD";
	public static final String CURR_REM = "CURR_REM";	
	/**
	 * just a serial version ID that eclipse always wants in swing classes
	 */
	private static final long serialVersionUID = -5678564321234343567L;
	private Client networking;
	private int permissions;
	private String curScreen;
	private String curMan;
	private TreeMap<String, Screen> screens = new TreeMap<String, Screen>();
	boolean reg = false;
	/**
	 * Constructor, initialized and show
	 */
	public ClientGUI(){
		networking = new Client();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		setExtendedState(getExtendedState()|MAXIMIZED_BOTH);
		this.setVisible(true);
	}
//TODO the two if's are only there because eclispe gave warnings	
	private void init(){
		screens.put(WELCOME, new WelcomeScreen(this));
		screens.put(LOGIN, new LoginScreen(this));
		screens.put(CLASSES, new ClassesManagerScreen(this));
		screens.put(COURSES, new CourseManagerScreen(this));
		screens.put(DEPARTMENTS, new DepartmentManagerScreen(this));
		screens.put(MAJORS, new MajorManagerScreen(this));
		screens.put(OPTIONS, new OptionsScreen(this,0));
		screens.put(CHECK, new RequirementsScreen(this));
		screens.put(SCHEDULE, new ScheduleScreen(this));
		curScreen = WELCOME;
		this.add(screens.get(curScreen));
		curMan="";
	}
	public void changeScreen(String str){
		Screen s = screens.get(str);
		if(s==null){
			throw new UnsupportedOperationException("Cannot go to Screen " + str + ", it doesn't exist");
		}
		JPanel r = screens.get(curScreen);
		if(!curMan.equals("")){
			ManagerScreen man = (ManagerScreen)(r);
			if(curMan.equals(CURR_ADD)){
				r=man.getAddScreen();
			} else if(curMan.equals(CURR_EDIT)){
				r=man.getEditScreen();
			} else if(curMan.equals(CURR_REM)){
				r=man.getRemoveScreen();
			}
		}
		this.remove(r);
		this.add(s);
		this.validate();
		this.repaint();
		curScreen=str;
		curMan="";
	}
	//TODO most of the methods are not easily implemented due to lack of server types
	public void changeManageScreen(String str){
		if(str.equals(CURR_ADD)){
			JPanel p = ((ManagerScreen)screens.get(curScreen)).getAddScreen();
			if(p==null){
				throw new UnsupportedOperationException("Cannot go to Screen " + str + ", it doesn't exist");
			}
			JPanel r = screens.get(curScreen);
			if(curMan.equals(CURR_EDIT)){
				r=((ManagerScreen)screens.get(curScreen)).getEditScreen();
			} else if(curMan.equals(CURR_REM)){
				r=((ManagerScreen)screens.get(curScreen)).getRemoveScreen();
			}
			this.remove(r);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_ADD;
		} else if(str.equals(CURR_EDIT)){
			JPanel p = ((ManagerScreen)screens.get(curScreen)).getEditScreen();
			if(p==null){
				throw new UnsupportedOperationException("Cannot go to Screen " + str + ", it doesn't exist");
			}
			JPanel r = screens.get(curScreen);
			if(curMan.equals(CURR_ADD)){
				r=((ManagerScreen)screens.get(curScreen)).getAddScreen();
			} else if(curMan.equals(CURR_REM)){
				r=((ManagerScreen)screens.get(curScreen)).getRemoveScreen();
			}
			this.remove(r);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_EDIT;
			
		} else if(str.equals(CURR_REM)){
			JPanel p = ((ManagerScreen)screens.get(curScreen)).getRemoveScreen();
			if(p==null){
				throw new UnsupportedOperationException("Cannot go to Screen " + str + ", it doesn't exist");
			}
			JPanel r = screens.get(curScreen);
			if(curMan.equals(CURR_ADD)){
				r=((ManagerScreen)screens.get(curScreen)).getAddScreen();
			} else if(curMan.equals(CURR_EDIT)){
				r=((ManagerScreen)screens.get(curScreen)).getEditScreen();
			}
			this.remove(r);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_REM;
		}
	}
	/**
	 * Main Method, doesn't handle arguments (yet?)
	 * @param args
	 */
	public static void main(String[] args) {
		new ClientGUI();
	}

}
