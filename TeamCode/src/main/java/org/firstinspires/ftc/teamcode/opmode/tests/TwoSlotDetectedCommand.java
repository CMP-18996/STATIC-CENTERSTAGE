package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
@TeleOp(name="Slots Test",group="test")
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
