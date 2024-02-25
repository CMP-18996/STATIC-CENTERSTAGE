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

    public FTagCommand(Camera camera, SampleMecanumDrive drive) {
        this.camera = camera;
        this.drive = drive;
        addRequirements(this.camera);
    }
    @Override
    public void initialize() {
        drive.setPoseEstimate(new Pose2d(0, 0, drive.getPoseEstimate().getHeading()));
    }
    @Override
    public void execute() {
        currentDetections = camera.getTagLocalization();
        if (currentDetections != null) {
            if (currentDetections.size() > 0) {
                AprilTagDetection tag = currentDetections.get(0);
                tag = currentDetections.get(0);
                double[] stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, Math.toRadians(tag.ftcPose.yaw)};
                //double d = drive.getPoseEstimate().getX() + stats[1] * Math.cos(stats[2]) - 5.5;
                //double y = drive.getPoseEstimate().getY() - (stats[1] * Math.sin(stats[2]) + stats[0]);
                double d = drive.getPoseEstimate().getX() + stats[1] - 7;
                double y = drive.getPoseEstimate().getY();
                double a = drive.getPoseEstimate().getHeading() + stats[2];
                try {
                    switch (tag.id) {
                        case 2: case 5:
                            drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                    .splineToLinearHeading(new Pose2d(d, y, a), a)
                                    .waitSeconds(0.25)
                                    .build());
                            break;
                        case 1: case 4:
                            drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                    .splineToLinearHeading(new Pose2d(d, y, a), a)
                                    .waitSeconds(0.25)
                                    .build());
                            break;
                        case 3: case 6:
                            drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                    .splineToLinearHeading(new Pose2d(d, y, a), a)
                                    .waitSeconds(0.25)
                                    .build());
                            break;
                    }
                } catch (Exception ignored) {}
            }
            t++;
        }
    }
    @Override
    public boolean isFinished() {
        return t >= 2; //replaced by number of times you want to run
    }
    public static void move(AprilTagDetection tag, SampleMecanumDrive drive) {
        double[] stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, Math.toRadians(tag.ftcPose.yaw)};
        //double d = drive.getPoseEstimate().getX() + stats[1] * Math.cos(stats[2]) - 5.5;
        //double y = drive.getPoseEstimate().getY() - (stats[1] * Math.sin(stats[2]) + stats[0]);
        double d = drive.getPoseEstimate().getX() + stats[1] - 7;
        double y = drive.getPoseEstimate().getY();
        double a = drive.getPoseEstimate().getHeading() + stats[2];
        try {
            switch (tag.id) {
                case 2: case 5:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y, a), a)
                            .waitSeconds(0.25)
                            .build());
                    break;
                case 1: case 4:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y, a), a)
                            .waitSeconds(0.25)
                            .build());
                    break;
                case 3: case 6:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(d, y, a), a)
                            .waitSeconds(0.25)
                            .build());
                    break;
            }
        } catch (Exception ignored) {}
    }
}



