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
                        drive.trajectorySequenceBuilder(new Pose2d(45, -34, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(x, y - Math.signum(y) * 50, Math.toRadians(0)))

                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(0)))

//                                .splineTo(new Vector2d(-60, -12), Math.toRadians(180))
//                                .splineTo(new Vector2d(-62, -12), Math.toRadians(180))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(35, -12, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(45, -34, Math.toRadians(0)))

//                                .splineTo(new Vector2d(42, -36), Math.toRadians(180))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}