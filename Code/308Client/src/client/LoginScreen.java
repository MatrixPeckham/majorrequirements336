package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Screen for login
 * @author JP
 *
 */
public class LoginScreen extends Screen {
        //name label
    	private JLabel userName;
        //password label
	private JLabel passWord;
	//title label
        private JLabel logIn;
        //name text field
	private JTextField nameField;
        //password text field
	private JTextField passWordField;
        //log in button
	private JButton	logInButton;
        //back button
	private JButton back;
        //error button
        private JLabel error;

	/**
	 * serial version ID that eclipse wants in all swing classes
	 */
	private static final long serialVersionUID = -6230641324251613660L;
	/**
	 * Constructor lays out GUI including action listener
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
		logInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                            frame.login(nameField.getText(), passWordField.getText());
                            if(frame.reg){
                                if(frame.getPermissions()==3)
					frame.changeScreen(ClientGUI.DEPARTMENTS, null);
                            } else {
                                if(frame.getPermissions()>1)
        				frame.changeScreen(ClientGUI.MAJORS, null);
                            }
                        }
                });
		logInButton.setFont(new Font("Times New Roman",1,24));
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.changeScreen(ClientGUI.WELCOME, null);
			}
		});
		back.setFont(new Font("Times New Roman",1,24));
                error = new JLabel("Error: Not a valid user and password combination");
                error.setFont(new Font("Times New Roman",1,12));
                error.setForeground(Color.RED);
                error.setVisible(false);
		
		// LAYOUT ALL THE COMPONENTS USING GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		addJComponentToContainerUsingGBL(logIn, this, 1,1,2,1);
		addJComponentToContainerUsingGBL(userName, this, 1,10,1,1);
		addJComponentToContainerUsingGBL(passWord, this, 1,20,1,1);
		addJComponentToContainerUsingGBL(nameField, this, 2, 10, 1, 1);
		addJComponentToContainerUsingGBL(passWordField, this, 2, 20, 1, 1);
		addJComponentToContainerUsingGBL(logInButton, this, 1, 30, 1, 1);
		addJComponentToContainerUsingGBL(back, this, 2, 30, 1, 1);
                addJComponentToContainerUsingGBL(error, this, 1, 40, 1, 1);
	}

	@Override
	public void getScreen(Object fillWith) {
		// TODO Auto-generated method stub
	}

    @Override
    public boolean validateForm() {
        if(nameField.getText().isEmpty()==true)
        {
            error.setVisible(true);
            return false;
        }
        if(passWordField.getText().isEmpty()==true)
        {
            error.setVisible(true);
            return false;
        }
        error.setVisible(false);
        return true;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
