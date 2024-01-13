package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneMotorTestCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

@Disabled
@TeleOp(name="Drone Motor Test")
public class DroneMotorTest extends CommandOpMode {
    Robot robot;
    MiscSubsystem miscSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        miscSubsystem = new MiscSubsystem(robot);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new DroneCommand(miscSubsystem),
                        new WaitCommand(10000)
                )
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
    }
}
