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
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class ToSpikeMarkCommand extends CommandBase {
    public MecanumDrive drive;
    boolean t = false;
    public ToSpikeMarkCommand(MecanumDrive drive) {
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
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 15.5), h)
                        .splineTo(new Vector2d(x + Math.signum(y) * 2, y - Math.signum(y) * 33),
                                h + Math.PI / 2)
                        .build());
                break;
            case RIGHT:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 15.5), h)
                        .splineTo(new Vector2d(x - Math.signum(y) * 2, y - Math.signum(y) * 33),
                                h - Math.PI / 2)
                        .build());
                break;
            case MIDDLE:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 15.5), h)
                        .splineTo(new Vector2d(x, y - Math.signum(y) * 30),
                                h)
                        .build());
                break;
            case UNDETECTED:
                switch (GlobalVariables.color) {
                    case RED: //right
                        Actions.runBlocking(drive.actionBuilder(drive.pose)
                                .splineTo(new Vector2d(x, y - Math.signum(y) * 15.5), h)
                                .splineTo(new Vector2d(x - Math.signum(y) * 2, y - Math.signum(y) * 33),
                                        h - Math.PI / 2)
                                .build());
                        break;
                    case BLUE: //left
                        Actions.runBlocking(drive.actionBuilder(drive.pose)
                                .splineTo(new Vector2d(x, y - Math.signum(y) * 15.5), h)
                                .splineTo(new Vector2d(x + Math.signum(y) * 2, y - Math.signum(y) * 33),
                                        h + Math.PI / 2)
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
