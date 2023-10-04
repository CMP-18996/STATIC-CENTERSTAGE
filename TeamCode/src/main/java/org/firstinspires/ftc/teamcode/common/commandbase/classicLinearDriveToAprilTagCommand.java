package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class classicLinearDriveToAprilTagCommand extends CommandBase {
    private Camera camera;
    private Drive drive;
    private static final double FEET_PER_METER = 3.28084;
    private AprilTagDetection tagOfInterest = null;
    ArrayList<AprilTagDetection> currentDetections;
    private double[] stats = new double[]{999, 999, 999, 999, 999, 999};
    private final double MARGIN_OF_ERROR = 1.0;
    private int tagID;
    public int getTagOfInterest() {
        if (tagOfInterest == null) {
            return -1;
        }
        return tagOfInterest.id;
    }
    public static List<Double> convertArrayToList(double[] array) {
        // Create an empty List
        List<Double> list = new ArrayList<>();

        // Iterate through the array
        for (double t : array) {
            // Add each element into the list
            list.add(t);
        }

        // Return the converted List
        return list;
    }

    public List<Double> getStats() {
        return convertArrayToList(stats);
    }
    public classicLinearDriveToAprilTagCommand(Robot robot, Drive drive, int tagID) {
        this.camera = robot.camera;
        this.drive = drive;
        this.tagID = tagID;
        addRequirements(this.camera, this.drive);
    }

    @Override
    public void initialize() {}
    @Override
    public void execute() {
        currentDetections = camera.getAprilTagPipeline().getLatestDetections();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 4 ||tag.id == 5 ||tag.id == 6) {
                    tagOfInterest = tag;
                    break;
                }
            }
        }
        if (tagOfInterest != null) {
            Orientation rot = Orientation.getOrientation(tagOfInterest.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
            stats = new double[]{tagOfInterest.pose.x * FEET_PER_METER,
                    tagOfInterest.pose.y * FEET_PER_METER,
                    tagOfInterest.pose.z * FEET_PER_METER,
                    rot.firstAngle,
                    rot.secondAngle,
                    rot.thirdAngle};
            drive.manualPower(0.3*stats[0], 0.3*stats[1], 0.01 * stats[3]);
        } else {
            drive.manualPower(0,0,0.2);
        }
    }
    @Override
    public boolean isFinished() {
        if (Math.abs(stats[0]) < MARGIN_OF_ERROR && Math.abs(stats[3]) < MARGIN_OF_ERROR) {
            drive.manualPower(0,0,0);
            return true;
        }
        return false;
    }

}



