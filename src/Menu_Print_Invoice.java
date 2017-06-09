import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class Menu_Print_Invoice extends JFrame {
	public Menu_Print_Invoice() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\print_invoice.gif"));
		setBackground(SystemColor.inactiveCaption);
		getContentPane().setBackground(SystemColor.inactiveCaption);
	}

	private JPanel contentPane;
	private JTextField print_textfield_billno;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public void Menu_Print_Invoice_do() {
		setResizable(false);
		setTitle("Print Invoice");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\print (1).png"));
	//	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 402, 258);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240,255,240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel print_label_billno = new JLabel("Enter Bill_No: ");
		print_label_billno.setFont(new Font("Tahoma", Font.BOLD, 13));
		print_label_billno.setBounds(85, 37, 111, 31);
		contentPane.add(print_label_billno);
		
		print_textfield_billno = new JTextField();
		print_textfield_billno.setFont(new Font("Tahoma", Font.BOLD, 11));
		print_textfield_billno.setBounds(206, 43, 104, 20);
		contentPane.add(print_textfield_billno);
		print_textfield_billno.setColumns(10);
		
		JButton print_button_print = new JButton("Print");
		print_button_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Print_Invoice obj=new Print_Invoice(Integer.parseInt(print_textfield_billno.getText()));
			}
		});
		print_button_print.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\print (1).png"));
		print_button_print.setFont(new Font("Tahoma", Font.BOLD, 11));
		print_button_print.setBounds(152, 99, 89, 23);
		contentPane.add(print_button_print);
	}
}
