/*

----------------------
CURRENTLY DISCONTINUED
----------------------

 */


package org.firstinspires.ftc.teamcode.common.other;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.StackCycleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToSpikeMarkCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class InstructionInterpreter {
    enum ReadState {
        VARIABLES_NOT_FOUND,
        VARIABLES,
        COMMANDS,
    }
    final private String path = "/sdcard/FIRST/instructions.txt";
    //first bit is team blue/red, second bit is distance far/close (as 0/1)
    private int colorDistanceByte = 0B00;

    public Robot robot;
    public MecanumDrive drive;
    public Telemetry telemetry;
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
                            colorDistanceByte += 0B10;
                        } /*else if (input[0].equals("team") && input[1].equals("blue")) {
                            colorDistanceByte += 0B00;
                        } */else if (input[0].equals("distance") && input[1].equals("close")) {
                            colorDistanceByte += 0B01;
                        } /*else if (input[0].equals("distance") && input[1].equals("far")) {
                            colorDistanceByte += 0B00;
                        }*/
                        break;
                    case COMMANDS:
                        switch (data) {
                            case "wait":
                                CommandScheduler.getInstance().schedule(new WaitCommand(500));
                            case "deposit prop pixel":
                                CommandScheduler.getInstance().schedule(new ToSpikeMarkCommand(drive));
                            case "align":
                                CommandScheduler.getInstance().schedule(new ToTagCommand(robot.camera, drive));
                            default:
                                switch (colorDistanceByte) {
                                case 0B00: //blue far
                                    switch (data) {
                                        case "approach":
                                            CommandScheduler.getInstance().schedule(new ToBoardCommand(drive));
                                        case "cycle stack":
                                            CommandScheduler.getInstance().schedule(new StackCycleCommand(drive));
                                    }
                                case 0B01: //blue close
                                    switch (data) {
                                        case "approach":
                                            CommandScheduler.getInstance().schedule(new ToBoardCommand(drive));
                                        case "cycle stack":
                                            CommandScheduler.getInstance().schedule(new StackCycleCommand(drive));
                                    }
                                case 0B10: //red far

                                case 0B11: //red close
                            }
                        }
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