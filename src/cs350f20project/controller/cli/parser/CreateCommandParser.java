package cs350f20project.controller.cli.parser;

public class CreateCommandParser extends CommandParser {

    protected String commandText;
    protected String[] commandArr;

    protected CreateCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
        this.commandText = commandText;
        this.commandArr = commandText.split(" ");
    }

    public void parse(){

        if(commandArr[1].equalsIgnoreCase("power")){
            power();
        }
        else if(commandArr[1].equalsIgnoreCase("stock")){
            stock();
        }
        else if(commandArr[1].equalsIgnoreCase("track")){
            track();
        }
    }

    private void power(){

        CreatePowerCommandParser power = new CreatePowerCommandParser(super.parserHelper, commandText);

        if(commandArr[2].equalsIgnoreCase("catenary")){
            power.parseCatenary();
        }
        else if(commandArr[2].equalsIgnoreCase("pole")){
            power.parsePole();
        }
        else if(commandArr[2].equalsIgnoreCase("station")){
            power.parseStation();
        }
        else if(commandArr[2].equalsIgnoreCase("substation")){
            power.parseSubstation();
        }
    }


    private void stock(){

        CreateStockCommandParser stock = new CreateStockCommandParser(super.parserHelper, commandText);

        if(commandArr[2].equalsIgnoreCase("car")){
            stock.parseCar();
        }
        else if(commandArr[2].equalsIgnoreCase("engine")){
            stock.parseEngine();
        }
    }

    private void track(){

    }
}
