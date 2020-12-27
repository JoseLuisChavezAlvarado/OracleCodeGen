package code_generator;

import code_generator.documents.DocumentGenerator;
import code_generator.entities.EntitiesGenerator;
import code_generator.security.SecurityGenerator;
import code_generator.services.ServiceEntitiesGenerator;
import code_generator.services.ServiceViewsGenerator;
import code_generator.views.ViewsGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import penoles.oraclebdutils.controllers.DatabaseConfigController;
import penoles.oraclebdutils.entities.KeyColumnObject;
import penoles.oraclebdutils.entities.TableDetails;
import penoles.oraclebdutils.entities.UserTable;
import penoles.oraclebdutils.entities.ViewObject;
import penoles.oraclebdutils.singleton.DataInstance;
import utils.FileUtils;

/**
 *
 * @author Jose Luis Ch.
 */
public class Generator {

    public static final String VIEW_SERVICES_PATH = "Generated/src/resources/views/";
    public static final String DB_VIEWS_PATH = "Generated/src/entities/views/";
    public static final String ENTITIES_PATH = "Generated/src/entities/";
    public static final String SERVICES_PATH = "Generated/src/resources/";
    public static final String SECURYTY_PATH = "Generated/src/security/";
    public static final String DOCUMENTS_PATH = "Generated/documents/";
    public static final String TEST_PATH = "Generated/src/test/";

    public static void generate(String path) {

        //WRITE DIRECTORIES
        FileUtils.createPath(path, VIEW_SERVICES_PATH);
        FileUtils.createPath(path, DOCUMENTS_PATH);
        FileUtils.createPath(path, DB_VIEWS_PATH);
        FileUtils.createPath(path, ENTITIES_PATH);
        FileUtils.createPath(path, SERVICES_PATH);
        FileUtils.createPath(path, SECURYTY_PATH);
        FileUtils.createPath(path, TEST_PATH);

        List<KeyColumnObject> keyColumnObjectsList = DataInstance.getInstance().getKeyColumnObjectList();
        List<ViewObject> viewObjectsList = DataInstance.getInstance().getViewObjectList();
        List<UserTable> userTables = DataInstance.getInstance().getUserTablesList();

        //DELETE DUPLICATED CONSTRAINTS
        Set<KeyColumnObject> set = new HashSet<>(keyColumnObjectsList);
        keyColumnObjectsList.clear();
        keyColumnObjectsList.addAll(set);

        //GENERATE VIEWS
        for (ViewObject viewObject : viewObjectsList) {
            Map<String, TableDetails> tableDetailsMap = DatabaseConfigController.getTableDetails(viewObject.getTable_name());
            ViewsGenerator.createEntityClass(viewObject, path, tableDetailsMap);
            ServiceViewsGenerator.createServiceClass(viewObject, path, tableDetailsMap);
            DocumentGenerator.createViewWSDocumentation(viewObject.getTable_name(), path, tableDetailsMap);
        }

        //GENERATE ENTITIES, RESOURCES AND DOCUMENTS FOR EVERY TABLE
        for (UserTable userTable : userTables) {
            Map<String, TableDetails> tableDetailsMap = DatabaseConfigController.getTableDetails(userTable.getTable_name());
            ServiceEntitiesGenerator.createServiceClass(userTable, path, tableDetailsMap, keyColumnObjectsList);
            EntitiesGenerator.createEntityClass(userTable, path, tableDetailsMap, keyColumnObjectsList);
            DocumentGenerator.createEntitiesWSDocumentation(userTable.getTable_name(), path, tableDetailsMap);
        }

        //CREATE EXTRA CLASSES FOR UTILITIES AND TEST
        ServiceEntitiesGenerator.createExtraServices(path, userTables);
        SecurityGenerator.createSecurityClass(path);
    }

}
