package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

    public class AutoDropCommand extends SequentialCommandGroup {
    AutoDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, MecanumDrive drive, DepositSubsystem.GrabberPos grabberPos, DepositSubsystem.LowerHorizontalState lowerHorizontalState){
        addCommands(
                new ParallelCommandGroup(
                        new SetReadyToDeposit(depositSubsystem, liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE),
                        new ToTagCommand(camera, drive),
                        new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalState)
                ),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, grabberPos)
        );
    }
}
