package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class StackSwivelCommand extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;
    public StackSwivelCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .turn(Math.toRadians(60))
                .turn(Math.toRadians(-60))
                .build());
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}
