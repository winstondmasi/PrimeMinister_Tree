import java.util.ArrayList;

public class PMTree {
    private class Node {
        Node parent;
        Node left, right;
        int days;
        String name; //EXER 1.1 ADD STRING COMPONENT
        int current_node_number = 0; // measure current node position

        Node (Node parent, int days, String names) {
            this.parent = parent;
            this.days = days;
            this.left = this.right = null;
            this.name = name; //EXER 1.1 ADD STRING COMPONENT
        }

        /*
         *  When deleting, we want to know how many children a node has.
         */
        int numChildren () {
            return (left == null ? 0 : 1) + (right == null ? 0 : 1);
        }
    }

    private Node root = null;
    
    //EXER 1.3 RETRIEVE NAME
    public String getName (int days) {
        return getNode(days).name; // as name implies get the nodes name that corresponds with the parameter day
    }

    private Node getNode (int days) {
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

    //EXER 1.2 MODIFY INSERT AND DELETE
    public void insert (int days, String name) {
        Node cur = root;
        if (root == null) {
            root = new Node (null, days, name); 
            return;
        }

        while (true) {
            if (days == cur.days){
                cur.name = name; //if tree with duplicate day exists update the name to match
                return;
            }
            else if (days < cur.days) {
                if (cur.left == null) {
                    cur.left = new Node (cur, days, name);
                    cur.left.current_node_number += 1; //increment node to match its current position
                    return;
                } else
                    cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node (cur, days, name);
                    cur.right.current_node_number += 1; //increment node to match its current position
                    return;
                } else
                    cur = cur.right;
            }
        }
    }

    /*
     *  Delete a node from the tree. If the node has one child or none,
     *  delete it as if it, its parent (if it exists) and its child (if
     *  it has one) are a doubly linked list. Otherwise, replace the value
     *  in the node to be deleted with the minimum element of its right
     *  subtree and delete the node that originally contained that element.
     *  Note that, by construction, that node has at most one child (it has
     *  no left child because a left child would contain a smaller value).
     */

    //EXER 1.2 & 2 MODIFY INSERT AND DELETE
    public void delete (int days) {
        Node node = getNode (days);
        if (node == null)
            return;

        if (node.numChildren() < 2) {
            simpleDelete (node);
        } else {
            Node min = getMinNode(node.right);
            simpleDelete (min);
            node.days = min.days;
        }

        //if the number of nodes the parent has is more than 0
        //reduce the count of it
        if(node.parent.numChildren() > 0){
            node.parent.current_node_number -= 1;
        }
    }

    /*
     *  Delete a node that has one child or none. We treat this node,
     *  its parent (if it exists) and its child (if it has one) as a doubly
     *  linked list and delete accordingly. The code is fiddly because of
     *  the special cases. We might be deleting the root (parent == null)
     *  and/or we might be deleting a node with no child, with just a left
     *  child or just a right child.
     */
    private void simpleDelete (Node node) {
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
     *  Return the node containing the smallest value in the subtree
     *  rooted at the given node. This is found by following the left
     *  child reference until we get to a node that has no left child.
     */
    private Node getMinNode (Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }


    //EXER 3 NTHSHORTEST
    int count;
    String prime_min_the_nTH;
    public String nthShortest(int n){
        return nthShortest(n);
    }

    //function that serves as inorder traversal method
    //to find the nth shortest prime minister in EXER 3
    public void inorder_traversal_method(Node head_node, int day_of_PM){
        
    }

    //EXER 4 ALL_N_SHORTEST
    public String[] allNShortest (int n){
        return null;
    }
    
    //EXER 5 MAIN
    public static void main(String[] args) {
        /*
         * Initialize new PM_Tree
         * Initialize Arraylist with data type: Entry from PMList
         * 
         * loop through list and call insert(); to add list of prime ministers to new tree
         */
        PMTree resulting_PmTree = new PMTree();
        ArrayList<PMList.Entry> prime_miniArrayList = (ArrayList<PMList.Entry>)PMList.getPrimeMinisters();

        for(PMList.Entry getInfo : prime_miniArrayList){
            resulting_PmTree.insert(getInfo.days, getInfo.name);
        }

        //Printing 10th, 20th, 30th, 40th and 50th shortest-serving prime ministers.
        System.out.println("10th shortest serving prime minister: " + resulting_PmTree.nthShortest(10));
        System.out.println("20th shortest serving prime minister: " + resulting_PmTree.nthShortest(20));
        System.out.println("30th shortest serving prime minister: " + resulting_PmTree.nthShortest(30));
        System.out.println("40th shortest serving prime minister: " + resulting_PmTree.nthShortest(40));
        System.out.println("50th shortest serving prime minister: " + resulting_PmTree.nthShortest(50));
        
        //Printing the 10 shortest serving prime-ministers
        System.out.println("\nThe 10 shortest serving prime ministers are: \n");

    }
}