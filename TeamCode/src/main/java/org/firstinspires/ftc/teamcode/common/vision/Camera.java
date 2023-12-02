package org.firstinspires.ftc.teamcode.common.vision;

import static java.lang.Thread.sleep;

import android.util.Size;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.tensorflow.lite.task.vision.detector.Detection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Camera extends SubsystemBase {
    private VisionPortal visionPortal;
    private boolean exposureSet = false;
    private AprilTagProcessor aprilTag;
    private PropProcessor propProcessor;


    public ArrayList<AprilTagDetection> getTagLocalization() {
        return aprilTag.getDetections();
    }

    public int getTelemetryTestVal() {
        return propProcessor.telemetryTestVal;
    }
    public VisionPortal getVisionPortal() { return visionPortal; };

    public Camera(HardwareMap hardwareMap) {
        aprilTag = new AprilTagProcessor
                // I have no clue what this stuff does
                .Builder()
                .setLensIntrinsics(1430, 1430, 480, 620) // manually found values: 920.510, 921.306, 653.369, 354.512, these are worse
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .build();
        propProcessor = new PropProcessor(GlobalVariables.color);
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                // .setCameraResolution(new Size(1080, 920))
                // .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(propProcessor)
                .addProcessor(aprilTag)
                .setAutoStopLiveView(true)
                .build();
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            try {
                sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        setManualExposure(6, 250);
    }

    public void startPropProcessing() {
        propProcessor.startDetecting = true;
        if (propProcessor.objectDetected) {
            visionPortal.setProcessorEnabled(propProcessor, false);
        }
    }

    // only use when stop is not requested
    private void setManualExposure(int exposureMS, int gain) {
        if (visionPortal == null) {
            return;
        }
        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
            exposureControl.setMode(ExposureControl.Mode.Manual);
        }
        exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(gain);
    }
}
