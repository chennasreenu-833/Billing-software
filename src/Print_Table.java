import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Print_Table extends JFrame{

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Print_Table(Object[][] print_table_data,String[] print_table_columnnames) {
		setTitle("Print Table");
		setResizable(false);
		setBounds(100, 100,  722, 368);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240,255,240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 696, 287);
		contentPane.add(scrollPane);
		table=new JTable(print_table_data,print_table_columnnames);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		table.setBackground(new Color(240,255,240));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,11));
		scrollPane.setViewportView(table);
		
		JButton print_button_exit = new JButton("Exit");
		print_button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                dispose();
			}
		});
		print_button_exit.setBounds(317, 309, 89, 23);
		contentPane.add(print_button_exit);
	}

}
