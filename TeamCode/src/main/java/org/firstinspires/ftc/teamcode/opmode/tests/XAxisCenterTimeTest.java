package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

@TeleOp
public class XAxisCenterTimeTest extends CommandOpMode {
    Robot robot;
    DepositSubsystem depositSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        depositSubsystem = new DepositSubsystem(robot);

    }
}
