package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class Robot {
    public MotorEx leftFront, rightFront, leftRear, rightRear, intakeMotor;
    public DcMotorEx liftOne, liftTwo, xAdj;
    public DcMotorEx droneMotor;
    public ServoEx coverServo, fourBar, leftGrabber, rightGrabber, depositExpansion,
            depositRotator, droneServo, hangServo1, hangServo2;
    // Two new servos for intake, need to figure out where to use them
    public ServoEx frontBar1, frontBar2;
    public Camera camera;
    public ColorSensor colorSensor1, colorSensor2;
    public HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap) {

        camera = new Camera(hardwareMap);
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


        /*
        // Lift
        liftOne = hardwareMap.get(DcMotorEx.class, "liftOne");
//        liftOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        liftOne.setMotorEnable();
        liftOne.setTargetPositionTolerance(2);


        liftTwo = hardwareMap.get(DcMotorEx.class, "liftTwo");
//        liftTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        liftTwo.setMotorEnable();
        liftTwo.setTargetPositionTolerance(2);
//        liftTwo.setDirection(DcMotorSimple.Direction.REVERSE);
        */

        /*
        // Intake
        intakeMotor = new MotorEx(hardwareMap, "intake");
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        ColorSensor colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");
        ColorSensor colorSensor2 = hardwareMap.get(ColorSensor.class, "color2");
        colorSensor1.enableLed(false);
        colorSensor2.enableLed(false);


        double coverServoMinRot = 0.0;
        double coverServoMaxRot = 180.0;
        coverServo = new SimpleServo(hardwareMap, "cover1", coverServoMinRot, coverServoMaxRot);
        coverServo.setInverted(false);
//        coverServo2 = new SimpleServo(hardwareMap, "cover2", coverServoMinRot, coverServoMaxRot);
//        coverServo2.setInverted(true);

        // Deposit

        xAdj = hardwareMap.get(DcMotorEx.class, "xAdj");
        xAdj.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        xAdj.setTargetPositionTolerance(2);

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
        */
        /*
        droneMotor = hardwareMap.get(DcMotorEx.class, "droneMotor");
        droneMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        droneMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         */
    }
}
