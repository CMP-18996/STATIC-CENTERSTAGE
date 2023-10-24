package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@TeleOp(name = "ultrapercise", group = "bruh")
public class ultraprecise extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-35.25, -60.46, Math.toRadians(90)));
        waitForStart();
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .splineTo(new Vector2d(-47, 47), Math.toRadians(90))
                        .splineTo(new Vector2d(-34, 59), Math.toRadians(0))
                        .splineTo(new Vector2d(-13, 59), Math.toRadians(-90))
                        .splineTo(new Vector2d(-11, 38), Math.toRadians(-90))
                        .splineTo(new Vector2d(11.0, 38), Math.toRadians(0))
                        .splineTo(new Vector2d(35.5, 24), Math.toRadians(-90))
                        .splineTo(new Vector2d(3, -34), Math.toRadians(180))
                        .splineTo(new Vector2d(-10, -34), Math.toRadians(270))
                        .splineTo(new Vector2d(-20, -57), Math.toRadians(180))
                        .splineTo(new Vector2d(-35.25, -60.46), Math.toRadians(-90))
                        .waitSeconds(20)
                        .build());

    }   // end method runOpMode()
}
