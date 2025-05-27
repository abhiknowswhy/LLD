package pattern;

import java.util.ArrayList;
import java.util.List;

public class TreeClient {
    private final List<TreeLocation> trees = new ArrayList<>();
    private final TreeFactory treeFactory;

    public TreeClient(TreeFactory treeFactory) {
        this.treeFactory = treeFactory;
    }

    public void addTree(String type, int x, int y) throws Exception {
        Tree tree = treeFactory.getTree(type);
        trees.add(new TreeLocation(tree, x, y));
    }

    public void displayAll() {
        for (TreeLocation treeLoc : trees) {
            treeLoc.tree.display(treeLoc.x, treeLoc.y);
        }
    }

    private static class TreeLocation {
        Tree tree;
        int x, y;
        TreeLocation(Tree tree, int x, int y) {
            this.tree = tree;
            this.x = x;
            this.y = y;
        }
    }
}
