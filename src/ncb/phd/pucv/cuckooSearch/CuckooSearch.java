package ncb.phd.pucv.cuckooSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.BadBinaryOpValueExpException;

//import kMeans.Punto;
import ncb.phd.pucv.cuckooSearch.dbscan.*;
import ncb.phd.pucv.KnapSack.Knapsack;


//import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Nicolás Caselli Benavente Phd Informática PUCV
 */
public class CuckooSearch{

	private  int cantNidos, cantNidosInicial; //número de población de nidos(soluciones), tambien es el número de filas
	private  int cantidadColumnas, posicionmejor, iEsimaIteracion, numEjecucion, contIteracion, contIncrementoNidos, contDecrementoNidos;
	private  float cs_probDescubrimiento, probDescubrimientoInicial, cs_alfa;  //Probabilidad de descubrir el nido(solución) por la especie dueña de este.
	private  int cantidadIteraciones, iteracionIntervencionML, cantEjecuciones; //Número de iteraciones del ciclo de búsqueda.
	private  String tipoBinarizacion, tipoDiscretizacion, idArchivo;
	private  float nidosDecimales[][], nidosBinarios[][];
	private  float fitnessNidos[], fitness[];
	private  float  bestFit, bestFitAnterior;;
	private  long tiempoInicioEjecucion, tiempoInicioGlobal, tiempoTermino;
	private  long semilla;
	private  Map<Integer, String> mNombresInstancias = new HashMap<Integer, String>(); 
	private  Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
	private  Knapsack mochila;
//	private int cantClusters;
	private int debugMode = 0;
//	private float pesosClusters[];
	private int cantMinimaElementosCluster;
	private double distanciaMaximaElementos;
	private double fitnessTotalActual;
//	private double fitnessTotalAnterior;
	private static Random rnd;
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
		cantNidos = cantNidos;
		cs_probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = binarizacion;
		mNombresInstancias = mInstanciasAprocesar;
		mBestFistInstancias = mMejoresFitsInstancias;
		nidosDecimales = null;
//		costos = vectorCostos;
//		restricciones = matrizRestricciones;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		cantidadColumnas = numColumnas;
		cantNidos = numFilas;
//		pesosClusters = new float[] {0.02f, 0.04f, 0.06f};
//		cantClusters = 3;
		
		

	}
	
	CuckooSearch(int cantNidos, float probabilidad,  int numeroiteraciones, Knapsack mochilaEntrada, int cantClusters) { //Constructor
		cantNidos = cantNidos;
		cs_probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
//		this.cantClusters= cantClusters;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidosDecimales = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
//		pesosClusters = new float[] {0.02f, 0.04f, 0.06f};
		

	}
	/**
	 * Constructor para Knapsack
	 * @param cantNidos
	 * @param probabilidad
	 * @param numeroiteraciones
	 * @param mochilaEntrada
	 * @param discretizacion
	 * @param tipoTransferFuncion
	 */
	CuckooSearch(int _cantNidos, float probabilidad,  int numeroiteraciones, 
			Knapsack mochilaEntrada, 
			String discretizacion, 
			String tipoTransferFuncion, 
			int _iteracionIntervencionML,
			int _cantEjecuciones) { 
		cantNidos = _cantNidos;
		cantNidosInicial = cantNidos;
		cs_probDescubrimiento = probabilidad;
		probDescubrimientoInicial = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = tipoTransferFuncion;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidosDecimales = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();
		tiempoTermino = 0;
		semilla = new Date().getTime();
		iteracionIntervencionML = _iteracionIntervencionML;
		cantEjecuciones = _cantEjecuciones;
		

	}
	
	CuckooSearch(int _cantNidos, float probabilidad,  int numeroiteraciones, Knapsack mochilaEntrada) { 
		cantNidos = _cantNidos;
		cs_probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		cantidadIteraciones = numeroiteraciones;
		tipoDiscretizacion = null;
		tipoBinarizacion = null;
		mochila = mochilaEntrada;
		cantidadColumnas = mochila.getObjetos().size();
		nidosDecimales = null;
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
		return cantNidos;
	}
	public void setNum_nidos(int num_nidos) {
		cantNidos = num_nidos;
	}
	public float getProbDescubrimiento() {
		return cs_probDescubrimiento;
	}
	public void setProbDescubrimiento(float probDescub) {
		cs_probDescubrimiento = probDescub;
	}

	public int getNumIteraciones() {
		return cantidadIteraciones;
	}
	public void setNumIteraciones(int n_iter_total) {
		cantidadIteraciones = n_iter_total;
	}
	public float[][] getNidos() {
		return nidosDecimales;
	}
	public void setNidos(float[][] nest) {
		nidosDecimales = nest;
	}
	/*Este método inicializa las soluciones de manera binaria y aleatoria*/
	private float[][] inicializacionSoluciones() throws FileNotFoundException, IOException {
		Random rnd = new Random(System.nanoTime());
		float nest[][] = new float[cantNidos][cantidadColumnas];
		for (int i = 0; i < cantNidos; i++) {
				for (int j = 0; j < cantidadColumnas; j++) {
					nest[i][j] = rnd.nextFloat();
				}	
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
	
	private float[][] clusterizaSoluciones (float[][] fitnessSoluciones )
	{
		
		Map<Integer, Float> aClusterCentroide = new  HashMap <Integer, Float>();
		Map<Integer, List<Punto>> mClusterPuntos = new HashMap <Integer, List<Punto>>();
		Map <Integer, Float> mFactorCluster = new HashMap <Integer, Float>();
		ArrayList<Punto> puntosRuido = new ArrayList<Punto>();
		Random rnd = new Random();
		float[][] solucionClusterizada = new float[fitnessSoluciones.length][cantidadColumnas];
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
									? Math.abs(nidosDecimales[punto.getPosicionFila()][punto.getPosicionColumna()] - 1)
									: nidosDecimales[punto.getPosicionFila()][punto.getPosicionColumna()];
    				
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
		for (int i = 0; i < cantNidos; i++) {
			fitness[i] = -10000;
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

	private float[] transferFunctionSolucion(float[] decimalNest, Random rnd, String tipoBinarizacion) {
		float[] f = new float[cantidadColumnas];
//		for (int i = 0; i < cantNidos; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				switch (tipoBinarizacion){
					case "sshape1":
						f[j] = (float) (1/(1+Math.pow(Math.E,-2*decimalNest[j])));
						break;
					case "sshape2":
						f[j] = (float) (1 / (1 + Math.pow(Math.E, -decimalNest[j] )));
						break;
						
					case "sshape3":
						f[j] = (float) (1/(1+Math.pow(Math.E,-1*decimalNest[j]/2)));
						break;
						
					case "sshape4":
						f[j] = (float) (1/(1+Math.pow(Math.E,-1*decimalNest[j]/3)));
						break;
			
					case "vshape1":
						f[j] = (float) Math.abs(erf((Math.sqrt(Math.PI) / 2) * decimalNest[j] ));
						break;
			
					case "vshape2":
						f[j] = Math.abs((float)Math.tan(decimalNest[j] ));
						break;
	//					return rnd.nextFloat() <= Math.abs((float)Math.tan(f)) ? 1:0;
			
					case "vshape3":
						f[j] = (float) Math.abs(decimalNest[j]/Math.sqrt(1+Math.pow(decimalNest[j] , 2)));
						break;
			
					case "vshape4":
						f[j] = (float) Math.abs(decimalNest[j]/Math.sqrt(1+Math.pow(decimalNest[j] , 2)));
						break;
			
					case "vshape5":
						f[j] = (float) Math.abs(2/Math.PI*Math.atan(Math.PI/2*decimalNest[j] ));  
						break;      	
					case "basic":
						f[j] = 0.5 <= decimalNest[j] ? 0 : 1;
						break;
					default:
						f[j] = 0;
						break;
				}
			}
//		}			
		return f;

	}
	
	private float[][] transferFunctionPoblacion(float[][] decimalPopulation, Random rnd, String tipoBinarizacion) throws FileNotFoundException, IOException {
		float[][] f = new float[cantNidos][cantidadColumnas];
		for (int i = 0; i < cantNidos; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				switch (tipoBinarizacion){
					case "sshape1":
						f[i][j] = (float) (1/(1+Math.pow(Math.E,-2*decimalPopulation[i][j])));
						break;
						
					case "sshape2":
						f[i][j] = (float) (1 / (1 + Math.pow(Math.E, -decimalPopulation[i][j] )));
						break;
						
					case "sshape3":
						f[i][j] = (float) (1/(1+Math.pow(Math.E,-1*decimalPopulation[i][j]/2)));
						break;
						
					case "sshape4":
						f[i][j] = (float) (1/(1+Math.pow(Math.E,-1*decimalPopulation[i][j]/3)));
						break;
			
					case "vshape1":
						f[i][j] = (float) Math.abs(erf((Math.sqrt(Math.PI) / 2) * decimalPopulation[i][j] ));
						break;
			
					case "vshape2":
						f[i][j] = Math.abs((float)Math.tan(decimalPopulation[i][j] ));
						break;
	//					return rnd.nextFloat() <= Math.abs((float)Math.tan(f)) ? 1:0;
			
					case "vshape3":
						f[i][j] = (float) Math.abs(decimalPopulation[i][j]/Math.sqrt(1+Math.pow(decimalPopulation[i][j] , 2)));
						break;
			
					case "vshape4":
						f[i][j] = (float) Math.abs(decimalPopulation[i][j]/Math.sqrt(1+Math.pow(decimalPopulation[i][j] , 2)));
						break;
			
					case "vshape5":
						f[i][j] = (float) Math.abs(2/Math.PI*Math.atan(Math.PI/2*decimalPopulation[i][j] ));
						break;        	
					case "basic":
						f[i][j] = 0.5 <= decimalPopulation[i][j] ? 0 : 1;
						break;
					default:
						f[i][j] = 0;
						break;
				}
//				f[i][j] = 0.5 <= decimalPopulation[i][j] ? 1 : 0;
			}
		}			
		return f;

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
	
	private float[]  discretizacionSolucion(float[] discretedPopulation, Random rnd, String tipoDiscretizacion) throws FileNotFoundException, IOException {
		double alpha = 0.4;
		float[] f = new float[cantidadColumnas];
		for (int j = 0; j < cantidadColumnas; j++) {
			switch (tipoDiscretizacion){
				case "standard":
					f[j] = rnd.nextFloat() <= discretedPopulation[j] ? 1 : 0;
					break;
				case "complement":
					f[j] = rnd.nextFloat() <= discretedPopulation[j] ? discretizacion(1-discretedPopulation[j], rnd,  "standard" ) : 0;
					break;
				case "staticProbability":
					f[j] = alpha >= discretedPopulation[j] ? 0 : (alpha < discretedPopulation[j] && discretedPopulation[j] <= ((1 + alpha) / 2)) ? discretizacion(discretedPopulation[j], rnd, "standard" ) : 1;
					break;
				case "elitist":
					f[j] = rnd.nextFloat() < discretedPopulation[j] ? discretizacion(discretedPopulation[j], rnd, "standard" ) : 0;
					break;
				default:
					f[j] = 0;
					break;
	
			}
		}
		return f;

	}
	
	private float[][] discretizacionPoblacion(float[][] discretedPopulation, Random rnd, String tipoDiscretizacion) throws FileNotFoundException, IOException {
		double alpha = 0.4;
		float[][] f = new float[cantNidos][cantidadColumnas];

		for (int i = 0; i < cantNidos; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				switch (tipoDiscretizacion){
					case "standard":
						f[i][j] = rnd.nextFloat() <= discretedPopulation[i][j] ? 1 : 0;
						break;
					case "complement":
						f[i][j] = rnd.nextFloat() <= discretedPopulation[i][j] ? discretizacion(1-discretedPopulation[i][j], rnd,  "standard" ) : 0;
						break;
					case "staticProbability":
						f[i][j] = alpha >= discretedPopulation[i][j] ? 0 : (alpha < discretedPopulation[i][j] && discretedPopulation[i][j] <= ((1 + alpha) / 2)) ? discretizacion(discretedPopulation[i][j], rnd, "standard" ) : 1;
						break;
					case "elitist":
						f[i][j] = rnd.nextFloat() < discretedPopulation[i][j] ? discretizacion(discretedPopulation[i][j], rnd, "standard" ) : 0;
						break;
					default:
						f[i][j] = 0;
						break;
		
				}
			}
		}
		return f;

	}

	/**
	 * Evalua la F.O. en todos los nidos, y devuelve el índice del nido con mejor fitnes entre todos
	 * @param nidos
	 * @param nuevosNidosDecimales
	 * @param fitness
	 * @return
	 */
	private int obtenerMejorNido(float nidos[][], float nuevosNidosDecimales[][],float nuevosNidosBinarios[][], float fitness[]) {
		int mejorposicion = -1;
		float costo = 0;
		float aux = -1000000000;
//		fitnessNidos = new float[cantNidos];
		//todo: revisar como obtener el mejor nido de todos, acorde al problema del knapsack
		for (int i = 0; i < cantNidos; i++) { //evaluando todos los "nuevos nidos" en la f.o y comparandolos con el anterior!
			costo  = funcionObjetivo(nuevosNidosBinarios[i]);
//			fitnessNidos[i] = costo;
	
			if (costo > fitness[i]) {
				nidos[i] = nuevosNidosDecimales[i];
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
	private float[][] generarNuevasSoluciones(float nidos[][], int posicionmejor, Random rnd, float alfa) throws FileNotFoundException, IOException {
//		int solucionCorrecta = 0;
		float nuevosnidos[][] = new float[cantNidos][cantidadColumnas];
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
		for (int i = 0; i < cantNidos; i++) {
				for (int j = 0; j < cantidadColumnas; j++) {
//					//if(i != posicionmejor){
//					float valor = (float) (nidos[i][j] + ((float) ((float) alfa * (float) paso[j]) * (nidos[posicionmejor][j] - nidos[i][j]) * rnd.nextGaussian()));
//					//this.test(valor,0);
					float valor;
					if(i > nidos.length-1) //quiere decir que son nidos nuevos (nuevas filas), y son valores aleatoreos.
					{
						 valor = rnd.nextFloat();
						
					}else {
						 valor = (float) (nidos[i][j] + ((float) ((float) alfa * (float) paso[j]) * (nidos[posicionmejor][j] - nidos[i][j]) * rnd.nextGaussian()));
					}
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
		return nuevosnidos;
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
	private float[][] nidosVacios(float nidos[][], Random rnd) throws FileNotFoundException, IOException {
		float nuevosnidos[][] = new float[cantNidos][cantidadColumnas];
		
		for (int i = 0; i < cantNidos; i++) {
			int filaRandom1 = rnd.nextInt(cantNidos);
			int filaRandom2 = rnd.nextInt(cantNidos);

			if (rnd.nextFloat() > cs_probDescubrimiento) { //si la especie dueña del nido descubre el huevo ajeno, se va a otro nido.
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
		return nuevosnidos;
	}

	/**
	 * Calcula el costo de la mochila, apra un nido en particular
	 * @param nidoBinario el vector con la solución propuesta.
	 * @return el valor de la suma de todos los elementos considerados en la mochila.
	 */
	private float funcionObjetivo(float nidoBinario[]) {
		int costo = 0;
		for (int j=0; j< mochila.getObjetos().size(); j++)
			  costo += nidoBinario[j] * (int) (mochila.getObjetos().get(j).getValor());
//		mochila.setCostoActual(costo);
		return costo;
	}
   
	public  boolean esFactible(float nido[]) {
    	double peso = 0;
        for (int j = 0; j < mochila.getObjetos().size(); j++) {
            peso += nido[j] * mochila.getObjetos().get(j).getPeso();
        }
//        mochila.setPesoActual((int)peso);
        return peso <= mochila.getCapacidadmochila();
	}
	
	public  double pesoActualIncluido(float nido[]) {
    	double peso = 0;
        for (int j = 0; j < mochila.getObjetos().size(); j++) {
            peso += nido[j] * mochila.getObjetos().get(j).getPeso();
        }
//        mochila.setPesoActual((int)peso);
        return peso;
	}
	
	public float[][] reparaKnapSack(float[][] badKnapSack)
	{
		float[][] fixedKnapSack = badKnapSack;
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
	
	public int obtienePosicionMayorPesoMochila(float[] vectorSolucion)
	{
		//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento

		double pesoMayor = -999999999, pesoEvaluado = 0;
		int posicionPesoMayor = 0;
		for(int col = 0; col< vectorSolucion.length; col++)
		{
			
			if(vectorSolucion[col] == 1) {
				
				pesoEvaluado = (mochila.getObjetos().get(col).getPeso()/mochila.getObjetos().size()*( pesoActualIncluido(vectorSolucion)- mochila.getCapacidadmochila()))/mochila.getObjetos().get(col).getValor();
				
				if(pesoEvaluado >= pesoMayor) {
					pesoMayor = pesoEvaluado; 
					posicionPesoMayor = col;
					//factor de cambi = (peso elemento /cantidad elementos*(capacidad mochila - capacidad incluída))/ costo elemento
				}
			}
		}
		return posicionPesoMayor;
	}
	
	public int obtienePosicionMenorPesoNoIncluidoEnMochila(float[] vectorSolucion)
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
	private void saveLineToFile(int ejecucion, int iteracion, String fileName, double knowFitInstancia, String nombreInstancia) {
//			|Nº EJECUCION | NIDOS | BINARIZACION  |ITERACIONES|  SEMILLA     | MEJOR FITNESS |    FITNESS FOUND| TIME |\n");
	        BufferedWriter bufferedWriter = null;
	        File file;
	        String line = "|";
//| INSTANCIA | EJEC |  ITER  | ITER MAX | NIDOS |    %Pa    | CAP. MOCH. | MEJOR FIT. | FIT. FOUND |  RDP  | TIME  |    SEMILLA    |                         VEC. SOLUCION                        |

	        try {
	            file = new File(fileName);
	            boolean exists = file.exists();

	            if (!exists) {
	                (new File(file.getParent())).mkdirs();
	            }

	            bufferedWriter = new BufferedWriter(new FileWriter(file, true));

	            try (PrintWriter pw = new PrintWriter(bufferedWriter)) {
	                if (!exists) {
	                	line += Write(2, "Instancia") + 
	                			Write(2, "Ejecucion") + 
	                			Write(2, "Iteracion") +
	                			Write(2, "Iter Max") + 
	                    		Write(5, "Nidos") + 
	                    		Write(6, "%Pa") + 
	                    		Write(8,"tipoDiscretizacion") + //%-17s
		                		Write(8,"tipoBinarizacion") + //%-12s 
		                		Write(8,"CapMoch") + //%-10s
		                		Write(8, "Optmo") ; //%-10s
	                    line += Write(8, "OptimoObtenido") + 
	                    		Write(8, "RDP") + 
	                    		Write(5, "time") +
	                    		Write(13, "semilla") + 
	                    		Write(15, "VectorSolucionReal")+
	                    		Write(15, "VectorSolucionBinario") ;
	                    pw.println(line);
	                    line = "";
	                    for (int i = 0; i < 97; i++) {
	                        line += "-";
	                    }
	                    pw.println(line);
	                    line = "|";
	                }


	                line += 
	                		Write(8,mochila.getNombreInstancia()) + //%-9s 
	                		Write(2, ejecucion) + // %-4s 
	                		Write(8,iEsimaIteracion) + //%-9s 
	                		Write(8,cantidadIteraciones) + //%-9s 
	                		Write(5, cantNidos) + //%-5s
	                		Write(6, cs_probDescubrimiento) +  
	                		Write(8,tipoDiscretizacion) + //%-17s
	                		Write(8,tipoBinarizacion) + //%-12s 
	                		Write(8,mochila.getCapacidadmochila()) + //%-10s
	                		Write(8, mochila.getValorOptimo()) + //%-10s
	                		Write(8, bestFit)+ //%-10s
	                		Write(8, String.format("%.2f", Math.abs((bestFit- mochila.getValorOptimo())/mochila.getValorOptimo()*100)))+ //%-5s
	                		Write(5, (String.format("%.2g%n", (tiempoTermino-tiempoInicioEjecucion) / (1000f))).trim()); //%-5s
	                line += Write(13, semilla) + //%-13s
	                		Write(15, Arrays.toString(nidosDecimales[posicionmejor]))+
            				Write(15, Arrays.toString(nidosBinarios[posicionmejor])); //%-60s
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
		return ("Numero de nidos:" + this.cantNidos + "\n" + "Numero de dimensiones:" + this.cantidadColumnas + "\n" + "Operador probabilidad:" + this.cs_probDescubrimiento + "\n" + "Numero de Iteraciones:" + this.cantidadIteraciones + "\n");
	}

	
	public void ejecutar() {
//		int repActual = 1;//Integer.parseInt(args[0]);
		 iEsimaIteracion=0;
		 contIncrementoNidos= 1; 
		 contDecrementoNidos=1;
		 debugMode = 0; //1 para mostrar, otro para no mostrar
		
		float nuevosnidos[][] = null;

		cs_alfa = (float) 0.01;
		try {
//			 System.out.print("en Clase Cuckoo:\n NumeroFilas:"+ numeroFilas +"numeroCol"+ numeroColumnas + "costos:"+costos.length);
			//"| %-4s 		| %-5s 	| %-4s | %-17s 			   | %-12s 		  | %-9s 	  | %-10s 	   | %-10s 		| %-10s 	 | %-5s  | %-5s  | %-13s 	     | %-60s | \n"
			System.out.print(
					"| INSTANCIA | EJEC |  ITER  | ITER MAX | NIDOS |    %Pa    | CAP. MOCH. | MEJOR FIT. | FIT. FOUND |  RDP  |     TIME    |    SEMILLA    |                         VEC. SOLUCION                        |\n");
			System.out.print(
					"|____________________________________________________________________________________________________________________________________________________________________________________________");
			idArchivo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			for ( numEjecucion = 1; numEjecucion <= cantEjecuciones; numEjecucion++) {

//					int mejorIteracion = 0;
//				Random rnd;
				rnd = new Random();

				tiempoInicioEjecucion = System.currentTimeMillis();
				semilla = new Date().getTime();
				rnd.setSeed(semilla);
				
				cs_probDescubrimiento =probDescubrimientoInicial ; 
				cantNidos = cantNidosInicial;
				fitness = new float[cantNidos];

				nidosDecimales = this.inicializacionSoluciones(); // Inicializar nidos o soluciones || con valores aleatoreos
				nidosBinarios = this.discretizacionPoblacion( this.transferFunctionPoblacion(nidosDecimales, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
				nidosBinarios = reparaKnapSack(nidosBinarios);
				inicializacionFitness(fitness); // Inicializar el vector con las mejores soluciones para los nidos
												// i-ésimos || laprimera vez todos los fitness en 10^10 (Minimizar) -100 maximizar
				
				posicionmejor = this.obtenerMejorNido(nidosDecimales, nidosDecimales, nidosBinarios, fitness);
//				fitnessTotalActual = calculaRendimientoPoblacion(fitnessNidos);
//				fitnessTotalAnterior = fitnessTotalActual; 
				// hasta aquí solo inicializó la primera vez de evaluaciones, con valores
				// aleatorios y fitness altísimos para poder dejar los valores iniciales.

				int auxPosicion = posicionmejor;
				bestFit = fitness[posicionmejor];
				iEsimaIteracion = 1;

				while (iEsimaIteracion <= cantidadIteraciones) { // Aqui comienza a iterar el algoritmo CS.
					bestFitAnterior = bestFit ;
					
					nuevosnidos = generarNuevasSoluciones(nidosDecimales, posicionmejor, rnd, cs_alfa); // Aqui se generarán nuevas
					nidosBinarios = this.discretizacionPoblacion( this.transferFunctionPoblacion(nuevosnidos, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
					nidosBinarios = reparaKnapSack(nidosBinarios);																		// soluciones aplicando LF
					posicionmejor = obtenerMejorNido(nidosDecimales, nuevosnidos, nidosBinarios, fitness);

					if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
						auxPosicion = posicionmejor;
						bestFit = fitness[posicionmejor];
					}
					//i++;
					nuevosnidos = nidosVacios(nidosDecimales, rnd);
					nidosBinarios = this.discretizacionPoblacion( this.transferFunctionPoblacion(nuevosnidos, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
					nidosBinarios = reparaKnapSack(nidosBinarios);																		// soluciones aplicando LF
					posicionmejor = obtenerMejorNido(nidosDecimales, nuevosnidos, nidosBinarios, fitness);
					
					if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
						auxPosicion = posicionmejor;
						bestFit = fitness[posicionmejor];
					}
					if (iEsimaIteracion%iteracionIntervencionML == 0)
					{
						parametterTunning();
					}
//					tiempoTermino = System.currentTimeMillis();
					if (iEsimaIteracion==1){
						printConsole_SaveFile();
						
					}
					iEsimaIteracion++;
					if(bestFit >= mochila.getValorOptimo())
						break;
//					evolucionarCuckooSearch();
					
					
				}

				mochila.setValorOptimoObtenido(((int) funcionObjetivo(nidosBinarios[posicionmejor])));

				if (!esFactible(nidosBinarios[posicionmejor]))
					System.out.print("\n\nERROR EN PASO DE UNA O MAS RESTRICCION\n");

				tiempoTermino = System.currentTimeMillis();

				printConsole_SaveFile();

			}
			System.out.println("\nEjecuciones terminadas en: " 
			+ String.format("%02.0f", (float) ((tiempoTermino - tiempoInicioGlobal)/1000 / 86400 ))//dias
			+ String.format(":%02.0f", (float) (((tiempoTermino - tiempoInicioGlobal)/1000)%86400 / 3600 ))//horas
			+ String.format(":%02.0f", (float) (((tiempoTermino - tiempoInicioGlobal) /1000)% 3600 / 60))//minutos
			+ String.format(":%02.0f", (float) (((tiempoTermino - tiempoInicioGlobal) /1000)% 60 ))
			);//segundos

		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}
	
	
	private void printConsole_SaveFile() {
		System.out.printf( "%n| %-9s | %-5s | %-5s | %-8s | %-5s | %-7s | %-10s | %-10s | %-10s | %-5s | %-1s:%-1s:%-1s:%-2s | %-13s | %-60s | "
				 ,mochila.getNombreInstancia() 
				,String.format("%02d", numEjecucion)
				,String.format("%04d", iEsimaIteracion)
				,cantidadIteraciones 
				,cantNidos 
				,String.format("%.7f", cs_probDescubrimiento)
				, mochila.getCapacidadmochila()
				, mochila.getValorOptimo()
				,(int)bestFit   
				,String.format("%.2f",Math.abs((bestFit - mochila.getValorOptimo()) / mochila.getValorOptimo() * 100))
//				,String.format("%2.0f", (float) TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - tiempoInicioEjecucion))
//				,String.format("%2.0f", (float) TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - tiempoInicioEjecucion)-TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - tiempoInicioEjecucion)*24)
				,String.format("%02.0f", (float) ((System.currentTimeMillis() - tiempoInicioEjecucion)/1000 / 86400 ))//dias
				,String.format("%02.0f", (float) (((System.currentTimeMillis() - tiempoInicioEjecucion)/1000)%86400 / 3600 ))//horas
				,String.format("%02.0f", (float) (((System.currentTimeMillis() - tiempoInicioEjecucion) /1000)% 3600 / 60))//minutos
				,String.format("%02.0f", (float) (((System.currentTimeMillis() - tiempoInicioEjecucion) /1000)% 60 ))//segundos
				, semilla
				,Arrays.toString(nidosDecimales[posicionmejor])
				);
		/* guardamos resultados en archivo plano*/
		saveLineToFile(numEjecucion, iEsimaIteracion,
       		"resources/output/Salida_DBSCAN_autoPA_"+idArchivo +"_" + mochila.getNombreInstancia(),
       		mochila.getValorOptimo(), 
       		mochila.getNombreInstancia());
		
	}
	private void parametterTunning() throws FileNotFoundException, IOException {

//		System.out.print( "\n");

		clusterizaDbscanSoluciones(nidosDecimales,  fitness);
		if (cs_probDescubrimiento == 0)
			cs_probDescubrimiento = 0.25f;
		else if(cs_probDescubrimiento >=0.45f)
			cs_probDescubrimiento = 0.45f;
		else if (cs_probDescubrimiento <=0.1f)
			cs_probDescubrimiento = 0.1f;
		
        if(bestFit == bestFitAnterior)
        {
        	//incrementamos el contador para aumentar cont
        	contIncrementoNidos++;
        }else if(bestFit > bestFitAnterior)
        {
        	//incrementamos contador para achicar pob
        	contDecrementoNidos++;
        }
        
        if(contIncrementoNidos == 4) {
        	cantNidos +=5; contIncrementoNidos =0;
        	
        	float fitnessNuevos[] = new float[cantNidos];
        	System.arraycopy(fitness, 0, fitnessNuevos, 0, fitness.length);
        	for (int col = fitness.length; col < cantNidos; col++) {
//        		fitnessNuevos[col] = (float) Math.pow((double) 10, (double) 10);//para minimizar
        		fitnessNuevos[col] = 0;//para maximizar
        	}
        	fitness = new float[cantNidos];
        	fitness = fitnessNuevos.clone();
        	
        	
        	float nuevosNidosFloat[][] = new float[cantNidos][cantidadColumnas];
        	for (int fila = 0; fila<nidosDecimales.length; fila++) {
        		System.arraycopy(nidosDecimales[fila], 0, nuevosNidosFloat[fila], 0, cantidadColumnas);
        	}
        	nidosDecimales = new float[cantNidos][cantidadColumnas];
        	nidosDecimales = nuevosNidosFloat.clone();
        	nidosBinarios = new float[cantNidos][cantidadColumnas];
        	
        	
        }
        printConsole_SaveFile();
//        if(contDecrementoNidos == 2 && cantNidos >=15)
//        	cantNidos -=5; contDecrementoNidos = 0;
	}
	
	private int[][] clusterizaDbscanSoluciones(float[][] poblacionSoluciones, float[] vectoFitnessSoluciones ) throws FileNotFoundException, IOException
	{
		
		Map <Integer, List<Punto>> mClusterPuntos = new HashMap <Integer, List<Punto>>();
		Map <Integer, Float> mPromFitnessCluster = new HashMap <Integer, Float>();
		ArrayList<Punto> puntosRuido = new ArrayList<Punto>();
		
		int[][] solucionClusterizada = new int[poblacionSoluciones.length][cantidadColumnas];
		List<Punto> puntos = new ArrayList<Punto>();
//		float pesoTransicion = 0.2F;

//		 System.out.print("\nCantidad de elementos en la solucion: "+ solucion.length);
		for (int fil = 0; fil< poblacionSoluciones.length; fil++) {

			Float[] solucion = new Float[cantidadColumnas];
			for (int col = 0; col< poblacionSoluciones[0].length; col++) 
				solucion[col] = poblacionSoluciones[fil][col];				
			
			Punto p = new Punto(solucion, fil, 0, vectoFitnessSoluciones[fil]);
			puntos.add(p);
			
		}
		int cantPuntos = puntos.size();
		
		if (cantPuntos < 2 ) {
			System.out.print("\n Nada que clusterizar, retornando\n");
			return solucionClusterizada;
		}
		
//		int minElementosEnCluster = (int) (puntos.size()*0.1f/100);
		// Se sugiere setear el valor del minElemen como LOG(n);
		int minElementosEnCluster = 3;
//		int minElementosEnCluster = (int) Math.log10(cantPuntos);
//		System.out.println(minElementosEnCluster+" es el Log(cantPuntos)");
//        double maxDistance = (double)(puntos.size()*0.0000095d/100);
//        double epsilonValue = 5.0d;
		double epsilonValue = getEpsilonValueKnn(minElementosEnCluster, puntos);

        
        
        DBSCANClusterer<Punto> clusterer = null;
        try {
            clusterer = new DBSCANClusterer<Punto>(puntos, minElementosEnCluster, epsilonValue, new DistanceMetricPunto());
        } catch (DBSCANClusteringException e1) {
            System.out.println("Should not have failed on instantiation: " + e1);
        }
        
        ArrayList<ArrayList<Punto>> result = null;
        
        try {
        	if (debugMode == 1)System.out.print("\n");

            result = clusterer.performClustering(); //-->clusteriza los puntos
            if (debugMode == 1)System.out.print("\n");
            puntosRuido = clusterer.getNoiseValues(); //obtiene puntos ruido
            cs_probDescubrimiento = (float)puntosRuido.size()/cantPuntos;
            int conteoCluster = 0, sumFitCluster;

          //Recorremos los clusteres para obtener el promedio y asignar peso
            for (ArrayList<Punto> arrayPnt: result) {
            	conteoCluster++;
            	sumFitCluster = 0;
            	mClusterPuntos.put(conteoCluster, arrayPnt);
            	for (int dim = 0; dim < arrayPnt.size(); dim++) {
            		sumFitCluster += arrayPnt.get(dim).getFitnessSolucion();
            	}
            	mPromFitnessCluster.put(conteoCluster, (float)sumFitCluster/arrayPnt.size());
            	
            }
            conteoCluster++;
        	sumFitCluster = 0;
            for(Punto pntRuido:puntosRuido){
            	sumFitCluster += pntRuido.getFitnessSolucion();
            	
            }
        	mPromFitnessCluster.put(conteoCluster, (float)sumFitCluster/puntosRuido.size());

            //mostramos los promedios para análisis:
            for(Map.Entry<Integer, Float> entry: mPromFitnessCluster.entrySet())
    		{
            	if (entry.getKey() == conteoCluster) {
            		if (debugMode == 1) System.out.println("cluster: " + entry.getKey() + " Promedio Fitness: "+ entry.getValue()+" Cant Puntos: "+puntosRuido.size()+"\n");
            		if (debugMode == 1)System.out.println("Fitness en cluster Ruido:\n");
            		Collections.sort(puntosRuido, new SortbyFitness());
            		if (debugMode == 1) for(Punto pntRuido:puntosRuido){
	            			System.out.println(pntRuido.getFitnessSolucion()+" ");
	                    }
            		if (debugMode == 1)System.out.println("Análisis de Fit en Cluster Ruido:\n(PromedioClusterRuido > bestFit-1 && PromedioClusterRuido < bestFit+1)");
            		if (debugMode == 1)System.out.println("if ("+entry.getValue() +"> "+(bestFit-1)+" && "+entry.getValue()+" < "+(bestFit+1)+")");
                	if (entry.getValue() > bestFit-1 && entry.getValue() < bestFit+1)
                	{
//                		Collections.sort(arrayPnt, new SortbyFitness());
                		if (debugMode == 1)System.out.println("promedio dentro del rango!!:");
                		if (debugMode == 1)System.out.println("Reemplazando la mitad del cluster RUIDO en nuevos nidos:");
                		//si estamos acá, quiere decir que el cluster tiene valores cercanos al mejor valor
                		for (int dim = (puntosRuido.size()/2); dim < puntosRuido.size(); dim++) {
                			for(int col = 0; col < cantidadColumnas; col++) {
                				nidosDecimales[puntosRuido.get(dim).getPosicionFila()][col] = rnd.nextFloat();
                        	}
                			//aplicamos funcion de transferencia
                			nidosBinarios[puntosRuido.get(dim).getPosicionFila()] = discretizacionSolucion(transferFunctionSolucion(nidosDecimales[puntosRuido.get(dim).getPosicionFila()], rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
    					}
                		//reparamos solucion
                		nidosBinarios = reparaKnapSack(nidosBinarios);
                		if (debugMode == 1) for (int dim = (puntosRuido.size()/2); dim < puntosRuido.size(); dim++) {
                			System.out.println(funcionObjetivo(nidosBinarios[puntosRuido.get(dim).getPosicionFila()]));
                    	}
                	}
            		
            	}else {
            		if (debugMode == 1)System.out.println("cluster: " + entry.getKey() + " Promedio Fitness: "+ entry.getValue()+" Cant Puntos: "+mClusterPuntos.get(entry.getKey()).size()+"\n");
            		if (debugMode == 1)System.out.println("Fitness en cluster:\n");
            		for (ArrayList<Punto> arrayPnt: result) {
                    	
            			Collections.sort(arrayPnt, new SortbyFitness());
            			if (debugMode == 1)System.out.println("Mostrando los fitness del cluster "+ entry.getKey() +" ascendentes:");
                		//si estamos acá, quiere decir que el cluster tiene valores cercanos al mejor valor
            			if (debugMode == 1)for (int dim = 0; dim < arrayPnt.size(); dim++) {
                    		System.out.println(arrayPnt.get(dim).getFitnessSolucion()+" ");
                    	}
            			if (debugMode == 1)System.out.println("Análisis de Fit en Cluster "+ entry.getKey() +":\n(PromedioCluster > bestFit-1 && PromedioCluster < bestFit+1)");
            			if (debugMode == 1)System.out.println("if ("+entry.getValue() +"> "+(bestFit-1)+" && "+entry.getValue()+" < "+(bestFit+1)+")");
                    	if (entry.getValue() > bestFit-1 && entry.getValue() < bestFit+1)
                    	{
//                    		Collections.sort(arrayPnt, new SortbyFitness());
                    		if (debugMode == 1)System.out.println("promedio dentro del rango:");
                    		if (debugMode == 1)System.out.println("Reemplazando la mitad del cluster en nuevos nidos:");
                    		//si estamos acá, quiere decir que el cluster tiene valores cercanos al mejor valor
                    		for (int dim = (arrayPnt.size()/2); dim < arrayPnt.size(); dim++) {
                    			for(int col = 0; col < cantidadColumnas; col++) {
                    				nidosDecimales[arrayPnt.get(dim).getPosicionFila()][col] = rnd.nextFloat();
                            	}
                    			//aplicamos funcion de transferencia
                    			nidosBinarios[arrayPnt.get(dim).getPosicionFila()] =discretizacionSolucion(transferFunctionSolucion(nidosDecimales[arrayPnt.get(dim).getPosicionFila()], rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
        					}
                    		//reparamos solucion
                    		nidosBinarios = reparaKnapSack(nidosBinarios);
                    		if (debugMode == 1)for (int dim = (arrayPnt.size()/2); dim < arrayPnt.size(); dim++) {
                    			System.out.println(funcionObjetivo(nidosBinarios[arrayPnt.get(dim).getPosicionFila()]));
                    		}
                    	
                    	}
                    }
            	}

    		}
            
        } catch (DBSCANClusteringException e) {
            System.out.println("Should not have failed while performing clustering: " + e);
            
        }
		return solucionClusterizada;
	}
	
	private double getEpsilonValueKnn(int kValue, List<Punto> puntos) {
		//Copiamos los puntos a evaluar para compararlos entre si
		List<Punto> lPuntosRecorrer = new ArrayList<>(puntos);
		double meanTotalPuntos = 0;
		
		for(Punto ptoOrigen:puntos)
		{
			//creamos una lista que contendrá las distancias del punto evaluado con el resto
			List<Double> distancias = new ArrayList<Double>();
			
			for(Punto ptoDestino:lPuntosRecorrer){
				if (!ptoOrigen.equals(ptoDestino))
					distancias.add(ptoOrigen.distanciaEuclideana(ptoDestino));
			}
			//Ordenamos la lista de menor a mayor, para sacar el promedio de los k primeros elementos
			Collections.sort(distancias);
			//calculamos el promedio de los k-primeros valores
			double meanPuntoOrigen = 0;
			
			for(int i = 0; i<kValue; i++) {
//				if(distancias.size()>i)
					meanPuntoOrigen += distancias.get(i);
//				System.out.println(meanPuntoOrigen + "");
			}
			meanPuntoOrigen = meanPuntoOrigen/kValue;
			
			//sumamos el promedio del punto al promedio total
			meanTotalPuntos += meanPuntoOrigen;
		}
		meanTotalPuntos = meanTotalPuntos/puntos.size();
		if (debugMode == 1)System.out.println("kvalue: "+ meanTotalPuntos);
		return 	meanTotalPuntos;
	}		

	
	
	private double calculaRendimientoPoblacion(float[] fitnessSoluciones) {
		double result = 0;
		for (float val=0; val < fitnessSoluciones.length;val++)
			result +=val;
		return result/fitnessSoluciones.length;
	}

}
