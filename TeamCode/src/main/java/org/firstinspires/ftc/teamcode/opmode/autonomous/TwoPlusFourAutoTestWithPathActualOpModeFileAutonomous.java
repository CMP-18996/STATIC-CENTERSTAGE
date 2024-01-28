package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.TwoPlusFourPathTestCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

@Disabled
@Autonomous(name="Two Plus Four Auto")
public class TwoPlusFourAutoTestWithPathActualOpModeFileAutonomous extends CommandOpMode {
    Robot robot;
    SampleMecanumDrive drive;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        CommandScheduler.getInstance().schedule(
                new TwoPlusFourPathTestCommand(drive)
        );
    }

    public void run() {
        CommandScheduler.getInstance().run();
    }
}
