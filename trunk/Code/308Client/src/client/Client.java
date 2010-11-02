package client;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import server.*;
/**
 * Main networking class for the client.
 * @author Bill
 *
 */
//TODO finish this class all methods marked with TODO need
//TODO server classes the rest are stubs that need socket interaction
public class Client implements Runnable{
	/**Socket for communication with server*/
	private Socket s;
        public Schedule generateSchedule() {return null;}
	public File getFile(String str) {return null;}
	public int uploadFile(File file, String str) {return 0;}
	public boolean addCourse(Course c, String str) {return false;}
	public Course loadCourse(String str) {return null;}
	public boolean removeCourse(String str) {return false;}
	public boolean editCourse(Course c) {return false;}
	public Major loadMajor(String str) {return null;}
	public void addMajor(Major m) {}
	public boolean editMajor(Major m) {return false;}
	public boolean removeMajor(String str) {return false;}
	public boolean addRequirement(Requirement r, String str) {return false;}
	public boolean removeRequirement(String str1, String str2){return false;}
	public int login(String usr,String pass) {return 3;}
	public boolean logout() {return false;}
	public ArrayList<Requirements> getRequirements() {return null;}
	public int getCreditsRemaining() {return 0;}
	public void addDepartment(String str, Department dep) {}
	public void removeDepartment (String str) {}
	public void editDepartment(String str, Department d) {}
	public ArrayList<Department> getDepartments() {return null;}
	public Department getDepartment(String str) {return null;}
	public ArrayList<Course> getDepartmentCourses(String str) {return null;}

	@Override
	//TODO finish
	public void run() {
		if(s==null){
			return;
		}
	}
}