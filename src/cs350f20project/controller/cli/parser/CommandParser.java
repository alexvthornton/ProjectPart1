package cs350f20project.controller.cli.parser;

public class CommandParser {

    protected MyParserHelper parserHelper;
    protected String[] commands;

    public CommandParser(MyParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commands = commandText.toLowerCase().split(";");
    }

    public void parse(){


        System.out.println("Alex and Miguel's parser!");

        for(int i = 0; i < commands.length; i++) {

            String commandText = commands[i];
            System.out.println(commandText);

            if (commandText.charAt(0) == '@') {
                AtCommandParser acp = new AtCommandParser(parserHelper, commandText);
                acp.parse();
            } else if (commandText.charAt(0) == 'd') {
                DoCommandParser dcp = new DoCommandParser(parserHelper, commandText);
                dcp.parse();
            } else if (commandText.substring(0, 6).equals("create")) {
                CreateCommandParser ccp = new CreateCommandParser(parserHelper, commandText);
                ccp.parse();
            }
        }
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
