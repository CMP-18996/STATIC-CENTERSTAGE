package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "ps4Test")
public class ps4Test extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    public double x1, y1, x2, y2;
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
        x1 = gamepad1.touchpad_finger_1_x;
        y1 = gamepad1.touchpad_finger_1_y;
        x2 = gamepad1.touchpad_finger_2_x;
        y2 = gamepad1.touchpad_finger_2_y;
        if (gamepad1.touchpad && x1 > 0.4) {
            drive.manualPower(0.35, 0, 0);
        } else if (gamepad1.touchpad && x1 < -0.4) {
            drive.manualPower(-0.35, 0, 0);
        } else if (gamepad1.touchpad && y1 > 0.4) {
            drive.manualPower(0, 0.35, 0);
        } else if (gamepad1.touchpad && y1 < -0.4) {
            drive.manualPower(0, -0.35, 0);
        } else {
            drive.manualPower(0, 0, 0);
        }
        telemetry.addData("Status", "Rumning...");
        telemetry.addData("Touchpad X", gamepad1.touchpad_finger_1_x);
        telemetry.addData("Touchpad Y", gamepad1.touchpad_finger_1_y);
        telemetry.update();
    }
}
