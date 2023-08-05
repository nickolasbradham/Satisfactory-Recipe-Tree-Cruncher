package nbradham.recipeWeigher;

import java.util.ArrayList;
import java.util.HashMap;

final class Item {

	private final String name;
	private final ArrayList<Recipe> recipes = new ArrayList<>();

	private float weight = Float.POSITIVE_INFINITY;

	Item(String iName) {
		name = iName;
	}

	Item(String iName, float iWeight) {
		name = iName;
		weight = iWeight;
	}

	void addRecipe(Recipe r) {
		recipes.add(r);
	}

	@Override
	public String toString() {
		return new StringBuilder("(").append(name).append(", ").append(weight).append(", ").append(recipes).append(")")
				.toString();
	}

	static HashMap<String, Float> parseItemStacks(String str) {
		HashMap<String, Float> items = new HashMap<>();
		String[] split;
		for (String stack : str.split(", "))
			items.put((split = stack.split(":"))[1], Float.parseFloat(split[0]));
		return items;
	}
}