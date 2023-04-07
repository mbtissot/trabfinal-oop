/**
 * Classe ValorInvalido eh um tipo de exceçao para quando o valor colocado num campo nao eh valido.
 * Exemplo: Timer com tempo negativo
 *
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */
public class ValorInvalido extends Throwable
{
    public ValorInvalido(String mensagem){
        super(mensagem);
    }    
}
