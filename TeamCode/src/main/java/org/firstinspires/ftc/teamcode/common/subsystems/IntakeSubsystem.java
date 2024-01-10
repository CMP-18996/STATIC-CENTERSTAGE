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
    public static double intakePower = .6;
    //different speeds for picking up from each pixel from the stack and on the ground
    public static double repelPower = .3; // keep positive, subject to change more
    private CoverState coverState = CoverState.CLOSED;
    public ColorState slotOne;
    public ColorState slotTwo;
    private FrontBarState frontBarState = FrontBarState.GROUND;

    public static double OPEN_COVER_VAL = 180.0;
    public static double CLOSED_COVER_VAL = 180.0;

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
        public String toString() {return name;}
    }

    public enum FrontBarState {
        // five heights for the stack
        GROUND(0, 1),
        LEVEL1(1, 2),
        LEVEL2(2, 3),
        LEVEL3(3, 4),
        LEVEL4(4, 5);

        double barState;
        double intakeSpeed;
        FrontBarState(double barState, double intakeSpeed) {
            this.barState = barState;
            this.intakeSpeed = intakeSpeed;
        }
        public double getBarHeight() {return barState;}
        public double getIntakeSpeed() {return intakeSpeed;}
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
        int openPosition = 0;
        int closedPosition = 0;

        switch(coverState) {
            case OPEN:
                robot.coverServo.setPosition(openPosition);
            case CLOSED:
                robot.coverServo.setPosition(closedPosition);
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
            if (g1 > 9000) {
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
            if (g2 >9000) {
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
        }
        switch (slotTwo) {
            case NONE:
            case BLACK:
                twoFilled = false;
                break;
            default:
                twoFilled = true;
        }

        if (oneFilled && twoFilled)
            return true;
        else
            return false;
    }

    public boolean slotOneColor(){
        boolean oneCheck;
        switch (slotOne) {

            // this works as intended
            case NONE:
            case BLACK:
                oneCheck = false;
                break;
            default:
                oneCheck = true;
        }
        return oneCheck;
    }
    public boolean slotTwoColor(){
        boolean twoCheck;
        switch (slotTwo) {

            // this works as intended
            case NONE:
            case BLACK:
                twoCheck = false;
                break;
            default:
                twoCheck = true;
        }
        return twoCheck;
    }
    public void updateFrontBarState(FrontBarState frontBarState) {
        robot.frontBar1.setPosition(frontBarState.getBarHeight());
        robot.frontBar2.setPosition(frontBarState.getBarHeight());
        this.frontBarState = frontBarState;
    }

    public boolean frontBarFinished() {
        return (robot.frontBar1.getPosition() == frontBarState.getBarHeight())
                && (robot.frontBar2.getPosition() == frontBarState.getBarHeight());
    }

    public IntakeSubsystem(Robot robot) {
        this.robot = robot;
    }
}
