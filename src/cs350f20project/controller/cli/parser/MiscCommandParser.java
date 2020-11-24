package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.structural.CommandStructuralCommit;
import cs350f20project.controller.command.structural.CommandStructuralCouple;
import cs350f20project.controller.command.structural.CommandStructuralLocate;

public class MiscCommandParser extends CommandParser{
	
	 private String commandText;
	 private String[] commandArr;
	 private final static String commandOpen = "OPEN VIEW ORIGIN WORLD WIDTH SCREEN WIDTH HEIGHT", commandCouple = "COUPLE STOCK AND", commandLocate = "LOCATE STOCK ON TRACK DISTANCE FROM";
	 private static String enteredCommand;
	 
	 private A_Command command;
	 
	 protected MiscCommandParser(MyParserHelper parserHelper, String commandText) {
		 super(parserHelper, commandText);
		 this.commandText = commandText;
		 this.commandArr = commandText.split(" ");
	 }
	 
	 public void parse() {
		 
		 if(commandArr[0].equalsIgnoreCase("open") && commandArr.length == 13){
			 open();
	     }
	     else if(commandArr[0].equalsIgnoreCase("commit")){
	        	commit();
	     }
	     else if(commandArr[0].equalsIgnoreCase("couple") && commandArr.length == 5){
	    	 couple();
	     }
	     else if(commandArr[0].equalsIgnoreCase("locate") && commandArr.length == 10){
	    	 locate();
	     }
	     else {
	    	 throw new RuntimeException("Invalid Command!");
	     }
	 }
	 
	 private void open() {
		 enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[3] + " " + commandArr[5] + " " + commandArr[6] + " " + commandArr[8] + " " + commandArr[9] + " " + commandArr[11];
		 if(commandOpen.equalsIgnoreCase(enteredCommand)) {
			 
		 }
	 }
	 
	 private void commit() {
		 this.command = new CommandStructuralCommit();
     	 this.parserHelper.getActionProcessor().schedule(this.command);
	 }
	 
	 private void couple() {
		 enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[3];
		 if(commandCouple.equalsIgnoreCase(enteredCommand)) {
			 String idStock1 = commandArr[2], idStock2 = commandArr[4];
			 this.command = new CommandStructuralCouple(idStock1, idStock2);
			 this.parserHelper.getActionProcessor().schedule(this.command);
		 }
		 else {
			 throw new RuntimeException("Invalid Command!");
		 }	 
	 }
	 
	 private void locate() {
		 enteredCommand = commandArr[0] + " " + commandArr[1] + " " + commandArr[3] + " " + commandArr[4] + " " + commandArr[6] + " " + commandArr[8];
		 if(commandLocate.equalsIgnoreCase(enteredCommand)) {
			  String stockId = commandArr[2], trackId = commandArr[5], startOrEnd = commandArr[9];
			  double distance = 0;
			  try{
				  distance = Double.parseDouble(commandArr[7]);
			  } catch (Exception e) {
				  throw new RuntimeException(commandArr[7] + " must be a number!");
			  }
			  boolean isStartOrEnd;
			  if(startOrEnd.equalsIgnoreCase("START")) {
				  isStartOrEnd = true;
			  }
			  else if(startOrEnd.equalsIgnoreCase("END")){
				  isStartOrEnd = false;
			  }
			  else
				  throw new RuntimeException(startOrEnd + " must be START or END");
			  
			  TrackLocator locator = new TrackLocator(trackId, distance, isStartOrEnd);
			  this.command = new CommandStructuralLocate(stockId, locator);
			  this.parserHelper.getActionProcessor().schedule(this.command);
		 }
		 else {
			 throw new RuntimeException("Invalid Command!");
		 } 
	 }
}
