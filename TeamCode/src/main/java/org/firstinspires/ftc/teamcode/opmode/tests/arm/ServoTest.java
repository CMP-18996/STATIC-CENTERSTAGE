package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Servo Test")
public class ServoTest extends LinearOpMode {
    Robot robot;
    private double currentPosition = 900;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        waitForStart();
        // setPosition 0 to 1
        robot.xAdj.setPosition(0.83);
        // robot.xAdj.turnToAngle(250);
        sleep(3000);

        robot.xAdj.setPosition(0.8);
        // robot.xAdj.turnToAngle(1550);
        sleep(3000);
        robot.xAdj.setPosition(0.75);
        // robot.xAdj.turnToAngle(900);

        telemetry.addData("Position", robot.xAdj.getAngle());
        telemetry.update();
        sleep(5000);
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                currentPosition += 0.025;
                // setPosition is percentage, five turn
                robot.xAdj.setPosition(currentPosition);
            }

            if (gamepad1.dpad_down) {
                currentPosition -= 0.025;
                robot.xAdj.setPosition(currentPosition);
            }
        }
    }
}
