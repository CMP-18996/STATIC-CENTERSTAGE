/**
 * Purpose: Use prop location to drop the yellow pixel in the relative position.
 * Dependencies (variables): POSITION
 * Dependencies (subsystem): ...
 * Most Likely Errors:
 * - idk
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
    public double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.000000000000001;
        if (imag == 0) imag += 0.000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        return h;
    }
    @Override
    public void initialize() {
        p = drive.pose;
        switch (GlobalVariables.position) {
            case LEFT:
                Actions.runBlocking(drive.actionBuilder(p)
                        .setReversed(true)
                        .splineTo(new Vector2d(p.position.x + Math.signum(p.position.y) * 2, p.position.y - Math.signum(p.position.y) * 29),
                                calculateHeading(p.heading.real, p.heading.imag) - Math.PI / 2)
                        .build());
                break;
            case RIGHT:
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(p.position.x - Math.signum(p.position.y) * 2, p.position.y - Math.signum(p.position.y) * 2),
                                calculateHeading(p.heading.real, p.heading.imag) + Math.PI / 2)
                        .build());
                break;
            default: //middle and nothing, bc average convenient position
                Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .splineTo(new Vector2d(p.position.x, p.position.y - Math.signum(p.position.y) * 29),
                                calculateHeading(p.heading.real, p.heading.imag))
                        .build());
                break;
        }
        t = true;
    }
    @Override
    public void execute() {}
    @Override
    public boolean isFinished() { return t;}
}
