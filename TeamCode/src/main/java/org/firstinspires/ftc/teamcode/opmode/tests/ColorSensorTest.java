package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;


@TeleOp
public class ColorSensorTest extends CommandOpMode {
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;
    Robot robot;
    int r1, b1, g1, r2, b2, g2;
    @Override
    public void initialize() {
        colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");
        colorSensor2 = hardwareMap.get(ColorSensor.class, "color2");
    }

    @Override
    public void run() {

        r1 = robot.colorSensor1.red();
        g1 = robot.colorSensor1.green();
        b1 = robot.colorSensor1.blue();
        if (g1 < 200 && b1 < 200 && r1 < 200) {
            telemetry.addData("Color", "Black");
        } else if (g1 > b1 && b1 > r1) {
            if (g1 > 9000) {
                telemetry.addData("Color", "White");
            } else {
                telemetry.addData("Color", "Green");
            }
        } else if (g1 > r1 && r1 > b1) {
            telemetry.addData("Color", "Yellow");
        } else if (b1 > g1 && g1 > r1) {
            telemetry.addData("Color", "Purple");
        } else {
            telemetry.addData("Color", "NONE");
        }
        r2 = robot.colorSensor2.red();
        g2 = robot.colorSensor2.green();
        b2 = robot.colorSensor2.blue();
        if (g2 < 200 && b2 < 200 && r2 < 200) {
            telemetry.addData("Color", "Black");
        } else if (g2 > b2 && b2 > r2) {
            if (g2 >9000) {
                telemetry.addData("Color", "White");
            } else {
                telemetry.addData("Color", "Green");
            }
        } else if (g2 > r2 && r2 > b2) {
            telemetry.addData("Color", "Yellow");
        } else if (b2 > g2 && g2 > r2) {
            telemetry.addData("Color", "Purple");
        } else {
            telemetry.addData("Color", "NONE");

        }
        telemetry.update();
        sleep(20);
    }
}
