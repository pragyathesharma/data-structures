import java.util.*;
import java.io.*;
public class MapTask3
{
	public MapTask3()
	{
		TreeMap<String, TreeSet<BasketballPlayer>> map = new TreeMap<String, TreeSet<BasketballPlayer>>();
		File name = new File("BasketballPlayerList.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = input.readLine();
			while((text=input.readLine())!= null)
			{
				String[] temp = text.split("	");
				String key = temp[8];
				//0Uniform # 1Name	2Position	3Age	4Height	5Weight	6University	7Salary	8Team
				if(!map.containsKey(key))
					map.put(key, new TreeSet<BasketballPlayer>());
				map.get(key).add(new BasketballPlayer(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8]));

			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
		Iterator it = map.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			String output = key+": \n";
			for(BasketballPlayer b : map.get(key))
				output+= b;
			System.out.println(output);
		}
	}
	public static void main(String[] args)
	{
		MapTask3 app = new MapTask3();
	}

	public class BasketballPlayer implements Comparable<BasketballPlayer>
	{
		private String uniform, name, pos, age, height, weight, university, salary, team;
		public BasketballPlayer(String uniform, String name, String pos, String age, String height, String weight, String university, String salary, String team)
		{
			this.uniform = uniform;
			this.name = name;
			this.pos = pos;
			this.age = age;
			this.height = height;
			this.weight = weight;
			this.university = university;
			this.salary = salary;
			this.team = team;
		}
		public String toString()
		{
			return String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", uniform, name, "("+pos+")", age, height, weight, university, salary);
		}
		public int compareTo(BasketballPlayer b)
		{
			if(pos.compareTo(b.getPos())!=0)
				return pos.compareTo(b.getPos());
			if(height.compareTo(b.getHeight())!=0)
				return height.compareTo(b.getHeight());
			if(weight.compareTo(b.getWeight())!=0)
				return weight.compareTo(b.getWeight());
			if(uniform.compareTo(b.getUniform())!=0)
				return uniform.compareTo(b.getUniform());
			if(salary.compareTo(b.getSalary())!=0)
				return salary.compareTo(b.getSalary());
			if(university.compareTo(b.getUniversity())!=0)
				return university.compareTo(b.getUniversity());
			if(age.compareTo(b.getAge())!=0)
				return age.compareTo(b.getAge());
			return 0;
		}

		public String getUniform(){return uniform;}
		public String getName(){return name;}
		public String getPos(){return pos;}
		public String getAge(){return age;}
		public String getHeight(){return height;}
		public String getWeight(){return weight;}
		public String getUniversity(){return university;}
		public String getSalary(){return salary;}
		public String getTeam(){return team;}
	}

}