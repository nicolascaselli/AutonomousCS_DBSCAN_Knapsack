package ncb.phd.pucv.KnapSack;
public class Objeto {

    private double valor;
    private double peso;

    public Objeto(double valor, double peso) {
        this.valor = valor;
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValorPeso() {
        return (valor / peso);
    }

    @Override
    public String toString() {
        return String.format("Valor = %s, Peso = %s", valor, peso);
    }
}
