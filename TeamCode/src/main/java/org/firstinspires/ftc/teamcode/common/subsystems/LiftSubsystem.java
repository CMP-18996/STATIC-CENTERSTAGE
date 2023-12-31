package org.firstinspires.ftc.teamcode.common.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.util.Range;

@Config
public class LiftSubsystem extends SubsystemBase {
    public static double power = .4;
    private Robot robot;
    private LiftHeight currentHeight;
    public int error;
    private double proportionalConstant, integralConstant, derivativeConstant;
    public static int LIFT_HEIGHT_INCREMENT = 300;
    public static int LIFT_PICKUP_HEIGHT = 100;
    public static double F = 0.05;
    public static double P = 0.005;
    public static double maxDesc = 0.5;
    private PIDFController pidfController = new PIDFController(0.7, 0.2, 0.5, 0);
    /* Honestly at this point we should get rid of this stuff
    private double baseHeight, heightLevelOne, heightLevelTwo, heightLevelThree, heightLevelFour, heightLevelFive;
    // May or may not need the height detector, could use potentiometer or encoding
    private DistanceSensor heightDetector;
     */

    public LiftSubsystem(Robot robot) {
        this.robot = robot;
        currentHeight = LiftHeight.BASE;
    }

    public enum LiftHeight {
        // Change the values for the actual robot, otherwise it'll probably crash
        // At least six states
        // Probably can only use up to eight total actual heights
        BASE(30),
        HEIGHTONE(300),
        HEIGHTTWO(600),
        HEIGHTTHREE(900),
        HEIGHTFOUR(1200),
        HEIGHTFIVE(1500),
        HEIGHTSIX(1800),
        HEIGHTSEVEN(2100),
        HEIGHTEIGHT(2400),
        HEIGHTNINE(2700),
        HEIGHTTEN(3000),
        HEIGHTELEVEN(3300),
        PICKUPHEIGHT(100);

        public final int value;


        LiftHeight(int height) {
            this.value = height;
        }

    }

    public LiftHeight getCurrentHeight() {
        return currentHeight;
    }

    // do not use outside of command - probably bad idea
    public void updateState(LiftHeight height) {
        // int error;
        error = height.value - this.getCurrentHeight().value;
        if (error > 10) {
            error = height.value - robot.liftOne.getCurrentPosition();

            double power = Range.clip(P * error + F, -maxDesc, 1);
            robot.liftOne.setPower(power);
            robot.liftTwo.setPower(power);
        } else {
            robot.liftOne.setPower(0);
            robot.liftTwo.setPower(0);
        }
        // update current height
        currentHeight = height;
    }

    @Deprecated
    public void updateStatePID(LiftHeight height) {
        /*
        robot.liftOne.setVeloCoefficients(0.7, 0.2, 0.5);
        robot.liftTwo.setVeloCoefficients(0.7, 0.2, 0.5);
        robot.liftOne.setTargetPosition(error);
        robot.liftTwo.setTargetPosition(error);
        robot.liftOne.setRunMode(Motor.RunMode.PositionControl);
        robot.liftTwo.setRunMode(Motor.RunMode.PositionControl);
        robot.liftOne.set(0.4);
        robot.liftTwo.set(0.4);
         */

    }

    @Deprecated
    public void resetPid() {
        pidfController.setPIDF(0.7, 0.2, 0.5, 0);
    }

    public boolean motorsFinished() {
        return robot.liftOne.getCurrentPosition() == error && robot.liftTwo.getCurrentPosition() == error;
    }

    @Deprecated
    public static LiftSubsystem.LiftHeight getHeightFromInt(int i) {
        switch(i) {
            case 1:
                return LiftHeight.HEIGHTONE;
            case 2:
                return LiftHeight.HEIGHTTWO;
            case 3:
                return LiftHeight.HEIGHTTHREE;
            case 4:
                return LiftHeight.HEIGHTFOUR;
            case 5:
                return LiftHeight.HEIGHTFIVE;
            case 6:
                return LiftHeight.HEIGHTSIX;
            case 7:
                return LiftHeight.HEIGHTSEVEN;
            case 8:
                return LiftHeight.HEIGHTEIGHT;
            case 9:
                return LiftHeight.HEIGHTNINE;
            case 10:
                return LiftHeight.HEIGHTTEN;
            default:
                return LiftHeight.PICKUPHEIGHT;
        }
    }
}


/*
doubles
kP = 0
kI = 0
kD = 0

input -> error
eP = error * kP * dt
eI = eI + error * kI * dt
eD = (error - prev error) * kD * dt
error * (eP + eI * dt + eD)


 */


