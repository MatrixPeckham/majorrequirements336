package client;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Screen for login
 * @author Bill
 *
 */
public class LoginScreen extends Screen {
	
	private JLabel userName;
	private JLabel passWord;
	private JLabel logIn;
	private JTextField nameField;
	private JTextField passWordField;
	private JButton	logInButton;

	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -6230641324251613660L;
	/**
	 * Constructor
	 * @param gui GUI for parent Screen
	 */
	public LoginScreen(ClientGUI gui) 
	{
		super(gui);
		
		userName = new JLabel("User Name: ");
		userName.setFont(new Font("Times New Roman",1,24));
		passWord = new JLabel("Password: ");
		passWord.setFont(new Font("Times New Roman",1,24));
		logIn = new JLabel("Please Log In");
		logIn.setFont(new Font("Times New Roman",1,72));
		nameField = new JTextField(15);
		passWordField = new JTextField(15);
		logInButton = new JButton("Log In");
		logInButton.setFont(new Font("Times New Roman",1,24));
		
		// LAYOUT ALL THE COMPONENTS USING GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		addJComponentToContainerUsingGBL(logIn, this, 1,1,2,1);
		addJComponentToContainerUsingGBL(userName, this, 1,10,1,1);
		addJComponentToContainerUsingGBL(passWord, this, 1,20,1,1);
		addJComponentToContainerUsingGBL(nameField, this, 2, 10, 1, 1);
		addJComponentToContainerUsingGBL(passWordField, this, 2, 20, 1, 1);
		addJComponentToContainerUsingGBL(logInButton, this, 2, 30, 1, 1);
	}

	@Override
	public void getScreen() {
		// TODO Auto-generated method stub

	}

}
