import java.util.*;
import java.io.*;
public class MapTask1
{
	public MapTask1()
	{
		File name = new File("map1.txt");
		HashMap<String,Integer> numMap=new HashMap<String,Integer>();

		try
		{

			BufferedReader input = new BufferedReader(new FileReader(name));
			String num,output="";
			while( (num=input.readLine())!= null)
			{
				if(!numMap.containsKey(num))
					numMap.put(num,0);
				numMap.put(num,numMap.get(num)+1);
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}

		System.out.println(numMap);

	}
	public static void main(String[] args)
	{
		MapTask1 app = new MapTask1();
	}
}