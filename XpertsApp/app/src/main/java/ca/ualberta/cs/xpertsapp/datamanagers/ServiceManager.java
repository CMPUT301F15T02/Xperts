package ca.ualberta.cs.xpertsapp.datamanagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.model.Service;

public class ServiceManager {

    private Map<String, Service> services = new HashMap<String, Service>();
    private static ServiceManager instance = new ServiceManager();

    private ServiceManager() { }

    public static ServiceManager sharedManager() {
        return ServiceManager.instance;
    }

    public void addService(Service service) {
    }

    public Service getService(String id) {
        return services.get(id);
    }

    public List<Service> getServices() {
        return new ArrayList<Service>(services.values());
    }

    public void clearCache() {
        services.clear();
    }
}
