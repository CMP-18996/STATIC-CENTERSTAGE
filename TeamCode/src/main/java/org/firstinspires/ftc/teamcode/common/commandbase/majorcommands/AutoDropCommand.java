package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class AutoDropCommand extends SequentialCommandGroup {
    public AutoDropCommand(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, MecanumDrive drive, DepositSubsystem.LowerHorizontalState lowerHorizontalState){
        addCommands(
                //extends arm
                //drives to apriltag
                //correct position of arm based on apriltag
                //drop pixel on boarde
                new ParallelCommandGroup(
                        new ToTagCommand(camera, drive),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                        new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalState)
                ),
                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGHDROP),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT)
        );
    }
}

