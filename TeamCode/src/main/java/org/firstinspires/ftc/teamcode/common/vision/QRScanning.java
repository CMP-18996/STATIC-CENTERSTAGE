package org.firstinspires.ftc.teamcode.common.vision;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.objdetect.QRCodeDetector;

public class QRScanning extends OpenCvPipeline {
    

    @Override
    public Mat processFrame(Mat mat) {
        QRCodeDetector detector = new QRCodeDetector();
        return mat;
    }
}
