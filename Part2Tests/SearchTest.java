import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by murdock on 10/8/15.
 */
public class SearchTest extends ActivityInstrumentationTestCase2 {
    public FriendTest() {
        super(com.ualberta.murdock.project.MainActivity.class);
    }

    public void testServiceList() {
        Service service = new Service();
        ServiceList servicelist = new ServiceList();
        servicelist.add(service);
        assertTrue(servicelist.contains(service));
        assertFalse(servicelist.contains(notservice));
    }

    public void testCategorySearch() {
        Service service = new Service();
        service.setcategory("Mechanical");
        ServiceList servicelist = new ServiceList();
        servicelist.add(service);
        assertTrue(servicelist.contains(service));
        assertEquals(servicelist.CategorySearch(Mechanical), service);
    }

    public void testTextQuery() {
        Service service = new Service();
        service.setname("Carpenter");
        ServiceList servicelist = new ServiceList();
        servicelist.add(service);
        assertTrue(servicelist.contains(service));
        assertEquals(servicelist.TextquerySearch(Carpenter), service);
    }

    public void testBrowseInventory() {
        User user = new User();
        Service service = new Service();
        user.addService(service);
        assertTrue(user.servicelist.browse(),service);
    }

    public void testSearchInventory() {
        User user = new User();
        Service service = new Service();
        service.setcategory("Mechanical");
        service.setname("Knitting");
        user.addService(service);
        assertTrue(user.servicelist.TextquerySearch(Knitting),service);
        assertTrue(user.servicelist.CategorySearch(Mechanical),service);
    }   
}
