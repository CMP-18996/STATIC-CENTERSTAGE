package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.WaitForPress;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class ManualTakeFromIntakeCommand extends SequentialCommandGroup {
    public ManualTakeFromIntakeCommand(LiftSubsystem liftSubsystem, DepositSubsystem depositSubsystem, IntakeSubsystem intakeSubsystem, GamepadEx gamepadEx) {
        addCommands(
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new WaitForPress(gamepadEx),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitForPress(gamepadEx),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new WaitForPress(gamepadEx),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                new WaitForPress(gamepadEx),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                new WaitForPress(gamepadEx),
                new ZeroLiftCommand(liftSubsystem),
                new WaitForPress(gamepadEx),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP),
                new WaitForPress(gamepadEx),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                new WaitForPress(gamepadEx),
                new WaitCommand(700),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP_ADDED),
                new WaitForPress(gamepadEx),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP_ADDED),
                new WaitForPress(gamepadEx),
                new WaitCommand(300),

                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new WaitForPress(gamepadEx),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new WaitForPress(gamepadEx),
                new WaitCommand(450), // might need to be tuned

                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                new WaitForPress(gamepadEx),
                new WaitCommand(400),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitForPress(gamepadEx),
                new WaitCommand(200),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                new WaitForPress(gamepadEx),
                new WaitCommand(600),

                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new WaitForPress(gamepadEx),
                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }
}
