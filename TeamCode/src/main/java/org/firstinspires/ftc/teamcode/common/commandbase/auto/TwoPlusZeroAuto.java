package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class TwoPlusZeroAuto extends SequentialCommandGroup {
    public TwoPlusZeroAuto(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, MecanumDrive drive, IntakeSubsystem intakeSubsystem){
        addCommands(
                new SequentialCommandGroup(
                        //push block out of way, place purple pixel on ground
                        new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
                        new GroundCommand(depositSubsystem, liftSubsystem),
                        new WaitCommand(300),
                        new SpikePushCommand(drive),
                        new ConditionalCommand(new SequentialCommandGroup(
                                new WaitCommand(300),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                                new WaitCommand(300)),
                                new WaitCommand(1),
                                () -> GlobalVariables.position != GlobalVariables.Position.UNDETECTED),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        //evade purple pixel, place yellow pixel on board
                        new ToBoardCommand(drive),
                        new WaitCommand(1000),//nessecary for camera to stabilize
                        new ConditionalCommand(new AutoDropCommand(depositSubsystem, liftSubsystem, camera, drive, DepositSubsystem.LowerHorizontalState.A),
                                new ConditionalCommand(new AutoDropCommand(depositSubsystem, liftSubsystem, camera, drive, DepositSubsystem.LowerHorizontalState.E),
                                        new AutoDropCommand(depositSubsystem, liftSubsystem, camera, drive, DepositSubsystem.LowerHorizontalState.C),
                                        () -> GlobalVariables.position == GlobalVariables.Position.RIGHT),
                                () -> GlobalVariables.position == GlobalVariables.Position.LEFT)
                )
        );
    }
}

