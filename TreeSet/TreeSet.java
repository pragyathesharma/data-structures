public class TreeSet<Object>
{
	TreeNode<Object> root;
	int size;
	String str = "", toStringStr ="";

	public TreeSet()
	{
		root = null;
		size = 0;
	}

	public boolean add(char newObject)
	{
		boolean added = false;
		if(size == 0)
		{
			root = new TreeNode<Object>(newObject);
			size++;
			toStringStr = newObject+", ";
		}
		else
		{
			TreeNode<Object> current = root;
			added = false;
			while(!added && current.getValue()!=newObject)
			{
				if(newObject < current.getValue() )
				{
					if(current.getLeft() == null)
					{
						current.setLeft(new TreeNode<Object>(newObject));
						size++;
						toStringStr+=newObject+", ";
						added = true;
					}
					else current = current.getLeft();
				}
				else if(newObject > current.getValue())
				{
					if(current.getRight() == null)
					{
						current.setRight(new TreeNode<Object>(newObject));
						size++;
						toStringStr+=newObject+", ";
						added = true;
					}
					else current = current.getRight();
				}
			}

		}
		return added;
	}

	public int size(){return size;}
	public TreeNode<Object> getRoot(){return root;};


	public void preOrder(TreeNode<Object> n)
	{
		if(n!=null)
		{
			str+=n.getValue()+", ";
			preOrder(n.getLeft());
			preOrder(n.getRight());
		}

	}

	public void inOrder(TreeNode<Object> n)
	{
		if(n!=null)
		{
			inOrder(n.getLeft());
			str+=n.getValue()+", ";
			inOrder(n.getRight());
		}
	}

	public void postOrder(TreeNode<Object> n)
	{
		if(n!=null)
		{
			postOrder(n.getLeft());
			postOrder(n.getRight());
			str+=n.getValue()+", ";
		}
	}

	public String preOrderToString()
	{
		str="";
		preOrder(root);
		return format(str);
	}
	public String inOrderToString()
	{
		str = "";
		inOrder(root);
		return format(str);
	}
	public String postOrderToString()
	{
		str = "";
		postOrder(root);
		return format(str);
	}

	public String toString()
	{
		if(size == 0)
			return "[]";
		return format(toStringStr);
	}

	public String format(String s){return "["+s.substring(0,s.length()-2)+"]";}

	public class TreeNode<Object>
	{
		TreeNode<Object> left, right;
		char value;

		public TreeNode(char newValue)
		{
			value = newValue;
		}

		public TreeNode<Object> getRight(){return right;}
		public TreeNode<Object> getLeft(){return left;}


		public void setRight(TreeNode<Object> newRight)
		{
			right = newRight;
		}

		public void setLeft(TreeNode<Object> newLeft)
		{
			left = newLeft;
		}

		public char getValue()
		{
			return value;
		}

		public String toString()
		{
			return value+"";
		}

	}

}
