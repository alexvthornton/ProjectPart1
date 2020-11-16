package cs350f20project.controller.cli.parser;

public class AtCommandParser extends CommandParser{

    protected AtCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parse(){
        System.out.println(super.commandText);
    }

}
