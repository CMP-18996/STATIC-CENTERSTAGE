package org.firstinspires.ftc.teamcode.common.commandbase;

//

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class StasisCommand extends ParallelCommandGroup {
    public StasisCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new SampleLiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED)
        );
    }
}
