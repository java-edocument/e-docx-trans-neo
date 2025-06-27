package org.rmj.edocx.trans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;

/**
 *
 * @author Maynard
 */
public class EDocSysUtil {

    public static boolean RSyncFile(GRider foApp, String fsTransNo) {
        String Master_Table = "EDocSys_Master";
        String Detail_Table = "EDocSys_Detail";

        String SystemFileDir = "";
        String ServerFileDir = "";
        String lsServerUser = "";
        String lsServerPass = "";
        String path;

        try {
            String lsSQLMaster = " SELECT "
                    + " sTransNox "
                    + " , cTranStat "
                    + "   FROM " + Master_Table
                    + " WHERE sTransNox = "
                    + SQLUtil.toSQL(fsTransNo);

            String lsSQLDetail = " SELECT "
                    + " sTransNox "
                    + " , sFileName "
                    + "   FROM " + Detail_Table
                    + " WHERE sTransNox = "
                    + SQLUtil.toSQL(fsTransNo);

            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = "D:/GGC_Java_Systems";
            } else {
                path = "/srv/GGC_Java_Systems";
            }

            System.setProperty("sys.default.path.config", path);

            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/edocs.properties"));

            
            //Default Path
            System.setProperty("pos.backend.images.dir01", po_props.getProperty("pos.backend.images.dir01"));
            //FILE SERVER INFO
//            System.setProperty("pos.backend.server.domain", po_props.getProperty("pos.backend.server.domain"));
            System.setProperty("pos.backend.images.dir02", po_props.getProperty("pos.backend.images.dir02"));
            System.setProperty("pos.backend.sys.user", po_props.getProperty("pos.backend.sys.user"));
            System.setProperty("pos.backend.sys.pass", po_props.getProperty("pos.backend.sys.pass"));

            SystemFileDir = System.getProperty("pos.backend.images.dir01");
            ServerFileDir = System.getProperty("pos.backend.images.dir02");
            lsServerUser = System.getProperty("pos.backend.sys.user");
            lsServerPass = System.getProperty("pos.backend.sys.pass");

            if (lsServerUser.isEmpty() && lsServerPass.isEmpty()
                    && ServerFileDir.isEmpty() && SystemFileDir.isEmpty()) {
                return false;
            }
            //check connection to drive
            if (!isSharedFolderConnected(ServerFileDir, lsServerUser, lsServerPass)) {
                return false;
            }

            System.err.println(MiscUtil.addCondition(lsSQLMaster, " cSendStat = '1' ").toString());
            ResultSet loRS = foApp.executeQuery(MiscUtil.addCondition(lsSQLMaster, " cSendStat = '1' "));

            boolean hasRows = false;
            while (loRS.next()) {
                hasRows = true;
                ResultSet loRSDetail = foApp.executeQuery(lsSQLDetail);

                //Get file name in details then move to local directory
                while (loRSDetail.next()) {

                    Path lsFileImageServer = Paths.get(ServerFileDir + "\\"
                            + loRS.getString("sTransNox") + "\\"
                            + loRSDetail.getString("sFileName"));

                    if (!Files.exists(lsFileImageServer)) {
                        return false;
                    }

                    Path lsLocalFileImagePath = Paths.get(SystemFileDir);
                    // Check if the img directory folder already exists
                    Path destinationFilePath = lsLocalFileImagePath.resolve(loRSDetail.getString("sFileName"));

                    // Check if the file exists
                    if (Files.exists(lsFileImageServer)) {
                        Files.copy(lsFileImageServer, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

                    } else {
                        System.out.println("The file does not exist: " + lsFileImageServer);
                        return false;
                    }

                }

                loRSDetail.close();
                System.out.println(loRS.getString("sTransNox") + " successfully retrieve");

            }

            if (!hasRows) {
                return false;
            }

            loRS.close();

            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isSharedFolderConnected(String fsFileDir, String fsUser, String fsPass) {
        try {
            // Command to connect the network drive
            String command = "net use " + fsFileDir + "  /user:" + fsUser + " " + fsPass;
            ProcessBuilder pbConnect = new ProcessBuilder("cmd.exe", "/c", command);
            //execute
            Process pConnect = pbConnect.start();
            int resultConnect = pConnect.waitFor();
            if (resultConnect == 0) {
                System.out.println("Network drive Connected successfully.");
                return true;
            } else {
                System.err.println("Failed to Connect the network drive.");
                return false;
            }
        } catch (InterruptedException | IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

}
