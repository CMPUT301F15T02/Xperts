package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A singleton to store the list of categories for the app. Ensures there is only 10 categories and
 * that none are added or removed during execution of the app.
 */
public class CategoryList {

	private static String[] categoryNames = {
			"ACCOUNTING",
			"CONSTRUCTION",
			"COMPUTER",
			"FITNESS",
			"GENERAL LABOUR",
			"HEALTHCARE",
			"LEGAL",
			"MUSIC",
			"TUTORING",
			"OTHER"
	};

	private List<Category> categories = new ArrayList<Category>();

	/**
	 * @return List of all categories available
	 */
	public List<Category> getCategories() {
		return this.categories;
	}

	/**
	 * @return A category class representing "Other"
	 */
	public Category otherCategory() {
		return this.categories.get(this.categories.size() - 1);
	}

	/**
	 * Singleton
	 */
	private static CategoryList instance = new CategoryList();

	/**
	 * Constructor that sets up the categories based on the hard-codes strings in the code.
	 */
	private CategoryList() {
		for (String name : CategoryList.categoryNames) {
			this.categories.add(new Category(name));
		}
	}

	public static CategoryList sharedCategoryList() {
		return CategoryList.instance;
	}
}