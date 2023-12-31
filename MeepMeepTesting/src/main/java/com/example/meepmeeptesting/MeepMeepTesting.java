package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.000000000000001;
        if (imag == 0) imag += 0.000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        if (h >= 2 * Math.PI) h -= 2 * Math.PI;
        return h;
    }
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        Pose2d p = new Pose2d(12, 62, Math.toRadians(-90));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 30, Math.toRadians(180), Math.toRadians(180), 13.69)
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(p)
                            //.splineTo(new Vector2d(p.getX(), p.getY() - Math.signum(p.getY()) * 0), p.getHeading()+0.00001)
                            .splineToConstantHeading(new Vector2d(p.getX(), p.getY() - Math.signum(p.getY()) * 15.5), p.getHeading() + Math.toRadians(45))
                            /*.splineTo(new Vector2d(p.getX(), p.getY() - Math.signum(p.getY()) * 33),
                                    p.getHeading() + Math.PI / 2)*/
                            /*.splineTo(new Vector2d(p.getX(), p.getY() - Math.signum(p.getY()) * 27),
                                p.getHeading())*/
                            /*.splineTo(new Vector2d(p.getX() - Math.signum(p.getY()) * 2, p.getY() - Math.signum(p.getY()) * 33),
                                    p.getHeading() - Math.PI / 2)*/
                            //.turn(Math.toRadians(180))
                            //.splineTo(new Vector2d(-24, -36), Math.toRadians(0))
                            .splineTo(new Vector2d(20, -36), Math.toRadians(0))
                            .setReversed(true)
                            .splineTo(new Vector2d(-62, -36), Math.toRadians(180))
                            .waitSeconds(0.5)
                            .setReversed(false)
                            .splineTo(new Vector2d(20, -36), Math.toRadians(0))
                            .waitSeconds(2)
                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}