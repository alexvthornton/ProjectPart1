package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreateStockCarBox;
import cs350f20project.controller.command.creational.CommandCreateStockCarCaboose;

public class CreateStockCommandParser extends CreateCommandParser{

    protected CreateStockCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCar(){

        String id = super.commandArr[3];

        A_Command command;

        if(super.commandArr[5].equalsIgnoreCase("box")){
            command = new CommandCreateStockCarBox(id);
        }
        else{
            command = new CommandCreateStockCarCaboose(id);
        }

        this.parserHelper.getActionProcessor().schedule(command);
        
    }

    public void parseEngine(){

    }
}
