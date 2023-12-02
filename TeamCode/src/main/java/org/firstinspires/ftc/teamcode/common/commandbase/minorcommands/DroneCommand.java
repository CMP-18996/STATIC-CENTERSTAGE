package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

public class DroneCommand extends InstantCommand {
    public DroneCommand(MiscSubsystem miscSubsystem) {
        super(
                () -> miscSubsystem.releaseDrone()
        );
    }
}
