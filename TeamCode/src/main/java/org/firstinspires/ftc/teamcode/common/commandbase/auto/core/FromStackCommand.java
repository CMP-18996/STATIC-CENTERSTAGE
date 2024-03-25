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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class FromStackCommand extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;
    public FromStackCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case BLUE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineToConstantHeading(new Vector2d(-44, 0), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(27, 0), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(46, 30), Math.toRadians(0))
                        .turn(Math.toRadians(20))
                        .build());
                break;
            case RED:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineToConstantHeading(new Vector2d(27, -6), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(43, -36), Math.toRadians(0))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



