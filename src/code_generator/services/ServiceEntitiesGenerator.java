package code_generator.services;

import code_generator.Generator;
import static code_generator.Generator.SERVICES_PATH;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joseluisch.jdbc_utils.entities.KeyColumnObject;
import joseluisch.jdbc_utils.entities.TableDetails;
import joseluisch.jdbc_utils.entities.UserTable;
import joseluisch.jdbc_utils.utils.Consts;
import joseluisch.jdbc_utils.utils.ReflectUtils;
import joseluisch.jdbc_utils.utils.StringUtils;
import utils.FileUtils;

/**
 *
 * @author Jose Luis Chavez
 */
public class ServiceEntitiesGenerator {

    private static final String[] PACKAGES = {
        "import abstract_classes.ResponseObject;"
        + "import security.SecurityUtils;"
        + "import entities.*;"
        + "import com.google.gson.Gson;"
        + "import java.util.Map;"
        + "import joseluisch.jdbc_utils.controllers.DatabaseController;"
        + "import javax.ws.rs.Produces;"
        + "import javax.ws.rs.GET;"
        + "import javax.ws.rs.HeaderParam;"
        + "import javax.ws.rs.Path;"
        + "import javax.ws.rs.QueryParam;"
        + "import javax.ws.rs.core.MediaType;"
        + "import javax.ws.rs.core.Response;"};

    public static void createServiceClass(UserTable userTable, String path, Map<String, TableDetails> mapDetails, List<KeyColumnObject> keyColumnObjectsList) {
        String classServiceName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(userTable.getTable_name()));
        String fileServiceName = path.concat(Generator.SERVICES_PATH).concat(classServiceName).concat("Resource.java");
        String fileServiceContent = writeServiceFile(mapDetails, classServiceName);
        FileUtils.writeFile(fileServiceName, fileServiceContent);
    }

    public static void createExtraServices(String path, List<UserTable> list) {
        String fileServiceName = path.concat(SERVICES_PATH).concat("ResourceParent.java");
        String fileServiceContent = Consts.RESOURCE_PARENT;
        FileUtils.writeFile(fileServiceName, fileServiceContent);

        String fileTestName = path.concat(Generator.TEST_PATH).concat("TestResource.java");
        String fileTestContent = writeTestFile(list);
        FileUtils.writeFile(fileTestName, fileTestContent);
    }

    private static String writeServiceFile(Map<String, TableDetails> mapDetails, String className) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builderParams = new StringBuilder();

        builder.append("package resources;\n");
        for (String mPackage : PACKAGES) {
            builder.append("\n").append(mPackage);
        }

        builder.append("\n\n/**\n * REST Web Service\n *\n * @author Class generated by JDBCUtils.\n */");
        builder.append("\n@Path(\"").append(StringUtils.toLowerScoreCase(className)).append("\")");
        builder.append("\npublic class ").append(className).append("Resource extends ResourceParent<").append(className).append("> {");
        builder.append("\n\n\t@GET");
        builder.append("\n\t@Produces(MediaType.APPLICATION_JSON)");
        builder.append("\n\tpublic Response get(");

        for (Map.Entry entry : mapDetails.entrySet()) {
            TableDetails details = (TableDetails) entry.getValue();
            String paramType = ReflectUtils.getPropperFieldType(details.getType());
            String paramName = details.getField();

            builder.append("\n\t\t\t@QueryParam(\"").append(paramName).append("\") ").append(paramType).append(" ").append(paramName).append(",");
            builderParams.append(paramName).append(", ");
        }

        builderParams.replace(builderParams.length() - 2, builderParams.length(), "");
        builder.append("\n\t\t\t@HeaderParam(\"user\") String user,");
        builder.append("\n\t\t\t@HeaderParam(\"pass\") String pass,");
        builder.append("\n\t\t\t@HeaderParam(\"token\") String token)");
        builder.append("\n\t{");

        builder.append("\n\tif (SecurityUtils.validateSecurity(user, pass) || SecurityUtils.validateSecurity(token)) {");
        builder.append("\n\t\t").append(className).append(" entity = new ").append(className).append("(").append(builderParams).append(");");
        builder.append(" ResponseObject<Map<String, Object>, Exception> responseObject = DatabaseController.select(entity);");
        builder.append("   Map<String, Object> map = (Map<String, Object>) responseObject.getResponse();");
        builder.append(" Exception exception = responseObject.getException();");
        builder.append(" if (exception != null) {");
        builder.append("                return Response.serverError().entity(new Gson().toJson(exception)).build();");
        builder.append(" } else {");
        builder.append("return Response.ok().entity(new Gson().toJson(map)).build();");
        builder.append("}");
        builder.append("\n\t} else {");
        builder.append("\n\t\treturn Response.status(Response.Status.FORBIDDEN).build();");
        builder.append("\n\t}");
        builder.append("\n\t}");
        builder.append("\n\n}");

        return builder.toString();
    }

    private static String writeTestFile(List<UserTable> list) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builderParams = new StringBuilder();

        builder.append("package test;\n");
        for (String mPackage : PACKAGES) {
            builder.append("\n").append(mPackage);
        }

        builder.append("\n\n/**\n * REST Web Service\n *\n * @author Class generated by JDBCUtils.\n */");
        builder.append("\n@Path(\"test\")");
        builder.append("\npublic class TestResource {");
        builder.append("\n\n@GET");
        builder.append("\n\t@Produces(MediaType.TEXT_PLAIN)");
        builder.append("\n\tpublic String get() {");

        for (UserTable userTable : list) {
            String classEntityName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(userTable.getTable_name()));
            builderParams.append("new ").append(classEntityName).append("(), ");
        }

        builderParams.replace(builderParams.length() - 2, builderParams.length(), "");
        builder.append("\n\tObject[] objects = {").append(builderParams).append("};");
        builder.append("\n\tfor (Object object : objects) {");
        builder.append("\n\t\tfor (int i = 0; i < 1; i++) {");
        builder.append("\n\t\t\tDatabaseController.select(object);");
        builder.append("\n\t}");
        builder.append("\n\t}");
        builder.append("\n\treturn \"Test finalizado\";");
        builder.append("\n\t}");
        builder.append("\n}");

        return builder.toString();

    }
}
