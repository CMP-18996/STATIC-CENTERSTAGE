package org.firstinspires.ftc.teamcode.tests.arm;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name="IntakeTest",group="test")
public class IntakeTest extends LinearOpMode {
    DcMotor intakeMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        intakeMotor = hardwareMap.dcMotor.get("intake");
        waitForStart();
        while (opModeIsActive()) {
            intakeMotor.setPower(gamepad1.right_trigger);
        }
    }
}
