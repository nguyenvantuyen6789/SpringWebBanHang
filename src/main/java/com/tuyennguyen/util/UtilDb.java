package com.tuyennguyen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class UtilDb {

    private static Logger log = LoggerFactory.getLogger(UtilDb.class);

    /**
     * String backup
     */
    private static String backup;
    @Value("${backup}")
    public void setBackUp(String backup) {
        this.backup = backup;
    }
    /**
     * String username
     */
    public static String username;
    @Value("${user_name}")
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * String password
     */
    public static String password;
    @Value("${password}")
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * String dbName
     */
    public static String dbName;
    @Value("${dbName}")
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    private static String[] createCommandBackup(String pathDb, String folder, String prefix, String suffix) {
        String osName = System.getProperty("os.name");
        String cmd = "";
        String[] command;
        // if Operation System is Linux
        if ("Linux".equals(osName)) {
            // set path
            if (UtilCon.EMPTY.equals(password)) {
                cmd = "/opt/lampp/bin/mysqldump " + " -u" + username + " --databases " + dbName + " > " + pathDb + folder + prefix + "_" + suffix + ".sql";
            } else {
                cmd = "/opt/lampp/bin/mysqldump " + " -u" + username + " -p" + password + " --databases " + dbName + " > " + pathDb + folder + "/" + prefix + "_" + suffix + ".sql";
            }

            // set command
            command = new String[]{"/bin/sh", "-c", cmd};
        } else {
            // if Operation System is Window
            if (UtilCon.EMPTY.equals(password)) {
                cmd ="\"" + "C:\\xampp\\mysql\\bin\\mysqldump.exe " + " \" -u" + username + " --databases " + dbName + " > \"" + pathDb + "\\" + folder + "\\" + prefix + "_" + suffix + ".sql";
            } else {
                System.out.println(2);
                cmd ="\"" + "C:\\xampp\\mysql\\bin\\mysqldump.exe " + " \" -u" + username + " -p" + password + " --databases " + dbName + " > \"" + pathDb + "\\" + folder + "\\" + prefix + "_" + suffix + ".sql";
            }

            // set command
            command = new String[]{"cmd", "/c", cmd};
        }
        return command;
    }

    public static void backUpDb() {
        String pathDb = UtilPath.getPathResource();
        pathDb = pathDb.replaceAll("resources", "src\\\\main\\\\resources\\\\static");

        String[] command = createCommandBackup(pathDb, "backup_db", "BU" , UtilDate.getYYYYMMDD_HHMMSS());

        try {
            Runtime.getRuntime().exec(command);
            // log success
            log.debug("Back up database successful!\n\t" +
                      "Th??ng b??o th??nh c??ng th?? c?? th??? th??nh c??ng th???t ho???c kh??ng\n\t" +
                      "C??n th??ng b??o backup th???t b???i th?? ch???c ch???n l?? th???t b???i\n\t" +
                      "Check xem c?? th??m file Back up *.sql, n???u c?? th?? m???i ch???c ch???n l?? th??nh c??ng");
        } catch (IOException e) {
            // log error
            log.error(e.getMessage());
            log.error("Back up database error!");
        }
    }

}
