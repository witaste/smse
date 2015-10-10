package smse;

import org.junit.Test;

public class Digui {
	
	@Test
	public void testJian(){
		String s = "0123456789";
		jian(s);
		String a = null;
		String b = a==null?"null":a;
		System.out.println(b);
	}
	
	private void jian(String s){
		if(s.length()==0) return;
		System.out.println(s);
		jian(s.substring(1));
	}
	
	
}
