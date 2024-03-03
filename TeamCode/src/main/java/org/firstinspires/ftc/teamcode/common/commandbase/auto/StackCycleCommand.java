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

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class StackCycleCommand extends SequentialCommandGroup {
    public StackCycleCommand(SampleMecanumDrive drive, IntakeSubsystem intake, LiftSubsystem lift, DepositSubsystem deposit, Camera camera, IntakeSubsystem.FrontBarState state) {
        addCommands(
                //new StasisCommand(lift, deposit, intake),
                new ParallelCommandGroup(
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                        new FrontBarCommand(intake, state),
                        new ToStackCommand(drive, intake)
                ),
                // strafe
                //new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new ParallelCommandGroup(
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                        new TakeFromIntakeCommand(lift, deposit, intake),
                        new FromStackCommand(drive, intake)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(500),
                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGH),
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED)
                ),
                new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                //new FTagCommand(camera, drive),
                new ParallelDeadlineGroup(
                        new WaitCommand(1000),
                        new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                        new LiftCommand(lift, LiftSubsystem.LiftHeight.HEIGHTTHREE),
                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGHDROP)
                ),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new ParallelDeadlineGroup(
                        new WaitCommand(250),
                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.STASIS),
                        new CoverCommand(intake, IntakeSubsystem.CoverState.CLOSED)
                )
        );
    }
}