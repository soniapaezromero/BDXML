
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author :Sonia Paez
 * Actualizar Elemento prestado
 */
public class Apartado3_3 {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, XMLDBException, NoSuchMethodException, InvocationTargetException, XMLDBException, InvocationTargetException {

        Class cl = Class.forName(Conexion.DRIVER);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        String valor= "";
        String nuevoValor="";
        ArrayList<String> valorPrestamo = new ArrayList<String>();

        try {
            col = DatabaseManager.getCollection(Conexion.URI + Conexion.COLLECTION, Conexion.USERNAME, Conexion.PASSWORD);
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            ResourceSet result = xpqs.query("//prestamo/prestado/node()");
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                res = i.nextResource();
                valorPrestamo.add((String) res.getContent());
                System.out.println(res.getContent());
            }
            Collections.sort(valorPrestamo);
            for( int j=0;j<valorPrestamo.size();j++){
              String s=valorPrestamo.get(j) ;
            if (!s.isEmpty()) {
                if(s.equals( "No Valido")){
                    valor= "No Valido";
                    nuevoValor ="No prestado";
                    String sQuery = "update replace //prestado[. = \"No Valido\"] with <prestado>No prestado</prestado> ";

                    xpqs.query(sQuery);
                }else{
                    String sQuery = "update replace //prestado[. = \"Valido\"] with <prestado>prestado</prestado> " ;
                    xpqs.query(sQuery);
                }
            }



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
