package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;

public class CreateTrackCommandParser extends CreateCommandParser{

    protected CreateTrackCommandParser(MyParserHelper parserHelper, String commandText) {
        super(parserHelper, commandText);
    }

    public void parseCurve(){
        //CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 ( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )

    }

    public void parseStraight(){

    }

    public void parseLayout(){

    }

    public void parseRoundhouse(){

    }

    public void parseSwitch(){

    }

}
