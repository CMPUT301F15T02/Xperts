public class BehaviourTest extends ActivityInstrumentationTestCase2 {
  public BehaviourTest() {
    super(MainActivity.class);
  }

  /* 
  Services and Trades should be done in offline mode first, changes are saved in local storage.
  Then System write changes to server when internet connection is detected.
  */
 
  // Test that a service offer is added offline
  public void testAddOffersOffline() {
    User owner = new User("username");
    ArrayList<Service> offers = new ArrayList<>();

    // Owner makes service offers offline
    Service service = new Service("test");
    offers.add(service);
    owner.setOffers(offers);

    // Saves to local storage
    new IOManager().writeToFile(owner);

    User ownerLocal = new IOManager().loadFromFile("username");
    assertEquals(offers, ownerLocal.getOffers());
  }

  // Test that an offline service offer is added to server when internet connection is detected  
  public void testPushOffersToServer() {
    // Loads user from local storage
    User ownerLocal = new IOManager().loadFromFile("username");

    // Writes to server (if internet is detected) 
    new IOManager().pushToServer(ownerLocal);

    // Owner changes his service offers offline
    ownerLocal.getOffers.get(0).setName("newTest");

    // Writes change to local storage
    new IOManager().writeToFile(ownerLocal);
   
    // Writes change to server (if internet is detected)
    new IOManager().pushToServer(ownerLocal);

    // Retrieves from server
    User ownerServer = new IOManager().pullFromServer("username");

    assertEquals(ownerLocal.getOffers(), ownerServer.getOffers());
  }

  // Test that a trade can be done offline
  public void testTradeOffline() {
    User borrower = new User("username");
    ArrayList<Service> gives = new ArrayList<>();
    ArrayList<Service> receives = new ArrayList<>();

    // Borrower makes trade offline
    Service give = new Service("test");
    gives.add(give);
    Service receive = new Service("newTest");
    receives.add(service);
    borrower.setGives(gives);
    borrower.setReceives(receives);

    // Writes trade to local storage
    new IOManager().writeToFile(borrower);

    User borrowerLocal = new IOManager().loadFromFile("username");
    assertEquals(gives, borrowerLocal.getGives());
    assertEquals(receives, borrowerLocal.getReceives());
  }

  // Test that an offline trade can be written to server if internet is detected  
  public void testPushTradesToServer() {
    // Loads borrower from local storage
    User borrowerLocal = new IOManager().loadFromFile("username");

    // Writes to server (if internet is detected) 
    new IOManager().pushToServer(borrowerLocal);

    // Retrieves from server
    User borrowerServer = new IOManager().pullFromServer("username");

    assertEquals(borrowerLocal.getGives(), borrowerServer.getGives());
    assertEquals(borrowerLocal.getReceives(), borrowerServer.getReceives());
  }

  // Test borrower's search history 
  public void testSearchHistory() {
    User borrower = new User("username");
    Service service = new Service("test");
    borrower.getHistoryList().add(service);
    assertFalse(borrower.getHistoryList().size(), 0);
  }
}
