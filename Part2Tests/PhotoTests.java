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
