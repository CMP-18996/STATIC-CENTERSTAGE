package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.Robot;

public class DepositSubsystem extends SubsystemBase {
    Robot robot;
    final static double incrementVal = 100;
    final static double centerVal = 300;
    //ParityState parityState = ParityState.LOWER;
    LowerHorizontalState lowerHorizontalState = LowerHorizontalState.C;
    UpperHorizontalState upperHorizontalState = UpperHorizontalState.C;
    FourBarState fourBarState = FourBarState.DOWN;
    GrabberState rightGrabberState = GrabberState.CLOSED;
    GrabberState leftGrabberState = GrabberState.CLOSED;
    ExpandedState expandedState = ExpandedState.NOT_EXPANDED;
    DepositRotationState depositRotationState = DepositRotationState.PARALLEL;


    public void setLowerHorizontalState(LowerHorizontalState state) {
        robot.xAdj.setTargetDistance(lowerHorizontalState.value - state.value);
        lowerHorizontalState = state;
    }

    public boolean horizontalFinishedMoving() {
        return robot.xAdj.atTargetPosition();
    }

    public void setUpperHorizontalState(UpperHorizontalState state) {
        robot.xAdj.setTargetDistance(upperHorizontalState.value - state.value);
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
        PICKING_UP(20.0),
        PARALLEL(30.0),
        DROPPING_GROUND(40.0);
        public double value;
        DepositRotationState(double val) { value = val; }
    }

    public enum GrabberState {
        OPEN(40.0),
        CLOSED(0.0);
        public double value;
        GrabberState(double val) { value = val; }
    }
    public enum GrabberPos {
        RIGHT,
        LEFT
    }

    public enum ExpandedState {
        EXPANDED_STATE(20.0),
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
        STASIS(20.0),
        HIGH(50.0),
        LOW(80.0);

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
