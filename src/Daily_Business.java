import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class Daily_Business extends JFrame {

	private JPanel contentPane;
	private JTextField daily_textfield_date;
	private JTable daily_table;
	private JLabel daily_table_total;
	private JTextField daily_textfield_total;
	private TableModel model;
	private int count;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public Daily_Business() {
		setTitle("Daily Business");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\daily.png"));
		setBounds(100, 100, 509, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(240,255,240));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel daily_label_date = new JLabel("Enter Date(dd-mm-yyyy):");
		daily_label_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		daily_label_date.setBounds(123, 11, 156, 20);
		contentPane.add(daily_label_date);
		
		daily_textfield_date = new JTextField();
		daily_textfield_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String date=daily_textfield_date.getText();
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					smt=c.createStatement();
					String sql="SELECT BILL_NO,NAME,CUSTOMER_CODE,DATE, AMOUNT FROM CUSTOMERS,ORDERS WHERE CUSTOMER_CODE=CODE AND DATE='"+date+"';";
					boolean data_found=false;
					ResultSet rs=smt.executeQuery(sql);
					count=0;
					double total_amount=0;
					while(rs.next()){
						data_found=true;
						model.setValueAt(rs.getInt("bill_no"), count, 0);
						model.setValueAt(rs.getString("name"), count, 1);
						model.setValueAt(rs.getString("customer_code"), count, 2);
						model.setValueAt(rs.getString("date"), count, 3);
						model.setValueAt(Math.ceil(rs.getDouble("amount")), count, 4);
						total_amount+=(double)model.getValueAt(count, 4);
						count++;
					}
					daily_textfield_date.setEditable(false);
					if(!data_found){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("No Data Found or Inappropriate Date");
						error_display_obj.setVisible(true);
					}
					daily_textfield_total.setText(""+total_amount);
					smt.close();
					c.close();
				}catch(Exception e1){
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e1.getMessage());
					error_display_obj.setVisible(true);
				}
				
			}
		});
		daily_textfield_date.setBounds(286, 11, 99, 20);
		contentPane.add(daily_textfield_date);
		daily_textfield_date.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 483, 247);
		contentPane.add(scrollPane);
		Object data[][]=new Object[100][6];
		String name[]={"Bill No","Customer Name","Code","Date","Amount"};
		
		daily_table = new JTable(data,name);
		daily_table.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		daily_table.getColumnModel().getColumn(0).setPreferredWidth(59);
		daily_table.getColumnModel().getColumn(1).setPreferredWidth(110);
		daily_table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,11));
		daily_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		daily_table.setBackground(SystemColor.menu);
		daily_table.setEnabled(false);
		daily_table.setColumnSelectionAllowed(true);
		model=daily_table.getModel();
		scrollPane.setViewportView(daily_table);
		
		daily_table_total = new JLabel("Total :");
		daily_table_total.setFont(new Font("Tahoma", Font.BOLD, 11));
		daily_table_total.setBounds(139, 316, 64, 14);
		contentPane.add(daily_table_total);
		
		daily_textfield_total = new JTextField();
		daily_textfield_total.setEditable(false);
		daily_textfield_total.setHorizontalAlignment(SwingConstants.CENTER);
		daily_textfield_total.setFont(new Font("Tahoma", Font.BOLD, 11));
		daily_textfield_total.setBounds(213, 313, 99, 20);
		contentPane.add(daily_textfield_total);
		daily_textfield_total.setColumns(10);
		
		JButton daily_button_new = new JButton("New");
		daily_button_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<count;i++){
					for(int j=0;j<5;j++){
						model.setValueAt(null, i, j);
					}
				}
				daily_textfield_date.setEditable(true);
			}
		});
		daily_button_new.setFont(new Font("Tahoma", Font.BOLD, 11));
		daily_button_new.setBounds(404, 313, 89, 23);
		contentPane.add(daily_button_new);
	}
}
