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
                /*.followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12.18, 64, Math.toRadians(-90)))
                                .splineTo(new Vector2d(47, 45.5), Math.toRadians(0))
                                .build()
                );*/
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(new Pose2d(-35.08, 64, Math.toRadians(-90)))
                            .splineTo(new Vector2d(-36, 24), Math.toRadians(-90))
                            .splineToSplineHeading(new Pose2d(0, 12, 0), Math.toRadians(0))
                            .splineTo(new Vector2d(47, 45.5), Math.toRadians(0))
                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}