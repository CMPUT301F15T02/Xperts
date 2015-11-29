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

//		String largeB64_1 = MyApplication.getContext().getString(R.string.largeTestImage);
//		byte[] largeByteArray_1 = Base64.decode(largeB64_1, Base64.DEFAULT);
//		Bitmap largeImage_1 = BitmapFactory.decodeByteArray(largeByteArray_1, 0, largeByteArray_1.length);
//		ByteArrayOutputStream byteArrayOutputStream__1 = new ByteArrayOutputStream();
//		largeImage_1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream__1);
//		byte[] b__1 = byteArrayOutputStream__1.toByteArray();
//		String largeB64_2 = Base64.encodeToString(b__1, 2);
//		byte[] smallByteArray__1 = Base64.decode(largeB64_2, Base64.DEFAULT);
//		largeImage = BitmapFactory.decodeByteArray(smallByteArray__1, 0, smallByteArray__1.length);
	}

	@Override
	protected void tearDown2(){
		super.tearDown2();
	}

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

		ImageManager.sharedManager().clearCache();
		assertTrue(service.getPictures().get(0).sameAs(smallImage));
		tearDown2();
	}

	public void test_06_02_01() {
		setUp2();
		// TODO: Test view photo
		assertTrue(false);

		tearDown2();
	}

	public void test_06_03_01() {
		setUp2();
		// TODO: Test delete photo
		assertTrue(false);

		tearDown2();
	}

	public void test_06_04_01() {
		setUp2();
		// TODO: Test image size check under 65536 bytes
		assertTrue(false);

		tearDown2();
	}

	public void test_06_05_01() {
		setUp2();
		// TODO: Test manually download individual photos if auto-download is off
		assertTrue(false);

		tearDown2();
	}
}