package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.opmode.tests.DroneMotorTest;

public class DroneMotorTestCommand extends CommandBase {
    MiscSubsystem miscSubsystem;
    public DroneMotorTestCommand(MiscSubsystem miscSubsystem) {
        this.miscSubsystem = miscSubsystem;
        miscSubsystem.droneMotorTest();
    }

}
