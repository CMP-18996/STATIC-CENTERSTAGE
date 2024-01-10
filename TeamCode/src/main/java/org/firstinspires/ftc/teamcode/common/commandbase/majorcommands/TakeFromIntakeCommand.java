package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositExpansionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.TwoSlotDetectedCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class TakeFromIntakeCommand extends SequentialCommandGroup {
    public TakeFromIntakeCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new ParallelCommandGroup(
                        // add intake height
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                        new SequentialCommandGroup(
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS), // add arm state
                                new WaitCommand(300),
                                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING)
                        ),
                        new LowerHorizontalMoveCommand(depositSubsystem, DepositSubsystem.LowerHorizontalState.C),
                        new WaitCommand(1300) // might need to be tuned
                ),
                new ParallelRaceGroup(
                        new WaitCommand(5000),
                        new TwoSlotDetectedCommand(intakeSubsystem)
                ),
                new ParallelCommandGroup(
                        new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.REPELLING),
                        new WaitCommand(600) // might need to be tuned
                ),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                new WaitCommand(700),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                new WaitCommand(1000),

                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(400), // might need to be tuned

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitCommand(500),

                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new WaitCommand(500),

                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }
}
