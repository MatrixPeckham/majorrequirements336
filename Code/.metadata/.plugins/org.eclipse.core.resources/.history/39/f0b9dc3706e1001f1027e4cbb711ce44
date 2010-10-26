package client;

import javax.swing.JFrame;
/**
 * Main Frame, and starting point of the 
 * client program
 * @author Bill
 *
 */
public class ClientGUI extends JFrame {

	/**
	 * just a serial version ID that eclipse always wants in swing classes
	 */
	private static final long serialVersionUID = -5678564321234343567L;
	private Client networking;
	private int permissions;
	//TODO add screens when implemented
	/**
	 * Constructor, initialized and show
	 */
	public ClientGUI(){
		networking = new Client();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		this.setVisible(true);
	}
//TODO the two if's are only there because eclispe gave wrnings	
	private void init(){
		if(networking==null){
			return;
		}
		if(permissions==-1){
			return;
		}
	}
	/**
	 * Main Method, doesn't handle arguments (yet?)
	 * @param args
	 */
	public static void main(String[] args) {
		new ClientGUI();
	}
	//TODO most of the methods are not easily implemented due to lack of server types
	public void changeScreen(String str){
		
	}
	public void changeManageScreen(String str){
		
	}

}
