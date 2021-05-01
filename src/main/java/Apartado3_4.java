import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * @author :Sonia Paez
 * Eliminar elemento prestado antes 2005
 */
public class Apartado3_4 {
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
            ResourceSet result = xpqs.query("for $libro in //libro let $a:=$libro/id let $b:= /prestamos/prestamo[xs:double(substring(fechadevolucion/text(),xs:double(7),xs:double(11)))<2005]/libro where $a =$b return $libro ");
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                res = i.nextResource();
                System.out.println(res.getContent());

            }
            String sQuery = "for $libro in //libro let $a:=$libro/id let $b:= /prestamos/prestamo[xs:double(substring(fechadevolucion/text(),xs:double(7),xs:double(11)))<2005]/libro where $a =$b return update delete $libro";
            System.out.println(sQuery);
            xpqs.query(sQuery);
            System.out.println("REgistro Eliminado");
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

