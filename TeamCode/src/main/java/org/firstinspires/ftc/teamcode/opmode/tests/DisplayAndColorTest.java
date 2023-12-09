package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.I2Cdisplay.HT16K33;

@TeleOp
public class DisplayAndColorTest extends CommandOpMode {
    HT16K33 display;
    ColorSensor colorSensor;
    @Override
    public void initialize() {
        display = hardwareMap.get(HT16K33.class, "display");
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
    }

    @Override
    public void run() {
        display.init();
        display.writeCharacter(HT16K33.DeviceNumber.ONE, new byte[] { (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF });
        sleep(10);
        telemetry.addData("Red Detected", colorSensor.red());
        telemetry.addData("Green Detected", colorSensor.green());
        telemetry.addData("Blue Detected", colorSensor.blue());
        telemetry.update();
    }
}
