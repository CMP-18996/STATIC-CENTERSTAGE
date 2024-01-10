package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public double calculateHeading(double real, double imag) {
        if (real == 0) real += 0.000000000000001;
        if (imag == 0) imag += 0.000000000000001;
        double h = Math.atan(imag / real);
        if (real < 0) h += Math.PI;
        if (h >= 2 * Math.PI) h -= 2 * Math.PI;
        return h;
    }
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, Math.toRadians(180) / 1.5, Math.toRadians(180) / 1.5, 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -62, Math.toRadians(90)))
                .lineToYLinearHeading(-40, Math.toRadians(90))
                .setReversed(false)
                .splineTo(new Vector2d(-35, -39), Math.toRadians(90))
                .splineTo(new Vector2d(-24, -36), Math.toRadians(0))
                .splineTo(new Vector2d(36, -36), Math.toRadians(0))
                .waitSeconds(2)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}