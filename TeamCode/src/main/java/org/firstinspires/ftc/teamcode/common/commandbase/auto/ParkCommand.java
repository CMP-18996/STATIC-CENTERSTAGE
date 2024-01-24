package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;

public class ParkCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;
    public ParkCommand(MecanumDrive drive) {
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
                                .setReversed(false)
                                .splineToConstantHeading(new Vector2d(40, 60), Math.toRadians(0))
                                .splineTo(new Vector2d(54, 60), 0)
                                .build());
                break;
            case RED:
                Actions.runBlocking(
                        drive.actionBuilder(drive.pose)
                                .splineToConstantHeading(new Vector2d(40, -60), Math.toRadians(0))
                                .splineTo(new Vector2d(54, -60), 0)
                                .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}
