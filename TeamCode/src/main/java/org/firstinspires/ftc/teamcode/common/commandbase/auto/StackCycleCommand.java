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

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoStrafeBackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.AutoStrafeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToMiddleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class StackCycleCommand extends SequentialCommandGroup {
    public StackCycleCommand(SampleMecanumDrive drive, IntakeSubsystem intake, LiftSubsystem lift, DepositSubsystem deposit, Camera camera, IntakeSubsystem.FrontBarState state) {
        addCommands(
                new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.GROUND),
                new WaitCommand(200),
                new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.AUTO),
                new CoverCommand(intake, IntakeSubsystem.CoverState.CLOSED),
                new ToStackCommand(drive, intake),
                // strafe
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new AutoStrafeCommand(drive),
                new FrontBarCommand(intake, IntakeSubsystem.FrontBarState.LEVEL1),
                new AutoStrafeBackCommand(drive),
                new WaitCommand(1000),
                new FromStackCommand(drive, intake),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new TakeFromIntakeCommand(lift, deposit, intake),
                //deposit command
                new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGH),
                new WaitCommand(250),
                new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                new CoverCommand(intake, IntakeSubsystem.CoverState.CLOSED),

                new AutoDropCommand(deposit, lift, camera, drive, true)
        );
    }
}



