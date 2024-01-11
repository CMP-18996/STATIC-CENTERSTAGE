package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;

import java.util.ArrayList;

public class TouchpadAndDisplaySubsystem extends SubsystemBase {
    public Gamepad g;
    private boolean b = true;
    public ArrayList<Integer> history = new ArrayList<>();
    private AdaDisplay display1;
    private AdaDisplay display2;
    ChoosingOptions currentOption = ChoosingOptions.ROW;
    LeftRightState leftRightState = LeftRightState.LEFT;
    public int leftRow;
    public int leftColumn;
    public int rightRow;
    public int rightColumn;
    public boolean isChosen = false;
    private int mostRecentlyPressed = 0;
    AdaDisplay usedDisplay;
    public enum LeftRightState {
        LEFT,
        RIGHT,
        CHOSEN
    }
    public enum ChoosingOptions {
        ROW,
        COLUMN,
        CHOSEN
    }
    public TouchpadAndDisplaySubsystem(Gamepad g, AdaDisplay display1, AdaDisplay display2) {

        this.g = g;
        this.display1 = display1;
        this.display2 = display2;
    }
    @Override
    public void periodic() {
        int column = (int) Math.floor(2.5 * (g.touchpad_finger_1_x + 0.2) + 3);
        int row = (int) (-1 * Math.floor(0.5 * g.touchpad_finger_1_y) + 1);
        if (g.touchpad) {
            if (b) {
                int num = column + 5 * (row - 1);
                mostRecentlyPressed = num;
                if (leftRightState == LeftRightState.CHOSEN && num == 10) {
                    clearArray();
                }
                usedDisplay = display1;

                switch (leftRightState) {
                    case LEFT:
                        usedDisplay = display1;
                        if (num == 10 && history.size() >= 1) {
                            if (currentOption == ChoosingOptions.ROW) {
                                leftRow = history.get(1);
                                leftColumn = history.get(0);
                            } else {
                                leftColumn = history.get(1);
                                leftRow = history.get(0);
                            }
                            currentOption = ChoosingOptions.ROW;
                            leftRightState = LeftRightState.RIGHT;
                        }
                        break;
                    case RIGHT:
                        usedDisplay = display2;
                        if (num == 10 && history.size() >= 1) {
                            if (currentOption == ChoosingOptions.ROW) {
                                rightRow = history.get(1);
                                rightColumn = history.get(0);
                            } else {
                                rightRow = history.get(0);
                                rightColumn = history.get(1);
                            }
                            isChosen = true;
                            leftRightState = LeftRightState.CHOSEN;
                        }
                        break;
                    case CHOSEN:
                        currentOption = ChoosingOptions.CHOSEN;
                        if (num == 10) {
                            isChosen = false;
                            currentOption = ChoosingOptions.ROW;
                            leftRightState = LeftRightState.LEFT;
                            clearArray();
                        }
                        break;
                }

                switch (currentOption) {
                    case ROW:
                        usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.DASH);
                        switch (num) {
                            case 0:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.ZERO);
                                break;
                            case 1:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.ONE);
                                break;
                            case 2:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.TWO);
                                break;
                            case 3:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.THREE);
                                break;
                            case 4:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.FOUR);
                                break;
                            case 5:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.FIVE);
                                break;
                            case 6:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.SIX);
                                break;
                            case 7:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.SEVEN);
                                break;
                            case 8:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.EIGHT);
                                break;
                            case 9:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.NINE);
                                break;
                        }
                        currentOption = ChoosingOptions.COLUMN;
                        break;

                    case COLUMN:
                        // TODO : MAKE DEVICE TWO NOT ONE
                        switch (num) {
                            case 0:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.DASH);
                                break;
                            case 1:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_A);
                                break;
                            case 2:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_B);
                                break;
                            case 3:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_C);
                                break;
                            case 4:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_D);
                                break;
                            case 5:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_E);
                                break;
                            case 6:
                                usedDisplay.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.CHAR_F);
                                break;
                        }
                        currentOption = ChoosingOptions.ROW;
                        break;
                }

                history.add(num);

                if (history.size() > 4) {
                    history.remove(0);
                }
                b = false;

                switch (leftRightState) {
                    case LEFT:
                        display1.startFlashing();
                        display2.stopFlashing();
                        break;
                    case RIGHT:
                        display1.stopFlashing();
                        display2.startFlashing();
                        break;
                    case CHOSEN:
                        display1.stopFlashing();
                        display2.stopFlashing();
                        break;
                }
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

    public String getLeftRightState() {
        return leftRightState.toString();
    }

    public String getOptionState() {
        return currentOption.toString();
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

    public int getMostRecentlyPressed() { return mostRecentlyPressed; }

    public int getLeftRow() {
        return leftRow;
    }

    public int getRightRow() {
        return rightRow;
    }

    public int getLeftColumn() {
        return leftColumn;
    }

    public int getRightColumn() {
        return rightColumn;
    }

    public Gamepad getGamepad() { return g; }
    public void rumble(int ms) {g.rumble(ms);}

    public void clearArray() {
        display1.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.DASH);
        display1.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.DASH);
        display2.writeCharacter(AdaDisplay.DeviceNumber.ONE, AdaDisplay.AvailableCharacters.DASH);
        display2.writeCharacter(AdaDisplay.DeviceNumber.TWO, AdaDisplay.AvailableCharacters.DASH);
    }

    public void reset() {
        clearArray();
        leftRightState = LeftRightState.LEFT;
        currentOption = ChoosingOptions.ROW;
        isChosen = false;
        history = new ArrayList<>();
    }
}