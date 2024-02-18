package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.GroundCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.SpikePushCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class TwoPlusZeroAuto extends SequentialCommandGroup {
    public TwoPlusZeroAuto(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, SampleMecanumDrive drive, IntakeSubsystem intakeSubsystem){
        addCommands(
                new InstantCommand(),
                //push block out of way, place purple pixel on ground
                new ParallelDeadlineGroup(
                        new WaitCommand(1000),
                        new GroundCommand(intakeSubsystem, depositSubsystem, liftSubsystem)
                ),
                new SpikePushCommand(drive),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new ParallelDeadlineGroup(
                        new WaitCommand(500),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)
                ),
                //evade purple pixel, place yellow pixel on board
                new ToBoardCommand(drive),
                new WaitCommand(250),//nessecary for camera to stabilize
                new AutoDropCommand(depositSubsystem, liftSubsystem, camera, drive, true)
                //new ParkCommand(drive)
        );
    }
}

