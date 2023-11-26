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

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotorEx;


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
        /*ColorSensor colorSensor1;
        ColorSensor colorSensor2;
        DcMotorEx intakeMotor;
        int r1, b1, g1, r2, b2, g2;
        colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");
        colorSensor2 = hardwareMap.get(ColorSensor.class, "color2");
        colorSensor1.enableLed(false);
        colorSensor2.enableLed(false);

            r1 = colorSensor1.red();
            g1 = colorSensor1.green();
            b1 = colorSensor1.blue();
            if (g1 < 200 && b1 < 200 && r1 < 200) {
                slotOne = ColorState.BlACK;
            } else if (g1 > b1 && b1 > r1) {
                if (g1 > 9000) {
                    slotOne = ColorState.WHITE;
                } else {
                    telemetry.addData("color1", "green");
                    color1 = "GREEN";
                }
            } else if (g1 > r1 && r1 > b1) {
                telemetry.addData("color1", "yellow");
                color1 = "YELLOW";
            } else if (b1 > g1 && g1 > r1) {
                telemetry.addData("color1", "purple");
                color1 = "PURPLE";
            } else {
                telemetry.addData("color1", "none");
                color1 = "NONE";
            }
            r2 = colorSensor2.red();
            g2 = colorSensor2.green();
            b2 = colorSensor2.blue();
            if (g2 < 200 && b2 < 200 && r2 < 200) {
                telemetry.addData("color2", "black");
                color2 = "BLACK";
            } else if (g2 > b2 && b2 > r2) {
                if (g2 >9000) {
                    telemetry.addData("color2", "white");
                    color2 = "WHITE";
                } else {
                    telemetry.addData("color2", "green");
                    color2 = "GREEN";
                }
            } else if (g2 > r2 && r2 > b2) {
                telemetry.addData("color2", "yellow");
                color2 = "YELLOW";
            } else if (b2 > g2 && g2 > r2) {
                telemetry.addData("color2", "purple");
                color2 = "PURPLE";
            } else {
                telemetry.addData("color2", "none");
                color2 = "NONE";

            }
            telemetry.update();
            sleep(20);


        }*/
    }

    public boolean checkFilled() {
        return true;
    }

    public void periodic() {

    }

    public IntakeSubsystem(Robot robot) {
        this.robot = robot;
    }
}
