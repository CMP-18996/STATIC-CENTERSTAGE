package org.firstinspires.ftc.teamcode.opmode.lame;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@Disabled
@TeleOp(name="OFFICIAL TELEOP", group="Official")
public class LameTeleOp extends CommandOpMode {
    private Robot robot;
    private Drive drive;
    private GamepadEx drivePad;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        GlobalVariables.color = GlobalVariables.Color.RED;

        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        drivePad = new GamepadEx(gamepad1);

        register(drive);
        telemetry.addData("Status", "Ready!");
        telemetry.update();
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.manualPower(-drivePad.getLeftX(), drivePad.getLeftY(), -drivePad.getRightX());
        telemetry.addData("Status", "Runming...");
        telemetry.update();
    }
}

