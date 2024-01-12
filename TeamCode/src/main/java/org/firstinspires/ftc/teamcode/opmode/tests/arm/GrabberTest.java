package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp(name="Grabber Test")
public class GrabberTest extends CommandOpMode {
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
                    new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                    new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL)
                )
        );
    }

    public void run() {CommandScheduler.getInstance().run();}
}
