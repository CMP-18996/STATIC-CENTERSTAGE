package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;


@TeleOp
public class ColorSensorTest extends CommandOpMode {
    ColorSensor colorSensor;
    @Override
    public void initialize() {
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
    }

    @Override
    public void run() {
        telemetry.addData("Red Detected", colorSensor.red());
        telemetry.addData("Green Detected", colorSensor.green());
        telemetry.addData("Blue Detected", colorSensor.blue());
        telemetry.update();
        sleep(20);
    }
}
