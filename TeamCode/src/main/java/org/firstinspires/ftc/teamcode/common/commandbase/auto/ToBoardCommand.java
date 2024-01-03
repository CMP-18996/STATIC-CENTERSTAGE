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
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(0))
                .build());
        if (GlobalVariables.distance.equals(GlobalVariables.Distance.BLUEFAR)) {
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .splineTo(new Vector2d(-24, 36), Math.toRadians(0))
                    .splineTo(new Vector2d(20, 36), Math.toRadians(0))
                    .build());
        } else if (GlobalVariables.distance.equals(GlobalVariables.Distance.REDFAR)) {
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .splineTo(new Vector2d(-24, -36), Math.toRadians(0))
                    .splineTo(new Vector2d(20, -36), Math.toRadians(0))
                    .build());
        } else {
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .splineTo(new Vector2d(20, -36), Math.toRadians(0))
                    .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



