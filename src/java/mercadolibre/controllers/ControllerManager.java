package mercadolibre.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by fcambarieri on 10/08/15.
 */
public class ControllerManager {

    private static final ControllerManager instance = new ControllerManager();

    public List<AbstractController> controllers;

    public static ControllerManager getInstance() {
        return instance;
    }

    private ControllerManager(){

    }

    public void addController(AbstractController controller) {
        if (controllers == null) {
            this.controllers = new ArrayList<AbstractController>();
        }

        this.controllers.add(controller);

    }

    public AbstractController getController(String uri) {
        Iterator<AbstractController> it = controllers.iterator();
        while(it.hasNext()) {
            AbstractController controller = it.next();
            if (controller.match(uri)) {
                return controller;
            }
        }
        return null;
    }
}
