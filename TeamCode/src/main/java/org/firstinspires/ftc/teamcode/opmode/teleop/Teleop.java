package org.firstinspires.ftc.teamcode.opmode.teleop;

import java.util.HashMap;
import java.lang.Integer;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.IntakeWait;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

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
    private IntakeSubsystem intakeSubsystem;
    private Camera camera;
    private MiscSubsystem miscSubsystem;
    private MecanumDrive mecanumDrive;
    private int rowNumber;
    private int columnNumber;
    // I'm sorry about the naming
    private LiftSubsystem.LiftHeight liftHeight;
    private DepositSubsystem.LowerHorizontalState depositLowerColumn;
    private DepositSubsystem.UpperHorizontalState depostUpperColumn;
    private LeftRightChoiceState currentLeftRightChoice = LeftRightChoiceState.LEFT;
    private SelectionState currentSelectionState = SelectionState.CHOOSINGROW;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    // Lower deposit enum when row odd
    public HashMap<Integer, DepositSubsystem.LowerHorizontalState> depositLowerColumns = new HashMap<>();
    // Upper deposit enum when row even
    public HashMap<Integer, DepositSubsystem.UpperHorizontalState> depositUpperColumns = new HashMap<>();
    public enum LeftRightChoiceState {
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
        intakeSubsystem = new IntakeSubsystem(robot);
        camera = robot.camera;
        miscSubsystem = new MiscSubsystem(robot);

        register(drive, touchpad);

        liftPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> schedule(new InstantCommand(() -> {
                    currentLeftRightChoice = LeftRightChoiceState.RIGHT;
                })));
        liftPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> schedule(new InstantCommand(() -> {
                    currentLeftRightChoice = LeftRightChoiceState.LEFT;
                })));

        this.fillMaps();

        liftHeight = liftHeights.get(rowNumber);

        depostUpperColumn = depositUpperColumns.get(columnNumber);
        liftPad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> {
                    try {
                        rowNumber = touchpad.getHistory().get(0);
                        columnNumber = touchpad.getHistory().get(1);
                        boolean depositLower = rowNumber % 2 == 1;

                        switch (currentSelectionState) {

                        case CHOOSINGROW:
                                 CommandScheduler.getInstance().schedule(new ParallelCommandGroup(
                                     new LiftCommand(liftSubsystem, liftHeights.get(rowNumber))
                                 ));
                            break;
                        case CHOOSINGCOLUMN:
                            if (depositLower) {
                                CommandScheduler.getInstance().schedule(new ParallelCommandGroup(
                                        new LiftCommand(liftSubsystem, liftHeights.get(rowNumber)),
                                        new LowerHorizontalMoveCommand(depositSubsystem, depositLowerColumns.get(columnNumber))
                                ));
                            }
                            else {
                                CommandScheduler.getInstance().schedule(new ParallelCommandGroup(
                                        new LiftCommand(liftSubsystem, liftHeights.get(rowNumber)),
                                        new UpperHorizontalMoveCommand(depositSubsystem, depositUpperColumns.get(columnNumber))
                                ));
                            }
                            break;
                        }

                        currentSelectionState = SelectionState.CHOOSINGCOLUMN;

                    } catch (Exception e) {}
                });

        liftPad.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(() -> CommandScheduler.getInstance().schedule(
                                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem))
                        );

        liftPad.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(() -> CommandScheduler.getInstance().schedule(
                                new IntakeWait(intakeSubsystem))
                        );

        liftPad.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(() -> CommandScheduler.getInstance().schedule(
                                )// new SetReadyToDeposit(depositSubsystem))
                        );
        /*liftPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(() -> CommandScheduler.getInstance().schedule(
                                new ToTagCommand(camera, drive))
                        );
         */
        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new DroneCommand(miscSubsystem))
                );
        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new HangCommand(miscSubsystem))
                );

        // Manually change the intake state

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
        liftHeights.put(10, LiftSubsystem.LiftHeight.HEIGHTTEN);

        depositLowerColumns.put(1, DepositSubsystem.LowerHorizontalState.A);
        depositLowerColumns.put(2, DepositSubsystem.LowerHorizontalState.B);
        depositLowerColumns.put(3, DepositSubsystem.LowerHorizontalState.C);
        depositLowerColumns.put(4, DepositSubsystem.LowerHorizontalState.D);
        depositLowerColumns.put(5, DepositSubsystem.LowerHorizontalState.E);
        depositLowerColumns.put(6, DepositSubsystem.LowerHorizontalState.F);

        depositUpperColumns.put(1, DepositSubsystem.UpperHorizontalState.A);
        depositUpperColumns.put(2, DepositSubsystem.UpperHorizontalState.B);
        depositUpperColumns.put(3, DepositSubsystem.UpperHorizontalState.C);
        depositUpperColumns.put(4, DepositSubsystem.UpperHorizontalState.D);
        depositUpperColumns.put(5, DepositSubsystem.UpperHorizontalState.E);
        depositUpperColumns.put(6, DepositSubsystem.UpperHorizontalState.F);
        depositUpperColumns.put(7, DepositSubsystem.UpperHorizontalState.G);
    }
}

