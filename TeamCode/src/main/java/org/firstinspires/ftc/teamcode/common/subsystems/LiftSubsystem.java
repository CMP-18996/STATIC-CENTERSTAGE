package org.firstinspires.ftc.teamcode.common.subsystems;

import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.util.Range;

@Config
public class LiftSubsystem extends SubsystemBase {
    public static double power = .4;
    public Robot robot;
    private LiftHeight currentHeight;
    public int error;
    public static int LIFT_HEIGHT_INCREMENT = 300;
    public static int LIFT_PICKUP_HEIGHT = 100;
    public static double F = 0.07;
    public static double P = 0.005;
    public static double maxDesc = 0.5;
    public boolean controlLift = true;
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
        GROUND(-500),
        ZERO(0),
        BASE(30),
        HEIGHTONE(100),
        HEIGHTTWO(300),
        HEIGHTTHREE(500),
        HEIGHTFOUR(700),
        HEIGHTFIVE(900),
        HEIGHTSIX(1100),
        HEIGHTSEVEN(1300),
        HEIGHTEIGHT(1500),
        HEIGHTNINE(1700),
        HEIGHTTEN(1800),
        PICKUPHEIGHT(43);

        public final int target;


        LiftHeight(int height) {
            this.target = height;
        }

    }

    public LiftHeight getCurrentHeight() {
        return currentHeight;
    }

    // do not use outside of command - probably bad idea
    public void updateState(LiftHeight height) {
        currentHeight = height;
    }


    @Override
    public void periodic() {
        if (controlLift) {
            error = this.currentHeight.target - robot.liftOne.getCurrentPosition();

            double power = Range.clip(P * error + F * (error / Math.max(abs(error), 0.01)), -maxDesc, .8); // CONSIDER REMOVING * (error / Math.max(abs(error), 0.01))
            robot.liftOne.setPower(power);
            robot.liftTwo.setPower(power);
        }
    }
    public boolean checkDone(LiftHeight height) {
        return abs(height.target - this.getCurrentHeight().target) < 15;
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

    public boolean motorsFinished() {
        return robot.liftOne.isBusy();
    }

    public boolean abovePosition(int position) {
        return robot.liftOne.getCurrentPosition() >= position;
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


