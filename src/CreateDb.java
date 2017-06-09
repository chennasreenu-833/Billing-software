import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDb {
	Connection con=null;
	Statement smt=null;
	public CreateDb(){
		try {
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection("jdbc:sqlite:bill.db");
			con.setAutoCommit(false);
			smt=con.createStatement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create_table_customers(){
		String sql="CREATE TABLE IF NOT EXISTS CUSTOMERS(\n"
				+"NAME TEXT NOT NULL,\n"
				+"CODE TEXT PRIMARY KEY NOT NULL,\n"
				+"PHONE_NO TEXT,\n"
				+"CELL_NO TEXT NOT NULL,\n"
				+"ADDRESS TEXT NOT NULL,\n"
				+"AREA TEXT NOT NULL,\n"
				+"CITY TEXT NOT NULL,\n"
				+"PINCODE TEXT,\n"
				+"EMAIL TEXT);";
		try {
			smt.execute(sql);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create_table_order_details(){
		String sql="CREATE TABLE IF NOT EXISTS order_details(\n"
				+"order_id int not null,\n"
				+"item_code text not null,\n"
				+"quantity int);";
		try {
			smt.execute(sql);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void create_table_orders(){
		String sql="CREATE TABLE IF NOT EXISTS orders(\n"
				+"bill_no INT UNIQUE PRIMARY KEY NOT NULL,\n"
				+"customer_code text not null,\n"
				+"date text not null,\n"
				+"amount real);";
		try {
			smt.execute(sql);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void create_table_items(){
		String sql="CREATE TABLE IF NOT EXISTS items(\n"
				+"item_code text primary key not null,\n"
				+"item_name text not null,\n"
				+"item_rate real);";
		
		try {
			smt.execute(sql);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close_connection(){
		try {
			smt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
