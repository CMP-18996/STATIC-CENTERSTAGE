package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
@Disabled

@TeleOp
public class DisplayTest extends CommandOpMode {
    HT16K33 display;
    @Override
    public void initialize() {
        display = hardwareMap.get(HT16K33.class, "display");
        telemetry = FtcDashboard.getInstance().getTelemetry();
    }

    @Override
    public void run() {
        telemetry.addLine("I am working");
        telemetry.addData("Address", display.getCurrentAddress().get8Bit());
        telemetry.update();
        //display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.CHAR_A);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.PLAY);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.THREE, HT16K33.AvailableCharacters.ASTERISK);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.FOUR, HT16K33.AvailableCharacters.DASH);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.FIVE, HT16K33.AvailableCharacters.UP_ARROW);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.DOWN_ARROW);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress());
        telemetry.update();
        //display.writeCharacter(HT16K33.DeviceNumber.THREE, HT16K33.AvailableCharacters.RIGHT_ARROW);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress().hashCode());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.FOUR, HT16K33.AvailableCharacters.LEFT_ARROW);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress().get7Bit());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.PAUSE);
        sleep(1000);
        telemetry.addData("Address", display.getCurrentAddress().get8Bit());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.FIVE, HT16K33.AvailableCharacters.HAPPY);
        sleep(1000);
        telemetry.addData("Address:", display.getCurrentAddress().toString());
        telemetry.update();
        display.writeCharacter(HT16K33.DeviceNumber.THREE, HT16K33.AvailableCharacters.SAD);
        sleep(1000);
        telemetry.addData("Address:", display.getCurrentAddress().toString());
        telemetry.update();
    }
}