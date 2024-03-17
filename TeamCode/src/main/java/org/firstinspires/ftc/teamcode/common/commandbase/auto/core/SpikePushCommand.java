/**
 * Purpose: Use prop location to drive robot into a position to drop purple pixel
 * Dependencies (variables): POSITION
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Invalid/miscoordinated initial drive pose
 * - Robot pushed off course
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto.core;

import android.provider.Settings;

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
    public double adj = 0;
    @Override
    public void initialize() {
        p = drive.getPoseEstimate();
        x = p.getX();
        y = p.getY();
        h = p.getHeading();
        if ((GlobalVariables.position.equals(GlobalVariables.Position.RIGHT) && GlobalVariables.distance.equals(GlobalVariables.Distance.BLUECLOSE)) ||
                (GlobalVariables.position.equals(GlobalVariables.Position.LEFT) && GlobalVariables.distance.equals(GlobalVariables.Distance.BLUEFAR)) ||
                (GlobalVariables.position.equals(GlobalVariables.Position.LEFT) && GlobalVariables.distance.equals(GlobalVariables.Distance.REDCLOSE)) ||
                (GlobalVariables.position.equals(GlobalVariables.Position.RIGHT) && GlobalVariables.distance.equals(GlobalVariables.Distance.REDFAR))) {
            adj = 5;
        }
    }
    //blue = y+ close = x+
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
            case RED:
                switch (GlobalVariables.position) {
                    case LEFT:
                        //needs to drive more forwards
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3,
                                        -61 + (15 + adj),
                                        Math.toRadians(90) + (Math.signum(x) - 1) *
                                                Math.toRadians(10) + Math.toRadians(30)))
                                .build());
                        break;
                    case RIGHT:
                        //needs to turn more right
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3,
                                        -61 + (15 + adj),
                                        Math.toRadians(90) + (Math.signum(x) + 1) * Math.toRadians(10) - Math.toRadians(33)))
                                .build());
                        break;
                    default:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineTo(new Vector2d(x, y - Math.signum(y) * 20))
                                .build());
                        break;
                }
                break;
            case BLUE:
                switch (GlobalVariables.position) {
                    case LEFT:
                        //needs to drive more forwards
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3,
                                        61 - (15 + adj),
                                        Math.toRadians(-90) - (Math.signum(x) - 1) *
                                                Math.toRadians(15) + Math.toRadians(20)))
                                .build());
                        break;
                    case RIGHT:
                        //needs to turn more right
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3,
                                        61 - (15 + adj),
                                        Math.toRadians(-90) - (Math.signum(x) + 1) * Math.toRadians(15) - Math.toRadians(20)))
                                .build());
                        break;
                    default:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineTo(new Vector2d(x, y - Math.signum(y) * 20))
                                .build());
                        break;
                }
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() {return t;}
}
