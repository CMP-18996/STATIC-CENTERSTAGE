package org.firstinspires.ftc.teamcode.common.commandbase;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class BlueApproachCommand extends CommandBase {
    public enum Distance {
        CLOSE,
        FAR
    }
    private MecanumDrive drive;
    Distance d;
    boolean t = false;

    public BlueApproachCommand(MecanumDrive drive, Distance d) {
        this.drive = drive;
        this.d = d;
    }

    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (d) {
            case CLOSE:
                Actions.runBlocking(
                        drive.actionBuilder(new Pose2d(-59.18, 12.18, Math.toRadians(0.00)))
                                .splineTo(new Vector2d(-36.32, 48.93), Math.toRadians(90.00))
                                .build());
            case FAR:
                Actions.runBlocking(
                        drive.actionBuilder(new Pose2d(-59.77, -35.08, Math.toRadians(0.00)))
                                .splineTo(new Vector2d(-24.23, -35.54), Math.toRadians(0.00))
                                .splineTo(new Vector2d(-12.23, -0.23), Math.toRadians(90.00))
                                .splineTo(new Vector2d(-36.23, 48.69), Math.toRadians(90.00))
                                .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}

}



