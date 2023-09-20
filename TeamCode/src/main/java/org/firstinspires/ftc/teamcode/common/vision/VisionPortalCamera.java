package org.firstinspires.ftc.teamcode.common.vision;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.LinkedList;
import java.util.List;

public class VisionPortalCamera extends SubsystemBase {
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    public VisionPortalCamera(Robot robot) {
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(robot.hardwareMap.get(WebcamName.class, "Webcam 1"));
        //builder.setCameraResolution(new Size(640, 480));
        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
        visionPortal.setProcessorEnabled(aprilTag, true);
    }
    public VisionPortal getVisionPortal() {
        return visionPortal;
    }
    public LinkedList<double[]> getTagLocalization() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        LinkedList<double[]> observations = new LinkedList<>();
        for (AprilTagDetection detection : currentDetections) {
            observations.add(new double[]{detection.id,
                    detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z,
                    detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw});
        }
        return observations;
    }
    @Override
    public void periodic() {

    }
}
