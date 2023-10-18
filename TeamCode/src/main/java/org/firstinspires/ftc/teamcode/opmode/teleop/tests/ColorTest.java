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

            // Wait for the Play button to be pressed
            waitForStart();

            // While the Op Mode is running, update the telemetry values.
            while (opModeIsActive()) {

                telemetry.addData("color sensed using pro sensing (I am god)", colorSensor.argb());
                telemetry.update();
            }
        }

}
