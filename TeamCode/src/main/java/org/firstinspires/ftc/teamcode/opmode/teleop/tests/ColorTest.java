package org.firstinspires.ftc.teamcode.opmode.teleop.tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.FtcDashboard;

@Config
@TeleOp
public class ColorTest extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor colorSensor;
    int r, b, g;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        colorSensor = hardwareMap.get(ColorSensor.class, "Color");
        colorSensor.enableLed(false);
        // Wait for the Play button to be pressed
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            r = colorSensor.red();
            g = colorSensor.green();
            b = colorSensor.blue();
            telemetry.addData("red", r);
            telemetry.addData("blue", b);
            telemetry.addData("green", g);
            telemetry.addData("color", "none");
            if (g > b && b > r) {
                if (g < 150) {
                    telemetry.addData("color", "black");
                } else if (g > 400) {
                    telemetry.addData("color", "white");
                } else {
                    telemetry.addData("color", "green");
                }
            } else if (g > r && r > b) {
                telemetry.addData("color", "yellow");
            } else if (b > g && g > r) {
                telemetry.addData("color", "purple");
            }
            telemetry.update();
        }
    }
}
