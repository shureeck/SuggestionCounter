import java.io.*;
import java.util.ArrayList;

import static stringconstant.LoggerMessages.*;
import static stringconstant.StringsConstants.*;

/**
 * Created by Poliakov.A on 11/15/2017.
 */
public class ReadFile {

    public ArrayList<String> readFile(String path) {
        ArrayList<String> result = new ArrayList<String>();
        FileReader sc = null;
        BufferedReader buffer = null;

        if (path != (null)) {
            try {
                sc = new FileReader(path);
                buffer = new BufferedReader(sc);
                String line = buffer.readLine().trim();

                while (line != null) {
                    line.replaceAll(UNVISIBLE_u202A, EMPTY);
                    if (line.trim().matches(TESTLIST_STRING_FORMAT)) {
                        result.add(line);
                        Logger.setLog(LINE_READ + line);
                    } else {
                        Logger.setLog(STRING_HAS_WRONG_FORMAT + line);
                    }
                    line = buffer.readLine();
                }//while

                buffer.close();
                sc.close();
                Logger.setLog(FILE + new File(path).getName() + READ_SUCCESSFULLY);
            }//try

            catch (FileNotFoundException e) {
                Logger.setLog(FILE_NOT_FOUND);
                Logger.setLog(e.getStackTrace().toString());
                //   e.printStackTrace();
            }//catch;
            catch (IOException e) {
                Logger.setLog(COULD_NOT_READ_FILE);
                Logger.setLog(e.getStackTrace().toString());
                //  e.printStackTrace();
            }//catch;
        }//if
        else {
            Logger.setLog(PATH_TO_TESTLIST_MISSING);
        }

        return result;

    }// fileRead
}
