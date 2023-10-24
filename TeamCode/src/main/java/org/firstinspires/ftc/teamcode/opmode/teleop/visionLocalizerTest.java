package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.classicLinearDriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "visionLocalizerTest")
public class visionLocalizerTest extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    classicLinearDriveToAprilTagCommand t;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
        drive = new Drive(robot);
        register(drive);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
        t = new classicLinearDriveToAprilTagCommand(robot, drive, 5);
        CommandScheduler.getInstance().schedule(t);
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.periodic();
        telemetry.addData("What I see?", t.getTagOfInterest());
        telemetry.addData("Stats", t.getStats());
        telemetry.update();
    }
}   // end class

