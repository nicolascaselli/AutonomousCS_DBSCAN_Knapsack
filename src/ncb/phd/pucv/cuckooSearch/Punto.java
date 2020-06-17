package ncb.phd.pucv.cuckooSearch;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Punto {
	private Float[] data;
	private int posFila, posColumna;
	private float fitnessSolucion;

	public Punto(String[] strings) {
		super();
		List<Float> puntos = new ArrayList<Float>();
		for (String string : strings) {
			puntos.add(Float.parseFloat(string));
		}
		this.data = puntos.toArray(new Float[strings.length]);
	}

	public Punto(Float[] data) {
		this.data = data;
	}
	public Punto(Float[] data, int pos) {
		this.data = data;
		this.posFila = pos;
	}
	
	public Punto(Float[] data, int posF, int posC) {
		this.data = data;
		this.posFila = posF;
		this.posColumna = posC;
	}
	public Punto(Float[] data, int posF, int posC, float fitness) {
		this.data = data;
		this.posFila = posF;
		this.posColumna = posC;
		this.fitnessSolucion = fitness;
	}

	public float getFitnessSolucion() {
		return fitnessSolucion;
	}

	public void setFitnessSolucion(float fitnessSolucion) {
		this.fitnessSolucion = fitnessSolucion;
	}

	public int getPosicionFila() {
		return posFila;
	}
	
	public int getPosicionColumna() {
		return posColumna;
	}

	public void setPosicionFila(int posicionFila) {
		this.posFila = posicionFila;
	}
	
	public void setPosicionColumna(int posicionColumna) {
		this.posFila = posicionColumna;
	}

	public float get(int dimension) {
		return data[dimension];
	}

	public int getGrado() {
		return data.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[0]);
		for (int i = 1; i < data.length; i++) {
			sb.append(", ");
			sb.append(data[i]);
		}
		return sb.toString();
	}

	public Double distanciaEuclideana(Punto destino) {
		Double d = 0d;
		for (int i = 0; i < data.length; i++) {
			d += Math.pow(data[i] - destino.get(i), 2);
		}
		return Math.sqrt(d);
	}

	@Override
	public boolean equals(Object obj) {
		Punto other = (Punto) obj;
		for (int i = 0; i < data.length; i++) {
			if (data[i] != other.get(i)) {
				return false;
			}
		}
		return true;
	}
}
class SortbyFitness implements Comparator<Punto> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(Punto a, Punto b) 
    { 
        return (int)(a.getFitnessSolucion()-b.getFitnessSolucion()); 
    } 
}