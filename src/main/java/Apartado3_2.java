import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * @author :Sonia Paez
 * insertar Elemento prestado
 */
public class Apartado3_2 {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, XMLDBException, NoSuchMethodException, InvocationTargetException, XMLDBException, InvocationTargetException {

        Class cl = Class.forName(Conexion.DRIVER);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        String valor= "";
        ArrayList<String> fechas = new ArrayList<String>();

        try {
            col = DatabaseManager.getCollection(Conexion.URI + Conexion.COLLECTION, Conexion.USERNAME, Conexion.PASSWORD);
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            ResourceSet result = xpqs.query("//prestamo/fechadevolucion");
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                res = i.nextResource();
                fechas.add((String) res.getContent());
                System.out.println(res.getContent());
            }
            Collections.sort(fechas);

                if (!fechas.isEmpty()) {

                        String sQuery = "update insert <prestado>No Valido</prestado> into //prestamo[fechadevolucion]";
                        System.out.println(sQuery);
                        xpqs.query(sQuery);

                }else{
                    valor= "Válido";
                    String sQuery = "update insert <prestado>Valido</prestado> into //prestamo[fechadevolucion]";
                    System.out.println(sQuery);
                    xpqs.query(sQuery);


                }
                result = xpqs.query("//prestamo");
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
