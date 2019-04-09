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