import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Accounts_User extends JFrame {

	private JPanel contentPane;
	private JTextField user_textfield_userid;
	private JPasswordField user_passwordfield_password;
	private JFrame frame;

	/**
	 * Launch the application.
	

	/**
	 * Create the frame.
	 */
	public Accounts_User() {
		setTitle("User Details");
		setResizable(false);
		setBounds(100, 100, 383, 282);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240,255,240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel user_label_userid = new JLabel("User ID :");
		user_label_userid.setFont(new Font("Tahoma", Font.BOLD, 11));
		user_label_userid.setBounds(108, 46, 69, 14);
		contentPane.add(user_label_userid);
		
		user_textfield_userid = new JTextField();
		user_textfield_userid.setFont(new Font("Tahoma", Font.BOLD, 11));
		user_textfield_userid.setBounds(189, 43, 107, 20);
		contentPane.add(user_textfield_userid);
		user_textfield_userid.setColumns(10);
		
		JLabel user_label_password = new JLabel("Password :");
		user_label_password.setFont(new Font("Tahoma", Font.BOLD, 11));
		user_label_password.setBounds(108, 79, 79, 14);
		contentPane.add(user_label_password);
		
		user_passwordfield_password = new JPasswordField();
		user_passwordfield_password.setBounds(189, 76, 107, 20);
		contentPane.add(user_passwordfield_password);
		
		JButton user_button_login = new JButton("Log in");
		user_button_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		user_button_login.setFont(new Font("Tahoma", Font.BOLD, 11));
		user_button_login.setBounds(157, 131, 89, 23);
		contentPane.add(user_button_login);
	}
}
