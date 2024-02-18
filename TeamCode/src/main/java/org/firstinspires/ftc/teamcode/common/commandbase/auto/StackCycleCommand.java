/**
 * Purpose: Drive from the backdrop to a stack, then drives back to the backdrop.
 * Dependencies (variables): COLOR
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Crashes into other robots
 * - Intake system fails to obtain pixel
 * - General odometry drift
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class StackCycleCommand extends SequentialCommandGroup {
    public StackCycleCommand(SampleMecanumDrive drive, IntakeSubsystem intake, LiftSubsystem lift, DepositSubsystem deposit, Camera camera, IntakeSubsystem.FrontBarState state) {
        addCommands(
                new StasisCommand(lift, deposit, intake),
                new ParallelDeadlineGroup(
                        new WaitCommand(7000),
                        new ToStackCommand(drive, intake)
                ),
                // strafe
                //new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new FromStackCommand(drive, intake),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                new TakeFromIntakeCommand(lift, deposit, intake),
                //deposit command
                /*new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGH),
                new WaitCommand(250),
                new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                new CoverCommand(intake, IntakeSubsystem.CoverState.CLOSED),*/
                new StasisCommand(lift, deposit, intake),
                new AutoDropCommand(deposit, lift, camera, drive, false)
        );
    }
}