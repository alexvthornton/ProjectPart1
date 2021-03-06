package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.behavioral.*;

public class DoCommandParser extends CommandParser{

    private String commandText;
    private A_Command command;
    
    private final static String command2 = "DO BRAKE", command8 = "DO SELECT SWITCH PATH", command11 = "DO SET DIRECTION", command12 = "DO SET REFERENCE ENGINE", command15 = "DO SET SPEED";
    private static String enteredCommand;

    protected DoCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;
    }

    public void parse(){

        String[] commandArr = commandText.split(" ");
        if(commandArr.length == 6 && commandArr[1].equalsIgnoreCase("select")){
            select(commandArr);
        }
        else if(commandArr.length == 5 && commandArr[1].equalsIgnoreCase("set")){
            set(commandArr);
        }
        else if(commandArr.length == 3 && commandArr[1].equalsIgnoreCase("brake")){
        	brake(commandArr);
        }
        else {
        	throw new RuntimeException("Invalid DO Command!");
        }
    }
    
    private void brake(String[] commandArr) {
    	enteredCommand = commandArr[0] + " " + commandArr[1];
    	if(command2.equalsIgnoreCase(enteredCommand)) {
    		String engineId = commandArr[2];
    		this.command = new CommandBehavioralBrake(engineId);
    		this.parserHelper.getActionProcessor().schedule(this.command);
    	}
    	else 
    		throw new RuntimeException("Invalid DO BRAKE Command!");
    }

    private void select(String[] commandArr){
    	enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[2] + " " + commandArr[4];
    	if(command8.equalsIgnoreCase(enteredCommand)){
    		
    		String id = commandArr[3];
    		boolean primaryElseSecondary = false;
    		if(commandArr[5].equalsIgnoreCase("primary"))
    			primaryElseSecondary = true;
    		else if(commandArr[5].equalsIgnoreCase("secondary"))
    			primaryElseSecondary = false;
    		else {
    			throw new RuntimeException(commandArr[5] + " not recognized, must be PRIMARY or SECONDARY");
    		}
    		
    		this.command = new CommandBehavioralSelectSwitch(id, primaryElseSecondary);
    		this.parserHelper.getActionProcessor().schedule(this.command);
    	}//end initial if
    	else {
    		throw new RuntimeException("Invalid DO SELECT SWITCH Command!");
    	}
    }

    private void set(String [] commandArr){
    	String id;
    	this.command = null;
    	
    	if(commandArr[3].equalsIgnoreCase("direction")) {
    		enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[3];
    		if(command11.equalsIgnoreCase(enteredCommand)){
    			this.command = setDirection(commandArr);	
    		}//
    	}//end direction if
    	else if(commandArr[2].equalsIgnoreCase("reference")) {    		
    		enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[2] + " " + commandArr[3];
    		if(command12.equalsIgnoreCase(enteredCommand)){
    			id = commandArr[4];
    			this.command = new CommandBehavioralSetReference(id);
    		}//   		
    	}//end reference if
    	else if(commandArr[3].equalsIgnoreCase("speed")) {   		
    		enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[3];
    		if(command15.equalsIgnoreCase(enteredCommand)){
    			id = commandArr[2];
    			try {
    				double speed = Double.parseDouble(commandArr[4]);
    				this.command = new CommandBehavioralSetSpeed(id, speed);
    			} catch(Exception e) {
    				throw new RuntimeException(commandArr[4] + " invalid, must be a number!");
    			}
    		}//    		
    	}//end speed if
    	else {
    		throw new RuntimeException("Invalid DO SET Command!");
    	}

    	if(this.command == null) {
    		throw new RuntimeException("Invalid Command!");
    	}
    	this.parserHelper.getActionProcessor().schedule(this.command);
    	
    }//end set
    
    private A_Command setDirection(String [] commandArr) {
    	String id = commandArr[2];
		boolean forwardOrBackward;
		if(commandArr[4].equalsIgnoreCase("forward"))
			forwardOrBackward = true;
		else if(commandArr[4].equalsIgnoreCase("backward"))
			forwardOrBackward = false;
		else {
			throw new RuntimeException(commandArr[4] + " not recognized, must be FORWARD or BACKWARD");
		}
		return new CommandBehavioralSetDirection(id, forwardOrBackward);
    }

}

