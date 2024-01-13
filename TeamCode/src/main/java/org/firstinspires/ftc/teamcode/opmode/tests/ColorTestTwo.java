package org.firstinspires.ftc.teamcode.opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.Robot;

@Disabled
@TeleOp
public class ColorTestTwo extends LinearOpMode {
    ColorSensor colorSensor1;
    Robot robot;
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            telemetry.addData("Red", colorSensor1.red());
            telemetry.addData("Green", colorSensor1.green());
            telemetry.addData("Blue", colorSensor1.blue());
            telemetry.update();
        }
    }
}
