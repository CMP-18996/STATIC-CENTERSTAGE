/**
 * Purpose: Drive from the backdrop to a stack, intakes two pixels,
 * processes them for placement while driving back to the backdrop.
 * Dependencies (variables): COLOR
 * Dependencies (subsystem): RR-drive...
 * Most Likely Errors:
 * - Crashes into other robots
 * - Intake system fails to obtain pixel
 * - General odometry drift
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class ToStackCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;
    public ToStackCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case BLUE:
                Actions.runBlocking(
                        drive.actionBuilder(drive.pose)
                                .setReversed(false)
                                .splineTo(new Vector2d(-24, 35), Math.toRadians(180))
                                .splineTo(new Vector2d(-62, 12), Math.toRadians(180))
                                .setReversed(true)
                                .splineTo(new Vector2d(-24, 35), Math.toRadians(0))
                                .splineTo(new Vector2d(53,  36), Math.toRadians(0))
                                .build());
                break;
            case RED:
                Actions.runBlocking(
                        drive.actionBuilder(drive.pose)
                                .setReversed(false)
                                .splineTo(new Vector2d(-24, -36), Math.toRadians(180))
                                .splineTo(new Vector2d(-62, -12), Math.toRadians(180))
                                .setReversed(true)
                                .splineTo(new Vector2d(-24, -36), Math.toRadians(0))
                                .splineTo(new Vector2d(53, -36), Math.toRadians(0))
                                .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



