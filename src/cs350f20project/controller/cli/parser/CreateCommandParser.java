package cs350f20project.controller.cli.parser;

public class CreateCommandParser extends CommandParser {

    private String commandText;
    private String[] commandArr;

    protected CreateCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;
        this.commandArr = commandText.split(" ");
    }

    public void parse(){

        if(commandArr[1].equals("power")){
            power();
        }
        else if(commandArr[1].equals("stock")){
            stock();
        }
        else if(commandArr[1].equals("track")){
            track();
        }
    }

    private void power(){

        CreatePowerCommandParser power = new CreatePowerCommandParser(super.parserHelper, commandText);

        if(commandArr[2].equals("catenary")){
            power.parseCatenary();
        }
        else if(commandArr[2].equals("pole")){
            power.parsePole();
        }
        else if(commandArr[2].equals("station")){
            power.parseStation();
        }
        else if(commandArr[2].equals("substation")){
            power.parseSubstation();
        }
    }


    private void stock(){

    }

    private void track(){

    }
}
