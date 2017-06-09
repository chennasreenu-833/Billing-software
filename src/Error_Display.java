import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Color;

public class Error_Display extends JFrame {

	private JPanel contentPane;
	private JLabel error_label_display;

	public Error_Display() {
		setTitle("Error");
		setBounds(100, 100, 387, 215);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		error_label_display = new JLabel("");
		error_label_display.setHorizontalTextPosition(SwingConstants.CENTER);
		error_label_display.setHorizontalAlignment(SwingConstants.CENTER);
		error_label_display.setBounds(10, 11, 351, 94);
		contentPane.add(error_label_display);
		
		JButton error_button_close = new JButton("Close");
		error_button_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		error_button_close.setFont(new Font("Tahoma", Font.BOLD, 11));
		error_button_close.setBounds(132, 116, 89, 23);
		contentPane.add(error_button_close);
	}
	public void display(String err){
		error_label_display.setText(err);
		error_label_display.setToolTipText(err);
	}
}
