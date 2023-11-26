package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class TakeFromDepositCommand extends ParallelCommandGroup {
    public TakeFromDepositCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem) {
        addCommands(
                new SampleLiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
                new UpperHorizontalMoveCommand(depositSubsystem, DepositSubsystem.UpperHorizontalState.D),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT)
        );
    }
}
