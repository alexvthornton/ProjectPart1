package cs350f20project.controller.cli.parser;

public class DoCommandParser extends CommandParser{

    protected DoCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parse(){

        String[] commandArr = super.commandText.split(" ");
        if(commandArr[1]=="select"){
            select();
        }
        else if(commandArr[1]=="set"){
            set();
        }
        else if(commandArr[1]=="brake"){

        }

    }

    private void select(){

    }

    private void set(){

    }

}
