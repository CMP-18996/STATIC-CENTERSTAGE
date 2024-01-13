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

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class ToTagCommand extends CommandBase {
    private Camera camera;
    private MecanumDrive drive;
    int t = 0;
    List<AprilTagDetection> currentDetections;

    public ToTagCommand(Camera camera, MecanumDrive drive) {
        this.camera = camera;
        this.drive = drive;
        addRequirements(this.camera);
    }
    //turn complex coordinates into angle from 0-360
    public double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.0000000000000000001;
        if (imag == 0) imag += 0.0000000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        if (h < 0) h += 2 * Math.PI;
        return h;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        currentDetections = camera.getTagLocalization();
        if (currentDetections != null) {
            if (currentDetections.size() > 0) {
                for (AprilTagDetection tag : currentDetections) {
                    double[] stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z,
                            tag.ftcPose.pitch, tag.ftcPose.roll, tag.ftcPose.yaw};
                    double d = drive.pose.position.x + stats[1] - 6;
                    double y = drive.pose.position.y - stats[0];
                    double a = calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + Math.toRadians(stats[5]);
                    if (t == 0) {
                        try {
                            Actions.runBlocking(drive.actionBuilder(drive.pose)
                                    .turnTo(a)
                                    .build());
                        } catch (Exception ignored) {}
                    }
                    try {
                        switch (tag.id) {
                            case 2: case 5:
                                Actions.runBlocking(drive.actionBuilder(drive.pose)
                                        .splineToSplineHeading(new Pose2d(d, y, a), a)
                                        .waitSeconds(1)
                                        .build());
                                break;
                            case 1: case 4:
                                Actions.runBlocking(drive.actionBuilder(drive.pose)
                                        .splineToSplineHeading(new Pose2d(d, y - 6, a), a)
                                        .waitSeconds(1)
                                        .build());
                                break;
                            case 3: case 6:
                                Actions.runBlocking(drive.actionBuilder(drive.pose)
                                        .splineToSplineHeading(new Pose2d(d, y + 6, a), a)
                                        .waitSeconds(1)
                                        .build());
                                break;
                            }
                        } catch (Exception ignored) {}
                        break;
                    }
                } else {
                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                            .splineTo(new Vector2d(drive.pose.position.x - 4, drive.pose.position.y),
                                    calculateHeading(drive.pose.heading.real, drive.pose.heading.imag))
                            .build());
                }
            }
            t++;
        }
    @Override
    public boolean isFinished() {
        return t >= 2; //replaced by number of times you want to run
    }
}



