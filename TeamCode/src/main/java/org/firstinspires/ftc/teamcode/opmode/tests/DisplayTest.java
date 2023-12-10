package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.I2Cdisplay.HT16K33;

@TeleOp
public class DisplayTest extends CommandOpMode {
    HT16K33 display;
    ColorSensor colorSensor;
    @Override
    public void initialize() {
        display = hardwareMap.get(HT16K33.class, "display");
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
        display.init();
    }

    @Override
    public void run() {
        display.turnOff();
        sleep(100);
        display.testWriteChar();
        display.turnOn();
        sleep(100);
        telemetry.addData("Red Detected", (byte) 0x1F);
        telemetry.update();
    }
}
