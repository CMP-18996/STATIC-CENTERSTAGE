package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;

import java.util.ArrayList;

public class RewrittenTouchpad extends SubsystemBase {
    private Gamepad g;
    private boolean b = true;
    private HT16K33 display;
    private ArrayList<Integer> history;
    @Override
    public void periodic() {
        if (g.touchpad) {
            if (b) {
                int column = (int) Math.floor(2.5 * (g.touchpad_finger_1_x + 0.2) + 3);
                int row = (int) (-1 * Math.floor(0.5 * g.touchpad_finger_1_y) + 1);
                int num = column + 5 * (row - 1);
                history.add(num);
                if (num == 10) {

                }
            }
        } else {
            b = true;
        }
    }
}
