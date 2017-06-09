import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PropagateTable {
	Connection con=null;
	Statement smt=null;
	public PropagateTable(){
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
	public void propagate_table_items(){
		String sql="INSERT OR IGNORE INTO ITEMS VALUES('asp001','Asparagus',20.00);";
		try {
			smt.executeUpdate(sql);
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql="INSERT OR IGNORE INTO ITEMS VALUES('bro002','Broccoli',25.00);";
		try {
			smt.executeUpdate(sql);
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void include_item(String name,String code, double rate){
		String sql="INSERT OR IGNORE INTO ITEMS VALUES('"+code+"','"+name+"',"+rate+");";
		try {
			smt.executeUpdate(sql);
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
