package org.firstinspires.ftc.teamcode.common.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.IntakeWait;

import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.ColorSensor;


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
        OPEN(180.0),
        CLOSED(0.0);
        public double value;
        CoverState(double val) { value = val; }
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
        BLACK,
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
//                CommandScheduler.getInstance().schedule(new SequentialCommandGroup(new IntakeWait(this)));
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

    public IntakeSubsystem(Robot robot) {
        this.robot = robot;
    }
}
