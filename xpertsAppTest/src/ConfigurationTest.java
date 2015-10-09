public class ConfigurationTest extends ActivityInstrumentationTestCase2 implements MyObserver {
  public ConfigurationTest() {
    super(MainActivity.class);
  }

  private Boolean imageDownloaded;

  public void notifyDownloaded() {
    imageDownloaded = Boolean.TRUE;
  }

  public void testDownloaded() {
    imageDownloaded = Boolean.FALSE;
    new DownloadManager(this).downloadImage(imageUrl);
    assertTrue(imageDownloaded);
  }

  private Boolean profileWasChanged;

  public void notifyProfile() {
    profileWasChanged = Boolean.TRUE;
  }

  public void testProfileChange() {
    User user = new User("username");
    profileWasChanged = Boolean.FALSE;
    user.addObserver(this);
    user.setName("test"); 
    assertTrue(profileWasChanged);
  }
}
