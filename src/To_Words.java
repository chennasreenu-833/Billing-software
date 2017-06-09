
public class To_Words {
	private String word="";
	public To_Words(int dividend){
		int val,sub_val1,sub_val2;
		if(dividend==0){
			word="Zero ";
		}
		if(dividend>9999999)
		{
			val=dividend/10000000;
		
			if(val>19){
				sub_val1=val/10;
		     	sub_val2=val%10;
				word+=get_string1(sub_val1);
				word+=get_string(sub_val2);
			}
			else{
				word+=get_string(val);
			}
			word+="Crore ";
			dividend=dividend%10000000;
		}
		if(dividend>99999)
		{
			val=dividend/100000;
		
			if(val>19){
				sub_val1=val/10;
		     	sub_val2=val%10;
				word+=get_string1(sub_val1);
				word+=get_string(sub_val2);
			}
			else{
				word+=get_string(val);
			}
			word+="Lakh ";
			dividend=dividend%100000;
		}
		if(dividend>999){
			val=dividend/1000;
			if(val>19){
				sub_val1=val/10;
		     	sub_val2=val%10;
				word+=get_string1(sub_val1);
				word+=get_string(sub_val2);
			}
			else{
				word+=get_string(val);
			}
		
			word+="Thousand ";
			dividend=dividend%1000;
		}
		if(dividend>99){
			val=dividend/100;
			if(val>0){
				word+=get_string(val)+" ";
			}
			word+="Hundred ";
			dividend=dividend%100;
		}
		if(dividend>0){
			val=dividend;
				if(val>19){
				sub_val1=val/10;
		     	sub_val2=val%10;
				word+=get_string1(sub_val1);
				word+=get_string(sub_val2);
			}
			else{
				word+=get_string(val);
			}
		}
		word+="Ruppees only";
	}
	public String get_string(int val){
		switch(val){
			case 1:return "One ";
			case 2:return "Two ";
			case 3:return "Three ";
			case 4:return "Four ";
			case 5:return "Five ";
			case 6:return "Six ";
			case 7:return "Seven ";
			case 8:return "Eight ";
			case 9:return "Nine ";
			case 10:return "Ten ";
			case 11:return "Eleven ";
			case 12:return "Twelve ";
			case 13:return "Thirteen ";
			case 14:return "Fourteen ";
			case 15:return "Fifteen ";
			case 16:return "Sixteen ";
			case 17:return "Seventeen ";
			case 18:return "Eighteen ";
			case 19:return "Nineteen ";
			default:return "";
			
		}
	}
	public String get_string1(int val){
		switch(val){
		    case 2:return "Twenty ";
			case 3:return "Thirty ";
			case 4:return "Forty ";
			case 5:return "Fifty ";
			case 6:return "Sixty ";
			case 7:return "Seventy ";
			case 8:return "Eighty ";
			case 9:return "Ninty ";
			default:return "";
		}
	}
	public String output(){
		return word;
	}


}
