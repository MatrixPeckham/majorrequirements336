package client;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.*;
import java.io.*;
import java.util.Scanner;
/**
 * Main networking class for the client.
 * @author Bill
 *
 */
//TODO finish this class all methods marked with TODO need
//TODO server classes the rest are stubs that need socket interaction
public class Client{

	/**Socket for communication with server*/
	private Socket s;
//TODO public Schedule generateSchedule() {return null;}
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private BufferedReader rdr;
        private PrintWriter pw;
//TODO public Schedule generateSchedule() {return null;}
	
	public File getFile(File location, String str) {
            try{
            FileWriter fw=new FileWriter(location);
            PrintWriter pw2=new PrintWriter(fw);
            pw.println(str);
            String s;
            while(!(s=rdr.readLine()).equals("ENDXML")) {
                pw.println(s);
            }
            pw.flush();
                return location;
            }catch(Exception e) {
                return null;
            }

        }
	public int uploadFile(File file, String str) {
            pw.println(str);
            try{
            Scanner sc=new Scanner(file);
            while(sc.hasNext()) {
                pw.println(sc.nextLine());
            }
            pw.println("ENDXML");
            String s=rdr.readLine();
            return s.equals("OK")?0:Integer.parseInt(s);
            }catch(Exception e) {
            return -1;
            }
        }
//TODO	public boolean addCourse(Course c, String str) {return false;}
//TODO	public Course loadCourse(String str) {return null;}
	public boolean addCourse(Course c, String str) {
            try{
                pw.println(Commands.ADD_CLASS);
                    oos.writeObject(c);
                return Boolean.parseBoolean(rdr.readLine());
            }catch(Exception e) {return false;}
        }
    	public Course loadCourse(String str) {
            try{
                pw.println(Commands.GETCOURSE);
                pw.println(str);
                ois=new ObjectInputStream(s.getInputStream());
                return (Course) ois.readObject();
            }catch(Exception e){return null;}
        }
	public boolean removeCourse(String str) {
            try{
                pw.println(Commands.REMOVE_COURSE);
                pw.println(str);
                return rdr.readLine().equals("OK");
            } catch(Exception e){
                return false;
            }
        }
//TODO	public boolean editCourse(Course c) {return false;}
//TODO	public Major loadMajor(String str) {return null;}
//TODO	public void addMajor(Major m) {}
//TODO	public boolean editMajor(Major m) {return false;}




	public boolean removeMajor(String str) {return false;}
//TODO	public boolean addRequirement(Requirement r, String str) {return false;}

	public boolean removeRequirement(String str1, String str2){return false;}
	public int login(String usr,String pass) {
            pw.println(Commands.LOGIN);
            pw.println(usr);
            pw.println(pass);
            pw.flush();
            try{
            return Integer.parseInt(rdr.readLine());
            } catch(Exception e) {
                return -1;
            }
        }
	public boolean logout() {
            pw.println(Commands.LOGOUT);
            try{
            return rdr.readLine().equals("OK");
            }catch(Exception e) {return false;}
        }
//TODO	public Vector<Requirements> getRequirements() {return null;}

	public int getCreditsRemaining() {return 0;}
//TODO	public void addDepartment(String, Department) {}

	public void removeDepartment (String str) {}
//TODO	public void editDepartment(String str, Department d) {}
//TODO	public Vector<Department> getDepartments() {return null;}
//TODO	public Department getDepartment(String str) {return null;}
//TODO	public Vector<Course> getDepartmentCourses(String) {return null;} 
//TODO	public void editDepartment(String str, Department d) {}
//TODO	public Vector<Department> getDepartments() {return null;}
//TODO	public Department getDepartment(String str) {return null;}
//TODO	public Vector<Course> getDepartmentCourses(String) {return null;}

        Client() {
            try {
                //first thing initiate connection
                s = new Socket("localhost", 8989);
              
                rdr=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(s.getOutputStream(), true);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    Schedule generateSchedule() {
        pw.println(Commands.GETSCHED);
        try{
            ois=new ObjectInputStream(s.getInputStream());
            return (Schedule) ois.readObject();
        }catch(Exception e){
            return null;
        }
    }

    boolean editCourse(Course c) {
        pw.println(Commands.EDIT_COURSE);
        try{
        oos.writeObject(c);
        oos.reset();
        return rdr.readLine().equals("OK");
        }catch(Exception e) {
            return false;
        }
    }

    Major loadMajor(String str) {
        pw.println(Commands.GET_MAJOR);
        pw.println(str);
        try{
            ois=new ObjectInputStream(s.getInputStream());
            return (Major)ois.readObject();
        }catch(Exception e) {
            return null;
        }
    }

    boolean addMajor(Major m, String dept) {
        pw.println(Commands.ADD_MAJOR);
        pw.println(dept);
        try{
            oos.writeObject(m);
            return rdr.readLine().equals("OK");
        } catch(Exception e) {
            return false;
        }
    }

    boolean editMajor(Major m, String dept) {
        pw.println(Commands.EDIT_MAJOR);
        pw.println(dept);

        try{
            oos.writeObject(m);

            return rdr.readLine().equals("OK");
        } catch(Exception e) {
            return false;
        }
    }

    boolean addRequirement(Requirement r, String dept, String major) {

        return false;
    }
    ArrayList<Requirement> getRequirements(String dept, String major) {
       try{
           return new ArrayList<Requirement>(this.getMajor(dept, major).getRequirements());
       } catch(Exception e) {
           return null;
       }
    }

    boolean addDepartment(Department dep) {
        pw.println(Commands.ADD_DEPT);
        try{
            oos.writeObject(dep);
            String s = rdr.readLine();
            return s.equals("OK");
        }catch(Exception e) {
            return false;
        }
    }

    boolean editDepartment(String str, Department d) {
        pw.println(Commands.EDIT_DEPT);
        pw.println(str);
        try{
            oos.writeObject(d);
            return rdr.readLine().equals("OK");
        }catch(Exception e) {
            return false;
        }
    }

    ArrayList<Department> getDepartments() {
        pw.println(Commands.GET_DEPT);
        try{
            ois=new ObjectInputStream(s.getInputStream());
            ArrayList<Department> n=(ArrayList<Department>) ois.readObject();
            rdr.readLine();
            return n;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Department getDepartment(String str) {
        ArrayList<Department> a=getDepartments();
        if(a==null) {return null;}
        for(Department d : a) {
            if(d.getName().equals(str)) {
                return d;
            }
        }
        return null;
    }

    ArrayList<Course> getDepartmentCourses(String str) {
       pw.println(Commands.GETDEPTCOURSES);
       pw.println(str);
       pw.flush();
       try{
        ois=new ObjectInputStream(s.getInputStream());
        ArrayList<Course> arr = (ArrayList<Course>)ois.readObject();
        rdr.readLine();
        return arr;
        }catch(Exception e) {
            return null;
        }
    }
public User getStudentInfo() {
    try{
        return null;
    } catch(Exception e) {
    return null;
    }
}

    String getCurrentDepartment() {
        
        return "CSE";
    }

    CourseRecord getCourseRecord(String str) {
        return null;
    }

    boolean removeCourseRecord(String str) {
        return false;
    }

    ArrayList<Requirement> checkSchedule() {
        pw.println(Commands.GET_REQS);

        return null;
    }

    int addCourseRecord(CourseRecord r) {
        pw.println(Commands.ADD_COURSE_RECORD);
        return sendCourseRecord(r);
    }

    int editCourseRecord(CourseRecord r) {
        pw.println(Commands.EDIT_COURSE_RECORD);
        return sendCourseRecord(r);
    }
    private int sendCourseRecord(CourseRecord r) {
        try{
            oos.writeObject(r);
            return Integer.parseInt(rdr.readLine());
        } catch(Exception e) {
            return -1;
        }
    }
    Major getMajor(String dept, String major) {
        pw.println(Commands.GET_MAJOR);
        pw.println(dept);
        pw.println(major);
        try{
            ois=new ObjectInputStream(s.getInputStream());
            return (Major) ois.readObject();
        } catch(Exception e) {
            return null;
        }
    }

    String getCurrentMajor() {

        return null;
    }

    public Course getCourse(String string) {
        pw.println(Commands.GETCOURSE);
        pw.println(getCurrentDepartment());
        pw.println(string);
        try{
            ois=new ObjectInputStream(s.getInputStream());
            Course c = (Course) ois.readObject();
            rdr.readLine();
            return c;
        } catch(Exception e) {
            return null;
        }
    }

    public void downloadFile(File file, String str) {
        getFile(file,str);
    }

    ArrayList<Course> getAllCourses() {
        return null;
    }
}
