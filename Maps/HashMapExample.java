import java.util.*;
public class HashMapExample
{
	public static void main( String[] args )
	{
		HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();

		map.put(2, new ArrayList<Integer>() );
		map.put(3, new ArrayList<Integer>() );

		map.get(2).add(25);
		map.get(2).add(20);
		map.get(2).add(30);
		map.get(3).add(12);
		map.get(3).add(15);

		System.out.println( map );
		System.out.println();

		Iterator it=map.entrySet().iterator();
		System.out.println( "Iterate through the HashMap" );
		while(it.hasNext())
			System.out.println( it.next());

		System.out.println();

		Iterator it2=map.keySet().iterator();
		System.out.println( "Iterate through the keys of the HashMap" );
		while(it2.hasNext())
			System.out.println( it2.next());

		System.out.println();

		Iterator it3=map.keySet().iterator();
		System.out.println( "Iterate through the values of the HashMap" );
		int sum=0;
		while(it3.hasNext())
		{
			for(Integer num:map.get( (int)(it3.next()) ))
			{
				System.out.println(num);
				sum+=num;
			}
		}
		System.out.println("Sum: "+sum);
	}
}