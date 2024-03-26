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
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.StackSwivelCommand;
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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class StackCycleCommand extends SequentialCommandGroup {
    public StackCycleCommand(SampleMecanumDrive drive, IntakeSubsystem intake, LiftSubsystem lift, DepositSubsystem deposit) {
        addCommands(
                new ParallelDeadlineGroup(
                        new WaitCommand(500),
                        new StasisCommand(lift, deposit, intake)
                ),
                new ParallelCommandGroup(
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                        new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.LEVEL1),
                        new ToStackCommand(drive, intake)
                ),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.LEVEL4),
                new FromStackCommand(drive),

                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                new TakeFromIntakeCommand(lift, deposit, intake),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(200),
                new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGHDROP),
                new WaitCommand(300),
                new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                new WaitCommand(600),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(200),

                new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGH),
                new WaitCommand(200),
                new TakeFromIntakeCommand(lift, deposit, intake),
                new WaitCommand(200),
                new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGHDROP),
                new WaitCommand(300),
                new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                new WaitCommand(600),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(200),

                new StasisCommand(lift, deposit, intake)
        );
    }
}