package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.drive.Drive;

import com.enforcers7149.touchpadplusplus.src.TouchObjects.*;
import com.enforcers7149.touchpadplusplus.src.Utils.*;
import com.enforcers7149.touchpadplusplus.src.Utils.Bounds.*;
import com.enforcers7149.touchpadplusplus.src.Touchpad;

@TeleOp(name = "ps4Test")
public class ps4Test extends CommandOpMode {
    public Robot robot;
    public Drive drive;

    Touchpad touchpad;
    TouchpadHandler handler = new TouchpadHandler();
    Button topRight, topLeft, center;
    Swipe rotateLeft, rotateRight;
    @Override
    public void initialize() {
        telemetry.addData("Status", "Initalizing...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.TELEOP);
        drive = new Drive(robot);
        register(drive);

        touchpad = new Touchpad(gamepad1);
        topRight = new Button(touchpad, false, RectBounds.TOP_RIGHT, true);
        topLeft = new Button(touchpad, false, RectBounds.TOP_LEFT, true);
        center = new Button(touchpad, false, RectBounds.CENTER, true);
        rotateLeft = new Swipe(touchpad, false, Swipe.SwipeType.LEFT_SWIPE);
        rotateRight = new Swipe(touchpad, false, Swipe.SwipeType.RIGHT_SWIPE);
        handler.addInputs(touchpad, topRight, rotateLeft, rotateRight);

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.manualPower(0, gamepad1.left_stick_y, 0);

        handler.updateInputs();
        if (topLeft.get()) drive.manualPower(-0.4, 0, 0);
        if (topRight.get()) drive.manualPower(0.4, 0, 0);
        if (center.get()) drive.manualPower(0, 0, 0);
        if (rotateLeft.get()) drive.manualPower(0, 0, -0.4);
        if (rotateRight.get()) drive.manualPower(0, 0, 0.4);

        telemetry.addData("Status", "Rumning...");
        telemetry.addLine();
        telemetry.addData("PRESS top left/right", "Strafe left/right");
        telemetry.addData("SWIPE left/right", "Turn left/right");
        telemetry.addData("PRESS center", "Stop motion");
        telemetry.update();
    }
}