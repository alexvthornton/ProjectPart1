package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoRun;

public class AtCommandParser extends CommandParser{

    private String commandText;
    private A_Command command;

    protected AtCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;

    }

    public void parse(){
        String secondPart = commandText.substring(1).split(" ")[0];

        if(secondPart.equalsIgnoreCase("run")){
        	runFile();
        }
        else if(secondPart.equalsIgnoreCase("schedule")) {
        	
        }
        else {
        	throw new RuntimeException(commandText + " not recognized!");
        }
    }
    
    private void runFile() {
    	String fileName = "";
    	try {
    		fileName = commandText.split(" ")[1];
    	} catch (Exception e) {
    		throw new RuntimeException("Missing File Name!");
    	}

        this.command = new CommandMetaDoRun(fileName);
        this.parserHelper.getActionProcessor().schedule(this.command);
    }

}
