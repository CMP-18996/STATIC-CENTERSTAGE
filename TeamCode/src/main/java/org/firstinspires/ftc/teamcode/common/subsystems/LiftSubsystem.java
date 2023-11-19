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
    private int upPower, downPower;
    private Encoder encoderUp, encoderDown;
    private Robot robot;
    private LiftHeight currentHeight;
    private Timing.Timer timer;
    private double proportionalConstant, integralConstant, derivativeConstant;
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
        BASE(0.0),
        HEIGHTONE(1.0),
        HEIGHTTWO(2.0),
        HEIGHTTHREE(3.0),
        HEIGHTFOUR(4.0),
        HEIGHTFIVE(55.0);

        private final double height;

        LiftHeight(double height) {
            this.height = height;
        }

        public double getHeight() {
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
        double error;
        error = height.getHeight() - this.getCurrentHeight().getHeight();

    }



    public void periodic() {

    }
}
