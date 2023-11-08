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
        ColorSensor colorSensor1;
        ColorSensor colorSensor2;
        int r1, b1, g1, r2, b2, g2;
        public String color1;
        public String color2;
        @Override
        public void runOpMode() {
            // Get the color sensor from hardwareMap
            colorSensor1 = hardwareMap.get(ColorSensor.class, "Color1");
            colorSensor2 = hardwareMap.get(ColorSensor.class, "Color2");
            colorSensor1.enableLed(false);
            colorSensor2.enableLed(false);
            // Wait for the Play button to be pressed
            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
            waitForStart();
            // While the Op Mode is running, update the telemetry values.
            while (opModeIsActive()) {
                r1 = colorSensor1.red();
                g1 = colorSensor1.green();
                b1 = colorSensor1.blue();

            }
            if(g1< 200 && b1< 200 && r1< 200){
                telemetry.addData("color1", "black");
                color1 = "black";
            }
                else if (g1 > b1 && b1 > r1) {
                    if (g1 > 400) {
                        telemetry.addData("color1", "white");
                        color1 = "white";
                    } else {
                        telemetry.addData("color1", "green");
                        color1 = "green";
                    }
                } else if (g1 > r1 && r1 > b1) {
                    telemetry.addData("color1", "yellow");
                color1 = "yellow";
                } else if (b1 > g1 && g1> r1) {
                    telemetry.addData("color1", "purple");
                color1 = "purple";
                }
                else {
                telemetry.addData("color1", "none");
                color1 = "none";

            }

            while (opModeIsActive()) {
                r2 = colorSensor2.red();
                g2 = colorSensor2.green();
                b2 = colorSensor2.blue();

            }
            if(g2< 200 && b2< 200 && r2< 200){
                telemetry.addData("color2", "black");
                color2 = "black";
            }
            else if (g2 > b2 && b2 > r2) {
                if (g2 > 400) {
                    telemetry.addData("color2", "white");
                    color2 = "white";
                } else {
                    telemetry.addData("color2", "green");
                    color2 = "green";
                }
            } else if (g2 > r2 && r2 > b2) {
                telemetry.addData("color2", "yellow");
                color2 = "yellow";
            } else if (b2 > g2 && g2> r2) {
                telemetry.addData("color2", "purple");
                color2 = "purple";
            }
            else {
                telemetry.addData("color2", "none");
                color2 = "none";

            }
                telemetry.update();
                sleep(20);


        }
}
