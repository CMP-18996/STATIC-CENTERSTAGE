package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

public class TwoSlotDetectedCommand extends CommandBase {
    @Override
    public boolean isFinished() {
        return true; // return {function that returns true or false depending on if both slots are filled}
    }
}
