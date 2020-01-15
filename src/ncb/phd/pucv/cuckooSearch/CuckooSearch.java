package ncb.phd.pucv.cuckooSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.BadBinaryOpValueExpException;

//import kMeans.Punto;
import ncb.phd.pucv.cuckooSearch.dbscan.*;
import ncb.phd.pucv.cuckooSearch.dbscan.metrics.DistanceMetricPunto;
import ncb.phd.pucv.KnapSack.Knapsack;


//import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Nicolás Caselli Benavente Phd Informática PUCV
 */
public class CuckooSearch{

	private  int cantidadNidos; //número de población de nidos(soluciones), tambien es el número de filas
	private  int cantidadColumnas;
	private  float probDescubrimiento;  //Probabilidad de descubrir el nido(solución) por la especie dueña de este.
	private  int cantidadIteraciones; //Número de iteraciones del ciclo de búsqueda.
	private  String tipoBinarizacion, tipoDiscretizacion;
	private  int nidos[][];
	private  float fitnessNidos[];
	private  float bestFit;
	private  long tiempoInicioEjecucion, tiempoInicioGlobal, tiempoTermino;
	private  long semilla;
	private  Map<Integer, String> mNombresInstancias = new HashMap<Integer, String>(); 
	private  Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
	private  Knapsack mochila;
	private int cantClusters;
	private float pesosClusters[];
	private int cantMinimaElementosCluster;
	private double distanciaMaximaElementos;
	private double fitnessTotalActual;
	private double fitnessTotalAnterior;
	/**
	 * Cantidad mínima de elementos que pueden comprender un cluster
	 * @return cantMinimaElementosCluster
	 */
	public int getCantMinimaElementosCluster() {
		return cantMinimaElementosCluster;
	}
	/**
	 * Establece la cantidad mínima de puntos que formarán un cluster
	 * @param cantMinimaElementosCluster
	 */
	public void setCantMinimaElementosCluster(int cantMinimaElementosCluster) {
		this.cantMinimaElementosCluster = cantMinimaElementosCluster;
	}
	
	/**
	 * Obtiene la distancia máxima a buscar elementos
	 * @return distanciaMaximaElementos
	 */
	public double getDistanciaMaximaElementos() {
		return distanciaMaximaElementos;
	}

	/**
	 * Obtiene la distancia máxima que se considerarán para buscar puntos cercanos
	 * @param distanciaMaximaElementos
	 */
	public void setDistanciaMaximaElementos(double distanciaMaximaElementos) {
		this.distanciaMaximaElementos = distanciaMaximaElementos;
	}
    /**
     * {@code logger} = Log de errores
     */
    private static final Logger logger = Logger.getLogger(CuckooSearch.class.getName());

	/**
	 * {@code input} = ubicacion del archivo SCP {@code output} = Nombre y
	 * ubicacion del archivo de resultados
	 */
	private String inPut;
	/**
	 * {@code outPut} = ubicacion del archivo SCP {@code output} = Nombre y
	 * ubicacion del archivo de resultados
	 */
	private String outPut;

	public long getSemilla() {
		return semilla;
	}

	public void setSemilla(long seed) {
		semilla = seed;
	}

	public String getInPut() {
		return inPut;
	}

	public void setInPut(String inP) {
		inPut = inP;
	}

	public String getOutPut() {
		return outPut;
	}

	public void setOutPut(String outP) {
		outPut = outP;
	}

	public long getTiempoInicioEjecucion() {
		return tiempoInicioEjecucion;
	}

	public void setTiempoInicioEjecucion(long tiempInicioEjec) {
		tiempoInicioEjecucion = tiempInicioEjec;
	}

	public long getTiempoInicioGlobal() {
		return tiempoInicioGlobal;
	}

	public void setTiempoInicioGlobal(long tiempInicioGlobal) {
		tiempoInicioGlobal = tiempInicioGlobal;
	}

	public long getTiempoTermino() {
		return tiempoTermino;
	}

	public void setTiempoTermino(long tiempTermino) {
		tiempoTermino = tiempTermino;
	}
//
//	public String getTipoBinarizacion() {
//		return tipoBinarizacion;
//	}

	CuckooSearch(int cantNidos, float probabilidad,  int numeroiteraciones,
						Map<Integer, String> mInstanciasAprocesar, 
						Map<Integer, Integer> mMejoresFitsInstancias,
						String discretizacion,
						String binarizacion,
						int [] vectorCostos,
						int [] matrizRestricciones[],
						int numFilas,
						int numColumnas) { //Constructor
		cantidadNidos = cantNidos;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = binarizacion;
		mNombresInstancias = mInstanciasAprocesar;
		mBestFistInstancias = mMejoresFitsInstancias;
		nidos = null;
//		costos = vectorCostos;
//		restricciones = matrizRestricciones;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		cantidadColumnas = numColumnas;
		cantidadNidos = numFilas;
		pesosClusters = new float[] {0.02f, 0.04f, 0.06f};
		cantClusters = 3;
		
		

	}
	
	CuckooSearch(int cantNidos, float probabilidad,  int numeroiteraciones, Knapsack mochilaEntrada, int cantClusters) { //Constructor
		cantidadNidos = cantNidos;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		this.cantClusters= cantClusters;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidos = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		pesosClusters = new float[] {0.02f, 0.04f, 0.06f};
		

	}
	/**
	 * Constructor para Knapsack
	 * @param cantNidos
	 * @param probabilidad
	 * @param numeroiteraciones
	 * @param mochilaEntrada
	 * @param discretizacion
	 * @param binarizacion
	 */
	CuckooSearch(int cantNidos, float probabilidad,  int numeroiteraciones, Knapsack mochilaEntrada, String discretizacion, String binarizacion) { 
		cantidadNidos = cantNidos;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = binarizacion;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidos = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		

	}
	
	CuckooSearch(int cantNidos, float probabilidad,  int numeroiteraciones, Knapsack mochilaEntrada) { 
		cantidadNidos = cantNidos;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = null;
		tipoBinarizacion = null;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidos = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		

	}

	public void setBestFit (int mejorFit)
	{
		bestFit = mejorFit;
	}

	public float getBestFit() {
		return bestFit;
	}

	public void setInput(String input) {
		this.inPut = input;
	}

	public void setOutput(String output) {
		this.outPut = output;
	}
//
//	public void setTipoBinarizacion(String tipBinarizacion) {
//		tipoBinarizacion = tipBinarizacion;
//	}

	public int getNum_nidos() {
		return cantidadNidos;
	}
	public void setNum_nidos(int num_nidos) {
		cantidadNidos = num_nidos;
	}
	public float getProbDescubrimiento() {
		return probDescubrimiento;
	}
	public void setProbDescubrimiento(float probDescub) {
		probDescubrimiento = probDescub;
	}

	public int getNumIteraciones() {
		return cantidadIteraciones;
	}
	public void setNumIteraciones(int n_iter_total) {
		cantidadIteraciones = n_iter_total;
	}
	public int[][] getNidos() {
		return nidos;
	}
	public void setNidos(int[][] nest) {
		nidos = nest;
	}
	/*Este método inicializa las soluciones de manera binaria y aleatoria*/
	private int[][] inicializacionSoluciones() throws FileNotFoundException, IOException {
		Random rnd = new Random(System.nanoTime());
		int nest[][] = new int[cantidadNidos][cantidadColumnas];
		for (int i = 0; i < cantidadNidos; i++) {
			do {
				for (int j = 0; j < cantidadColumnas; j++) {
					
					nest[i][j] = rnd.nextInt(2);
					
				}
			}while (!esFactible(nest[i]));
		}
		return nest;
	}
	
	// Function to calculate hamming distance 
	static int hammingDistance(int n1, int n2) 
	{ 
	    int x = n1 ^ n2; 
	    int setBits = 0; 
	  
	    while (x > 0)  
	    { 
	        setBits += x & 1; 
	        x >>= 1; 
	    } 
	  
	    return setBits; 
	} 
	
	private int[][] clusterizaSoluciones (float[][] fitnessSoluciones )
	{
		
		Map<Integer, Float> aClusterCentroide = new  HashMap <Integer, Float>();
		Map<Integer, List<Punto>> mClusterPuntos = new HashMap <Integer, List<Punto>>();
		Map <Integer, Float> mFactorCluster = new HashMap <Integer, Float>();
		ArrayList<Punto> puntosRuido = new ArrayList<Punto>();
		Random rnd = new Random();
		int[][] solucionClusterizada = new int[fitnessSoluciones.length][cantidadColumnas];
		//traspaso los elementos de mi solucion a lista, para que kmeans lo soporte
		List<Punto> puntos = new ArrayList<Punto>();
		float pesoTransicion = 0.2F;

//		 System.out.print("\nCantidad de elementos en la solucion: "+ solucion.length);
		for (int fil = 0; fil< fitnessSoluciones.length; fil++) {
			for (int col = 0; col< fitnessSoluciones[0].length; col++) {
				if (fitnessSoluciones[fil][col] != 0 && fitnessSoluciones[fil][col] != 1)
				{
					Float[] solucion = new Float[1];
					solucion[0] = fitnessSoluciones[fil][col];
					Punto p = new Punto(solucion, fil, col);
					puntos.add(p);
				} 
			}
		}
//		if (puntos.size() < 1 ) {
//			System.out.print("\n Nada que clusterizar, retornando\n");
//			return fitnessSoluciones;
//		}
		int minCluster = cantidadColumnas/4;
        double maxDistance = 0.009;
        
        DBSCANClusterer<Punto> clusterer = null;
        try {
            clusterer = new DBSCANClusterer<Punto>(puntos, minCluster, maxDistance, new DistanceMetricPunto());
        } catch (DBSCANClusteringException e1) {
            System.out.println("Should not have failed on instantiation: " + e1);
        }
        
        ArrayList<ArrayList<Punto>> result = null;
        
        try {
            result = clusterer.performClustering();
            int conteoCluster = 0;
        	float sumValPuntos = 0;

            //Recorremos los clusteres para obtener el promedio y asignar peso
            for (ArrayList<Punto> arrayPnt: result) {
            	conteoCluster++;
            	sumValPuntos = 0;
            	mClusterPuntos.put(conteoCluster, arrayPnt);
            	for (int dim = 0; dim < arrayPnt.size(); dim++) {
            		sumValPuntos += Float.parseFloat(arrayPnt.get(dim).toString());
            	}
            	mFactorCluster.put(conteoCluster, sumValPuntos/arrayPnt.size());
            }
            puntosRuido = clusterer.getNoiseValues();
            if(puntosRuido.size() > 0)
            {
            	sumValPuntos = 0;            	
                conteoCluster++;
                //Agregamos los puntos ruidos
            	mClusterPuntos.put(conteoCluster, puntosRuido);
                // asignamos peso a los puntos ruidos
                for (int nosie = 0; nosie < puntosRuido.size(); nosie++) {
            		sumValPuntos += Float.parseFloat(puntosRuido.get(nosie).toString());
            	}
            	mFactorCluster.put(conteoCluster, sumValPuntos/puntosRuido.size());
            }
            
        	//ahora ordenamos los promedios de cada cluster
        	mFactorCluster = sortByValue(mFactorCluster);
        	//a cada promedio le asignamos si peso de cambio
        	pesoTransicion = 0.2F/conteoCluster;
            System.out.println("\nfactor de transicion inicial " + pesoTransicion);
            System.out.println("\nTotal Clusteres " + conteoCluster);

    		for(Map.Entry<Integer, Float> entry: mFactorCluster.entrySet())
    		{
    			for (Punto punto: mClusterPuntos.get(entry.getKey()) )
    			{
					solucionClusterizada[punto.getPosicionFila()][punto
							.getPosicionColumna()] = rnd.nextFloat() >= pesoTransicion
									? Math.abs(nidos[punto.getPosicionFila()][punto.getPosicionColumna()] - 1)
									: nidos[punto.getPosicionFila()][punto.getPosicionColumna()];
    				
    			}
    			pesoTransicion+=pesoTransicion;
    		}
            
        } catch (DBSCANClusteringException e) {
            System.out.println("Should not have failed while performing clustering: " + e);
            
        }
		return solucionClusterizada;
	}
	
	
	public static <K, V> void printMap(Map<K, V> map) {
	        for (Map.Entry<K, V> entry : map.entrySet()) {
	            System.out.println("Key : " + entry.getKey()
	                    + " Value : " + entry.getValue());
	        }
	    }
	 
	private static Map<Integer, Float> sortByValue(Map<Integer, Float> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Float>> list =
                new LinkedList<Map.Entry<Integer, Float>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Float>>() {
            public int compare(Map.Entry<Integer, Float> o1,
                               Map.Entry<Integer, Float> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Float> sortedMap = new LinkedHashMap<Integer, Float>();
        for (Map.Entry<Integer, Float> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }

	
	/**
	 * Funcion que deja el valor del fitnes en 10^10
	 * @param fitness
	 */
	private void inicializacionFitness(float fitness[]) {
		for (int i = 0; i < cantidadColumnas; i++) {
			fitness[i] = 0;
		}
	}

	private static double erf(double z) {

		double q = 1.0 / (1.0 + 0.5 * Math.abs(z));

		double ans = 1 - q * Math.exp(-z * z - 1.26551223
				+ q * (1.00002368
						+ q * (0.37409196
								+ q * (0.09678418
										+ q * (-0.18628806
												+ q * (0.27886807
														+ q * (-1.13520398
																+ q * (1.48851587
																		+ q * (-0.82215223
																				+ q * (0.17087277))))))))));

		return z >= 0 ? ans : -ans;
	}

	private double binarizacion(float f, Random rnd, String tipoBinarizacion) throws FileNotFoundException, IOException {
		
		switch (tipoBinarizacion){
			case "sshape1":
				return (1/(1+Math.pow(Math.E,-2*f)));
				
			case "sshape2":
				return (1 / (1 + Math.pow(Math.E, -f)));
				
			case "sshape3":
				return (1/(1+Math.pow(Math.E,-1*f/2)));
				
			case "sshape4":
				return (1/(1+Math.pow(Math.E,-1*f/3)));
	
			case "vshape1":
				return Math.abs(erf((Math.sqrt(Math.PI) / 2) * f));
	
			case "vshape2":
				return Math.abs((float)Math.tan(f));
//				return rnd.nextFloat() <= Math.abs((float)Math.tan(f)) ? 1:0;
	
			case "vshape3":
				return Math.abs(f/Math.sqrt(1+Math.pow(f, 2)));
	
			case "vshape4":
				return Math.abs(f/Math.sqrt(1+Math.pow(f, 2)));
	
			case "vshape5":
				return Math.abs(2/Math.PI*Math.atan(Math.PI/2*f));        	
			case "basic":
				return 0.5 <= f ? 1 : 0;
			default:
				return 0;

		}

	}

	private int discretizacion(double x, Random rnd, String tipoDiscretizacion) throws FileNotFoundException, IOException {
		double alpha = 0.4;
		switch (tipoDiscretizacion){
			case "standard":
				return rnd.nextFloat() <= x ? 1 : 0;
			case "complement":
				return rnd.nextFloat() <= x ? discretizacion(1-x, rnd,  "standard" ) : 0;
			case "staticProbability":
				return alpha >= x ? 0 : (alpha < x && x <= ((1 + alpha) / 2)) ? discretizacion(x, rnd, "standard" ) : 1;
			case "elitist":
				return rnd.nextFloat() < x ? discretizacion(x, rnd, "standard" ) : 0;
			default:
				return 0;

		}

	}

	/**
	 * Evalua la F.O. en todos los nidos, y devuelve el índice del nido con mejor fitnes entre todos
	 * @param nidos
	 * @param nuevosnidos
	 * @param fitness
	 * @return
	 */
	private int obtenerMejorNido(int nidos[][], int nuevosnidos[][], float fitness[]) {
		int mejorposicion = -1;
		float costo = 0;
		float aux = -1000000000;
		fitnessNidos = new float[cantidadNidos];
		//todo: revisar como obtener el mejor nido de todos, acorde al problema del knapsack
		for (int i = 0; i < cantidadNidos; i++) { //evaluando todos los "nuevos nidos" en la f.o y comparandolos con el anterior!
			costo  = funcionObjetivo(nuevosnidos[i]);
			fitnessNidos[i] = costo;
	
			if (costo > fitness[i]) {
				nidos[i] = nuevosnidos[i];
				fitness[i] = costo;
			}
			costo = 0;
		}
		for (int i = 0; i < fitness.length; i++) { //obteniendo el mejor nido
			if (aux < fitness[i]) {
				aux = fitness[i];
				mejorposicion = i;
			}
		}
		return mejorposicion;
	}
	
	/**
	 * Genera de manera aleatorea los  valores para los nidos, para cada valor los binariza, luego repara los valores que violan restricciones.    
	 * @param nidos
	 * @param posicionmejor
	 * @param restricciones
	 * @param costos
	 * @param fitness
	 * @param rnd
	 * @param alfa
	 * @param tipoBinarizacion
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private int[][] generarNuevasSoluciones(int nidos[][], int posicionmejor, Random rnd, float alfa) throws FileNotFoundException, IOException {
//		int solucionCorrecta = 0;
		float nuevosnidos[][] = new float[cantidadNidos][cantidadColumnas];
//		int nidosClusterizados[][] = new int[cantidadNidos][cantidadColumnas];

//		int nuevosnidos[][] = new int[cantidadNidos][cantidadColumnas];
//		
//		float beta = (float) 3 / 2;
		float sigma;
		float[] paso = new float[cantidadColumnas];
		float[] u = new float[cantidadColumnas];
		float[] v = new float[cantidadColumnas];
		//sigma = (float) Math.pow(((Gamma.gamma((float)1+beta)*Math.sin(Math.PI*beta/2)))/(Gamma.gamma(((float)1+beta)/2)*beta*Math.pow(2,(float)(beta-1)/2)),(float)(1/beta));//(1/beta));               
		sigma = (float) 0.69657;
		for (int i = 0; i < cantidadColumnas; i++) {
			u[i] = (float) (rnd.nextGaussian() * sigma);
			v[i] = (float) rnd.nextGaussian();
			paso[i] = (float) (float) u[i] / (float) Math.abs(v[i]);
			paso[i] = (float) Math.pow(paso[i], 2);
			paso[i] = (float) Math.cbrt(paso[i]);
		}
		for (int i = 0; i < cantidadNidos; i++) {
				for (int j = 0; j < cantidadColumnas; j++) {
					//if(i != posicionmejor){
					float valor = (float) (nidos[i][j] + ((float) ((float) alfa * (float) paso[j]) * (nidos[posicionmejor][j] - nidos[i][j]) * rnd.nextGaussian()));
					//this.test(valor,0);
					if (valor < 0) { //LOWER BOUNDS
						nuevosnidos[i][j] = 0.000000000001F;
					} else if (valor > 1) {//UPPER BOUNDS
						nuevosnidos[i][j] = 0.999999999999F;
					} else {
						nuevosnidos[i][j] = valor;
						//obtenemos la velocidad
//						nuevosnidos[i][j] = this.discretizacion(this.binarizacion(valor, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);

					}
				}	
		}
		//acá deberemos clusterizar
		return reparaKnapSack(clusterizaSoluciones(nuevosnidos));
	}
	
	/**
	 * similar a generarNuevasSoluciones, crea nidos nuevos de maner aleatorea, binariza luego repara.
	 * @param nidos
	 * @param posicionmejor
	 * @param restricciones
	 * @param rnd
	 * @param alfa
	 * @param costos
	 * @param tipoBinarizacion
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private int[][] nidosVacios(int nidos[][], Random rnd) throws FileNotFoundException, IOException {
		float nuevosnidos[][] = new float[cantidadNidos][cantidadColumnas];
		
		for (int i = 0; i < cantidadNidos; i++) {
			int filaRandom1 = rnd.nextInt(cantidadNidos);
			int filaRandom2 = rnd.nextInt(cantidadNidos);

			if (rnd.nextFloat() > probDescubrimiento) { //si la especie dueña del nido descubre el huevo ajeno, se va a otro nido.
				for (int j = 0; j < cantidadColumnas; j++) {
					float valor = (float) (nidos[i][j]
							+ ((float) rnd.nextFloat() * (nidos[filaRandom1][j] - nidos[filaRandom2][j])));
					if (valor < 0) { // LOWER BOUNDS
						nuevosnidos[i][j] = 0.000000000001F;
					} else if (valor > 1) {// UPPER BOUNDS
						nuevosnidos[i][j] = 0.999999999999F;
					} else {
//							nuevosnidos[i][j] = this.discretizacion(this.binarizacion(valor, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
						nuevosnidos[i][j] = valor;
					}
				}
			} else {
				for(int j = 0; j < cantidadColumnas; j++)
				{
					nuevosnidos[i][j] = (float)nidos[i][j];
				}
			}
		}
		return reparaKnapSack(clusterizaSoluciones(nuevosnidos));
	}

	/**
	 * Calcula el costo de la mochila, apra un nido en particular
	 * @param nido el vector con la solución propuesta.
	 * @return el valor de la suma de todos los elementos considerados en la mochila.
	 */
	private float funcionObjetivo(int nido[]) {
		int costo = 0;
		for (int j=0; j< mochila.getObjetos().size(); j++)
			  costo += nido[j] * (int) (mochila.getObjetos().get(j).getValor());
//		mochila.setCostoActual(costo);
		return costo;
	}
   
	public  boolean esFactible(int nido[]) {
    	double peso = 0;
        for (int j = 0; j < mochila.getObjetos().size(); j++) {
            peso += nido[j] * mochila.getObjetos().get(j).getPeso();
        }
//        mochila.setPesoActual((int)peso);
        return peso <= mochila.getCapacidadmochila();
	}
	
	public  double pesoActualIncluido(int nido[]) {
    	double peso = 0;
        for (int j = 0; j < mochila.getObjetos().size(); j++) {
            peso += nido[j] * mochila.getObjetos().get(j).getPeso();
        }
//        mochila.setPesoActual((int)peso);
        return peso;
	}
	
	public int[][] reparaKnapSack(int[][] badKnapSack)
	{
		int[][] fixedKnapSack = badKnapSack;
		int colMayorPeso, colMenorPeso;
		for (int filas = 0; filas < badKnapSack.length; filas++)
		{
			if (!esFactible(fixedKnapSack[filas]))
			{
				colMayorPeso = obtienePosicionMayorPesoMochila(fixedKnapSack[filas]);
				fixedKnapSack[filas][colMayorPeso] = 0;
				filas--;
			}
		}
		//quitamos los elementos más pesados, y ahora agragamos los elementos menos pesados
		for (int filas = 0; filas < badKnapSack.length; filas++) {
			
			colMenorPeso = obtienePosicionMenorPesoNoIncluidoEnMochila(fixedKnapSack[filas]);
			if((mochila.getObjetos().get(colMenorPeso)).getPeso()+ pesoActualIncluido(fixedKnapSack[filas])<= mochila.getCapacidadmochila()) {
				fixedKnapSack[filas][colMenorPeso] = 1;
				filas--;
			}
					
			
		}
		
		
		return fixedKnapSack;
	}
	
	public int obtienePosicionMayorPesoMochila(int[] vectorSolucion)
	{
		//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento

		double pesoMayor = -999999999, pesoEvaluado = 0;
		int posicionPesoMayor = 0;
		for(int col = 0; col< vectorSolucion.length; col++)
		{
			
			if(vectorSolucion[col] == 1) {
				
				pesoEvaluado = (mochila.getObjetos().get(col).getPeso()/mochila.getObjetos().size()*(mochila.getCapacidadmochila() - pesoActualIncluido(vectorSolucion)))/mochila.getObjetos().get(col).getValor();
				
				if(pesoEvaluado >= pesoMayor) {
					pesoMayor = pesoEvaluado; 
					posicionPesoMayor = col;
					//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento
				}
			}
		}
		return posicionPesoMayor;
	}
	
	public int obtienePosicionMenorPesoNoIncluidoEnMochila(int[] vectorSolucion)
	{
		//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento

		double pesoMenor = 999999999, pesoEvaluado = 0;
		int posicionMayorPeso = 0;
		for(int col = 0; col< vectorSolucion.length; col++)
		{
			
			if(vectorSolucion[col] == 0) {
				
				pesoEvaluado = (mochila.getObjetos().get(col).getPeso()/mochila.getObjetos().size()*(mochila.getCapacidadmochila() - pesoActualIncluido(vectorSolucion)))/mochila.getObjetos().get(col).getValor();
				
				if(pesoEvaluado <= pesoMenor) {
					pesoMenor = pesoEvaluado; 
					posicionMayorPeso = col;
					//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento
				}
			}
		}
		return posicionMayorPeso;
	}
	
	/* funciones relacionadas al manejo de instancias y escritura de resultados
	 * en archivos planos, para utilizarlos posteriormente
	 * */

	 private static String Write(int b, Object o) {
	        String line = "";
	        for (int i = 0; i < b - String.valueOf(o).length(); i++) {
	            line += " ";
	        }
	        return (line + o + " |");
	    }
	/**
	 * escribe los resultados de cada ejecución en @filename	
	 * @param ejecucion es el número de la ejecucion
	 * @param fileName es el archivo dónde se escriben los resultados
	 * @param knowFitInstancia es el mejor resultado conocido de dicha instancia
	 * @param nombreInstancia es el nombre del archivo OR utilizado
	 */
	private void SaveLine_SCP(int ejecucion, String fileName, int posicionmejor) {
//			|Nº EJECUCION | NIDOS | BINARIZACION  |ITERACIONES|  SEMILLA     | MEJOR FITNESS |    FITNESS FOUND| TIME |\n");
	        BufferedWriter bufferedWriter = null;
	        File file;
	        String line = "|";

	        try {
	            file = new File(fileName);
	            boolean exists = file.exists();

	            if (!exists) {
	                (new File(file.getParent())).mkdirs();
	            }

	            bufferedWriter = new BufferedWriter(new FileWriter(file, true));

	            try (PrintWriter pw = new PrintWriter(bufferedWriter)) {
	                if (!exists) {
	                	line += Write(2, "#") + 
	                    		Write(5, "Nest") + 
	                    		Write(6, "%Desc") + 
	                    		Write(5, "#Iter") + 
//	                    		Write(8,"tipoDiscretizacion") + //%-17s
//		                		Write(8,"tipoBinarizacion") + //%-12s 
		                		Write(8,"Instancia")+ 
		                		Write(8,"CapMoch") + //%-10s
		                		Write(8, "ValOptmo") ; //%-10s
	                    		
//	                    		Write(8, "BestFit");
	                    
	                    line += Write(8, "BestFit") + 
	                    		Write(8, "RDP") + 
	                    		Write(5, "time") +
	                    		Write(13, "semilla") + 
	                    		Write(5, "VectorSolucion");
	                    pw.println(line);
	                    line = "";
	                    for (int i = 0; i < 97; i++) {
	                        line += "-";
	                    }
	                    pw.println(line);
	                    line = "|";
	                }


	                line += Write(2, ejecucion) + // %-4s 
	                		Write(5, cantidadNidos) + //%-5s
	                		Write(6, probDescubrimiento) +  
	                		Write(5, cantidadIteraciones) + // %-4s
//	                		Write(8,tipoDiscretizacion) + //%-17s
//	                		Write(8,tipoBinarizacion) + //%-12s 
	                		Write(8,mochila.getNombreInstancia()) + //%-9s 
	                		Write(8,mochila.getCapacidadmochila()) + //%-10s
	                		Write(8, mochila.getValorOptimo()) + //%-10s
	                		Write(8, bestFit)+ //%-10s
	                		Write(8, String.format("%.2f", Math.abs((bestFit- mochila.getValorOptimo())/mochila.getValorOptimo()*100)))+ //%-5s
	                		Write(5, (String.format("%.2g%n", (tiempoTermino-tiempoInicioEjecucion) / (1000f))).trim()); //%-5s
	                line += Write(13, semilla) + //%-13s
	                		Write(5, Arrays.toString(nidos[posicionmejor])); //%-60s
	                pw.println(line);
	            }
	        } catch (IOException ex) {
	            logger.log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bufferedWriter != null) {
	                    bufferedWriter.close();
	                }
	            } catch (IOException ex) {
	                logger.log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	@Override
	public String toString() {
		return ("Numero de nidos:" + this.cantidadNidos + "\n" + "Numero de dimensiones:" + this.cantidadColumnas + "\n" + "Operador probabilidad:" + this.probDescubrimiento + "\n" + "Numero de Iteraciones:" + this.cantidadIteraciones + "\n");
	}

	
	public void ejecutar() {
//		int repActual = 1;//Integer.parseInt(args[0]);
		int posicionmejor, i = 0;
		int nuevosnidos[][] = null;

		float alfa = (float) 0.01;
		try {
//			 System.out.print("en Clase Cuckoo:\n NumeroFilas:"+ numeroFilas +"numeroCol"+ numeroColumnas + "costos:"+costos.length);
			//"| %-4s 		| %-5s 	| %-4s | %-17s 			   | %-12s 		  | %-9s 	  | %-10s 	   | %-10s 		| %-10s 	 | %-5s  | %-5s  | %-13s 	     | %-60s | \n"
			System.out.print(
					"| EXEC | NIDOS | ITER  | INSTANCIA | CAP. MOCH. | MEJOR FIT. | FIT. FOUND |  RDP  | TIME  |    SEMILLA    |                         VEC. SOLUCION                        |\n");
			System.out.print(
					"|__________________________________________________________________________________________________________________________________________________________________________\n");
			for (int numEjecucion = 1; numEjecucion <= 31; numEjecucion++) {

//					int mejorIteracion = 0;
				Random rnd;
				rnd = new Random();

				tiempoInicioEjecucion = System.currentTimeMillis();
				semilla = new Date().getTime();
				rnd.setSeed(semilla);
				float fitness[] = new float[cantidadNidos];

				nidos = this.inicializacionSoluciones(); // Inicializar nidos o soluciones || con valores aleatoreos
				inicializacionFitness(fitness); // Inicializar el vector con las mejores soluciones para los nidos
												// i-ésimos || laprimera vez todos los fitness en 10^10
				posicionmejor = this.obtenerMejorNido(nidos, nidos, fitness);
				fitnessTotalActual = calculaRendimientoPoblacion(fitnessNidos);
				fitnessTotalAnterior = fitnessTotalActual; 
				// hasta aquí solo inicializó la primera vez de evaluaciones, con valores
				// aleatorios y fitness altísimos para poder dejar los valores iniciales.

				int auxPosicion = posicionmejor;
				bestFit = fitness[posicionmejor];
				i = 1;

				while (i <= cantidadIteraciones) { // Aqui comienza a iterar el algoritmo CS.
					nuevosnidos = generarNuevasSoluciones(nidos, posicionmejor, rnd, alfa); // Aqui se generarán nuevas
																							// soluciones aplicando LF
					posicionmejor = obtenerMejorNido(nidos, nuevosnidos, fitness);

					if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
						auxPosicion = posicionmejor;
						bestFit = fitness[posicionmejor];
					}
					i++;
					nuevosnidos = nidosVacios(nidos, rnd);
					
					posicionmejor = obtenerMejorNido(nidos, nuevosnidos, fitness);
					if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
						auxPosicion = posicionmejor;
						bestFit = fitness[posicionmejor];
					}
					
//					evolucionarCuckooSearch();
					
				}

				mochila.setValorOptimoObtenido(((int) funcionObjetivo(nidos[posicionmejor])));

				if (!esFactible(nidos[posicionmejor]))
					System.out.print("\n\nERROR EN PASO DE UNA O MAS RESTRICCION\n");

				tiempoTermino = System.currentTimeMillis();

				System.out.printf("| %-4s | %-5s | %-4s | %-9s | %-10s | %-10s | %-10s | %-5s | %-5s | %-13s | %-60s | \n"
						,String.format("%02d", numEjecucion)
						,cantidadNidos
						,cantidadIteraciones
//						,tipoDiscretizacion
//						,tipoBinarizacion
						,mochila.getNombreInstancia()
						,mochila.getCapacidadmochila()
						,mochila.getValorOptimo()
						,(int) bestFit
						,String.format("%.2f",Math.abs((bestFit - mochila.getValorOptimo()) / mochila.getValorOptimo() * 100))
						,String.format("%.2f", (float) (tiempoTermino - tiempoInicioEjecucion) / 1000 )
						,semilla
						,Arrays.toString(nidos[posicionmejor]));

				/* guardamos resultados en archivo plano */
				SaveLine_SCP(numEjecucion,
						"resources/output/Salida_KnapSack_Nidos_" + cantidadNidos + "Iter_" + cantidadIteraciones
								+ "DBSCAN"
								+ "Instancia_" + mochila.getNombreInstancia(), posicionmejor);

			}
			System.out.println("Ejecuciones terminadas en: " + (tiempoTermino - tiempoInicioGlobal) / 3600000 + ":"
					+ ((tiempoTermino - tiempoInicioGlobal) % 3600000) / 60000 + ":"
					+ ((tiempoTermino - tiempoInicioGlobal) % 3600000) % 60000);


		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}
	
	
	private void evolucionarCuckooSearch() {

		fitnessTotalActual = calculaRendimientoPoblacion(fitnessNidos);
		
		//realizamos DBSCAN de soluciones
//		clusterizaSoluciones(fitnessNidos);
		if (fitnessTotalActual>fitnessTotalAnterior)
		{
			//entonces podemos achicar la población
		}else if(fitnessTotalActual == fitnessTotalAnterior){
			//entonces podemos 
		}else {
			//Entonces podemos agrandar la población
		}
		fitnessTotalAnterior = fitnessTotalActual; 
		
	}
	
	
	private double calculaRendimientoPoblacion(float[] fitnessSoluciones) {
		double result = 0;
		for (float val=0; val < fitnessSoluciones.length;val++)
			result +=val;
		return result/fitnessSoluciones.length;
	}

}
