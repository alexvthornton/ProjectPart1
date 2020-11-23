package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreateStockCarBox;
import cs350f20project.controller.command.creational.CommandCreateStockCarCaboose;
import cs350f20project.controller.command.creational.CommandCreateStockEngineDiesel;

public class CreateStockCommandParser extends CreateCommandParser{

    protected CreateStockCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCar(){ //create stock car IDcar as box

        boolean matches = super.commandText.toLowerCase().matches("create stock car \\w+ as (box|caboose)");

        if(!matches) {
            throw new RuntimeException("Invalid create stock car command");
        }

        String id = super.commandArr[3];

        A_Command command;

        if(super.commandArr[5].equalsIgnoreCase("box")){
            command = new CommandCreateStockCarBox(id);
        }
        else{
            command = new CommandCreateStockCarCaboose(id);
        }

        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseEngine(){
        //CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )
        boolean matches = super.commandText.toLowerCase().matches("create stock engine \\w+ as diesel on track \\w+ distance (\\d+(\\.\\d+)?) from (start|end) facing (start|end)");

        if(!matches) {
            throw new RuntimeException("Invalid create stock engine command");
        }
        else{
            System.out.println("matches");
        }

        String id1 = super.commandArr[3];

        String id2 = super.commandArr[8];
        double distance = Double.parseDouble(super.commandArr[10]);
        boolean isFromAElseB = super.commandArr[10].equalsIgnoreCase("start");
        TrackLocator tl = new TrackLocator(id2, distance, isFromAElseB);

        boolean isFacingAElseB = super.commandArr[10].equalsIgnoreCase("start");

        A_Command command = new CommandCreateStockEngineDiesel(id1, tl, isFacingAElseB);
        this.parserHelper.getActionProcessor().schedule(command);


    }
}
