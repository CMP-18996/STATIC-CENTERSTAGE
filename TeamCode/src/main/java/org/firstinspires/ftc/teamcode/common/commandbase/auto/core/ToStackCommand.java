/**
 * Purpose: Drive from the backdrop to a stack, then drives back to the backdrop.
 * Dependencies (variables): COLOR
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Crashes into other robots
 * - Intake system fails to obtain pixel
 * - General odometry drift
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class ToStackCommand extends CommandBase {
    private SampleMecanumDrive drive;
    private IntakeSubsystem intake;
    boolean t = false;
    public ToStackCommand(SampleMecanumDrive drive, IntakeSubsystem intake) {
        this.drive = drive;
        this.intake = intake;
    }
    @Override
    public void initialize() {
       drive.setPoseEstimate(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), 0));
    }
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case BLUE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .splineToConstantHeading(new Vector2d(27, 0), Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(-44, 0), Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(-61.5, 18), Math.toRadians(180))
                        .setReversed(false)
                        .build());
                break;
            case RED:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(true)
                        .splineToConstantHeading(new Vector2d(27, -6), Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(-40, -6), Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(-62, -17), Math.toRadians(180))
                        .setReversed(false)
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



