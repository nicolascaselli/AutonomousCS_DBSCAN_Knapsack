package ncb.phd.pucv.cuckooSearch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ncb.phd.pucv.KnapSack.Knapsack;
import ncb.phd.pucv.KnapSack.Objeto;




public class Main {
	public static List<String> instancias;
	public static int costos[];
	public static int restricciones[][];
	public static int numeroFilas, numeroColumnas;
	public static Map<Integer, Knapsack> mInstanciasMochilas = new HashMap<Integer, Knapsack>(); 
//	public static Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
	public static Map<Integer, String> mTiposDeBinarizacion  = new HashMap<Integer, String>(); 
	public static Map<Integer, String> mDiscretizaciones = new HashMap<Integer, String>();
	public static int cantNidos = 25;
	public static float probDescub = (float)0.25;
	public static int numeroIteraciones = 2500; 
	public static int cantInstancias = 2;
	public static boolean fs, ss;
	 /**
     * {@code rows} = Filas de M {@code cols} = Columnas de M
     */
    public static int rows, cols;
    /**
     * {@code rs} = Restricciones {@code rc} = Restricciones cubiertas
     * {@code cc} = Contador de costos {@code cr} = Contador de restricciones
     * {@code fc} = Contador de fitness {@code tf} = Total fitness
     */
    public static int rs, rc, cc, cr;
    /**
	
    /**
     * {@code logger} = Log de errores
     */
    private  final Logger logger = Logger.getLogger(CuckooSearch.class.getName());
	
    public static  int cargaInstancias_mochila()
	{
		ArrayList<Objeto> elementos = new ArrayList<>();
		Knapsack mochila = new Knapsack();
		int i = 0;
		//f1 - dimension 10 optimo=295  - w 269
		i++;
		elementos.add(new Objeto(55, 95));
		elementos.add(new Objeto(10, 4));
		elementos.add(new Objeto(47, 60));
		elementos.add(new Objeto(5, 32));
		elementos.add(new Objeto(4, 23));
		elementos.add(new Objeto(50, 72));
		elementos.add(new Objeto(8, 80));
		elementos.add(new Objeto(61, 62));
		elementos.add(new Objeto(85, 65));
		elementos.add(new Objeto(87, 46));
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(269);
		mochila.setValorOptimo(295);
		mochila.setNombreInstancia("f1");
		mInstanciasMochilas.put(i, mochila);
		
//		///////////////////////////////////
//        
        //f2 - dimension 20   optimo=1024  b = 878.
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(44, 92));
        elementos.add(new Objeto(46, 4));
        elementos.add(new Objeto(90, 43));
        elementos.add(new Objeto(72, 83));
        elementos.add(new Objeto(91, 84));
        elementos.add(new Objeto(40, 68));
        elementos.add(new Objeto(75, 92));
        elementos.add(new Objeto(35, 82));
        elementos.add(new Objeto(8, 6));
        elementos.add(new Objeto(54, 44));
        elementos.add(new Objeto(78, 32));
        elementos.add(new Objeto(40, 18));
        elementos.add(new Objeto(77, 56));
        elementos.add(new Objeto(15, 83));
        elementos.add(new Objeto(61, 25));
        elementos.add(new Objeto(17, 96));
        elementos.add(new Objeto(75, 70));
        elementos.add(new Objeto(29, 48));
        elementos.add(new Objeto(75, 14));
        elementos.add(new Objeto(63, 58));
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(878);
		mochila.setValorOptimo(1024);
		mochila.setNombreInstancia("f2");
		mInstanciasMochilas.put(i, mochila);
////        
        // f3 dimension 4 optimo 35  b=20
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(9, 6));
        elementos.add(new Objeto(11, 5));
        elementos.add(new Objeto(13, 9));
        elementos.add(new Objeto(15, 7));
        
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(20);
		mochila.setValorOptimo(35);
		mochila.setNombreInstancia("f3");
		mInstanciasMochilas.put(i, mochila);
//		//
////		f4 dimension 4 optimo ? b=11
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
		elementos.add(new Objeto(6, 2));
		elementos.add(new Objeto(10, 4));
		elementos.add(new Objeto(12, 6));
		elementos.add(new Objeto(13, 7));
        
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(11);
		mochila.setValorOptimo(23); 
		mochila.setNombreInstancia("f4");
		mInstanciasMochilas.put(i, mochila);

        //f5 dimension 15 optimo 481.0694 b=375
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(0.125126, 56.358531));
        elementos.add(new Objeto(19.330424, 80.87405));
        elementos.add(new Objeto(58.500931, 47.987304));
        elementos.add(new Objeto(35.029145, 89.59624));
        elementos.add(new Objeto(82.284005, 74.660482));
        elementos.add(new Objeto(17.41081, 85.894345));
        elementos.add(new Objeto(71.050142, 51.353496));
        elementos.add(new Objeto(30.399487, 1.498459));
        elementos.add(new Objeto(9.140294, 36.445204));
        elementos.add(new Objeto(14.731285, 16.589862));
        elementos.add(new Objeto(98.852504, 44.569231));
        elementos.add(new Objeto(11.908322, 0.466933));
        elementos.add(new Objeto(0.89114, 37.788018));
        elementos.add(new Objeto(53.166295, 57.118442));
        elementos.add(new Objeto(60.176397, 60.716575));
        
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(375);
		mochila.setValorOptimo(481.0694);
		mochila.setNombreInstancia("f5");
		mInstanciasMochilas.put(i, mochila);
//		
//        //f6 dimension 10 optimo 52 b=60
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(20, 30));
        elementos.add(new Objeto(18, 25));
        elementos.add(new Objeto(17, 20));
        elementos.add(new Objeto(15, 18));
        elementos.add(new Objeto(15, 17));
        elementos.add(new Objeto(10, 11));
        elementos.add(new Objeto(5, 5));
        elementos.add(new Objeto(3, 2));
        elementos.add(new Objeto(1, 1));
        elementos.add(new Objeto(1, 1));
        
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(60);
		mochila.setValorOptimo(52);
		mochila.setNombreInstancia("f6");
		mInstanciasMochilas.put(i, mochila);

        //f7 dimensión 7 optimo 107 b=50
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(70, 31));
        elementos.add(new Objeto(20, 10));
        elementos.add(new Objeto(39, 20));
        elementos.add(new Objeto(37, 19));
        elementos.add(new Objeto(7, 4));
        elementos.add(new Objeto(5, 3));
        elementos.add(new Objeto(10, 6));
		
        mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(50);
		mochila.setValorOptimo(107);
		mochila.setNombreInstancia("f7");
		mInstanciasMochilas.put(i, mochila);
//
////f8 dimensión 7 optimo 9767  b=10000
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(81,983));
        elementos.add(new Objeto(980,982));
        elementos.add(new Objeto(979,981));
        elementos.add(new Objeto(978,980));
        elementos.add(new Objeto(977,979));
        elementos.add(new Objeto(976,978));
        elementos.add(new Objeto(487,488));
        elementos.add(new Objeto(974,976));
        elementos.add(new Objeto(970,972));
        elementos.add(new Objeto(485,486));
        elementos.add(new Objeto(485,486));
        elementos.add(new Objeto(970,972));
        elementos.add(new Objeto(970,972));
        elementos.add(new Objeto(484,485));
        elementos.add(new Objeto(484,485));
        elementos.add(new Objeto(976,969));
        elementos.add(new Objeto(974,966));
        elementos.add(new Objeto(482,483));
        elementos.add(new Objeto(962,964));
        elementos.add(new Objeto(961,963));
        elementos.add(new Objeto(959,961));
        elementos.add(new Objeto(958,958));
        elementos.add(new Objeto(857,959));
		
        mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(10000);
		mochila.setValorOptimo(9767);
		mochila.setNombreInstancia("f8");
		mInstanciasMochilas.put(i, mochila);

        //f9  dimension 5 optimo 130 b=80
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(33,15));
        elementos.add(new Objeto(24,20));
        elementos.add(new Objeto(36,17));
        elementos.add(new Objeto(37,8));
        elementos.add(new Objeto(12,31));
        
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(80);
		mochila.setValorOptimo(130);
		mochila.setNombreInstancia("f9");
		mInstanciasMochilas.put(i, mochila);

        //f10 dimension 20 optimo 1025 b=879
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
        elementos.add(new Objeto(91, 84));
        elementos.add(new Objeto(72, 83));
        elementos.add(new Objeto(90, 43));
        elementos.add(new Objeto(46, 4));
        elementos.add(new Objeto(55, 44));
        elementos.add(new Objeto(8, 6));
        elementos.add(new Objeto(35, 82));
        elementos.add(new Objeto(75, 92));
        elementos.add(new Objeto(61, 25));
        elementos.add(new Objeto(15, 83));
        elementos.add(new Objeto(77, 56));
        elementos.add(new Objeto(40, 18));
        elementos.add(new Objeto(63, 58));
        elementos.add(new Objeto(75, 14));
        elementos.add(new Objeto(29, 48));
        elementos.add(new Objeto(75, 70));
        elementos.add(new Objeto(17, 96));
        elementos.add(new Objeto(78, 32));
        elementos.add(new Objeto(40, 68));
        elementos.add(new Objeto(44, 92));
		
        mochila.setObjetos(elementos );
		mochila.setCapacidadMochila(879);
		mochila.setValorOptimo(1025);
		mochila.setNombreInstancia("f10");
		mInstanciasMochilas.put(i, mochila);
        
		return 0;
	}
	
	public  int cargaDatosDeInstancia(String rutaYnombreInstancia) throws FileNotFoundException, IOException
	{
		System.out.print("COMENZANDO A LEER EL ARCHIVO...\n\n");
        String ruta = rutaYnombreInstancia;
        
        String linea = "";
        List<String> elementos = null;
        File archivo = new File(ruta);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        int numElem = 0, i = 0,  k = 0, auxFichero = 0, flag = 0, aux=0;
//        
//        //LECTURA FICHEROS UNICOST
//        
        try {
	        while (aux == 0) { //leyendo dimensiones de la matriz de restriccion.
	            linea = br.readLine().trim();
	            elementos = Arrays.asList(linea.split(" "));
	            numeroFilas = Integer.parseInt(elementos.get(0));
	            numeroColumnas = Integer.parseInt(elementos.get(1));
	            costos = new int[numeroColumnas];
	            System.out.print("Archivo: "+ruta+"\nFilas: "+numeroFilas+"\nColumnas:"+numeroColumnas+"\n\n");
	            
	            while (numElem != 1) { //llenando el vector de costos asociados a cada columna/dimensión/variable.
	                linea = br.readLine().trim();
	                elementos = Arrays.asList(linea.split(" "));
	                numElem = elementos.size();
	                for (int j = i; j < costos.length && k != elementos.size() && numElem != 1; j++) {
	                    costos[j] = Integer.parseInt(elementos.get(k));
	                    k++;
	                }
	                if (numElem == 1) {
	                    flag = Integer.parseInt(elementos.get(0));
	                }
	                k = 0;
	                i += elementos.size();
	                
	            }
	            System.out.print("costos cargados OK\n");
	            k = 0;
	            linea = br.readLine().trim();
	            elementos = Arrays.asList(linea.split(" "));
	            restricciones = new int[numeroFilas][numeroColumnas];
	            System.out.print("inicio carga Restricciones...\n elementos a cargar : "+elementos+"\n");
	            System.out.print("flag: "+flag+"\nauxFichero: "+auxFichero+"\n");
	            System.out.print("flag: "+elementos.size()+"\n");
	            
	            for (i = 0; i < numeroFilas; i++) { // llenando matriz de restricciones
	                while (flag != auxFichero) {
	                    auxFichero = auxFichero + elementos.size();
	                    
	                    while (k < elementos.size()) {
	                        restricciones[i][Integer.parseInt(elementos.get(k)) - 1] = 1;
	                        k++;
	                        
	                    }
	                    k = 0;
	                    if ((linea = br.readLine()) != null) {
	                        elementos = Arrays.asList(linea.trim().split(" "));
	                    }
//	                    System.out.print(" "+flag+"!="+auxFichero+"?\n");
	                    return 0;
	                }
	                System.out.print("Fila: " +i +" cargada OK\n");
		            
	                auxFichero = 0;
	                flag = Integer.parseInt(elementos.get(0));
	                if ((linea = br.readLine()) != null) {
	                    elementos = Arrays.asList(linea.trim().split(" "));
	                }
	                for (int j = 0; j < numeroColumnas; j++) {
	                    if (restricciones[i][j] != 1) {
	                        restricciones[i][j] = 0;
	                    }
	                }
	
	            }
	            aux = 1;
	        }
	        System.out.println("Archivo cargado correctamente");
	        
            //CS.generarExcel(numerofilas,numerocolumnas,numeroIteraciones,fitness[posicionmejor],fitness[posicionPeor],fBinarizacion,id,archivo.getName().replace(".txt", ""),repActual,tiempoInicio,mejorIteracion);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return 0;
	}
	
	
    public  void LeeArchivoInstancia(String rutaYnombreInstancia) {
    	fs = true;
        ss = true;

        rs = 0;
        rc = 0;
        cc = 0;
        cr = 0;
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(rutaYnombreInstancia));
            while ((line = br.readLine()) != null) {
                ProcesarLinea(line);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
    
	public  void ProcesarLinea(String line) {
	        String[] values = line.trim().split(" ");

	        if (fs) {
	            numeroFilas = Integer.parseInt(values[0]);
	            numeroColumnas = Integer.parseInt(values[1]);
	            restricciones = new int[numeroFilas][numeroColumnas];
	            costos = new int[numeroColumnas];
	            fs = false;
	        } else {
	            if (ss) {
	                for (int i = 0; i < values.length; i++) {
	                    costos[cc++] = Integer.parseInt(values[i]);
	                }
	                ss = cc < numeroColumnas;
	            } else {
	                if (rc == 0) {
	                    rc = Integer.parseInt(values[0]);
	                } else {
	                    for (int i = 0; i < values.length; i++) {
	                        restricciones[rs][Integer.parseInt(values[i]) - 1] = 1;
	                        cr++;
	                    }
	                    if (rc <= cr) {
	                        rs++;
	                        rc = 0;
	                        cr = 0;
	                    }
	                }
	            }
	        }
	    }

	public static int cargaTiposDeBinarizaciones ()
	{
		int i = 0;
		i++;
		mTiposDeBinarizacion.put(i, "sshape1");
		i++;
		mTiposDeBinarizacion.put(i, "sshape2");
		i++;
		mTiposDeBinarizacion.put(i, "sshape3");
		i++;
		mTiposDeBinarizacion.put(i, "sshape4");
		i++;
		mTiposDeBinarizacion.put(i, "vshape1");
		i++;
		mTiposDeBinarizacion.put(i, "vshape2");
		i++;
		mTiposDeBinarizacion.put(i, "vshape3");
		i++;
		mTiposDeBinarizacion.put(i, "vshape4");
		i++;
		mTiposDeBinarizacion.put(i, "vshape5");

		return 0;
	}
	
	public static int cargaTiposDeDiscretizaciones ()
	{
		int i = 0;
		 //lista de columnas restricciones, con su respectivo índice
		i++;
		mDiscretizaciones.put(i, "standard");
		i++;
		mDiscretizaciones.put(i, "complement");
		i++;
		mDiscretizaciones.put(i, "staticProbability");
		i++;
		mDiscretizaciones.put(i, "elitist");

		return 0;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int cantNidos = 25, iteracionIntervencionML = 200;
		float probDescub = (float) 0.25;
		int numeroIteraciones = 2500, cantEjecuciones=15;
		try {

			cargaInstancias_mochila();

//			cargaTiposDeBinarizaciones();
//			cargaTiposDeDiscretizaciones();
			
			for (int numInstanciaMochila = 1; numInstanciaMochila<= mInstanciasMochilas.size(); numInstanciaMochila++){
				CuckooSearch CuckooSearch_Knapsack = new CuckooSearch(
						cantNidos, 
						probDescub, 
						numeroIteraciones, 
						mInstanciasMochilas.get(numInstanciaMochila),
						"standard", 
						"sshape3",
						iteracionIntervencionML,
						cantEjecuciones
						);

				CuckooSearch_Knapsack.ejecutar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
