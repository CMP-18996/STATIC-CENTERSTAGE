/**
 * ONLY WORKS FOR ONE SPECIFIED TAG
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class AutoDriveToTagCommand extends CommandBase {
    private Camera camera;
    private MecanumDrive drive;
    boolean b = false;
    List<AprilTagDetection> currentDetections;
    private double[] stats = new double[]{999, 999, 999, 999, 999, 999};
    public AutoDriveToTagCommand(Camera camera, MecanumDrive drive) {
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
                    if (tag.id == 2 || tag.id == 5) {
                        stats = new double[]{tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z,
                                tag.ftcPose.pitch, tag.ftcPose.roll, tag.ftcPose.yaw};
                        Actions.runBlocking(drive.actionBuilder(drive.pose)
                                .splineTo(new Vector2d(drive.pose.position.y - stats[0],
                                                drive.pose.position.x + stats[1]),
                                        Math.toRadians(calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + stats[5]))
                                .build());
                        break;
                    }
                }
            }
        }
        b = true; //command only run once
    }
    @Override
    public boolean isFinished() {return b;}
}



