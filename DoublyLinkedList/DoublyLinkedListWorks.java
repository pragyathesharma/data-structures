import java.lang.*;
public class DoublyLinkedList<Object>
{
	private ListNode<Object> root, end;
	private int size;

	public DoublyLinkedList()
	{
		root = null;
		end = null;
		size = 0;
	}



	public void add(Object obj)
	{
		ListNode<Object> newNode = new ListNode<Object>(obj);
		if(root==null)
		{
			root = newNode;
			end = root;
		}
		else
		{
			end.setNext(newNode);
			newNode.setPrevious(end);
			end = newNode;
		}
		size++;
	}

	public void add(int index,Object obj)
	{
		ListNode<Object> newNode = new ListNode<Object>(obj);
		boolean inserted = false;
		if(index>=0 || index<size)
		{
			if(root==null)
			{
				root = newNode;
				end = root;
				inserted = true;
			}
			else
			{
				int checkIndex = 0;
				ListNode<Object> current = root;
				if(index==0)  // (newNode) (root) (1)
				{
					newNode.setNext(current);
					current.setPrevious(newNode);
					root = newNode;
					inserted = true;
				}
				else if(index==size)
				{
					end.setNext(newNode);
					newNode.setPrevious(end);
					end = newNode;
					inserted = true;
				}
				else
				{
					while(!inserted && current!=null)//cycles through the list starting with root.
					{
						if(checkIndex==index ) //if current index = the one you receive
						{
							ListNode<Object> currentPrevious = current.getPrevious();
							currentPrevious.setNext(newNode);
							newNode.setPrevious(currentPrevious);
							newNode.setNext(current);
							current.setPrevious(newNode);
							inserted = true;
						}
						checkIndex++;
						current=current.getNext();
					}
				}
			}

				size++;
		}
		else throw new ArrayIndexOutOfBoundsException("Index "+index+" out of bounds.");
	}

	public ListNode<Object> getRoot()
	{
		return root;
	}

	public ListNode<Object> getEnd()
	{
		return end;
	}

	public int size()
	{
		return size;
	}

	public void getNext()
	{
		root = root.getNext();
	}

	public void remove(int index)
	{
			if(index>=0 && index<size)
			{
				if(index==0)
				{
					root = root.getNext();
					root.setPrevious(null);
				}
				else
				{
					ListNode<Object> current = root;
					ListNode<Object> currentPrevious = null;
					for(int i = 0; i < index; i++)
					{
						currentPrevious = current;
						if(current.hasNext())
							current = current.getNext();
						else current.setNext(null);
					}
					if(current.hasNext())
					{
						currentPrevious.setNext(current.getNext());
						if(currentPrevious.hasPrevious())
							current.getNext().setPrevious(currentPrevious);
						else current.getNext().setPrevious(null);
					}
					else
					{
						currentPrevious.setNext(null);
						end = end.getPrevious();
					}
				}
				size--;
			}

		 else throw new ArrayIndexOutOfBoundsException("Index out of bounds.");

	}

public boolean isEmpty()
		{
			return root==null;
		}
	public void clear()
	{
		if(size>0)
		{
			int index = 0;
			ListNode<Object> x = root;
			while(x!=null && index<size)
			{
				remove(index);
				index++;
				x = x.getNext();
			}
		}
		size = 0;
		root = null;
		end = null;
	}

	public boolean contains(Object obj)
	{
		boolean isPresent = false;
		ListNode<Object> x = root;
		while(!isPresent && x!=null)
		{
			isPresent = x.getValue().equals(obj);
			x=x.getNext();
		}
		return isPresent;
	}

	public String toString()
	{
		String formatted = "[";
		if(size>0)
		{
			formatted += root.getValue();
			for(ListNode<Object> x = root.getNext(); x!=null; x=x.getNext())
				formatted+=", "+x.getValue();
		}
		return formatted+="]";
	}

	public String toReversedString()
	{
		String formatted = "[";
		if(size>0)
		{
			formatted += end.getValue();
			for(ListNode<Object> x = end.getPrevious(); x!=null; x=x.getPrevious())
				formatted+=", "+x.getValue();
		}
		return formatted+="]";
	}

	public class ListNode<Object>
	{
		private Object value;
		private ListNode<Object> previous,next;
		public ListNode(Object initValue)
		{
			value = initValue;
			next = null;
			previous = null;
		}
		public Object getValue()
		{
			return value;
		}
		public ListNode<Object> getNext()
		{
			return next;
		}
		public ListNode<Object> getPrevious()
		{
			return previous;
		}
		public void setValue(Object theNewValue)
		{
			value = theNewValue;
		}
		public void setNext(ListNode<Object> theNewNext)
		{
			next = theNewNext;
		}
		public void setPrevious(ListNode<Object> theNewPrevious)
		{
			previous = theNewPrevious;
		}
		public boolean hasNext()
		{
			return getNext()!=null;

		}
		public boolean hasPrevious()
		{
			return getPrevious()!=null;

		}


	}

}

