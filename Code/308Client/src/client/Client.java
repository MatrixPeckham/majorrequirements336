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
import java.util.Collection;
import java.util.Scanner;
import server.Commands;
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
            oos.writeObject(str);
            String s;
            while(!(s=(String) ois.readObject()).equals("ENDXML")) {
                oos.writeObject(s);
            }
            pw.flush();
                return location;
            }catch(Exception e) {
                return null;
            }

        }
	public int uploadFile(File file, String str) {
            try{
            oos.writeObject(str);
            
            Scanner sc=new Scanner(file);
            while(sc.hasNext()) {
                oos.writeObject(sc.nextLine());
            }
            oos.writeObject("ENDXML");
            String s=(String) ois.readObject();
            return s.equals("OK")?0:Integer.parseInt(s);
            }catch(Exception e) {
            return -1;
            }
        }
//TODO	public boolean addCourse(Course c, String str) {return false;}
//TODO	public Course loadCourse(String str) {return null;}
	public boolean addCourse(Course c, String str) {
            try{
                oos.writeObject(Commands.ADD_CLASS);
                    oos.writeObject(c);
                return Boolean.parseBoolean((String) ois.readObject());
            }catch(Exception e) {return false;}
        }
    	public Course loadCourse(String str) {
            try{
                oos.writeObject(Commands.GETCOURSE);
                oos.writeObject(str);
                ois=new ObjectInputStream(s.getInputStream());
                return (Course) ois.readObject();
            }catch(Exception e){return null;}
        }
	public boolean removeCourse(String str) {
            try{
                oos.writeObject(Commands.REMOVE_COURSE);
                oos.writeObject(str);
                return ((String) ois.readObject()).equals("OK");
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
            try{
            oos.writeObject(Commands.LOGIN);
            oos.writeObject(usr);
            oos.writeObject(pass);
            pw.flush();
            
            return Integer.parseInt((String) ois.readObject());
            } catch(Exception e) {
                return -1;
            }
        }
	public boolean logout() {
            try{
            oos.writeObject(Commands.LOGOUT);
  
            return ((String) ois.readObject()).equals("OK");
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
                ois=new ObjectInputStream(s.getInputStream());
                oos=new ObjectOutputStream(s.getOutputStream());
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    Schedule generateSchedule() {
        try{
            oos.writeObject(Commands.GETSCHED);
        
            //ois=new ObjectInputStream(s.getInputStream());
            return (Schedule) ois.readObject();
        }catch(Exception e){
            return null;
        }
    }

    boolean editCourse(Course c) {
        try{
        oos.writeObject(Commands.EDIT_COURSE);

            oos.reset();
        oos.writeObject(c);
        
        return ((String) ois.readObject()).equals("OK");
        }catch(Exception e) {
            return false;
        }
    }

    Major loadMajor(String str) {
        try{
        oos.writeObject(Commands.GET_MAJOR);
        oos.writeObject(str);
                   //ois=new ObjectInputStream(s.getInputStream());
            return (Major)ois.readObject();
        }catch(Exception e) {
            return null;
        }
    }

    boolean addMajor(Major m, String dept) {
        try{
        oos.writeObject(Commands.ADD_MAJOR);
        oos.writeObject(dept);
            oos.reset();
            oos.writeObject(m);
            return ((String) ois.readObject()).equals("OK");
        } catch(Exception e) {
            return false;
        }
    }

    boolean editMajor(Major m, String dept) {
        try{
        oos.writeObject(Commands.EDIT_MAJOR);
        oos.writeObject(dept);
            oos.reset();
            oos.writeObject(m);

            return ((String) ois.readObject()).equals("OK");
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
        boolean ret = false;
        try{
        oos.writeObject(Commands.ADD_DEPT);
             
             //oos=new ObjectOutputStream(s.getOutputStream());
             
             oos.writeObject(dep);
             oos.flush();
             String s=(String) ois.readObject();
             System.out.println(s);
            return s.equals("OK");
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean editDepartment(String str, Department d) {
        try{
        oos.writeObject(Commands.EDIT_DEPT);
        oos.writeObject(str);
            

            oos.writeObject(d);

            return ((String) ois.readObject()).equals("OK");
        }catch(Exception e) {
            return false;
        }
    }

    ArrayList<Department> getDepartments() {
        pw.flush();
        try{
        oos.writeObject(Commands.GET_DEPT);
        oos.flush();
           // ois=new ObjectInputStream(s.getInputStream());
           //System.out.println((String) ois.readObject());
            ArrayList<Department> a=(ArrayList<Department>)ois.readObject();
            System.out.println((String) ois.readObject());
            return a;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Department getDepartment(String str) {
        ArrayList<Department> a=getDepartments();
        Department ret = null;
        if(a==null) {return null;}
        for(Department d : a) {
            if(d.getName().equals(str)) {
                ret = d;
                break;
            }
        }
        try {
            rdr.readLine();
        } catch (IOException ex) {

        }
        return ret;
    }

    ArrayList<Course> getDepartmentCourses(String str) {
        try{
       oos.writeObject(Commands.GETDEPTCOURSES);
       oos.writeObject(str);
       pw.flush();
        //ois=new ObjectInputStream(s.getInputStream());
        ArrayList<Course> arr = (ArrayList<Course>)ois.readObject();
        ois.readObject();
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
       try{
        oos.writeObject(Commands.GET_REQS);

        return null;
        }catch(Exception e) {return  null;}

    }

    int addCourseRecord(CourseRecord r) {
        try{
        oos.writeObject(Commands.ADD_COURSE_RECORD);
        return sendCourseRecord(r);
        }catch(Exception e){return -1;}
    }

    int editCourseRecord(CourseRecord r) {
        try{

        oos.writeObject(Commands.EDIT_COURSE_RECORD);
        return sendCourseRecord(r);
        }catch(Exception e){return -1;}
    }
    private int sendCourseRecord(CourseRecord r) {
        try{
            oos.writeObject(r);
            return Integer.parseInt((String) ois.readObject());
        } catch(Exception e) {
            return -1;
        }
    }
    Major getMajor(String dept, String major) {
        try{
        oos.writeObject(Commands.GET_MAJOR);
        oos.writeObject(dept);
        oos.writeObject(major);
            ois=new ObjectInputStream(s.getInputStream());
            Major m = (Major) ois.readObject();
            rdr.readLine();
            return m;
        } catch(Exception e) {
            return null;
        }
    }

    String getCurrentMajor() {

        return null;
    }

    public Course getCourse(String string) {
        try{
        oos.writeObject(Commands.GETCOURSE);
        oos.writeObject(getCurrentDepartment());
        oos.writeObject(string);
            ois=new ObjectInputStream(s.getInputStream());
            Course c = (Course) ois.readObject();
            ois.readObject();
            return c;
        } catch(Exception e) {
            return null;
        }
    }

    public void downloadFile(File file, String str) {
        getFile(file,str);
    }

    ArrayList<Course> getAllCourses() {

        try{
            ois=new ObjectInputStream(s.getInputStream());
            Object o = ois.readObject();
            rdr.readLine();
            ArrayList<Course> c = new ArrayList<Course>((Collection<Course>)o);
            return c;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
