package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class TwoPlusFourAuto extends SequentialCommandGroup {
    public TwoPlusFourAuto(DepositSubsystem deposit, LiftSubsystem lift, Camera camera, SampleMecanumDrive drive, IntakeSubsystem intake){
        addCommands(
                new TwoPlusZeroAuto(deposit, lift, camera, drive, intake),
                new ToStackCommand(drive),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new WaitCommand(2000),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                                new TakeFromIntakeCommand(lift, deposit, intake)
                        ),
                        new FromStackCommand(drive)
                ),
                new AutoDropCommand(deposit, lift, camera, drive, false),
                new ToStackCommand(drive),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new WaitCommand(2000),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                                new TakeFromIntakeCommand(lift, deposit, intake)
                        ),
                        new FromStackCommand(drive)
                ),
                new AutoDropCommand(deposit, lift, camera, drive, false)
        );
    }
}
