package controllers;

import com.google.gson.*;
import model.dateRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class manage the needed operations
 * to calculate missing dates of the time period
 * obtained from json file
 */
public class DateRangeController {

    final static Logger logger = LogManager.getLogger(DateRangeController.class);
    private JsonArray listaFechas = new JsonArray(); // List of all read dates in json format
    private ArrayList<String> missingDates = new ArrayList<>(); // List of all missing dates from period obtained
    private String datesJson = ""; // String read from json file containing the dates
    private JsonObject datesJsonObject; // Store the dates in json format

    public DateRangeController(String dates){
        datesJson = dates;
        datesJsonObject = new JsonParser().parse(datesJson).getAsJsonObject();
        listaFechas.add(datesJsonObject.get("fechaCreacion"));
        listaFechas.addAll(datesJsonObject.get("fechas").getAsJsonArray());
        listaFechas.add(datesJsonObject.get("fechaFin"));
    }

    /**
     * This function iterate the dates array for calculate missing dates
     * @return json containing missing dates
     */
    public String iterateDates(){
        LocalDate initialDate =  LocalDate.parse(listaFechas.get(0).getAsString()); // Creation date
        LocalDate lastDate = LocalDate.parse(listaFechas.get(listaFechas.size()-1).getAsString()); // Last date
        Integer currentYear = initialDate.getYear(); // Current year being evaluated
        ArrayList<Integer> monthsReceived = new ArrayList<Integer>(); // Months received in json of current year

        // Iterate the json
        for ( JsonElement date : listaFechas ) {
            Integer year = LocalDate.parse(date.getAsString()).getYear();
            Integer month = LocalDate.parse(date.getAsString()).getMonth().getValue();

            // While in the same year, keep counting the months received in the json for this year
            if(year.intValue() == currentYear.intValue())
                monthsReceived.add(month);
            else{
                // If is not the first year or final year, callculate the missing months as usual.
                // This is due to the requirements of the project that ask for special treatment in
                // border conditions (creation year or final year).
                if (currentYear.intValue() != initialDate.getYear() && currentYear.intValue() != lastDate.getYear() )
                    getMissingDates(currentYear, monthsReceived);
                else if (currentYear.intValue() == initialDate.getYear())
                    getMissingDates(currentYear, monthsReceived, true);

                currentYear = new Integer(year);
                monthsReceived = new ArrayList<>();
                monthsReceived.add(month);
            }
        }
        // At this point, variables 'currentYear' and 'monthsReceived' belong to the last year
        getMissingDates(currentYear, monthsReceived, false);
        return serialiazeDates();
    }

    /**
     * Calculate the missing months of a year in the json received
     * @param year year to be evaluated
     * @param months read months
     */
    private void getMissingDates(Integer year, List<Integer> months){
        Collections.sort(months);
        ArrayList<Integer> missingMonths = new ArrayList<>();
        for(int i = 1; i<13; i++)
            if (!months.contains(i))
                missingDates.add(LocalDate.of(year, i, 1).toString());
    }

    /**
     * This method calculate the missing months in border condition (first or last year)
     * @param year year to be evaluated
     * @param months read months
     * @param firstYear True for first year, false for last year
     */
    private void getMissingDates(Integer year, List<Integer> months, boolean firstYear){
        Collections.sort(months);
        ArrayList<Integer> missingMonths = new ArrayList<>();
        int initialMonth, lastMonth;

        // If first year, dont count months before the first month registered
        if (firstYear){
            initialMonth = months.get(0); lastMonth = 13;
            months.remove(0);
        }
        // If last year, dont count the months after the last registered
        else{
            initialMonth = 1; lastMonth = months.get(months.size()-1)+1;
            months.remove(months.size()-1);
        }

        for(int i = initialMonth; i<lastMonth; i++)
            if (!months.contains(i))
                missingDates.add(LocalDate.of(year, i, 1).toString());
    }

    /**
     * @return json with required missing dates structure
     */
    private String serialiazeDates(){

        dateRange dr = new dateRange(
                datesJsonObject.get("id").getAsInt(),
                datesJsonObject.get("fechaCreacion").getAsString(),
                datesJsonObject.get("fechaFin").getAsString(),
                missingDates
        );

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = gson.toJson(dr);
        logger.info(jsonResponse);
        return jsonResponse;
    }

}
