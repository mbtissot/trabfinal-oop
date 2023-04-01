public class Device implements Timer{
    private String name;
    private boolean state;
    private boolean hasTimer;
    private int timeLeft;
    
    public Device(String name, boolean timer) {
        this.name = name;
        this.state = false;
        this.hasTimer = timer;
        this.timeLeft = 0;
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
    
    public boolean getTimer(){
        return this.hasTimer;
    }
    
    public String stateToString(){
        if(this.state==false){
            return "Off";
        } else{
            return "On";
        }
    }
    
    public int getTimeLeft(){
        return this.timeLeft;
    }
    
    public void setTimeLeft(int newTimeLeft){
        this.timeLeft = newTimeLeft;
    }
    
    @Override
    public String toString() {
        return name + " - " + this.stateToString();
    }
    
    public void countdown(int a){
        //
    }
}
