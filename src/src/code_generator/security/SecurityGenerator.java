package code_generator.security;

import code_generator.Generator;
import joseluisch.jdbc_utils.utils.Consts;
import utils.FileUtils;

/**
 *
 * @author Jose Luis Chavez
 */
public class SecurityGenerator {

    public static void createSecurityClass(String path) {
        String fileServiceName = path.concat(Generator.SECURYTY_PATH).concat("SecurityUtils.java");
        String fileServiceContent = Consts.SECURITY_UTILS;
        FileUtils.writeFile(fileServiceName, fileServiceContent);
    }

}
