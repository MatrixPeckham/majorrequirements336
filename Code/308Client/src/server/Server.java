/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.net.*;
/**
 *
 * @author TJ
 */
public class Server {
    private static boolean running=true;
     public static void main(String[] args) {
         ServerSocket s=null;
         School.load();
         try {
             s=new ServerSocket(8989);
         }catch(Exception e) {
             running=false;
         }
         while(running) {
             try{
                 Socket client=s.accept();
                 ServerThread st=new ServerThread(client);
                 Thread t=new Thread(st);
                 t.start();
                 Thread.sleep(50);
             }catch(NullPointerException e) {
                 e.printStackTrace();
                 running=false;
             } catch (Exception e) {
                 e.printStackTrace();
                 running=false;
             }
         }
     }
}
