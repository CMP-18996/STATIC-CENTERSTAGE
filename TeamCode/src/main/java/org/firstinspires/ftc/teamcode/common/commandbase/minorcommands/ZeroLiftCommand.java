package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.controller.PController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ZeroLiftCommand extends CommandBase {
    LiftSubsystem liftSubsystem;
    public ZeroLiftCommand(LiftSubsystem liftSubsystem) {
        this.liftSubsystem = liftSubsystem;
    }

    @Override
    public void initialize() {
        liftSubsystem.controlLift = false;
        liftSubsystem.robot.liftOne.setPower(-.3);
        liftSubsystem.robot.liftTwo.setPower(-.3);
    }

    @Override
    public boolean isFinished() {
        if (!liftSubsystem.robot.liftLimitSwitch.getState()) {
            liftSubsystem.robot.liftOne.setPower(0);
            liftSubsystem.robot.liftTwo.setPower(0);
        }
        return false;
    }
}
