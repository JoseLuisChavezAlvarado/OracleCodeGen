package code_generator.documents;

import code_generator.Generator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import penoles.oraclebdutils.entities.TableDetails;
import penoles.oraclebdutils.utils.ReflectUtils;
import penoles.oraclebdutils.utils.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jose Luis Ch.
 */
public class DocumentGenerator extends DocumentGeneratorTools {

    public static void createEntitiesWSDocumentation(String tableName, String path, Map<String, TableDetails> mapDetails) {

        FileOutputStream fileOutputStream = null;

        try {

            String classServiceName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(tableName));
            String fileServiceName = path.concat(Generator.DOCUMENTS_PATH).concat(classServiceName).concat("Documentation.docx");
            fileOutputStream = new FileOutputStream(new File(fileServiceName));

            XWPFDocument document = new XWPFDocument();

            createTittle(document, "Documentación de Servicio web: " + StringUtils.toLowerScoreCase(tableName), false);
            createSubTittle(document, "Documento creado por JDBCUtils.", false);
            createParagraph(document, "");
            createParagraph(document, "URL:    http://<SERVER >:<PORT>/ProjectName/webresources/" + StringUtils.toLowerScoreCase(tableName));
            createParagraph(document, "");
            //GET ==================================================================
            createSubTittle(document, "Http Methods: GET", false);
            createParagraph(document, "");
            createSubTittle(document, "Query Params:", false);

            for (Map.Entry entry : mapDetails.entrySet()) {
                TableDetails details = (TableDetails) entry.getValue();
                String paramType = ReflectUtils.getPropperFieldType(details.getType());
                String paramName = details.getField();

                createParagraph(document, "\t●    " + paramName + ": " + paramType);
            }

            createParagraph(document, "");
            createSubTittle(document, "Header Params:", false);
            createParagraph(document, "\t●    user: " + "String");
            createParagraph(document, "\t●    pass: " + "String");
            createParagraph(document, "\t●    token: " + "String");
            createParagraph(document, "");
            createSubTittle(document, "Response:", false);
            createParagraph(document, "\t●    Http Response Status");
            createParagraph(document, "\t●    Application JSON");
            createParagraph(document, "\t●    JSON Java Exception");
            //POST =================================================================
            createSubTittle(document, "Http Methods: POST", true);
            createParagraph(document, "");
            createSubTittle(document, "Body:", false);
            createParagraph(document, "{");

            for (Map.Entry entry : mapDetails.entrySet()) {
                TableDetails details = (TableDetails) entry.getValue();
                String paramType = ReflectUtils.getPropperFieldType(details.getType());
                String paramName = details.getField();

                if (details.getKey() == null) {
                    createParagraph(document, "\t\"" + paramName + "\"" + ":    " + paramType + "\t" + (details.getNullable().equals("Y") ? "(OPTIONAL)" : "(REQUIERED)"));
                }

            }

            createParagraph(document, "}");
            createParagraph(document, "");
            createSubTittle(document, "Header Params:", false);
            createParagraph(document, "\t●    Content-Type: application/json");
            createParagraph(document, "\t●    user: " + "String");
            createParagraph(document, "\t●    pass: " + "String");
            createParagraph(document, "\t●    token: " + "String");
            createParagraph(document, "");
            createSubTittle(document, "Response:", false);
            createParagraph(document, "\t●    Http Response Status");
            createParagraph(document, "\t●    Application JSON");
            createParagraph(document, "\t●    JSON Java Exception");
            //PUT ==================================================================
            createSubTittle(document, "Http Methods: PUT", true);
            createParagraph(document, "");
            createSubTittle(document, "Body:", false);
            createParagraph(document, "{");

            for (Map.Entry entry : mapDetails.entrySet()) {
                TableDetails details = (TableDetails) entry.getValue();
                String paramType = ReflectUtils.getPropperFieldType(details.getType());
                String paramName = details.getField();

                createParagraph(document, "\t\"" + paramName + "\"" + ":    " + paramType);
            }

            createParagraph(document, "}");
            createParagraph(document, "");
            createSubTittle(document, "Header Params:", false);
            createParagraph(document, "\t●    Content-Type: application/json");
            createParagraph(document, "\t●    user: " + "String");
            createParagraph(document, "\t●    pass: " + "String");
            createParagraph(document, "\t●    token: " + "String");
            createParagraph(document, "");
            createSubTittle(document, "Response:", false);
            createParagraph(document, "\t●    Http Response Status");
            createParagraph(document, "\t●    Application JSON");
            createParagraph(document, "\t●    JSON Java Exception");
            //DELETE ===============================================================
            createSubTittle(document, "Http Methods: DELETE", true);
            createParagraph(document, "");
            createSubTittle(document, "Body:", false);

            createParagraph(document, "{");
            String paramName = "ID_VALUE";
            String paramType = "VALUE (REQUIERED)";

            for (Map.Entry entry : mapDetails.entrySet()) {
                TableDetails details = (TableDetails) entry.getValue();
                if (details.getKey() != null && details.getKey().equals("PRIMARY")) {
                    paramType = ReflectUtils.getPropperFieldType(details.getType());
                    paramName = details.getField();
                    break;
                }
            }

            createParagraph(document, "\t\"" + paramName + "\"" + ":    " + paramType + "    (REQUIRED)");
            createParagraph(document, "}");
            createParagraph(document, "");
            createSubTittle(document, "Header Params:", false);
            createParagraph(document, "\t●    Content-Type: application/json");
            createParagraph(document, "\t●    user: " + "String");
            createParagraph(document, "\t●    pass: " + "String");
            createParagraph(document, "\t●    token: " + "String");
            createParagraph(document, "");
            createSubTittle(document, "Response:", false);
            createParagraph(document, "\t●    Http Response Status");
            createParagraph(document, "\t●    Application JSON");
            createParagraph(document, "\t●    JSON Java Exception");
            //======================================================================
            document.write(fileOutputStream);

            fileOutputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void createViewWSDocumentation(String tableName, String path, Map<String, TableDetails> mapDetails) {
        FileOutputStream fileOutputStream = null;

        try {

            String classServiceName = StringUtils.toUpperCamelCase(StringUtils.toLowerScoreCase(tableName));
            String fileServiceName = path.concat(Generator.DOCUMENTS_PATH).concat(classServiceName).concat("Documentation.docx");
            fileOutputStream = new FileOutputStream(new File(fileServiceName));

            XWPFDocument document = new XWPFDocument();

            createTittle(document, "Documentación de Servicio web: " + StringUtils.toLowerScoreCase(tableName), false);
            createSubTittle(document, "Documento creado por JDBCUtils.", false);
            createParagraph(document, "");
            createParagraph(document, "URL:    http://<SERVER >:<PORT>/ProjectName/webresources/" + StringUtils.toLowerScoreCase(tableName));
            createParagraph(document, "");
            //GET ==================================================================
            createSubTittle(document, "Http Methods: GET", false);
            createParagraph(document, "");
            createSubTittle(document, "Query Params:", false);

            for (Map.Entry entry : mapDetails.entrySet()) {
                TableDetails details = (TableDetails) entry.getValue();
                String paramType = ReflectUtils.getPropperFieldType(details.getType());
                String paramName = details.getField();

                createParagraph(document, "\t●    " + paramName + ": " + paramType);
            }

            createParagraph(document, "");
            createSubTittle(document, "Header Params:", false);
            createParagraph(document, "\t●    user: " + "String");
            createParagraph(document, "\t●    pass: " + "String");
            createParagraph(document, "\t●    token: " + "String");
            createParagraph(document, "");
            createSubTittle(document, "Response:", false);
            createParagraph(document, "\t●    Http Response Status");
            createParagraph(document, "\t●    Application JSON");
            createParagraph(document, "\t●    JSON Java Exception");

            document.write(fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
