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
			queue.offer(r);
		}
		Recipe r;
		float sum, rate;
		HashMap<String, Float> is;
		Item i;
		while (!queue.isEmpty()) {
			sum = 0;
			rate = 60 / (r = queue.poll()).time();
			is = r.inputs();
			for (String k : is.keySet())
				sum += items.get(k).weight() * is.get(k) * rate;
			if (sum < r.weight()) {
				r.setWeight(sum);
				for (String k : r.outputs().keySet()) {
					i = items.get(k);
					if (sum < i.weight()) {
						i.setBest(r);
						i.setWeight(sum);
						queue.addAll(i.recipes());
					}
				}
			}
		}
		System.out.println(items);
	}

	public static void main(String[] args) {
		new Weigher().start();
	}
}