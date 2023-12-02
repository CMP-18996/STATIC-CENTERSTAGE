/**
 * Purpose: Drive from any starting position to corresponding backdrop.
 * Dependencies (variables): COLOR, DISTANCE
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Dependent variables do not match in color
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import static org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance.*;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

public class ToBoardCommand extends CommandBase {
    private MecanumDrive drive;
    boolean t = false;

    public ToBoardCommand(MecanumDrive drive) {
        this.drive = drive;
    }
    @Override
    public void initialize() {}
    @Override
    public void execute() {
        switch (GlobalVariables.color) {
             case BLUE:
                switch (GlobalVariables.distance) {
                    case BLUECLOSE:
                        Actions.runBlocking(drive.actionBuilder(BLUECLOSE.getP())
                                .setReversed(true)
                                .turnTo(Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(46, 36, Math.toRadians(180)), Math.toRadians(0))
                                .build());
                    break;
                    case BLUEFAR:
                        Actions.runBlocking(drive.actionBuilder(BLUEFAR.getP())
                                .setReversed(true)
                                .turnTo(Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(46, 36, Math.toRadians(180)), Math.toRadians(0))
                                .build());
                }
                break;
             case RED:
                 switch (GlobalVariables.distance) {
                     case REDCLOSE:
                         Actions.runBlocking(drive.actionBuilder(REDCLOSE.getP())
                                 .setReversed(true)
                                 .turnTo(Math.toRadians(180))
                                 .splineToSplineHeading(new Pose2d(46, -36, Math.toRadians(180)), Math.toRadians(0))
                                 .build());
                         break;
                     case REDFAR:
                         Actions.runBlocking(drive.actionBuilder(REDFAR.getP())
                                 .setReversed(true)
                                 .turnTo(Math.toRadians(180))
                                 .splineToSplineHeading(new Pose2d(46, 36, Math.toRadians(180)), Math.toRadians(0))
                                 .build());
                 }
        }
        t = true;
    }
    @Override
    public boolean isFinished() { return t;}
}


