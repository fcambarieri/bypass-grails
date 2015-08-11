package mercadolibre.controllers;

import mercadolibre.utils.SpringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fcambarieri on 10/08/15.
 */
public class DataSourceController extends AbstractController {

    @Override
    public void executeService(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) throws IOException {
        DataSource dataSource = SpringUtils.getBean("dataSource");
        Map map = new HashMap();
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            TransactionAwareDataSourceProxy ds = (TransactionAwareDataSourceProxy)dataSource;
            if (ds.getTargetDataSource() instanceof LazyConnectionDataSourceProxy) {
                LazyConnectionDataSourceProxy lcsp = (LazyConnectionDataSourceProxy) ds.getTargetDataSource();
                map.put("target_datasource_x2", lcsp.getTargetDataSource().getClass().getName());
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
            map.put("target_datasource", ds.getTargetDataSource().getClass().getName());
        }

        map.put("controller", "datasoruce");
        map.put("driver", dataSource.getClass().getName());

        sendOk(response, toJSON(map));
    }

    @Override
    String uriPattern() {
        return "/datasource";
    }
}
