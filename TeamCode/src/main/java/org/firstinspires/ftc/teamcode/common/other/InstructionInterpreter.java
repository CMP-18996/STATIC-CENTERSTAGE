package org.firstinspires.ftc.teamcode.common.other;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoDriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.BlueApproachCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.PropPixelCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

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

    public Robot robot;
    public MecanumDrive drive;
    public Telemetry telemetry;
    Map<String, CommandBase> redCloseMap = new HashMap<String, CommandBase>() {{
        // Insert Red Close Commands Here
    }};
    Map<String, CommandBase> redFarMap = new HashMap<String, CommandBase>() {{
        // Insert Red Far Commands Here
    }};
    Map<String, CommandBase> blueCloseMap = new HashMap<String, CommandBase>() {{
        put("Approach", new BlueApproachCommand(drive, GlobalVariables.Distance.CLOSE));
        put("Wait", new WaitCommand(500));
        put("Align", new AutoDriveToTagCommand(robot.camera, drive, 2));
        put("Cycle Stack", new AutoStackCommand(drive, GlobalVariables.Color.BLUE));
        put("Deposit Prop Pixel", new PropPixelCommand(telemetry));
    }};
    Map<String, CommandBase> blueFarMap = new HashMap<String, CommandBase>() {{
        put("Approach", new BlueApproachCommand(drive, GlobalVariables.Distance.FAR));
        put("Wait", new WaitCommand(500));
        put("Align", new AutoDriveToTagCommand(robot.camera, drive, 2));
        put("Cycle Stack", new AutoStackCommand(drive, GlobalVariables.Color.BLUE));
        put("Deposit Prop Pixel", new PropPixelCommand(telemetry));
    }};
    public InstructionInterpreter(Robot robot, MecanumDrive drive, Telemetry telemetry) throws FileNotFoundException {
        this.robot = robot;
        this.drive = drive;
        this.telemetry = telemetry;
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