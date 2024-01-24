/**
 * Purpose: Use prop location to drive robot into a position to drop purple pixel
 * Dependencies (variables): POSITION
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Invalid/miscoordinated initial drive pose
 * - Robot pushed off course
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;

public class SpikePushCommand extends CommandBase {
    public MecanumDrive drive;
    boolean t = false;
    public SpikePushCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    public Pose2d p;
    public double x;
    public double y;
    public double h;
    public double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.000000000000001;
        if (imag == 0) imag += 0.000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        if (h >= 2 * Math.PI) h -= 2 * Math.PI;
        return h;
    }
    @Override
    public void initialize() {
        p = drive.pose;
        x = p.position.x;
        y = p.position.y;
        h = calculateHeading(p.heading.real, p.heading.imag);
    }
    
    @Override
    public void execute() {
        switch (GlobalVariables.position) {
            case LEFT:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x + Math.signum(x) * 2, y - Math.signum(y) * 16), h - (Math.signum(y) * Math.signum(x) - 1) * Math.toRadians(17.5) + Math.toRadians(18))
                        .build());
                break;
            case RIGHT:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x + Math.signum(x) * 2, y - Math.signum(y) * 16), h - (Math.signum(y) * Math.signum(x) + 1) * Math.toRadians(17.5) - Math.toRadians(18))
                        .build());
                break;
            case MIDDLE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 20), h)
                        .build());
                break;
            default:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 14), h)
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public boolean isFinished() {return t;}
}
