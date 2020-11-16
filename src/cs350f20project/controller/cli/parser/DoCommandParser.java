package cs350f20project.controller.cli.parser;

public class DoCommandParser extends CommandParser{

    protected DoCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parse(){
        System.out.println(super.commandText);
    }
}
