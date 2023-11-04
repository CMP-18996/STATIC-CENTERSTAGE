
package org.firstinspires.ftc.teamcode.opmode.teleop.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Current test")
public class MotorCurrent extends LinearOpMode {
    DcMotor leftFront, rightFront, leftRear, rightRear;
    @Override
    public void runOpMode() {
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");

        waitForStart(); // init blocker
        // random code 2
        while (opModeIsActive()) {
            telemetry.addData("Left front: ", leftFront);
            telemetry.addData("Right front: ", rightFront.getPower());
            telemetry.addData("Left rear: ", leftRear.getPower());
            telemetry.addData("Right rear: ", rightRear.getPower());
            telemetry.update();
        }
    }
}