package org.firstinspires.ftc.teamcode.common.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;


import org.firstinspires.ftc.teamcode.common.Robot;
@Config
public class DepositSubsystem extends SubsystemBase {
    Robot robot;
    public static double incrementVal = 0.067;
    // Proposed centerVals, 0.8325 from indexing by eye, 0.82875 from averaging range
    // 0.830625 from averaging them
    public static double centerVal = 0.830625;
    // end position on the right is 0.6575, bar ranges from 0.6575-1 on set position
    public static double DEPOSIT_PICKING_UP_VALUE = .4725;
    public static double DEPOSIT_PARALLEL_VALUE = .37;
    public static double DEPOSIT_PICKING_UP_VALUE_ADDED = .4855;
    public static double DEPOSIT_DROPPING_OFF_VALUE = .2; // TODO: FIND THIS VALUE
    public static double GRABBER_OPEN_VALUE = 0.75;
    public static double GRABBER_CLOSED_VALUE = 0.25;
    public static double EXPANDED_STATE_VALUE = 20.0; // TODO: CONSIDER ADDING THIS
    public static double FOUR_BAR_STASIS = .245;
    public static double FOUR_BAR_HIGH = .71;
    public static double FOUR_BAR_HIGH_DROP = .71;
    public static double FOUR_BAR_LOW = .9;
    public static double PICK_UP_HEIGHT = 0.1;
    public static double PICK_UP_HEIGHT_ADDED = 0.08;

    //ParityState parityState = ParityState.LOWER;
    LowerHorizontalState lowerHorizontalState = LowerHorizontalState.C;
    UpperHorizontalState upperHorizontalState = UpperHorizontalState.C;
    FourBarState fourBarState = FourBarState.PICKUP;
    GrabberState rightGrabberState = GrabberState.CLOSED;
    GrabberState leftGrabberState = GrabberState.CLOSED;
    ExpandedState expandedState = ExpandedState.NOT_EXPANDED;
    DepositRotationState depositRotationState = DepositRotationState.PARALLEL;
   

    public void setLowerHorizontalState(LowerHorizontalState state) {
        /*
        robot.xAdj.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.xAdj.setTargetPosition((int)lowerHorizontalState.value - (int)state.value);
        robot.xAdj.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.xAdj.setMotorEnable();
        robot.xAdj.setPower(0.4);
         */
        robot.xAdj.setPosition(state.value);
        lowerHorizontalState = state;
    }

    @Deprecated // Why? Because the f****** motor got changed from dc to a servo
    public boolean horizontalFinishedMoving() {
        return true;
    }

    public void setUpperHorizontalState(UpperHorizontalState state) {
        /*
        robot.xAdj.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.xAdj.setTargetPosition((int)upperHorizontalState.value - (int)state.value);
        robot.xAdj.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.xAdj.setMotorEnable();
        robot.xAdj.setPower(0.4);
         */
        robot.xAdj.setPosition(state.value);
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
        robot.depositRotator.setPosition(depositRotationState.value);
        this.depositRotationState = depositRotationState;
    }

    public DepositSubsystem(Robot robot) {
        this.robot = robot;
    }


    public enum DepositRotationState {
        PICKING_UP(DEPOSIT_PICKING_UP_VALUE),
        PICKING_UP_ADDED(DEPOSIT_PICKING_UP_VALUE_ADDED),
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
        A(centerVal + 2.5 * incrementVal),
        B(centerVal + 1.5 * incrementVal),
        C(centerVal + 0.5 * incrementVal),
        D(centerVal - 0.5 * incrementVal),
        E(centerVal - 1.5 * incrementVal),
        F(centerVal - 2.5 * incrementVal);

        public double value;
        UpperHorizontalState(double val) {
            value = val;
        }
    }

    public enum LowerHorizontalState {
        /*A(centerVal - 2.5 * incrementVal),
        B(centerVal - 1.5 * incrementVal),
        C(centerVal - .5 * incrementVal),
        D(centerVal + .5 * incrementVal),
        E(centerVal + 1.5 * incrementVal),
        F(centerVal + 2.5 * incrementVal);*/
        A(centerVal + 2 * incrementVal),
        B(centerVal + incrementVal),
        C(centerVal),
        D(centerVal - incrementVal),
        E(centerVal - 2 * incrementVal);

        public double value;
        LowerHorizontalState(double val) {
            value = val;
        }
    }

    public enum FourBarState {
        PICKUP(PICK_UP_HEIGHT),
        PICKUP_ADDED(PICK_UP_HEIGHT_ADDED),
        STASIS(FOUR_BAR_STASIS),
        HIGH(FOUR_BAR_HIGH),
        HIGHDROP(FOUR_BAR_HIGH_DROP),
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
