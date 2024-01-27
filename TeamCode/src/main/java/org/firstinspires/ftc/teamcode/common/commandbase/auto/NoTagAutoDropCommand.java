package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class NoTagAutoDropCommand extends SequentialCommandGroup {
    public NoTagAutoDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, SampleMecanumDrive drive){
        addCommands(
                //extends arm
                //drives to apriltag
                //correct position of arm based on apriltag
                //drop pixel on board
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                /*new SequentialCommandGroup(
                        new ToTagCommand(camera, drive),
                        new ParallelCommandGroup(
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE)
                                //new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalState)
                        )
                ),*/
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGHDROP),
                new WaitCommand(500),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(2000),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)
        );
    }
}

