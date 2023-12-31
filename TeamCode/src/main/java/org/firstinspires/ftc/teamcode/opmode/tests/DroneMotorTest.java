package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneMotorTestCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

@TeleOp(name="Drone Motor Test")
public class DroneMotorTest extends CommandOpMode {
    Robot robot;
    MiscSubsystem miscSubsystem;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap);
        miscSubsystem = new MiscSubsystem(robot);
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().schedule(
                new DroneMotorTestCommand(miscSubsystem)
        );
    }

    @Override
    public void run() {
        waitForStart();
        CommandScheduler.getInstance().run();
    }
}
