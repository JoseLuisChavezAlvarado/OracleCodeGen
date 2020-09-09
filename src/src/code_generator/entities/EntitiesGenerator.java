package code_generator.entities;

import code_generator.Generator;
import java.util.List;
import java.util.Map;
import joseluisch.jdbc_utils.entities.KeyColumnObject;
import joseluisch.jdbc_utils.entities.TableDetails;
import joseluisch.jdbc_utils.entities.UserTable;
import joseluisch.jdbc_utils.utils.ReflectUtils;
import joseluisch.jdbc_utils.utils.StringUtils;
import utils.FileUtils;

/**
 *
 * @author Jose Luis Ch.
 */
public class EntitiesGenerator {

    public static void createEntityClass(UserTable userTable, String path, Map<String, TableDetails> mapDetails, List<KeyColumnObject> keyColumnObjectsList) {
        String classEntityName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(userTable.getTable_name()));
        String fileEntityName = path.concat(Generator.ENTITIES_PATH).concat(classEntityName).concat(".java");
        String fileEntityContent = writeEntityFile(mapDetails, classEntityName, keyColumnObjectsList);
        FileUtils.writeFile(fileEntityName, fileEntityContent);
    }

    private static String writeEntityFile(Map<String, TableDetails> mapDetails, String className, List<KeyColumnObject> list) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builderParams = new StringBuilder();
        StringBuilder builderExtras = new StringBuilder();
        StringBuilder builderGetters = new StringBuilder();
        StringBuilder builderExtraParams = new StringBuilder();

        StringBuilder builderConstr = new StringBuilder();
        StringBuilder builderConstrParams = new StringBuilder();
        StringBuilder builderConstrValues = new StringBuilder();

        builderExtraParams.append("\n");
        builder.append("package entities;");
        builder.append("\n\nimport java.io.Serializable;");
        builder.append("\n/**\n *\n * @author Class generated by JDBCUtils.\n */");
        builder.append("\npublic class ").append(className).append(" implements Serializable {");

        builderConstr.append("\n\n\tpublic ").append(className).append("() {\n\t}");
        builderConstr.append("\n\n\tpublic ").append(className).append("(");

        for (Map.Entry entry : mapDetails.entrySet()) {
            TableDetails details = (TableDetails) entry.getValue();

            String paramType = ReflectUtils.getPropperFieldType(details.getType());
            String paramName = details.getField().toLowerCase();

            builderParams.append("\n\tprivate ").append(paramType).append(" ").append(paramName).append(";");

            String upperCasedParamName = StringUtils.toUpperCamelCaseNew(paramName);

            builderGetters.append("\n\n\tpublic ").append(paramType).append(" get").append(upperCasedParamName).append("() {");
            builderGetters.append("\n\t\treturn ").append(paramName).append(";");
            builderGetters.append("\n\t}");

            builderGetters.append("\n\n\tpublic void set").append(upperCasedParamName).append("(").append(paramType).append(" ").append(paramName).append(") {");
            builderGetters.append("\n\t\tthis.").append(paramName).append(" = ").append(paramName).append(";");
            builderGetters.append("\n\t}");

            builderConstrParams.append(paramType).append(" ").append(paramName).append(", ");
            builderConstrValues.append("\n\t\tthis.").append(paramName).append(" = ").append(paramName).append(";");
        }

        builderConstrParams.replace(builderConstrParams.length() - 2, builderConstrParams.length(), "");
        builderConstr.append(builderConstrParams).append(") {");
        builderConstr.append(builderConstrValues);
        builderConstr.append("\n\t}");

        boolean idHasBeenFound = false;

        for (KeyColumnObject keyObject : list) {

            String classEntityName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(keyObject.getTable_name()));

            if (classEntityName.equals(className) && keyObject.getReferenced_table_name() != null) {

                String paramName = StringUtils.toLowerCamelCase(keyObject.getReferenced_table_name());
                String paramType = StringUtils.toUpperCamelCase(keyObject.getReferenced_table_name());
                String secondayParamName = StringUtils.toFirstUpperCased(keyObject.getColumn_name().toLowerCase());

                String upperCasedParamName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(paramName));

                builderExtraParams.append("\n\tprivate ").append(paramType).append(" ").append(paramName).append(secondayParamName).append(";");

                builderGetters.append("\n\n\tpublic ").append(paramType).append(" get").append(upperCasedParamName).append(secondayParamName).append("() {");
                builderGetters.append("\n\t\tif (").append(paramName).append(secondayParamName).append(" == null) {");
                builderGetters.append("\n\t\t\t").append(paramName).append(secondayParamName).append(" = new ").append(paramType).append("();");
                builderGetters.append("\n\t\t}");
                builderGetters.append("\n\t\treturn ").append(paramName).append(secondayParamName).append(";");
                builderGetters.append("\n\t}");

                builderGetters.append("\n\n\tpublic void set").append(upperCasedParamName).append(secondayParamName).append("(").append(paramType).append(" ").append(paramName).append(secondayParamName).append(") {");
                builderGetters.append("\n\t\tthis.").append(paramName).append(secondayParamName).append(" = ").append(paramName).append(secondayParamName).append(";");
                builderGetters.append("\n\t}");

            } else if (classEntityName.equals(className) && keyObject.getConstraint_type().equals("P") && idHasBeenFound) {
                builderExtras = new StringBuilder();
            } else if (classEntityName.equals(className) && keyObject.getConstraint_type().equals("P") && !idHasBeenFound) {
                idHasBeenFound = true;
                builderExtras.append("\n\n\t@Override");
                builderExtras.append("\n\tpublic String toString() {");
                builderExtras.append("\n\t\treturn ").append(keyObject.getColumn_name().toLowerCase()).append(".toString();");
                builderExtras.append("\n\t}");
            }
        }

        builder.append("\n").append(builderParams).append(builderExtraParams).append(builderConstr).append(builderGetters).append(builderExtras).append("\n}");

        return builder.toString();
    }

}
