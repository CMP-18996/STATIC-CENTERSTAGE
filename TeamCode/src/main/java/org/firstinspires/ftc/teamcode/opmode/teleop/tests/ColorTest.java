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
                if(425>= colorSensor.red() && colorSensor.red() >= 345){
                    if(607>= colorSensor.blue() && colorSensor.blue() >= 527){
                        if(685>= colorSensor.green() && colorSensor.green() >= 605){
                            telemetry.addLine("white");
                            telemetry.update();
                        }
                    }
                }
                else if(150>= colorSensor.red() && colorSensor.red() >= 70){
                    if(193>= colorSensor.blue() && colorSensor.blue() >= 113){
                        if(299>= colorSensor.green() && colorSensor.green() >= 219){
                            telemetry.addLine("green");
                            telemetry.update();
                        }
                    }
                }
                else if(275>= colorSensor.red() && colorSensor.red() >= 195){
                    if(199>= colorSensor.blue() && colorSensor.blue() >= 119){
                        if(383>= colorSensor.green() && colorSensor.green() >= 303){
                            telemetry.addLine("yellow");
                            telemetry.update();
                        }
                    }
                }
                else if(219>= colorSensor.red() && colorSensor.red() >= 159){
                    if(415>= colorSensor.blue() && colorSensor.blue() >= 335){
                        if(332>= colorSensor.green() && colorSensor.green() >= 252){
                            telemetry.addLine("purple");
                            telemetry.update();
                        }
                    }
                }
            }
        }

}
