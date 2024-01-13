package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PController;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

import java.util.Date;

public class ZeroLiftCommand extends CommandBase {
    Date originalTime = new Date();
    LiftSubsystem liftSubsystem;
    public ZeroLiftCommand(LiftSubsystem liftSubsystem) {
        this.liftSubsystem = liftSubsystem;
        liftSubsystem.controlLift = false;
    }

    @Override
    public void execute() {
        liftSubsystem.robot.liftOne.setPower(-.3);
        liftSubsystem.robot.liftTwo.setPower(-.3);
    }

    @Override
    public boolean isFinished() {
        if ((new Date()).getTime() - originalTime.getTime() > .3 * 1000) {
            liftSubsystem.controlLift = true;
            liftSubsystem.robot.liftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftSubsystem.robot.liftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftSubsystem.robot.liftOne.setPower(0);
            liftSubsystem.robot.liftTwo.setPower(0);
            return true;
        }
        return false;
    }
}
