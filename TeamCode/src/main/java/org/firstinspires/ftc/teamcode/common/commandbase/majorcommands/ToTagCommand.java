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
package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

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
                    double d = drive.pose.position.x + stats[1] - 9;
                    if (t <= 1) {
                        d -= 10;
                    }
                    if ((GlobalVariables.color.equals(GlobalVariables.Color.BLUE) && tag.id != 2) || (GlobalVariables.color.equals(GlobalVariables.Color.RED) && tag.id != 5)
                            || -0.05 > stats[0] || stats[0] > 0.05 || 8.95 > stats[1] || stats[1] > 9.05 || -1 > stats[5] || stats[5] > 1) {
                        try {
                            Actions.runBlocking(drive.actionBuilder(drive.pose)
                                    .turnTo(calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + Math.toRadians(stats[5]))
                                    .waitSeconds(0.5)
                                    .build());
                            switch (tag.id) {
                                case 2: case 5:
                                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                                            .setReversed(true)
                                            .splineToConstantHeading(new Vector2d(d, drive.pose.position.y - stats[0]),
                                                    calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + Math.toRadians(stats[5]) - Math.PI)
                                            .waitSeconds(1)
                                            .build());
                                    break;
                                case 1: case 4:
                                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                                            .setReversed(true)
                                            .splineToConstantHeading(new Vector2d(d, drive.pose.position.y - stats[0] - 6),
                                                    calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + Math.toRadians(stats[5]) - Math.PI)
                                            .waitSeconds(1)
                                            .build());
                                    break;
                                case 3: case 6:
                                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                                            .setReversed(true)
                                            .splineToConstantHeading(new Vector2d(d, drive.pose.position.y - stats[0] + 6),
                                                    calculateHeading(drive.pose.heading.real, drive.pose.heading.imag) + Math.toRadians(stats[5]) - Math.PI)
                                            .waitSeconds(1)
                                            .build());
                                    break;
                            }
                        } catch (Exception ignored) {}
                        break;
                    }
                }
            } else {
                try {
                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                            .splineTo(new Vector2d(drive.pose.position.x - 6, drive.pose.position.y),
                                    calculateHeading(drive.pose.heading.real, drive.pose.heading.imag))
                            .build());
                } catch (Exception ignored) {}
            }
        }
        t++;
    }
    @Override
    public boolean isFinished() {
        return t >= 10;
    }
    public List<Integer> getSight() {
        List<Integer> i = new ArrayList<>();
        if (currentDetections != null) {
            for (AprilTagDetection tag : currentDetections) {
                i.add(tag.id);
            }
        }
        return i;
    }
}



