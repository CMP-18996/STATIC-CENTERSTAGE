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
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class ToTagCommand extends CommandBase {
    private Camera camera;
    private SampleMecanumDrive drive;
    int t = 0;
    List<AprilTagDetection> currentDetections;

    public ToTagCommand(Camera camera, SampleMecanumDrive drive) {
        this.camera = camera;
        this.drive = drive;
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
                    move(tagOfInterest, drive, t);
                } else {
                    tagOfInterest = currentDetections.get(0);
                    move(tagOfInterest, drive, t);
                }
            }
            t++;
        }
    }
    @Override
    public boolean isFinished() {
        return t >= 2; //replaced by number of times you want to run
    }
    public static void move(AprilTagDetection tag, SampleMecanumDrive drive, int t) {
        double[] stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z,
                tag.ftcPose.pitch, tag.ftcPose.roll, tag.ftcPose.yaw};
        double d = drive.getPoseEstimate().getX() + stats[1] - 7;
        double y = drive.getPoseEstimate().getY() - stats[0];
        double a = drive.getPoseEstimate().getHeading() + Math.toRadians(stats[5]);
        if (t == 0) {
            try {
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .turn(Math.toRadians(stats[5]))
                        .build());
            } catch (Exception ignored) {}
        }
        try {
            switch (tag.id) {
                case 2: case 5:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToConstantHeading(new Vector2d(d, y), a)
                            .waitSeconds(1)
                            .build());
                    break;
                case 1: case 4:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToConstantHeading(new Vector2d(d, y - 6), a)
                            .waitSeconds(1)
                            .build());
                    break;
                case 3: case 6:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                            .splineToConstantHeading(new Vector2d(d, y + 6), a)
                            .waitSeconds(1)
                            .build());
                    break;
            }
        } catch (Exception ignored) {}
    }
}



