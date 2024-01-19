package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp(name="X Axis Tuning")
public class XAxisTuning extends CommandOpMode {
    Robot robot;
    DepositSubsystem deposit;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap);
        deposit = new DepositSubsystem(robot);
        CommandScheduler.getInstance().reset();
        class TelemetryCommand extends CommandBase {
            public TelemetryCommand(String caption, String value) {
                telemetry.addData(caption, value);
                telemetry.update();
            }
        }
        class SleepCommand extends CommandBase {
            public SleepCommand(long sleepLength) {
                sleep(sleepLength);
            }
        }

        CommandScheduler.getInstance().schedule(
                // Use a sequential command group
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.A),
                new TelemetryCommand("Step", "1"),
                // new WaitCommand(1000),
                new SleepCommand(3000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.E),
                new TelemetryCommand("Step", "2"),
                // new WaitCommand(1000),
                new SleepCommand(3000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.B),
                new TelemetryCommand("Step", "3")
                );
    }

    public void run() {
        waitForStart();
        CommandScheduler.getInstance().run();
    }
}
