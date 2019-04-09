import java.util.*;
public class Test
{
	public Test()
	{
		String str = "34	Giannis Antetokounmpo	PF	23	6-11	222	Euro State 	$22,471,910	Milwaukee Bucks";
		String[] a = str.split("	");

		for(String s : a)
			System.out.println(s);
	}

	public static void main (String[] args)
	{
		Test app = new Test();
	}
}