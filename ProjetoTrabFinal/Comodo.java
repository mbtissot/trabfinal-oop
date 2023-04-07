import java.util.ArrayList;
import java.util.List;

/**
 * Classe Comodo implementa objetos do tipo Comodo. Comodos possuem um nome, e uma lista de Devices que se encontram no comodo.
 *
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */
public class Comodo {
    private String name;       // Nome do comodo
    private List<Device> devices = new ArrayList<>(); // Lista de Devices que estao dentro do Comodo

    /**
     * Construtor para objetos da classe Comodo
     * @param nome (String)
     */
    public Comodo(String name) {
        this.name = name;
    }
    
    /** Getters e setter */
    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        return devices;
    }
    /** Fim dos getters e setters */
    
    /** Metodo addDevice adiciona um Device a lista de Devices do Comodo
       @param device (Device) */
    public void addDevice(Device device) {
        this.devices.add(device);
    }
    
    /** Metodo rmDevice remove um Device da lista de Devices do Comodo
       @param device (Device) */
    public void rmDevice(Device device){
        this.devices.remove(device);
    }
    
    /** Metodo toString da classe Comodo.
     * @returns String o nome do Comodo
       */
    public String toString() {
        return name;
    }
}
