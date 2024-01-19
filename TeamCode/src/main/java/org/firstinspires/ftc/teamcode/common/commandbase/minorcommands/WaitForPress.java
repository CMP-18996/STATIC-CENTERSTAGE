package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class WaitForPress extends CommandBase {
    GamepadEx gamepadEx;
    public WaitForPress(GamepadEx gamepadEx) {
        this.gamepadEx = gamepadEx;
    }

    public boolean isFinished() {
        return gamepadEx.wasJustPressed(GamepadKeys.Button.A);
    }
}
