package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadAndDisplaySubsystem;

@TeleOp(name="Touchpad test")
public class TouchpadTest extends LinearOpMode {
    Robot robot;
    TouchpadAndDisplaySubsystem touchpad;
    AdaDisplay display1, display2;
    @Override
    public void runOpMode() {
        display1 = hardwareMap.get(AdaDisplay.class, "display1");
        display1 = hardwareMap.get(AdaDisplay.class, "display2");
        touchpad = new TouchpadAndDisplaySubsystem(gamepad1, display1, display2);
        // touchpad.register();
        waitForStart();
        while (opModeIsActive()) {
             CommandScheduler.getInstance().run();
            // This works, issue fixed
            if (touchpad.getGamepad().left_bumper) {
                touchpad.rumble(500);
            }
            if (touchpad.getGamepad().b) {
                telemetry.addData("Button", " pressed");
            }
            else {
                telemetry.addData("Button", " not pressed");
            }

            if (touchpad.getGamepad().left_stick_button) {
                telemetry.addData("Left Stick", "pressed");
            }
            else {
                telemetry.addData("Left Stick", "not pressed");
            }

            if (touchpad.getGamepad().right_stick_button) {
                telemetry.addData("Right Stick", "pressed");
            }
            else {
                telemetry.addData("Right Stick", "not pressed");
            }

            // This doesn't work, issue fixed
            telemetry.addData("Telemetry", " works");
            telemetry.addData("History", touchpad.getHistory());
            telemetry.update();
        }
    }
}
