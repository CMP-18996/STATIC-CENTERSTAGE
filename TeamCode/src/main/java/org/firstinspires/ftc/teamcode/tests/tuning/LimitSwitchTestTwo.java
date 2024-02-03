package org.firstinspires.ftc.teamcode.tests.tuning;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@TeleOp(name="Limit Switch Test Two")
public class LimitSwitchTestTwo extends CommandOpMode {
    Robot robot;
    LiftSubsystem liftSubsystem;
    boolean a = true;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        liftSubsystem.updateState(LiftSubsystem.LiftHeight.HEIGHTFOUR);

    }

    @Override
    public void run() {
        if (a) {
            CommandScheduler.getInstance().schedule(
                    new ZeroLiftCommand(liftSubsystem),
                    new InstantCommand(() -> {
                        telemetry.addLine("asd");
                        telemetry.update();
                    })
            );
            a = false;
        }
        CommandScheduler.getInstance().run();
    }
}
