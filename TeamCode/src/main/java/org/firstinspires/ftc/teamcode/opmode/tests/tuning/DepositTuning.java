package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class DepositTuning extends CommandOpMode {
    Robot robot;
    DepositSubsystem depositSubsystem;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        depositSubsystem = new DepositSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL)
                )
        );
    }
}
