package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class SetReadyToDeposit extends ParallelCommandGroup {
    public SetReadyToDeposit(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, LiftSubsystem.LiftHeight LiftHeight) {
       addCommands(
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                new LiftCommand(liftSubsystem, LiftHeight)
       );
    }
}
