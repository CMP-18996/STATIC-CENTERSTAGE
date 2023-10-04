package org.firstinspires.ftc.teamcode.common.vision;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;

public class PropProcessor implements VisionProcessor {
    private int width;
    private int height;
    private CameraCalibration cameraCalibration;

    // These are not final nor is the system for detecting final
    private Coordinate position1 = new Coordinate(0, 0);
    private Coordinate position2 = new Coordinate(0, 0);
    private Coordinate position3 = new Coordinate(0, 0);

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        this.width = width;
        this.height = height;
        this.cameraCalibration = calibration;
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        return new Mat();
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}

class Coordinate {
    int x;
    int y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
