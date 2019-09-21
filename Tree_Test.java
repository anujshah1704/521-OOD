package tt;

class Tree_Test
{
	public static void main(String[] args) 
	{
		AbsTree tr = new Tree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
	
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
		
	
	}
}


class DupTree_Test 
{


	public static void main(String[] args) 
	{
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
	

		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);

	}
}


abstract class AbsTree 
{
	// instance variables
	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;
	// methods
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract void set_count(int v);
	
	public AbsTree(int n) // creates tree node
	{
		value = n;
		left = null;
		right = null;	
	}

	public void insert(int n) // inserts value in the node
	{	
		if (value == n)		// checks for same value
		{
			count_duplicates(); 		// counts duplicates for DupTree
			return;
		}
		else 						// BST property logic
		{	
			if (value < n)
				if (right == null) 
				{
					right = add_node(n);
					right.parent=this;	
				} 
				else
					right.insert(n);
			else 
				if (left == null) 
				{
					left = add_node(n);
					left.parent=this;
				} 
				else
					left.insert(n);
		}
	}

	public void delete(int n) 	// deletes value
	{  
		AbsTree t = find(n);

		if (t == null) 
		{
		 // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		}

		int c = t.get_count();	// DupTree mantains count so reduce count rather than deleting
		if (c > 1) 
		{
			t.set_count(c-1);
			System.out.println("Since the count of " +n+ " was " +c+ ", it has been reduced by 1 rather than deleting");
			return;
		}

		if (t.left == null && t.right == null)		// t is a leaf node (no subtrees)
		{
			if (t != this) 		// checks whether t is the root of the tree
				case1(t);
			else
				System.out.println("Unable to delete " + n + " since it is the root else tree will become empty!");
			return;
		}
		if (t.left == null || t.right == null)			// t has only 1 subtree 
		{
			if (t != this) 			// checks whether t is the root of the tree
			{
				case2(t);
				return;
			} 
			else 					// it is the root node 
			{
				if (t.right == null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		}
		case3R(t); 			// t has two subtrees
	}

	protected void case1(AbsTree t) 		 	// remove the leaf node(no subtrees)
	{
		if(t.value<t.parent.value)
			t.parent.left=null;
		else
			t.parent.right=null;
		
		t.parent=null;
	}

	protected void case2(AbsTree t) 	// internal node (non root node) with one subtree
	{
	 	if(t.left==null) 		// left subtree is null
	 	{
	 		if(t.parent.left==t)
	 		{
	 			t.parent.left=t.right;
	 			t.right.parent=t.parent;
	 		}
	 		else
	 		{
	 			t.parent.right=t.right;
	 			t.right.parent=t.parent;
	 		}
	 	}
	 	else				// right subtree is null?
	 	{
	 		if(t.parent.left==t)
	 		{
	 			t.parent.left=t.right;
	 			t.left.parent=t.parent;
	 		}
	 		else
	 		{
	 			t.parent.right=t.left;
	 			t.left.parent=t.parent;
	 		}
	 	}
	 	t.parent=null;			// make sure it is out from the tree 
	 	t.right=null;
	 	t.left=null;
	}

	protected void case3L(AbsTree t) 	// root node with no right subtree 
	{
		AbsTree follow= t.left;			// go left
		follow=follow.max();			// find the max
		int c= follow.get_count();		//logic to update count
		t.set_count(c);
		// 2 possible cases
		if (follow.parent!=t) 			// case1: max is the child of the root
		{	
			if(follow.left!=null)		
			{
				follow.parent.right = follow.left;
				follow.left.parent=follow.parent;
			}
			else
				follow.parent.right=follow.left;
		}
		else						// case 2: max is any other node
		{
			if(follow.left!=null)
			{
				follow.parent.left = follow.left; 
				follow.left.parent=follow.parent;	
			}
			else
				follow.parent.left=follow.left;
		}
		int val=t.value;				// replace value logic
		t.value=follow.value;
		follow.value=val;
		follow.parent=null;				// make sure it is out from the tree
		follow.left=null;
		follow.right=null;
	}

	protected void case3R(AbsTree t)		// any node with 2 subtrees
	 {
		AbsTree follow = t.right;		// go right
		follow=follow.min();			// find max
		int c= follow.get_count();		// update count logic
		t.set_count(c);
		// 2 cases:
		if (follow.parent==t) 		// case 1: max is the child of the root
		{	
			if(follow.right!=null)
			{
				follow.parent.right = follow.right;
				follow.right.parent=follow.parent;
			}
			else
				follow.parent.right=follow.right;
		}
		else					// case 2: max is any other node
		{
			if(follow.right!=null)
			{
				follow.parent.left = follow.right; 
				follow.right.parent=follow.parent;	
			}
			else
				follow.parent.left=follow.right;
		}
		int val=t.value;				// replace value logic
		t.value=follow.value;
		follow.value=val;
		follow.parent=null;				// make sure it is out from the tree
		follow.left=null;
		follow.right=null;
	}


	public AbsTree find(int n) 		// find any value in the BST 
	{
		AbsTree follow = this;		
		while(follow!=null)
		{
			if(follow.value==n)
			{
				return follow;
			}
			else
			{
				if(n<follow.value)
					follow=follow.left;
				else
					follow=follow.right;
			}
		}
		return follow;				//returns null
	}
	
	public AbsTree min() 			// finds minimum value in tree
	{
		AbsTree follow=this;
		while(follow.left!=null)
			follow=follow.left;
		return follow;
	}
	
	public AbsTree max() 			// finds maximum value in tree
	{
		AbsTree follow= this;
		while(follow.right!=null)
			follow=follow.right;
		return follow;
	}
}


class Tree extends AbsTree 
{
	public Tree(int n)		// creates new tree
	{
		super(n);
	}

	protected AbsTree add_node(int n) 
	{
		return new Tree(n);			
	}

	protected void count_duplicates() 
	{
		// BST doesn't keep track of duplcates
	}

	
	protected int get_count() 
	{
		return 1;	// for all nodes count is 1 in BST; helps in combining delete for both trees
	}

	protected void set_count(int v)
	 {
		// can return 1 but the function will never be accessed for BST
	 }
}

class DupTree extends AbsTree 
{
	protected int count;			// needed to keep track of count in DupTree
	
	public DupTree(int n) {
		super(n);
		count = 1;				// initialize to 1 (DupTree keeps count of duplicates)
	};

	protected AbsTree add_node(int n) 
	{
		return new DupTree(n);
	}

	protected void count_duplicates() 
	{
		count++;			// DupTree keeps count of duplicates
	}

	protected int get_count() 
	{
		return count;			
	}

	protected void set_count(int v) 
	{
		count=v;				// count reduced in function call
	}	
}
