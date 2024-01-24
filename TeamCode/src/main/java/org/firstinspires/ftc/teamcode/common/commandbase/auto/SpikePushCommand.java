/**
 * Purpose: Use prop location to drive robot into a position to drop purple pixel
 * Dependencies (variables): POSITION
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Invalid/miscoordinated initial drive pose
 * - Robot pushed off course
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class SpikePushCommand extends CommandBase {
    public SampleMecanumDrive drive;
    boolean t = false;
    public SpikePushCommand(SampleMecanumDrive drive) {
        this.drive = drive;
    }
    public Pose2d p;
    public double x;
    public double y;
    public double h;
    @Override
    public void initialize() {
        p = drive.getPoseEstimate();
        x = p.getX();
        y = p.getY();
        h = p.getHeading();
    }
    
    @Override
    public void execute() {
        switch (GlobalVariables.position) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineTo(new Vector2d(x + Math.signum(x) * 2, y - Math.signum(y) * 16), h - (Math.signum(y) * Math.signum(x) - 1) * Math.toRadians(17.5) + Math.toRadians(18))
                        .build());
                break;
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineTo(new Vector2d(x + Math.signum(x) * 2, y - Math.signum(y) * 16), h - (Math.signum(y) * Math.signum(x) + 1) * Math.toRadians(17.5) - Math.toRadians(18))
                        .build());
                break;
            case MIDDLE:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 20), h)
                        .build());
                break;
            default:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 14), h)
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() {return t;}
}
