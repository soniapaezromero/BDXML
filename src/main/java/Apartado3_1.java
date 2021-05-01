import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import java.lang.reflect.InvocationTargetException;

/**
 * @author :Sonia Paez
 * Actualiza el año de edicion del libro cuyo id=2
 */
public class Apartado3_1 {
    public static void main(String[] args) throws ClassNotFoundException, XMLDBException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class cl = Class.forName(Conexion.DRIVER);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;
        try {
            col = (Collection) DatabaseManager.getCollection(Conexion.URI + Conexion.COLLECTION, Conexion.USERNAME, Conexion.PASSWORD);
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            ResourceSet result = xpqs.query(" // libro[id=2]" );
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                res = i.nextResource();
                System.out.println(res.getContent());

            }
            String sQuery= "update value // libro[id=2]/publicacion with 1999";
            xpqs.query(sQuery);
            System.out.println("Registro actualizado");
            result = xpqs.query(" // libro[id=2]" );
             i = result.getIterator();
             res = null;
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

