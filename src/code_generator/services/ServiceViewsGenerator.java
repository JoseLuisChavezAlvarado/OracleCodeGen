package code_generator.services;

import code_generator.Generator;
import java.util.Map;
import penoles.oraclebdutils.entities.TableDetails;
import penoles.oraclebdutils.entities.ViewObject;
import penoles.oraclebdutils.utils.ReflectUtils;
import penoles.oraclebdutils.utils.StringUtils;
import utils.FileUtils;

/**
 *
 * @author Jose Luis Chavez
 */
public class ServiceViewsGenerator {

    private static final String[] PACKAGES = {
        "import penoles.oraclebdutils.abstractclasses.ResponseObject;",
        "import security.SecurityUtils;",
        "import com.google.gson.Gson;",
        "import java.util.List;",
        "import penoles.oraclebdutils.database.controller.DatabaseController;",
        "import javax.ws.rs.Produces;",
        "import javax.ws.rs.GET;",
        "import javax.ws.rs.HeaderParam;",
        "import javax.ws.rs.Path;",
        "import javax.ws.rs.QueryParam;",
        "import javax.ws.rs.core.MediaType;",
        "import entities.views.*;",
        "import javax.ws.rs.core.Response;"};

    public static void createServiceClass(ViewObject viewObject, String path, Map<String, TableDetails> mapDetails) {
        String classEntityName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(viewObject.getTable_name()));
        String classServiceName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(viewObject.getTable_name()));
        String fileServiceName = path.concat(Generator.VIEW_SERVICES_PATH).concat(classServiceName).concat("Resource.java");
        String fileServiceContent = writeServiceFile(mapDetails, classEntityName);
        FileUtils.writeFile(fileServiceName, fileServiceContent);
    }

    private static String writeServiceFile(Map<String, TableDetails> mapDetails, String className) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builderParams = new StringBuilder();

        builder.append("package resources.views;\n");
        for (String mPackage : PACKAGES) {
            builder.append("\n").append(mPackage);
        }

        builder.append("\n\n/**\n * REST Web Service\n *\n * @author Class generated by JDBCUtils.\n */");
        builder.append("\n@Path(\"").append(StringUtils.toLowerScoreCase(className)).append("\")");
        builder.append("\npublic class ").append(className).append("Resource").append(" {");
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

        builder.append("\n        if (SecurityUtils.validateSecurity(user, pass) || SecurityUtils.validateSecurity(token)) {");
        builder.append("\n\t\t").append(className).append(" entity = new ").append(className).append("(").append(builderParams).append(");");
        builder.append("\n            ResponseObject<List<Object>, Exception> responseObject = DatabaseController.select(entity);");
        builder.append("\n            List<Object> map = (List<Object>) responseObject.getResponse();");
        builder.append("\n            Exception exception = responseObject.getException();");
        builder.append("\n            if (exception != null) {");
        builder.append("\n                return Response.serverError().entity(new Gson().toJson(exception)).build();");
        builder.append("\n            } else {");
        builder.append("\n                return Response.ok().entity(new Gson().toJson(map)).build();");
        builder.append("\n            }");
        builder.append("\n        } else {");
        builder.append("\n            return Response.status(Response.Status.FORBIDDEN).build();");
        builder.append("\n        }");
        builder.append("\n    }");
        builder.append("\n}");

        return builder.toString();
    }

}
