package mercadolibre.controllers;

import bypass.grails.TransactionService;
import mercadolibre.utils.SpringUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fcambarieri on 10/08/15.
 */
public class TransactionController extends AbstractController {

    @Override
    public void executeService(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) throws IOException {
        Map map = new HashMap();
        map.put("controller", "transaction");
        TransactionService service = SpringUtils.getBean("transactionService");
        service.testCreationIdle(Long.parseLong(request.getParameter("timeout")));
        sendOk(response, toJSON(map));
    }

    @Override
    String uriPattern() {
        return "/transactions";
    }
}
