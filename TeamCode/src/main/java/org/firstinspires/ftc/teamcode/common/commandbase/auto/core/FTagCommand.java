/**
 * Purpose: Align to a central apriltag with a trajectory
 * to go right in front of and centered to a backdrop.
 * Dependencies (variables): none
 * Dependencies (subsystem): Camera, RR-drive
 * Most Likely Errors:
 * - Incorrect camera positioning leads to incorrect localization
 * - Camera fails to see apriltag due to distance, blur, or lighting
 * - Various camera crashes
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class FTagCommand extends CommandBase {
    private Camera camera;
    private SampleMecanumDrive drive;
    int t = 0;
    List<AprilTagDetection> currentDetections;
    static boolean willAdj;

    public FTagCommand(Camera camera, SampleMecanumDrive drive) {
        this.camera = camera;
        this.drive = drive;
        ToTagCommand.willAdj = willAdj;
        addRequirements(this.camera);
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        currentDetections = camera.getTagLocalization();
        if (currentDetections != null) {
            if (currentDetections.size() > 0) {
                AprilTagDetection tagOfInterest = null;
                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == 2 || tag.id == 5) {
                        tagOfInterest = tag;
                    }
                }
                if (tagOfInterest != null) {
                    move(tagOfInterest, drive);
                } else {
                    tagOfInterest = currentDetections.get(0);
                    move(tagOfInterest, drive);
                }
            }
            t++;
        }
    }
    @Override
    public boolean isFinished() {
        return t >= 1; //replaced by number of times you want to run
    }
    public static void move(AprilTagDetection tag, SampleMecanumDrive drive) {
        double[] stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, Math.toRadians(tag.ftcPose.yaw)};
        double d = drive.getPoseEstimate().getX() + stats[1] * Math.cos(stats[2]) - 5.5;
        double y = drive.getPoseEstimate().getY() - (stats[1] * Math.sin(stats[2]) + stats[0]);
        //double d = drive.getPoseEstimate().getX() + stats[1]  - 5.5;
        //double y = drive.getPoseEstimate().getY() - stats[0];
        if (true) {
            switch (GlobalVariables.position) {
                case LEFT:
                    y += 8.5;
                    break;
                case RIGHT:
                    y -= 6.5;
                    break;
            }
        }
        double a = drive.getPoseEstimate().getHeading() + stats[2];
        try {
            switch (tag.id) {
                case 2: case 5:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y, a), a)
                            .build());
                    break;
                case 1: case 4:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y - 8, a), a)
                            .build());
                    break;
                case 3: case 6:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y + 8, a), a)
                            .build());
                    break;
            }
        } catch (Exception ignored) {}
    }
}



