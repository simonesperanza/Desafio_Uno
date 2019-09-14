package commands;

import controllers.DateRangeController;
import interfaces.DateCommand;

public class GetMissingDatesCommand implements DateCommand {

    private String datesJson = "";

    public GetMissingDatesCommand(String datesJson){
        this.datesJson = datesJson;
    }

    public String execute(){
        DateRangeController dateRangeController = new DateRangeController(datesJson);
        return dateRangeController.iterateDates();
    }
}
