public class DLLRunner
{
	public DLLRunner()
	{

		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		DoublyLinkedList<Integer> intactList = new DoublyLinkedList<Integer>();
		int sum=0, evenSum=0, oddSum=0, evenIndices=0, oddIndices=0;
		for(int i = 0; i < 30; i++)
		{
			int rand = (int)(Math.random()*1000)+1;
			list.add(rand);
			intactList.add(rand);
			sum+=rand;
			if(i%2==0)
			{
				evenSum+=rand;
				evenIndices++;
			}
			else
			{
				oddSum+=rand;
				oddIndices++;
			}

		}
		System.out.println("List: "+list+"\n");
		System.out.println("List reversed: "+list.toReversedString()+"\n");
		System.out.println("Size: "+list.size()+"\n");
		System.out.println("Average: "+(sum/list.size())+"\n");
		System.out.println("Even Index Average: "+(evenSum/evenIndices)+"\n");
		System.out.println("Odd Index Average: "+(oddSum/oddIndices)+"\n");


		DoublyLinkedList<Integer> odds = new DoublyLinkedList<Integer>();
		while(!list.isEmpty())
		{
			int val = list.getRoot().getValue();
			if(val%2!=0)
				odds.add(val);
			list.getNext();
		}
		list = intactList;
		System.out.println("Odds List: "+odds+"\n");

		while(!odds.isEmpty())
		{
			list.add(odds.getRoot().getValue());
			odds.getNext();
		}
		System.out.println("List w/ More Odds: "+list+"\n");
		intactList = new DoublyLinkedList<Integer>();
		int n  = 0;
		while(!list.isEmpty() && n < list.size())
		{
			int val = list.getRoot().getValue();
			if(val%4!=0)
			{
				intactList.add(val);
			}
			list.getNext();
			n++;
		}
		list = intactList;
		list.add(3, 45454);
		System.out.println("List -4s + 45454: "+list+"\n");
/*
		int key, j;
	   for (int i = 1; i < list.size(); i++)
	   {
		   key = list.get(i);
		   j = i-1;
		   while (j >= 0 && list.get(j) > key)
		   {
			   list.set(j+1, list.get(j));
			   j-=1;
		   }
		   list.set(j+1, key);
	 }
	 */

	boolean swapped;
	do
	{
		swapped = false;
		DoublyLinkedList<Integer> temp = new DoublyLinkedList<Integer>();
		while(!list.isEmpty())
		{
			if(list.getRoot().hasNext() && list.getRoot().getValue()>list.getRoot().getNext().getValue())
			{
				temp.add(list.getRoot().getNext().getValue());
				temp.add(list.getRoot().getValue());
				swapped = true;
			}
			else temp.add(list.getRoot().getValue());
			list.getNext();
		}
		list = temp;
	}while(swapped);

System.out.println("Sorted List: "+list+"\n");

//x x x
	if(list.size()%2!=0)
	{
		int med = list.size()/2;
		System.out.println("Median: "+list.get(med));
		if(med-1 >= 0)
			System.out.println("Before Median: "+list.get(med-1));
		if(med+1 < list.size())
			System.out.println("After Median: "+list.get(med+1));
	}
	else
	{
		int med2 = list.size()/2;
		int med1 = med2-1;
		System.out.println("Median: "+(list.get(med1)+list.get(med2))/2);
		System.out.println("Before Median: "+list.get(med1)+"\nAfter Median: "+list.get(med2));
	}
	// x x x x

	}

	public static void main(String[] args)
	{
		DLLRunner app=new DLLRunner();
	}

}
