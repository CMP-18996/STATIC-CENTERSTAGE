/**
 * Purpose: Drive from the backdrop to a stack, then drives back to the backdrop.
 * Dependencies (variables): COLOR
 * Dependencies (subsystem): RR-drive
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

public class StackCycleCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;
    public StackCycleCommand(MecanumDrive drive) {
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
                                .splineTo(new Vector2d(-62, 36), Math.toRadians(180))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .splineTo(new Vector2d(46,  36), Math.toRadians(0))
                                .build());
                break;
            case RED:
                Actions.runBlocking(
                        drive.actionBuilder(drive.pose)
                                .setReversed(false)
                                .splineTo(new Vector2d(-62, -36), Math.toRadians(180))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .splineTo(new Vector2d(46, -36), Math.toRadians(0))
                                .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



