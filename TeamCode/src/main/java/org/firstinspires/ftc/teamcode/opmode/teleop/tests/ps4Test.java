/*
columns: 1 2 3 4 5
rows: 1
      2
 */
package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

@TeleOp(name = "ps4Test")
public class ps4Test extends CommandOpMode {
    public Robot robot;
    public Drive drive;
    public double x,y;
    public int column, row;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new Drive(robot);
        register(drive);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        x = gamepad1.touchpad_finger_1_x;
        y = gamepad1.touchpad_finger_1_y;
        column = (int) Math.floor(2.5 * (x + 0.2) + 3);
        row = (int) (-1 * Math.floor(0.5 * y) + 1);
        /*if (gamepad1.touchpad && x > 0.4) {
            drive.manualPower(0.35, 0, 0);
        } else if (gamepad1.touchpad && x < -0.4) {
            drive.manualPower(-0.35, 0, 0);
        } else if (gamepad1.touchpad && y > 0.4) {
            drive.manualPower(0, 0.35, 0);
        } else if (gamepad1.touchpad && y < -0.4) {
            drive.manualPower(0, -0.35, 0);
        } else {
            drive.manualPower(0, 0, 0);
        }*/
        if (row == 3) {
            gamepad1.rumble(1000);
        }
        telemetry.addData("Status", "Rumning...");
        telemetry.addData("Touchpad X, Y", x + " , " + y);
        telemetry.addData("Row, Column", row + " x " + column);
        telemetry.update();
    }
}
