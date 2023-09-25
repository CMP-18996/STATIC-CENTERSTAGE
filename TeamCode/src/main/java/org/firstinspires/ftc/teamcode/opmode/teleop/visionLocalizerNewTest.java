package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.classicLinearDriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.linearDriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.vision.VisionPortalCamera;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

import java.util.Arrays;

@TeleOp(name = "visionLocalizerNewTest")
public class visionLocalizerNewTest extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    public VisionPortalCamera cam;
    linearDriveToAprilTagCommand t;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
        drive = new Drive(robot);
        cam = new VisionPortalCamera(robot);
        register(drive, cam);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
        t = new linearDriveToAprilTagCommand(cam, drive, 5);
        CommandScheduler.getInstance().schedule(t);
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.periodic();
        telemetry.addData("What I see?", cam.getTagLocalization());
        telemetry.update();
    }
}   // end class