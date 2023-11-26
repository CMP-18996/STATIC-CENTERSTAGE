package org.firstinspires.ftc.teamcode.common.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.opmode.teleop.tests.ColorTest.color1;
import static org.firstinspires.ftc.teamcode.opmode.teleop.tests.ColorTest.color2;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    private Robot robot;
    private SweepingState sweepingState = SweepingState.STOPPED;
    private double intakePower = .6;
    private double repelPower = .8; // keep positive
    private CoverState coverState = CoverState.CLOSED;
    public ColorState slotOne;
    public ColorState slotTwo;

    // color1, color2, both strings
    // ColorSensor colorSensor1 = hardwareMap.get(ColorSensor.class, "Color1");
    // ColorSensor colorSensor2 = hardwareMap.get(ColorSensor.class, "Color2");

    public enum CoverState {
        OPENED,
        OPENING,
        CLOSED
    }

    public enum SweepingState {
        INTAKING,
        STOPPED,
        REPELLING
    }

    public enum ColorState {
        WHITE,
        GREEN,
        PURPLE,
        YELLOW,
        BlACK,
        NONE;
    }

    public void updateSweepingState(SweepingState setState) {
        sweepingState = setState;
        switch (sweepingState) {
            case INTAKING:
                robot.intakeMotor.set(intakePower);
                break;
            case STOPPED:
                robot.intakeMotor.set(0);
                break;
            case REPELLING:
                robot.intakeMotor.set(-repelPower);
                break;
        }
    }

    public SweepingState getSweepingState() { return this.sweepingState; }

    public void updateCoverState(CoverState setState) {
        coverState = setState;
        int openPosition = 0;
        int closedPosition = 0;

        switch(coverState) {
            case OPENED:
                break;
            case OPENING:
                robot.coverServo1.setPosition(openPosition);
                break;
            case CLOSED:
                robot.coverServo1.setPosition(closedPosition);
                break;
        }
    }

    public void identifyColor() {
        if (!color1.equals("")) {
            slotOne = ColorState.valueOf(color1);
        }
        if (!color2.equals("")) {
            slotTwo = ColorState.valueOf(color2);
        }
    }

    public void periodic() {

    }

    public IntakeSubsystem(Robot robot) {
        this.robot = robot;
    }
}
