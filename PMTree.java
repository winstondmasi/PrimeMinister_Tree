public class PMTree {
    private class Node {
        Node parent;
        Node left, right;
        int days;
        String name; //adding string component

        Node (Node parent, int days, String names) {
            this.parent = parent;
            this.days = days;
            this.left = this.right = null;
            this.name = name;
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

    public void insert (int days, String name) {
        Node cur = root;
        if (root == null) {
            root = new Node (null, days, name);
            return;
        }

        while (true) {
            if (days == cur.days)
                return;
            else if (days < cur.days) {
                if (cur.left == null) {
                    cur.left = new Node (cur, days, name);
                    return;
                } else
                    cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node (cur, days, name);
                    return;
                } else
                    cur = cur.right;
            }
        }
    }

    
    public String nthShortest(int n){
       return nthShortest(n);
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
    public void delete (int days, String name) {
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

    

    public String[] allNShortest (int n){
        return null;
    }

    public static void main(String[] args) {

        PMTree resulting_PmTree = new PMTree();

    }
}