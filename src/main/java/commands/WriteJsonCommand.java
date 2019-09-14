package commands;

import controllers.FileController;
import interfaces.FileCommand;

public class WriteJsonCommand implements FileCommand {

    private String fileName = "";
    private String missingDates = "";
    private FileController fh = new FileController();

    public WriteJsonCommand(String name, String dates){
        fileName = name;
        missingDates = dates;
    }

    public String execute(){
        String path = fh.save(fileName, missingDates);
        return path;
    }
}
