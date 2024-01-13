package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class Robot {
    public MotorEx leftFront, rightFront, leftRear, rightRear, intakeMotor;
    // xAdj is now a servo
    public DcMotorEx liftOne, liftTwo;
    public DcMotorEx droneMotor;
    // Two servos for x axis, in a single port, so technically we only program one
    public ServoEx coverServo, fourBar, leftGrabber, rightGrabber, depositExpansion,
            depositRotator;
    public CRServo hangServo1, hangServo2;
    // Two new servos for intake, need to figure out where to use them
    public ServoEx frontBar1, frontBar2;
    public ServoEx xAdj;
    public Camera camera;
    public ColorSensor colorSensor1, colorSensor2;
    public HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap) {

        camera = new Camera(hardwareMap);
        // drivetrain
        this.hardwareMap = hardwareMap;


        // Drive
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



        // Lift
        liftOne = hardwareMap.get(DcMotorEx.class, "liftTwo"); // don't question this naming too much
        liftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftTwo = hardwareMap.get(DcMotorEx.class, "liftOne");
        liftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftTwo.setDirection(DcMotorSimple.Direction.REVERSE);



        // Intake
        intakeMotor = new MotorEx(hardwareMap, "intake");
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");
        colorSensor2 = hardwareMap.get(ColorSensor.class, "color2");
        colorSensor1.enableLed(false);
        colorSensor2.enableLed(false);


        double coverServoMinRot = 0.0;
        double coverServoMaxRot = 1800.0;
        coverServo = new SimpleServo(hardwareMap, "cover", coverServoMinRot, coverServoMaxRot);
        coverServo.setInverted(false);
//        coverServo2 = new SimpleServo(hardwareMap, "cover2", coverServoMinRot, coverServoMaxRot);
//        coverServo2.setInverted(true);


        double frontBarMinRot = 0.0;
        double frontBarMaxRot = 1800.0;
        frontBar1 = new SimpleServo(hardwareMap, "frontBar1", frontBarMinRot, frontBarMaxRot);
        frontBar1.setInverted(false);
        frontBar2 = new SimpleServo(hardwareMap, "frontBar2", frontBarMinRot, frontBarMaxRot);
        frontBar2.setInverted(true);


        // Deposit

        double xAdjMinRot = 0.0;
        double xAdjMaxRot = 1800.0;
        xAdj = new SimpleServo(hardwareMap, "xAdj", xAdjMinRot, xAdjMaxRot);


        double fourBarMinRot = 0.0;
        double fourBarMaxRot = 1800.0;
        fourBar = new SimpleServo(hardwareMap, "fourBar", fourBarMinRot, fourBarMaxRot);

        double depositMinRot = 0.0;
        double depositMaxRot = 1800.0;
        rightGrabber = new SimpleServo(hardwareMap, "rightGrabber", depositMinRot, depositMaxRot);
        leftGrabber = new SimpleServo(hardwareMap, "leftGrabber", depositMinRot, depositMaxRot);

        double channelMinRot = 0.0;
        double channelMaxRot = 1800.0;
        //depositExpansion = new SimpleServo(hardwareMap, "depositExpansion", channelMinRot, channelMaxRot);

        double depositRotatorMinRot = 0.0;
        double depositRotatorMaxRot = 1800.0;
        depositRotator = new SimpleServo(hardwareMap, "depositRotator", depositRotatorMinRot, depositRotatorMaxRot);

        // Not using droneServo anymore
        // double droneServoMinRot = 0.0;
        // double droneServoMaxRot = 1800.0;
        // droneServo = new SimpleServo(hardwareMap, "drone", droneServoMinRot, droneServoMaxRot);

        // double hangServoMinRot = 0.0;
        // double handServoMaxRot = 1800.0;
        // hangServo1 = new SimpleServo(hardwareMap, "hangServo1", hangServoMinRot, handServoMaxRot);
        // hangServo2 = new SimpleServo(hardwareMap, "hangServo2", hangServoMinRot, handServoMaxRot);
        hangServo1 = hardwareMap.get(CRServo.class, "hangServo1");
        hangServo2 = hardwareMap.get(CRServo.class, "hangServo2");


        /*
        droneMotor = hardwareMap.get(DcMotorEx.class, "droneMotor");
        droneMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        droneMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         */
    }
}
