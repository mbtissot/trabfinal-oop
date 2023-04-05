/**
 * Classe Intense implementa objetos que alem de ligar e desligar, possuem uma intensidade.
 * Exemplo: ventiladores possuem, normalmente, tres 'forças'.
 *
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */
public class Intense extends Device
{
    private int intensity; // Intensidade do dispositivo
    /**
     * Constructor for objects of class Intense
     */
    public Intense(String name, boolean timer)
    {
        super(name, timer);
        this.intensity = 0;
    }

    /** Getters e setters */
    public int getIntensity(){
        return this.intensity;
    }
    
    public void setIntensity(int intens){
        this.intensity = intens;
    }
    /**Fim dos getters e setters */
    
    /** Metodo toString mostra as informaçoes mostradas pelo toString do Device + a intensidade do Device
       */
    public String toString() {
        return super.toString() + " - I:" + this.intensity;
    }
}
