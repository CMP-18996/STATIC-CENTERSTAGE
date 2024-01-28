package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@Disabled
@TeleOp
public class PushBot extends CommandOpMode {
    Robot robot;
    Drive drive;
    GamepadEx drivePad;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        drivePad = new GamepadEx(gamepad1);
    }

    @Override
    public void run() {
        drive.manualPower(drivePad.getLeftX(), -drivePad.getLeftY(), -drivePad.getRightX());
    }

}
