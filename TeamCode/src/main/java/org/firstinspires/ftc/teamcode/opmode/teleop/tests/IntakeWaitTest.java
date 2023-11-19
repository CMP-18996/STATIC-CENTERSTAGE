package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@TeleOp(name = "IntakeWaitTest")
public class IntakeWaitTest extends CommandOpMode {
    Robot robot;
    IntakeSubsystem intake;

    /*
Make motor run for 5 seconds normally in the picking up direction
Stall/Move Back and Forwards Command
 */

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.TELEOP);
        intake = new IntakeSubsystem(robot);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                        new WaitCommand(1000),
                        new IntakeWait(intake),
                        new WaitCommand(500)
                )
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
    }

}
