package pattern;

import pattern.Trees.ConiferTree;
import pattern.Trees.DeciduousTree;

public class TreeFactory {
	private volatile Tree d, c;

	public Tree getTree(String type) throws Exception {
		if (type.equals("deciduous")) {
			if (d == null) {
				synchronized (this) {
					if (d == null) {
						d = new DeciduousTree();
					}
				}
			}
			return d;
		} else if (type.equals("conifer")) {
			if (c == null) {
				synchronized (this) {
					if (c == null) {
						c = new ConiferTree();
					}
				}
			}
			return c;
		} else {
			throw new Exception("Invalid kind of tree");
		}
	}
}