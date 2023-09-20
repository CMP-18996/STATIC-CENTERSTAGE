package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.vision.VisionPortalCamera;

import java.util.LinkedList;

public class linearDriveToAprilTagCommand extends CommandBase {
    public VisionPortalCamera cam;
    public Drive drive;
    public int tagID;
    private double y;
    public linearDriveToAprilTagCommand(VisionPortalCamera cam, Drive drive, int tagID) {
        this.cam = cam;
        this.drive = drive;
        this.tagID = tagID;
        addRequirements(cam, drive);
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        LinkedList<double[]> obs = cam.getTagLocalization();
        for (double[] tag : obs) {
            if (tag[0] == tagID) {
                y = tag[2];
                drive.manualPower(0, Math.sqrt(tag[2]) - 1, Math.signum(tag[1])*Math.sqrt(Math.abs(tag[1])));
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
