/**
 * Purpose: Drive from spike mark to board
 * Dependencies (variables): DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Wrong color set
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class ToBoardCommand extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;

    public ToBoardCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.distance) {
            case REDFAR:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .splineTo(new Vector2d(-35,-58), Math.toRadians(180))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, -36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case BLUEFAR:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .splineTo(new Vector2d(-35,58), Math.toRadians(180))
                        .setReversed(false)
                        .splineTo(new Vector2d(10, 58), Math.toRadians(0))
                        .splineTo(new Vector2d(36, 36), Math.toRadians(0))
                        .waitSeconds(2)
                        .build());
                break;
            case REDCLOSE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(36, -57, Math.toRadians(90)))
                        .setReversed(false)
                        .splineTo(new Vector2d(42, -36), Math.toRadians(0))
                        .build());
                break;
            case BLUECLOSE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(20, 58, Math.toRadians(-90)))
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



