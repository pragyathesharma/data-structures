public class ListNode<Object>
	{
		private Object value;
		private ListNode previous,next;
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
		public ListNode getNext()
		{
			return next;
		}
		public ListNode getPrevious()
		{
			return previous;
		}
		public void setValue(Object theNewValue)
		{
			value = theNewValue;
		}
		public void setNext(ListNode theNewNext)
		{
			next = theNewNext;
		}
		public void setPrevious(ListNode theNewPrevious)
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
