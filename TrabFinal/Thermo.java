
/**
 * Classe para dispositivos com temperatura controlada (ares condicionados, fornos, geladeiras, etc.)
 *
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */
public class Thermo extends Device
{
    /** Variaveis de instancia da classe Thermo */
    private int temperature;   // Temperatura que o dispositivo se encontra agora
    private int tMin = -20;    // Temperatura minima que o dispositivo pode ter. Padrao: -20 (Minima de um freezer)
    private int tMax = 300;    // Temperatura maxima que o dispositivo pode ter. Padrao: 300 (maxima de um forno)
    private int avgTemp;       // Temperatura media entre a minima e a maxima.
    /**
     * Construtor para objetos da classe Thermo
     * @param nome (String), tMin (int), tMax (int), timer (boolean)
     */
    public Thermo(String name, int tMin, int tMax, boolean timer)
    {
        super(name, timer);     
        this.tMax = tMax;
        this.tMin = tMin;
        this.avgTemp = (tMin+tMax)/2;
        this.temperature = avgTemp;
    }

    /** Getters e Setters */
    public int getTemp(){
        return this.temperature;
    }
    
    public void setTemp(int temp){
        this.temperature = temp;    
    }
    
    public int getTMin(){
        return this.tMin;
    }
    
    public void setTMin(int t){
        this.tMin = t;
    }
    
    public void setTMax(int t){
        this.tMax = t;
    }
    
    public int getTMax(){
        return this.tMax;
    }
    /** Fim dos getters e setters */
    
    /** Metodo toString da classe Thermo.
     * @returns String com os dados mostrados pela toString do Device + a Temperatura do Device
       */
    public String toString() {
        return super.toString() + " - T:" + this.temperature;
    }
}
