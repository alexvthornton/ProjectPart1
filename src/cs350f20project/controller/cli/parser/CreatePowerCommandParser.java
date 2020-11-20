package cs350f20project.controller.cli.parser;



import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreatePowerCatenary;
import cs350f20project.controller.command.creational.CommandCreatePowerPole;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CreatePowerCommandParser extends CreateCommandParser{


    protected CreatePowerCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCatenary(){
        boolean matches = super.commandText.toLowerCase().matches("create power catenary \\w+ with poles \\w+(( \\w+)+)?"); //[[ \w+]+]?

        if(matches) {
            System.out.println(matches);
        }
        else
        {
            System.out.println(super.commandText);
        }



        String id1 = super.commandArr[3];
        List<String> idList = new ArrayList<String>();

        for(int i = 6; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }
/*
        System.out.println("id1: " + id1);
        System.out.print("idList: ");
        for(int i = 0; i < idList.size(); i++){
            System.out.print(idList.get(i) + " ");
        }
*/

        A_Command command = new CommandCreatePowerCatenary(id1, idList);
        this.parserHelper.getActionProcessor().schedule(command);


    }

    public void parsePole(){
        boolean matches = super.commandText.toLowerCase().matches("create power pole \\w+ on track \\w+ distance (\\d+(\\.\\d+)?) from start|finsih"); //\d+|((\d+)?\.\d+) "create power pole \\w+ on track \\w+ distance (\\d+|((\\d+)?\\.\\d+)) from (start|finsih)"

        if(matches) {
            System.out.println(matches);
        }
        else
        {
            System.out.println(super.commandText);
        }

        String id1 = super.commandArr[3];
        String id2 = super.commandArr[6];
        double distance = Double.parseDouble(super.commandArr[8]);
        boolean isFromAElseB = super.commandArr[10].equalsIgnoreCase("start");

        TrackLocator tl = new TrackLocator(id2, distance, isFromAElseB);

        /*
        System.out.println("id1: " + id1);
        System.out.println("TrackLocator: " + id2 + ", " + distance + ", " + isFromAElseB);
         */

        A_Command command = new CommandCreatePowerPole(id1, tl);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseStation(){
        // solve station command here
    }

    public void parseSubstation(){
        // solve substation command here
    }
}
