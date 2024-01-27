package com.example.meepmeepreborn;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        Pose2d p = new Pose2d(12, -61, Math.toRadians(90));
        double x = p.getX();
        double y = p.getY();
        double h = p.getHeading();

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, Math.toRadians(360), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -61, Math.toRadians(90)))
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
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}