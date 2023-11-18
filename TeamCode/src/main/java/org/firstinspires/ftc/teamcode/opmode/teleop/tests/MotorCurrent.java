package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name="Current test")
public class MotorCurrent extends LinearOpMode {
    DcMotorEx leftFront, rightFront, leftRear, rightRear;
    double x1, y1, x2;
    @Override
    public void runOpMode() {
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");

        waitForStart(); // init blocker
        // random code 2
        while (opModeIsActive()) {
            leftFront.setDirection(DcMotor.Direction.REVERSE);
            leftRear.setDirection(DcMotor.Direction.REVERSE);
            rightFront.setDirection(DcMotor.Direction.FORWARD);
            rightRear.setDirection(DcMotor.Direction.FORWARD);
            x1 = gamepad1.left_stick_x;
            y1 = gamepad1.left_stick_y;
            x2 = gamepad1.right_stick_x;
            leftFront.setPower(x1 - y1 - x2);
            rightFront.setPower(x1 + y1 + x2);
            leftRear.setPower(x1 + y1 - x2);
            rightRear.setPower(x1 - y1 + x2);
            telemetry.addData("Left front: ", leftFront.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Right front: ", rightFront.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Left rear: ", leftRear.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Right rear: ", rightRear.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }
}