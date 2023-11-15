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
    public enum UpperHorizontalState {
        A(centerVal - 2.5 * incrementVal),
        B(centerVal - 1.5 * incrementVal),
        C(centerVal - .5 * incrementVal),
        D(centerVal + .5 * incrementVal),
        E(centerVal + 1.5 * incrementVal),
        F(centerVal + 2.5 * incrementVal);
        public double value;
        UpperHorizontalState(double val) {
            value = val;
        }
    }

    public enum LowerHorizontalState {
        A(centerVal - 2 * incrementVal),
        B(centerVal - 1 * incrementVal),
        C(centerVal),
        D(centerVal + 1 * incrementVal),
        E(centerVal + 2 * incrementVal);
        public double value;
        LowerHorizontalState(double val) {
            value = val;
        }
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

    public void setLowerHorizontalState(LowerHorizontalState state) {
        robot.xAdj.setTargetDistance(lowerHorizontalState.value - state.value);
        lowerHorizontalState = state;
    }

    public void setUpperHorizontalState(UpperHorizontalState state) {
        robot.xAdj.setTargetDistance(upperHorizontalState.value - state.value);
        upperHorizontalState = state;
    }

    public DepositSubsystem(Robot robot) {
        this.robot = robot;
    }

}
