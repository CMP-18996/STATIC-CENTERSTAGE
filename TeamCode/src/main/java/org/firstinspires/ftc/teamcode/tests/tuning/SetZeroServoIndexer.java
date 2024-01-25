package org.firstinspires.ftc.teamcode.tests.tuning;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.Robot;


@TeleOp
public class SetZeroServoIndexer extends LinearOpMode {
    Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        boolean upPressed, downPressed, rightBumperPressed, leftBumperPressed, rightPressed, leftPressed;
        upPressed = false;
        downPressed = false;
        rightBumperPressed = false;
        rightPressed = false;
        leftPressed = false;
        leftBumperPressed = false;
        ServoEx servos[] = {
                robot.coverServo,
                robot.xAdj,
                robot.fourBar,
                robot.leftGrabber,
                robot.rightGrabber,
                robot.depositExpansion
        };
        String names[] = {
                "robot.coverServo",
                "robot.xAdj",
                "robot.fourBar",
                "robot.leftGrabber",
                "robot.rightGrabber",
                "robot.depositExpansion"
        };
        double value = 0;
        double incrementValue = .0025;
        int index = 0;
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !upPressed) {
                upPressed = true;
                value = Math.min(1, value + incrementValue);
            } else if (!gamepad1.dpad_up) {
                upPressed = false;
            }

            if (gamepad1.dpad_down && !downPressed) {
                downPressed = true;
                value = Math.max(0, value - incrementValue);
            } else if (!gamepad1.dpad_down) {
                downPressed = false;
            }

            if (gamepad1.right_bumper && !rightBumperPressed) {
                rightBumperPressed = true;
                index += 1;
            } else if (!gamepad1.right_bumper) {
                rightBumperPressed = false;
            }

            if (gamepad1.left_bumper && !leftBumperPressed) {
                leftBumperPressed = true;
                index -= 1;
            } else if (!gamepad1.left_bumper) {
                leftBumperPressed = false;
            }

            if (gamepad1.dpad_right && !rightPressed) {
                rightPressed = true;
                incrementValue *= 1.3;
                value = 0;
            } else if (!gamepad1.dpad_right) {
                rightPressed = false;
            }

            if (gamepad1.dpad_left && !leftPressed) {
                leftPressed = true;
                incrementValue /= 1.3;
                value = 0;
            } else if (!gamepad1.dpad_left) {
                leftPressed = false;
            }

            servos[index].setPosition(value % 6);
            telemetry.addLine("Currently Changing " + names[index % 6]);
            telemetry.addData("Current Value:", value);
            telemetry.addData("Current Increment:", incrementValue);
            telemetry.update();
        }
    }
}
