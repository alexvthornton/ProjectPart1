package cs350f20project.controller.cli.parser;

public class CreateCommandParser extends CommandParser {

    protected CreateCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }
    public void parse(){

        String[] commandArr = super.commandText.split(" ");
        if(commandArr[1]=="power"){
            power();
        }
        else if(commandArr[1]=="stock"){
            stock();
        }
        else if(commandArr[1]=="track"){
            track();
        }
    }

    private void power(){

    }

    private void stock(){

    }

    private void track(){

    }
}
