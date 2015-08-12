package mercadolibre.utils;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fcambarieri on 11/08/15.
 */
public class PoolUtils {

    public static String printPoolStats() {
        Map pool = poolStats();
        JSONObject json = new JSONObject();
        json.putAll((Map)pool.get("pool"));
        return json.toString();
        //System.out.println ("Pool: " + json.toString());
    }
    public static Map poolStats() {
        DataSource dataSource = SpringUtils.getBean("dataSource");
        Map map = new HashMap();
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            TransactionAwareDataSourceProxy ds = (TransactionAwareDataSourceProxy)dataSource;
            if (ds.getTargetDataSource() instanceof LazyConnectionDataSourceProxy) {
                LazyConnectionDataSourceProxy lcsp = (LazyConnectionDataSourceProxy) ds.getTargetDataSource();
                if (lcsp.getTargetDataSource() instanceof org.apache.tomcat.jdbc.pool.DataSource) {
                    org.apache.tomcat.jdbc.pool.DataSource apacheDS = (org.apache.tomcat.jdbc.pool.DataSource)lcsp.getTargetDataSource();
                    Map pool = new HashMap();
                    pool.put("size", apacheDS.getSize());
                    pool.put("active", apacheDS.getActive());
                    pool.put("numActive", apacheDS.getNumActive());
                    pool.put("idle", apacheDS.getIdle());
                    pool.put("numIdle", apacheDS.getNumIdle());
                    pool.put("waitCount", apacheDS.getWaitCount());
                    map.put("pool", pool);
                }
            }

        }
        map.put("driver", dataSource.getClass().getName());
        return map;
    }
}
