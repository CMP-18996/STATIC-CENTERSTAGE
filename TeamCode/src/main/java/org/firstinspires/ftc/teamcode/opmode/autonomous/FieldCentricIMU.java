package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@TeleOp(name="Field Centric Drive with IMU only")
public class FieldCentricIMU extends LinearOpMode {
    double heading = 0;
    double y, x;
    double imuI = 0.5;
    public double calculateHeading(double real, double imag) {
        /*if (real == 0) real += 0.0000000000000000001;
        if (imag == 0) imag += 0.0000000000000000001;*/
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        //if (h < 0) h += 2 * Math.PI;
        return h;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        drive.imu.resetYaw();
        waitForStart();

        while (opModeIsActive()) {
            heading = imuI * drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) +
                    (1 - imuI) * calculateHeading(drive.pose.heading.real, drive.pose.heading.imag);
            y = (gamepad1.left_stick_x * Math.sin(heading) + gamepad1.left_stick_y * Math.cos(heading));
            x = (gamepad1.left_stick_x * Math.cos(heading) - gamepad1.left_stick_y * Math.sin(heading));
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(-y, -x), -gamepad1.right_stick_x));

            drive.updatePoseEstimate();

            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading", Math.toDegrees(heading));
            telemetry.update();
        }
    }
}
