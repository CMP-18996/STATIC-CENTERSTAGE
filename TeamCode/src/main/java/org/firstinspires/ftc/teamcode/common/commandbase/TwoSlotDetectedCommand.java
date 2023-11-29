package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class TwoSlotDetectedCommand extends CommandBase {
    IntakeSubsystem intakeSubsystem;

    public TwoSlotDetectedCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public boolean isFinished() {
        intakeSubsystem.identifyColor();
        return intakeSubsystem.checkFilled(); // return {function that returns true or false depending on if both slots are filled}
    }
}
