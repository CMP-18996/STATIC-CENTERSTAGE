package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp(name="X Axis Tuning Two")
public class XAxisTuningTwo extends CommandOpMode {
    Robot robot;
    DepositSubsystem deposit;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap);
        deposit = new DepositSubsystem(robot);
        class SleepCommand extends CommandBase {
            public SleepCommand(long sleepTime) {
                sleep(sleepTime);
            }
        }

        CommandScheduler.getInstance().schedule(
                // Use a sequential command group
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.A),
                new SleepCommand(2000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.B),
                new SleepCommand(2000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.C),
                new SleepCommand(2000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.D),
                new SleepCommand(2000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.E),
                new SleepCommand(3000),

                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.A),
                new SleepCommand(4000),
                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.B),
                new SleepCommand(2000),
                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.C),
                new SleepCommand(2000),
                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.D),
                new SleepCommand(2000),
                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.E),
                new SleepCommand(2000),
                new UpperHorizontalMoveCommand(deposit, DepositSubsystem.UpperHorizontalState.F)
                );
    }

    @Override
    public void run() {
        waitForStart();
        CommandScheduler.getInstance().run();
    }
}
