package ca.ualberta.cs.xpertsapp.datamanagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.models.Service;

public class ServiceManager {

    private Map<String, Service> services = new HashMap<String, Service>();

    public void addService(Service service) {
        services.put(service.getId(), service);
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
