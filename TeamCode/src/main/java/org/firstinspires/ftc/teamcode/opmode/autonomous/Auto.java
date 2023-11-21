package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.other.InstructionInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@Autonomous(name = "Auto")
public class Auto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.color = GlobalVariables.Color.BLUE;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
        drive = new MecanumDrive(hardwareMap, Distance.FAR.getP());
        File f = new File("/sdcard/FIRST/instructions.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileWriter myWriter = new FileWriter(f.getName());
            myWriter.write("Variables\n" +
                    "Team Blue\n" +
                    "Distance Far\n" +
                    "\n" +
                    "Commands\n" +
                    "Approach\n" +
                    "Align\n" +
                    "Deposit Prop Pixel\n" +
                    "Cycle Stack\n" +
                    "Align\n" +
                    "Cycle Stack\n" +
                    "Align\n" +
                    "\n" +
                    "End");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InstructionInterpreter i = new InstructionInterpreter(robot, drive, telemetry);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        robot.camera.startPropProcessing();
        CommandScheduler.getInstance().run();
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
