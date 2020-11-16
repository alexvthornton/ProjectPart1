package cs350f20project.controller.cli.parser;

public class CommandParser {

    protected MyParserHelper parserHelper;
    protected String commandText;

    public CommandParser(MyParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText.toLowerCase();
    }

    public void parse(){

        System.out.println("our parser!");
        if(commandText.charAt(0)=='@'){
            AtCommandParser acp = new AtCommandParser(parserHelper, commandText);
            acp.parse();
        }
        else if(commandText.charAt(0)=='d'){
            DoCommandParser dcp = new DoCommandParser(parserHelper, commandText);
            dcp.parse();
        }
        else if(commandText.charAt(0)=='c'){
            CreateCommandParser ccp = new CreateCommandParser(parserHelper, commandText);
            ccp.parse();
        }
    }


    /*
    other cases that must be handled

    open
    commit
    couple
    locate
    use
    Rule#2throughRule#65
     */
}
