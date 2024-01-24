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

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class StackCycleCommand extends CommandBase {
    private SampleMecanumDrive drive;
    boolean t = false;
    public StackCycleCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case BLUE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .setReversed(true)
                                .splineTo(new Vector2d(-62, 36), Math.toRadians(180))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .splineTo(new Vector2d(20,  36), Math.toRadians(0))
                                .build());
                break;
            case RED:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .setReversed(true)
                                .splineTo(new Vector2d(-62, -36), Math.toRadians(180))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .splineTo(new Vector2d(20, -36), Math.toRadians(0))
                                .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}



