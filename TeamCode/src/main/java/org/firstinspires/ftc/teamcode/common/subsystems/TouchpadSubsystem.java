package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

public class TouchpadSubsystem extends SubsystemBase {
    public Gamepad g;
    private boolean b = true;
    public ArrayList<Integer> history = new ArrayList<>();
    public TouchpadSubsystem(Gamepad g) {
        this.g = g;
    }
    @Override
    public void periodic() {
        int column = (int) Math.floor(2.5 * (g.touchpad_finger_1_x + 0.2) + 3);
        int row = (int) (-1 * Math.floor(0.5 * g.touchpad_finger_1_y) + 1);
        if (g.touchpad) {
            if (b) {
                history.add(column + 5 * (row - 1));
                if (history.size() > 4) {
                    history.remove(0);
                }
                b = false;
            }
        } else {
            b = true;
        }
    }
    public void deleteLastInput() {
        if (history.size() != 0) {
            history.remove(history.size() - 1);
        }
    }
    //note that the latest entry will be second
    public ArrayList<Integer> getHistory() {
        ArrayList<Integer> i = new ArrayList<>();
        if (history.size() == 0) {
            i.add(0);
            i.add(0);
        } else if (history.size() == 1) {
            i.add(history.get(0));
            i.add(0);
        } else {
            i.add(history.get(history.size() - 2));
            i.add(history.get(history.size() - 1));
        }
        return i;
    }
    public Gamepad getGamepad() {return g;}
    public void rumble(int ms) {g.rumble(ms);}
}