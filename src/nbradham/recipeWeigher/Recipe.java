package nbradham.recipeWeigher;

import java.util.HashMap;

final class Recipe {

	private final String name, machine;
	private final HashMap<String, Float> ins, outs;
	private final short t;

	Recipe(String rName, String rMachine, HashMap<String, Float> inputs, short time, HashMap<String, Float> outputs) {
		name = rName;
		machine = rMachine;
		ins = inputs;
		outs = outputs;
		t = time;
	}

	HashMap<String, Float> inputs() {
		return ins;
	}

	HashMap<String, Float> outputs() {
		return outs;
	}

	@Override
	public String toString() {
		return name;
	}
}