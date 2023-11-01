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

                telemetry.addData("red", colorSensor.red());
                telemetry.addData("blue", colorSensor.blue());
                telemetry.addData("green", colorSensor.green());
                telemetry.update();
                if(405>= colorSensor.red() && colorSensor.red() >= 365){
                    if(587>= colorSensor.blue() && colorSensor.blue() >= 547){
                        if(665>= colorSensor.green() && colorSensor.green() >= 625){
                            telemetry.addLine("white");
                            telemetry.update();
                        }
                    }
                }
                else if(130>= colorSensor.red() && colorSensor.red() >= 90){
                    if(173>= colorSensor.blue() && colorSensor.blue() >= 133){
                        if(279>= colorSensor.green() && colorSensor.green() >= 239){
                            telemetry.addLine("green");
                            telemetry.update();
                        }
                    }
                }
                else if(255>= colorSensor.red() && colorSensor.red() >= 215){
                    if(179>= colorSensor.blue() && colorSensor.blue() >= 139){
                        if(363>= colorSensor.green() && colorSensor.green() >= 323){
                            telemetry.addLine("yellow");
                            telemetry.update();
                        }
                    }
                }
                else if(219>= colorSensor.red() && colorSensor.red() >= 179){
                    if(395>= colorSensor.blue() && colorSensor.blue() >= 355){
                        if(312>= colorSensor.green() && colorSensor.green() >= 272){
                            telemetry.addLine("purple");
                            telemetry.update();
                        }
                    }
                }
            }
        }

}
