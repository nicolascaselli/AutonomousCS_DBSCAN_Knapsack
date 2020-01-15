package ncb.phd.pucv.KnapSack;

import java.util.ArrayList;

public class Knapsack {

    private  Knapsack INSTANCE;
    private  ArrayList<Objeto> objetos = new ArrayList<>();
   	private  int capacidadMochila = 269;//15;
   	private  double valorOptimo, valorOptimoObtenido;
	private  int costoActual;
   	private  int pesoActual;
   	private String nombreInstancia;
   	
   	public double getValorOptimoObtenido() {
		return valorOptimoObtenido;
	}

	public void setValorOptimoObtenido(double valorOptimoObtenido) {
		this.valorOptimoObtenido = valorOptimoObtenido;
	}
	public String getNombreInstancia() {
		return nombreInstancia;
	}

	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}

	public int getCostoActual() {
		return costoActual;
	}

	public  void setCostoActual(int costoAct) {
		costoActual = costoAct;
	}

	public  int getPesoActual() {
		return pesoActual;
	}

	public void setPesoActual(int pesoAct) {
		pesoActual = pesoAct;
	}

	public double getValorOptimo() {
		return valorOptimo;
	}

	public void setValorOptimo(double valorOpt) {
		valorOptimo = valorOpt;
	}

	//    protected final long seed =  System.currentTimeMillis();
//    protected final Random rnd = new Random(seed); 
    public  ArrayList<Objeto> getObjetos() {
		return objetos;
	}

	public  void setObjetos(ArrayList<Objeto> objts) {
		objetos = objts;
	}



    public int getCapacidadmochila() {
		return capacidadMochila;
	}
    
    public void setCapacidadMochila( int capacidadTotalMochila) {
    	capacidadMochila = capacidadTotalMochila;
    }

	public Knapsack() {
//		capacidadMochila = 269;
//		createInstance();
    }
	
	public Knapsack(int capacidadKnapsac, ArrayList<Objeto> elementos, int optimoConocido) {
		capacidadMochila = capacidadKnapsac;
		objetos = elementos;
		valorOptimo = optimoConocido;
		
	}

    public  Knapsack getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
 

    private void createInstance() {
        synchronized (Knapsack.class) {

            if (INSTANCE == null) {
                INSTANCE = new Knapsack();
//f1 - dimension 10 optimo=295  - w 269
                objetos.add(new Objeto(55, 95));
                objetos.add(new Objeto(10, 4));
                objetos.add(new Objeto(47, 60));
                objetos.add(new Objeto(5, 32));
                objetos.add(new Objeto(4, 23));
                objetos.add(new Objeto(50, 72));
                objetos.add(new Objeto(8, 80));
                objetos.add(new Objeto(61, 62));
                objetos.add(new Objeto(85, 65));
                objetos.add(new Objeto(87, 46));
                ///////////////////////////////////

                //f2 - dimension 20   optimo=1024  b = 878.
//                objetos.add(new Objeto(44, 92));
//                objetos.add(new Objeto(46, 4));
//                objetos.add(new Objeto(90, 43));
//                objetos.add(new Objeto(72, 83));
//                objetos.add(new Objeto(91, 84));
//                objetos.add(new Objeto(40, 68));
//                objetos.add(new Objeto(75, 92));
//                objetos.add(new Objeto(35, 82));
//                objetos.add(new Objeto(8, 6));
//                objetos.add(new Objeto(54, 44));
//                objetos.add(new Objeto(78, 32));
//                objetos.add(new Objeto(40, 18));
//                objetos.add(new Objeto(77, 56));
//                objetos.add(new Objeto(15, 83));
//                objetos.add(new Objeto(61, 25));
//                objetos.add(new Objeto(17, 96));
//                objetos.add(new Objeto(75, 70));
//                objetos.add(new Objeto(29, 48));
//                objetos.add(new Objeto(75, 14));
//                objetos.add(new Objeto(63, 58));
//                
                // f3 dimension 4 optimo 35  b=20
//                objetos.add(new Objeto(9, 6));
//                objetos.add(new Objeto(11, 5));
//                objetos.add(new Objeto(13, 9));
//                objetos.add(new Objeto(15, 7));

// 
//                objetos.add(new Objeto( 6, 2));
//            objetos.add(new Objeto( 10, 4));
//           objetos.add(new Objeto(12, 6));
//            objetos.add(new Objeto( 13, 7));

                //f5 dimension 15 optimo 481.0694 b=375
//                objetos.add(new Objeto(0.125126, 56.358531));
//                objetos.add(new Objeto(19.330424, 80.87405));
//                objetos.add(new Objeto(58.500931, 47.987304));
//                objetos.add(new Objeto(35.029145, 89.59624));
//                objetos.add(new Objeto(82.284005, 74.660482));
//                objetos.add(new Objeto(17.41081, 85.894345));
//                objetos.add(new Objeto(71.050142, 51.353496));
//                objetos.add(new Objeto(30.399487, 1.498459));
//                objetos.add(new Objeto(9.140294, 36.445204));
//                objetos.add(new Objeto(14.731285, 16.589862));
//                objetos.add(new Objeto(98.852504, 44.569231));
//                objetos.add(new Objeto(11.908322, 0.466933));
//                objetos.add(new Objeto(0.89114, 37.788018));
//                objetos.add(new Objeto(53.166295, 57.118442));
//                objetos.add(new Objeto(60.176397, 60.716575));

                //f6 dimension 10 optimo 52 b=60

//                objetos.add(new Objeto(20, 30));
//                objetos.add(new Objeto(18, 25));
//                objetos.add(new Objeto(17, 20));
//                objetos.add(new Objeto(15, 18));
//                objetos.add(new Objeto(15, 17));
//                objetos.add(new Objeto(10, 11));
//                objetos.add(new Objeto(5, 5));
//                objetos.add(new Objeto(3, 2));
//                objetos.add(new Objeto(1, 1));
//                objetos.add(new Objeto(1, 1));

                //f7 dimensión 7 optimo 107 b=50

//                objetos.add(new Objeto(70, 31));
//                objetos.add(new Objeto(20, 10));
//                objetos.add(new Objeto(39, 20));
//                objetos.add(new Objeto(37, 19));
//                objetos.add(new Objeto(7, 4));
//                objetos.add(new Objeto(5, 3));
//                objetos.add(new Objeto(10, 6));

//f8 dimensión 7 optimo 9767  b=10000
//                objetos.add(new Objeto(81,983));
//                objetos.add(new Objeto(980,982));
//                objetos.add(new Objeto(979,981));
//                objetos.add(new Objeto(978,980));
//                objetos.add(new Objeto(977,979));
//                objetos.add(new Objeto(976,978));
//                objetos.add(new Objeto(487,488));
//                objetos.add(new Objeto(974,976));
//                objetos.add(new Objeto(970,972));
//                objetos.add(new Objeto(485,486));
//                objetos.add(new Objeto(485,486));
//                objetos.add(new Objeto(970,972));
//                objetos.add(new Objeto(970,972));
//                objetos.add(new Objeto(484,485));
//                objetos.add(new Objeto(484,485));
//                objetos.add(new Objeto(976,969));
//                objetos.add(new Objeto(974,966));
//                objetos.add(new Objeto(482,483));
//                objetos.add(new Objeto(962,964));
//                objetos.add(new Objeto(961,963));
//                objetos.add(new Objeto(959,961));
//                objetos.add(new Objeto(958,958));
//                objetos.add(new Objeto(857,959));


                //f9  dimension 5 optimo 130 b=80
//                objetos.add(new Objeto(33,15));
//                objetos.add(new Objeto(24,20));
//                objetos.add(new Objeto(36,17));
//                objetos.add(new Objeto(37,8));
//                objetos.add(new Objeto(12,31));

                //f10 dimension 20 optimo 1025 b=879

//                objetos.add(new Objeto(91, 84));
//                objetos.add(new Objeto(72, 83));
//                objetos.add(new Objeto(90, 43));
//                objetos.add(new Objeto(46, 4));
//                objetos.add(new Objeto(55, 44));
//                objetos.add(new Objeto(8, 6));
//                objetos.add(new Objeto(35, 82));
//                objetos.add(new Objeto(75, 92));
//                objetos.add(new Objeto(61, 25));
//                objetos.add(new Objeto(15, 83));
//                objetos.add(new Objeto(77, 56));
//                objetos.add(new Objeto(40, 18));
//                objetos.add(new Objeto(63, 58));
//                objetos.add(new Objeto(75, 14));
//                objetos.add(new Objeto(29, 48));
//                objetos.add(new Objeto(75, 70));
//                objetos.add(new Objeto(17, 96));
//                objetos.add(new Objeto(78, 32));
//                objetos.add(new Objeto(40, 68));
//                objetos.add(new Objeto(44, 92));
            }
        }
    }
}
