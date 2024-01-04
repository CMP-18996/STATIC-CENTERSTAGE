package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class XAxisTuning extends CommandOpMode {
    Robot robot;
    DepositSubsystem deposit;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap);
        deposit = new DepositSubsystem(robot);
        CommandScheduler.getInstance().reset();

        CommandScheduler.getInstance().schedule(
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.A),
                new WaitCommand(1000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.D),
                new WaitCommand(1000),
                new LowerHorizontalMoveCommand(deposit, DepositSubsystem.LowerHorizontalState.B)
        );
    }

    public void run() {
        waitForStart();

    }
}
