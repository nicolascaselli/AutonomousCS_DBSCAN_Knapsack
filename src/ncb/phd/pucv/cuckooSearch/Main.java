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
	public static String nombreInstancia, rutaInstancias;
	public static int costos[];
	public static int restricciones[][];
	public static int numeroFilas, numeroColumnas, capacidadMochila, valorOptimoInstancia;
	public static Map<Integer, Knapsack> mInstanciasMochilas = new HashMap<Integer, Knapsack>(); 
//	public static Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
	public static Map<Integer, String> mTiposDeBinarizacion  = new HashMap<Integer, String>(); 
	public static Map<Integer, String> mDiscretizaciones = new HashMap<Integer, String>();
	public static int cantNidos = 25;
	public static float probDescub = (float)0.25;
	public static int numeroIteraciones = 2500; 
	public static int cantInstancias = 2, conteoInstancias = 1;
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
    private  final static Logger logger = Logger.getLogger(CuckooSearch.class.getName());
	
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
        elementos.add(new Objeto(0.125126, 56.358531));//0.125126 56.358531
        elementos.add(new Objeto(19.330424, 80.874050));//19.330424 80.874050
        elementos.add(new Objeto(58.500931, 47.987304));//58.500931 47.987304
        elementos.add(new Objeto(35.029145, 89.596240));//35.029145 89.596240
        elementos.add(new Objeto(82.284005, 74.660482));//82.284005 74.660482
        elementos.add(new Objeto(17.41081, 85.894345));//17.410810 85.894345
        elementos.add(new Objeto(71.050142, 51.353496));//71.050142 51.353496
        elementos.add(new Objeto(30.399487, 1.498459));//30.399487 1.498459
        elementos.add(new Objeto(9.140294, 36.445204));//9.140294 36.445204
        elementos.add(new Objeto(14.731285, 16.589862));//14.731285 16.589862
        elementos.add(new Objeto(98.852504, 44.569231));//98.852504 44.569231
        elementos.add(new Objeto(11.908322, 0.466933));//11.908322 0.466933
        elementos.add(new Objeto(0.891140, 37.788018));//0.891140 37.788018
        elementos.add(new Objeto(53.166295, 57.118442));//53.166295 57.118442
        elementos.add(new Objeto(60.176397, 60.716575));//60.176397 60.716575
        
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
        elementos.add(new Objeto(981,983));
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
	
    public static  int cargaInstancia_mochila_fh2_1000()
 	{
 		ArrayList<Objeto> elementos = new ArrayList<>();
 		Knapsack mochila = new Knapsack();
 		int i = 0;

		//Hf2-1000  dimension 1000 optimo 9052 b=5002
		elementos = new ArrayList<>();
		mochila = new Knapsack();
		i++;
		
		elementos.add(new Objeto(482,485));
		elementos.add(new Objeto(257,326));
		elementos.add(new Objeto(286,248));
		elementos.add(new Objeto(517,421));
		elementos.add(new Objeto(404,322));
		elementos.add(new Objeto(713,795));
		elementos.add(new Objeto(45,43));
		elementos.add(new Objeto(924,845));
		elementos.add(new Objeto(873,955));
		elementos.add(new Objeto(160,252));
		elementos.add(new Objeto(1,9));
		elementos.add(new Objeto(838,901));
		elementos.add(new Objeto(40,122));
		elementos.add(new Objeto(58,94));
		elementos.add(new Objeto(676,738));
		elementos.add(new Objeto(627,574));
		elementos.add(new Objeto(766,715));
		elementos.add(new Objeto(862,882));
		elementos.add(new Objeto(405,367));
		elementos.add(new Objeto(923,984));
		elementos.add(new Objeto(379,299));
		elementos.add(new Objeto(461,433));
		elementos.add(new Objeto(612,682));
		elementos.add(new Objeto(133,72));
		elementos.add(new Objeto(813,874));
		elementos.add(new Objeto(97,138));
		elementos.add(new Objeto(908,856));
		elementos.add(new Objeto(165,145));
		elementos.add(new Objeto(996,995));
		elementos.add(new Objeto(623,529));
		elementos.add(new Objeto(220,199));
		elementos.add(new Objeto(298,277));
		elementos.add(new Objeto(157,97));
		elementos.add(new Objeto(723,719));
		elementos.add(new Objeto(144,242));
		elementos.add(new Objeto(48,107));
		elementos.add(new Objeto(129,122));
		elementos.add(new Objeto(148,70));
		elementos.add(new Objeto(35,98));
		elementos.add(new Objeto(644,600));
		elementos.add(new Objeto(632,645));
		elementos.add(new Objeto(272,267));
		elementos.add(new Objeto(1040,972));
		elementos.add(new Objeto(977,895));
		elementos.add(new Objeto(312,213));
		elementos.add(new Objeto(778,748));
		elementos.add(new Objeto(567,487));
		elementos.add(new Objeto(965,923));
		elementos.add(new Objeto(1,29));
		elementos.add(new Objeto(616,674));
		elementos.add(new Objeto(569,540));
		elementos.add(new Objeto(628,554));
		elementos.add(new Objeto(493,467));
		elementos.add(new Objeto(76,46));
		elementos.add(new Objeto(733,710));
		elementos.add(new Objeto(575,553));
		elementos.add(new Objeto(288,191));
		elementos.add(new Objeto(775,724));
		elementos.add(new Objeto(723,730));
		elementos.add(new Objeto(912,988));
		elementos.add(new Objeto(64,90));
		elementos.add(new Objeto(354,340));
		elementos.add(new Objeto(565,549));
		elementos.add(new Objeto(210,196));
		elementos.add(new Objeto(922,865));
		elementos.add(new Objeto(775,678));
		elementos.add(new Objeto(566,570));
		elementos.add(new Objeto(934,936));
		elementos.add(new Objeto(626,722));
		elementos.add(new Objeto(742,651));
		elementos.add(new Objeto(194,123));
		elementos.add(new Objeto(485,431));
		elementos.add(new Objeto(483,508));
		elementos.add(new Objeto(617,585));
		elementos.add(new Objeto(876,853));
		elementos.add(new Objeto(653,642));
		elementos.add(new Objeto(896,992));
		elementos.add(new Objeto(652,725));
		elementos.add(new Objeto(220,286));
		elementos.add(new Objeto(727,812));
		elementos.add(new Objeto(900,859));
		elementos.add(new Objeto(563,663));
		elementos.add(new Objeto(56,88));
		elementos.add(new Objeto(157,179));
		elementos.add(new Objeto(280,187));
		elementos.add(new Objeto(537,619));
		elementos.add(new Objeto(284,261));
		elementos.add(new Objeto(920,846));
		elementos.add(new Objeto(124,192));
		elementos.add(new Objeto(239,261));
		elementos.add(new Objeto(459,514));
		elementos.add(new Objeto(931,886));
		elementos.add(new Objeto(504,530));
		elementos.add(new Objeto(910,849));
		elementos.add(new Objeto(382,294));
		elementos.add(new Objeto(795,799));
		elementos.add(new Objeto(485,391));
		elementos.add(new Objeto(351,330));
		elementos.add(new Objeto(289,298));
		elementos.add(new Objeto(865,790));
		elementos.add(new Objeto(250,275));
		elementos.add(new Objeto(738,826));
		elementos.add(new Objeto(75,72));
		elementos.add(new Objeto(788,866));
		elementos.add(new Objeto(947,951));
		elementos.add(new Objeto(651,748));
		elementos.add(new Objeto(595,685));
		elementos.add(new Objeto(993,956));
		elementos.add(new Objeto(663,564));
		elementos.add(new Objeto(265,183));
		elementos.add(new Objeto(338,400));
		elementos.add(new Objeto(637,721));
		elementos.add(new Objeto(206,207));
		elementos.add(new Objeto(328,323));
		elementos.add(new Objeto(618,611));
		elementos.add(new Objeto(64,116));
		elementos.add(new Objeto(139,109));
		elementos.add(new Objeto(806,795));
		elementos.add(new Objeto(412,343));
		elementos.add(new Objeto(912,862));
		elementos.add(new Objeto(715,685));
		elementos.add(new Objeto(1,10));
		elementos.add(new Objeto(929,881));
		elementos.add(new Objeto(1025,984));
		elementos.add(new Objeto(410,403));
		elementos.add(new Objeto(273,360));
		elementos.add(new Objeto(507,449));
		elementos.add(new Objeto(450,541));
		elementos.add(new Objeto(174,272));
		elementos.add(new Objeto(882,877));
		elementos.add(new Objeto(427,359));
		elementos.add(new Objeto(787,707));
		elementos.add(new Objeto(355,308));
		elementos.add(new Objeto(734,770));
		elementos.add(new Objeto(1,30));
		elementos.add(new Objeto(248,208));
		elementos.add(new Objeto(397,311));
		elementos.add(new Objeto(159,100));
		elementos.add(new Objeto(874,939));
		elementos.add(new Objeto(449,422));
		elementos.add(new Objeto(857,785));
		elementos.add(new Objeto(286,370));
		elementos.add(new Objeto(976,989));
		elementos.add(new Objeto(908,969));
		elementos.add(new Objeto(103,143));
		elementos.add(new Objeto(1071,972));
		elementos.add(new Objeto(1,28));
		elementos.add(new Objeto(117,61));
		elementos.add(new Objeto(697,638));
		elementos.add(new Objeto(424,348));
		elementos.add(new Objeto(439,347));
		elementos.add(new Objeto(74,66));
		elementos.add(new Objeto(442,391));
		elementos.add(new Objeto(568,638));
		elementos.add(new Objeto(195,295));
		elementos.add(new Objeto(854,826));
		elementos.add(new Objeto(281,196));
		elementos.add(new Objeto(433,449));
		elementos.add(new Objeto(845,855));
		elementos.add(new Objeto(224,143));
		elementos.add(new Objeto(487,487));
		elementos.add(new Objeto(181,140));
		elementos.add(new Objeto(496,564));
		elementos.add(new Objeto(690,615));
		elementos.add(new Objeto(124,135));
		elementos.add(new Objeto(575,564));
		elementos.add(new Objeto(374,360));
		elementos.add(new Objeto(766,793));
		elementos.add(new Objeto(149,163));
		elementos.add(new Objeto(776,859));
		elementos.add(new Objeto(793,760));
		elementos.add(new Objeto(674,711));
		elementos.add(new Objeto(696,662));
		elementos.add(new Objeto(90,159));
		elementos.add(new Objeto(732,660));
		elementos.add(new Objeto(214,268));
		elementos.add(new Objeto(935,948));
		elementos.add(new Objeto(228,315));
		elementos.add(new Objeto(619,676));
		elementos.add(new Objeto(349,341));
		elementos.add(new Objeto(734,689));
		elementos.add(new Objeto(964,894));
		elementos.add(new Objeto(644,706));
		elementos.add(new Objeto(484,490));
		elementos.add(new Objeto(491,478));
		elementos.add(new Objeto(628,671));
		elementos.add(new Objeto(1009,932));
		elementos.add(new Objeto(944,899));
		elementos.add(new Objeto(315,237));
		elementos.add(new Objeto(92,187));
		elementos.add(new Objeto(554,472));
		elementos.add(new Objeto(757,772));
		elementos.add(new Objeto(88,98));
		elementos.add(new Objeto(952,906));
		elementos.add(new Objeto(818,911));
		elementos.add(new Objeto(628,635));
		elementos.add(new Objeto(316,225));
		elementos.add(new Objeto(818,823));
		elementos.add(new Objeto(255,164));
		elementos.add(new Objeto(432,343));
		elementos.add(new Objeto(763,732));
		elementos.add(new Objeto(490,502));
		elementos.add(new Objeto(721,740));
		elementos.add(new Objeto(587,576));
		elementos.add(new Objeto(530,612));
		elementos.add(new Objeto(992,902));
		elementos.add(new Objeto(491,454));
		elementos.add(new Objeto(343,411));
		elementos.add(new Objeto(880,973));
		elementos.add(new Objeto(645,703));
		elementos.add(new Objeto(761,850));
		elementos.add(new Objeto(152,77));
		elementos.add(new Objeto(241,220));
		elementos.add(new Objeto(727,802));
		elementos.add(new Objeto(352,403));
		elementos.add(new Objeto(246,181));
		elementos.add(new Objeto(1,10));
		elementos.add(new Objeto(479,525));
		elementos.add(new Objeto(958,919));
		elementos.add(new Objeto(749,668));
		elementos.add(new Objeto(551,527));
		elementos.add(new Objeto(538,462));
		elementos.add(new Objeto(319,291));
		elementos.add(new Objeto(580,605));
		elementos.add(new Objeto(501,457));
		elementos.add(new Objeto(487,405));
		elementos.add(new Objeto(349,417));
		elementos.add(new Objeto(252,279));
		elementos.add(new Objeto(731,685));
		elementos.add(new Objeto(637,596));
		elementos.add(new Objeto(339,307));
		elementos.add(new Objeto(257,224));
		elementos.add(new Objeto(271,322));
		elementos.add(new Objeto(740,840));
		elementos.add(new Objeto(1002,975));
		elementos.add(new Objeto(404,401));
		elementos.add(new Objeto(190,91));
		elementos.add(new Objeto(254,327));
		elementos.add(new Objeto(277,330));
		elementos.add(new Objeto(100,182));
		elementos.add(new Objeto(648,603));
		elementos.add(new Objeto(810,793));
		elementos.add(new Objeto(535,615));
		elementos.add(new Objeto(702,733));
		elementos.add(new Objeto(855,864));
		elementos.add(new Objeto(76,16));
		elementos.add(new Objeto(773,863));
		elementos.add(new Objeto(917,987));
		elementos.add(new Objeto(279,306));
		elementos.add(new Objeto(123,34));
		elementos.add(new Objeto(922,840));
		elementos.add(new Objeto(613,700));
		elementos.add(new Objeto(658,706));
		elementos.add(new Objeto(705,787));
		elementos.add(new Objeto(101,105));
		elementos.add(new Objeto(807,834));
		elementos.add(new Objeto(883,798));
		elementos.add(new Objeto(358,310));
		elementos.add(new Objeto(634,609));
		elementos.add(new Objeto(786,690));
		elementos.add(new Objeto(618,561));
		elementos.add(new Objeto(644,579));
		elementos.add(new Objeto(141,60));
		elementos.add(new Objeto(466,388));
		elementos.add(new Objeto(340,309));
		elementos.add(new Objeto(495,407));
		elementos.add(new Objeto(283,200));
		elementos.add(new Objeto(352,313));
		elementos.add(new Objeto(886,970));
		elementos.add(new Objeto(95,33));
		elementos.add(new Objeto(291,273));
		elementos.add(new Objeto(217,277));
		elementos.add(new Objeto(1007,997));
		elementos.add(new Objeto(1,40));
		elementos.add(new Objeto(219,227));
		elementos.add(new Objeto(764,860));
		elementos.add(new Objeto(1025,940));
		elementos.add(new Objeto(600,608));
		elementos.add(new Objeto(895,990));
		elementos.add(new Objeto(690,590));
		elementos.add(new Objeto(738,806));
		elementos.add(new Objeto(1,52));
		elementos.add(new Objeto(772,801));
		elementos.add(new Objeto(727,764));
		elementos.add(new Objeto(774,710));
		elementos.add(new Objeto(340,386));
		elementos.add(new Objeto(503,593));
		elementos.add(new Objeto(1,50));
		elementos.add(new Objeto(432,494));
		elementos.add(new Objeto(228,156));
		elementos.add(new Objeto(993,936));
		elementos.add(new Objeto(1058,965));
		elementos.add(new Objeto(130,86));
		elementos.add(new Objeto(821,723));
		elementos.add(new Objeto(189,184));
		elementos.add(new Objeto(943,868));
		elementos.add(new Objeto(343,340));
		elementos.add(new Objeto(404,412));
		elementos.add(new Objeto(560,487));
		elementos.add(new Objeto(196,209));
		elementos.add(new Objeto(418,346));
		elementos.add(new Objeto(782,860));
		elementos.add(new Objeto(314,307));
		elementos.add(new Objeto(59,95));
		elementos.add(new Objeto(778,821));
		elementos.add(new Objeto(979,941));
		elementos.add(new Objeto(828,821));
		elementos.add(new Objeto(584,587));
		elementos.add(new Objeto(122,201));
		elementos.add(new Objeto(741,665));
		elementos.add(new Objeto(566,527));
		elementos.add(new Objeto(760,812));
		elementos.add(new Objeto(385,424));
		elementos.add(new Objeto(395,459));
		elementos.add(new Objeto(250,343));
		elementos.add(new Objeto(789,836));
		elementos.add(new Objeto(492,506));
		elementos.add(new Objeto(261,179));
		elementos.add(new Objeto(914,916));
		elementos.add(new Objeto(380,430));
		elementos.add(new Objeto(537,596));
		elementos.add(new Objeto(731,808));
		elementos.add(new Objeto(361,269));
		elementos.add(new Objeto(473,512));
		elementos.add(new Objeto(737,811));
		elementos.add(new Objeto(708,685));
		elementos.add(new Objeto(635,567));
		elementos.add(new Objeto(804,776));
		elementos.add(new Objeto(827,855));
		elementos.add(new Objeto(695,718));
		elementos.add(new Objeto(756,853));
		elementos.add(new Objeto(57,97));
		elementos.add(new Objeto(178,146));
		elementos.add(new Objeto(688,746));
		elementos.add(new Objeto(1,15));
		elementos.add(new Objeto(871,965));
		elementos.add(new Objeto(415,512));
		elementos.add(new Objeto(510,480));
		elementos.add(new Objeto(1057,958));
		elementos.add(new Objeto(764,665));
		elementos.add(new Objeto(193,191));
		elementos.add(new Objeto(905,964));
		elementos.add(new Objeto(583,515));
		elementos.add(new Objeto(696,606));
		elementos.add(new Objeto(887,822));
		elementos.add(new Objeto(444,536));
		elementos.add(new Objeto(741,693));
		elementos.add(new Objeto(12,19));
		elementos.add(new Objeto(313,335));
		elementos.add(new Objeto(81,101));
		elementos.add(new Objeto(828,922));
		elementos.add(new Objeto(354,294));
		elementos.add(new Objeto(981,910));
		elementos.add(new Objeto(499,410));
		elementos.add(new Objeto(283,250));
		elementos.add(new Objeto(545,546));
		elementos.add(new Objeto(635,729));
		elementos.add(new Objeto(540,609));
		elementos.add(new Objeto(689,720));
		elementos.add(new Objeto(946,976));
		elementos.add(new Objeto(730,759));
		elementos.add(new Objeto(703,798));
		elementos.add(new Objeto(14,74));
		elementos.add(new Objeto(644,648));
		elementos.add(new Objeto(241,299));
		elementos.add(new Objeto(793,785));
		elementos.add(new Objeto(912,846));
		elementos.add(new Objeto(461,390));
		elementos.add(new Objeto(801,897));
		elementos.add(new Objeto(424,416));
		elementos.add(new Objeto(744,837));
		elementos.add(new Objeto(272,247));
		elementos.add(new Objeto(338,293));
		elementos.add(new Objeto(193,144));
		elementos.add(new Objeto(987,938));
		elementos.add(new Objeto(432,467));
		elementos.add(new Objeto(505,480));
		elementos.add(new Objeto(944,955));
		elementos.add(new Objeto(653,665));
		elementos.add(new Objeto(38,26));
		elementos.add(new Objeto(468,564));
		elementos.add(new Objeto(461,449));
		elementos.add(new Objeto(1,26));
		elementos.add(new Objeto(576,521));
		elementos.add(new Objeto(329,301));
		elementos.add(new Objeto(274,321));
		elementos.add(new Objeto(701,613));
		elementos.add(new Objeto(432,388));
		elementos.add(new Objeto(851,879));
		elementos.add(new Objeto(915,990));
		elementos.add(new Objeto(137,188));
		elementos.add(new Objeto(927,963));
		elementos.add(new Objeto(903,820));
		elementos.add(new Objeto(373,300));
		elementos.add(new Objeto(770,822));
		elementos.add(new Objeto(160,255));
		elementos.add(new Objeto(285,277));
		elementos.add(new Objeto(231,153));
		elementos.add(new Objeto(366,400));
		elementos.add(new Objeto(317,227));
		elementos.add(new Objeto(423,518));
		elementos.add(new Objeto(420,447));
		elementos.add(new Objeto(216,315));
		elementos.add(new Objeto(772,813));
		elementos.add(new Objeto(271,324));
		elementos.add(new Objeto(566,502));
		elementos.add(new Objeto(243,299));
		elementos.add(new Objeto(725,780));
		elementos.add(new Objeto(390,376));
		elementos.add(new Objeto(491,516));
		elementos.add(new Objeto(225,212));
		elementos.add(new Objeto(392,329));
		elementos.add(new Objeto(781,810));
		elementos.add(new Objeto(434,486));
		elementos.add(new Objeto(535,485));
		elementos.add(new Objeto(368,416));
		elementos.add(new Objeto(938,952));
		elementos.add(new Objeto(946,894));
		elementos.add(new Objeto(353,358));
		elementos.add(new Objeto(1,21));
		elementos.add(new Objeto(707,647));
		elementos.add(new Objeto(143,44));
		elementos.add(new Objeto(630,683));
		elementos.add(new Objeto(364,361));
		elementos.add(new Objeto(822,814));
		elementos.add(new Objeto(147,231));
		elementos.add(new Objeto(54,8));
		elementos.add(new Objeto(91,165));
		elementos.add(new Objeto(431,496));
		elementos.add(new Objeto(606,699));
		elementos.add(new Objeto(917,852));
		elementos.add(new Objeto(553,459));
		elementos.add(new Objeto(473,535));
		elementos.add(new Objeto(222,314));
		elementos.add(new Objeto(954,944));
		elementos.add(new Objeto(369,306));
		elementos.add(new Objeto(413,459));
		elementos.add(new Objeto(717,637));
		elementos.add(new Objeto(491,447));
		elementos.add(new Objeto(447,408));
		elementos.add(new Objeto(44,107));
		elementos.add(new Objeto(994,962));
		elementos.add(new Objeto(518,565));
		elementos.add(new Objeto(448,407));
		elementos.add(new Objeto(931,857));
		elementos.add(new Objeto(369,317));
		elementos.add(new Objeto(181,98));
		elementos.add(new Objeto(815,818));
		elementos.add(new Objeto(655,600));
		elementos.add(new Objeto(267,288));
		elementos.add(new Objeto(960,902));
		elementos.add(new Objeto(611,567));
		elementos.add(new Objeto(271,190));
		elementos.add(new Objeto(771,749));
		elementos.add(new Objeto(993,908));
		elementos.add(new Objeto(359,276));
		elementos.add(new Objeto(810,748));
		elementos.add(new Objeto(563,592));
		elementos.add(new Objeto(360,393));
		elementos.add(new Objeto(264,363));
		elementos.add(new Objeto(199,163));
		elementos.add(new Objeto(1074,983));
		elementos.add(new Objeto(131,179));
		elementos.add(new Objeto(41,16));
		elementos.add(new Objeto(423,478));
		elementos.add(new Objeto(197,259));
		elementos.add(new Objeto(779,868));
		elementos.add(new Objeto(344,295));
		elementos.add(new Objeto(536,596));
		elementos.add(new Objeto(140,71));
		elementos.add(new Objeto(669,622));
		elementos.add(new Objeto(938,864));
		elementos.add(new Objeto(715,644));
		elementos.add(new Objeto(67,5));
		elementos.add(new Objeto(321,365));
		elementos.add(new Objeto(434,429));
		elementos.add(new Objeto(19,64));
		elementos.add(new Objeto(928,854));
		elementos.add(new Objeto(759,770));
		elementos.add(new Objeto(178,88));
		elementos.add(new Objeto(160,157));
		elementos.add(new Objeto(277,368));
		elementos.add(new Objeto(467,421));
		elementos.add(new Objeto(653,719));
		elementos.add(new Objeto(330,271));
		elementos.add(new Objeto(966,975));
		elementos.add(new Objeto(817,816));
		elementos.add(new Objeto(1045,962));
		elementos.add(new Objeto(508,535));
		elementos.add(new Objeto(492,438));
		elementos.add(new Objeto(743,696));
		elementos.add(new Objeto(884,881));
		elementos.add(new Objeto(259,318));
		elementos.add(new Objeto(56,45));
		elementos.add(new Objeto(1,52));
		elementos.add(new Objeto(421,496));
		elementos.add(new Objeto(270,313));
		elementos.add(new Objeto(99,138));
		elementos.add(new Objeto(429,334));
		elementos.add(new Objeto(1004,988));
		elementos.add(new Objeto(512,508));
		elementos.add(new Objeto(702,770));
		elementos.add(new Objeto(657,751));
		elementos.add(new Objeto(862,780));
		elementos.add(new Objeto(516,441));
		elementos.add(new Objeto(246,207));
		elementos.add(new Objeto(592,516));
		elementos.add(new Objeto(641,674));
		elementos.add(new Objeto(390,408));
		elementos.add(new Objeto(831,920));
		elementos.add(new Objeto(522,573));
		elementos.add(new Objeto(611,634));
		elementos.add(new Objeto(825,878));
		elementos.add(new Objeto(929,990));
		elementos.add(new Objeto(734,826));
		elementos.add(new Objeto(718,696));
		elementos.add(new Objeto(883,800));
		elementos.add(new Objeto(355,411));
		elementos.add(new Objeto(426,422));
		elementos.add(new Objeto(668,707));
		elementos.add(new Objeto(235,334));
		elementos.add(new Objeto(1080,986));
		elementos.add(new Objeto(397,494));
		elementos.add(new Objeto(137,150));
		elementos.add(new Objeto(648,562));
		elementos.add(new Objeto(342,380));
		elementos.add(new Objeto(799,832));
		elementos.add(new Objeto(1067,979));
		elementos.add(new Objeto(313,376));
		elementos.add(new Objeto(452,511));
		elementos.add(new Objeto(441,414));
		elementos.add(new Objeto(214,160));
		elementos.add(new Objeto(1059,982));
		elementos.add(new Objeto(260,238));
		elementos.add(new Objeto(579,490));
		elementos.add(new Objeto(897,899));
		elementos.add(new Objeto(316,251));
		elementos.add(new Objeto(526,526));
		elementos.add(new Objeto(909,897));
		elementos.add(new Objeto(139,39));
		elementos.add(new Objeto(634,657));
		elementos.add(new Objeto(384,452));
		elementos.add(new Objeto(577,538));
		elementos.add(new Objeto(840,831));
		elementos.add(new Objeto(509,460));
		elementos.add(new Objeto(262,185));
		elementos.add(new Objeto(934,934));
		elementos.add(new Objeto(513,440));
		elementos.add(new Objeto(556,490));
		elementos.add(new Objeto(97,168));
		elementos.add(new Objeto(107,71));
		elementos.add(new Objeto(937,977));
		elementos.add(new Objeto(370,458));
		elementos.add(new Objeto(193,204));
		elementos.add(new Objeto(802,803));
		elementos.add(new Objeto(134,139));
		elementos.add(new Objeto(390,399));
		elementos.add(new Objeto(439,420));
		elementos.add(new Objeto(1034,991));
		elementos.add(new Objeto(636,578));
		elementos.add(new Objeto(1008,972));
		elementos.add(new Objeto(191,177));
		elementos.add(new Objeto(429,398));
		elementos.add(new Objeto(445,348));
		elementos.add(new Objeto(483,496));
		elementos.add(new Objeto(347,276));
		elementos.add(new Objeto(769,825));
		elementos.add(new Objeto(540,614));
		elementos.add(new Objeto(212,121));
		elementos.add(new Objeto(789,730));
		elementos.add(new Objeto(317,341));
		elementos.add(new Objeto(381,284));
		elementos.add(new Objeto(714,698));
		elementos.add(new Objeto(24,17));
		elementos.add(new Objeto(570,546));
		elementos.add(new Objeto(771,672));
		elementos.add(new Objeto(1052,974));
		elementos.add(new Objeto(776,866));
		elementos.add(new Objeto(290,377));
		elementos.add(new Objeto(470,559));
		elementos.add(new Objeto(367,340));
		elementos.add(new Objeto(626,527));
		elementos.add(new Objeto(879,779));
		elementos.add(new Objeto(688,761));
		elementos.add(new Objeto(553,483));
		elementos.add(new Objeto(112,92));
		elementos.add(new Objeto(440,390));
		elementos.add(new Objeto(510,583));
		elementos.add(new Objeto(741,661));
		elementos.add(new Objeto(473,391));
		elementos.add(new Objeto(289,336));
		elementos.add(new Objeto(574,661));
		elementos.add(new Objeto(85,125));
		elementos.add(new Objeto(36,89));
		elementos.add(new Objeto(362,275));
		elementos.add(new Objeto(310,359));
		elementos.add(new Objeto(129,178));
		elementos.add(new Objeto(763,780));
		elementos.add(new Objeto(31,42));
		elementos.add(new Objeto(69,5));
		elementos.add(new Objeto(470,380));
		elementos.add(new Objeto(739,772));
		elementos.add(new Objeto(726,816));
		elementos.add(new Objeto(57,9));
		elementos.add(new Objeto(476,484));
		elementos.add(new Objeto(498,545));
		elementos.add(new Objeto(669,589));
		elementos.add(new Objeto(112,157));
		elementos.add(new Objeto(934,901));
		elementos.add(new Objeto(854,777));
		elementos.add(new Objeto(112,74));
		elementos.add(new Objeto(246,247));
		elementos.add(new Objeto(1,55));
		elementos.add(new Objeto(481,479));
		elementos.add(new Objeto(883,785));
		elementos.add(new Objeto(1051,958));
		elementos.add(new Objeto(763,680));
		elementos.add(new Objeto(417,393));
		elementos.add(new Objeto(900,944));
		elementos.add(new Objeto(573,596));
		elementos.add(new Objeto(601,619));
		elementos.add(new Objeto(588,611));
		elementos.add(new Objeto(900,852));
		elementos.add(new Objeto(327,283));
		elementos.add(new Objeto(199,299));
		elementos.add(new Objeto(847,766));
		elementos.add(new Objeto(240,312));
		elementos.add(new Objeto(862,826));
		elementos.add(new Objeto(635,726));
		elementos.add(new Objeto(680,733));
		elementos.add(new Objeto(806,805));
		elementos.add(new Objeto(767,679));
		elementos.add(new Objeto(495,403));
		elementos.add(new Objeto(870,964));
		elementos.add(new Objeto(259,337));
		elementos.add(new Objeto(476,541));
		elementos.add(new Objeto(512,478));
		elementos.add(new Objeto(467,369));
		elementos.add(new Objeto(1001,946));
		elementos.add(new Objeto(798,821));
		elementos.add(new Objeto(819,833));
		elementos.add(new Objeto(835,837));
		elementos.add(new Objeto(502,490));
		elementos.add(new Objeto(119,37));
		elementos.add(new Objeto(652,594));
		elementos.add(new Objeto(82,129));
		elementos.add(new Objeto(242,258));
		elementos.add(new Objeto(215,246));
		elementos.add(new Objeto(291,362));
		elementos.add(new Objeto(687,755));
		elementos.add(new Objeto(550,504));
		elementos.add(new Objeto(1,71));
		elementos.add(new Objeto(249,177));
		elementos.add(new Objeto(203,176));
		elementos.add(new Objeto(865,803));
		elementos.add(new Objeto(189,237));
		elementos.add(new Objeto(758,677));
		elementos.add(new Objeto(90,33));
		elementos.add(new Objeto(188,115));
		elementos.add(new Objeto(621,597));
		elementos.add(new Objeto(807,769));
		elementos.add(new Objeto(481,536));
		elementos.add(new Objeto(1031,956));
		elementos.add(new Objeto(349,341));
		elementos.add(new Objeto(222,184));
		elementos.add(new Objeto(260,234));
		elementos.add(new Objeto(551,569));
		elementos.add(new Objeto(245,216));
		elementos.add(new Objeto(631,537));
		elementos.add(new Objeto(183,84));
		elementos.add(new Objeto(873,810));
		elementos.add(new Objeto(656,657));
		elementos.add(new Objeto(5,33));
		elementos.add(new Objeto(137,159));
		elementos.add(new Objeto(579,520));
		elementos.add(new Objeto(656,668));
		elementos.add(new Objeto(373,287));
		elementos.add(new Objeto(232,229));
		elementos.add(new Objeto(352,374));
		elementos.add(new Objeto(428,474));
		elementos.add(new Objeto(598,672));
		elementos.add(new Objeto(943,915));
		elementos.add(new Objeto(582,511));
		elementos.add(new Objeto(120,123));
		elementos.add(new Objeto(573,569));
		elementos.add(new Objeto(218,199));
		elementos.add(new Objeto(347,396));
		elementos.add(new Objeto(628,715));
		elementos.add(new Objeto(1,59));
		elementos.add(new Objeto(642,610));
		elementos.add(new Objeto(959,917));
		elementos.add(new Objeto(983,927));
		elementos.add(new Objeto(186,270));
		elementos.add(new Objeto(60,117));
		elementos.add(new Objeto(513,516));
		elementos.add(new Objeto(143,215));
		elementos.add(new Objeto(234,216));
		elementos.add(new Objeto(290,237));
		elementos.add(new Objeto(597,615));
		elementos.add(new Objeto(452,499));
		elementos.add(new Objeto(521,477));
		elementos.add(new Objeto(837,760));
		elementos.add(new Objeto(267,285));
		elementos.add(new Objeto(20,56));
		elementos.add(new Objeto(1027,966));
		elementos.add(new Objeto(690,622));
		elementos.add(new Objeto(235,221));
		elementos.add(new Objeto(199,194));
		elementos.add(new Objeto(186,91));
		elementos.add(new Objeto(374,347));
		elementos.add(new Objeto(244,252));
		elementos.add(new Objeto(424,473));
		elementos.add(new Objeto(438,516));
		elementos.add(new Objeto(385,385));
		elementos.add(new Objeto(337,357));
		elementos.add(new Objeto(945,958));
		elementos.add(new Objeto(686,665));
		elementos.add(new Objeto(698,792));
		elementos.add(new Objeto(16,64));
		elementos.add(new Objeto(464,420));
		elementos.add(new Objeto(655,671));
		elementos.add(new Objeto(911,968));
		elementos.add(new Objeto(839,877));
		elementos.add(new Objeto(144,177));
		elementos.add(new Objeto(535,523));
		elementos.add(new Objeto(65,139));
		elementos.add(new Objeto(230,152));
		elementos.add(new Objeto(806,732));
		elementos.add(new Objeto(312,246));
		elementos.add(new Objeto(578,565));
		elementos.add(new Objeto(699,740));
		elementos.add(new Objeto(817,902));
		elementos.add(new Objeto(105,17));
		elementos.add(new Objeto(886,973));
		elementos.add(new Objeto(360,298));
		elementos.add(new Objeto(658,611));
		elementos.add(new Objeto(53,32));
		elementos.add(new Objeto(1,11));
		elementos.add(new Objeto(319,337));
		elementos.add(new Objeto(36,136));
		elementos.add(new Objeto(879,889));
		elementos.add(new Objeto(595,522));
		elementos.add(new Objeto(721,805));
		elementos.add(new Objeto(80,28));
		elementos.add(new Objeto(981,931));
		elementos.add(new Objeto(838,757));
		elementos.add(new Objeto(654,609));
		elementos.add(new Objeto(480,544));
		elementos.add(new Objeto(973,905));
		elementos.add(new Objeto(647,595));
		elementos.add(new Objeto(243,246));
		elementos.add(new Objeto(152,113));
		elementos.add(new Objeto(183,175));
		elementos.add(new Objeto(140,156));
		elementos.add(new Objeto(263,244));
		elementos.add(new Objeto(856,883));
		elementos.add(new Objeto(338,265));
		elementos.add(new Objeto(792,883));
		elementos.add(new Objeto(403,462));
		elementos.add(new Objeto(560,485));
		elementos.add(new Objeto(238,272));
		elementos.add(new Objeto(214,204));
		elementos.add(new Objeto(1011,984));
		elementos.add(new Objeto(453,409));
		elementos.add(new Objeto(330,398));
		elementos.add(new Objeto(819,909));
		elementos.add(new Objeto(469,370));
		elementos.add(new Objeto(882,882));
		elementos.add(new Objeto(279,214));
		elementos.add(new Objeto(811,766));
		elementos.add(new Objeto(82,35));
		elementos.add(new Objeto(341,421));
		elementos.add(new Objeto(271,271));
		elementos.add(new Objeto(397,370));
		elementos.add(new Objeto(876,848));
		elementos.add(new Objeto(128,50));
		elementos.add(new Objeto(1016,976));
		elementos.add(new Objeto(243,270));
		elementos.add(new Objeto(843,796));
		elementos.add(new Objeto(949,963));
		elementos.add(new Objeto(451,379));
		elementos.add(new Objeto(1091,991));
		elementos.add(new Objeto(594,551));
		elementos.add(new Objeto(867,897));
		elementos.add(new Objeto(353,307));
		elementos.add(new Objeto(471,421));
		elementos.add(new Objeto(100,74));
		elementos.add(new Objeto(401,388));
		elementos.add(new Objeto(591,559));
		elementos.add(new Objeto(884,980));
		elementos.add(new Objeto(607,615));
		elementos.add(new Objeto(651,681));
		elementos.add(new Objeto(209,231));
		elementos.add(new Objeto(133,207));
		elementos.add(new Objeto(741,818));
		elementos.add(new Objeto(372,331));
		elementos.add(new Objeto(950,979));
		elementos.add(new Objeto(403,445));
		elementos.add(new Objeto(419,348));
		elementos.add(new Objeto(780,724));
		elementos.add(new Objeto(345,401));
		elementos.add(new Objeto(618,646));
		elementos.add(new Objeto(766,668));
		elementos.add(new Objeto(335,242));
		elementos.add(new Objeto(288,302));
		elementos.add(new Objeto(795,861));
		elementos.add(new Objeto(777,830));
		elementos.add(new Objeto(117,77));
		elementos.add(new Objeto(541,441));
		elementos.add(new Objeto(499,589));
		elementos.add(new Objeto(1025,990));
		elementos.add(new Objeto(217,189));
		elementos.add(new Objeto(799,868));
		elementos.add(new Objeto(944,996));
		elementos.add(new Objeto(329,363));
		elementos.add(new Objeto(232,237));
		elementos.add(new Objeto(367,420));
		elementos.add(new Objeto(552,565));
		elementos.add(new Objeto(220,214));
		elementos.add(new Objeto(353,333));
		elementos.add(new Objeto(811,726));
		elementos.add(new Objeto(176,175));
		elementos.add(new Objeto(35,58));
		elementos.add(new Objeto(548,625));
		elementos.add(new Objeto(1,66));
		elementos.add(new Objeto(142,169));
		elementos.add(new Objeto(400,475));
		elementos.add(new Objeto(298,204));
		elementos.add(new Objeto(27,90));
		elementos.add(new Objeto(421,496));
		elementos.add(new Objeto(40,1));
		elementos.add(new Objeto(462,474));
		elementos.add(new Objeto(359,449));
		elementos.add(new Objeto(299,252));
		elementos.add(new Objeto(395,332));
		elementos.add(new Objeto(382,287));
		elementos.add(new Objeto(206,189));
		elementos.add(new Objeto(980,889));
		elementos.add(new Objeto(1,63));
		elementos.add(new Objeto(285,378));
		elementos.add(new Objeto(109,206));
		elementos.add(new Objeto(813,897));
		elementos.add(new Objeto(219,230));
		elementos.add(new Objeto(361,382));
		elementos.add(new Objeto(601,575));
		elementos.add(new Objeto(155,60));
		elementos.add(new Objeto(442,529));
		elementos.add(new Objeto(405,477));
		elementos.add(new Objeto(253,290));
		elementos.add(new Objeto(57,89));
		elementos.add(new Objeto(894,961));
		elementos.add(new Objeto(548,461));
		elementos.add(new Objeto(763,702));
		elementos.add(new Objeto(542,532));
		elementos.add(new Objeto(704,680));
		elementos.add(new Objeto(44,53));
		elementos.add(new Objeto(545,642));
		elementos.add(new Objeto(1018,1000));
		elementos.add(new Objeto(803,891));
		elementos.add(new Objeto(730,822));
		elementos.add(new Objeto(59,152));
		elementos.add(new Objeto(423,324));
		elementos.add(new Objeto(590,633));
		elementos.add(new Objeto(266,267));
		elementos.add(new Objeto(325,381));
		elementos.add(new Objeto(307,272));
		elementos.add(new Objeto(422,506));
		elementos.add(new Objeto(403,419));
		elementos.add(new Objeto(524,461));
		elementos.add(new Objeto(613,526));
		elementos.add(new Objeto(1033,978));
		elementos.add(new Objeto(403,468));
		elementos.add(new Objeto(820,729));
		elementos.add(new Objeto(777,795));
		elementos.add(new Objeto(631,581));
		elementos.add(new Objeto(298,383));
		elementos.add(new Objeto(302,226));
		elementos.add(new Objeto(505,421));
		elementos.add(new Objeto(461,386));
		elementos.add(new Objeto(351,273));
		elementos.add(new Objeto(556,484));
		elementos.add(new Objeto(638,676));
		elementos.add(new Objeto(152,107));
		elementos.add(new Objeto(236,229));
		elementos.add(new Objeto(831,902));
		elementos.add(new Objeto(882,962));
		elementos.add(new Objeto(38,105));
		elementos.add(new Objeto(100,84));
		elementos.add(new Objeto(1006,991));
		elementos.add(new Objeto(661,701));
		elementos.add(new Objeto(596,689));
		elementos.add(new Objeto(748,655));
		elementos.add(new Objeto(528,600));
		elementos.add(new Objeto(587,538));
		elementos.add(new Objeto(492,499));
		elementos.add(new Objeto(783,881));
		elementos.add(new Objeto(1017,981));
		elementos.add(new Objeto(546,473));
		elementos.add(new Objeto(368,383));
		elementos.add(new Objeto(618,679));
		elementos.add(new Objeto(662,573));
		elementos.add(new Objeto(563,589));
		elementos.add(new Objeto(553,480));
		elementos.add(new Objeto(161,145));
		elementos.add(new Objeto(612,624));
		elementos.add(new Objeto(926,923));
		elementos.add(new Objeto(972,915));
		elementos.add(new Objeto(249,232));
		elementos.add(new Objeto(948,974));
		elementos.add(new Objeto(662,735));
		elementos.add(new Objeto(735,678));
		elementos.add(new Objeto(682,640));
		elementos.add(new Objeto(722,644));
		elementos.add(new Objeto(146,212));
		elementos.add(new Objeto(75,123));
		elementos.add(new Objeto(276,253));
		elementos.add(new Objeto(890,943));
		elementos.add(new Objeto(1,18));
		elementos.add(new Objeto(1007,916));
		elementos.add(new Objeto(302,327));
		elementos.add(new Objeto(607,640));
		elementos.add(new Objeto(646,615));
		elementos.add(new Objeto(488,470));
		elementos.add(new Objeto(565,655));
		elementos.add(new Objeto(239,288));
		elementos.add(new Objeto(448,401));
		elementos.add(new Objeto(906,840));
		elementos.add(new Objeto(564,633));
		elementos.add(new Objeto(647,707));
		elementos.add(new Objeto(339,424));
		elementos.add(new Objeto(757,682));
		elementos.add(new Objeto(213,194));
		elementos.add(new Objeto(829,855));
		elementos.add(new Objeto(229,190));
		elementos.add(new Objeto(703,728));
		elementos.add(new Objeto(257,178));
		elementos.add(new Objeto(255,275));
		elementos.add(new Objeto(123,88));
		elementos.add(new Objeto(321,367));
		elementos.add(new Objeto(517,544));
		elementos.add(new Objeto(768,738));
		elementos.add(new Objeto(1040,963));
		elementos.add(new Objeto(439,460));
		elementos.add(new Objeto(282,266));
		elementos.add(new Objeto(739,727));
		elementos.add(new Objeto(128,33));
		elementos.add(new Objeto(471,442));
		elementos.add(new Objeto(714,628));
		elementos.add(new Objeto(467,384));
		elementos.add(new Objeto(298,244));
		elementos.add(new Objeto(411,482));
		elementos.add(new Objeto(760,765));
		elementos.add(new Objeto(435,446));
		elementos.add(new Objeto(245,247));
		elementos.add(new Objeto(865,913));
		elementos.add(new Objeto(658,560));
		elementos.add(new Objeto(457,528));
		elementos.add(new Objeto(694,594));
		elementos.add(new Objeto(882,935));
		elementos.add(new Objeto(415,462));
		elementos.add(new Objeto(555,621));
		elementos.add(new Objeto(705,660));
		elementos.add(new Objeto(525,564));
		elementos.add(new Objeto(276,302));
		elementos.add(new Objeto(871,936));
		elementos.add(new Objeto(582,592));
		elementos.add(new Objeto(639,724));
		elementos.add(new Objeto(181,118));
		elementos.add(new Objeto(200,299));
		elementos.add(new Objeto(201,251));
		elementos.add(new Objeto(35,129));
		elementos.add(new Objeto(5,96));
		elementos.add(new Objeto(85,167));
		elementos.add(new Objeto(239,169));
		elementos.add(new Objeto(359,381));
		elementos.add(new Objeto(948,976));
		elementos.add(new Objeto(771,767));
		elementos.add(new Objeto(804,837));
		elementos.add(new Objeto(457,407));
		elementos.add(new Objeto(284,203));
		elementos.add(new Objeto(485,387));
		elementos.add(new Objeto(803,735));
		elementos.add(new Objeto(629,706));
		elementos.add(new Objeto(358,335));
		elementos.add(new Objeto(13,40));
		elementos.add(new Objeto(808,821));
		elementos.add(new Objeto(68,131));
		elementos.add(new Objeto(96,159));
		elementos.add(new Objeto(956,978));
		elementos.add(new Objeto(101,53));
		elementos.add(new Objeto(767,849));
		elementos.add(new Objeto(577,478));
		elementos.add(new Objeto(1,19));
		elementos.add(new Objeto(278,230));
		elementos.add(new Objeto(388,453));
		elementos.add(new Objeto(318,336));
		elementos.add(new Objeto(847,931));
		elementos.add(new Objeto(592,588));
		elementos.add(new Objeto(494,449));
		elementos.add(new Objeto(615,566));
		
		mochila.setObjetos(elementos);
		mochila.setCapacidadMochila(5002);
		mochila.setValorOptimo(9052);
		mochila.setNombreInstancia("Hf2-1000");
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
    
    public static  void LeeArchivoInstanciaKP() {
    	fs = true;
        ss = true;

        rs = 0;
        rc = 0;
        cc = 0;
        cr = 0;
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(rutaInstancias+nombreInstancia));
            ArrayList<Objeto> elementos = new ArrayList<>();
    		Knapsack mochila = new Knapsack();
    		
    		
            while ((line = br.readLine()) != null) {
            	 String[] values = line.trim().split(" ");
            	if (fs) { //leemos la primera fila de la instancia, donde está el número de objetos y capacidad de la mochila
                    numeroFilas = Integer.parseInt(values[0]);
                    capacidadMochila = Integer.parseInt(values[1]);
                    valorOptimoInstancia = Integer.parseInt(values[2]);
                    //restricciones = new int[numeroFilas][numeroColumnas];
                    //costos = new int[numeroColumnas];
                    fs = false;
                } else {
                    if (values.length == 2) {
//                    	elementos = new ArrayList<>();
                		
                		elementos.add(new Objeto(Integer.parseInt(values[0]), Integer.parseInt(values[1])));

                		
                    } else {
                    	System.out.printf("Error al procesar línea\n");
                    }
                }
            }
        	mochila.setObjetos(elementos);
    		mochila.setCapacidadMochila(capacidadMochila);
    		mochila.setValorOptimo(valorOptimoInstancia);
    		mochila.setNombreInstancia(nombreInstancia);
    		mInstanciasMochilas.put(conteoInstancias, mochila);
    		conteoInstancias++;
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
    
	public  void ProcesarLineaKP(String line) {
        String[] values = line.trim().split(" ");

        if (fs) {
            numeroFilas = Integer.parseInt(values[0]);
            capacidadMochila = Integer.parseInt(values[1]);
            //restricciones = new int[numeroFilas][numeroColumnas];
            //costos = new int[numeroColumnas];
            fs = false;
        } else {
                if (values.length == 2) {
                	ArrayList<Objeto> elementos = new ArrayList<>();
            		
            		elementos.add(new Objeto(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
            		
            		
                } else {
                	System.out.printf("Error al procesar línea");
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
		int cantNidos = 25, iteracionIntervencionML = 500;
		float probDescub = (float) 0.25;
		int numeroIteraciones = 5000, cantEjecuciones=15;
		try {

			cargaInstancias_mochila();
			rutaInstancias = "resources/input/";
			nombreInstancia = "Hf1-5000";
//			LeeArchivoInstanciaKP();
//			cargaTiposDeBinarizaciones();
//			cargaTiposDeDiscretizaciones();
			
			for (int numInstanciaMochila = 1; numInstanciaMochila<= mInstanciasMochilas.size(); numInstanciaMochila++){
				if (mInstanciasMochilas.get(numInstanciaMochila).getNombreInstancia() == "f5") { //|| mInstanciasMochilas.get(numInstanciaMochila).getNombreInstancia() == "f5" ) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
