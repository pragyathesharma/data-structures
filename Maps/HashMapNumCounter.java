import java.util.*;
public class HashMapNumCounter
{
	public static void main( String[] args )
	{
			//	Key		Values
		HashMap<Integer,Integer> numMap=new HashMap<Integer,Integer>();
		for(int x=0;x<10;x++)
		{
			int num=(int)(Math.random()*6)+1;
			if(!numMap.containsKey(num))
				numMap.put(num,0);
			numMap.put(num,numMap.get(num)+1);

		}
		System.out.println(numMap);

	}
}