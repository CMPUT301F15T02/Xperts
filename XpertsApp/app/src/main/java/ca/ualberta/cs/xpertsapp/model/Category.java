package ca.ualberta.cs.xpertsapp.model;

public class Category {
	private String name;

	Category(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() == String.class) {
			return this.name.equals(o);
		}
		return o.getClass() == this.getClass() && this.name.equals(((Category) o).getName());
	}

	@Override
	public String toString() {
		return this.name;
	}
}