package ca.ualberta.cs.xpertsapp.model;

/**
 *  Category class that stores name of a category a string. Basically the same as a string but wrapped
 *  as our own class and made immutable.
 */
public class Category {
	private String name;

	public Category(String name) {
		this.name = name;
	}

	/**
	 * @return the name of the category
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Override of the .equals() method to allow comparison with String objects.
	 * @param o A category or string with a category name
	 * @return whether the category is equivalent
	 */
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