package org.firstinspires.ftc.teamcode.opmode.tests.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name="Field Centric Drive",group="test")
public class FieldCentric extends CommandOpMode {
    enum HeadingSource {
        ODO,
        IMU
    }
    private MecanumDrive drive;
    double heading = 0;
    double y, x;
    HeadingSource hs = HeadingSource.ODO;
    public double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.0000000000000000001;
        if (imag == 0) imag += 0.0000000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        if (h < 0) h += 2 * Math.PI;
        return h;
    }
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        drive.imu.resetYaw();

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        if (gamepad1.x) hs = HeadingSource.ODO;
        if (gamepad1.y) hs = HeadingSource.IMU;
        switch (hs) {
            case ODO: heading = calculateHeading(drive.pose.heading.real, drive.pose.heading.imag);
            case IMU: heading = drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        }
        y = (gamepad1.left_stick_x * Math.sin(heading) + gamepad1.left_stick_y * Math.cos(heading));
        x = (gamepad1.left_stick_x * Math.cos(heading) - gamepad1.left_stick_y * Math.sin(heading));
        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(-y, -x), -gamepad1.right_stick_x));
        drive.updatePoseEstimate();

        telemetry.addData("x", drive.pose.position.x);
        telemetry.addData("y", drive.pose.position.y);
        telemetry.addData("heading", Math.toDegrees(heading));
        telemetry.addLine();
        telemetry.addData("Instructions", "Press X for odo heading, Y for imu heading.");
        telemetry.update();
    }
}
