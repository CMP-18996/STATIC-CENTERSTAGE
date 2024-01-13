package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@Disabled
@TeleOp(name="Front Bar Test")
public class FrontBarTest extends CommandOpMode {
    // Hang test, two servos opposite directions
    Robot robot;
    IntakeSubsystem intakeSubsystem;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND),
                        new WaitCommand(2000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL4),
                        new WaitCommand(2000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL1)
                )
        );
    }

    public void run() {
        CommandScheduler.getInstance().run();
    }
}
