import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Add_Items extends JFrame {

	private JPanel contentPane;
	private JTextField add_item_textfield_name;
	private JLabel add_item_lable_code;
	private JTextField add_item_txtfld_code;
	private JLabel add_item_lable_rate;
	private JTextField add_item_txtfld_rate;

	public Add_Items() {
		setResizable(false);
		setBackground(Color.LIGHT_GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\add_item (2).png"));
		setTitle("Add New Item");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240,255,240));
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(null);
		
		JLabel add_item_lable_name = new JLabel("Name");
		add_item_lable_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		add_item_lable_name.setBounds(83, 55, 46, 14);
		contentPane.add(add_item_lable_name);
		setContentPane(contentPane);
		
		add_item_textfield_name = new JTextField();
		add_item_textfield_name.setBounds(155, 53, 213, 20);
		contentPane.add(add_item_textfield_name);
		add_item_textfield_name.setColumns(10);
		
		add_item_lable_code = new JLabel("Code");
		add_item_lable_code.setFont(new Font("Tahoma", Font.BOLD, 13));
		add_item_lable_code.setBounds(83, 99, 46, 14);
		contentPane.add(add_item_lable_code);
		
		add_item_txtfld_code = new JTextField();
		add_item_txtfld_code.setBounds(155, 97, 125, 20);
		contentPane.add(add_item_txtfld_code);
		add_item_txtfld_code.setColumns(10);
		
		add_item_lable_rate = new JLabel("Rate");
		add_item_lable_rate.setFont(new Font("Tahoma", Font.BOLD, 13));
		add_item_lable_rate.setBounds(83, 141, 46, 14);
		contentPane.add(add_item_lable_rate);
		
		add_item_txtfld_rate = new JTextField();
		add_item_txtfld_rate.setBounds(155, 139, 101, 20);
		contentPane.add(add_item_txtfld_rate);
		add_item_txtfld_rate.setColumns(10);
		
		JButton add_item_button_add = new JButton("ADD");
		add_item_button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name=add_item_textfield_name.getText();
				String code=add_item_txtfld_code.getText();
				double rate=Double.parseDouble(add_item_txtfld_rate.getText());
				PropagateTable propagate_table_obj=new PropagateTable();
				propagate_table_obj.include_item(name, code, rate);
				propagate_table_obj.close_connection();
			}
		});
		add_item_button_add.setFont(new Font("Tahoma", Font.BOLD, 14));
		add_item_button_add.setBounds(175, 203, 89, 23);
		contentPane.add(add_item_button_add);
	}
}
