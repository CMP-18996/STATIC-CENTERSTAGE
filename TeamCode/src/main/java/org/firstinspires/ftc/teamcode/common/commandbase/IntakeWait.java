package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class IntakeWait extends SequentialCommandGroup {

    private boolean f = false;
    private IntakeSubsystem intake;

    public IntakeWait(IntakeSubsystem intake) {
        addCommands(
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new WaitCommand(400),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                new WaitCommand(200),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING)
        );
        addRequirements(intake);
    }
}
