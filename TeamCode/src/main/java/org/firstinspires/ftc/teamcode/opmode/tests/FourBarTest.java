package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp(name="Four Bar Test")
public class FourBarTest extends CommandOpMode {
    Robot robot;
    DepositSubsystem depositSubsystem;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap);
        depositSubsystem = new DepositSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                        new WaitCommand(1000),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                        new WaitCommand(1000),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        new WaitCommand(1000),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.LOW),
                        new WaitCommand(1000),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                        new WaitCommand(1000),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS)
                )
        );
    }
}
