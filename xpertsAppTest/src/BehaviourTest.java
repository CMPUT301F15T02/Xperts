public class BehaviourTest extends ActivityInstrumentationTestCase2 {
  public BehaviourTest() {
    super(MainActivity.class);
  }

  public void testPushOffersToServer() {
    User owner = new User("username");
    ArrayList<Service> offers = new ArrayList<>();

    // Owner makes service offers
    Service service = new Service("test");
    offers.add(service);
    owner.setOffers(offers);
    new IOManager().pushToServer("username", owner);

    // Owner changes his service offers
    offers.get(0).setName("newTest");
    owner.setOffers(offers);
    new IOManager().pushToServer("username", owner);

    // Retrieve from server
    User owner_server = new IOManager().pullFromServer("username");

    assertEquals(offers, owner_server.getOffers());
  }

  public void testPushTradesToServer() {
    User borrower = new User("username");
    ArrayList<Service> gives = new ArrayList<>();
    ArrayList<Service> receives = new ArrayList<>();

    // Borrower makes trade
    Service give = new Service("test");
    gives.add(give);
    Service receive = new Service("newTest");
    receives.add(service);
    borrower.setGives(gives);
    borrower.setReceives(receives);
    new IOManager().pushToServer("username", borrower);

    // Retrieve from server
    User borrower_server = new IOManager().pullFromServer("username");

    assertEquals(gives, borrower_server.getGives());
    assertEquals(receives, borrower_server.getReceives());
  }

  public void testHistoryNotEmpty() {
    ArrayList<Service> historyList = new ArrayList<>();
    Service service = new Service("test");
    historyList.add(service);
    assertFalse(historyList.size(), 0);
  }
}
