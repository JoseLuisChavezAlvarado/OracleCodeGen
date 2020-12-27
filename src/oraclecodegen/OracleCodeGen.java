package oraclecodegen;

import code_generator.Generator;
import penoles.oraclebdutils.database.Conexion;
import penoles.oraclebdutils.database.DatabaseInstance;

/**
 *
 * @author Jose Luis Chavez
 */
public class OracleCodeGen {

    public static void main(String[] args) {

        String url_conection = "jdbc:oracle:thin:@172.31.4.7:60000:DESAWEB";
        String path = "/home/desarrollo/Archivos/";
        String user = "logistica";
        String pass = "l0g1$t";

        DatabaseInstance.getInstance().init(url_conection, user, pass);
        Conexion conexion = new Conexion();
        conexion.test();
        conexion.closeConexion();

        System.out.println("\nConnected to: " + url_conection);
        System.out.println("As user: " + user + "\n");

        Generator.generate(path);

        System.out.println("\nRuta de guardado: " + path);
        System.out.println("Proceso de creación finalizado...\n");

    }

}
