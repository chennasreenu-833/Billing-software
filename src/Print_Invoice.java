import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.text.AttributeSet.CharacterAttribute;

import com.itextpdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class Print_Invoice {
	
		Document doc=new Document();
	    Font f1=new Font(Font.FontFamily.HELVETICA,24,Font.BOLD);
	    Font f2=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
	    Font f3=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL);
	    Font f4=new Font(Font.FontFamily.HELVETICA,15,Font.BOLD);
	    Connection c=null;
	    Statement smt=null;
	    
	    public Print_Invoice(int invoice){
		try{
			Class.forName("org.sqlite.JDBC");
	    	c=DriverManager.getConnection("jdbc:sqlite:bill.db");
	    	smt=c.createStatement();
	    	String sql="SELECT DATE FROM ORDERS WHERE BILL_NO="+invoice+";";
	    	ResultSet rs=smt.executeQuery(sql);
			PdfWriter.getInstance(doc, new FileOutputStream(invoice+".pdf"));
			doc.open();
			PdfPTable heading=new PdfPTable(2);
			heading.setWidthPercentage(100);
			float heading_columnwidths[]={2f,10f};
			heading.setWidths(heading_columnwidths);
			Image logo_image=Image.getInstance("C:\\Users\\chennasreenu\\workspace\\BillingSoftware\\src\\images\\bird-logo.png");
			logo_image.scaleAbsolute(5f, 5f);
			PdfPCell logo=new PdfPCell(logo_image,true);
			logo.setBorder(Rectangle.NO_BORDER);
			logo.setHorizontalAlignment(Element.ALIGN_CENTER);
			logo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			heading.addCell(logo);
			f1.setColor(BaseColor.CYAN);
			PdfPCell title=new PdfPCell(new Paragraph("All In One General Stores",f1));
			title.setHorizontalAlignment(Element.ALIGN_CENTER);
			title.setBorder(Rectangle.NO_BORDER);
			heading.addCell(title);
			doc.add(heading);
			Chunk line=new Chunk();
			doc.add(new Chunk("------------------------------------------------------------------------------------------------------------------------------"));
			PdfPTable invoice_table=new PdfPTable(2);
			int invoice_widths[]={1,1};
			invoice_table.setWidthPercentage(100);
			invoice_table.setWidths(invoice_widths);
			invoice_table.setSpacingBefore(5);
			PdfPCell invoice_no=new PdfPCell(new Paragraph("Invoice No:"+invoice));
			invoice_no.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell date=new PdfPCell(new Paragraph("Date :"+rs.getString("date")));
			date.setHorizontalAlignment(Element.ALIGN_RIGHT);
			invoice_no.setBorder(Rectangle.NO_BORDER);
			date.setBorder(Rectangle.NO_BORDER);
			invoice_table.addCell(invoice_no);
			invoice_table.addCell(date);
			invoice_table.setSpacingAfter(5);
			doc.add(invoice_table);
			doc.add(new Chunk("-------------------------------------------------------------------------------------------------------------------------------------------------------"));
			
			sql="select name,code,phone_no,cell_no,address,area,city,pincode,email from customers,orders where bill_no="+invoice+" and customer_code=code;";
            rs=smt.executeQuery(sql);
			Paragraph name=new Paragraph(rs.getString("name").toUpperCase(),f2);
		    Paragraph ph_no=new Paragraph("Ph No    : "+rs.getString("phone_no"),f2);
			Paragraph cell_no=new Paragraph("Cell No  : "+rs.getString("cell_no"),f2);
			Paragraph address=new Paragraph("D/No      : "+rs.getString("address")+", "+rs.getString("area"),f2);
			Paragraph city=new Paragraph(rs.getString("city").substring(0, 1).toUpperCase()+ rs.getString("city").substring(1).toLowerCase()+" - "+rs.getString("pincode"),f2);
			Paragraph email=new Paragraph("e-mail   : "+rs.getString("email"),f2);
			email.setSpacingAfter(0);
			Paragraph customer=new Paragraph();
			customer.add(name);
			customer.add(address);
			customer.add(ph_no);
			customer.add(cell_no);
			customer.add(city);
			customer.add(email);
			
			doc.add(customer);
			doc.add(new Chunk("****************************************************************************************************************"));
			
			PdfPTable table=new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setSpacingBefore(30);
			float columnwidths[]={3f,8f,4f,4f,4f};
			table.setWidths(columnwidths);
			PdfPCell s_no=new PdfPCell(new Paragraph("S.No",f2));
			s_no.setHorizontalAlignment(Element.ALIGN_CENTER);
			s_no.setVerticalAlignment(Element.ALIGN_MIDDLE);
			PdfPCell item_name=new PdfPCell(new Paragraph("Item Name",f2));
			item_name.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell item_rate=new PdfPCell(new Paragraph("Item Rate",f2));
			item_rate.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell item_quantity=new PdfPCell(new Paragraph("Quantity",f2));
			item_quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell item_total=new PdfPCell(new Paragraph("Total",f2));
			item_total.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(s_no);
			table.addCell(item_name);
			table.addCell(item_rate);
			table.addCell(item_quantity);
			table.addCell(item_total);
			table.setSpacingBefore(0);
			table.setSpacingAfter(1);
			table.setWidthPercentage(80);
			
			sql="SELECT ITEM_NAME,ITEM_RATE,QUANTITY FROM ORDER_DETAILS,ITEMS WHERE ORDER_ID="+invoice+" AND ORDER_DETAILS.ITEM_CODE=ITEMS.ITEM_CODE;";
			rs=smt.executeQuery(sql);
			int count=1;
			double totoftable=0;
			while(rs.next()){
				PdfPCell sno=new PdfPCell(new Paragraph(""+count,f3));
				sno.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(sno);
				count++;
				PdfPCell itemname=new PdfPCell(new Paragraph(rs.getString("item_name"),f3));
				itemname.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(itemname);
				PdfPCell itemrate=new PdfPCell(new Paragraph(rs.getString("item_rate"),f3));
				itemrate.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(itemrate);
				PdfPCell itemquantity=new PdfPCell(new Paragraph(rs.getString("quantity"),f3));
				itemquantity.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(itemquantity);
				double totofitem,rateofitem;
				int quantityofitem;
				rateofitem=rs.getDouble("item_rate");
				quantityofitem=rs.getInt("quantity");
				totofitem=rateofitem*quantityofitem;
				totoftable+=totofitem;
				PdfPCell itemtotal=new PdfPCell(new Paragraph(""+totofitem,f3));
				itemtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(itemtotal);
			}
			
		    doc.add(table);
			doc.add(new Chunk("****************************************************************************************************************"));

			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			PdfPTable amount_table=new PdfPTable(2);
			int amounttablewidths[]={1,1};
			amount_table.setWidths(amounttablewidths);
			double totofvat=(1.25/100)*totoftable,totofdiscount=(0.5/100)*totoftable;
			PdfPCell products=new PdfPCell(new Paragraph("Products  :   "+(count-1),f2));
			products.setHorizontalAlignment(Element.ALIGN_LEFT);
		    amount_table.addCell(products);
		    PdfPCell discount=new PdfPCell(new Paragraph("Discount  :   "+df.format(totofdiscount),f2));
		    discount.setHorizontalAlignment(Element.ALIGN_LEFT);
		    amount_table.addCell(discount);
		      PdfPCell totvat=new PdfPCell(new Paragraph("Total VAT :   "+df.format(totofvat),f2));
		    totvat.setHorizontalAlignment(Element.ALIGN_LEFT);
		    amount_table.addCell(totvat);
		    PdfPCell subtotal=new PdfPCell(new Paragraph("Sub Total :   "+totoftable,f2));
		    subtotal.setHorizontalAlignment(Element.ALIGN_LEFT);
		    amount_table.addCell(subtotal);
		    amount_table.setSpacingAfter(10);
		    double final_amount=totoftable+totofvat-totofdiscount;
		    doc.add(amount_table);
		    sql="Select amount from orders where bill_no="+invoice+";";
		    rs=smt.executeQuery(sql);
		    double stored_amount=rs.getDouble("amount");
		    if(stored_amount-final_amount>=40)
		    {
		    	Paragraph delivery=new Paragraph("Door Delivery Charges:Rs 40/-",f2);
		    	delivery.setAlignment(Element.ALIGN_RIGHT);
		    	delivery.setSpacingAfter(10);
		    	delivery.setIndentationRight(50);
		    	doc.add(delivery);
		    }
		    f1.setColor(BaseColor.BLACK);
		    To_Words to_words_obj=new To_Words((int)stored_amount);
		    Paragraph words=new Paragraph("In Words: "+to_words_obj.output(),f4);
		    words.setAlignment(Element.ALIGN_CENTER);
		    doc.add(words);
		    Paragraph total_amount=new Paragraph("Total : "+Math.ceil(stored_amount),f1);
		    total_amount.setAlignment(Element.ALIGN_RIGHT);
		    total_amount.setIndentationRight(50f);
		    doc.add(total_amount);
			doc.close();
			Desktop dt=Desktop.getDesktop();
			dt.open(new File(invoice+".pdf"));
			smt.close();
			c.close();
			
			
			
		}catch(Exception e){
			doc.close();
			Error_Display error_display_obj=new Error_Display();
			error_display_obj.display("Cant Print - No Data Found");
			error_display_obj.setVisible(true);
		}
	}


}
