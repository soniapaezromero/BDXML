import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import java.lang.reflect.InvocationTargetException;

/**
 * @author :Sonia Paez
 * consulta que muestra Libros publicados despues del 2000,ordenados por autor y cuantas veces se han prestado
 */

public class Apartado2_2 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, XMLDBException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class cl = Class.forName(Conexion.DRIVER);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;
        try {
            col = (Collection) DatabaseManager.getCollection(Conexion.URI + Conexion.COLLECTION, Conexion.USERNAME, Conexion.PASSWORD);
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            ResourceSet result = xpqs.query("for $libro in /libros/libro[publicacion >2000] let $a:=$libro/id let $b:=count(for $b in /prestamos/prestamo/libro where $b=$a return $b)order by $libro/autor return<libros2000>{$libro}<vecesprestado>{$b}</vecesprestado></libros2000> " );
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                res = i.nextResource();

                System.out.println(res.getContent());

            }

        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
}
