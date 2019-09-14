package commands;

import controllers.FileController;
import interfaces.FileCommand;

public class ReadJsonCommand implements FileCommand {

    String fileName = "";
    FileController fh = new FileController();

    public ReadJsonCommand(String name){
        this.fileName = name;
    }

    public String execute(){
        return fh.open(fileName);
    }

}
