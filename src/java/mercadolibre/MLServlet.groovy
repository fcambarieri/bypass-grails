package mercadolibre

import bypass.grails.Transaction
import bypass.grails.TransactionService
import grails.converters.JSON
import mercadolibre.controllers.AbstractController
import mercadolibre.controllers.ControllerManager
import mercadolibre.controllers.DataSourceController
import mercadolibre.controllers.TransactionController
import mercadolibre.utils.SpringUtils
import org.codehaus.groovy.grails.web.json.JSONObject
import org.hibernate.SessionFactory
import org.springframework.http.HttpMethod

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by fcambarieri on 08/08/15.
 */
class MLServlet extends HttpServlet {


    public void init() throws ServletException {
        //we can create DB connection resource here and set it to Servlet context
        /*if(getServletContext().getInitParameter("dbURL").equals("jdbc:mysql://localhost/mysql_db") &&
                getServletContext().getInitParameter("dbUser").equals("mysql_user") &&
                getServletContext().getInitParameter("dbUserPwd").equals("mysql_pwd"))
            getServletContext().setAttribute("DB_Success", "True");
        else throw new ServletException("DB Connection error");*/
        ControllerManager.getInstance().addController(new DataSourceController());
        ControllerManager.getInstance().addController(new TransactionController());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp, HttpMethod.PUT);
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod method) {
        String uri = req.getRequestURI().replace("/bypass-grails/ml","");

        AbstractController controller = ControllerManager.getInstance().getController(uri);
        if (controller != null) {
            controller.executeService(req, resp, method)
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp, HttpMethod.GET);
    }
/* @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {



            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Hello, World</title></head>");
            out.println("<body>");
            out.println("<h1>Hello, world!</h1>");  // says Hello
            // Echo client's request information
            out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
            out.println("<p>Protocol: " + request.getProtocol() + "</p>");
            out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
            out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
            // Generate a random number upon each request
            out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();  // Always close the output writer
        }

    }*/

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {

            TransactionService service = SpringUtils.getBean("transactionService");
            SessionFactory sessionFactory = SpringUtils.getBean("sessionFactory");

            /*Transaction trx = new Transaction();
            trx.setDateCreated(new Date());
            trx.paymentId = new Long(request.getParameter("payment_id"));
            trx.trxId = UUID.randomUUID().toString();

            Long id = service.createTransaction(request.getParameter("trx"), Long.parseLong(request.getParameter("payment_id")));

            Map map = new HashMap();
            map.put("id",id);

            writeJSONResponse(out,map);


            /*String message = request.getParameter("message")

            if (sessionFactory != null && sessionFactory.getStatistics()) {
                //sessionFactory.
                message = sessionFactory.getStatistics().toString();
            }

            message = message.replace("Statistics","").replace("[","").replace("]","");

            System.out.print(message);

            Map map = new HashMap();
            String[] values = message.split(",");
            for(String value in values) {
                String[] field = value.split("=");
                map.put(field[0],field[1]);
            }
            writeJSONResponse(out, map);
           // out.write(message);
            //Map result = service.tellMe();
            //writeJSONResponse(out, result);
            //render(text:(body as JSON).toString(), status: status, contentType:"application/json", encoding:"UTF-8")
        }catch (Exception e) {
            e.print(out);
        } finally {
            out.close();
        }
    }*/

    private void writeJSONResponse(PrintWriter out, Map result) {
        JSONObject json = new JSONObject();
        json.putAll(result);
        out.write(json.toString());
    }
}
