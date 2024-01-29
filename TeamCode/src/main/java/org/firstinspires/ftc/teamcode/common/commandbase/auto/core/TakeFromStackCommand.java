package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class TakeFromStackCommand extends SequentialCommandGroup {

    public TakeFromStackCommand(IntakeSubsystem intake, LiftSubsystem lift, DepositSubsystem deposit, IntakeSubsystem.FrontBarState state){
        addCommands(
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new FrontBarCommand(intake, state),
                new WaitCommand(2000),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.LEVEL4),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                new TakeFromIntakeCommand(lift, deposit, intake)
        );
    }

}
