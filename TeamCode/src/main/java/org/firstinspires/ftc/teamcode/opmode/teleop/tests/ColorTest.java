package org.firstinspires.ftc.teamcode.opmode.teleop.tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// move to function in intakesubsystem that updates color enum slot1 and slot2
// create a function in intakesubsystem that returns a true/false if both slots are occupied
// create a command that does nothing but ends when there are 2 pixels


@TeleOp
public class ColorTest extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;
    DcMotorEx intakeMotor;
    int r1, b1, g1, r2, b2, g2;
    public static String color1;
    public static String color2;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        colorSensor1 = hardwareMap.get(ColorSensor.class, "color1");
        colorSensor2 = hardwareMap.get(ColorSensor.class, "color2");
        // Use for intake test
        // intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        colorSensor1.enableLed(false);
        colorSensor2.enableLed(false);
        // Wait for the Play button to be pressed
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        // Use if you want to kill someone
        // intakeMotor.setPower(-0.8);
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            r1 = colorSensor1.red();
            g1 = colorSensor1.green();
            b1 = colorSensor1.blue();
            telemetry.addData("Red", colorSensor1.red());
            telemetry.addData("Green", colorSensor1.green());
            telemetry.addData("Blue", colorSensor1.blue());
            telemetry.addData("Red", colorSensor2.red());
            telemetry.addData("Green", colorSensor2.green());
            telemetry.addData("Blue", colorSensor2.blue());

            if (g1 < 200 && b1 < 200 && r1 < 200) {
                telemetry.addData("color1", "black");
                color1 = "BLACK";
            } else if (g1 > b1 && b1 > r1) {
                if (g1 > 9000) {
                    telemetry.addData("color1", "white");
                    color1 = "WHITE";
                } else {
                    telemetry.addData("color1", "green");
                    color1 = "GREEN";
                }
            } else if (g1 > r1 && r1 > b1) {
                telemetry.addData("color1", "yellow");
                color1 = "YELLOW";
            } else if (b1 > g1 && g1 > r1) {
                telemetry.addData("color1", "purple");
                color1 = "PURPLE";
            } else {
                telemetry.addData("color1", "none");
                color1 = "NONE";
            }


            r2 = colorSensor2.red();
            g2 = colorSensor2.green();
            b2 = colorSensor2.blue();

            telemetry.addData("red", r2);
            telemetry.addData("blue", b2);
            telemetry.addData("green", g2);

            if (g2 < 200 && b2 < 200 && r2 < 200) {
                telemetry.addData("color2", "black");
                color2 = "BLACK";
            } else if (g2 > b2 && b2 > r2) {
                if (g2 >9000) {
                    telemetry.addData("color2", "white");
                    color2 = "WHITE";
                } else {
                    telemetry.addData("color2", "green");
                    color2 = "GREEN";
                }
            } else if (g2 > r2 && r2 > b2) {
                telemetry.addData("color2", "yellow");
                color2 = "YELLOW";
            } else if (b2 > g2 && g2 > r2) {
                telemetry.addData("color2", "purple");
                color2 = "PURPLE";
            } else {
                telemetry.addData("color2", "none");
                color2 = "NONE";

            }
            telemetry.update();
            sleep(20);


        }

    }
}
