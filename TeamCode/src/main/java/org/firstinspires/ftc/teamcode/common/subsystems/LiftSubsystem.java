package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.arcrobotics.ftclib.controller.PIDFController;

public class LiftSubsystem extends SubsystemBase {
    private double power = .4;
    private Encoder encoderUp, encoderDown;
    private Robot robot;
    private LiftHeight currentHeight;
    private double proportionalConstant, integralConstant, derivativeConstant;
    private PIDFController pidfController = new PIDFController(0.7, 0.2, 0.5, 0);
    /* Honestly at this point we should get rid of this stuff
    private double baseHeight, heightLevelOne, heightLevelTwo, heightLevelThree, heightLevelFour, heightLevelFive;
    // May or may not need the height detector, could use potentiometer or encoding
    private DistanceSensor heightDetector;
     */

    public LiftSubsystem(Robot robot) {
        this.robot = robot;
    }

    public enum LiftHeight {
        // Change the values for the actual robot, otherwise it'll probably crash
        // At least six states
        // Probably can only use up to eight total actual heights
        BASE(0),
        HEIGHTONE(10),
        HEIGHTTWO(20),
        HEIGHTTHREE(30),
        HEIGHTFOUR(40),
        HEIGHTFIVE(50),
        HEIGHTSIX(60),
        HEIGHTSEVEN(70),
        HEIGHTEIGHT(80),
        HEIGHTNINE(90),
        HEIGHTTEN(100),
        HEIGHTELEVEN(110),
        PICKUPHEIGHT(5);

        private final int height;

        LiftHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }
    }

    public LiftHeight getCurrentHeight() {
        return currentHeight;
    }

    /* Set state
        // States are accurate
        // Make sure to move to state
         */
    public void updateState(LiftHeight height) {
        int error;
        error = height.getHeight() - this.getCurrentHeight().getHeight();
        robot.liftOne.setTargetPosition(error);
        robot.liftTwo.setTargetPosition(error);
        robot.liftOne.setRunMode(Motor.RunMode.PositionControl);
        robot.liftTwo.setRunMode(Motor.RunMode.PositionControl);
        robot.liftOne.set(power);
        robot.liftTwo.set(power);
    }

    @Deprecated
    public void updateStatePID(LiftHeight height) {
        int error;
        error = height.getHeight() - this.getCurrentHeight().getHeight();
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
        return robot.liftOne.atTargetPosition() && robot.liftTwo.atTargetPosition();
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


