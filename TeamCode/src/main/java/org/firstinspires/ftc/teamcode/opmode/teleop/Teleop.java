package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;

/**
 * Triggers intake reverse
 * Bumpers, deposit
 */
@TeleOp(name="Final TeleOp", group="Official")
public class Teleop extends CommandOpMode {
    private Robot robot;
    private Drive drive;
    private GamepadEx drivePad;
    private GamepadEx liftPad;
    private TouchpadSubsystem touchpad;
    private int row;
    private int column;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        drivePad = new GamepadEx(gamepad1);
        liftPad = new GamepadEx(gamepad2);
        touchpad = new TouchpadSubsystem(gamepad2);
        register(drive, touchpad);

        liftPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> schedule(new InstantCommand(() -> touchpad.deleteLastInput())));

        row = touchpad.getHistory().get(touchpad.getHistory().size() - 2);
        column = touchpad.getHistory().get(touchpad.getHistory().size() - 1);

        telemetry.addData("Status", "Ready!");
        telemetry.update();
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        drive.manualPower(-drivePad.getLeftX(), drivePad.getLeftY(), -drivePad.getRightX());

        telemetry.addData("Recorded Positions", touchpad.getHistory());
        telemetry.update();
    }
}
