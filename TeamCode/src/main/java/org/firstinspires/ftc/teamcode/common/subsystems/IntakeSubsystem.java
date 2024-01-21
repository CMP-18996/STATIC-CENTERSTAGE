package org.firstinspires.ftc.teamcode.common.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;

@Config
public class IntakeSubsystem extends SubsystemBase {
    /*
    Two more servos for intake, apparently they control the bar in front
    Still need to set them up in robot class
     */
    private Robot robot;
    private SweepingState sweepingState = SweepingState.STOPPED;
    private CoverState coverState = CoverState.CLOSED;
    public ColorState slotOne = ColorState.NONE;
    public ColorState slotTwo = ColorState.NONE;
    private FrontBarState frontBarState = FrontBarState.GROUND;

    public static double OPEN_COVER_VAL = .0675;
    public static double CLOSED_COVER_VAL = .7775;

    // color1, color2, both strings
    // ColorSensor colorSensor1 = hardwareMap.get(ColorSensor.class, "Color1");
    // ColorSensor colorSensor2 = hardwareMap.get(ColorSensor.class, "Color2");

    public enum CoverState {
        OPEN(OPEN_COVER_VAL),
        CLOSED(CLOSED_COVER_VAL);
        public double value;
        CoverState(double val) { value = val; }
    }

    public enum SweepingState {
        INTAKING,
        STOPPED,
        REPELLING
    }

    public enum ColorState {
        WHITE("White"),
        GREEN("Green"),
        PURPLE("Purple"),
        YELLOW("Yellow"),
        BLACK("Black"),
        NONE("None");

        String name;
        ColorState(String name) {this.name = name;}
        @Override
        public String toString() {return this.name;}
    }

    public enum FrontBarState {
        // five heights for the stack
        GROUND(0.003, 0.9),
        LEVEL1(0.00875, 0.9),
        LEVEL2(0.0175, 0.9),
        LEVEL3(0.02625, 0.9),
        LEVEL4(0.035, 0.9);

        double barState;
        double intakeSpeed;
        FrontBarState(double barState, double intakeSpeed) {
            this.barState = barState;
            this.intakeSpeed = -intakeSpeed;
        }
        public double getBarHeight() {
            return this.barState;
        }
        public double getIntakeSpeed() {
            return this.intakeSpeed;
        }
    }

    public void updateSweepingState(SweepingState setState) {
        sweepingState = setState;
        switch (sweepingState) {
            case INTAKING:
                // robot.intakeMotor.set(intakePower);
                robot.intakeMotor.set(frontBarState.getIntakeSpeed());
                break;
            case STOPPED:
                robot.intakeMotor.set(0);
                break;
            case REPELLING:
                // robot.intakeMotor.set(-repelPower);
                robot.intakeMotor.set(-frontBarState.getIntakeSpeed());
                // CommandScheduler.getInstance().schedule(new SequentialCommandGroup(new IntakeWait(this)));
                break;
        }
    }

    public SweepingState getSweepingState() { return this.sweepingState; }

    public void updateCoverState(CoverState setState) {
        coverState = setState;

        switch(coverState) {
            case OPEN:
                robot.coverServo.setPosition(OPEN_COVER_VAL);
                break;
            case CLOSED:
                robot.coverServo.setPosition(CLOSED_COVER_VAL);
                break;
        }
    }

    public void identifyColor() {
        int r1, b1, g1, r2, b2, g2;



        r1 = robot.colorSensor1.red();
        g1 = robot.colorSensor1.green();
        b1 = robot.colorSensor1.blue();
        if (g1 < 200 && b1 < 200 && r1 < 200) {
            slotOne = ColorState.BLACK;
        } else if (g1 > b1 && b1 > r1) {
            if (b1 > 2500) {
                slotOne = ColorState.WHITE;
            } else {
                slotOne = ColorState.GREEN;
            }
        } else if (g1 > r1 && r1 > b1) {
            slotOne = ColorState.YELLOW;
        } else if (b1 > g1 && g1 > r1) {
            slotOne = ColorState.PURPLE;
        } else {
            slotOne = ColorState.NONE;
        }
        r2 = robot.colorSensor2.red();
        g2 = robot.colorSensor2.green();
        b2 = robot.colorSensor2.blue();
        if (g2 < 200 && b2 < 200 && r2 < 200) {
            slotTwo = ColorState.BLACK;
        } else if (g2 > b2 && b2 > r2) {
            if (b2 > 2000) {
            slotTwo = ColorState.WHITE;
            } else {
                slotTwo = ColorState.GREEN;
            }
        } else if (g2 > r2 && r2 > b2) {
            slotTwo = ColorState.YELLOW;
        } else if (b2 > g2 && g2 > r2) {
            slotTwo = ColorState.PURPLE;
        } else {
            slotTwo = ColorState.NONE;

        }

    }


    public boolean checkFilled() {
        identifyColor();
        boolean oneFilled;
        boolean twoFilled;
        switch (slotOne) {

            // this works as intended
            case NONE:
            case BLACK:
                oneFilled = false;
                break;
            default:
                oneFilled = true;
                break;
        }
        switch (slotTwo) {
            case NONE:
            case BLACK:
                twoFilled = false;
                break;
            default:
                twoFilled = true;
                break;
        }

        if (oneFilled && twoFilled)
            return true;
        else
            return false;
    }

    public boolean slotOneFilled(){
        boolean oneCheck;
        switch (slotOne) {

            // this works as intended
            case NONE:
            case BLACK:
                oneCheck = false;
                break;
            default:
                oneCheck = true;
                break;
        }
        return oneCheck;
    }
    public boolean slotTwoFilled(){
        boolean twoCheck;
        switch (slotTwo) {

            // this works as intended
            case NONE:
            case BLACK:
                twoCheck = false;
                break;
            default:
                twoCheck = true;
                break;
        }
        return twoCheck;
    }
    public void updateFrontBarState(FrontBarState frontBarState) {
        robot.frontBar1.setPosition(frontBarState.getBarHeight());
        robot.frontBar2.setPosition(frontBarState.getBarHeight());
        this.frontBarState = frontBarState;
    }


    public CoverState getCoverState() {
        return coverState;
    }
    public IntakeSubsystem(Robot robot) {
        this.robot = robot;
    }

    /*
    @Override
    public void periodic() {this.identifyColor();}
    */
}
