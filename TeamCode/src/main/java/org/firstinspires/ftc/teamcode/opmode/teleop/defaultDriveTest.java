package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "defaultDriveTest")
public class defaultDriveTest extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.TELEOP);
        drive = new Drive(robot);
        register(drive);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.manualPower(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        telemetry.addData("Status", "Rumning...");
        telemetry.update();
    }
}
