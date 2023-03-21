public class Device {
    private String name;
    private boolean state;

    public Device(String name) {
        this.name = name;
        this.state = false;
    }

    public String getName() {
        return name;
    }
    
    public boolean getState(){
        return this.state;
    }
    
    public void setState(boolean stateNovo){
        this.state = stateNovo;
    }
    
    public String stateToString(){
        if(this.state==false){
            return "off";
        } else{
            return "on";
        }
    }
    
    @Override
    public String toString() {
        return name + " - " + this.getState();
    }
}
