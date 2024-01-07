package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

//

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class StasisCommand extends ParallelCommandGroup {
    public StasisCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                new LowerHorizontalMoveCommand(depositSubsystem, DepositSubsystem.LowerHorizontalState.C),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED)
        );
    }
}
