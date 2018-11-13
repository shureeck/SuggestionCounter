import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static stringconstant.StringsConstants.*;

/**
 * Created by Poliakov.A on 12/11/2017.
 */
public class Logger {
    public static void setLog(String msg) {
        //Creating date-time formats
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat formatTime = new SimpleDateFormat(TIME_FORMAT);
        String logName = REFERENCE_UPDATER + formatDate.format(date) + LOG;
        File log = new File(logName);

        //Create new file if not exist
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write data in .log file
        BufferedWriter writer = null;
        try {
            System.out.println(msg);
            writer = new BufferedWriter(new FileWriter(log, true));
            writer.write(formatTime.format(date) + COLON + msg + '\n');
        }//try
        catch (IOException e) {
            e.printStackTrace();
        }//catch
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//finally
    }

    public static void writeFile(File outputFile, String line) {
        //Creating date-time formats
        //Create new file if not exist
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write data in .log file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, true));
            writer.write(line + '\n');
        }//try
        catch (IOException e) {
            e.printStackTrace();
        }//catch
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//finally
    }

    public static void writeFile(File outputFile, ArrayList<String> list) {
        for (String line : list){
            writeFile(outputFile, line);
        }
    }
}
