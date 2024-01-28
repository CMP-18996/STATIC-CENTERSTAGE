package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class AutoDropCommand extends SequentialCommandGroup {
    public AutoDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, SampleMecanumDrive drive, boolean willAdjust){
        addCommands(
                //extends arm
                //drives to apriltag
                //drop pixel on boarde
                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                new SequentialCommandGroup(
                        new ToTagCommand(camera, drive, willAdjust),
                        new ParallelCommandGroup(
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE)
                        )
                ),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGHDROP),
                new WaitCommand(700),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(500),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)
        );
    }
}
