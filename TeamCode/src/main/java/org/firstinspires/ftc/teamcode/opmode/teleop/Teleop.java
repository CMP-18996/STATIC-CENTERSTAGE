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
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
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
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private int row;
    private int column;
    private ChoiceState currentChoice = ChoiceState.LEFT;
    public enum ChoiceState {
        LEFT,
        RIGHT
    }
    public enum SelectionState {
        CHOOSINGROW,
        CHOOSINGCOLUMN
    }
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        drivePad = new GamepadEx(gamepad1);
        liftPad = new GamepadEx(gamepad2);
        touchpad = new TouchpadSubsystem(gamepad2);
        liftSubsystem = new LiftSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        register(drive, touchpad);

        liftPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> schedule(new InstantCommand(() -> {
                    currentChoice = ChoiceState.RIGHT;
                })));
        liftPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> schedule(new InstantCommand(() -> {
                    currentChoice = ChoiceState.LEFT;
                })));

        row = touchpad.getHistory().get(touchpad.getHistory().size() - 2);
        column = touchpad.getHistory().get(touchpad.getHistory().size() - 1);

        liftPad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> new InstantCommand(() -> {

                }));

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
