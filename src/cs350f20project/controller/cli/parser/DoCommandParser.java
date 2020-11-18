package cs350f20project.controller.cli.parser;

public class DoCommandParser extends CommandParser{

    private String commandText;

    protected DoCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;
    }

    public void parse(){

        String[] commandArr = commandText.split(" ");
        if(commandArr[1].equals("select")){
            select();
        }
        else if(commandArr[1].equals("set")){
            set();
        }
        else if(commandArr[1].equals("brake")){

        }

    }

    private void select(){

    }

    private void set(){

    }

}
