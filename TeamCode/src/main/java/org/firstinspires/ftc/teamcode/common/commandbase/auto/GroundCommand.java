package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class GroundCommand extends SequentialCommandGroup {

    public GroundCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem){
        addCommands(
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.LOW),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.DROPPING_GROUND),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE)
                /*new WaitCommand(1300),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(300),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)*/
        );
    }

}
