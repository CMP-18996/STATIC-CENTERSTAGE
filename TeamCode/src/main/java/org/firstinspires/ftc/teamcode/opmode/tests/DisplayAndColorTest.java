package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;

@TeleOp
public class DisplayAndColorTest extends CommandOpMode {
    AdaDisplay display;
    ColorSensor colorSensor;
    @Override
    public void initialize() {
        display = hardwareMap.get(AdaDisplay.class, "display");
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
    }

    @Override
    public void run() {
        display.testWriteChar();
        sleep(10);
        telemetry.addData("Red Detected", colorSensor.red());
        telemetry.addData("Green Detected", colorSensor.green());
        telemetry.addData("Blue Detected", (byte) 0x70);
        telemetry.addData("Blue Detected", 0x70);
        telemetry.update();
    }
}
