package polynomial;
import java.util.Scanner;



public class Polynomial 
 {
	static String expression = "";
	static String command = "";
	static String expre[];
	static void Simplify(String[] value)
	{
		String sim_expression = "";
		String[] temp_exp = null;
		String[] temp_expre = null;
		String new_exp = "";
		String var="";
		int coefficient=1;
		int temp;
		int constant = 0;
		for(int i=0;i<expre.length;i++){
			if(expre[i].contains(value[0])){
				expre[i]=expre[i].replaceAll(value[0], value[1]);
				temp_exp=expre[i].split("\\*");
				for(int j=0;j<temp_exp.length;j++){
					try{
						temp = Integer.valueOf(temp_exp[j]);
						coefficient *= temp;
					}catch (Exception e){
						var = var + "*" + temp_exp[j];
					}
				}
				new_exp+=Integer.toString(coefficient)+var;
				sim_expression+=new_exp + "+";
			}
			else{
				sim_expression+=expre[i] + "+";
			}
			var="";
			coefficient=1;
			new_exp = "";
		}
		sim_expression = sim_expression.replace("-1*", "-");
		sim_expression = sim_expression.replace("1*", "");
		sim_expression = sim_expression.substring(0, sim_expression.length()-1);
		temp_expre = sim_expression.split("\\+");
		sim_expression = "";
		for(int i=0;i<temp_expre.length;i++){
			try{
				temp = Integer.valueOf(temp_expre[i]);
				constant += temp;
			}catch (Exception e){
				sim_expression += "+" + temp_expre[i];
			}
		}
		if(constant!=0)
			sim_expression = Integer.toString(constant) + sim_expression;
		else
			sim_expression = sim_expression.substring(1);
		while(sim_expression.contains("+-")){
			sim_expression = sim_expression.replace("+-", "-");
		}
		while(sim_expression.contains("++")){
			sim_expression = sim_expression.replace("++", "+");
		}
		if(sim_expression.endsWith("+") || sim_expression.endsWith("-"))
			sim_expression = sim_expression.substring(0, sim_expression.length()-1);
		System.out.println(sim_expression);
		expression();
	}
	static void Derivative(String value)
	{
		String[] temp=null;
		String[] str_temp = null;
		String tmp=null;
		String val_str = "";
		String temp0="**",temp1="*";
		String der_str = "";
		int constant = 0;
		int count=0;
		int coefficient = 1;
		for(int i=0;i<expre.length;i++){
			count = 0;
			if(expre[i].contains(value)){
				temp = expre[i].split("\\*");
				for(int j=0;j<temp.length;j++){
					if(temp[j].equalsIgnoreCase(value)){
						count++;
					}
				}
				tmp = Integer.toString(count) + "*" + expre[i].replaceFirst(value, "");
				try{
					tmp = tmp.replace(temp0, temp1);
				}catch (Exception e){
				}
				if(tmp.endsWith("*")){
					tmp=tmp.substring(0, tmp.length()-1);
				}
				der_str +=  tmp + "+";
			}
		}
		der_str = der_str.substring(0, der_str.length()-1);
		der_str = der_str.replace("-1*", "-");
		der_str = der_str.replace("1*", "");
		temp = der_str.split("\\+");
		der_str = "";
		for(int i=0;i<temp.length;i++){
			str_temp = temp[i].split("\\*");
			for(int j=0;j<str_temp.length;j++){
				try{
					count = Integer.valueOf(str_temp[j]);
					coefficient *= count;
				}catch (Exception e){
					val_str += "*" + str_temp[j];
				}
			}
			der_str += Integer.valueOf(coefficient)+val_str+"+";
			val_str = "";
			coefficient = 1;
		}
		der_str = der_str.substring(0, der_str.length()-1);
		temp = der_str.split("\\+");
		der_str = "";
		for(int i=0;i<temp.length;i++){
			try{
				count = Integer.valueOf(temp[i]);
				constant += count;
			}catch (Exception e){
				der_str += "+" + temp[i];
			}
		}
		if(constant!=0)
			der_str = Integer.toString(constant) + der_str;
		else
			der_str = der_str.substring(1);
		while(der_str.contains("+-")){
			der_str = der_str.replace("+-", "-");
		}
		while(der_str.contains("++")){
			der_str = der_str.replace("++", "+");
		}
		System.out.println(der_str);
	}
	static void expression()
	{
		expre=expression.split("\\+");
	}
	static boolean judge(String expression)
	{
		boolean flag= true;
		String ch=null;
		String num = "0123456789";
		String charac = "+*-abcdefghijklmnopqrstuvwxyz 	";
		for(int i=0;i<expression.length() && flag;i++){
			ch = expression.substring(i, i+1);
			ch = ch.toLowerCase();
			if(!num.contains(ch) && !charac.contains(ch))
				flag = false;
		}
		return flag;
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		System.out.print('>');
		Scanner sc = new Scanner(System.in);
		String str_in = null;
		str_in=sc.nextLine();
		String temp_com=null;
		String value[]=null;
		String valuename;
		while(!str_in.equals("!exit")){
			long tm =System.currentTimeMillis();
			System.out.println("开始时间："+tm);
			if(str_in.startsWith("!")){
				command=str_in;
				if(!expression.isEmpty()){
					if(command.startsWith("!simplify ")){
						temp_com=command.substring(10);
						value=temp_com.split("\\=");
						if(!expression.contains(value[0])){
							System.out.println("Error, no variable");
						}
						else{
							Simplify(value);
						}
					}
					else if(command.startsWith("!d/d")){
						valuename=command.substring(4);
						if(!expression.contains(valuename)){
							System.out.println("Error, no variable");
						}
						else{
							Derivative(valuename);
						}
					}
					else{
						System.out.println("no command!");
					}
				}
				else{
					System.out.println("no expression!");
				}
			}
			else{
				expression=str_in;
				if(judge(expression)){
					expression = expression.replace(" ","");
					expression = expression.replace("	", "");
					System.out.println(expression);
					if(expression.startsWith("+"))
					expression=expression.substring(1);
					expression = expression.replace("-", "+-1*");
					expression();
				}
				else{
					System.out.println("illegal char");
				}
			}
			System.out.println("结束时间："+System.currentTimeMillis());
			System.out.println("执行时间："+(System.currentTimeMillis()-tm));
			System.out.print('>');
			str_in=sc.nextLine();
		}
		sc.close();
	}
}
