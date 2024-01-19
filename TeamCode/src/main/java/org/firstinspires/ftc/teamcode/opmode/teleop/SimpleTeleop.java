package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.opmode.tests.DisplayTest;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

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
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private HashMap<Integer, IntakeSubsystem.FrontBarState> intakeHeights = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.LowerHorizontalState> lowerHorizontalStateHashMap = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.UpperHorizontalState> upperHorizontalStateHashMap = new HashMap<>();
    int inputtedLiftHeight = 1;
    int inputtedXAxisLocation = 0;
    //int inputtedIntakeHeight = 1;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        miscSubsystem = new MiscSubsystem(robot);
        drivePad = new GamepadEx(gamepad1);
        drive = new Drive(robot); 
        liftPad = new GamepadEx(gamepad2);
        display = hardwareMap.get(AdaDisplay.class, "display");

        this.fillMaps();

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.min(inputtedLiftHeight + 1, 11);
                    schedule(
                            new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight))
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.max(inputtedLiftHeight - 1, 1);
                    schedule(
                            new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight))
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                () -> {
                    inputtedXAxisLocation = Math.min(inputtedXAxisLocation + 1, 5);
                    if (inputtedXAxisLocation % 2 == 0) {
                        schedule(
                                new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalStateHashMap.get(inputtedXAxisLocation))
                        );
                    } else {
                        schedule(
                                new UpperHorizontalMoveCommand(depositSubsystem, upperHorizontalStateHashMap.get(inputtedXAxisLocation))
                        );
                    }
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                () -> {
                    inputtedXAxisLocation = Math.max(inputtedXAxisLocation - 1, -5);
                    if (inputtedXAxisLocation % 2 == 0) {
                        schedule(
                                new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalStateHashMap.get(inputtedXAxisLocation))
                        );
                    } else {
                        schedule(
                                new UpperHorizontalMoveCommand(depositSubsystem, upperHorizontalStateHashMap.get(inputtedXAxisLocation))
                        );
                    }
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                new WaitCommand(400),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH)
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> schedule(new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem))
        );

        liftPad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> schedule(
                        new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
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


       /* drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
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
        );*/


        drivePad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> robot.intakeMotor.set(-.5)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> {
                    robot.intakeMotor.set(.5);
                    sleep(400);
                    robot.intakeMotor.set(0);
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(new DroneCommand(miscSubsystem))
        );

/*        super.schedule(
                new ZeroLiftCommand(liftSubsystem)
        );*/

//        CommandScheduler.getInstance().schedule(
//                new ZeroLiftCommand(liftSubsystem)
//        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        //display.writeInt(AdaDisplay.DeviceNumber.ONE, inputtedLiftHeight);
        //display.writeInt(AdaDisplay.DeviceNumber.TWO, inputtedIntakeHeight);

        robot.hangServo1.setPower(gamepad2.left_stick_y);
        robot.hangServo2.setPower(-1* gamepad2.right_stick_y);

        drive.manualPower(drivePad.getLeftX(), -drivePad.getLeftY(), -drivePad.getRightX());
        display.writeInt(AdaDisplay.DeviceNumber.ONE, inputtedLiftHeight);
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
        liftHeights.put(9, LiftSubsystem.LiftHeight.HEIGHTNINE);
        liftHeights.put(10,LiftSubsystem.LiftHeight.HEIGHTTEN);

        intakeHeights.put(1, IntakeSubsystem.FrontBarState.GROUND);
        intakeHeights.put(2, IntakeSubsystem.FrontBarState.LEVEL1);
        intakeHeights.put(3, IntakeSubsystem.FrontBarState.LEVEL2);
        intakeHeights.put(4, IntakeSubsystem.FrontBarState.LEVEL3);
        intakeHeights.put(5, IntakeSubsystem.FrontBarState.LEVEL4);

        lowerHorizontalStateHashMap.put(-4, DepositSubsystem.LowerHorizontalState.A);
        lowerHorizontalStateHashMap.put(-2, DepositSubsystem.LowerHorizontalState.B);
        lowerHorizontalStateHashMap.put(0, DepositSubsystem.LowerHorizontalState.C);
        lowerHorizontalStateHashMap.put(2, DepositSubsystem.LowerHorizontalState.D);
        lowerHorizontalStateHashMap.put(4, DepositSubsystem.LowerHorizontalState.E);

        upperHorizontalStateHashMap.put(-5, DepositSubsystem.UpperHorizontalState.A);
        upperHorizontalStateHashMap.put(-3, DepositSubsystem.UpperHorizontalState.B);
        upperHorizontalStateHashMap.put(-1, DepositSubsystem.UpperHorizontalState.C);
        upperHorizontalStateHashMap.put(1, DepositSubsystem.UpperHorizontalState.D);
        upperHorizontalStateHashMap.put(3, DepositSubsystem.UpperHorizontalState.E);
        upperHorizontalStateHashMap.put(5, DepositSubsystem.UpperHorizontalState.F);

    }
}
