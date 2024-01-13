package org.firstinspires.ftc.teamcode.opmode.tests;

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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Config
@TeleOp
public class TakeFromIntakeTest extends CommandOpMode {
    Robot robot;
    LiftSubsystem liftSubsystem;
    DepositSubsystem depositSubsystem;
    IntakeSubsystem intakeSubsystem;
    public static int initialToFourBarTime = 200;
    public static int fourBarToGrabberTime = 1000;
    public static int grabberToFourBarTime = 450;
    public static int fourBarToCoverTime = 1000;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        GamepadEx gamepadEx = new GamepadEx(gamepad1);

        gamepadEx.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                new WaitCommand(2000),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                                new WaitCommand(initialToFourBarTime),

                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP),
                                new WaitCommand(fourBarToGrabberTime),

                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.PICKUP_ADDED),
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP_ADDED),
                                new WaitCommand(300),

                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                                new WaitCommand(grabberToFourBarTime), // might need to be tuned

                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                                new WaitCommand(200),

                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                                new WaitCommand(fourBarToCoverTime),

                                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                        )
                )
        );

        CommandScheduler.getInstance().schedule(
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING)
        );
    }

    @Override
    public void run() {
        super.run();
    }
}
