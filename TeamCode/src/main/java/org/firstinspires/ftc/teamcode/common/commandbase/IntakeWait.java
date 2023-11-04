package org.firstinspires.ftc.teamcode.common.commandbase;

import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;
import com.acmerobotics.roadrunner.*;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.Robot;

public class IntakeWait extends CommandBase {

    private MecanumDrive drive;
//    Idk if I even need this
    private int motorPower;
    private boolean f;

    public IntakeWait(MecanumDrive drive, int motorPower) {
        this.drive = drive;
        this.motorPower = motorPower;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
//        Actions.runBlocking(drive.);
    }

    @Override
    public boolean isFinished() {
        return f;
    }
}
