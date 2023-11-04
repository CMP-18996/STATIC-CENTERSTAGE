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
    private boolean objectDetected = false;
    private ArrayList<MatOfPoint> arbitrary;

    // All util things
    private Scalar lowerBound;
    private Scalar upperBound;
    private int pixelVal;
    public int telemetryTestVal;
    private Mat hsvMat;
    private Mat mask;
    private Mat resized;
    private Mat negMat;
    private List<MatOfPoint> contours;
    private Mat fillerMat;
    private MatOfPoint largestContour;
    private MatOfPoint largestContourFound;
    boolean startDetecting = false;

    // TODO: Figure these values out
    private int smallestAllowedArea = 0;

    // Number of Detections of Each Prop Location
    private int leftPos = 0;
    private int middlePos = 0;
    private int rightPos = 0;

    // These are not final nor is the system for detecting final

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        this.width = width;
        this.height = height;
        this.cameraCalibration = calibration;
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        frame = detectObject(frame);
        if (!objectDetected && startDetecting) {
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
        negMat = new Mat();
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
            //arbitrary = new ArrayList<>();
            //arbitrary.add(largestContour);
            pixelVal = (int) Math.ceil(boundingRect.x + boundingRect.width / 2);
            //Imgproc.drawContours(frame, arbitrary, 0, new Scalar(42, 42, 129), -1, Imgproc.LINE_8, fillerMat, 0);
            telemetryTestVal = pixelVal;
            if (pixelVal < width * scalePercent / 3) {
                leftPos++;
            } else if (pixelVal < width * scalePercent * 2 / 3) {
                middlePos++;
            } else {
                rightPos++;
            }
        }
        return mask;
    }

    private void checkFinish() {
        if (leftPos + middlePos + rightPos >= 10) {
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
            else { // only happens in the case that it gets something like left 5 right 5 middle 0
                objectDetected = false;
            }
        }
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        if (boundingRect != null) {
            Paint paint = new Paint();
            paint.setColor(Color.RED); // Set the color to red
            canvas.drawCircle(pixelVal * 10 / 4, boundingRect.y * 10 / 4, 10, paint);
        }

        Paint paint = new Paint();
        paint.setColor(Color.RED); // Set the color to red
        canvas.drawCircle(height / 2, width / 2, 10, paint);
    }

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


    // TODO: Get values for these and uncomment
    public PropProcessor(GlobalVariables.Color teamColor) {
        switch (teamColor) {
            case RED:
                lowerBound = new Scalar(0, 15, 58.7);
                upperBound = new Scalar(10, 255, 255);
                break;
            case BLUE:
                lowerBound = new Scalar(0, 0, 0);
                upperBound = new Scalar(255, 255, 255);
                break;
        }
    }
}
