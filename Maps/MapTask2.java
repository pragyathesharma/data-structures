import java.util.*;
import java.io.*;
public class MapTask2
{
	public MapTask2()
	{
		File name = new File("BowlingData.txt");
		TreeMap<String,PriorityQueue<Bowler>> bowlers = new TreeMap<String,PriorityQueue<Bowler>>();
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text,output="";
			while( (text=input.readLine())!= null)
			{
				String[] temp = text.split(" ");

				if(!bowlers.containsKey(temp[2]))
					bowlers.put(temp[2], new PriorityQueue<Bowler>());
				bowlers.get(temp[2]).add(new Bowler(temp[0], temp[1], temp[2]));

			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
		Iterator it=bowlers.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			String output = key+"=[";
			while(bowlers.get(key).peek()!=null)
			{
				output+=(bowlers.get(key).poll()+", ");
			}
			output = output.substring(0, output.length()-2) + "]";
	System.out.println(output);


		}
	}
	public static void main(String[] args)
	{
		MapTask2 app = new MapTask2();
	}
	public class Bowler implements Comparable<Bowler>
	{
		private String first, last, score;
		public Bowler(String first, String last, String score)
		{
			this.first = first;
			this.last = last;
			this.score = score;
		}
		public String getFirst()
		{
			return first;
		}
		public String getLast()
		{
			return last;
		}
		public String getScore()
		{
			return score;
		}
		public int compareTo(Bowler b)
		{
			if(getLast().compareTo(b.getLast())!=0)
				return getLast().compareTo(b.getLast());
			if(getFirst().compareTo(b.getFirst())!=0)
				return getLast().compareTo(b.getFirst());
			return 0;
		}
		public String toString()
		{
			return first+" "+last;
		}

	}
}