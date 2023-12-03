package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name="dumb",group="test")
public class VisionTeste extends CommandOpMode {
    public Camera camera;

    @Override
    public void initialize() {
        GlobalVariables.color = GlobalVariables.Color.RED;
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        schedule(new SequentialCommandGroup(
                new WaitCommand(5000)
        ));
        camera = new Camera(hardwareMap);
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        camera.startPropProcessing();
        List<AprilTagDetection> currentDetections = camera.getTagLocalization();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
        telemetry.addLine("Prop Position Detected: " + GlobalVariables.position.toString());
        telemetry.addData("Detected Pixel Val:", camera.getTelemetryTestVal());
        telemetry.update();
        sleep(20);
    }
}
