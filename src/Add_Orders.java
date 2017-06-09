import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Add_Orders {
	Connection c=null;
	Statement smt=null;
	public Add_Orders(){
		try{
			Class.forName("org.sqlite.JDBC");
			c=DriverManager.getConnection("jdbc:sqlite:bill.db");
			c.setAutoCommit(false);
			smt=c.createStatement();
		}
		catch(Exception e){
			String error=e.getMessage();
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(error);
			error_display_obj.setVisible(true);
		}
	}
	public void store_orders(int add_billno,String add_customercode,String add_date,double add_amount){
		String sql="INSERT INTO ORDERS VALUES("+add_billno+",'"+add_customercode+"','"+add_date+"',"+add_amount+");";
		try {
			smt.executeUpdate(sql);
			if(add_customercode.isEmpty()){
				Error_Display error_display_obj=new Error_Display();
				error_display_obj.display("Customer Code is empty cant save");
				error_display_obj.setVisible(true);
				c.rollback();
			}
			c.commit();
		} catch (Exception e) {
			String error=e.getMessage();
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(error);
			error_display_obj.setVisible(true);

		}
	}
	public void store_orders_details(int add_details_billno,String add_details_item,int add_details_quantity){
		String sql="INSERT INTO ORDER_DETAILS VALUES("+add_details_billno+",'"+add_details_item+"',"+add_details_quantity+");";
		try{
			smt.executeUpdate(sql);
			c.commit();
			
		}
		 catch (Exception e) {
			 String error=e.getMessage();
				Error_Display error_display_obj=new Error_Display();
				error_display_obj.display(error);
				error_display_obj.setVisible(true);
			}
	}
	public void close_connection() throws SQLException{
		smt.close();
		c.close();
	}
	
	

}
