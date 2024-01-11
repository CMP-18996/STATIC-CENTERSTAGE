package org.firstinspires.ftc.teamcode.opmode.teleop;

import java.util.HashMap;
import java.lang.Integer;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.WaitForLiftHeight;
import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadAndDisplaySubsystem;

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
    private TouchpadAndDisplaySubsystem touchpad;
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private MiscSubsystem miscSubsystem;
    private AdaDisplay display1;
    private AdaDisplay display2;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    // Lower deposit enum when row odd
    public HashMap<Integer, DepositSubsystem.LowerHorizontalState> depositLowerColumns = new HashMap<>();
    // Upper deposit enum when row even
    public HashMap<Integer, DepositSubsystem.UpperHorizontalState> depositUpperColumns = new HashMap<>();

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        drivePad = new GamepadEx(gamepad1);
        liftPad = new GamepadEx(gamepad2);
        display1 = hardwareMap.get(AdaDisplay.class, "display1");
        display2 = hardwareMap.get(AdaDisplay.class, "display2");
        touchpad = new TouchpadAndDisplaySubsystem(gamepad2, display1, display2);
        liftSubsystem = new LiftSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        miscSubsystem = new MiscSubsystem(robot);

        this.fillMaps();
        liftPad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> schedule(new InstantCommand(() -> {
                    if (touchpad.isChosen) {
                        int leftRow = touchpad.getLeftRow();
                        int leftColumn = touchpad.getLeftColumn();
                        int rightRow = touchpad.getRightRow();
                        int rightColumn = touchpad.getRightColumn();
                        schedule(new SequentialCommandGroup(
                                new ParallelCommandGroup( // Consider Changing to -Parallel
                                        new SetReadyToDeposit(depositSubsystem, liftSubsystem, liftHeights.get(leftRow)),
                                        // TODO: MAKE SURE THIS CODE WORKS IT IS SO SKETCHY
                                        new SequentialCommandGroup(
                                                new WaitForLiftHeight(liftSubsystem, 140),
                                                (leftColumn % 2 == 1) ?
                                                        new LowerHorizontalMoveCommand(depositSubsystem, depositLowerColumns.get(leftColumn))
                                                        : new UpperHorizontalMoveCommand(depositSubsystem, depositUpperColumns.get(leftColumn))
                                        )
                                ),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGHDROP),
                                new WaitCommand(200),
                                new ParallelCommandGroup(
                                        new WaitCommand(250),
                                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT)
                                ),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                new WaitCommand(200),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                                new ParallelCommandGroup(
                                        new SetReadyToDeposit(depositSubsystem, liftSubsystem, liftHeights.get(rightRow)),
                                        // TODO: MAKE SURE THIS CODE WORKS IT IS SO SKETCHY
                                        (rightColumn % 2 == 1) ?
                                                new LowerHorizontalMoveCommand(depositSubsystem, depositLowerColumns.get(rightColumn))
                                                : new UpperHorizontalMoveCommand(depositSubsystem, depositUpperColumns.get(rightColumn))
                                ),
                                new ParallelCommandGroup(
                                        new WaitCommand(250),
                                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT)
                                ),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                        ));
                        touchpad.reset();
                    }
                })));


        liftPad.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> schedule(
                        new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                ));

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> schedule(
                        new DroneCommand(miscSubsystem))
                );
        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> schedule(
                        new HangCommand(miscSubsystem))
                );
        // Manually change the intake state
        /*
        Already used buttons:
        Touchpad, Y, A, X, dpad up, dpad right, dpad down, right bumper, left bumper

        Commands used:
        SetReadyToDeposit, both DepositXMove commands, StasisCommand, IntakeWait, TakeFromDeposit, DroneCommand, HangCommand
        GrabberGripCommand,
        */

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
        liftHeights.put(9, LiftSubsystem.LiftHeight.PICKUPHEIGHT);
        liftHeights.put(10, LiftSubsystem.LiftHeight.BASE);

        depositLowerColumns.put(1, DepositSubsystem.LowerHorizontalState.A);
        depositLowerColumns.put(2, DepositSubsystem.LowerHorizontalState.B);
        depositLowerColumns.put(3, DepositSubsystem.LowerHorizontalState.C);
        depositLowerColumns.put(4, DepositSubsystem.LowerHorizontalState.D);
        depositLowerColumns.put(5, DepositSubsystem.LowerHorizontalState.E);
        // depositLowerColumns.put(6, DepositSubsystem.LowerHorizontalState.F);

        depositUpperColumns.put(1, DepositSubsystem.UpperHorizontalState.A);
        depositUpperColumns.put(2, DepositSubsystem.UpperHorizontalState.B);
        depositUpperColumns.put(3, DepositSubsystem.UpperHorizontalState.C);
        depositUpperColumns.put(4, DepositSubsystem.UpperHorizontalState.D);
        depositUpperColumns.put(5, DepositSubsystem.UpperHorizontalState.E);
        depositUpperColumns.put(6, DepositSubsystem.UpperHorizontalState.F);
        // depositUpperColumns.put(7, DepositSubsystem.UpperHorizontalState.G);
    }
}

