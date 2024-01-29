package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class GroundCommand extends SequentialCommandGroup {

    public GroundCommand(IntakeSubsystem intakeSubsystem, DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem){
        addCommands(
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitCommand(200),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.LOW),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.DROPPING_GROUND),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE)
        );
    }

}
