package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.vision.AprilTagCamera;

import java.util.LinkedList;

public class linearDriveToAprilTagCommand extends CommandBase {
    public AprilTagCamera cam;
    public Drive drive;
    public int tagID;
    private double y;
    public linearDriveToAprilTagCommand(AprilTagCamera cam, Drive drive, int tagID) {
        this.cam = cam;
        this.drive = drive;
        this.tagID = tagID;
        addRequirements(cam, drive);
    }
    @Override
    public void initialize() {
        cam.getVisionPortal().resumeStreaming();
    }
    @Override
    public void execute() {
        LinkedList<double[]> obs = cam.getTagLocalization();
        for (double[] tag : obs) {
            if (tag[0] == tagID) {
                y = tag[2];
                drive.manualPower(0, Math.sqrt(tag[2]) - 1, Math.sqrt(tag[1]));
                break;
            }
        }
    }
    @Override
    public boolean isFinished() {
        if (y <= 1) {
            cam.getVisionPortal().close();
            drive.manualPower(0,0,0);
            return true;
        }
        return false;
    }
}
