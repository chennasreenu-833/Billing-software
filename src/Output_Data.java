import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class Output_Data extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Object data[][]=new Object[256][3];
	private String names[];

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Output_Data() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\menu_card.png"));
		setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		setResizable(false);
		setTitle("Item_Details");
		setBounds(100, 100, 710, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 684, 474);
		contentPane.add(scrollPane);
		Connection c=null;
		Statement smt=null;
		
		try{
			Class.forName("org.sqlite.JDBC");
			c=DriverManager.getConnection("jdbc:sqlite:bill.db");
			smt=c.createStatement();
			c.setAutoCommit(false);
			String sql="SELECT * FROM ITEMS order by item_name,item_rate asc";
			ResultSet rs=smt.executeQuery(sql);
			int i=0,j=0;
			names=new String[] {"ITEM_NAME","ITEM_CODE","ITEM_RATE"};
			table = new JTable(data,names);
			table.setBackground(SystemColor.menu);
			table.setFont(new Font("Tahoma", Font.BOLD, 11));
			table.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			table.setEnabled(false);
			table.setBorder(new LineBorder(new Color(0, 0, 0), 0, true));
			TableModel model=table.getModel();
			while(rs.next()){
				model.setValueAt(rs.getString("item_name"), i, 0);
				model.setValueAt(rs.getString("item_code"), i, 1);
				model.setValueAt("Rs: "+rs.getDouble("item_rate"), i, 2);
				i++;
			}
			
		}catch(Exception e){
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.BOLD,12));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
	}
}
