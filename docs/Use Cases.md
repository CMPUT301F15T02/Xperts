# Photographs of Items

## Use Cases

| UC06.01.01 |     |
| ---------- | --- |
| Use case name: | AttachPhotographs |
| Triggering event: | Actor wants to attach a photo |
| Brief description: | Allow actor to attach photographs |
| Actors: | Owner |
| Input: | Owner tells system to attach an image |
| Output: | The image is attached to the item |
| Normal Execution Path: | 1. Owner adds a new item (US01.01.01) |
|| 2. While adding the item, Owner signals to the system they want to attach an image |
|| 3. System presents a dialog for picking an image |
|| 4. User selects a image |
|| 5. System attaches the image to the item |
| Exception Path : | 4. If Owner doesn't pick an image, revert to before Owner tried to add an image |

| US06.02.01 |     |
| ---------- | --- |
| Use case name: | ViewPhotographs |
| Triggering event: | Actor wants to view attached photos their item |
| Brief description: | Allow the actor to view the attached images on their item |
| Actors: | Owner |
| Input: | Owner tells the system that they wish to view the attached images on their item |
| Output: | The system presents a view containing the images attached to the item |
| Normal Execution Path: | 1. Owner browses their items (US01.02.01) |
|| 2. Owner chooses to view an item (US01.03.01) |
|| 3. System presents detailed information about the item |
|| 4. Owner tells the system they want to view the attached images |
|| 5. System presents the images to the Owner |
| Exception Path : | 4. If there are no attached images, the method of requesting the images is disabled. Nothing should happen |

| US06.03.01 |     |
| ---------- | --- |
| Use case name: | DeletePhotograph |
| Triggering event: | Actor wants to delete an attached photo from an item |
| Brief description: | Allow the actor to go back and remove an image from an item |
| Actors: | Owner |
| Input: | Owner tells the system they wish to delete an image from their item |
| Output: | The system deletes the image |
| Normal Execution Path: | 1. Owner browses their items (US01.02.01) |
|| 2. Owner chooses to view an item (US01.03.01) |
|| 3. Owner views the images attached to the item (US06.02.01) |
|| 4. Owner deletes an image |
| Exception Path : | None |

| US06.04.01 |     |
| ---------- | --- |
| Use case name: | ImageSizeLimit |
| Triggering event: | Owner attaches an image to an item |
| Brief description: | When an Owner tries to attach an image to an item, the size of the image should be check to ensure it meets the size requirements |
| Actors: | SysAdmin |
| Input: | The image the Owner is trying to attach |
| Output: | Whether or not the image can be attached |
| Normal Execution Path: | 1. Owner attempts to attach an image (UC06.01.01) |
|| 2. Image size is checked by the system |
|| 3. Image is attached |
| Exception Path : | 2. If the size exceeds the limit of 65536 bytes, the image should be rejected via a message to the Owner sayign "The image size is too large to be attached" |

| US06.05.01 | See US10.01.01 |
| ---------- | -------------- |
| Use case name: | ToggleImageDownload |
| Triggering event: | Actor tells the system to either allow or disallow image downloading |
| Brief description: | When the Actor whishes to allows or block image downloading, the system should comply and only download image when allowed |
| Actors: | Borrower |
| Input: | Borrower tells the system to allow or block image downloads |
| Output: | System either starts or stops downloading new images |
| Normal Execution Path: | 1. Borrower is browsing the settings (US10.01.01) |
|| 2. Borrower toggles the switch to allow or disallow image downloading |
|| 3. Systems responds accordingly |
| Exception Path : | None |


# Tests

```java
import android.test.ActivityInstrumentationTestCase2;

public class PhotoTests extends ActivityInstrumentationTestCase2 {
	public PhotoTests() {
		super(MainActivity.class);
	}
	
	public testAttachPhotographs() {
		Item item = new Item("Item Name");
		Image image1 = new Image("someimage.png") // An image the "Owner" chose to attach
		Image image2 = new Image("otherimage.png") // An image the "Owner" chose to attach
		item.attachImage(image1);
		item.attachImage(image2);
		assertTrue(item.getImages().count() == 2);
	}
	
	public testViewPhotographs() {
		Item item = new Item("item name");
		Image image1 = new Image("someimage.png")
		Image image2 = new Image("otherimage.png")
		item.attachImage(image1);
		item.attachImage(image2);
		List<Image> images = item.getImages();
		assertEqual(images.get(0), image1);
		assertEqual(images.get(1), image2);
	}
	
	public testDeletePhotograph() {
		Item item = new Item("item name");
		Image image = new Image("someimage.png")
		item.attachImage(image);
		item.detatchImage(image);
		assertTrue(item.getImages().count() == 0);
	}
	
	public testImageSizeLimit() {
		boolean pass = false;
		try {
			Item item = new Item("item name");
			Image largeImage = new Image("someLargeImageThatIsTooBig.png")
			item.attachImage(largeImage);
		} catch (ImageTooLargeException e) {
			pass = true;
		}
		assertTrue(pass);
	}
	
	public testToggleImageDownload() {
		ResourceManager rm = ResourceManager.sharedResourceManager();
		rm.setAllowsDownloads(true);
		assertTrue(rm.getAllowsDownloads);
		rm.setAllowsDownloads(false);
		asserFalse(rm.getAllowsDownloads);
	}
}
```