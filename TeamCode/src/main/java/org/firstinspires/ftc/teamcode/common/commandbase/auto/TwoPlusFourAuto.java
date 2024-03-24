package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class TwoPlusFourAuto extends SequentialCommandGroup {
    public TwoPlusFourAuto(DepositSubsystem deposit, LiftSubsystem lift, Camera camera, SampleMecanumDrive drive, IntakeSubsystem intake){
        addCommands(
                new TwoPlusZeroAuto(deposit, lift, camera, drive, intake, false),
                new StackCycleCommand(drive, intake, lift, deposit)
        );
    }
}

