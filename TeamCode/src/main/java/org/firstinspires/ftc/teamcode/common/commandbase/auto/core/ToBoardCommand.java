/**
 * Purpose: Drive from spike mark to board
 * Dependencies (variables): DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Wrong color set
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

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
                        .lineToLinearHeading(new Pose2d(-35,-58, Math.toRadians(0)))
                        .splineTo(new Vector2d(15, -58), Math.toRadians(0))
                        .splineTo(new Vector2d(42, -36), Math.toRadians(0))
                        .build());
                break;
            case REDCLOSE:
                if (GlobalVariables.position.equals(GlobalVariables.Position.RIGHT)) {
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .lineToLinearHeading(new Pose2d(36, -57, Math.toRadians(90)))
                            .build());
                }
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(42, -36, Math.toRadians(0)))
                        .build());
                break;
            case BLUEFAR:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(-35,55, Math.toRadians(0)))
                        .splineTo(new Vector2d(15, 55), Math.toRadians(0))
                        .splineTo(new Vector2d(42, 36), Math.toRadians(0))
                        .build());
                break;
            case BLUECLOSE:
                if (GlobalVariables.position.equals(GlobalVariables.Position.LEFT)) {
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .lineToLinearHeading(new Pose2d(36, 57, Math.toRadians(-90)))
                            .build());
                }
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(42, 36, Math.toRadians(0)))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



