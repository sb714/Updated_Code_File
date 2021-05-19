package tp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

 public class ppp 
{

	 public static void main(String[] args) {
		 	
			TreeSet<Employee> t = new TreeSet<>(new show1());
			Employee e1 = new Employee();
			Employee e2 = new Employee();
			Employee e3 = new Employee();
			Employee e4 = new Employee();
			e1.setId(1);
			e1.setName("Sagar");
			
			e2.setId(2);
			e2.setName("Jyostna");
			
			e3.setId(3);
			e3.setName("abc");
			
			e4.setId(4);
			e4.setName("XYz");
			
			t.add(e1);
			t.add(e2);
			t.add(e3);
			t.add(e4);
			/*t.add("jyostna");
			t.add("abc");
			t.add("raj");
			t.add("sagar");
			t.add("bvn");
			t.add("xy");*/
			//System.out.println(t);
			List al = new ArrayList<>(t);
			System.out.println(al);
			
		}
	
	
} 

 class show1 implements Comparator
{
	 @Override
		public int compare(Object o1, Object o2) {
		 
			Employee emp1 = (Employee)o1;
			Employee emp2 = (Employee)o2;
			String s1 = (String) emp1.getName(); 
			String s2 = (String)emp2.getName();
			
			return s1.compareTo(s2);
			
		}

}