package org.firstinspires.ftc.teamcode.common.vision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;





public class PropProcessor implements VisionProcessor {
    private int width;
    private int height;
    private Rect boundingRect;
    private CameraCalibration cameraCalibration;
    public boolean objectDetected = false;
    private ArrayList<MatOfPoint> arbitrary;

    // All util things
    private Scalar lowerBound;
    private Scalar upperBound;
    private int pixelVal;
    public int telemetryTestVal;
    private Mat hsvMat;
    private Mat mask;
    private Mat resized;
    private List<MatOfPoint> contours;
    private Mat fillerMat;
    private MatOfPoint largestContour;
    private MatOfPoint largestContourFound;
    boolean startDetecting = false;

    // TODO: Figure these values out
    private int smallestAllowedArea = 0;

    // Number of Detections of Each Prop Location
    private int leftPos = 10;
    private int middlePos = 10;
    private int rightPos = 10;

    // These are not final nor is the system for detecting final

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        this.width = width;
        this.height = height;
        this.cameraCalibration = calibration;
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        if (startDetecting) {
            frame = detectObject(frame);
            checkFinish();
        }
        return null;
    }

    public Mat detectObject(Mat frame) {
        double scalePercent = 40;
        int width = (int) Math.round(frame.size().width * scalePercent / 100);
        int height = (int) Math.round(frame.size().height * scalePercent / 100);
        Size dimensions = new Size(width, height);
        resized = new Mat();
        Imgproc.resize(frame, resized, dimensions);

        hsvMat = new Mat();
        mask = new Mat();
        fillerMat = new Mat();
        contours = new ArrayList<>();
        largestContour = new MatOfPoint();

        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);
        //Core.bitwise_not(hsvMat, negMat);
        Core.inRange(hsvMat, lowerBound, upperBound, mask);
        Imgproc.findContours(mask, contours, fillerMat, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        largestContour = findLargestContour(contours);
        if (contours != null && largestContour != null) {
            boundingRect = Imgproc.boundingRect(largestContour);
            // arbitrary = new ArrayList<>();
            // arbitrary.add(largestContour);
            pixelVal = (int) Math.ceil(boundingRect.x + boundingRect.width / 2.0);
            //Imgproc.drawContours(frame, arbitrary, 0, new Scalar(42, 42, 129), -1, Imgproc.LINE_8, fillerMat, 0);
            telemetryTestVal = pixelVal;
            if (pixelVal < 100) {
                leftPos++;
            } else if (pixelVal < 437.5) {
                middlePos++;
            } else {
                rightPos++;
            }
        }
        hsvMat.release();
        mask.release();
        fillerMat.release();
        resized.release();
        return mask;
    }

    private void checkFinish() {
        if (leftPos + middlePos + rightPos >= 15) {
            objectDetected = true;
            if (leftPos > middlePos && leftPos > rightPos) {
                GlobalVariables.position = GlobalVariables.Position.LEFT;
            }
            else if (middlePos > leftPos && rightPos > leftPos) {
                GlobalVariables.position = GlobalVariables.Position.MIDDLE;
            }
            else if (rightPos > leftPos && rightPos > middlePos) {
                GlobalVariables.position = GlobalVariables.Position.RIGHT;
            }
            else { // only happens in the case that it gets something like left 10 right 10 middle 0
                objectDetected = false;
            }
            leftPos = 0;
            middlePos = 0;
            rightPos = 0;
        }
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {}

    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {

        double largestArea = 0;
        largestContourFound = new MatOfPoint();

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > largestArea) {
                largestArea = area;
                largestContour = contour;
            }
        }

        if (largestArea < smallestAllowedArea) largestContour = null;

        return largestContour;
    }


    public PropProcessor(GlobalVariables.Color teamColor) {
        switch (teamColor) {
            case RED:
                lowerBound = new Scalar(0, 63.75, 63.75);
                upperBound = new Scalar(10, 255, 255);
                break;
            case BLUE:
                lowerBound = new Scalar(108, 63.75, 63.75);
                upperBound = new Scalar(121.8, 255, 255);
                break;
        }
    }
}