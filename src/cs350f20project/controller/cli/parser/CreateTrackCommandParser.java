package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.PointLocator;
import cs350f20project.controller.command.creational.CommandCreateTrackCurve;
import cs350f20project.controller.command.creational.CommandCreateTrackLayout;
import cs350f20project.controller.command.creational.CommandCreateTrackRoundhouse;
import cs350f20project.controller.command.creational.CommandCreateTrackSwitchTurnout;
import cs350f20project.controller.command.creational.CommandCreateTrackSwitchWye;
import cs350f20project.controller.command.creational.CommandCreateTrackStraight;
import cs350f20project.datatype.Angle;
import cs350f20project.datatype.CoordinatesDelta;
import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.ShapeArc;

import java.util.ArrayList;
import java.util.List;

public class CreateTrackCommandParser extends CreateCommandParser{

    protected CreateTrackCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCurve(){

        //create track curve curveID1 reference 34*25'21.4"/45*11'34.2" delta start 10.4:1 end 2:39.1 origin 20:10
        boolean matches = super.commandText.toLowerCase().matches("create track curve \\w+ reference ((\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"/(\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"|\\$\\w+) delta start ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) (distance origin ((\\+|-)?\\d+(\\.\\d+)?)|origin ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?))");

        if(!matches) {
            throw new RuntimeException("Invalid create track curve command");
        }

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
        //create track straight straightTrackID19 reference 34*25'21.4"/45*11'34.2" delta start 10.4:1 end 2:39.1
        boolean matches = super.commandText.toLowerCase().matches("create track straight \\w+ reference ((\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"/(\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"|\\$\\w+) delta start ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?)");

        if(!matches) {
            throw new RuntimeException("Invalid create track curve command");
        }

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

        PointLocator pl = new PointLocator(cw, cd1, cd2);

        A_Command command = new CommandCreateTrackStraight(id1, pl);

        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseLayout(){
        //create track layout trackLayoutID with tracks trackID1 trackID2
        boolean matches = super.commandText.toLowerCase().matches("create track layout \\w+ with tracks \\w+(( \\w+)+)?");

        if(!matches) {
            throw new RuntimeException("Invalid create track layout command");
        }

        String id1 = super.commandArr[3];

        List<String> idList = new ArrayList<String>();

        for(int i = 6; i < super.commandArr.length; i++){
            idList.add(super.commandArr[i]);
        }

        A_Command command = new CommandCreateTrackLayout(id1, idList);
        this.parserHelper.getActionProcessor().schedule(command);

    }

    public void parseRoundhouse(){

        boolean matches = super.commandText.toLowerCase().matches("create track roundhouse \\w+ reference ((\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"/(\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"|\\$\\w+) delta origin ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) angle entry (\\+|-)?\\d+(\\.\\d+)? start (\\+|-)?\\d+(\\.\\d+)? end (\\+|-)?\\d+(\\.\\d+)? with (\\+|-)?\\d+ spurs length (\\+|-)?\\d+(\\.\\d+)? turntable length (\\+|-)?\\d+(\\.\\d+)?");

        if(!matches) {
            throw new RuntimeException("Invalid create track round House command");
        }

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
    	if(super.commandArr[3].equalsIgnoreCase("TURNOUT")) {
    		parseSwitchTurnout();
    	}
    	else if(super.commandArr[3].equalsIgnoreCase("WYE")) {
    		parseSwitchWye();
    	}
    	else {
    		throw new RuntimeException("Invalid Command!");
    	}
    }
    
    private void parseSwitchTurnout() {
    	boolean matches = super.commandText.toLowerCase().matches("create track switch turnout \\w+ reference ((\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"/(\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"|\\$\\w+) "
    			+ "straight delta start ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) curve delta start ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) distance origin (\\+|-)?\\d+(\\.\\d+)?");
    	
    	if(!matches) {
    		throw new RuntimeException("Invalid create track switch turnout command!");
    	}
    	
    	String switchTrackID = super.commandArr[4];
    	
    	CoordinatesWorld coordsWorldRef;
    	if(super.commandArr[6].charAt(0) == '$') {
    		coordsWorldRef = parserHelper.getReference(super.commandArr[6].substring(1));
    	}
    	else {
    		String[] latAndLon = super.commandArr[6].split("/");
    		coordsWorldRef = calcCoordWorld(latAndLon[0], latAndLon[1]);
    	}
    	
    	String[] coordDelta1 = super.commandArr[10].split(":");
    	Double x1 = Double.parseDouble(coordDelta1[0]);
    	Double y1 = Double.parseDouble(coordDelta1[1]);
    	CoordinatesDelta delta1Start = new CoordinatesDelta(x1, y1);
    	
    	String[] coordDelta2 = super.commandArr[12].split(":");
    	Double x2 = Double.parseDouble(coordDelta2[0]);
    	Double y2 = Double.parseDouble(coordDelta2[1]);
    	CoordinatesDelta delta1End = new CoordinatesDelta(x2, y2);
    	
    	String[] coordDelta3 = super.commandArr[16].split(":");
    	Double x3 = Double.parseDouble(coordDelta3[0]);
    	Double y3 = Double.parseDouble(coordDelta3[1]);
    	CoordinatesDelta delta2Start = new CoordinatesDelta(x3, y3);
    	
    	String[] coordDelta4 = super.commandArr[18].split(":");
    	Double x4 = Double.parseDouble(coordDelta4[0]);
    	Double y4 = Double.parseDouble(coordDelta4[1]);
    	CoordinatesDelta delta2End = new CoordinatesDelta(x4, y4);
    	
    	double distanceOrigin = Double.parseDouble(super.commandArr[21]);
    	CoordinatesDelta delta2Origin = ShapeArc.calculateDeltaOrigin(coordsWorldRef, delta2Start, delta2End, distanceOrigin); //origin of the curved segment
    	
    	A_Command command = new CommandCreateTrackSwitchTurnout(switchTrackID, coordsWorldRef, delta1Start, delta1End, delta2Start, delta2End, delta2Origin);
    	parserHelper.getActionProcessor().schedule(command);
    }//end parseSwitchTurnout()
    
    private void parseSwitchWye() {
    	boolean matches = super.commandText.toLowerCase().matches("create track switch wye \\w+ reference ((\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"/(\\+|-)?\\d+\\*(\\+|-)?\\d+'((\\+|-)?\\d+(\\.\\d+)?)\"|\\$\\w+) delta start "
    			+ "((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) distance origin (\\+|-)?\\d+(\\.\\d+)? delta start ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) end ((\\+|-)?\\d+(\\.\\d+)?):((\\+|-)?\\d+(\\.\\d+)?) "
    			+ "distance origin (\\+|-)?\\d+(\\.\\d+)?");
    	if(!matches) {
    		throw new RuntimeException("Invalid create track switch wye command!");
    	}
    	
    	String switchTrackID = super.commandArr[4];
    	
    	CoordinatesWorld coordsWorldRef;
    	if(super.commandArr[6].charAt(0) == '$') {
    		coordsWorldRef = parserHelper.getReference(super.commandArr[6].substring(1));
    	}
    	else {
    		String[] latAndLon = super.commandArr[6].split("/");
    		coordsWorldRef = calcCoordWorld(latAndLon[0], latAndLon[1]);
    	}
    	
    	String[] coordDelta1 = super.commandArr[9].split(":");
    	Double x1 = Double.parseDouble(coordDelta1[0]);
    	Double y1 = Double.parseDouble(coordDelta1[1]);
    	CoordinatesDelta delta1Start = new CoordinatesDelta(x1, y1);
    	
    	String[] coordDelta2 = super.commandArr[11].split(":");
    	Double x2 = Double.parseDouble(coordDelta1[0]);
    	Double y2 = Double.parseDouble(coordDelta1[1]);
    	CoordinatesDelta delta1End = new CoordinatesDelta(x2, y2);
    	
    	double distanceOrigin1 = Double.parseDouble(super.commandArr[14]);
    	CoordinatesDelta delta1Origin = ShapeArc.calculateDeltaOrigin(coordsWorldRef, delta1Start, delta1End, distanceOrigin1);
    	
    	String[] coordDelta3 = super.commandArr[17].split(":");
    	Double x3 = Double.parseDouble(coordDelta1[0]);
    	Double y3 = Double.parseDouble(coordDelta1[1]);
    	CoordinatesDelta delta2Start = new CoordinatesDelta(x3, y3);
    	
    	String[] coordDelta4 = super.commandArr[19].split(":");
    	Double x4 = Double.parseDouble(coordDelta1[0]);
    	Double y4 = Double.parseDouble(coordDelta1[1]);
    	CoordinatesDelta delta2End = new CoordinatesDelta(x4, y4);
    	
    	double distanceOrigin2 = Double.parseDouble(super.commandArr[22]);
    	CoordinatesDelta delta2Origin = ShapeArc.calculateDeltaOrigin(coordsWorldRef, delta2Start, delta2End, distanceOrigin2);
    	
    	A_Command command = new CommandCreateTrackSwitchWye(switchTrackID, coordsWorldRef, delta1Start, delta1End, delta1Origin, delta2Start, delta2End, delta2Origin);
    	parserHelper.getActionProcessor().schedule(command);
    }//end parseSwitchWye()

}
