package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.Drivers.HT16K33;

import java.util.ArrayList;

public class TouchpadSubsystem extends SubsystemBase {
    public Gamepad g;
    private boolean b = true;
    public ArrayList<Integer> history = new ArrayList<>();
    private HT16K33 display;
    ChoosingOptions currentOption = ChoosingOptions.ROW;
    public enum ChoosingOptions {
        ROW,
        COLUMN
    }
    public TouchpadSubsystem(Gamepad g, HT16K33 display) {

        this.g = g;
        this.display = display;
    }
    @Override
    public void periodic() {
        int column = (int) Math.floor(2.5 * (g.touchpad_finger_1_x + 0.2) + 3);
        int row = (int) (-1 * Math.floor(0.5 * g.touchpad_finger_1_y) + 1);
        if (g.touchpad) {
            if (b) {
                int num = column + 5 * (row - 1);
                history.add(num);
                switch (currentOption) {
                case ROW:
                    switch (num) {
                        case 0:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.ZERO);
                            break;
                        case 1:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.ONE);
                            break;
                        case 2:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.TWO);
                            break;
                        case 3:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.THREE);
                            break;
                        case 4:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.FOUR);
                            break;
                        case 5:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.FIVE);
                            break;
                        case 6:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.SIX);
                            break;
                        case 7:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.SEVEN);
                            break;
                        case 8:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.EIGHT);
                            break;
                        case 9:
                            display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.NINE);
                            break;
                    }
                    currentOption = ChoosingOptions.COLUMN;
                    break;

                case COLUMN:
                    switch (num) {
                        case 0:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_A);
                            break;
                        case 1:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_B);
                            break;
                        case 2:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_C);
                            break;
                        case 3:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_D);
                            break;
                        case 4:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_E);
                            break;
                        case 5:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_F);
                            break;
                        case 6:
                            display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.CHAR_G);
                            break;
                    }
                    currentOption = ChoosingOptions.ROW;
                    break;
                }

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

    public void clearArray() {
        display.writeCharacter(HT16K33.DeviceNumber.ONE, HT16K33.AvailableCharacters.DASH);
        display.writeCharacter(HT16K33.DeviceNumber.TWO, HT16K33.AvailableCharacters.DASH);
    }
}