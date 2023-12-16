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
        Pose2d p = new Pose2d(-35, 63.5, Math.toRadians(90));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 30, Math.toRadians(180) / 2, Math.toRadians(180) / 3, 13.69)
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(p)
                            .setReversed(true)
                            /*.splineTo(new Vector2d(p.getX() + Math.signum(p.getY()) * 1, p.getY() - Math.signum(p.getY()) * 30),
                                    p.getHeading() - Math.PI / 2)*/
                            /*.splineTo(new Vector2d(p.getX(), p.getY() - Math.signum(p.getY()) * 28),
                                p.getHeading())*/
                            /*.splineTo(new Vector2d(p.getX() - Math.signum(p.getY()) * 4, p.getY() - Math.signum(p.getY()) * 30),
                                    p.getHeading() + Math.PI / 2)*/
                            .splineTo(new Vector2d(-24, 58), Math.toRadians(0))
                            .splineTo(new Vector2d(54, 58), Math.toRadians(0))
                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}