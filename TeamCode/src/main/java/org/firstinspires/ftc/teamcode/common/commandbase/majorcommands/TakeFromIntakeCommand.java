package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class TakeFromIntakeCommand extends SequentialCommandGroup {
    public TakeFromIntakeCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                new WaitCommand(1100),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP),
                new WaitCommand(1000),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP_ADDED),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP_ADDED),
                new WaitCommand(300),

                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(450), // might need to be tuned

                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new WaitCommand(200),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitCommand(600),
//                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                new WaitCommand(600),

                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }
}
