import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Customer_Business extends JFrame {

	private JPanel contentPane;
	private JTextField customer_textfield_getcode;
	private JTextField customer_textfield_getcellno;
	private JTextField customer_textfield_name;
	private JTextField customer_textfield_code;
	private JLabel customer_label_code;
	private JLabel customer_label_name;
	private JLabel customer_label_cellno;
	private JTextField customer_textfield_cellno;
	private JScrollPane scrollPane;
	private JTable customer_table;
	private TableModel model;
	private String code;
	private JLabel customer_label_total;
	private JTextField customer_textfield_total;
	private JButton customer_button_new;
	private Connection c=null;
	private Statement smt=null;
	private int count;
	
	/**
	 * Launch the application.
	

	/**
	 * Create the frame.
	 */
	public Customer_Business() {
		try{
			Class.forName("org.sqlite.JDBC");
			c=DriverManager.getConnection("jdbc:sqlite:bill.db");
		}catch(Exception e){
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(e.getMessage());
			error_display_obj.setVisible(true);
		}

		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\customer.png"));
		setFont(new Font("Dialog", Font.BOLD, 11));
		setTitle("Customer_Statistics");
		setResizable(false);
		setBounds(100, 100, 518, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(240,255,240));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel customer_label_getcode = new JLabel("Enter Customer Code:");
		customer_label_getcode.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_getcode.setBounds(30, 14, 139, 14);
		contentPane.add(customer_label_getcode);
		
		customer_textfield_getcode = new JTextField();
		customer_textfield_getcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_textfield_getcellno.setEnabled(false);
				customer_textfield_getcode.setEnabled(false);
				code=customer_textfield_getcode.getText();
				String sql="Select name,code,cell_no from customers where code='"+code+"';";
				try {
					smt=c.createStatement();
					ResultSet rs=smt.executeQuery(sql);
					boolean data_found=false;
					while(rs.next()){
						data_found=true;
						customer_textfield_name.setText(rs.getString("name"));
						customer_textfield_code.setText(rs.getString("code"));
						customer_textfield_cellno.setText(rs.getString("cell_no"));
					}
					if(!data_found){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("No Data Found");
						error_display_obj.setVisible(true);
					}
					else{
						fill_table(code);
					}
				} catch (Exception e1) {
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e1.getMessage());
					error_display_obj.setVisible(true);
				}
				

			}
		});
		customer_textfield_getcode.setBounds(168, 11, 103, 20);
		contentPane.add(customer_textfield_getcode);
		customer_textfield_getcode.setColumns(10);
		
		JLabel customer_label_or = new JLabel("Or");
		customer_label_or.setHorizontalAlignment(SwingConstants.CENTER);
		customer_label_or.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_or.setBounds(264, 14, 46, 14);
		contentPane.add(customer_label_or);
		
		JLabel customer_label_getcellno = new JLabel("Cell No:");
		customer_label_getcellno.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_getcellno.setBounds(313, 14, 53, 14);
		contentPane.add(customer_label_getcellno);
		
		customer_textfield_getcellno = new JTextField();
		customer_textfield_getcellno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_textfield_getcellno.setEnabled(false);
				customer_textfield_getcode.setEnabled(false);
				String cellno=customer_textfield_getcellno.getText();
				String sql="Select name,code,cell_no from customers where cell_no='"+cellno+"';";
				try {
					smt=c.createStatement();
					ResultSet rs=smt.executeQuery(sql);
					boolean data_found=false;
					while(rs.next()){
						data_found=true;
						customer_textfield_name.setText(rs.getString("name"));
						customer_textfield_code.setText(rs.getString("code"));
						code=rs.getString("code");
						customer_textfield_cellno.setText(rs.getString("cell_no"));
					}
					if(!data_found){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("No Data Found");
						error_display_obj.setVisible(true);
					}
					else{
						fill_table(code);
					}
				} catch (Exception e1) {
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e1.getMessage());
					error_display_obj.setVisible(true);
				}
				
			}
		});
		customer_textfield_getcellno.setBounds(366, 11, 125, 20);
		contentPane.add(customer_textfield_getcellno);
		customer_textfield_getcellno.setColumns(10);
		
		customer_textfield_name = new JTextField();
		customer_textfield_name.setHorizontalAlignment(SwingConstants.CENTER);
		customer_textfield_name.setForeground(new Color(218,112,214));
		customer_textfield_name.setBorder(null);
		customer_textfield_name.setBackground(new Color(240, 255, 240));
		customer_textfield_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		customer_textfield_name.setEditable(false);
		customer_textfield_name.setBounds(55, 39, 155, 20);
		contentPane.add(customer_textfield_name);
		customer_textfield_name.setColumns(10);
		
		customer_textfield_code = new JTextField();
		customer_textfield_code.setHorizontalAlignment(SwingConstants.CENTER);
		customer_textfield_code.setForeground(new Color(218,112,214));
		customer_textfield_code.setFont(new Font("Tahoma", Font.BOLD, 13));
		customer_textfield_code.setBorder(null);
		customer_textfield_code.setBackground(new Color(240,255,240));
		customer_textfield_code.setEditable(false);
		customer_textfield_code.setBounds(250, 39, 86, 20);
		contentPane.add(customer_textfield_code);
		customer_textfield_code.setColumns(10);
		
		customer_label_code = new JLabel("Code:");
		customer_label_code.setHorizontalAlignment(SwingConstants.CENTER);
		customer_label_code.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_code.setBounds(204, 42, 46, 14);
		contentPane.add(customer_label_code);
		
		customer_label_name = new JLabel("Name:");
		customer_label_name.setHorizontalAlignment(SwingConstants.CENTER);
		customer_label_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_name.setBounds(7, 42, 46, 14);
		contentPane.add(customer_label_name);
		
		customer_label_cellno = new JLabel("Cell_No:");
		customer_label_cellno.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_cellno.setHorizontalAlignment(SwingConstants.CENTER);
		customer_label_cellno.setBounds(339, 42, 53, 14);
		contentPane.add(customer_label_cellno);
		
		customer_textfield_cellno = new JTextField();
		customer_textfield_cellno.setHorizontalAlignment(SwingConstants.CENTER);
		customer_textfield_cellno.setForeground(new Color(218, 112, 214));
		customer_textfield_cellno.setFont(new Font("Tahoma", Font.BOLD, 13));
		customer_textfield_cellno.setBorder(null);
		customer_textfield_cellno.setBackground(new Color(240,255,240));
		customer_textfield_cellno.setBounds(393, 39, 109, 20);
		contentPane.add(customer_textfield_cellno);
		customer_textfield_cellno.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 75, 495, 252);
		contentPane.add(scrollPane);
		Object data[][]=new Object[100][4];
		String names[]={"Bill No","Date","Amount"};
		customer_table = new JTable(data,names);
		customer_table.setBackground(new Color(240,255,240));
		customer_table.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_table.setFillsViewportHeight(true);
		customer_table.setEnabled(false);
		scrollPane.setViewportView(customer_table);
		
		customer_label_total = new JLabel("Total:");
		customer_label_total.setHorizontalAlignment(SwingConstants.CENTER);
		customer_label_total.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_label_total.setBounds(157, 344, 53, 14);
		contentPane.add(customer_label_total);
		
		customer_textfield_total = new JTextField();
		customer_textfield_total.setBorder(null);
		customer_textfield_total.setBackground(new Color(240,255,240));
		customer_textfield_total.setHorizontalAlignment(SwingConstants.CENTER);
		customer_textfield_total.setEditable(false);
		customer_textfield_total.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_textfield_total.setBounds(221, 341, 89, 20);
		contentPane.add(customer_textfield_total);
		customer_textfield_total.setColumns(10);
		
		customer_button_new = new JButton("New");
		customer_button_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_textfield_cellno.setText(null);
				customer_textfield_code.setText(null);
				customer_textfield_name.setText(null);
				customer_textfield_total.setText(null);
				customer_textfield_getcellno.setText(null);
				customer_textfield_getcellno.setEnabled(true);
				customer_textfield_getcode.setText(null);
				customer_textfield_getcode.setEnabled(true);
				for(int i =0;i<count;i++){
					for(int j=0;j<3;j++){
						model.setValueAt(null, i, j);
					}
				}
			}
		});
		customer_button_new.setFont(new Font("Tahoma", Font.BOLD, 11));
		customer_button_new.setBackground(SystemColor.menu);
		customer_button_new.setBounds(390, 338, 89, 23);
		contentPane.add(customer_button_new);
		customer_table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,11));
		model=customer_table.getModel();
	}
	public void fill_table(String customer_code){
		String sql="Select bill_no,date,amount from orders where customer_code='"+customer_code+"';";
		try {
			ResultSet rs=smt.executeQuery(sql);
			boolean found=false;
			count=0;
			double totamt=0;
			while(rs.next()){
				found=true;
				model.setValueAt(rs.getInt("bill_no"), count, 0);
				model.setValueAt(rs.getString("date"), count, 1);
				model.setValueAt(Math.ceil(rs.getDouble("amount")), count, 2);
				totamt+=(double)model.getValueAt(count, 2);
				count++;
			}
			if(!found){
				Error_Display error_display_obj=new Error_Display();
				error_display_obj.display("No Orders Found");
				error_display_obj.setVisible(true);
			}
			customer_textfield_total.setText(""+totamt);
		} catch (Exception e) {
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(e.getMessage());
			error_display_obj.setVisible(true);
		}
	}

}
