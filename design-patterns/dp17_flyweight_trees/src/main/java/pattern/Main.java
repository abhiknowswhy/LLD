package pattern;

public class Main {
	public static void main(String[] args) {
		int[][] deciduousLocations = {{1, 1}, {33, 50}, {100, 90}};
		int[][] coniferLocations = {{10, 87}, {24, 76}, {2, 64}};
		TreeFactory treeFactory = new TreeFactory();
		TreeClient client = new TreeClient(treeFactory);
		try {
			for (int[] location : deciduousLocations) {
				client.addTree("deciduous", location[0], location[1]);
			}
			for (int[] location : coniferLocations) {
				client.addTree("conifer", location[0], location[1]);
			}
			client.displayAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}