package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class TakeFromDepositCommand extends SequentialCommandGroup {
    public TakeFromDepositCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new ParallelCommandGroup(
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
                        new UpperHorizontalMoveCommand(depositSubsystem, DepositSubsystem.UpperHorizontalState.D),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                        new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING),
                        new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                        new WaitCommand(1300) // might need to be tuned
                ),

                new TwoSlotDetectedCommand(intakeSubsystem),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                new WaitCommand(600),

                new ParallelCommandGroup(
                        new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                        new WaitCommand(400) // might need to be tuned
                ),

                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }
}
