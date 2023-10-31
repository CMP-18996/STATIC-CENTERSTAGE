package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class DriveToTagCommand extends CommandBase {
    private Camera camera;
    private Drive drive;
    private AprilTagDetection t = null;
    ArrayList<AprilTagDetection> currentDetections;
    private double[] stats = new double[]{999, 999, 999, 999, 999, 999};
    private final double MARGIN_OF_ERROR = 1.0;
    private int tagID;
    public int getTagOfInterest() {
        if (t == null) {
            return -1;
        }
        return t.id;
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
    public DriveToTagCommand(Robot robot, Drive drive, int tagID) {
        this.camera = robot.camera;
        this.drive = drive;
        this.tagID = tagID;
        addRequirements(this.camera, this.drive);
    }

    @Override
    public void initialize() {}
    @Override
    public void execute() {
        currentDetections = camera.getTagLocalization();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 4 || tag.id == 5 || tag.id == 6) {
                    t = tag;
                    break;
                }
            }
        }
        if (t != null) {
            stats = new double[]{t.ftcPose.x, t.ftcPose.y, t.ftcPose.z,
                    t.ftcPose.pitch, t.ftcPose.roll, t.ftcPose.yaw};
            drive.manualPower(0.3*stats[0], 0.3*stats[1], 0.01 * stats[5]);
        } else {
            drive.manualPower(0,0,0);
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



