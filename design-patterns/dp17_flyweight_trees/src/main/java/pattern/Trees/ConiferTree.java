package pattern.Trees;

import pattern.Tree;

public class ConiferTree implements Tree {
	// Complex trunk, branch, needle graphic data
	public void display(int x, int y) {
		System.out.println("Conifer tree is located at " + x + ", " + y);
	}
}