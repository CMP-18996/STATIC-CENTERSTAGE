package org.firstinspires.ftc.teamcode.tests.tuning;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Intake Bar Indexer")
public class IntakeBarIndexer extends LinearOpMode {
    Robot robot;
    ServoEx leftBar, rightBar;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        leftBar = robot.frontBar1;
        rightBar = robot.frontBar2;

        boolean upPressed, downPressed, rightPressed, leftPressed;
        upPressed = false;
        downPressed = false;
        rightPressed = false;
        leftPressed = false;

        waitForStart();
        double value1 = 0;
        double value2 = 1;
        double incrementValue = .0025;

        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !upPressed) {
                upPressed = true;
                value1 = Math.max(0, value1 - incrementValue);
                value2 = Math.min(1, value2 + incrementValue);
            } else if (!gamepad1.dpad_up) {
                upPressed = false;
            }

            if (gamepad1.dpad_down && !downPressed) {
                downPressed = true;
                value1 = Math.min(1, value1 + incrementValue);
                value2 = Math.max(0, value2 - incrementValue);
            } else if (!gamepad1.dpad_down) {
                downPressed = false;
            }

            if (gamepad1.dpad_right && !rightPressed) {
                rightPressed = true;
                incrementValue *= 1.3;
                value1 = 0;
                value2 = 1;
            } else if (!gamepad1.dpad_right) {
                rightPressed = false;
            }

            if (gamepad1.dpad_left && !leftPressed) {
                leftPressed = true;
                incrementValue /= 1.3;
                value1 = 0;
                value2 = 1;
            } else if (!gamepad1.dpad_left) {
                leftPressed = false;
            }

            // The servos are inverted, uninvert the servos to run
            leftBar.setPosition(value1);
            rightBar.setPosition(value2);
            telemetry.addData("Current Value 1:", value1);
            telemetry.addData("Current Value 2:", value2);
            telemetry.addData("Current Increment:", incrementValue);
            telemetry.update();
        }
    }
}
