package org.firstinspires.ftc.teamcode.opmode.tests.tuning;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
// import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

@TeleOp(name="Hang Test Two")
public class HangTestTwo extends CommandOpMode {
    Robot robot;
    MiscSubsystem miscSubsystem;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        miscSubsystem = new MiscSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new HangCommand(miscSubsystem)
        );
    }

    public void run() {
        CommandScheduler.getInstance().run();
    }
}
