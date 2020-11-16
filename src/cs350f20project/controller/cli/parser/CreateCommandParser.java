package cs350f20project.controller.cli.parser;

public class CreateCommandParser extends CommandParser {

    protected CreateCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }
    public void parse(){
        System.out.println(super.commandText);
    }
}
