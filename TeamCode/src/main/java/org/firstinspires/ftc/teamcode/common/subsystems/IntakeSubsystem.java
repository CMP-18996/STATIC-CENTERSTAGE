package org.firstinspires.ftc.teamcode.common.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    private Robot robot;
    private SweepingState sweepingState = SweepingState.STOPPED;
    private double intakePower = .6;
    private double repelPower = .8; // keep positive
    private CoverState coverState = CoverState.CLOSED;
    private ColorState slotOne ;

    DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
    DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    DcMotor leftRear = hardwareMap.get(DcMotor.class, "leftRear");
    DcMotor rightRear = hardwareMap.get(DcMotor.class, "rightRear");

    IntakeWait intakeWait = new IntakeWait(leftFront, rightFront, leftRear, rightRear);

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
        NONE
    }

    public void updateSweepingState(SweepingState setState) {
        sweepingState = setState;
        switch (sweepingState) {
            case INTAKING:
                intakeWait.schedule();
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

    }

    public void periodic() {

    }
}
