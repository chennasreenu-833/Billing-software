import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class Total_Business extends JFrame {

	private JPanel contentPane;
	private JTable total_table;
	private JLabel total_label_income;
	private JTextField total_textfield_income;
	public Total_Business() {
		setTitle("Total Statistics");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\total.png"));
		setFont(new Font("Dialog", Font.BOLD, 12));
		setBounds(100, 100, 627, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(240,255,240));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 601, 463);
		contentPane.add(scrollPane);
		Object data[][]=new Object[250][6];
		String name[]={"Bill No","Customer Name","Code","Date","Amount"};
		total_table = new JTable(data,name);
		total_table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,11));
		total_table.setFont(new Font("Tahoma", Font.BOLD, 11));
		total_table.setFillsViewportHeight(true);
		total_table.setBackground(new Color(240,255,240));
		total_table.setEnabled(false);
		TableModel model=total_table.getModel();
		scrollPane.setViewportView(total_table);
		
		total_label_income = new JLabel("Total Income:");
		total_label_income.setHorizontalAlignment(SwingConstants.CENTER);
		total_label_income.setFont(new Font("Tahoma", Font.BOLD, 11));
		total_label_income.setBounds(164, 485, 92, 14);
		contentPane.add(total_label_income);
		
		total_textfield_income = new JTextField();
		total_textfield_income.setHorizontalAlignment(SwingConstants.CENTER);
		total_textfield_income.setFont(new Font("Tahoma", Font.BOLD, 11));
		total_textfield_income.setEditable(false);
		total_textfield_income.setBounds(255, 482, 115, 20);
		contentPane.add(total_textfield_income);
		total_textfield_income.setColumns(10);
		
		Connection c=null;
		Statement smt=null;
		try{
			Class.forName("org.sqlite.JDBC");
			c=DriverManager.getConnection("jdbc:sqlite:bill.db");
			smt=c.createStatement();
			String sql="SELECT BILL_NO,NAME,CUSTOMER_CODE,DATE, AMOUNT FROM CUSTOMERS,ORDERS WHERE CUSTOMER_CODE=CODE";
			boolean data_found=false;
			ResultSet rs=smt.executeQuery(sql);
			int count=0;
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
			if(!data_found){
				Error_Display error_display_obj=new Error_Display();
				error_display_obj.display("No Data Found or Inappropriate Date");
				error_display_obj.setVisible(true);
			}
			total_textfield_income.setText(""+total_amount);
			smt.close();
			c.close();
		}catch(Exception e1){
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(e1.getMessage());
			error_display_obj.setVisible(true);
		}
	}

}
