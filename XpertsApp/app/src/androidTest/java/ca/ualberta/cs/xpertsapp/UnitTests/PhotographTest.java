package ca.ualberta.cs.xpertsapp.UnitTests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.ImageManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class PhotographTest extends TestCase {
	public PhotographTest() {
		super();
	}

	private Bitmap smallImage;
	private Bitmap largeImage;

	@Override
	protected void setUp2() {
		super.setUp2();

		String smallB64_1 = MyApplication.getContext().getString(R.string.smallTestImage);
		byte[] smallByteArray_1 = Base64.decode(smallB64_1, Base64.DEFAULT);
		Bitmap smallImage_1 = BitmapFactory.decodeByteArray(smallByteArray_1, 0, smallByteArray_1.length);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		smallImage_1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] b = byteArrayOutputStream.toByteArray();
		String smallB64_2 = Base64.encodeToString(b, 2);
		byte[] smallByteArray_2 = Base64.decode(smallB64_2, Base64.DEFAULT);
		smallImage = BitmapFactory.decodeByteArray(smallByteArray_2, 0, smallByteArray_2.length);
		assertTrue(smallImage.sameAs(BitmapFactory.decodeByteArray(smallByteArray_2, 0, smallByteArray_2.length)));

		String largeB64_1 = MyApplication.getContext().getString(R.string.largeTestImage);
		byte[] largeByteArray_1 = Base64.decode(largeB64_1, Base64.DEFAULT);
		Bitmap largeImage_1 = BitmapFactory.decodeByteArray(largeByteArray_1, 0, largeByteArray_1.length);
		ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
		largeImage_1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream1);
		byte[] b_2 = byteArrayOutputStream1.toByteArray();
		String largeB64_2 = Base64.encodeToString(b_2, 2);
		byte[] largeByteArray_2 = Base64.decode(largeB64_2, Base64.DEFAULT);
		largeImage = BitmapFactory.decodeByteArray(largeByteArray_2, 0, largeByteArray_2.length);
		assertTrue(largeImage.sameAs(BitmapFactory.decodeByteArray(largeByteArray_2, 0, largeByteArray_2.length)));
	}

	@Override
	protected void tearDown2(){
		super.tearDown2();
	}

	// Also 06.02.01
	public void test_06_01_01() {
		setUp2();
		// Test attach photo
		User user = UserManager.sharedManager().localUser();
		Service service = ServiceManager.sharedManager().newService();
		service.setName("testImageService");
		user.addService(service);
		service.addPicture(smallImage);

		assertEquals(service.getPictures().size(), 1);
		assertEquals(service.getPictures().get(0).hashCode(), smallImage.hashCode());

		// Test view photo
		ImageManager.sharedManager().clearCache();
		assertTrue(service.getPictures().get(0).sameAs(smallImage));
		tearDown2();
	}

	public void test_06_03_01() {
		setUp2();
		// Test delete photo
		User user = UserManager.sharedManager().localUser();
		Service service = ServiceManager.sharedManager().newService();
		service.setName("testImageService");
		user.addService(service);
		service.addPicture(smallImage);

		assertEquals(service.getPictures().size(), 1);
		assertEquals(service.getPictures().get(0).hashCode(), smallImage.hashCode());

		service.removePicture(0);
		assertEquals(service.getPictures().size(), 0);

		tearDown2();
	}

	public void test_06_04_01() {
		setUp2();
		// Test image size check under 65536 bytes
		User user = UserManager.sharedManager().localUser();
		Service service = ServiceManager.sharedManager().newService();
		service.setName("testImageService");
		user.addService(service);
		service.addPicture(largeImage);

		assertEquals(service.getPictures().size(), 1);
		assertFalse(service.getPictures().get(0).sameAs(largeImage));

		tearDown2();
	}

	// 06.05.01
}