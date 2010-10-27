package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestGUI extends JFrame
{
	private JPanel northPanel;
	private JPanel southPanel;
	private JLabel userName;
	private JLabel passWord;
	private JLabel logIn;
	private JTextField nameField;
	private JTextField passWordField;
	
	TestGUI()
	{
		super("Log In");
		this.setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		layoutGUI();
	}
	
	public void layoutGUI()
	{
		userName = new JLabel("User Name: ");
		userName.setFont(new Font("Times New Roman",1,24));
		passWord = new JLabel("Password: ");
		passWord.setFont(new Font("Times New Roman",1,24));
		logIn = new JLabel("Please Log In");
		logIn.setFont(new Font("Times New Roman",1,72));
		nameField = new JTextField(15);
		passWordField = new JTextField(15);
		
		// LAYOUT ALL THE COMPONENTS USING GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		addJComponentToContainerUsingGBL(logIn, this, 1,1,1,1);
		addJComponentToContainerUsingGBL(userName, this, 1,10,1,1);
		addJComponentToContainerUsingGBL(passWord, this, 1,20,1,1);
		addJComponentToContainerUsingGBL(nameField, this, 2, 10, 1, 1);
	}
	
	public void addJComponentToContainerUsingGBL(JComponent jc, Container c,
			int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	public static void main(String[]args)
	{
		TestGUI frame = new TestGUI();
		frame.setVisible(true);
	}
}
