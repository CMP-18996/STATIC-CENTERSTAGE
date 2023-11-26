package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class TakeFromDepositCommand extends SequentialCommandGroup {
    public TakeFromDepositCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new ParallelCommandGroup(
                        new SampleLiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
                        new UpperHorizontalMoveCommand(depositSubsystem, DepositSubsystem.UpperHorizontalState.D),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT)
                ),

                new TwoSlotDetectedCommand(intakeSubsystem),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),

                new ParallelCommandGroup(
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT)
                ),

                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }
}
