package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import static org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance.*;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class BlueApproachCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;

    public BlueApproachCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.distance) {
            case CLOSE:
                Actions.runBlocking(
                        drive.actionBuilder(CLOSE.getP())
                                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                                .build());
            case FAR:
                Actions.runBlocking(
                        drive.actionBuilder(FAR.getP())
                                .splineTo(new Vector2d(-36, 24), Math.toRadians(-90))
                                .splineToSplineHeading(new Pose2d(0, 12, 0), Math.toRadians(0))
                                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                                .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



