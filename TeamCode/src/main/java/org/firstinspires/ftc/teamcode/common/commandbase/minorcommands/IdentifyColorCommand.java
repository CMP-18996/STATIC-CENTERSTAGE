package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class IdentifyColorCommand extends CommandBase {
    public IdentifyColorCommand(IntakeSubsystem intake) {
        intake.identifyColor();
    }
}
