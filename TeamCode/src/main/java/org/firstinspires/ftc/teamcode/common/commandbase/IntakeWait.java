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
    // Don't need this anymore, probably should get rid of this
    // private boolean f = false;
    private IntakeSubsystem intake;

    public IntakeWait(IntakeSubsystem intake) {
        addCommands(
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new WaitCommand(500),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new WaitCommand(500),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED)
        );
        addRequirements(intake);
    }
}
