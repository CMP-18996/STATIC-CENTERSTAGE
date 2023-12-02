package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(new Pose2d(-35, 62, Math.toRadians(90)))
                            .setReversed(true)
                            .splineTo(new Vector2d(-37, 33), Math.toRadians(180))
                            .waitSeconds(1)
                            .splineToSplineHeading(new Pose2d(-24, 59, Math.toRadians(180.1)), Math.toRadians(0))
                            .splineToSplineHeading(new Pose2d(52, 36, Math.toRadians(180)), Math.toRadians(0))
                            .setReversed(false)
                            .splineTo(new Vector2d(-62, 35), Math.toRadians(180))
                            .setReversed(true)
                            .splineTo(new Vector2d(53,  35), Math.toRadians(0))
                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}