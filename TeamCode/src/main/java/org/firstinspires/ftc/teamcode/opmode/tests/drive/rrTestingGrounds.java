package org.firstinspires.ftc.teamcode.opmode.tests.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@TeleOp(name="rr testing grounds")
public class rrTestingGrounds extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(7, 10), Math.toRadians(0))
                .splineTo(new Vector2d(15, 0), Math.toRadians(0))
                .turn(2 * Math.PI)
                .setReversed(true)
                .splineTo(new Vector2d(7, 10), Math.toRadians(180))
                .splineTo(new Vector2d(0, 0), Math.toRadians(180))
                .setReversed(false)
                .strafeTo(new Vector2d(0, 10))
                .strafeTo(new Vector2d(0, 0))
                .waitSeconds(2)
                .setReversed(false)
                .strafeTo(new Vector2d(10, 10))
                .strafeTo(new Vector2d(0, 0))
                .lineToX(10)
                .lineToY(5)
                .lineToXLinearHeading(0, Math.toRadians(90))
                .lineToYLinearHeading(-5, Math.toRadians(0))
                .setReversed(false)
                .splineTo(new Vector2d(7, 10), Math.toRadians(0))
                .splineTo(new Vector2d(15, 0), Math.toRadians(0))
                .build());
    }
}