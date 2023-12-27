package org.firstinspires.ftc.teamcode.common.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


import org.firstinspires.ftc.teamcode.common.Robot;
@Config
public class DepositSubsystem extends SubsystemBase {
    Robot robot;
    public static double incrementVal = 100;
    public static double centerVal = 300;
    public static double DEPOSIT_PICKING_UP_VALUE = 20.0;
    public static double DEPOSIT_PARALLEL_VALUE = 30.0;
    public static double DEPOSIT_DROPPING_OFF_VALUE = 40.0;
    public static double GRABBER_OPEN_VALUE = 40.0;
    public static double GRABBER_CLOSED_VALUE = 0.0;
    public static double EXPANDED_STATE_VALUE = 20.0;
    public static double FOUR_BAR_STASIS = 20.0;
    public static double FOUR_BAR_HIGH = 50.0;
    public static double FOUR_BAR_LOW = 80.0;

    //ParityState parityState = ParityState.LOWER;
    LowerHorizontalState lowerHorizontalState = LowerHorizontalState.C;
    UpperHorizontalState upperHorizontalState = UpperHorizontalState.C;
    FourBarState fourBarState = FourBarState.DOWN;
    GrabberState rightGrabberState = GrabberState.CLOSED;
    GrabberState leftGrabberState = GrabberState.CLOSED;
    ExpandedState expandedState = ExpandedState.NOT_EXPANDED;
    DepositRotationState depositRotationState = DepositRotationState.PARALLEL;
   

    public void setLowerHorizontalState(LowerHorizontalState state) {
        robot.xAdj.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.xAdj.setTargetPosition((int)lowerHorizontalState.value - (int)state.value);
        robot.xAdj.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.xAdj.setMotorEnable();
        robot.xAdj.setPower(0.4);
        lowerHorizontalState = state;
    }

    public boolean horizontalFinishedMoving() {
        return robot.xAdj.isBusy();
    }

    public void setUpperHorizontalState(UpperHorizontalState state) {
        robot.xAdj.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.xAdj.setTargetPosition((int)upperHorizontalState.value - (int)state.value);
        robot.xAdj.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.xAdj.setMotorEnable();
        robot.xAdj.setPower(0.4);
        upperHorizontalState = state;
    }

    public void setFourBarState(FourBarState state) {
        robot.fourBar.setPosition(state.value);
        fourBarState = state;
    }

    public void setGrabberState(GrabberState grabberState, GrabberPos grabberPos) {
        switch (grabberPos) {
            case LEFT:
                leftGrabberState = grabberState;
                robot.leftGrabber.setPosition(leftGrabberState.value);
                break;
            case RIGHT:
                rightGrabberState = grabberState;
                robot.rightGrabber.setPosition(rightGrabberState.value);
                break;
        }
    }
    public FourBarState getFourBarState() {
        return fourBarState;
    }

    public UpperHorizontalState getUpperHorizontalState() {
        return upperHorizontalState;
    }

    public LowerHorizontalState getLowerHorizontalState() {
        return lowerHorizontalState;
    }

    public void setExpandedState(ExpandedState expandedState) {
        this.expandedState = expandedState;
        robot.depositExpansion.setPosition(expandedState.value);
    }

    public void setDepositRotationState(DepositRotationState depositRotationState) {
        robot.depositRotator.setPosition(this.depositRotationState.value - depositRotationState.value);
        this.depositRotationState = depositRotationState;
    }

    public DepositSubsystem(Robot robot) {
        this.robot = robot;
    }


    public enum DepositRotationState {
        PICKING_UP(DEPOSIT_PICKING_UP_VALUE),
        PARALLEL(DEPOSIT_PARALLEL_VALUE),
        DROPPING_GROUND(DEPOSIT_DROPPING_OFF_VALUE);
        public double value;
        DepositRotationState(double val) { value = val; }
    }

    public enum GrabberState {
        OPEN(GRABBER_OPEN_VALUE),
        CLOSED(GRABBER_CLOSED_VALUE);
        public double value;
        GrabberState(double val) { value = val; }
    }
    public enum GrabberPos {
        RIGHT,
        LEFT
    }

    public enum ExpandedState {
        EXPANDED_STATE(EXPANDED_STATE_VALUE),
        NOT_EXPANDED(0.0);
        public double value;
        ExpandedState(double val) { value = val; }
    }
    public enum UpperHorizontalState {
        A(centerVal - 3 * incrementVal),
        B(centerVal - 2 * incrementVal),
        C(centerVal - 1 * incrementVal),
        D(centerVal),
        E(centerVal + 1 * incrementVal),
        F(centerVal + 2 * incrementVal),
        G(centerVal + 3);
        public double value;
        UpperHorizontalState(double val) {
            value = val;
        }
    }

    public enum LowerHorizontalState {
        A(centerVal - 2.5 * incrementVal),
        B(centerVal - 1.5 * incrementVal),
        C(centerVal - .5 * incrementVal),
        D(centerVal + .5 * incrementVal),
        E(centerVal + 1.5 * incrementVal),
        F(centerVal + 2.5 * incrementVal);

        public double value;
        LowerHorizontalState(double val) {
            value = val;
        }
    }

    public enum FourBarState {
        DOWN(0.0),
        STASIS(FOUR_BAR_STASIS),
        HIGH(FOUR_BAR_HIGH),
        LOW(FOUR_BAR_LOW);

        public double value;
        FourBarState(double val) { value = val; }
    }

    /*
    Add back if necessary
    public enum ParityState {

        LOWER,
        UPPER,
    }

    public void setParity(ParityState parityState) {
        this.parityState = parityState;
    }
    */
}
