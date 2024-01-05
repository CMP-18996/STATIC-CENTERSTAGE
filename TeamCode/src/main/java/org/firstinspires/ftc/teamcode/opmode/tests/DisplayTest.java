package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.Drivers.AdaDisplay;

@TeleOp
public class DisplayTest extends CommandOpMode {
    AdaDisplay display;
    ColorSensor colorSensor;
    @Override
    public void initialize() {
        display = hardwareMap.get(AdaDisplay.class, "display");
        colorSensor = hardwareMap.get(ColorSensor.class, "color1");
    }

    @Override
    public void run() {
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.EMPTY);
        sleep(3000);
        display.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.PLAY);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.ASTERISK);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.DASH);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.UP_ARROW);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.DOWN_ARROW);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.RIGHT_ARROW);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.LEFT_ARROW);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.PAUSE);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.HAPPY);
        sleep(1500);
        display.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.SAD);
        sleep(1500);
    }
}
