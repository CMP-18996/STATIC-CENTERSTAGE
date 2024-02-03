package org.firstinspires.ftc.teamcode.tests.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorTouch;
import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Limit Switch Test")
public class LimitSwitchTest extends LinearOpMode {
    Robot robot;
    boolean switchDown;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        switchDown = true;
        waitForStart();
        while (opModeIsActive()) {
            switchDown = !robot.liftLimitSwitch.getState();
            telemetry.addData("Switch Down", switchDown);
            telemetry.update();
        }
    }
}
