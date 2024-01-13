package org.firstinspires.ftc.teamcode.opmode.tests.drive;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@Disabled
@TeleOp(name = "defaultDriveTest",group="Official")
public class defaultDriveTest extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        register(drive);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        drive.manualPower(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

        telemetry.addData("Status", "Rumning...");
        telemetry.addData("Current of rightFront", robot.rightFront.motor.getPower());
        telemetry.addData("Current of leftFront", robot.leftFront.motor.getPower());
        telemetry.addData("Current of rightRear", robot.rightRear.motor.getPower());
        telemetry.addData("Current of leftRear", robot.leftRear.motor.getPower());
        telemetry.update();
    }
}
