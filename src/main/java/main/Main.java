package main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import commands.GetMissingDatesCommand;
import commands.ReadJsonCommand;
import commands.WriteJsonCommand;
import interfaces.DateCommand;
import interfaces.FileCommand;

public class Main {

    final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try{
            FileCommand cmd = new ReadJsonCommand(args[0]);
            String datesJson = cmd.execute();
            DateCommand dateCmd = new GetMissingDatesCommand(datesJson);
            String missingDates = dateCmd.execute();
            FileCommand writeCmd = new WriteJsonCommand(args[1], missingDates);
            String responseFilePath = writeCmd.execute();
            System.out.println("Creado archivo: "+responseFilePath);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}