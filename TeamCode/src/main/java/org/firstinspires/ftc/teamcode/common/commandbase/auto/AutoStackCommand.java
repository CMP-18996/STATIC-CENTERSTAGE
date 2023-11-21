package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class AutoStackCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;
    public AutoStackCommand(MecanumDrive drive) {
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
                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(12, 12, Math.toRadians(0)), Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(-62, 12, Math.toRadians(0)), Math.toRadians(180))
                                .setReversed(false)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                                .build());
            case RED:
                Actions.runBlocking(
                        drive.actionBuilder(drive.pose)
                                .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



