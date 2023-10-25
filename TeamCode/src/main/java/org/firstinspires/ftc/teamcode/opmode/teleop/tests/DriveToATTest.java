package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.classicLinearDriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "apriltag test")
public class DriveToATTest extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
        drive = new Drive(robot);
        register(drive);

        CommandScheduler.getInstance().schedule(new classicLinearDriveToAprilTagCommand(robot, drive, 5));
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
    }
}
