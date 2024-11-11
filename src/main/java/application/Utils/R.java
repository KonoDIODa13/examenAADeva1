package application.Utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static InputStream getImage(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("images" + File.separator + name);
    }

    public static InputStream getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration" + File.separator + name);
    }

    public static URL getUI(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }

    // He creado un metodo igualito que el de getUI para coger el recurso de hibernate.cfg
    public static URL getCfg(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("configuration" + File.separator + name);
    }

}
