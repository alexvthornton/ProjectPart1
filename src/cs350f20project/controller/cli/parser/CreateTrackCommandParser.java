package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreateTrackCurve;
import cs350f20project.controller.command.creational.CommandCreateTrackLayout;
import cs350f20project.controller.command.creational.CommandCreateTrackRoundhouse;
import cs350f20project.datatype.Angle;
import cs350f20project.datatype.CoordinatesDelta;
import cs350f20project.datatype.CoordinatesWorld;

import java.util.ArrayList;
import java.util.List;

public class CreateTrackCommandParser extends CreateCommandParser{

    protected CreateTrackCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCurve(){
        //CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 ( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )

        String id1 = super.commandArr[3];

        CoordinatesWorld cw;
        if(super.commandArr[5].charAt(0)=='$'){
            cw = parserHelper.getReference(super.commandArr[5].substring(1));
        }
        else {
            String[] latAndLon = super.commandArr[5].split("/");
            cw = calcCoordWorld(latAndLon[0], latAndLon[1]);
        }

        String [] coordDelta1 = super.commandArr[8].split(":");
        Double x1 = Double.parseDouble(coordDelta1[0]);
        Double y1 = Double.parseDouble(coordDelta1[1]);
        CoordinatesDelta cd1 = new CoordinatesDelta(x1, y1);

        String [] coordDelta2 = super.commandArr[10].split(":");
        Double x2 = Double.parseDouble(coordDelta2[0]);
        Double y2 = Double.parseDouble(coordDelta2[1]);
        CoordinatesDelta cd2 = new CoordinatesDelta(x2, y2);

        A_Command command;
        if(super.commandArr[11].equalsIgnoreCase("distance")){
            double distance = Double.parseDouble(super.commandArr[13]);

            command = new CommandCreateTrackCurve(id1, cw, cd1, cd2, distance);

        }
        else{
            String [] coordDelta3 = super.commandArr[12].split(":");
            Double x3 = Double.parseDouble(coordDelta3[0]);
            Double y3 = Double.parseDouble(coordDelta3[1]);
            CoordinatesDelta cd3 = new CoordinatesDelta(x3, y3);

            command = new CommandCreateTrackCurve(id1, cw, cd1, cd2, cd3);
        }

        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseStraight(){

    }

    public void parseLayout(){

        String id1 = super.commandArr[3];

        List<String> idList = new ArrayList<String>();

        for(int i = 6; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }

        A_Command command = new CommandCreateTrackLayout(id1, idList);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseRoundhouse(){

        String id1 = super.commandArr[3];

        CoordinatesWorld cw;
        if(super.commandArr[5].charAt(0)=='$'){
            cw = parserHelper.getReference(super.commandArr[5].substring(1));
        }
        else {
            String[] latAndLon = super.commandArr[5].split("/");
            cw = calcCoordWorld(latAndLon[0], latAndLon[1]);
        }

        String [] coordDelta1 = super.commandArr[8].split(":");
        Double x1 = Double.parseDouble(coordDelta1[0]);
        Double y1 = Double.parseDouble(coordDelta1[1]);
        CoordinatesDelta cd = new CoordinatesDelta(x1, y1);

        Angle entry = new Angle(Double.parseDouble(super.commandArr[11]));
        Angle start = new Angle(Double.parseDouble(super.commandArr[13]));
        Angle end = new Angle(Double.parseDouble(super.commandArr[15]));

        int spurs = Integer.parseInt(super.commandArr[17]);
        double num1 = Double.parseDouble(super.commandArr[20]);
        double num2 = Double.parseDouble(super.commandArr[23]);

        A_Command command = new CommandCreateTrackRoundhouse(id1, cw, cd, entry, start, end, spurs, num1, num2);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseSwitch(){
        
    }

}
