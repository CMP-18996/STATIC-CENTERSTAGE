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
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;

public class Camera extends SubsystemBase {
    private VisionPortal visionPortal;
    private HardwareMap hardwareMap;
    private AprilTagProcessor aprilTag;
    private VisionProcessor propProcessor;


    public void initProcessor() {
        aprilTag = new AprilTagProcessor
                // I have no clue what this stuff does
                .Builder()
                .setLensIntrinsics(963.508, 972.014, 312.259, 223.992)
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .build();
        propProcessor = new PropProcessor();
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 360))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(propProcessor)
                .addProcessor(aprilTag)
                .build();
        setManualExposure(6, 250);
    }


    public Camera(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
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
