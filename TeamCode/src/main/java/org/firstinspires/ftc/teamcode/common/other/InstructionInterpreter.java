package org.firstinspires.ftc.teamcode.common.other;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;



public class InstructionInterpreter {
    enum ReadState {
        VARIABLES_NOT_FOUND,
        VARIABLES,
        COMMANDS,
    }

    enum Team {
        RED,
        BLUE
    }

    enum Distance {
        CLOSE,
        FAR
    }

    final private String path = "java/org/firstinspires/ftc/teamcode/opmode/autonomous/instructions.cool";
    private Team team;
    private Distance distance;
    Map<String, CommandBase> redCloseMap = new HashMap<String, CommandBase>() {{
        // Insert Red Close Commands Here
    }};
    Map<String, CommandBase> redFarMap = new HashMap<String, CommandBase>() {{
        // Insert Red Far Commands Here
    }};
    Map<String, CommandBase> blueCloseMap = new HashMap<String, CommandBase>() {{
        // Insert Blue Close Commands Here
    }};
    Map<String, CommandBase> blueFarMap = new HashMap<String, CommandBase>() {{
        // Insert Blue Far Commands Here
    }};



    InstructionInterpreter() throws FileNotFoundException {
        try {
            ReadState readState = ReadState.VARIABLES_NOT_FOUND;
            File myObj = new File(path);
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String data = reader.nextLine().trim().toLowerCase();
                switch (readState) {
                    case VARIABLES_NOT_FOUND:
                        if (data.equals("variables")) {
                            readState = ReadState.VARIABLES;
                        }
                        break;
                    case VARIABLES:
                        String[] input = data.split(" ");
                        if (input.length != 2) {
                            if (input.length == 1) {
                                if (input[0].equals("commands")) {
                                    readState = ReadState.COMMANDS;
                                }
                            }
                        }
                        if (input[0].equals("team") && input[1].equals("red")) {
                            team = Team.RED;
                        } else if (input[0].equals("team") && input[1].equals("blue")) {
                            team = Team.BLUE;
                        } else if (input[0].equals("distance") && input[1].equals("close")) {
                            distance = Distance.CLOSE;
                        } else if (input[0].equals("distance") && input[1].equals("far")) {
                            distance = Distance.FAR;
                        }
                        break;
                    case COMMANDS:
                        Map<String, CommandBase> usedMap =
                                (team == Team.RED) ?
                                        ((distance == Distance.CLOSE) ?
                                                redCloseMap :
                                                redFarMap
                                                )
                                        :
                                        ((distance == Distance.CLOSE) ?
                                                blueCloseMap :
                                                blueFarMap
                                        ); // change this pls
                        CommandScheduler.getInstance().schedule(usedMap.get(data));
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
}


class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}