package cs350f20project.controller.cli.parser;

public class AtCommandParser extends CommandParser{

    private String commandText;

    protected AtCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;

    }

    public void parse(){
        String secondPart = commandText.substring(1).split(" ")[0];

        if(secondPart.equals("run")){
            
        }
        else if(secondPart.equals("schedule")) {

        }
    }

}
