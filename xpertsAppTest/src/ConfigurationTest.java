public class ConfigurationTest extends ActivityInstrumentationTestCase2 implements MyObserver {
  public ConfigurationTest() {
    super(MainActivity.class);
  }

  public void testToggleDownload() {
    User user = new User("username");
    user.getSettings().setToggleDownload(Boolean.TRUE);
    assertTrue(user.getSettings().getToggleDownload());
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
