/**
 * Purpose: Drive from spike mark to board
 * Dependencies (variables): DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Wrong color set
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import static org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance.*;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

import java.util.Objects;

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
                        .lineToYLinearHeading(-58, Math.toRadians(0))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, -36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case BLUEFAR:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .lineToYLinearHeading(58, Math.toRadians(0))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, 58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, 36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case REDCLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .strafeToLinearHeading(new Vector2d(30, -54), Math.toRadians(60))
                        .setReversed(false)
                        .splineTo(new Vector2d(42, -36), Math.toRadians(0))
                        .build());
                break;
            case BLUECLOSE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .strafeToLinearHeading(new Vector2d(30, 54), Math.toRadians(-60))
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



