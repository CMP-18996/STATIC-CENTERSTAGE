package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp
public class DepositTest extends CommandOpMode {
    Robot robot;
    DepositSubsystem depositSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        robot = new Robot(hardwareMap);
        depositSubsystem = new DepositSubsystem(robot);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT)
                        ),
                        new WaitCommand(5000),
                        new ParallelCommandGroup(
                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PICKING_UP),
                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT)
                        )
                )
        );
    }

    @Override
    public void run() {
        super.run();
    }
}
