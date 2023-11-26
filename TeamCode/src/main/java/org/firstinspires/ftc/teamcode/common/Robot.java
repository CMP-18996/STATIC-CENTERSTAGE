package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class Robot {
    public Motor leftFront, rightFront, leftRear, rightRear, intakeMotor, xAdj, liftOne, liftTwo;
    public ServoEx coverServo, fourBar, leftGrabber, rightGrabber, depositExpansion,
            depositRotator, droneServo, hangServo1, hangServo2;
    public Camera camera;
    public HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap) {
        if (GlobalVariables.opMode.equals(GlobalVariables.OpMode.AUTO)) {
            camera = new Camera(hardwareMap);
        }
        // drivetrain
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

        liftOne = new Motor(hardwareMap, "liftOne");
        liftOne.setRunMode(Motor.RunMode.PositionControl);
        liftOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        liftTwo = new Motor(hardwareMap, "liftTwo");
        liftTwo.setInverted(true);
        liftTwo.setRunMode(Motor.RunMode.PositionControl);
        liftTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        intakeMotor = new MotorEx(hardwareMap, "intake");
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        // intake
        double coverServoMinRot = 0.0;
        double coverServoMaxRot = 180.0;
        coverServo = new SimpleServo(hardwareMap, "cover1", coverServoMinRot, coverServoMaxRot);
        coverServo.setInverted(false);
//        coverServo2 = new SimpleServo(hardwareMap, "cover2", coverServoMinRot, coverServoMaxRot);
//        coverServo2.setInverted(true);

        // Deposit
        xAdj = new MotorEx(hardwareMap, "xAdj");
        xAdj.setRunMode(Motor.RunMode.PositionControl);
        xAdj.setPositionTolerance(10.0);
        
        xAdj.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        double fourBarMinRot = 0.0;
        double fourBarMaxRot = 180.0;
        fourBar = new SimpleServo(hardwareMap, "fourBar", fourBarMinRot, fourBarMaxRot);

        double depositMinRot = 0.0;
        double depositMaxRot = 180.0;
        rightGrabber = new SimpleServo(hardwareMap, "rightGrabber", depositMinRot, depositMaxRot);
        leftGrabber = new SimpleServo(hardwareMap, "leftGrabber", depositMinRot, depositMaxRot);

        double channelMinRot = 0.0;
        double channelMaxRot = 180.0;
        depositExpansion = new SimpleServo(hardwareMap, "channel", channelMinRot, channelMaxRot);

        double depositRotatorMinRot = 0.0;
        double depositRotatorMaxRot = 180.0;
        depositRotator = new SimpleServo(hardwareMap, "depositRotator", depositRotatorMinRot, depositRotatorMaxRot);

        double droneServoMinRot = 0.0;
        double droneServoMaxRot = 90.0;
        droneServo = new SimpleServo(hardwareMap, "drone", droneServoMinRot, droneServoMaxRot);

        double hangServoMinRot = 0.0;
        double handServoMaxRot = 10.0;
        hangServo1 = new SimpleServo(hardwareMap, "hangServo1", hangServoMinRot, handServoMaxRot);
        hangServo2 = new SimpleServo(hardwareMap, "hangServo2", hangServoMinRot, handServoMaxRot);
    }
}
