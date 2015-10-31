package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {
	private static String[] categoryNames = {""};
	private List<Category> categories = new ArrayList<Category>();

	public List<Category> getCategories() {
		return this.categories;
	}

	public Category otherCategory() {
		return this.categories.get(this.categories.size() - 1);
	}

	// Singleton
	private static CategoryList instance = new CategoryList();
	private CategoryList() {
		for (String name : CategoryList.categoryNames) {
			this.categories.add(new Category(name));
		}
	}
	public static CategoryList sharedCategoryList() { return CategoryList.instance; }
}