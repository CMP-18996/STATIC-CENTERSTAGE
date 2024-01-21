package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.Command;
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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadAndDisplaySubsystem;

import java.util.HashMap;

@TeleOp(name="Experimental Touchpad TeleOP")
public class ExperimentalTouchpadTeleOp extends CommandOpMode {
    Robot robot;
    private Drive drive;
    private GamepadEx drivePad;
    private GamepadEx liftPad;
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private HT16K33 display;
    private MiscSubsystem miscSubsystem;
    private TouchpadAndDisplaySubsystem touchpadAndDisplaySubsystem;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private HashMap<Integer, IntakeSubsystem.FrontBarState> intakeHeights = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.LowerHorizontalState> lowerHorizontalStateHashMap = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.UpperHorizontalState> upperHorizontalStateHashMap = new HashMap<>();
    double xAxisPosition = 0.830625;
    double incrementVal = 0.067;
    boolean intaking = false;

    int column1 = 3;
    int column2 = 3;
    int row1 = 1;
    int row2 = 1;

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
        display = hardwareMap.get(HT16K33.class, "display");

        this.fillMaps();

        // Experimental stuff
        touchpadAndDisplaySubsystem = new TouchpadAndDisplaySubsystem(gamepad2, display, display);

        // Hypothetical, most likely doesn't work, be prepared to stop the program
        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> schedule(
                        new InstantCommand(
                                () -> {
                                    CommandScheduler.getInstance().schedule(
                                            new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH)
                                    );
                                    column1 = touchpadAndDisplaySubsystem.getLeftColumn();
                                    row1 = touchpadAndDisplaySubsystem.getLeftRow();
                                    column2 = touchpadAndDisplaySubsystem.getRightColumn();
                                    row2 = touchpadAndDisplaySubsystem.getRightRow();
                                    boolean lower1 = row1 % 2 == 1;
                                    boolean lower2 = row2 % 2 == 1;
                                    if (lower1) {
                                        CommandScheduler.getInstance().schedule(
                                                new SequentialCommandGroup(
                                                        // new LiftCommand(liftSubsystem, liftHeights.get(row1)),
                                                        new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalStateHashMap.get(Math.min(column1, 5))),
                                                        new WaitCommand(3000)
                                                )
                                        );
                                    }
                                    else {
                                        CommandScheduler.getInstance().schedule(
                                                new SequentialCommandGroup(
                                                        // new LiftCommand(liftSubsystem, liftHeights.get(row1)),
                                                        new UpperHorizontalMoveCommand(depositSubsystem, upperHorizontalStateHashMap.get(Math.min(column1, 6))),
                                                        new WaitCommand(3000)
                                                )
                                        );
                                    }
                                    CommandScheduler.getInstance().schedule(new WaitCommand(3000));
                                    if (lower2) {
                                        CommandScheduler.getInstance().schedule(
                                                new SequentialCommandGroup(
                                                        // new LiftCommand(liftSubsystem, liftHeights.get(row2)),
                                                        new LowerHorizontalMoveCommand(depositSubsystem, lowerHorizontalStateHashMap.get(Math.min(column2, 5))),
                                                        new WaitCommand(3000)
                                                )
                                        );
                                    }
                                    else {
                                        CommandScheduler.getInstance().schedule(
                                                new SequentialCommandGroup(
                                                        // new LiftCommand(liftSubsystem, liftHeights.get(row2)),
                                                        new UpperHorizontalMoveCommand(depositSubsystem, upperHorizontalStateHashMap.get(Math.min(column2, 6))),
                                                        new WaitCommand(3000)
                                                )
                                        );
                                    }
                                    // touchpadAndDisplaySubsystem.reset();
                                    try {
                                        telemetry.addData("Column 1", column1);
                                        telemetry.addData("Row 1", row1);
                                        telemetry.addData("Column 2", column2);
                                        telemetry.addData("Row 2", row2);
                                        telemetry.update();
                                    }
                                    catch (Exception e) {
                                        telemetry.addData("", "");
                                        telemetry.update();
                                    }
                                }
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new ConditionalCommand(
                                        new SequentialCommandGroup(
                                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                                                new WaitCommand(300),
                                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                                new WaitCommand(500),
                                                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED)
                                        ),
                                        new InstantCommand(() -> {}),
                                        () -> intakeSubsystem.getCoverState() == IntakeSubsystem.CoverState.OPEN
                                ),
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                new WaitCommand(400),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH)
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> {
                    schedule(
                            new SequentialCommandGroup(
                                    new ConditionalCommand(
                                            new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
                                            new InstantCommand(() -> {}),
                                            () -> depositSubsystem.getFourBarState() == DepositSubsystem.FourBarState.HIGH
                                    ),
                                    new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                            )
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> {
                    schedule(
                            new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                    );
                }
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


        drivePad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> {
                    intaking = true;
                    robot.intakeMotor.set(-.7);
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> {
                    intaking = false;
                    robot.intakeMotor.set(.5);
                    sleep(400);
                    robot.intakeMotor.set(0);
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(new DroneCommand(miscSubsystem))
        );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL3)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND)
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
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

        lowerHorizontalStateHashMap.put(1, DepositSubsystem.LowerHorizontalState.A);
        lowerHorizontalStateHashMap.put(2, DepositSubsystem.LowerHorizontalState.B);
        lowerHorizontalStateHashMap.put(3, DepositSubsystem.LowerHorizontalState.C);
        lowerHorizontalStateHashMap.put(4, DepositSubsystem.LowerHorizontalState.D);
        lowerHorizontalStateHashMap.put(5, DepositSubsystem.LowerHorizontalState.E);

        upperHorizontalStateHashMap.put(1, DepositSubsystem.UpperHorizontalState.A);
        upperHorizontalStateHashMap.put(2, DepositSubsystem.UpperHorizontalState.B);
        upperHorizontalStateHashMap.put(3, DepositSubsystem.UpperHorizontalState.C);
        upperHorizontalStateHashMap.put(4, DepositSubsystem.UpperHorizontalState.D);
        upperHorizontalStateHashMap.put(5, DepositSubsystem.UpperHorizontalState.E);
        upperHorizontalStateHashMap.put(6, DepositSubsystem.UpperHorizontalState.F);
    }
}
