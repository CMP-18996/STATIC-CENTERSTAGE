package org.firstinspires.ftc.teamcode.common.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftSubsystem extends SubsystemBase{
    private Motor motorUp, motorDown;
    private int upPower, downPower;
    private double baseHeight, heightLevelOne, heightLevelTwo, heightLevelThree, heightLevelFour, heightLevelFive;
    // May or may not need the height detector, could use potentiometer or encoding
    private DistanceSensor heightDetector;
    private Robot robot;
    private LiftHeight currentHeight;

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

        private double height;

        LiftHeight(double height) {
            this.height = height;
        }
    }
    // Set state
    // States are accurate
    // Make sure to move to state

    public void updateState(LiftHeight height) {
        currentHeight = height;
    }

    public void periodic() {

    }
}
