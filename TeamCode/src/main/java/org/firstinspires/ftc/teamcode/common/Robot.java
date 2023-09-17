package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    public enum OpModes {
        AUTO,
        TELEOP
    }
    public Motor leftFront, rightFront, leftRear, rightRear;
    public BNO055IMU imu;

    public Robot(HardwareMap hardwareMap, OpModes mode) {
        leftFront = new Motor(hardwareMap, "leftFront");
        leftFront.setRunMode(Motor.RunMode.RawPower);
        rightFront = new Motor(hardwareMap,"rightFront");
        rightFront.setRunMode(Motor.RunMode.RawPower);
        leftRear = new Motor(hardwareMap,"leftRear");
        leftRear.setRunMode(Motor.RunMode.RawPower);
        rightRear = new Motor(hardwareMap,"rightRear");
        rightRear.setRunMode(Motor.RunMode.RawPower);

        if (mode.equals(OpModes.AUTO)) {
            //camera init
        }
    }
    public double getAngle() {
        return imu.getAngularOrientation().firstAngle;
    }
}
