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

public class NoTagBoardAlign extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;

    public NoTagBoardAlign(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.position) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .lineToLinearHeading(new Pose2d(46.5, -28, Math.toRadians(0)))
                        .build());
                break;
            case MIDDLE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .lineToLinearHeading(new Pose2d(46.5, -34, Math.toRadians(0)))
                        .build());
                break;
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .lineToLinearHeading(new Pose2d(12, -52, Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(46.5, -40, Math.toRadians(0)))
                        .build());
                break;
            default:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .lineToLinearHeading(new Pose2d(12, -52, Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(46.5, -40, Math.toRadians(0)))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



