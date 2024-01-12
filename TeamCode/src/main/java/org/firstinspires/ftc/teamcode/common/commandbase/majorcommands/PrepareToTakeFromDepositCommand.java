package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositExpansionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.TwoSlotDetectedCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class PrepareToTakeFromDepositCommand extends SequentialCommandGroup {
    public PrepareToTakeFromDepositCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new LowerHorizontalMoveCommand(depositSubsystem, DepositSubsystem.LowerHorizontalState.C),
                // add intake height
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS), // add arm state
                new WaitCommand(300),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING)

        );
    }
}
