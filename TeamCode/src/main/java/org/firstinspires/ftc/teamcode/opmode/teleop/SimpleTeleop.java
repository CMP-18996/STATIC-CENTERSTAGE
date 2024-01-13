package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.opmode.tests.DisplayTest;

import java.util.HashMap;

@Config
@TeleOp(name="Simple Final TeleOp", group="Official")
public class SimpleTeleop extends CommandOpMode {
    private Robot robot;
    private Drive drive;
    private GamepadEx drivePad;
    private GamepadEx liftPad;
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private AdaDisplay display;
    private MiscSubsystem miscSubsystem;
    private double xAxisPosition = 0.830625;
    public static double xAxisProportion = .003;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private HashMap<Integer, IntakeSubsystem.FrontBarState> intakeHeights = new HashMap<>();
    int inputtedLiftHeight = 1;
    int inputtedIntakeHeight = 1;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        miscSubsystem = new MiscSubsystem(robot);
        drivePad = new GamepadEx(gamepad1);
        // drive = new Drive(robot); TODO: UNCOMMENT
        liftPad = new GamepadEx(gamepad2);
        display = hardwareMap.get(AdaDisplay.class, "display");

        this.fillMaps();

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.min(inputtedLiftHeight + 2, 9);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.max(1, inputtedLiftHeight - 2);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> schedule(new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem))
        );

        liftPad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                () -> schedule(new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem))
        );

        liftPad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight)),
                                new WaitCommand(500),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH)
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT))
                )
                .whenReleased(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT))
                );

        liftPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT))
                )
                .whenReleased(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT))
                );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> {
                    inputtedIntakeHeight = Math.min(inputtedLiftHeight + 1, 5);
                    schedule(
                            new FrontBarCommand(intakeSubsystem, intakeHeights.get(inputtedIntakeHeight))
                    );
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> {
                    inputtedIntakeHeight = Math.max(inputtedIntakeHeight - 1, 1);
                    schedule(
                            new FrontBarCommand(intakeSubsystem, intakeHeights.get(inputtedIntakeHeight))
                    );
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> schedule(
                        new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING)
                )
        );

        drivePad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> schedule(
                        new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED)
                )
        );

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new DroneCommand(miscSubsystem)
                )
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        xAxisPosition = xAxisPosition
                + (liftPad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) * xAxisProportion)
                - (liftPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) * xAxisProportion);
        robot.xAdj.setPosition(xAxisPosition);
        display.writeInt(AdaDisplay.DeviceNumber.ONE, inputtedLiftHeight);
        display.writeInt(AdaDisplay.DeviceNumber.TWO, inputtedIntakeHeight);

        robot.hangServo1.setPower(drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
        robot.hangServo2.setPower(drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        // drive.manualPower(-drivePad.getLeftX(), drivePad.getLeftY(), -drivePad.getRightX()); TODO: UNCOMMENT
        telemetry.addData("Lift Height:", inputtedLiftHeight);
        telemetry.addData("Front Bar Height:", inputtedIntakeHeight);
        telemetry.addData("xAxisPosition", xAxisPosition);
        telemetry.update();
    }

    public void fillMaps() {
        liftHeights.put(1, LiftSubsystem.LiftHeight.HEIGHTONE);
        liftHeights.put(2, LiftSubsystem.LiftHeight.HEIGHTTWO);
        liftHeights.put(3, LiftSubsystem.LiftHeight.HEIGHTTHREE);
        liftHeights.put(4, LiftSubsystem.LiftHeight.HEIGHTFOUR);
        liftHeights.put(5, LiftSubsystem.LiftHeight.HEIGHTFIVE);
        liftHeights.put(6, LiftSubsystem.LiftHeight.HEIGHTSIX);
        liftHeights.put(7, LiftSubsystem.LiftHeight.HEIGHTSEVEN);
        liftHeights.put(8, LiftSubsystem.LiftHeight.HEIGHTEIGHT);
        liftHeights.put(9, LiftSubsystem.LiftHeight.PICKUPHEIGHT);

        intakeHeights.put(1, IntakeSubsystem.FrontBarState.GROUND);
        intakeHeights.put(2, IntakeSubsystem.FrontBarState.LEVEL1);
        intakeHeights.put(3, IntakeSubsystem.FrontBarState.LEVEL2);
        intakeHeights.put(4, IntakeSubsystem.FrontBarState.LEVEL3);
        intakeHeights.put(5, IntakeSubsystem.FrontBarState.LEVEL4);
    }
}
