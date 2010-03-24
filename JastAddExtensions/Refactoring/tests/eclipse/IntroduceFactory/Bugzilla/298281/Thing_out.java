package p;

public class Thing {
	public static Thing createThing(Thing... subthings) {
		return new Thing(subthings);
	}

	private Thing[] subthings;

	public Thing() {
		subthings = new Thing[] {};
	}

	private /*[*//*]*/Thing(Thing... subthings) {
		this.subthings = subthings;
	}

	public static void main(String args[]) {
		System.out.println(createThing(createThing(new Thing()), createThing(new Thing())).subthings.length);
	}
}
