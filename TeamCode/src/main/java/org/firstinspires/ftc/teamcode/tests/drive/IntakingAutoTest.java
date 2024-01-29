package org.firstinspires.ftc.teamcode.tests.drive;

import com.acmerobotics.dashboard.config.Config;
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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

import java.util.HashMap;

@Config
@TeleOp(name="intake auto", group="test")
public class IntakingAutoTest extends CommandOpMode {
    private Robot robot;
    private GamepadEx liftPad;
    private LiftSubsystem liftSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private MiscSubsystem miscSubsystem;
    private HashMap<Integer, IntakeSubsystem.FrontBarState> intakeHeights = new HashMap<>();
    double xAxisPosition = DepositSubsystem.centerVal;
    int h = 1;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        miscSubsystem = new MiscSubsystem(robot);
        liftPad = new GamepadEx(gamepad2);

        this.fillMaps();

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(()->
                schedule(new InstantCommand(() -> robot.intakeMotor.set(-.5)))
        );
        liftPad.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->
                schedule(new InstantCommand(() -> robot.intakeMotor.set(.5)))
        );

        liftPad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new InstantCommand(() -> robot.intakeMotor.set(0))
        );


        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(() ->
                schedule(
                new InstantCommand(() -> h--),
                new FrontBarCommand(intakeSubsystem, intakeHeights.get(Math.max(1, h))))
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() ->
                schedule(
                        new InstantCommand(() -> h++),
                        new FrontBarCommand(intakeSubsystem, intakeHeights.get(Math.min(5, h))))
        );


        super.schedule(
                new ZeroLiftCommand(liftSubsystem),
                new InstantCommand(() -> robot.xAdj.setPosition(xAxisPosition))
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        if (h > 5) h = 5;
        if (h < 1) h = 1;

        telemetry.addData("Color Detected in Slot 1:", intakeSubsystem.slotOne.toString());
        telemetry.addData("Color Detected in Slot 2:", intakeSubsystem.slotTwo.toString());
        telemetry.update();
    }

    public void fillMaps() {
        intakeHeights.put(1, IntakeSubsystem.FrontBarState.GROUND);
        intakeHeights.put(2, IntakeSubsystem.FrontBarState.LEVEL1);
        intakeHeights.put(3, IntakeSubsystem.FrontBarState.LEVEL2);
        intakeHeights.put(4, IntakeSubsystem.FrontBarState.LEVEL3);
        intakeHeights.put(5, IntakeSubsystem.FrontBarState.LEVEL4);
    }
}
