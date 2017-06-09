import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Add_Customers {
	Connection c=null;
	Statement smt=null;
	public Add_Customers(){
	try{
		Class.forName("org.sqlite.JDBC");
		c=DriverManager.getConnection("jdbc:sqlite:bill.db");
		c.setAutoCommit(false);
		
	}catch(Exception e){
		String error=e.getMessage();
		Error_Display error_display_obj=new Error_Display();
		error_display_obj.display(error);
		error_display_obj.setVisible(true);
	}
	}
	public void Store(String add_name,String add_code,String add_phno,String add_cellno,String add_address,String add_area,String add_city,String add_pincode,String add_email) throws SQLException{
		smt=c.createStatement();
		String sql="INSERT INTO CUSTOMERS VALUES('"+add_name+"','"+add_code+"','"+add_phno+"','"+add_cellno+"','"+add_address+"','"+add_area+"','"+add_city+"','"+add_pincode+"','"+add_email+"');";
		
		boolean name=false,code=false,cellno=false,address=false,area=false,city=false;
		if(add_name.isEmpty()){
			name=true;
		}
		if(add_code.isEmpty()){
			code=true;
		}
		if(add_cellno.isEmpty()){
			cellno=true;
		}
		if(add_address.isEmpty()){
			address=true;
		}if(add_area.isEmpty()){
			area=true;
		}
		if(add_city.isEmpty()){
			city=true;
		}
		if(name||code||cellno||address||area||city){
			String err_msg="Empty field:";
			if(name){
				err_msg+="name, ";
				c.rollback();
			}
			if(code){
				err_msg+="code, ";
				c.rollback();
			}
			if(cellno){
				err_msg+="cellno, ";
				c.rollback();
			}
			if(address){
				err_msg+="address, ";
				c.rollback();
			}
			if(area){
				err_msg+="area, ";
				c.rollback();
			}
			if(city){
				err_msg+="city, ";
				c.rollback();
			}
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display(err_msg);
			error_display_obj.setVisible(true);
			c.close();
			smt.close();
		}
		else{
		smt.executeUpdate(sql);
		}
		c.commit();
		smt.close();
		c.close();
	}
	

}
