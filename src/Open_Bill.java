import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class Open_Bill extends JFrame {

	private JPanel contentPane;
	private JTextField invoice_textfield_billno;

	/**
	 * Launch the application.


	/**
	 * Create the frame.
	 */
	public Open_Bill() {
		setBackground(Color.LIGHT_GRAY);
		setResizable(false);
		setTitle("Invoice");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 241);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240,255,240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel invoice_label_billno = new JLabel("Enter Bill_No:");
		invoice_label_billno.setFont(new Font("Tahoma", Font.BOLD, 12));
		invoice_label_billno.setBounds(72, 56, 98, 14);
		contentPane.add(invoice_label_billno);
		
		invoice_textfield_billno = new JTextField();
		invoice_textfield_billno.setFont(new Font("Tahoma", Font.BOLD, 11));
		invoice_textfield_billno.setBounds(174, 54, 98, 20);
		contentPane.add(invoice_textfield_billno);
		invoice_textfield_billno.setColumns(10);
		
		JButton invoice_button_getinvoice = new JButton("Get Invoice");
		invoice_button_getinvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Desktop desktop=Desktop.getDesktop();
				try {
					desktop.open(new File(invoice_textfield_billno.getText()+".pdf"));
				} catch (Exception e) {
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e.getMessage());
					error_display_obj.setVisible(true);
				}
			}
		});
		invoice_button_getinvoice.setFont(new Font("Tahoma", Font.BOLD, 11));
		invoice_button_getinvoice.setBounds(117, 117, 105, 23);
		contentPane.add(invoice_button_getinvoice);
	}
}
