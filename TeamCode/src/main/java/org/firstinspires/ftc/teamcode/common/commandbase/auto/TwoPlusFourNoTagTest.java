package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.GroundCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.SpikePushCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class TwoPlusFourNoTagTest extends SequentialCommandGroup {
    public TwoPlusFourNoTagTest(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, Camera camera, SampleMecanumDrive drive, IntakeSubsystem intakeSubsystem){
        addCommands(
                new SequentialCommandGroup(
                        //push block out of way, place purple pixel on ground
                        new GroundCommand(intakeSubsystem, depositSubsystem, liftSubsystem),
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
                        new WaitCommand(2000),
                        //new ToBoardCommand(drive),
                        //new WaitCommand(1000),//necessary for camera to stabilize
                        new NoTagBoardAlign(drive),
                        new NoTagAutoDropCommand(depositSubsystem, liftSubsystem, camera, drive)
                        /*new StackCycleCommand(drive),
                        new NoTagAutoDropCommand(depositSubsystem, liftSubsystem, camera, drive),
                        new StackCycleCommand(drive),
                        new NoTagAutoDropCommand(depositSubsystem, liftSubsystem, camera, drive)*/

                        ));
    }
}

