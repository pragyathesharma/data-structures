import java.util.*;
public class ListNodeTest
{
	public ListNodeTest()
	{
		ListNode a=new ListNode(new String("Athos"));
		ListNode b=new ListNode(new String("Porthos"));
		ListNode c=new ListNode(new String("Aramis"));
		a.setNext(b);
		b.setNext(c);
		output(a);

		a.setNext(c);
		output(a);

	}
	public void output(ListNode a)
	{
		ListNode musketeers=a;
		for(ListNode x=musketeers;x!=null;x=x.getNext())
			System.out.println(x.getValue());
		System.out.println("===================================");
	}
	public static void main(String args[])
	{
		ListNodeTest app=new ListNodeTest();
	}

}