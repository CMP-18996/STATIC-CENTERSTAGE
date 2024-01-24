package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

import java.util.HashMap;

@Config
@TeleOp(name="Tag teleOp", group="Official")
public class TagTeleop extends CommandOpMode {
    private Robot robot;
    private MecanumDrive drive;
    private GamepadEx drivePad;
    private GamepadEx liftPad;
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private HT16K33 display;
    private MiscSubsystem miscSubsystem;
    private double xAxisPosition = 0.830625;
    private double centerPosition = 0.830625;
    public static double xAxisProportion = .015;
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
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        liftPad = new GamepadEx(gamepad2);
        display = hardwareMap.get(HT16K33.class, "display");

        this.fillMaps();

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.min(inputtedLiftHeight + 1, 9);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.max(1, inputtedLiftHeight - 1);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> schedule(
                        new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> {
                                    xAxisPosition = centerPosition;
                                }),
                                new WaitCommand(800),
                                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight)),
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                new WaitCommand(400),
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
                () -> robot.intakeMotor.set(-.9)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> robot.intakeMotor.set(0)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new DroneCommand(miscSubsystem)
                )
        );
        drivePad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                () -> schedule(
                        new InstantCommand(() -> drive.pose = new Pose2d(0, 0, 0)),
                        new ToTagCommand(robot.camera, drive)
                )
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        xAxisPosition = xAxisPosition
                + (gamepad2.right_trigger * xAxisProportion)
                - (gamepad2.left_trigger * xAxisProportion);
        robot.xAdj.setPosition(xAxisPosition);
        //display.writeInt(AdaDisplay.DeviceNumber.ONE, inputtedLiftHeight);
        //display.writeInt(AdaDisplay.DeviceNumber.TWO, inputtedIntakeHeight);

        robot.hangServo1.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        robot.hangServo2.setPower(-1* (gamepad1.right_trigger - gamepad1.left_trigger));

        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        drivePad.getLeftY(),
                        -drivePad.getLeftX()
                ),
                -drivePad.getRightX()
        ));

        drive.updatePoseEstimate();

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
