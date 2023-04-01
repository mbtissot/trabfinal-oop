
/**
 * Classe para dispositivos com temperatura controlada (ares condicionados, fornos, geladeiras, etc.)
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Thermo extends Device
{
    // instance variables - replace the example below with your own
    private int temperature;
    private int tMin;
    private int tMax;
    private boolean hasTimer;
    /**
     * Constructor for objects of class Thermo
     */
    public Thermo(String name, int tMin, int tMax, boolean timer)
    {
        super(name, timer);     
        this.tMax = tMax;
        this.tMin = tMin;
        this.temperature = (tMin+tMax)/2;
        
    }

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
    
    public String toString() {
        return super.toString() + " - T:" + this.temperature + " - Tempo: " + this.getTimeLeft();
    }
}
