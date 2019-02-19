package app.cl.CityLogs.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Utils {

    public static ArrayList<CityLogs> cityLogsArrayList = new ArrayList<CityLogs>();  //Store Field data in Array List
    public static ArrayList<CityLogs> tempcityLogsArrayList = new ArrayList<CityLogs>();  //Temporary Store Field data in Array List

    public static List<String> menuList = new LinkedList<String>() {{
        add("Perth");
        add("Brisbane");
        add("Sydney");
        add("Melbourne");
        add("Adelaide");
    }};

    /**
     * For showing error message in EditText
     */
    public static Map<String, String> errorMap = new HashMap<String, String>();

    /*
    Validate Input Log Entries
     */
    public static boolean validateInputData(String time, String contact, String invoice, String destination) {
        if (!(time.equals("") || contact.equals("") || invoice.equals("")|| destination.equals(""))) {
            try {

                if ( time.equals(""))
                        Utils.errorMap.put("time", "err, Empty");
                if ( contact.equals(""))
                        Utils.errorMap.put("contact", "err, Empty");
                if (invoice.equals(""))
                        Utils.errorMap.put("invoice", "err, Empty");

                return true;

            } catch (Exception ex) {
                return false;
            }
        }
        else{
        return false;
    }

    }

    public static int getNextPosition(int currentPosition) {
        int position;
        if (currentPosition == Utils.menuList.size() - 1)
            position = 0;
        else
            position = currentPosition + 1;
        return position;
    }

    public static int getPreviousPosition(int currentPosition) {
        int position;
        if (currentPosition == 0)
            position = Utils.menuList.size() - 1;
        else
            position = currentPosition - 1;
        return position;
    }
}
