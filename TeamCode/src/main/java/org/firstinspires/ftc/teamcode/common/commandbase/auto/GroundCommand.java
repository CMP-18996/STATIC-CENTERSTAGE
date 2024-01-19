package org.firstinspires.ftc.teamcode.common.commandbase.auto;

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
                new WaitCommand(60), // might need to be tuned
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(60), // might need to be tuned
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(300), // might need to be tuned

//                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
//                new WaitCommand(400),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitCommand(300),
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                new WaitCommand(500),

                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
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
