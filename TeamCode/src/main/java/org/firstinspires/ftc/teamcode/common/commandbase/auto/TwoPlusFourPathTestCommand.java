package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.drive.trajectorysequence.sequencesegment.TrajectorySegment;

public class TwoPlusFourPathTestCommand extends SequentialCommandGroup {
    public TwoPlusFourPathTestCommand(SampleMecanumDrive drive) {
//        Pose2d p = new Pose2d(12, -61, Math.toRadians(90));
        Pose2d p = drive.getPoseEstimate();
        double x = p.getX();
        double y = p.getY();
        double h = p.getHeading();

        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(12, -61, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(x, y - Math.signum(y) * 20, h))

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(36, -57, Math.toRadians(90)))
                .setReversed(false)
                .splineTo(new Vector2d(42, -36), Math.toRadians(0))

                .setReversed(true)
                .lineTo(new Vector2d(-62, -36))
                .waitSeconds(0.5)
                .setReversed(false)
                .lineTo(new Vector2d(42, -36))
                .waitSeconds(2)
                .build();
        drive.followTrajectorySequence(trajectory);
    }
}
