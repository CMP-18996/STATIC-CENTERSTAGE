package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.vision.Camera;


public class GroundDropCommand extends SequentialCommandGroup {

    public GroundDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem){
        addCommands(
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE),
                new WaitCommand(200),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT)
        );
    }

}
