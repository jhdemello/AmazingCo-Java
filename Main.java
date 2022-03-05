import java.util.LinkedList;
import java.util.Queue;

public class Main {

    // This variable is checked before printing debug statements.
    //
    //   TRUE => Print debug statements
    //   FALSE => Do not print debug statements
    private static Boolean debug = false;

    /**
     * This function searches the N-ary tree for a parent identified by 'parentName', starting from a given
     * node. A child node is then created and inserted as a child of the parent node found.
     * 
     * @param start         Starting point for the search.
     * @param parentName    The name of the parent to search for.
     * @param childName     The name of the child to create.
     */
    private static void insert(AwesomeTreeNode start, String parentName, String childName) {
        // If the start is null, there's nothing to process
        if (start == null)
            return;

        // Load a queue with the start if there's capacity.
        Queue<AwesomeTreeNode> queue = new LinkedList<>();
        queue.offer(start);

        // There variable 'queue' has scope within the while loop. The first iteration is the start, and the
        // subsequent iterations are the start's progeny generated from repeated queueing of child nodes in the
        // innermost loop.
        boolean found = false;
        while (!found && !queue.isEmpty()) {

            // For the length of the queue at the current level.
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                // Pop each node from the queue at the current level and ensure it is not null.
                AwesomeTreeNode node = queue.poll();
                assert node != null;

                // Process the current node.
                if (node.name.equalsIgnoreCase(parentName)) {
                    // We've found the parent node at which to insert the new node. Insert the node and exit the
                    // loop.
                    found = true;
                    node.children.add(new AwesomeTreeNode(start, node, childName));
                } else {
                    // Queue up the children of the current node. All child nodes are added at the end of the
                    // queue after the nodes at the current level (i.e. at this point, all nodes at level N are
                    // already queued, followed by child nodes at level N + 1). This process continues until the
                    // last leaf at the far "right" at the maximum depth of the tree is dequeued by the outermost
                    // 'while' loop.
                    for (AwesomeTreeNode item : node.children) {
                        queue.offer(item);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        debug = false;

        // Level 0, root
        AwesomeTreeNode root = new AwesomeTreeNode("Galt, John");

        // Level 1
        root.insert(root, "Danneskjold, Ragnar");
        root.insert(root, "Taggart, Dagny");
        root.insert(root, "d'Anconia, Francisco");

        // Level 2
        AwesomeTreeNode node = root.get("Danneskjold, Ragnar");
        node.insert(node, "Reardon, Hank");
        node.insert(node, "Willers, Eddie");
        node.insert(node, "Dannager, Ken");

        node = root.get("Taggart, Dagny");
        node.insert(node, "Taggart, James");

        node = root.get("d'Anconia, Francisco");
        node.insert(node, "Reardon, Lillian");
        node.insert(node, "Stadler, Robert");
        node.insert(node, "Brooks, Cherryl");

        root.printTree();

        // Move operation
        AwesomeTreeNode nodeMove = root.pluck("Taggart, James");

        node = root.get("Danneskjold, Ragnar");
        //AwesomeTreeNode nodeMove = root.pluck("Taggart, Dagny");
        node.insert(node, nodeMove.name);
        root.printTree();
    }
}
