package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

import java.util.logging.Handler;

public class HangCommand extends InstantCommand {
    public HangCommand(MiscSubsystem miscSubsystem, double hangPower) {
        miscSubsystem.hang(hangPower);
    }
}
