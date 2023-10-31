package org.firstinspires.ftc.teamcode.common.commandbase;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

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
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(-36.32, 48.93), Math.toRadians(90.00))
                        .build());
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}

}



