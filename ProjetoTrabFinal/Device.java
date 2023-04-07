/**
 * Classe Device eh a classe abstrata que possui todos os metodos abstratos dos tipos de Device existentes: onOff, Thermo e Intense
 * Objetos da classe Device podem ou nao ter um Timer, com metodos definidos na interface Timer.
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */
public abstract class Device implements Timer{
    private String name;       // Nome do dispositivo
    private boolean state;     // Estado do dispositivo (true/false)
    private boolean hasTimer;  // O dispositivo tem Timer? (true/false)
    private int timeLeft;      // Tempo que falta para chegar em 0 no timer.
    
    /** Construtor da classe device. Devices sao construidos desligados
     * @param name: String, timer: boolean
     */
    public Device(String name, boolean timer) {
        this.name = name;
        this.state = false;
        this.hasTimer = timer;
        this.timeLeft = 0;
    }

    /** Getters e Setters de Device*/
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
    
    public int getTimeLeft(){
        return this.timeLeft;
    }
    
    public void setTimeLeft(int newTimeLeft){
        this.timeLeft = newTimeLeft;
    }
    
    /** Metodo stateToString converte "true" para "On", e "false" para "Off".
       @returns String convertida
       */
    public String stateToString(){
        if(this.state==false){
            return "Off";
        } else{
            return "On";
        }
    }

    @Override
    /** Metodo toString da classe Device.
       @returns String com o nome do dispositivo e o estado em que ele esta (On ou Off)
       */
    public String toString() {
        String timer = "";
        if(this.getTimer()){
            timer = timer+" - Tempo: " + this.getTimeLeft();
        }
        return name + " - " + this.stateToString() + timer;
    }
    
    /** Metodo countdown diminui o timer dos dispositivos em um segundo 
       */
    public void countdown(){
        if(this.getTimeLeft()>0){
            this.setTimeLeft(this.getTimeLeft()-1);
        }
    }
}
