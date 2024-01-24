/**
 * Purpose: Drive from spike mark to board
 * Dependencies (variables): DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Wrong color set
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;

public class ToBoardCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;

    public ToBoardCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.distance) {
            case REDFAR:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-35,-58), Math.toRadians(180))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, -36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case BLUEFAR:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-35,58), Math.toRadians(180))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, 58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, 36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case REDCLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(36, -57), Math.toRadians(-90))
                        .setReversed(false)
                        .splineTo(new Vector2d(42, -36), Math.toRadians(0))
                        .build());
                break;
            case BLUECLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(20, 58), Math.toRadians(90))
                        .setReversed(false)
                        .splineTo(new Vector2d(42, 36), Math.toRadians(0))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



