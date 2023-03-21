
/**
 * Write a description of class Thermo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Thermo extends Device
{
    // instance variables - replace the example below with your own
    private int temperature;

    /**
     * Constructor for objects of class Thermo
     */
    public Thermo(String name)
    {
        super(name);
        this.temperature = 0;
    }

    public int getTemp(){
        return this.temperature;
    }
    
    public void setTemp(int temp){
        this.temperature = temp;    
    }
    
    public String toString() {
        return super.toString() + " - T:" + this.temperature;
    }
}
