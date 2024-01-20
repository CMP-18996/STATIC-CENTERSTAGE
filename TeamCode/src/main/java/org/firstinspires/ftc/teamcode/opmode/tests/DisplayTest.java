package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;

@TeleOp
public class DisplayTest extends CommandOpMode {
    HT16K33 display;
    ColorSensor colorSensor;

    @Override
    public void initialize() {
        display = hardwareMap.get(HT16K33.class, "display");
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
    }

    @Override
    public void run() {
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.EMPTY);
        sleep(3000);
        display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.PLAY);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.ASTERISK);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.DASH);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.UP_ARROW);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.DOWN_ARROW);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.RIGHT_ARROW);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.LEFT_ARROW);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.PAUSE);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.HAPPY);
        sleep(1500);
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.SAD);
        sleep(1500);
    }
}
