package org.firstinspires.ftc.teamcode.common.commandbase;

import static org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance.CLOSE;
import static org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance.FAR;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class BlueApproachCommand extends CommandBase {
    private MecanumDrive drive;
    GlobalVariables.Distance d;
    boolean t = false;

    public BlueApproachCommand(MecanumDrive drive, GlobalVariables.Distance d) {
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
                        drive.actionBuilder(CLOSE.getP())
                                .splineTo(new Vector2d(47, 45.5), Math.toRadians(0))
                                .build());
            case FAR:
                Actions.runBlocking(
                        drive.actionBuilder(FAR.getP())
                                .splineTo(new Vector2d(-36, 24), Math.toRadians(-90))
                                .splineToSplineHeading(new Pose2d(0, 12, 0), Math.toRadians(0))
                                .splineTo(new Vector2d(47, 45.5), Math.toRadians(0))
                                .build());
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}

}



