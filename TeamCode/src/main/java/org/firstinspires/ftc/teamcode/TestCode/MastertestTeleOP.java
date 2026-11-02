package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// Set as human control, sorry AI :(
@TeleOp(name="MastertestTeleOP", group="Linear Opmode")
public class MastertestTeleOP extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Trainingwheels drivetrain = new Trainingwheels(hardwareMap, telemetry);
        //LogitechAprilTagVision aprilTagVision = new LogitechAprilTagVision(hardwareMap);
        BasicMotor testMotor = new BasicMotor(hardwareMap, "Intakemotor");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            drivetrain.update(gamepad1);
            testMotor.update(gamepad1, gamepad2, telemetry);
            //aprilTagVision.updateTelemetry(telemetry);
            telemetry.update();
        }

        //aprilTagVision.close();
    }
}
