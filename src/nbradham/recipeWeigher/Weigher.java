package nbradham.recipeWeigher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

final class Weigher {

	private static final String DELIM = "\t|\r\n";

	private final HashMap<String, Item> items = new HashMap<>();
	private final Queue<Recipe> queue = new LinkedList<>();

	private void start() {
		Scanner scan = new Scanner(Weigher.class.getResourceAsStream("/raws.tsv")).useDelimiter(DELIM);
		String name;
		scan.nextLine();
		while (scan.hasNext()) {
			items.put(name = scan.next(), new Item(name, scan.nextFloat()));
			scan.nextLine();
		}
		scan.close();
		scan = new Scanner(Weigher.class.getResourceAsStream("/recipes.tsv")).useDelimiter(DELIM);
		scan.nextLine();
		while (scan.hasNext()) {
			Recipe r = new Recipe(scan.next(), scan.next(), Item.parseItemStacks(scan.next()), scan.nextShort(),
					Item.parseItemStacks(scan.next()));
			r.outputs().keySet().forEach(k -> items.putIfAbsent(k, new Item(k)));
			r.inputs().keySet().forEach(k -> {
				if (!items.containsKey(k))
					items.put(k, new Item(k));
				items.get(k).addRecipe(r);
			});
		}
		System.out.println(items);
	}

	public static void main(String[] args) {
		new Weigher().start();
	}
}