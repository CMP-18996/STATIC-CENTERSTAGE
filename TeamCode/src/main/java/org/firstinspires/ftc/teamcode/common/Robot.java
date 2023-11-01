package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class Robot {
    public enum OpModes {
        AUTO,
        TELEOP
    }
    public Motor leftFront, rightFront, leftRear, rightRear, intakeMotor;
    public ServoEx coverServo1, coverServo2, depositServo1, depositServo2;
    public Camera camera;
    public BNO055IMU imu;
    public HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap, OpModes mode) {
        this.hardwareMap = hardwareMap;
        leftFront = new MotorEx(hardwareMap, "leftFront");
        leftFront.setRunMode(Motor.RunMode.RawPower);
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        rightFront = new MotorEx(hardwareMap,"rightFront");
        rightFront.setRunMode(Motor.RunMode.RawPower);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        leftRear = new MotorEx(hardwareMap,"leftRear");
        leftRear.setRunMode(Motor.RunMode.RawPower);
        leftRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        rightRear = new MotorEx(hardwareMap,"rightRear");
        rightRear.setRunMode(Motor.RunMode.RawPower);
        rightRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        /*intakeMotor = new MotorEx(hardwareMap, "intake");
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);*/

       /* double coverServoMinRot = 0.0;
        double coverServoMaxRot = 180.0;
        coverServo1 = new SimpleServo(hardwareMap, "cover1", coverServoMinRot, coverServoMaxRot);
        coverServo1.setInverted(false);
//        coverServo2 = new SimpleServo(hardwareMap, "cover2", coverServoMinRot, coverServoMaxRot);
//        coverServo2.setInverted(true);

        double depositServoMinRot = 0.0;
        double depositServoMaxRot = 0.0;
        depositServo1 = new SimpleServo(hardwareMap, "deposit1", depositServoMinRot, depositServoMaxRot);
        depositServo1.setInverted(false);
        depositServo2 = new SimpleServo(hardwareMap, "deposit2", depositServoMinRot, depositServoMaxRot);
        depositServo2.setInverted(true);
````````*/

        if (mode.equals(OpModes.AUTO)) {
            camera = new Camera(hardwareMap);
        }
    }
    public double getAngle() {
        return imu.getAngularOrientation().firstAngle;
    }
}
