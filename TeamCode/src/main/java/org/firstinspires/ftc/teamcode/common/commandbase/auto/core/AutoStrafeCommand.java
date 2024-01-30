package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class AutoStrafeCommand extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;
    public AutoStrafeCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {
        drive.setPoseEstimate(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), 0));
    }
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case BLUE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .splineToConstantHeading(new Vector2d(27, 7), Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(-61, 7), Math.toRadians(180))
                        .setReversed(false)
                        .build());
                break;
            case RED:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        //.splineTo(new Vector2d(27, -7), Math.toRadians(180))
                        .strafeLeft(6)
                        .setReversed(false)
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



