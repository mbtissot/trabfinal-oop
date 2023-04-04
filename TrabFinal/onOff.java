
/**
 * Write a description of class onOff here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class onOff extends Device
{
    public onOff(String name, boolean hasTimer){
        super(name, hasTimer);
        
    }
    
    public void countdown(){
        if(this.getTimeLeft()>0){
            this.setTimeLeft(this.getTimeLeft()-1);
        }
    }
    
    public String toString() {
        String timer = "";
        if(this.getTimer()){
            timer = timer+" - Tempo: " + this.getTimeLeft();
        }
        return super.toString() + timer;
    }
}
