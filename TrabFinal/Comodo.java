import java.util.ArrayList;
import java.util.List;

public class Comodo {
    private String name;
    private List<Device> devices = new ArrayList<>();

    public Comodo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void rmDevice(Device device){
        this.devices.remove(device);
    }
    
    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public String toString() {
        return name;
    }
}
