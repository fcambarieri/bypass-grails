package mercadolibre.controllers;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by fcambarieri on 10/08/15.
 */
public abstract class AbstractController {

    public abstract void executeService(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) throws IOException;

    abstract String uriPattern();

    public boolean match(String uri) {
        Pattern pattern = Pattern.compile(uriPattern());
        return pattern.matcher(uri).matches();
    }

    void sendOk(HttpServletResponse response, String body) throws IOException{
        sendResponse(response, body, "application/json", 200);
    }

    void sendResponse(HttpServletResponse response, String body, String contentType, int status) throws IOException{
        response.setContentType(contentType);
        response.setStatus(status);
        PrintWriter pw = response.getWriter();
        try {
            pw.write(body);
        } finally {
            pw.close();
        }
    }

    String toJSON(Map map) {
        JSONObject json = new JSONObject();
        json.putAll(map);
        return json.toString();
    }
}
