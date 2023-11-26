package org.firstinspires.ftc.teamcode.opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Robot;


@TeleOp(name="Timer Test", group="test")
public class TimerTest extends LinearOpMode {
    private Robot robot;
    // private Timing.Timer timer;
    private ElapsedTime elapsedTime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        elapsedTime.startTime();
        while (opModeIsActive()) {
            telemetry.addData("Elapsed time", elapsedTime.toString());
            telemetry.update();
        }
    }
}
