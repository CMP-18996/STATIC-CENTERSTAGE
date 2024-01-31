package com.example.meepmeepreborn;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        Pose2d p = new Pose2d(-35, -61, Math.toRadians(90));
        double x = p.getX();
        double y = p.getY();
        double h = p.getHeading();

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, Math.toRadians(360), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(p)
                                //.lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3, y - Math.signum(y) * 16, h - (Math.signum(y) * Math.signum(x) - 1) * Math.toRadians(17.5) + Math.toRadians(18)))
                                .lineToLinearHeading(new Pose2d(x + Math.signum(x) * 3, y - Math.signum(y) * 16, h - (Math.signum(y) * Math.signum(x) + 1) * Math.toRadians(17.5) - Math.toRadians(18)))
                                .lineToLinearHeading(new Pose2d(-35,-59.5, Math.toRadians(0)))
                                .splineToConstantHeading(new Vector2d(25, -59.5), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(42, -36), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .splineToConstantHeading(new Vector2d(27, 0), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(-60, 0), Math.toRadians(180))
                                .setReversed(false)
                                .strafeRight(12)
                                .waitSeconds(0.5)
                                .splineToConstantHeading(new Vector2d(27, 0), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(42, -36), Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}