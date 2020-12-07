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
        else {
        	throw new RuntimeException(commandText + " not recognized!");
        }
    }
    
    private void runFile() {
    	String fileString = commandText.substring(4).trim();
    	
    	String fileName = "";
    	if(!fileString.isBlank() && (fileString.charAt(0) == '\'' && fileString.charAt(fileString.length()-1) == '\'')) {
    		fileName = fileString.substring(1, fileString.length()-1).trim();
    		if(fileName.isBlank())
    			throw new RuntimeException("File Name passed in is empty");
    	}
    	else {
    		throw new RuntimeException("File " + fileString + " Not A Valid Input!");
    	}

        this.command = new CommandMetaDoRun(fileName);
        this.parserHelper.getActionProcessor().schedule(this.command);
    }

}

