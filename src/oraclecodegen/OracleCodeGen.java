package oraclecodegen;

import code_generator.Generator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import penoles.oraclebdutils.database.Conexion;
import penoles.oraclebdutils.database.DatabaseInstance;

/**
 *
 * @author Jose Luis Chavez
 */
public class OracleCodeGen {

//    public static void main(String[] args) {
//
//        //String path = "/home/desarrollo/Archivos/";
//        //String path = "C:\\Users\\ALFA-DI3-9HFZSW2\\Desktop\\";
//        String concat = System.getProperty("user.dir").contains("\\") ? "\\" : "/";
//        String path = System.getProperty("user.dir").concat(concat);
//
//        String url_conection = "";
//        String user = "logistica";
//        String pass = "l0g1$t";
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//
//            boolean isAllFine = false;
//
//            while (!isAllFine) {
//
//                System.out.println("\nPara continuar es necesario proporcionar los siguientes datos:");
//                System.out.print("Url de conexión: ");
//                url_conection = reader.readLine();
//
//                System.out.print("Usuario: ");
//                user = reader.readLine();
//
//                System.out.print("Pass: ");
//                pass = reader.readLine();
//
//                System.out.println("\nLos datos introducidos son: ");
//                System.out.println(" : URL: " + url_conection);
//                System.out.println(" : Usuario: " + user);
//                System.out.println(" : Contraseña: " + pass);
//
//                String respuesta = "";
//                while (respuesta.isEmpty() || !(respuesta.equalsIgnoreCase("S") || respuesta.equalsIgnoreCase("N"))) {
//                    System.out.print("\n¿La información es correcta? (S / N): ");
//                    respuesta = reader.readLine();
//                }
//
//                isAllFine = respuesta.equalsIgnoreCase("S");
//            }
//
//            //CONSULTAR RUTA =======================================================
//            System.out.println("\nLos datos serán guardados en la siguiente ruta: " + System.getProperty("user.dir"));
//
//            boolean isPathRight = false;
//            while (!isPathRight) {
//                System.out.print("¿Desea cambiar la ruta de guardado? (S / N): ");
//                String response = reader.readLine();
//
//                if (response.equalsIgnoreCase("s")) {
//                    System.out.println("Introduce la nueva ruta de guardado: ");
//                    path = reader.readLine();
//
//                    System.out.println("La ruta de guardado es: " + path);
//                    System.out.println("¿Es correcto? ( S / N ): ");
//
//                    response = reader.readLine();
//
//                    if (response.equalsIgnoreCase("S")) {
//                        isPathRight = true;
//                    }
//
//                } else {
//                    isPathRight = true;
//                }
//
//            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        path = path.endsWith("/") || path.endsWith("\\") ? path : path + concat;
//
//        DatabaseInstance.getInstance().init(url_conection, user, pass);
//        Conexion conexion = new Conexion();
//        boolean test = conexion.test();
//        conexion.closeConexion();
//
//        if (!test) {
//            System.err.println("\nNo fue posible conectarse con los datos introducidos. Revise la información e inténtelo nuevamente");
//            return;
//        }
//
//        System.out.println("Generando código, esto puede tardar unos minutos....");
//        System.out.println("\nConnected to: " + url_conection);
//        System.out.println("As user: " + user + "\n");
//
//        Generator.generate(path);
//
//        System.out.println("\nRuta de guardado: " + path);
//        System.out.println("Proceso de creación finalizado...\\u001B[32m");
//
//    }
    public static void main(String[] args) {
        autoGen();
    }

    public static void autoGen() {

        String path = "/home/desarrollo/Archivos/";
        //String path = "C:\\Users\\ALFA-DI3-9HFZSW2\\Desktop\\";
        String url_conection = "jdbc:oracle:thin:@//172.31.4.228:51000/DESAWEB12";
        String user = "sicose";
        String pass = "s1co53d3$";

        DatabaseInstance.getInstance().init(url_conection, user, pass);
        Conexion conexion = new Conexion();
        boolean test = conexion.test();
        conexion.closeConexion();

        if (!test) {
            System.err.println("\nNo fue posible conectarse con los datos introducidos. Revise la información e inténtelo nuevamente");
            return;
        }

        System.out.println("Generando código, esto puede tardar unos minutos....");
        System.out.println("\nConnected to: " + url_conection);
        System.out.println("As user: " + user + "\n");

        Generator.generate(path);

        System.out.println("\nRuta de guardado: " + path);
        System.out.println("Proceso de creación finalizado...\\u001B[32m");

    }

}
