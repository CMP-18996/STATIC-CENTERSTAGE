package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneMotorTestCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

public class DroneMotorTest extends LinearOpMode {
    Robot robot;
    MiscSubsystem miscSubsystem;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        miscSubsystem = new MiscSubsystem(robot);
        waitForStart();
        CommandScheduler.getInstance().schedule(
                new DroneMotorTestCommand(miscSubsystem)
        );
    }
}
