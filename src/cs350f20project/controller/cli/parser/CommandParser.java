package cs350f20project.controller.cli.parser;

import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.Latitude;
import cs350f20project.datatype.Longitude;

public class CommandParser {

    protected MyParserHelper parserHelper;
    protected String[] commands;

    public CommandParser(MyParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commands = commandText.replaceAll(" +|\\t+", " ").split(";");
    }

    public void parse(){


        System.out.println("Alex and Miguel's parser!");

        for(int i = 0; i < commands.length; i++) {

            String commandText = commands[i].trim();


            if (commandText.charAt(0) == '@') {
                AtCommandParser acp = new AtCommandParser(parserHelper, commandText);
                acp.parse();
            } else if (commandText.charAt(0) == 'd' || commandText.charAt(0) == 'D' ) {
                DoCommandParser dcp = new DoCommandParser(parserHelper, commandText);
                dcp.parse();
            } else if (commandText.substring(0, 6).equalsIgnoreCase("create")) {
                CreateCommandParser ccp = new CreateCommandParser(parserHelper, commandText);
                ccp.parse();
            } else if(commandText.substring(0,3).equalsIgnoreCase("use")){
                use(commandText);
            }
        }
    }

    protected CoordinatesWorld calcCoordWorld(String lat, String lon) {

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

    private void use(String command){
        // use ref1 as reference 24*19'25.4"/15*28'21.8"

        boolean matches = command.toLowerCase().matches("use \\w+ as reference \\d+\\*\\d+'(\\d+(\\.\\d+)?)\"/\\d+\\*\\d+'(\\d+(\\.\\d+)?)\"");

        if(!matches) {
            throw new RuntimeException("Invalid use id as reference command");
        }

        String [] arr = command.split(" ");
        String id = arr[1];

        String [] latAndLon = arr[4].split("/");
        CoordinatesWorld cw = calcCoordWorld(latAndLon[0], latAndLon[1]);

        parserHelper.addReference(id, cw);
    }

    /*
    other cases that must be handled

    open
    commit
    couple
    locate
    use
    Rule#2throughRule#65

     */

}
