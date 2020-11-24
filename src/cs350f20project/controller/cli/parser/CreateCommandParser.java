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

        if(commandArr.length<2){
            throw new RuntimeException("invalid create command");
        }

        if(commandArr[1].equalsIgnoreCase("power")){
            power();
        }
        else if(commandArr[1].equalsIgnoreCase("stock")){
            stock();
        }
        else if(commandArr[1].equalsIgnoreCase("track")){
            track();
        }
        else{
            throw new RuntimeException("invalid create command");
        }
    }

    private void power(){

        if(commandArr.length<3){
            throw new RuntimeException("invalid create power command");
        }

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
        else{
            throw new RuntimeException("invalid create power command");
        }
    }


    private void stock(){

        if(commandArr.length<3){
            throw new RuntimeException("invalid create stock command");
        }

        CreateStockCommandParser stock = new CreateStockCommandParser(super.parserHelper, commandText);

        if(commandArr[2].equalsIgnoreCase("car")){
            stock.parseCar();
        }
        else if(commandArr[2].equalsIgnoreCase("engine")){
            stock.parseEngine();
        }
        else{
            throw new RuntimeException("invalid create stock command");
        }
    }

    private void track(){

        if(commandArr.length<3){
            throw new RuntimeException("invalid create track command");
        }

        CreateTrackCommandParser track = new CreateTrackCommandParser(super.parserHelper, commandText);

        if(commandArr[2].equalsIgnoreCase("curve")){
            track.parseCurve();
        }
        else if(commandArr[2].equalsIgnoreCase("straight")){
            track.parseStraight();
        }
        else if(commandArr[2].equalsIgnoreCase("layout")){
            track.parseLayout();
        }
        else if(commandArr[2].equalsIgnoreCase("roundhouse")){
            track.parseRoundhouse();
        }
        else if(commandArr[2].equalsIgnoreCase("switch")){
            track.parseSwitch();
        }
        else{
            throw new RuntimeException("invalid create track command");
        }

    }



}
