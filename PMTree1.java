import java.util.ArrayList;


public class PMTree1 {
	
	private class Node {
		Node parent;
		Node left, right;
		int days;
		String name;
		int no_nodes = 0;

		Node(Node parent, int days, String name) {
			this.parent = parent;
			this.days = days;
			this.left = this.right = null;
			this.name = name;
		}

		/*
		 * When deleting, we want to know how many children a node has.
		 */
		int numChildren() {
			return (left == null ? 0 : 1) + (right == null ? 0 : 1);
		}
		
		
	}
	
	
	

	private Node root = null;
	/*
	 * variables to be used by the
	 * helper function that helps in
	 * getting the nth prime minister
	 */
	 int count =0; 
	 String nthPmName = "";
	
	/**
	 * Returns name of prime minister who
	 * served exactly the given number of days
	 * else it returns null
	 * @param days
	 * @return
	 */
	public String getName (int days){
		return getNode(days).name;
	}
	
	

	private Node getNode(int days) {
		Node cur = root;
		while (cur != null) {
			if (days == cur.days)
				return cur;
			else if (days < cur.days)
				cur = cur.left;
			else
				cur = cur.right;
		}
		
		return null;
	}

	
	//to do, check if PM with those days exist
	//update name if exists
	public void insert (int days, String name){
	
		Node cur = root;
		if (root == null) {
			root = new Node(null, days,name);
			return;
		}
		
		while (true) {
			if (days == cur.days) {
				//if PM with same number exists, update name
				cur.name = name;
				return;
			}
			else if (days < cur.days) {
				if (cur.left == null) {
					cur.left = new Node(cur, days,name);
					//increment nodes for current node
					cur.no_nodes += 1;
					return;
				} else
					cur = cur.left;
			} else {
				if (cur.right == null) {
					cur.right = new Node(cur, days,name);
					//increment nodes for parent node
					cur.right.no_nodes += 1;
					return;
				} else
					cur = cur.right;
			}
		}
		
		
	}

	/*
	 * Delete a node from the tree. If the node has one child or none, delete it as
	 * if it, its parent (if it exists) and its child (if it has one) are a doubly
	 * linked list. Otherwise, replace the value in the node to be deleted with the
	 * minimum element of its right subtree and delete the node that originally
	 * contained that element. Note that, by construction, that node has at most one
	 * child (it has no left child because a left child would contain a smaller
	 * value).
	 */
	public void delete(int days) {
		Node node = getNode(days);
		
		if (node == null)
			return;
		if (node.numChildren() < 2) {
			simpleDelete(node);
		} else {
			Node min = getMinNode(node.right);
			simpleDelete(min);
			node.days = min.days;
		}
		
		if(node.parent != null) {
			//decrease child nodes
			node.parent.no_nodes -=1;
		}
		
	}

	/*
	 * Delete a node that has one child or none. We treat this node, its parent (if
	 * it exists) and its child (if it has one) as a doubly linked list and delete
	 * accordingly. The code is fiddly because of the special cases. We might be
	 * deleting the root (parent == null) and/or we might be deleting a node with no
	 * child, with just a left child or just a right child.
	 */
	private void simpleDelete(Node node) {
		Node child = node.left != null ? node.left : node.right;
		if (node == root) {
			root = child;
			if (root != null)
				root.parent = null;
		} else {
			if (node == node.parent.left)
				node.parent.left = child;
			else
				node.parent.right = child;
			if (child != null)
				child.parent = node.parent;
		}
		
		
	}

	/*
	 * Return the node containing the smallest value in the subtree rooted at the
	 * given node. This is found by following the left child reference until we get
	 * to a node that has no left child.
	 */
	private Node getMinNode(Node node) {
		while (node.left != null)
			node = node.left;
		return node;
	}
	
	public String nthShortest (int n) {
		  count =0; 
		  nthPmName = "";
		  
		 //check if value of n is valid
		  if(n > this.getLength()) {
			  //value of n is not valid since it is larger than the length of the tree
			  return null;
		  }else {
			 inorderTraversal( root,  n);
			return nthPmName;
		  }
		  
		
	}
	
	
	
	
	/**
	 * A helper function for finding the nth
	 * shortest serving prime minister.
	 * @param node
	 * @param n
	 */
	public void inorderTraversal( Node node, int n)
	{ 
		
	    if (node == null)
	        return;
	    
	    if (count <= n) {
	        /* first count on the left */
	    	inorderTraversal(node.left, n);
	        count++;
	        //System.out.println(count +".  "+node.name +"     "+ node.days);
	   
	        // when count = n then 
	        if (count == n) {
	        	nthPmName = node.name;
	        	//stop recursion after getting the value; this will speed up the algorithm
	        	return;
	        }
	        	
	   
	        /* now count on the right  */
	        inorderTraversal(node.right, n);
	    }

	}
	
	
	
	/**
	 * Helper function.
	 * Returns the number of nodes in the tree
	 * @return
	 */
	public int getLength() {
		return size(root);
	}
	
	/**
	 * Helper function to count the
	 * number of nodes in the tree
	 * @param node
	 * @return
	 */
    int size(Node node)
    {
        if (node == null)
            return 0;
        else
            return (size(node.left) + 1 + size(node.right));
    }
    
    
    public String[] allNShortest (int n) {
    	String pms[] = new String[10];
    	
    	for(int i = 1 ; i <= n; i++) {
    		pms[i-1]=this.nthShortest(i);
    	}
    	
    	return pms;
    	
    }
	
	
	public static void main(String args[]) {
		//System.out.println("\nInitializing PMTree ... \n");
		PMTree tree = new PMTree();
		ArrayList<PMList.Entry> list = (ArrayList<PMList.Entry>) PMList.getPrimeMinisters();
		for(PMList.Entry pm : list) {
			//System.out.println(pm.name +"     "+ pm.days);
			tree.insert(pm.days, pm.name);
		}
		//System.out.println("Successfully Loaded PMs Data ... \n");
		
		///System.out.println("Tree Length : "+ tree.getLength()+"\n");
		//10th, 20th, 30th, 40th and 50th
		System.out.println("10th Shortest : "+ tree.nthShortest(10));
		System.out.println("20th Shortest : "+ tree.nthShortest(20));
		System.out.println("30th Shortest : "+ tree.nthShortest(30));
		System.out.println("40th Shortest : "+ tree.nthShortest(40));
		System.out.println("50th Shortest : "+ tree.nthShortest(50));
		
		
		
		
		System.out.println("\n10 Shortest Serving Prime Ministers \n");
	    String pms[] = tree.allNShortest(10) ;
	    for(String p : pms) {
	    	System.out.println(p);
	    }
	    
	    int[] INCOMPLETE = PMList.INCOMPLETE;
	    //remove the incomplete pms
	    for(int j=0; j< INCOMPLETE.length; j++) {
	    	int rm = INCOMPLETE[j];
	    	tree.delete(rm);
	    }
	    
	    System.out.println("\n10 Shortest Serving Prime Ministers: After Replacements \n");
	    String pms2[] = tree.allNShortest(10) ;
	    for(String p : pms2) {
	    	System.out.println(p);
	    }
	    			
		
	}//end main
	
	
}