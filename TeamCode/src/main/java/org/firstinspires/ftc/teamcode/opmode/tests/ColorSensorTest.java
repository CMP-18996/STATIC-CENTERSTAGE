package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Command;


import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;


@TeleOp
public class ColorSensorTest extends CommandOpMode {
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;
    Robot robot;
    int r1, b1, g1, r2, b2, g2;
    public IntakeSubsystem.ColorState slotOne;
    public IntakeSubsystem.ColorState slotTwo;

    IntakeSubsystem intakeSubsystem;
    @Override
    public void initialize() {
        class TelemetryCommand extends CommandBase {
            public TelemetryCommand(String partOne, String partTwo) {
                telemetry.addData(partOne, partTwo);
                telemetry.update();
            }
        }

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new InstantCommand(() ->intakeSubsystem.identifyColor()),
                        new TelemetryCommand("Slot One", intakeSubsystem.slotOne.toString()),
                        new TelemetryCommand("Slot Two", intakeSubsystem.slotTwo.toString()),
                        new WaitCommand(20)

                )
                );


    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

    }


}
