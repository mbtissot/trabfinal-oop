
/**
 * Write a description of class Intense here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Intense extends Device
{
    private int intensity;
    /**
     * Constructor for objects of class Intense
     */
    public Intense(String name, boolean timer)
    {
        super(name, timer);
        this.intensity = 0;
    }

    public int getIntensity(){
        return this.intensity;
    }
    
    public void setIntensity(int intens){
        this.intensity = intens;
    }
    
    public String toString() {
        String timer = "";
        if(this.getTimer()){
            timer = timer+" - Tempo: " + this.getTimeLeft();
        }
        return super.toString() + " - I:" + this.intensity + timer;
    }
    
    public void countdown(){
        if(this.getTimeLeft()>0){
            this.setTimeLeft(this.getTimeLeft()-1);
        }
    }
}
