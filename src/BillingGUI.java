import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import org.sqlite.SQLiteConfig.DateClass;
import java.time.DayOfWeek;
import org.sqlite.SQLiteConfig.DatePrecision;

import com.lowagie.text.Paragraph;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.time.Month;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.ComponentOrientation;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.DebugGraphics;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;




public class BillingGUI extends JFrame {
	private Boolean permission_to_item=false,permission_to_quantity=false;
	public double rate,total_table=0,total_vat=0,subtotal=0,quantity,total_discount;
	static DecimalFormat df = new DecimalFormat();
	public  TableModel model;
	private JPanel contentPane;
	private JTextField gui_textfield_billno;
	private JTextField gui_textfield_customer;
	private JTextField gui_textfield_code;
	private JTextField gui_textfield_phno;
	private JTextField gui_textfield_address;
	private JTextField gui_textfield_area;
	private JTextField gui_textfield_city;
	private JTextField gui_textfield_pincode;
	public JTable gui_table;
	private JTextField gui_textfield_total;
	private JTextField gui_textfield_cellno;
	private JTextField gui_textfield_email;
	private JTextField gui_textfield_received;
	private JTextField gui_textfield_balance;
	private JTextField gui_textfield_products;
	private JTextField gui_textfield_totalvat;
	private JTextField gui_textfield_discount;
	private JTextField gui_textfield_subtotal;
	private JRadioButton gui_radiobutton_wholesale;
	private JRadioButton gui_radiobutton_retail;
	private ButtonGroup billtype,delivery,group_cash;
	private JRadioButton gui_radiobutton_inperson;
	private JRadioButton gui_radiobutton_cash;
	private JComboBox gui_combobox_category;
	private JComboBox gui_combobox_item;
	private JSpinner gui_spinner_quantity;
	public int count=0;
	private String gui_date="";
	private double total,balance,value,received=0;
	private Object gui_table_data[][];
	private String gui_table_columnnames[];
	public Item_Rates item_rates_obj;
	public Item_Codes item_codes_obj;
	public JScrollPane gui_scrollpane;
	private JRadioButton gui_radiobutton_doordelivery;
	private JTextField gui_textfield_date;
	private JButton gui_button_print;
	private JTextField gui_textfield_inwords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillingGUI frame = new BillingGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		df.setMaximumFractionDigits(2);
	}

	/**
	 * Create the frame.
	 */
	public BillingGUI() {
		setResizable(false);
		setVisible(true);
		setTitle("Billing Software--Invoice");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\billing.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 726);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.setToolTipText("");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		CreateDb createDbobj=new CreateDb();
		createDbobj.create_table_customers();
		createDbobj.create_table_orders();
		createDbobj.create_table_items();
		createDbobj.create_table_order_details();
		createDbobj.close_connection();
		
		PropagateTable prop_obj=new PropagateTable();
		prop_obj.propagate_table_items();
		prop_obj.close_connection();
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(51, 204, 255));
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuBar.setBounds(0, 0, 910, 34);
		contentPane.add(menuBar);
		
		JMenu menu_file = new JMenu("File");
		menu_file.setMnemonic('F');
		menu_file.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\file.png"));
		menuBar.add(menu_file);
		
		JMenuItem menu_file_new = new JMenuItem("New Bill");
		menu_file_new.setBackground(new Color(240, 240, 240));
		menu_file_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui_button_print.setEnabled(false);
				gui_combobox_category.setEnabled(true);
				gui_combobox_item.setEnabled(true);
				gui_textfield_customer.setText(null);
				gui_textfield_code.setText(null);
				gui_textfield_cellno.setText(null);
				gui_textfield_phno.setText(null);
				gui_textfield_address.setText(null);
				gui_textfield_area.setText(null);
				gui_textfield_city.setText(null);
				gui_textfield_pincode.setText(null);
				gui_textfield_email.setText(null);
				gui_combobox_category.setSelectedIndex(0);
				gui_combobox_item.removeAllItems();
				gui_textfield_inwords.setText("Zero Ruppees Only");
				gui_spinner_quantity.setValue(0);
				gui_textfield_products.setText("0");
				gui_textfield_totalvat.setText("0.00");
				gui_textfield_discount.setText("0.00");
				gui_textfield_subtotal.setText("0.00");
				gui_textfield_received.setText("0.00");
				gui_textfield_total.setText("0.00");
				gui_textfield_balance.setText("0.00");
			    gui_radiobutton_inperson.setSelected(true);
			    gui_radiobutton_wholesale.setSelected(true);
			    gui_radiobutton_cash.setSelected(true);
			    gui_textfield_products.setEditable(false);
			    gui_textfield_discount.setEditable(false);
				gui_textfield_totalvat.setEditable(false);
				gui_textfield_subtotal.setEditable(false);
				int gui_bill_no;
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					smt=c.createStatement();
					String sql="Select count(*) from orders;";
					ResultSet rs=smt.executeQuery(sql);
					while(rs.next()){
						gui_bill_no=16000+Integer.parseInt(rs.getString(1));
						gui_textfield_billno.setText(""+gui_bill_no);
						smt.close();
						c.close();
						
					}
				}catch (Exception e){
					System.out.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
				}
				for(int i=0;i<count;i++){
					for(int j=0;j<model.getColumnCount();j++){
						model.setValueAt(null, i, j);
					}
				}
				count=0;
				
			}
		});
		menu_file_new.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\file.png"));
		menu_file.add(menu_file_new);
		
		JMenuItem menu_file_openbill = new JMenuItem("Open Bill");
		menu_file_openbill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Open_Bill open_bill_obj=new Open_Bill();
				open_bill_obj.setVisible(true);
				
				
			}
		});
		menu_file_openbill.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\open_bill.png"));
		menu_file.add(menu_file_openbill);
		
		JMenu menu_document = new JMenu("Document");
		menu_document.setMnemonic('D');
		menu_document.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\document.png"));
		menuBar.add(menu_document);
		
		JMenuItem menu_document_open = new JMenuItem("Order Details");
		menu_document_open.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\open.png"));
		menu_document_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Get_Billno_Details get_billno_details_obj=new Get_Billno_Details();
				get_billno_details_obj.setVisible(true);
				
			}
		});
		
		JMenuItem menu_document_printinvoice = new JMenuItem("Print Invoice");
		menu_document_printinvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu_Print_Invoice obj=new Menu_Print_Invoice();
				obj.Menu_Print_Invoice_do();
				obj.setVisible(true);
			}
		});
		menu_document_printinvoice.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\print_invoice.gif"));
		menu_document.add(menu_document_printinvoice);
		menu_document.add(menu_document_open);
		
		JMenuItem menu_document_display = new JMenuItem("Display Table");
		menu_document_display.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\display-icon.png"));
		menu_document_display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Print_Table print_table_obj=new Print_Table(gui_table_data,gui_table_columnnames);
				print_table_obj.setVisible(true);
			}
		});
		menu_document.add(menu_document_display);
		
		JMenu mnInventory = new JMenu("Inventory");
		mnInventory.setMnemonic('I');
		mnInventory.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\voucher.png"));
		menuBar.add(mnInventory);
		
		JMenuItem inventory_item_voucher = new JMenuItem("Item Voucher");
		inventory_item_voucher.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\menu_card.png"));
		inventory_item_voucher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Output_Data obj=new Output_Data();
				obj.setVisible(true);
			}
		});
		mnInventory.add(inventory_item_voucher);
		
		JMenuItem mntmAddItem = new JMenuItem("Add Item");
		mntmAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_Items add_items_obj=new Add_Items();
				add_items_obj.setVisible(true);
			}
		});
		mntmAddItem.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\add_item (2).png"));
		mnInventory.add(mntmAddItem);
		
		JMenu menu_statistics = new JMenu("Statistics");
		menu_statistics.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\statistics.png"));
		menu_statistics.setMnemonic('S');
		menuBar.add(menu_statistics);
		
		JMenuItem menu_statistics_daily = new JMenuItem("Daily Business");
		menu_statistics_daily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Daily_Business daily_business_obj=new Daily_Business();
				daily_business_obj.setVisible(true);
				
			}
		});
		menu_statistics_daily.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\daily.png"));
		menu_statistics.add(menu_statistics_daily);
		
		JMenuItem menu_statistics_customer = new JMenuItem("Customer");
		menu_statistics_customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer_Business customer_business_obj=new Customer_Business();
				customer_business_obj.setVisible(true);
			}
		});
		menu_statistics_customer.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\customer.png"));
		menu_statistics.add(menu_statistics_customer);
		
		JMenuItem menu_statistics_business = new JMenuItem("Total Business");
		menu_statistics_business.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Total_Business total_business_obj=new Total_Business();
				total_business_obj.setVisible(true);
			}
		});
		menu_statistics_business.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\total.png"));
		menu_statistics.add(menu_statistics_business);
		
		JMenu menu_accounts = new JMenu("Accounts");
		menu_accounts.setMnemonic('A');
		menu_accounts.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\account.gif"));
		menuBar.add(menu_accounts);
		
		JMenuItem menu_accounts_user_details = new JMenuItem("User Details");
		menu_accounts_user_details.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Accounts_User accounts_user_obj=new Accounts_User();
				accounts_user_obj.setVisible(true);
			}
		});
		menu_accounts_user_details.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\user.png"));
		menu_accounts.add(menu_accounts_user_details);
		
		JMenu menu_setup = new JMenu("Setup");
		menu_setup.setMnemonic('S');
		menu_setup.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\SetUp.png"));
		menuBar.add(menu_setup);
		
		JMenu menu_windowlist = new JMenu("Windowlist");
		menu_windowlist.setMnemonic('W');
		menu_windowlist.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\window.png"));
		menuBar.add(menu_windowlist);
		
		JMenu menu_help = new JMenu("Help");
		menu_help.setMnemonic('H');
		menu_help.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\helpme.png"));
		menuBar.add(menu_help);
		
		JLabel gui_label_namelabel = new JLabel(" New Purchase Bill (All In One General Stores)*");
		gui_label_namelabel.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_namelabel.setIcon(null);
		gui_label_namelabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		gui_label_namelabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		gui_label_namelabel.setForeground(SystemColor.textHighlight);
		gui_label_namelabel.setBackground(new Color(192, 192, 192));
		gui_label_namelabel.setBounds(0, 33, 878, 20);
		contentPane.add(gui_label_namelabel);
		
		JLabel gui_label_billno = new JLabel("Bill No:");
		gui_label_billno.setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_label_billno.setBackground(new Color(204, 255, 135));
		gui_label_billno.setBounds(79, 59, 46, 26);
		contentPane.add(gui_label_billno);
		
		gui_textfield_billno = new JTextField();
		gui_textfield_billno.setHorizontalAlignment(SwingConstants.CENTER);
		gui_textfield_billno.setFont(new Font("Tahoma", Font.BOLD, 12));
		int gui_bill_no;
		Connection c=null;
		Statement smt=null;
		try{
			Class.forName("org.sqlite.JDBC");
			c=DriverManager.getConnection("jdbc:sqlite:bill.db");
			smt=c.createStatement();
			String sql="Select count(*) from orders;";
			ResultSet rs=smt.executeQuery(sql);
			while(rs.next()){
				gui_bill_no=16000+Integer.parseInt(rs.getString(1));
				gui_textfield_billno.setText(""+gui_bill_no);
				smt.close();
				c.close();
				
			}
		}catch (Exception e){
			System.out.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		gui_textfield_billno.setEditable(false);
		gui_textfield_billno.setBackground(SystemColor.menu);
		gui_textfield_billno.setBounds(126, 62, 104, 20);
		contentPane.add(gui_textfield_billno);
		gui_textfield_billno.setColumns(10);
		
		JLabel gui_label_date = new JLabel(" Date :");
		gui_label_date.setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_label_date.setBackground(UIManager.getColor("Button.background"));
		gui_label_date.setBounds(679, 64, 46, 21);
		contentPane.add(gui_label_date);
		
		JButton gui_button_reload = new JButton("Reload");
		gui_button_reload.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_button_reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui_textfield_customer.setText(null);
				gui_textfield_code.setText(null);
				gui_textfield_cellno.setText(null);
				gui_textfield_phno.setText(null);
				gui_textfield_address.setText(null);
				gui_textfield_area.setText(null);
				gui_textfield_city.setText(null);
				gui_textfield_pincode.setText(null);
				gui_textfield_email.setText(null);
				
				gui_combobox_category.setSelectedIndex(0);
				gui_combobox_item.removeAllItems();
				gui_spinner_quantity.setValue(0);
				gui_textfield_products.setText("0");
				gui_textfield_totalvat.setText("0.00");
				gui_textfield_discount.setText("0.00");
				gui_textfield_subtotal.setText("0.00");
				gui_textfield_received.setText("0.00");
				gui_textfield_total.setText("0.00");
				gui_textfield_balance.setText("0.00");
				//delivery.clearSelection();
			    gui_radiobutton_inperson.setSelected(true);
			    gui_radiobutton_wholesale.setSelected(true);
			  //  group_cash.clearSelection();
			    gui_radiobutton_cash.setSelected(true);
			    gui_textfield_products.setEditable(false);
			    gui_textfield_discount.setEditable(false);
				gui_textfield_totalvat.setEditable(false);
				gui_textfield_subtotal.setEditable(false);
				for(int i=0;i<count;i++){
					for(int j=0;j<model.getColumnCount();j++){
						model.setValueAt(null, i, j);
					}
				}
				count=0;
				
			}
		});
		gui_button_reload.setBackground(UIManager.getColor("Button.background"));
		gui_button_reload.setHorizontalAlignment(SwingConstants.LEADING);
		gui_button_reload.setToolTipText("wipe data");
		gui_button_reload.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\reload.png"));
		gui_button_reload.setBounds(362, 177, 98, 26);
		contentPane.add(gui_button_reload);
		
		gui_combobox_item = new JComboBox();
		gui_combobox_item.setBackground(UIManager.getColor("Button.background"));
		gui_combobox_item.setBounds(433, 214, 263, 23);
		contentPane.add(gui_combobox_item);
		
		JLabel gui_label_customer = new JLabel("Customer*");
		gui_label_customer.setBackground(UIManager.getColor("Button.background"));
		gui_label_customer.setBounds(10, 93, 66, 20);
		contentPane.add(gui_label_customer);
		
		gui_textfield_customer = new JTextField();
		gui_textfield_customer.setBackground(SystemColor.text);
		gui_textfield_customer.setBounds(79, 93, 121, 25);
		contentPane.add(gui_textfield_customer);
		gui_textfield_customer.setColumns(10);
		
		JLabel gui_label_code = new JLabel("Code*");
		gui_label_code.setBackground(UIManager.getColor("Button.background"));
		gui_label_code.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		gui_label_code.setBounds(254, 96, 39, 14);
		contentPane.add(gui_label_code);
		
		gui_textfield_code = new JTextField();
		gui_textfield_code.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String get_code_details=gui_textfield_code.getText();
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					smt=c.createStatement();
					String sql="SELECT * FROM CUSTOMERS WHERE CODE='"+get_code_details+"';";
					ResultSet rs=smt.executeQuery(sql);
					boolean data_found=false;
					while(rs.next()){
						data_found=true;
						gui_textfield_customer.setText(rs.getString("name"));
						gui_textfield_phno.setText(rs.getString("Phone_no"));
						gui_textfield_cellno.setText(rs.getString("cell_no"));
						gui_textfield_city.setText(rs.getString("city"));
						gui_textfield_address.setText(rs.getString("address"));
						gui_textfield_area.setText(rs.getString("area"));
						gui_textfield_pincode.setText(rs.getString("pincode"));
						gui_textfield_email.setText(rs.getString("email"));
						
					}
					if (!data_found){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display(" No Details Found");
						error_display_obj.setVisible(true);
					}
					smt.close();
					c.close();
				}catch(Exception e){
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e.getMessage());
					error_display_obj.setVisible(true);
				}
			}
		});
		gui_textfield_code.setBackground(SystemColor.text);
		gui_textfield_code.setBounds(303, 91, 71, 25);
		contentPane.add(gui_textfield_code);
		gui_textfield_code.setColumns(10);
		
		JLabel gui_label_phno = new JLabel("Ph No:");
		gui_label_phno.setBackground(UIManager.getColor("Button.background"));
		gui_label_phno.setBounds(482, 96, 46, 14);
		contentPane.add(gui_label_phno);
		
		gui_textfield_phno = new JTextField();
		gui_textfield_phno.setBackground(SystemColor.text);
		gui_textfield_phno.setBounds(538, 93, 111, 25);
		contentPane.add(gui_textfield_phno);
		gui_textfield_phno.setColumns(10);
		
		JLabel gui_label_address = new JLabel("Address*");
		gui_label_address.setBounds(10, 140, 66, 14);
		contentPane.add(gui_label_address);
		
		gui_textfield_address = new JTextField();
		gui_textfield_address.setBackground(SystemColor.text);
		gui_textfield_address.setBounds(79, 137, 170, 25);
		contentPane.add(gui_textfield_address);
		gui_textfield_address.setColumns(10);
		
		JLabel gui_label_area = new JLabel("Area*");
		gui_label_area.setBounds(303, 140, 39, 14);
		contentPane.add(gui_label_area);
		
		gui_textfield_area = new JTextField();
		gui_textfield_area.setBackground(SystemColor.text);
		gui_textfield_area.setBounds(352, 137, 86, 25);
		contentPane.add(gui_textfield_area);
		gui_textfield_area.setColumns(10);
		
		JLabel gui_label_city = new JLabel("City*");
		gui_label_city.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_label_city.setBounds(482, 140, 46, 14);
		contentPane.add(gui_label_city);
		
		gui_textfield_city = new JTextField();
		gui_textfield_city.setBounds(538, 137, 111, 25);
		contentPane.add(gui_textfield_city);
		gui_textfield_city.setColumns(10);
		
		JLabel gui_label_pincode = new JLabel("PinCode");
		gui_label_pincode.setBounds(679, 140, 54, 14);
		contentPane.add(gui_label_pincode);
		
		gui_textfield_pincode = new JTextField();
		gui_textfield_pincode.setBounds(732, 137, 98, 25);
		contentPane.add(gui_textfield_pincode);
		gui_textfield_pincode.setColumns(10);
		
		gui_scrollpane = new JScrollPane();
		gui_scrollpane.setName("Details");
		gui_scrollpane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		gui_scrollpane.setBounds(10, 248, 868, 204);
		contentPane.add(gui_scrollpane);
		gui_table_data=new Object[50][9] ;
		gui_table_columnnames=new String[] {
				"No:", "Code", "Item Name", "Rate", "Qty", "Disc%", "Disc", "Tax%", "Total"
			};
		gui_table = new JTable(gui_table_data,gui_table_columnnames);
		gui_table.setEnabled(false);
		gui_table.setFillsViewportHeight(true);
		gui_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gui_table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		gui_table.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		gui_table.setFocusTraversalPolicyProvider(true);
		gui_table.setBorder(UIManager.getBorder("CheckBoxMenuItem.border"));
		gui_table.setBackground(new Color(245, 245, 245));
		gui_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		gui_table.getColumnModel().getColumn(0).setPreferredWidth(32);
		gui_table.getColumnModel().getColumn(1).setPreferredWidth(71);
		gui_table.getColumnModel().getColumn(2).setPreferredWidth(137);
		gui_table.getColumnModel().getColumn(4).setPreferredWidth(51);
		gui_table.getColumnModel().getColumn(5).setPreferredWidth(54);
		gui_table.getColumnModel().getColumn(6).setPreferredWidth(62);
		gui_table.getColumnModel().getColumn(7).setPreferredWidth(53);
		gui_table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_scrollpane.setViewportView(gui_table);
		
		JButton gui_button_save = new JButton("Save");
		gui_button_save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gui_button_save.setBorder(new EmptyBorder(0, 0, 0, 0));
		gui_button_save.setBorderPainted(false);
		gui_button_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui_button_print.setEnabled(true);
				gui_combobox_item.setEnabled(false);
				gui_combobox_category.setEnabled(false);
				int gui_billno=Integer.parseInt(gui_textfield_billno.getText());
				String gui_customercode=gui_textfield_code.getText();
				gui_date=gui_textfield_date.getText();
				double gui_amount=value;
				Add_Orders add_orders_obj=new Add_Orders();
				add_orders_obj.store_orders(gui_billno, gui_customercode, gui_date, gui_amount);
				
				for(int i=0;i<count;i++){
					String gui_itemcode=model.getValueAt(i, 1).toString();
					int gui_quantity=(int)model.getValueAt(i, 4);
					add_orders_obj.store_orders_details(gui_billno, gui_itemcode, gui_quantity);
				}
				try {
					add_orders_obj.close_connection();
				} catch (Exception e) {
					gui_button_print.setEnabled(false);
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(e.getMessage());
					error_display_obj.setVisible(true);
				}
				
			}
		});
		gui_button_save.setBackground(new Color(255, 153, 51));
		gui_button_save.setForeground(new Color(0, 0, 0));
		gui_button_save.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\save.png"));
		gui_button_save.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_button_save.setBounds(39, 641, 143, 45);
		contentPane.add(gui_button_save);
		gui_button_print = new JButton("Print");
		gui_button_print.setBorder(new EmptyBorder(0, 0, 0, 0));
		gui_button_print.setEnabled(false);
		gui_button_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				Print_Invoice print_invoice_obj=new Print_Invoice(Integer.parseInt(gui_textfield_billno.getText()));
			}
		});
		
		gui_button_print.setForeground(new Color(0, 0, 0));
		gui_button_print.setBackground(new Color(255, 153, 51));
		gui_button_print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gui_button_print.setMnemonic('B');
		gui_button_print.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\print (1).png"));
		gui_button_print.setFont(new Font("Arial Black", Font.BOLD, 11));
		gui_button_print.setBounds(217, 640, 158, 46);
		contentPane.add(gui_button_print);
		
		JButton gui_button_exit = new JButton("Exit");
		gui_button_exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gui_button_exit.setBorder(new EmptyBorder(0, 0, 0, 0));
		gui_button_exit.setForeground(new Color(0, 0, 0));
		gui_button_exit.setBackground(new Color(255, 153, 51));
		gui_button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		gui_button_exit.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\Exit.png"));
		gui_button_exit.setMnemonic('E');
		gui_button_exit.setFont(new Font("Arial Black", Font.BOLD, 11));
		gui_button_exit.setBounds(411, 640, 143, 46);
		contentPane.add(gui_button_exit);
		
		JPanel gui_panel_total = new JPanel();
		gui_panel_total.setBackground(SystemColor.inactiveCaption);
		gui_panel_total.setBounds(657, 463, 221, 111);
		contentPane.add(gui_panel_total);
		gui_panel_total.setLayout(null);
		
		JLabel gui_label_total = new JLabel("Total:");
		gui_label_total.setFont(new Font("Tahoma", Font.BOLD, 15));
		gui_label_total.setBounds(10, 44, 52, 27);
		gui_panel_total.add(gui_label_total);
		
		gui_textfield_total = new JTextField();
		gui_textfield_total.setHorizontalAlignment(SwingConstants.CENTER);
		gui_textfield_total.setBackground(SystemColor.inactiveCaption);
		gui_textfield_total.setBorder(null);
		gui_textfield_total.setFont(new Font("Tahoma", Font.BOLD, 23));
		gui_textfield_total.setText("0.00");
		gui_textfield_total.setBounds(75, 24, 136, 60);
		gui_panel_total.add(gui_textfield_total);
		gui_textfield_total.setColumns(10);
		
		JButton gui_button_add = new JButton("Add New Customer");
		gui_button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_Customers addObj=new Add_Customers();
				String gui_name,gui_code,gui_cellno,gui_phno,gui_address,gui_area,gui_city,gui_pincode,gui_email;
				gui_name=gui_textfield_customer.getText();
				gui_code=gui_textfield_code.getText();
				gui_phno=gui_textfield_phno.getText();
				gui_cellno=gui_textfield_cellno.getText();
				gui_address=gui_textfield_address.getText();
				gui_area=gui_textfield_area.getText();
				gui_city=gui_textfield_city.getText();
				gui_pincode=gui_textfield_pincode.getText();
				gui_email=gui_textfield_email.getText();
				try {
					addObj.Store(gui_name, gui_code, gui_phno, gui_cellno, gui_address, gui_area, gui_city, gui_pincode, gui_email);
				} catch (Exception e) {
					String error=e.getMessage();
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(error+" choose another code");
					error_display_obj.setVisible(true);
				}
			}
		});
		gui_button_add.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_button_add.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\edit_2add.png"));
		gui_button_add.setBounds(517, 177, 179, 26);
		contentPane.add(gui_button_add);
		
		JLabel gui_label_item = new JLabel("Item:");
		gui_label_item.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_item.setBounds(352, 215, 71, 20);
		contentPane.add(gui_label_item);
		
		JLabel gui_label_billtype = new JLabel("Bill Type:");
		gui_label_billtype.setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_label_billtype.setBounds(352, 64, 59, 14);
		contentPane.add(gui_label_billtype);
		
		gui_radiobutton_wholesale = new JRadioButton("wholesale");
		gui_radiobutton_wholesale.setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_radiobutton_wholesale.setBackground(new Color(240,248,255));
		gui_radiobutton_wholesale.setSelected(true);
		gui_radiobutton_wholesale.setBounds(417, 60, 86, 23);
		contentPane.add(gui_radiobutton_wholesale);
		
		gui_radiobutton_retail = new JRadioButton("retail");
		gui_radiobutton_retail.setBackground(new Color(240,248,255));
		gui_radiobutton_retail.setFont(new Font("Tahoma", Font.BOLD, 12));
		gui_radiobutton_retail.setBounds(516, 60, 62, 23);
		contentPane.add(gui_radiobutton_retail);
		
		billtype=new ButtonGroup();
		billtype.add(gui_radiobutton_wholesale);
		billtype.add(gui_radiobutton_retail);
		
		JLabel gui_label_cellno = new JLabel("Cell No:*");
		gui_label_cellno.setBounds(679, 99, 54, 14);
		contentPane.add(gui_label_cellno);
		
		gui_textfield_cellno = new JTextField();
		gui_textfield_cellno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String get_cellno_details=gui_textfield_cellno.getText();
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					smt=c.createStatement();
					String sql="SELECT * FROM CUSTOMERS WHERE CELL_NO='"+get_cellno_details+"';";
					ResultSet rs=smt.executeQuery(sql);
					boolean found=false;
					while(rs.next()){
						found=true;
						gui_textfield_customer.setText(rs.getString("name"));
						gui_textfield_phno.setText(rs.getString("Phone_no"));
						gui_textfield_code.setText(rs.getString("code"));
						gui_textfield_city.setText(rs.getString("city"));
						gui_textfield_address.setText(rs.getString("address"));
						gui_textfield_area.setText(rs.getString("area"));
						gui_textfield_pincode.setText(rs.getString("pincode"));
						gui_textfield_email.setText(rs.getString("email"));
						
					}
					if(!found){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("No Deatils Found");
						error_display_obj.setVisible(true);
					}
					smt.close();
					c.close();
				}catch(Exception e){
					String error=e.getMessage();
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(error);
					error_display_obj.setVisible(true);
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
				}
			}
		});
		gui_textfield_cellno.setBounds(733, 96, 129, 25);
		contentPane.add(gui_textfield_cellno);
		gui_textfield_cellno.setColumns(10);
		
		JLabel gui_label_email = new JLabel("E-mail:");
		gui_label_email.setBounds(10, 186, 46, 14);
		contentPane.add(gui_label_email);
		
		gui_textfield_email = new JTextField();
		gui_textfield_email.setBounds(79, 177, 239, 25);
		contentPane.add(gui_textfield_email);
		gui_textfield_email.setColumns(10);
		
		JPanel gui_panel_balance = new JPanel();
		gui_panel_balance.setBackground(new Color(154, 205, 50));
		gui_panel_balance.setBounds(657, 600, 221, 86);
		contentPane.add(gui_panel_balance);
		gui_panel_balance.setLayout(null);
		
		JLabel gui_label_received = new JLabel("Received");
		gui_label_received.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_received.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_label_received.setBounds(10, 11, 81, 14);
		gui_panel_balance.add(gui_label_received);
		
		JLabel gui_label_balance = new JLabel("Balance");
		gui_label_balance.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_balance.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_label_balance.setBounds(111, 11, 79, 14);
		gui_panel_balance.add(gui_label_balance);
		
		gui_textfield_received = new JTextField();
		gui_textfield_received.setEditable(false);
		gui_textfield_received.setHorizontalAlignment(SwingConstants.CENTER);
		gui_textfield_received.setText("0.00");
		gui_textfield_received.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_received.setBounds(10, 33, 86, 29);
		gui_panel_balance.add(gui_textfield_received);
		gui_textfield_received.setColumns(10);
		
		gui_textfield_balance = new JTextField();
		gui_textfield_balance.setEditable(false);
		gui_textfield_balance.setText("0.00");
		gui_textfield_balance.setHorizontalAlignment(SwingConstants.CENTER);
		gui_textfield_balance.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_balance.setBounds(121, 33, 90, 29);
		gui_panel_balance.add(gui_textfield_balance);
		gui_textfield_balance.setColumns(10);
		
		JLabel gui_label_category = new JLabel("Category:");
		gui_label_category.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_category.setBounds(37, 214, 84, 23);
		contentPane.add(gui_label_category);
		
		gui_combobox_category = new JComboBox();
		gui_combobox_category.setModel(new DefaultComboBoxModel(new String[] {"---select---", "Fresh vegetables", "Fresh fruits", "Refrigerated items", "Frozen", "Condiments / Sauces", "Various groceries", "Canned foods", "Spices & herbs", "Dairy", "Cheese", "Meat", "Seafood", "Beverages", "Baked goods", "Baking", "Snacks", "Themed meals", "Baby stuff", "Pets", "Personal care", "Medicine", "Kitchen", "Cleaning products", "Office supplies", "Other stuff", "Carcinogens"}));
		gui_combobox_category.setBounds(124, 217, 209, 20);
		contentPane.add(gui_combobox_category);
		
		JLabel gui_label_quantity = new JLabel("Quantity:");
		gui_label_quantity.setHorizontalAlignment(SwingConstants.CENTER);
		gui_label_quantity.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_label_quantity.setBounds(733, 214, 81, 20);
		contentPane.add(gui_label_quantity);
		
		gui_spinner_quantity = new JSpinner();
		gui_spinner_quantity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(permission_to_quantity){
				 int dup=(int)gui_spinner_quantity.getValue();
					
					quantity=(int)gui_spinner_quantity.getValue();
					model.setValueAt(dup, count-1, 4);
					model.setValueAt((quantity*rate),count-1,8);
					model.setValueAt(df.format((0.5/100)*((double)model.getValueAt(count-1, 8))), count-1, 6);
					total_table=0;total_discount=0;
					for(int i=0;i<count;i++){
				    total_table+=(double)model.getValueAt(i, 8);
				    total_discount+=(0.5/100)*((double)model.getValueAt(i, 8));
					}
					total_vat=(1.25/100)*total_table;
					gui_textfield_discount.setText(""+df.format(total_discount));
					gui_textfield_totalvat.setText(""+df.format(total_vat));
					gui_textfield_products.setText(""+count);
					subtotal=total_table;
					total=subtotal+total_vat-total_discount;
					value=Math.ceil(total);
					balance=total-received;
					To_Words to_words_obj=new To_Words((int)(Math.ceil(total)));
					gui_textfield_inwords.setText(to_words_obj.output());
					gui_radiobutton_inperson.setSelected(true);
					gui_textfield_subtotal.setText(""+df.format(subtotal));
					gui_textfield_total.setText(""+Math.ceil(total));
					gui_textfield_received.setText(""+df.format(Math.ceil(received)));
					gui_textfield_balance.setText(""+df.format(Math.ceil(balance)));
				}
			}
		});
		gui_spinner_quantity.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		gui_spinner_quantity.setBounds(817, 214, 46, 23);
		contentPane.add(gui_spinner_quantity);
		
		JPanel gui_panel_cash = new JPanel();
		gui_panel_cash.setBorder(null);
		gui_panel_cash.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_panel_cash.setBackground(new Color(240,248,255));
		gui_panel_cash.setBounds(10, 463, 365, 25);
		contentPane.add(gui_panel_cash);
		gui_panel_cash.setLayout(null);
		
		gui_radiobutton_cash = new JRadioButton("Cash");
		gui_radiobutton_cash.setBackground(new Color(240,248,255));
		gui_radiobutton_cash.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_cash.setBounds(6, 0, 59, 23);
		gui_radiobutton_cash.setSelected(true);
		gui_panel_cash.add(gui_radiobutton_cash);
		
		JRadioButton gui_radiobutton_credit = new JRadioButton("Credit");
		gui_radiobutton_credit.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_credit.setBackground(new Color(240,248,255));
		gui_radiobutton_credit.setBounds(88, 0, 66, 23);
		gui_panel_cash.add(gui_radiobutton_credit);
		
		JRadioButton gui_radiobutton_card = new JRadioButton("Card");
		gui_radiobutton_card.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_card.setBackground(new Color(240,248,255));
		gui_radiobutton_card.setBounds(178, 0, 54, 23);
		gui_panel_cash.add(gui_radiobutton_card);
		
		JRadioButton gui_radiobutton_cheque = new JRadioButton("Cheque");
		gui_radiobutton_cheque.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_cheque.setBackground(new Color(240,248,255));
		gui_radiobutton_cheque.setBounds(261, 0, 82, 23);
		gui_panel_cash.add(gui_radiobutton_cheque);
		ButtonGroup group_cash=new ButtonGroup();
		group_cash.add(gui_radiobutton_cash);
		group_cash.add(gui_radiobutton_credit);
		group_cash.add(gui_radiobutton_card);
		group_cash.add(gui_radiobutton_cheque);
		
		JLabel gui_label_products = new JLabel("Products");
		gui_label_products.setBounds(37, 519, 54, 14);
		contentPane.add(gui_label_products);
		
		gui_textfield_products = new JTextField();
		gui_textfield_products.setEditable(false);
		gui_textfield_products.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_products.setHorizontalAlignment(SwingConstants.RIGHT);
		gui_textfield_products.setText("0");
		gui_textfield_products.setBounds(97, 516, 85, 20);
		contentPane.add(gui_textfield_products);
		gui_textfield_products.setColumns(10);
		
		JLabel gui_label_totalvat = new JLabel("Total VAT ");
		gui_label_totalvat.setBounds(37, 544, 62, 22);
		contentPane.add(gui_label_totalvat);
		
		gui_textfield_totalvat = new JTextField();
		gui_textfield_totalvat.setEditable(false);
		gui_textfield_totalvat.setHorizontalAlignment(SwingConstants.RIGHT);
		gui_textfield_totalvat.setText("0.00");
		gui_textfield_totalvat.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_totalvat.setBounds(97, 546, 86, 20);
		contentPane.add(gui_textfield_totalvat);
		gui_textfield_totalvat.setColumns(10);
		
		JLabel gui_label_discount = new JLabel("Discount");
		gui_label_discount.setBounds(208, 519, 58, 14);
		contentPane.add(gui_label_discount);
		
		gui_textfield_discount = new JTextField();
		gui_textfield_discount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double temp_discount;
				temp_discount=Double.parseDouble(gui_textfield_discount.getText());
				if(gui_radiobutton_doordelivery.isSelected()){
				}
			}
		});
		gui_textfield_discount.setEditable(false);
		gui_textfield_discount.setHorizontalAlignment(SwingConstants.RIGHT);
		gui_textfield_discount.setText("0.00");
		gui_textfield_discount.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_discount.setBounds(276, 516, 99, 20);
		contentPane.add(gui_textfield_discount);
		gui_textfield_discount.setColumns(10);
		
		JLabel gui_label_subtotal = new JLabel("Sub Total");
		gui_label_subtotal.setBounds(210, 550, 58, 14);
		contentPane.add(gui_label_subtotal);
		
		gui_textfield_subtotal = new JTextField();
		gui_textfield_subtotal.setEditable(false);
		gui_textfield_subtotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		gui_textfield_subtotal.setText("0.00");
		gui_textfield_subtotal.setBounds(276, 546, 99, 20);
		contentPane.add(gui_textfield_subtotal);
		gui_textfield_subtotal.setColumns(10);
		
		gui_radiobutton_inperson = new JRadioButton("In Person");
		gui_radiobutton_inperson.setBackground(new Color(240,248,255));
		gui_radiobutton_inperson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(gui_radiobutton_inperson.isSelected()){
					value=total;
					To_Words object=new To_Words((int)value);
					gui_textfield_inwords.setText(object.output());
					gui_textfield_total.setText(""+Math.ceil(value));
				}
			}
		});
		gui_radiobutton_inperson.setSelected(true);
		gui_radiobutton_inperson.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_inperson.setBounds(443, 519, 111, 37);
		contentPane.add(gui_radiobutton_inperson);
		
		gui_radiobutton_doordelivery = new JRadioButton("Door Delivery(+40)");
		gui_radiobutton_doordelivery.setBackground(new Color(240,248,255));
		gui_radiobutton_doordelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(gui_radiobutton_doordelivery.isSelected()){
					value=total+40;
					To_Words object=new To_Words((int)value);
					gui_textfield_inwords.setText(object.output());
					gui_textfield_total.setText(""+Math.ceil(value));
				}
			}
		});
		
		
		gui_radiobutton_doordelivery.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_radiobutton_doordelivery.setBounds(443, 558, 170, 32);
		contentPane.add(gui_radiobutton_doordelivery);
		
		delivery=new ButtonGroup();
		delivery.add(gui_radiobutton_doordelivery);
		delivery.add(gui_radiobutton_inperson);
		
		JButton gui_button_resetitems = new JButton("Reset items");
		gui_button_resetitems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<count;i++){
					for(int j=0;j<model.getColumnCount();j++){
						model.setValueAt(null, i, j);
					}
				}
				count=0;
				total=0;
				gui_combobox_category.setSelectedIndex(0);
				gui_textfield_inwords.setText("Zero Ruppees Only");
				gui_spinner_quantity.setValue(0);
				gui_textfield_products.setText(""+count);
				gui_textfield_totalvat.setText("0.00");
				gui_textfield_subtotal.setText("0.00");
				gui_textfield_discount.setText("0.00");
				gui_textfield_total.setText("0.00");
				gui_textfield_balance.setText("0.00");
			}
		});
		gui_button_resetitems.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\reset.png"));
		gui_button_resetitems.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_button_resetitems.setBounds(739, 177, 124, 26);
		contentPane.add(gui_button_resetitems);
		
		JButton gui_button_check = new JButton("Check");
		gui_button_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String check=gui_textfield_code.getText();
				if(check.isEmpty()){
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display("Please Enter Code");
					error_display_obj.setVisible(true);
				}
				else{
				Connection c=null;
				Statement smt=null;
				try{
					Class.forName("org.sqlite.JDBC");
					c=DriverManager.getConnection("jdbc:sqlite:bill.db");
					c.setAutoCommit(false);
					smt=c.createStatement();
					String sql="SELECT * FROM CUSTOMERS WHERE CODE='"+check+"';";
					ResultSet rs=smt.executeQuery(sql);
					boolean present=false;
					while(rs.next()){
						present=true;
					}
					if(present){
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("Code already exists choose another");
						error_display_obj.setVisible(true);
					}
					else{
						Error_Display error_display_obj=new Error_Display();
						error_display_obj.display("Code accepted");
						error_display_obj.setVisible(true);
					}
				}
				catch(Exception e){
					String error=e.getMessage();
					Error_Display error_display_obj=new Error_Display();
					error_display_obj.display(error);
					error_display_obj.setVisible(true);
				}
			}
			}
		});
		gui_button_check.setFont(new Font("Tahoma", Font.BOLD, 10));
		gui_button_check.setBounds(393, 92, 79, 23);
		contentPane.add(gui_button_check);
		DateFormat date=new SimpleDateFormat("dd-MM-yyyy");
		Date obj=new Date();
		gui_textfield_date = new JTextField();
		gui_textfield_date.setEditable(false);
		gui_textfield_date.setBackground(new Color(240,248,255));
		gui_textfield_date.setText(date.format(obj)+"");
		gui_textfield_date.setForeground(Color.BLACK);
		gui_textfield_date.setHorizontalAlignment(SwingConstants.CENTER);
		gui_textfield_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_date.setBounds(724, 64, 94, 20);
		contentPane.add(gui_textfield_date);
		gui_textfield_date.setColumns(10);
		
		JTextArea gui_textarea_date = new JTextArea();
		gui_textarea_date.setLineWrap(true);
		gui_textarea_date.setFont(new Font("Tahoma", Font.PLAIN, 9));
		gui_textarea_date.setBackground(new Color(240,248,255));
		gui_textarea_date.setEditable(false);
		gui_textarea_date.setBorder(null);
		gui_textarea_date.setText("dd-mm-yyyy");
		gui_textarea_date.setBounds(819, 70, 59, 14);
		contentPane.add(gui_textarea_date);
		
		JButton gui_button_modify = new JButton("Modify");
		gui_button_modify.setEnabled(false);
		gui_button_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(count==1)
				{
					gui_button_modify.setEnabled(false);
				}
				count=count-1;
				for(int i=0;i<9;i++){
					model.setValueAt(null, count, i);
				}
				total_table=0;total_discount=0;
				for(int i=0;i<count;i++){
			    total_table+=(double)model.getValueAt(i, 8);
			    total_discount+=(0.5/100)*((double)model.getValueAt(i, 8));
				}
				total_vat=(1.25/100)*total_table;
				gui_textfield_discount.setText(""+df.format(total_discount));
				gui_textfield_totalvat.setText(""+df.format(total_vat));
				gui_textfield_products.setText(""+count);
				subtotal=total_table;
				total=subtotal+total_vat-total_discount;
				value=Math.ceil(total);
				balance=total-received;
				To_Words object=new To_Words((int)value);
				gui_textfield_inwords.setText(object.output());
				gui_radiobutton_inperson.setSelected(true);
				gui_textfield_subtotal.setText(""+df.format(subtotal));
				gui_textfield_total.setText(""+df.format(Math.ceil(total)));
				gui_textfield_received.setText(""+df.format(Math.ceil(received)));
				gui_textfield_balance.setText(""+df.format(Math.ceil(balance)));
			}
		});
		gui_button_modify.setIcon(new ImageIcon("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\edit_2add.png"));
		gui_button_modify.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_button_modify.setBounds(470, 478, 128, 34);
		contentPane.add(gui_button_modify);
		
		JLabel gui_label_inwords = new JLabel("In Words:");
		gui_label_inwords.setBounds(10, 598, 54, 30);
		contentPane.add(gui_label_inwords);
		
		gui_textfield_inwords = new JTextField();
		gui_textfield_inwords.setText("Zero Ruppees Only");
		gui_textfield_inwords.setBorder(null);
		gui_textfield_inwords.setEditable(false);
		gui_textfield_inwords.setFont(new Font("Tahoma", Font.BOLD, 11));
		gui_textfield_inwords.setBackground(new Color(240,248,255));
		gui_textfield_inwords.setBounds(75, 597, 503, 33);
		contentPane.add(gui_textfield_inwords);
		gui_textfield_inwords.setColumns(10);
	gui_combobox_category.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				permission_to_item=false;
				permission_to_quantity=false;
				gui_spinner_quantity.setValue(0);
				gui_combobox_item.removeAllItems();
				if(gui_combobox_category.getSelectedItem().toString()=="Fresh vegetables" ){
					
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Asparagus");
					gui_combobox_item.addItem("Broccoli");
					gui_combobox_item.addItem("Carrots");
					gui_combobox_item.addItem("Cauliflower");
					gui_combobox_item.addItem("Celery");
					gui_combobox_item.addItem("Corn");
					gui_combobox_item.addItem("Cucumbers");
					gui_combobox_item.addItem("Lettuce / Greens");
					gui_combobox_item.addItem("Onions");
					gui_combobox_item.addItem("Potatoes");
					gui_combobox_item.addItem("Tomatoes");
					gui_combobox_item.addItem("Brinjal");
					gui_combobox_item.addItem("Beetroot");
					gui_combobox_item.addItem("Beans");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Fresh fruits"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Apples");
					gui_combobox_item.addItem("Bananas");
					gui_combobox_item.addItem("Berries");
					gui_combobox_item.addItem("Cherries");
					gui_combobox_item.addItem("Grapefruit");
					gui_combobox_item.addItem("Grapes");
					gui_combobox_item.addItem("Lemons");
					gui_combobox_item.addItem("Melon");
					gui_combobox_item.addItem("Oranges");
					gui_combobox_item.addItem("Papaya");
					gui_combobox_item.addItem("Peaches");
					gui_combobox_item.addItem("Nectarines");
					gui_combobox_item.addItem("Mango");
					gui_combobox_item.addItem("Pears");
				}
				else	if(gui_combobox_category.getSelectedItem().toString()=="Refrigerated items"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Bagels");
					gui_combobox_item.addItem("Chip dip");
					gui_combobox_item.addItem("English muffins");
					gui_combobox_item.addItem("Eggs / Fake eggs");
					gui_combobox_item.addItem("Fruit juice");
					gui_combobox_item.addItem("Hummus");
					gui_combobox_item.addItem("Ready-bake breads");
					gui_combobox_item.addItem("Tofu");
					gui_combobox_item.addItem("Tortillas");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Frozen"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Breakfasts");
					gui_combobox_item.addItem("Burritos");
					gui_combobox_item.addItem("Fish sticks");
					gui_combobox_item.addItem("Ice cream / Sorbet");
					gui_combobox_item.addItem("Juice concentrate");
					gui_combobox_item.addItem("Pizza / Pizza Rolls");
					//gui_combobox_item.addItem("Popsicles");
					gui_combobox_item.addItem("Fries / Tater tots");
					gui_combobox_item.addItem("Vegetables");
					gui_combobox_item.addItem("TV dinners");
					gui_combobox_item.addItem("Veggie burgers");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Condiments / Sauces"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("BBQ sauce");
					gui_combobox_item.addItem("Honey");
					gui_combobox_item.addItem("Gravy");
					gui_combobox_item.addItem("Jam / Jelly / Preserves");
					gui_combobox_item.addItem("Hot sauce");
					gui_combobox_item.addItem("Ketchup / Mustard");
					gui_combobox_item.addItem("Mayonnaise");
					gui_combobox_item.addItem("Pasta sauce");
					gui_combobox_item.addItem("Salad dressing");
					gui_combobox_item.addItem("Relish");
					gui_combobox_item.addItem("Salsa");
					gui_combobox_item.addItem("Soy sauce");
					gui_combobox_item.addItem("Steak sauce");
					gui_combobox_item.addItem("Syrup");
					gui_combobox_item.addItem("Worcestershire sauce");
					
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Various groceries"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Bouillon cubes");
					gui_combobox_item.addItem("Cereal");
					gui_combobox_item.addItem("Coffee / Filters");
					gui_combobox_item.addItem("Instant potatoes");
					gui_combobox_item.addItem("Lemon / Lime juice");
					gui_combobox_item.addItem("Mac & cheese");
					gui_combobox_item.addItem("Olive oil");
					gui_combobox_item.addItem("Pancake / Waffle mix");
					gui_combobox_item.addItem("Pasta");
					gui_combobox_item.addItem("Peanut butter");
					gui_combobox_item.addItem("Pickles");
					gui_combobox_item.addItem("Rice");
					gui_combobox_item.addItem("Tea");
					gui_combobox_item.addItem("Vinegar");
					gui_combobox_item.addItem("Vegetable oil");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Canned foods"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Applesauce");
					gui_combobox_item.addItem("Baked beans");
					gui_combobox_item.addItem("Chili");
					gui_combobox_item.addItem("Fruit");
					gui_combobox_item.addItem("Olives");
					gui_combobox_item.addItem("Tinned meats");
					gui_combobox_item.addItem("Tuna / Chicken");
					gui_combobox_item.addItem("Soups");
					gui_combobox_item.addItem("Tomatoes");
					gui_combobox_item.addItem("Veggies");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Spices & herbs"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Basil");
					gui_combobox_item.addItem("Black pepper");
					gui_combobox_item.addItem("Cilantro");
					gui_combobox_item.addItem("Cinnamon");
					gui_combobox_item.addItem("Garlic");
					gui_combobox_item.addItem("Ginger");
					gui_combobox_item.addItem("Mint");
					gui_combobox_item.addItem("Oregano");
					gui_combobox_item.addItem("Paprika");
					gui_combobox_item.addItem("Parsley");
					gui_combobox_item.addItem("Red pepper");
					gui_combobox_item.addItem("Spice mix");
					gui_combobox_item.addItem("Salt");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Dairy"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Butter / Margarine");
					gui_combobox_item.addItem("Cottage cheese");
					gui_combobox_item.addItem("Half & half");
					gui_combobox_item.addItem("Milk");
					gui_combobox_item.addItem("Sour cream");
					gui_combobox_item.addItem("Whipped cream");
					gui_combobox_item.addItem("Yogurt");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Cheese"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Bleu cheese");
					gui_combobox_item.addItem("Cheddar");
					gui_combobox_item.addItem("Cottage cheese");
					gui_combobox_item.addItem("Cream cheese");
					gui_combobox_item.addItem("Feta");
					gui_combobox_item.addItem("Goat cheese");
					gui_combobox_item.addItem("Mozzarella / Provolone");
					gui_combobox_item.addItem("Parmesan");
					gui_combobox_item.addItem("Provolone");
					gui_combobox_item.addItem("Ricotta");
					gui_combobox_item.addItem("Swiss");
					gui_combobox_item.addItem("Sandwich slices");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Meat"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Bacon / Sausage");
					gui_combobox_item.addItem("Beef");
					gui_combobox_item.addItem("Chicken");
					gui_combobox_item.addItem("Ground beef / Turkey");
					gui_combobox_item.addItem("Ham / Pork");
					gui_combobox_item.addItem("Hot dogs");
					gui_combobox_item.addItem("Lunchmeat");
					gui_combobox_item.addItem("Turkey");
					
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Seafood"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Catfish");
					gui_combobox_item.addItem("Crab");
					gui_combobox_item.addItem("Lobster");
					gui_combobox_item.addItem("Mussels");
					gui_combobox_item.addItem("Oysters");
					gui_combobox_item.addItem("Salmon");
					gui_combobox_item.addItem("Shrimp");
					gui_combobox_item.addItem("Tilapia");
					gui_combobox_item.addItem("Tuna");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Beverages"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Beer");
					gui_combobox_item.addItem("Club soda / Tonic");
					gui_combobox_item.addItem("Champagne");
					gui_combobox_item.addItem("Gin");
					gui_combobox_item.addItem("Juice");
					gui_combobox_item.addItem("Mixers");
					gui_combobox_item.addItem("Red wine / White wine");
					gui_combobox_item.addItem("Rum");
					gui_combobox_item.addItem("Saké");
					gui_combobox_item.addItem("Soda pop");
					gui_combobox_item.addItem("Sports drink");
					gui_combobox_item.addItem("Whiskey");
					gui_combobox_item.addItem("Vodka");
					
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Baked goods"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Bagels / Croissants");
					gui_combobox_item.addItem("Buns / Rolls");
					gui_combobox_item.addItem("Cake / Cookies");
					gui_combobox_item.addItem("Donuts / Pastries");
					gui_combobox_item.addItem("Fresh bread");
					gui_combobox_item.addItem("Sliced bread");
					gui_combobox_item.addItem("Pie! Pie! Pie!");
					gui_combobox_item.addItem("Pita bread");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Baking"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Baking powder / Soda");
					gui_combobox_item.addItem("Bread crumbs");
					gui_combobox_item.addItem("Cake / Brownie mix");
					gui_combobox_item.addItem("Cake icing / Decorations");
					gui_combobox_item.addItem("Chocolate chips / Cocoa");
					gui_combobox_item.addItem("Flour");
					gui_combobox_item.addItem("Shortening");
					gui_combobox_item.addItem("Sugar");
					gui_combobox_item.addItem("Sugar substitute");
					
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Snacks"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Candy / Gum");
					gui_combobox_item.addItem("Cookies");
					gui_combobox_item.addItem("Crackers");
					gui_combobox_item.addItem("Dried fruit");
					gui_combobox_item.addItem("Granola bars / Mix");
					gui_combobox_item.addItem("Nuts / Seeds");
					gui_combobox_item.addItem("Oatmeal");
					gui_combobox_item.addItem("Popcorn");
					gui_combobox_item.addItem("Potato / Corn chips");
					gui_combobox_item.addItem("Pretzels");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Themed meals"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Burger night");
					gui_combobox_item.addItem("Chili night");
					gui_combobox_item.addItem("Pizza night");
					gui_combobox_item.addItem("Spaghetti night");
					gui_combobox_item.addItem("Take-out deli food");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Baby stuff"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Baby food");
					gui_combobox_item.addItem("Diapers");
					gui_combobox_item.addItem("Formula");
					gui_combobox_item.addItem("Lotion");
					gui_combobox_item.addItem("Baby wash");
					gui_combobox_item.addItem("Wipes");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Pets"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Cat food / Treats");
					gui_combobox_item.addItem("Cat litter");
					gui_combobox_item.addItem("Dog food / Treats");
					gui_combobox_item.addItem("Flea treatment");
					gui_combobox_item.addItem("Pet shampoo");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Personal care"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Antiperspirant / Deodorant");
					gui_combobox_item.addItem("Bath soap / Hand soap");
					gui_combobox_item.addItem("Condoms / Other b.c.");
					gui_combobox_item.addItem("Cosmetics");
					gui_combobox_item.addItem("Cotton swabs / Balls");
					gui_combobox_item.addItem("Facial cleanser");
					gui_combobox_item.addItem("Facial tissue");
					gui_combobox_item.addItem("Feminine products");
					gui_combobox_item.addItem("Floss");
					gui_combobox_item.addItem("Hair gel / Spray");
					gui_combobox_item.addItem("Lip balm");
					gui_combobox_item.addItem("Moisturizing lotion");
					gui_combobox_item.addItem("Mouthwash");
					gui_combobox_item.addItem("Razors / Shaving cream");
					gui_combobox_item.addItem("Shampoo / Conditioner");
					gui_combobox_item.addItem("Sunblock");
					gui_combobox_item.addItem("Toilet paper");
					gui_combobox_item.addItem("Toothpaste");
					gui_combobox_item.addItem("Vitamins / Supplements");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Medicine"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Allergy");
					gui_combobox_item.addItem("Antibiotic");
					gui_combobox_item.addItem("Antidiarrheal");
					gui_combobox_item.addItem("Aspirin");
					gui_combobox_item.addItem("Antacid");
					gui_combobox_item.addItem("Band-aids / Medical");
					gui_combobox_item.addItem("Cold / Flu / Sinus");
					gui_combobox_item.addItem("Pain reliever");
					gui_combobox_item.addItem("Prescription pick-up");
					
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Kitchen"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Aluminum foil");
					gui_combobox_item.addItem("Napkins");
					gui_combobox_item.addItem("Non-stick spray");
					gui_combobox_item.addItem("Paper towels");
					gui_combobox_item.addItem("Plastic wrap");
					gui_combobox_item.addItem("Sandwich / Freezer bags");
					gui_combobox_item.addItem("Wax paper");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Cleaning products"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Air freshener");
					gui_combobox_item.addItem("Bathroom cleaner");
					gui_combobox_item.addItem("Bleach / Detergent");
					gui_combobox_item.addItem("Dish / Dishwasher soap");
					gui_combobox_item.addItem("Garbage bags");
					gui_combobox_item.addItem("Glass cleaner");
					gui_combobox_item.addItem("Mop head / Vacuum bags");
					gui_combobox_item.addItem("Sponges / Scrubbers");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Office supplies"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("CDRs / DVDRs");
					gui_combobox_item.addItem("Notepad / Envelopes");
					gui_combobox_item.addItem("Glue / Tape");
					gui_combobox_item.addItem("Pens / Pencils");
					gui_combobox_item.addItem("Printer paper");
					gui_combobox_item.addItem("Postage stamps");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Other stuff"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Automotive");
					gui_combobox_item.addItem("Batteries");
					gui_combobox_item.addItem("Charcoal / Propane");
					gui_combobox_item.addItem("Flowers / Greeting card");
					gui_combobox_item.addItem("Insect repellent");
					gui_combobox_item.addItem("Light bulbs");
					gui_combobox_item.addItem("Newspaper / Magazine");
					gui_combobox_item.addItem("Random impulse buy");
				}
				else if(gui_combobox_category.getSelectedItem().toString()=="Carcinogens"){
					gui_combobox_item.addItem("--select--");
					gui_combobox_item.addItem("Asbestos");
					gui_combobox_item.addItem("Arsenic");
					gui_combobox_item.addItem("Cigarettes");
					gui_combobox_item.addItem("Radionuclides");
					gui_combobox_item.addItem("Vinyl chloride");
				}
				permission_to_item=true;
				
				//gui_combobox_item.addActionListener(gui_combobox_item);
			}
			
		});
		
		gui_combobox_item.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				if(permission_to_item){
					permission_to_quantity=false;
					gui_spinner_quantity.setValue(0);
					item_rates_obj=new Item_Rates();
					item_codes_obj=new Item_Codes();
					
					if(gui_combobox_item.getSelectedItem().toString()!="--select--"){
				    model=gui_table.getModel();
				    model.setValueAt(count+1, count, 0);
					model.setValueAt(gui_combobox_item.getSelectedItem().toString(), count, 2);
				    model.setValueAt(gui_spinner_quantity.getValue(), count, 4);
					model.setValueAt(item_rates_obj.get_rate(gui_combobox_item.getSelectedItem().toString()), count, 3);
					model.setValueAt(item_codes_obj.get_code(gui_combobox_item.getSelectedItem().toString()), count, 1);
					model.setValueAt(0.5, count, 5);
					model.setValueAt(1.25, count, 7);
					rate=(double)model.getValueAt(count, 3);
					double quantity=(int)model.getValueAt(count, 4);
					model.setValueAt((quantity*rate),count,8);
					count++;
					permission_to_quantity=true;
					gui_spinner_quantity.setValue(1);
					gui_button_modify.setEnabled(true);
					
			}
					
					
			}
		}
		});
		
		
		
		
	}
}
