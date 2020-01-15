package ncb.phd.pucv.cuckooSearch.dbscan.metrics;

import ncb.phd.pucv.cuckooSearch.dbscan.DBSCANClusteringException;
import ncb.phd.pucv.cuckooSearch.dbscan.DistanceMetric;
import ncb.phd.pucv.cuckooSearch.dbscan.Punto;

/**
 * Distance metric implementation for numeric values.
 * 
 * @author <a href="mailto:cf@christopherfrantz.org">Christopher Frantz</a>
 * @version 0.1
 *
 */
public class DistanceMetricPunto implements DistanceMetric<Punto>{

	@Override
	public double calculateDistance(Punto val1, Punto val2) throws DBSCANClusteringException {
		// TODO Auto-generated method stub
		return Math.abs(val1.get(0) - val2.get(0));
	}

}
