package org.firstinspires.ftc.teamcode.common.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.util.Timing.Timer;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftSubsystem extends SubsystemBase{
    private double power = .4;
    private Encoder encoderUp, encoderDown;
    private Robot robot;
    private LiftHeight currentHeight;
    private double proportionalConstant, integralConstant, derivativeConstant;
    private Timing.Timer timer;
    /* Honestly at this point we should get rid of this stuff
    private double baseHeight, heightLevelOne, heightLevelTwo, heightLevelThree, heightLevelFour, heightLevelFive;
    // May or may not need the height detector, could use potentiometer or encoding
    private DistanceSensor heightDetector;
     */

    LiftSubsystem(Robot robot) {
        this.robot = robot;
    }

    public enum LiftHeight {
        // Change the values for the actual robot, otherwise it'll probably crash
        // At least six states
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
        PICKUPHEIGHT(120);

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

    public void updateStatePID(LiftHeight height) {
        int error;
        error = height.getHeight() - this.getCurrentHeight().getHeight();
        robot.liftOne.setVeloCoefficients(0.7, 0.2, 0.5);
        robot.liftTwo.setVeloCoefficients(0.7, 0.2, 0.5);
        robot.liftOne.setTargetPosition(error);
        robot.liftTwo.setTargetPosition(error);
        robot.liftOne.setRunMode(Motor.RunMode.PositionControl);
        robot.liftTwo.setRunMode(Motor.RunMode.PositionControl);
        robot.liftOne.set(0.4);
        robot.liftTwo.set(0.4);
    }

    public void resetPid() {
        robot.liftOne.setVeloCoefficients(1, 0, 0);
        robot.liftTwo.setVeloCoefficients(1, 0, 0);
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


