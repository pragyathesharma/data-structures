public class TreeSetRunner
{
	public TreeSetRunner()
	{
		TreeSet<Character> set = new TreeSet<>();
		TreeSet<Character> preSet = new TreeSet<>();
		TreeSet<Character> inSet = new TreeSet<>();
		TreeSet<Character> postSet = new TreeSet<>();
		String d = "-------------------------------------------------";
		int average = 0;

		for(int i = 0; i < 30; i++)
		{
			char c = (char) ((int)(Math.random()*25)+97);
			if(set.add(c))
				average += c;
		}

		average /= set.size();
		System.out.println("Set:\n"+set+"\nAverage: "+average + "\nAverage character: "+ ((char)(average))+"\nSize: "+set.size()+"\n"+d);

		String preOrder = set.preOrderToString();
		String inOrder = set.inOrderToString();
		String postOrder = set.postOrderToString();

		for(int i = 1; i < inOrder.length()-1; i+=3)
		{
			preSet.add(preOrder.charAt(i));
			inSet.add(inOrder.charAt(i));
			postSet.add(postOrder.charAt(i));
		}

		System.out.println("Original Set Traversals:\n\npreOrder:\n"+preSet+"\n\ninOrder:\n"+inSet+"\n\npostOrder:\n"+postSet+"\n"+d);

		System.out.println("preOrder Set Traversals:\n\npreOrder:\n"+preSet.preOrderToString()+"\n\ninOrder:\n"+preSet.inOrderToString()+"\n\npostOrder:\n"+preSet.postOrderToString()+"\n"+d);

		System.out.println("inOrder Set Traversals:\n\npreOrder:\n"+inSet.preOrderToString()+"\n\ninOrder:\n"+inSet.inOrderToString()+"\n\npostOrder:\n"+inSet.postOrderToString()+"\n"+d);

		System.out.println("postOrder Set Traversals:\n\npreOrder:\n"+postSet.preOrderToString()+"\n\ninOrder:\n"+postSet.inOrderToString()+"\n\npostOrder:\n"+postSet.postOrderToString()+"\n"+d);

	}

	public static void main(String[] args)
	{
		TreeSetRunner app = new TreeSetRunner();
	}
}