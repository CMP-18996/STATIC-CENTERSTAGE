package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IdentifyColorCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@Disabled
@TeleOp(name="Color Sensor Test Three")
public class ColorSensorTestThree extends CommandOpMode {
    Robot robot;
    IntakeSubsystem intakeSubsystem;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);

        class TelemetryCommand extends CommandBase {
            public TelemetryCommand(String partOne, String partTwo) {
                telemetry.addData(partOne, partTwo);
                // telemetry.update();
            }
        }
        class TelemetryUpdate extends CommandBase {
            public TelemetryUpdate() {
                telemetry.update();
            }
        }

        CommandScheduler.getInstance().schedule(
                new IdentifyColorCommand(intakeSubsystem),
                new TelemetryCommand("Slot One", intakeSubsystem.slotOne.toString()),
                new TelemetryCommand("Slot Two", intakeSubsystem.slotTwo.toString()),
                new TelemetryUpdate()
        );
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            this.initialize();
            CommandScheduler.getInstance().run();
        }
    }
}
