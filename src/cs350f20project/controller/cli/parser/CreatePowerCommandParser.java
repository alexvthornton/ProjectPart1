package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreatePowerCatenary;
import cs350f20project.controller.command.creational.CommandCreatePowerPole;
import cs350f20project.controller.command.creational.CommandCreatePowerStation;
import cs350f20project.controller.command.creational.CommandCreatePowerSubstation;
import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.CoordinatesDelta;
import cs350f20project.datatype.Latitude;
import cs350f20project.datatype.Longitude;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CreatePowerCommandParser extends CreateCommandParser{

    protected CreatePowerCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCatenary(){
        boolean matches = super.commandText.toLowerCase().matches("create power catenary \\w+ with poles \\w+(( \\w+)+)?");

        if(!matches) {
            throw new RuntimeException("Invalid parse catenary command");
        }

        String id1 = super.commandArr[3];
        List<String> idList = new ArrayList<String>();

        for(int i = 6; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }

        A_Command command = new CommandCreatePowerCatenary(id1, idList);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parsePole(){
        boolean matches = super.commandText.toLowerCase().matches("create power pole \\w+ on track \\w+ distance (\\d+(\\.\\d+)?) from start|finsih");

        if(!matches) {
            System.out.println("Invalid parse pole command");
        }

        String id1 = super.commandArr[3];
        String id2 = super.commandArr[6];
        double distance = Double.parseDouble(super.commandArr[8]);
        boolean isFromAElseB = super.commandArr[10].equalsIgnoreCase("start");

        TrackLocator tl = new TrackLocator(id2, distance, isFromAElseB);

        A_Command command = new CommandCreatePowerPole(id1, tl);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseStation(){
        // create power station firstID reference 34*25'21.4"/45*11'34.2" delta 10.6:9 with substations ID1 ID2 ID3 ID4

        String id1 = super.commandArr[3];

        String [] latAndLon = super.commandArr[5].split("/");
        CoordinatesWorld cw = calcCoordWorld(latAndLon[0], latAndLon[1]);

        String [] coordDelta = super.commandArr[7].split(":");
        Double x = Double.parseDouble(coordDelta[0]);
        Double y = Double.parseDouble(coordDelta[1]);
        CoordinatesDelta cd = new CoordinatesDelta(x, y);

        List<String> idList = new ArrayList<String>();

        for(int i = 10; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }

        System.out.println("id1: " + id1);
        System.out.println("latitude: " + latAndLon[0]);
        System.out.println("longitude: " + latAndLon[1]);
        System.out.println("x: " + x);
        System.out.println("y: " + y);

        System.out.println("idList: ");
        for(int i = 0; i < idList.size(); i++){
            System.out.println(idList.get(i));
        }

        A_Command command = new CommandCreatePowerStation(id1, cw, cd, idList);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseSubstation(){
        // create power substation firstID reference 34*25'21.4"/45*11'34.2" delta 10.6:9 with catenaries ID1 ID2 ID3 ID4

        String id1 = super.commandArr[3];

        String [] latAndLon = super.commandArr[5].split("/");
        CoordinatesWorld cw = calcCoordWorld(latAndLon[0], latAndLon[1]);

        String [] coordDelta = super.commandArr[7].split(":");
        Double x = Double.parseDouble(coordDelta[0]);
        Double y = Double.parseDouble(coordDelta[1]);
        CoordinatesDelta cd = new CoordinatesDelta(x, y);

        List<String> idList = new ArrayList<String>();

        for(int i = 10; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }

        System.out.println("id1: " + id1);
        System.out.println("latitude: " + latAndLon[0]);
        System.out.println("longitude: " + latAndLon[1]);
        System.out.println("x: " + x);
        System.out.println("y: " + y);

        System.out.println("idList: ");
        for(int i = 0; i < idList.size(); i++){
            System.out.println(idList.get(i));
        }

        A_Command command = new CommandCreatePowerSubstation(id1, cw, cd, idList);
        this.parserHelper.getActionProcessor().schedule(command);
    }

    private CoordinatesWorld calcCoordWorld(String lat, String lon) {

        int latDeg = Integer.parseInt(lat.split("\\*")[0]);
        int latMin = Integer.parseInt(lat.split("\'")[0].split("\\*")[1]);
        double latSec =  Double.parseDouble(lat.split("\"")[0].split("\'")[1]);

        Latitude latitude = new Latitude(latDeg, latMin, latSec);

        int lonDeg = Integer.parseInt(lon.split("\\*")[0]);
        int lonMin = Integer.parseInt(lon.split("\'")[0].split("\\*")[1]);
        double lonSec =  Double.parseDouble(lon.split("\"")[0].split("\'")[1]);

        Longitude longitude = new Longitude(lonDeg, lonMin, lonSec);

        return new CoordinatesWorld(latitude, longitude);
    }

}
