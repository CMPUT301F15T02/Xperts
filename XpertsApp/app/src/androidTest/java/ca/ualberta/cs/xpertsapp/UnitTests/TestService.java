package ca.ualberta.cs.xpertsapp.UnitTests;

import java.util.UUID;

import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;

public class TestService extends Service {

	TestService(String owner, String name, String desc, int categoryNumber, boolean shareable) {
		super(UUID.randomUUID().toString(), owner);
		this.setName(name);
		this.setDescription(desc);
		this.setCategory(CategoryList.sharedCategoryList().getCategories().get(categoryNumber));
		this.setShareable(shareable);
//		IOManager.sharedManager().storeData(this, Constants.serverServiceExtension() + this.getID());
	}

	@Override
	protected boolean isEditable() {
		return true;
	}
}