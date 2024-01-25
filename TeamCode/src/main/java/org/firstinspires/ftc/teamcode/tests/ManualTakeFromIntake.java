package org.firstinspires.ftc.teamcode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ManualTakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@TeleOp
public class ManualTakeFromIntake extends CommandOpMode {
    Robot robot;
    DepositSubsystem depositSubsystem;
    LiftSubsystem liftSubsystem;
    IntakeSubsystem intakeSubsystem;
    GamepadEx gamepadEx;
    @Override
    public void initialize() {
        super.reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        liftSubsystem = new LiftSubsystem(robot);
        gamepadEx = new GamepadEx(gamepad1);
        super.schedule(
                new ManualTakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem, gamepadEx)
        );
    }

    @Override
    public void run() {
        super.run();
    }
}
