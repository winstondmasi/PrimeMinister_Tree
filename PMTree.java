import java.util.ArrayList;

public class PMTree {
    private class Node {
        Node parent;
        Node left, right;
        int days;
        String name; //EXER 1.1 ADD STRING COMPONENT
        int current_node_number = 0; // EXER 2 measure current node position

        Node (Node parent, int days, String name) {
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
    int sum = 0;
    String nth_short_found = " ";
    public String getName (int days){
        Node checking = root;
        nth_short_found = " ";

        /*
        loop to check if the day we put as a parameter is the
        same as the day in the list we'll be traversing through

        while loop is true if the day is equal to node.day
        nth_short_found will be assigned to the node.name and return it if it's correct
        if not check left and right node and increment iteratively until first if statement is correct
        */
        while(true){
            if(days == checking.days){
                nth_short_found = checking.name;
                return nth_short_found;
            }
            else if(days < checking.days){
                if(checking.left == null){
                    checking.left.current_node_number += 1;
                }else{
                    checking = checking.left;
                }
            }else{
                if(checking.right == null){
                    checking.right.current_node_number++;
                }else{
                    checking = checking.right;
                }
            }
        }
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

    //EXER 1.1 & 1.2 MODIFY INSERT AND DELETE
    public void insert (int days, String name) {
        Node cur = root;
        if (root == null) {
            root = new Node (null, days, name); 
            return;
        }

        while (true) {
            if (days == cur.days){
                cur.name = name; // EXER 1 if tree with duplicate day exists update the name to match
                return;
            }
            else if (days < cur.days) {
                if (cur.left == null) {
                    cur.left = new Node (cur, days, name);
                    cur.left.current_node_number += 1; // EXER 2 increment node to match its current position
                    return;
                } else
                    cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node (cur, days, name);
                    cur.right.current_node_number += 1; // EXER 2 increment node to match its current position
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

    //EXER 1.2 MODIFY INSERT AND DELETE
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
        if(node.parent != null){
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

    //EXER 3 NTH-SHORTEST
    public String nthShortest(int n){
        /*
        use the inorder traversal method defined earlier to traverse through the tree
        find the corresponding day and
        return the string "nth_short_found" to match
         */
        sum = 0;
        nth_short_found = " ";
        inorder_traversal_method_recur(root, n);
        return nth_short_found;
    }

    //function that serves as inorder search method for the binary tree
    //to find the nth shortest prime minister in EXER 3
    public void inorder_traversal_method_recur(Node head_node, int prime_shortest){
        //base case
        if(head_node == null){
            return;
        }  
        /*
         * if sum is less than or equal to int prime shortest, recursively traverse through the left side of tree
         * whilst incrementing sum until sum is == to the int we are looking for,
         * then nth_short_found will be assigned as to head_node name
         * 
         * After that we are going to traverse the right side of the tree also recursively
         */
        if(sum <= prime_shortest){
            inorder_traversal_method_recur(head_node.left, prime_shortest);
            sum++;

            if(sum == prime_shortest){
                nth_short_found = head_node.name;
                return;
            }
            inorder_traversal_method_recur(head_node.right, prime_shortest);
        }        
    }

    //EXER 4 ALL_N_SHORTEST
    public String[] allNShortest (int n){
        /*
        Make a string array
        loop through the array n times and
        assign each index of the string array to each index of nthShortest
        then return string array
         */
        String[] all_n_s = new String[11];
        for(int i = 0; i < n; i++){ all_n_s[i] = this.nthShortest(i);}
        return all_n_s;
    }
    
    //EXER 5 MAIN
    public static void main(String[] args) {
        /*
         * Initialize new PM_Tree
         * Initialize Arraylist with data type: Entry from PMList
         * loop through list and call insert(); to add list of prime ministers to new tree
         */
        PMTree resulting_PmTree = new PMTree();
        ArrayList<PMList.Entry> prime_miniArrayList = (ArrayList<PMList.Entry>)PMList.getPrimeMinisters();
        for(PMList.Entry getInfo : prime_miniArrayList){resulting_PmTree.insert(getInfo.days, getInfo.name);}

        /*
        OR ANOTHER SOLUTION TO INSERT DAYS AND NAME

        for(int w = 0; i < PMList.getPrimeMinisters().size(); w++){
            resulting_PmTree.insert(PMList.getPrimeMinisters().get(w).days, PMList.getPrimeMinisters().get(w).name);
        }
        */

        //Printing 10th, 20th, 30th, 40th and 50th shortest-serving prime ministers.
        System.out.println("\n10th shortest serving prime minister is: " + resulting_PmTree.nthShortest(10));
        System.out.println("20th shortest serving prime minister is: " + resulting_PmTree.nthShortest(20));
        System.out.println("30th shortest serving prime minister is: " + resulting_PmTree.nthShortest(30));
        System.out.println("40th shortest serving prime minister is: " + resulting_PmTree.nthShortest(40));
        System.out.println("50th shortest serving prime minister is: " + resulting_PmTree.nthShortest(50));
        
        //Printing the 10 shortest serving prime-ministers
        System.out.println("\nThe 10 shortest serving prime ministers are: ");
        String[] aspm = resulting_PmTree.allNShortest(11);
        for(String print_all_shortest : aspm) System.out.println(print_all_shortest);

        //REMOVE DEAD PM FROM THE LIST
        int[] impartial = PMList.INCOMPLETE;
        for (int fodder : impartial) {resulting_PmTree.delete(fodder);}

        //PRINT LIST AFTER DELETION
        System.out.println("\nThe 10 Shortest Prime Ministers: After Deleting From PMList.INCOMPLETE");
        String[] deleted_impartial = resulting_PmTree.allNShortest(11);
        for(String erased : deleted_impartial){ System.out.println(erased);}

        /*
        System.out.println("\n" + "Testing getName() method: " + "\n");
        System.out.println("getName (49 days): " + resulting_PmTree.getName(49));
        System.out.println("getName (3 years and 259 days): " + resulting_PmTree.getName(365* 3 + 259));
        System.out.println("getName (6 years and 255 days): " + resulting_PmTree.getName(365* 6 + 255));
        System.out.println("getName (10 years and 56 days): " + resulting_PmTree.getName(365*10 +  56));

        resulting_PmTree.insert(23, "John Stewart");
        System.out.println("Insert day(23) is: " + resulting_PmTree.getName(23));
         */
    }
}