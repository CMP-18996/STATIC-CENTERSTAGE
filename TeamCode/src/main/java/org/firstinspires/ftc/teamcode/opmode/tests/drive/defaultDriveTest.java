package org.firstinspires.ftc.teamcode.opmode.tests.drive;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "defaultDriveTest",group="Official")
public class defaultDriveTest extends CommandOpMode {
    public Robot robot;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        double lf = 100;
        double lb = 65;
        double rf = 130;
        double rb = 95;
        double max = Math.max(Math.max(lf, lb), Math.max(rf, rb));

        robot.leftFront.set((y + x + rx) * lf / max);
        robot.leftRear.set((y - x + rx) * lb / max);
        robot.rightFront.set((y - x - rx) * rf / max);
        robot.rightRear.set((y + x - rx) * rb / max);

        telemetry.update();
    }
}
