import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Get_Billno_Details extends JFrame {

	private JPanel contentPane;
	private JTextField bill_textfield_billno;
	private JTextField bill_textfield_name;
	private JTextField bill_textfield_code;
	private JTextField bill_textfield_date;
	private JTable bill_table;
	private Object data[][];
	private String names[];
	private JLabel bill_label_products;
	private JTextField bill_textfield_products;
	private JLabel bill_label_amount;
	private JTextField bill_textfield_amount;
	private int bill_no;
	private TableModel model;
	private String bill_name,bill_code,bill_date,bill_products,bill_amount;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Get_Billno_Details() {
		setTitle("BILL DETAILS");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\bill details.png"));
		setBounds(100, 100, 544, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(240,255,240));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel bill_label_billno = new JLabel("Enter Bill_No:");
		bill_label_billno.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_billno.setBounds(149, 11, 87, 19);
		contentPane.add(bill_label_billno);
		
		bill_textfield_billno = new JTextField();
		bill_textfield_billno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bill_no=Integer.parseInt(bill_textfield_billno.getText());
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					smt=c.createStatement();
					String sql="SELECT NAME,CODE,DATE,AMOUNT FROM CUSTOMERS,ORDERS WHERE BILL_NO="+bill_no+" AND CUSTOMER_CODE=CODE;";
					ResultSet rs=smt.executeQuery(sql);
					while(rs.next()){
						bill_textfield_name.setText(rs.getString("name"));
						bill_textfield_code.setText(rs.getString("code"));
						bill_textfield_date.setText(rs.getString("date"));
						bill_textfield_amount.setText(Math.ceil(rs.getDouble("amount"))+"");
					}
					sql="SELECT ITEM_NAME,ITEM_RATE,QUANTITY FROM ITEMS,ORDER_DETAILS WHERE ORDER_ID="+bill_no+" AND order_details.item_code=items.item_code;";
					rs=smt.executeQuery(sql);
					int i=0;
					while(rs.next()){
						model.setValueAt(rs.getString("item_name"), i, 0);
						model.setValueAt(rs.getString("item_rate"), i, 1);
						model.setValueAt(rs.getString("quantity"), i, 2);
						i++;
					}
					bill_textfield_products.setText(""+i);
				}catch(Exception e){
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
				}
				
				
			}
		});
		bill_textfield_billno.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_billno.setBounds(246, 10, 95, 20);
		contentPane.add(bill_textfield_billno);
		bill_textfield_billno.setColumns(10);
		
		JLabel bill_label_name = new JLabel("Name :");
		bill_label_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_name.setBounds(10, 44, 56, 14);
		contentPane.add(bill_label_name);
		
		bill_textfield_name = new JTextField();
		bill_textfield_name.setEditable(false);
		bill_textfield_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_name.setBounds(76, 41, 151, 20);
		contentPane.add(bill_textfield_name);
		bill_textfield_name.setColumns(10);
		
		JLabel bill_label_code = new JLabel("Code: ");
		bill_label_code.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_code.setBounds(246, 44, 40, 14);
		contentPane.add(bill_label_code);
		
		bill_textfield_code = new JTextField();
		bill_textfield_code.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_code.setEditable(false);
		bill_textfield_code.setBounds(296, 41, 79, 20);
		contentPane.add(bill_textfield_code);
		bill_textfield_code.setColumns(10);
		
		JLabel bill_label_date = new JLabel("Date:");
		bill_label_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_date.setBounds(395, 44, 46, 14);
		contentPane.add(bill_label_date);
		
		bill_textfield_date = new JTextField();
		bill_textfield_date.setEditable(false);
		bill_textfield_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_date.setBounds(434, 41, 86, 20);
		contentPane.add(bill_textfield_date);
		bill_textfield_date.setColumns(10);
		
		JScrollPane bill_scrollpane = new JScrollPane();
		bill_scrollpane.setBounds(10, 82, 510, 226);
		contentPane.add(bill_scrollpane);
		data=new Object[200][4];
		names=new String[]{"ITEM_NAME","ITEM_RATE","ITEM_QUANTITY"};
		bill_table = new JTable(data,names);
		bill_table.setEnabled(false);
		bill_table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,11));
		bill_table.setFillsViewportHeight(true);
		model=bill_table.getModel();getContentPane();
		bill_scrollpane.setViewportView(bill_table);
		
		bill_label_products = new JLabel("Products:");
		bill_label_products.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_products.setBounds(43, 322, 66, 14);
		contentPane.add(bill_label_products);
		
		bill_textfield_products = new JTextField();
		bill_textfield_products.setHorizontalAlignment(SwingConstants.CENTER);
		bill_textfield_products.setBackground(new Color(154, 205, 50));
		bill_textfield_products.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_products.setEditable(false);
		bill_textfield_products.setBounds(110, 319, 86, 20);
		contentPane.add(bill_textfield_products);
		bill_textfield_products.setColumns(10);
		bill_textfield_products.setText(""+0);
		
		bill_label_amount = new JLabel("Amount:");
		bill_label_amount.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_label_amount.setBounds(296, 322, 66, 14);
		contentPane.add(bill_label_amount);
		
		bill_textfield_amount = new JTextField();
		bill_textfield_amount.setHorizontalAlignment(SwingConstants.CENTER);
		bill_textfield_amount.setBackground(new Color(154, 205, 50));
		bill_textfield_amount.setEditable(false);
		bill_textfield_amount.setFont(new Font("Tahoma", Font.BOLD, 11));
		bill_textfield_amount.setText(""+0);
		bill_textfield_amount.setBounds(372, 319, 86, 20);
		contentPane.add(bill_textfield_amount);
		bill_textfield_amount.setColumns(10);
		
		
	}
}
