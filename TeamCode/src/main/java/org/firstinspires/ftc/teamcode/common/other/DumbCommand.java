/**
 * Purpose: Drive from spike mark to board
 * Dependencies (variables): DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Wrong color set
 */
package org.firstinspires.ftc.teamcode.common.other;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class DumbCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;
    public DumbCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.distance) {
            case REDCLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(24, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(54, -58), Math.toRadians(0))
                        .build());
                break;
            case REDFAR:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-24, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(54, -58), Math.toRadians(0))
                        .build());
                break;
            case BLUECLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(24, 58), Math.toRadians(0))
                        .splineTo(new Vector2d(54, 58), Math.toRadians(0))
                        .build());
                break;
            case BLUEFAR:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-24, 58), Math.toRadians(0))
                        .splineTo(new Vector2d(54, 58), Math.toRadians(0))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



