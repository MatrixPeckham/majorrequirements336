package client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import server.*;
/**
 * Main Frame, and starting point of the
 * client program
 * @author Bill
 *
 */
public class ClientGUI extends JFrame implements WindowListener {
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

        //client for handeling networking
        private Client networking;
        //permisions of user
	private int permissions=User.STUDENT;
        //current screen string
	private String curScreen;
        //current manager screen
	private String curMan;
        //map of strings to screens
	private TreeMap<String, Screen> screens = new TreeMap<String, Screen>();
        //toggle for login page to use to decide what to switch to
	boolean reg = false;
	/**
	 * Constructor, initialized and show
	 */
	public ClientGUI(){
		networking = new Client();
            //    Thread thread = new Thread(networking);
              //  thread.start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		setExtendedState(getExtendedState()|MAXIMIZED_BOTH);
                this.addWindowListener(this);
	}

        public static void main(String[] args) {
		ClientGUI gui = new ClientGUI();
                gui.setVisible(true);
	}
        //initializes all the screens
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
        /**
         * changes the screen
         * @param str string that is the code for the GUI to switch to
         */
	public void changeScreen(String str, Object fillWith){
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
                s.getScreen(fillWith);
		this.add(s);
		this.validate();
		this.repaint();
		curScreen=str;
		curMan="";
	}

        /**
         * changes which manager screen is shown from the current screen
         * @param str string that is code for the screen to show
         */
        public void changeManageScreen(String str, Object fillWith){
		if(str.equals(CURR_ADD)){
			Screen p = ((ManagerScreen)screens.get(curScreen)).getAddScreen();
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
                        p.getScreen(fillWith);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_ADD;
		} else if(str.equals(CURR_EDIT)){
			Screen p = ((ManagerScreen)screens.get(curScreen)).getEditScreen();
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
                        p.getScreen(fillWith);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_EDIT;

		} else if(str.equals(CURR_REM)){
			Screen p = ((ManagerScreen)screens.get(curScreen)).getRemoveScreen();
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
                        p.getScreen(fillWith);
			this.add(p);
			this.validate();
			this.repaint();
			curMan=CURR_REM;
		}
	}
	/**
	 * Main Method, doesn't handle arguments (yet?)
	 * @param args doesn't get handled
	 */

        public Schedule generateSchedule() {return networking.generateSchedule();}
	public File getFile(String str) {return networking.getFile(null, str);}
	public int uploadFile(File file, String str) {return networking.uploadFile(file, str);}
	public boolean addCourse(Course c, String str) {return networking.addCourse(c, str);}
	public Course loadCourse(String str) {return networking.loadCourse(str);}
	public boolean removeCourse(String str) {return networking.removeCourse(networking.getCurrentDepartment(),str);}
	public boolean editCourse(Course c, String str, String cstr) {return networking.editCourse(c, str, cstr);}
	public Major loadMajor(String str) {return networking.loadMajor(str);}
        public int changeYear(int i){return networking.changeYear(i);}
	public void addMajor(Major m) {networking.addMajor(m,networking.getCurrentDepartment());}
	public boolean editMajor(Major m, String s) {return networking.editMajor(m,networking.getCurrentDepartment(),s);}
	public boolean removeMajor(String str) {return networking.removeMajor(networking.getCurrentDepartment(),str);}
	public boolean addRequirement(Requirement r, String str) {return networking.addRequirement(r,networking.getCurrentDepartment(),networking.getCurrentMajor().getId());}
	public boolean removeRequirement(String str1, String str2){return networking.removeRequirement(str1, str2);}
	public int login(String usr,String pass) {permissions = networking.login(usr, pass);return permissions;}
	public boolean logout() { permissions=User.STUDENT; return networking.logout();}
        public void uploadCSV(File f) throws Exception {networking.uploadCSV(f);}
        //added
        public ArrayList<Major> getAllMajors() {return networking.getAllMajors();}
	public ArrayList<Requirement> getRequirements() {return networking.getRequirements(networking.getCurrentDepartment(),networking.getCurrentMajor().getId());}
	public int getCreditsRemaining() {return networking.getCreditsRemaining();}
	public void addDepartment(String str, Department dep) {networking.addDepartment(dep);}
	public void removeDepartment (String str) {networking.removeDepartment(str);}
	public void editDepartment(String str, Department d) {networking.editDepartment(str,d);}
	public ArrayList<Department> getDepartments() {return networking.getDepartments();}
	public Department getDepartment(String str) {return networking.getDepartment(str);}
	public ArrayList<Course> getDepartmentCourses(String str) {return networking.getDepartmentCourses(str);}
        public int getPermissions(){return permissions;}


    public User getStudentInfo() {
        return networking.getStudentInfo();
    }

    public void setCurrentDepartment(String dep){
        networking.setCurrentDepartment(dep);
    }
    public String getCurrentDepartment() {
        return networking.getCurrentDepartment();
    }

    CourseRecord getCourseRecord(String str) {
        return networking.getCourseRecord(str);
    }

    boolean removeCourseRecord(String str) {
        return networking.removeCourseRecord(str);
    }

    void downloadFile(File file, String str) {
        networking.downloadFile(file, str);
    }

    String checkSchedule() {
        return networking.checkSchedule();
    }

    void addCourseRecord(CourseRecord r) {
        networking.addCourseRecord(r);
    }

    void editCourseRecord(CourseRecord r) {
        networking.editCourseRecord(r);
    }

    Major getMajor(String string) {
        return networking.getMajor(networking.getCurrentDepartment(),string);
    }

    Major getCurrentMajor() {
        return networking.getCurrentMajor();
    }

    Course getCourse(String string) {
        return networking.getCourse(string);
    }

    ArrayList<Course> getAllCourses() {
        return networking.getAllCourses();
    }

    boolean changeMajor(Major selectedItem) {
        return networking.changeMajor(selectedItem);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        return;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        networking.Exit();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        return;
    }

    @Override
    public void windowIconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        return;
    }

    void removeAllCourseListings() {
        networking.removeAllCourseListings();
    }
 }
