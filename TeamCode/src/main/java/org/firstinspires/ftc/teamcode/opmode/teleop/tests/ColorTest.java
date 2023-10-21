package org.firstinspires.ftc.teamcode.opmode.teleop.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ColorTest extends LinearOpMode {
        // Define a variable for our color sensor
        ColorSensor colorSensor;

        @Override
        public void runOpMode() {
            // Get the color sensor from hardwareMap
            colorSensor = hardwareMap.get(ColorSensor.class, "Color");
            colorSensor.enableLed(false);
            // Wait for the Play button to be pressed
            waitForStart();

            // While the Op Mode is running, update the telemetry values.
            while (opModeIsActive()) {

                telemetry.addData("red", colorSensor.red());
                telemetry.addData("blue", colorSensor.blue());
                telemetry.addData("green", colorSensor.green());
                telemetry.update();
            }
        }

}
