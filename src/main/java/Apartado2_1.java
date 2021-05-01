import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

/**
 * @author :Sonia Paez
 * consulta que muestra cuantas veces se ha prestado cada libro
 */
public class Apartado2_1 {
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
            ResourceSet result = xpqs.query("for $a in distinct-values( //prestamo/libro)  let $c:= count(for $b in /prestamos/prestamo/libro where $b=$a return $b) return <prestamo> <libro> {$a}</libro><veces>{$c}</veces></prestamo>" );
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
