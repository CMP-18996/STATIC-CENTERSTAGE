package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.linearDriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.vision.AprilTagCamera;

@TeleOp(name = "visionLocalizerTest")
public class visionLocalizerTest extends CommandOpMode {
    public Robot robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
    public AprilTagCamera cam;
    public Drive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        drive = new Drive(robot);
        cam = new AprilTagCamera(robot);
        register(drive, cam);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
        schedule(new linearDriveToAprilTagCommand(cam, drive, 3));
    }
    @Override
    public void run() {
        run();
        telemetry.addData("Status", "Rumning...");
        telemetry.update();
    }
}   // end class

