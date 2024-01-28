package org.firstinspires.ftc.teamcode.tests.drive;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

@TeleOp(name = "april tag local",group="test")
public class AprilTagTest extends CommandOpMode {
    public Robot robot;
    public SampleMecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

        CommandScheduler.getInstance().schedule(
                new ToTagCommand(robot.camera, drive, false)
        );

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
