package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class AutoDropCommand extends SequentialCommandGroup {
    public AutoDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, SampleMecanumDrive drive, boolean willAdj){
        addCommands(
                //extends arm
                //drives to apriltag
                //drop pixel on boarde
                //new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                //new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new ConditionalCommand(
                        new ToTagCommand(camera, drive, willAdj),
                        new FTagCommand(camera, drive),
                        () -> willAdj
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(850),
                        new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGHDROP)
                ),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new ParallelDeadlineGroup(
                        new WaitCommand(250),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)
                )
        );
    }
}

