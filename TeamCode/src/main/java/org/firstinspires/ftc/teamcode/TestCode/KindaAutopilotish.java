package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Basic Auto (Timed)", group = "Autonomous")
public class KindaAutopilotish extends LinearOpMode {

    // Declare hardware variables
    private DcMotor leftfrontmotor;
    private DcMotor rightfrontmotor;
    private DcMotor leftbackmotor;
    private DcMotor rightbackmotor;
    // Timer for time-based actions
    private ElapsedTime runtime = new ElapsedTime();
    private double power = 1;
    @Override
    public void runOpMode() {

        // 1. Map hardware to configuration names set on the Driver Station
        leftfrontmotor = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightfrontmotor = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftbackmotor = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightbackmotor = hardwareMap.get(DcMotor.class, "rightBackDrive");

        // 2. Set motor directions (one side usually needs to be reversed)
        leftfrontmotor.setDirection(DcMotor.Direction.REVERSE);
        rightfrontmotor.setDirection(DcMotor.Direction.FORWARD);
        leftbackmotor.setDirection(DcMotor.Direction.REVERSE);
        rightbackmotor.setDirection(DcMotor.Direction.FORWARD);

        // Tell driver station that initialization is complete
        telemetry.addData("Status", "Initialized");
        telemetry.update();



        // Wait for the driver to press PLAY
        waitForStart();
        runtime.reset();

        // --- AUTONOMOUS ROUTINE BEGINS HERE ---

        if (opModeIsActive()) {

            // Auto Instructions
            GoForward(90);


        }
    }
    public void GoForward(int distance) {
        // Step 1: Drive forward in inches
        double time = (double) distance /30 * power;
        telemetry.addData("Status", "Driving Forward");
        telemetry.update();

        leftfrontmotor.setPower(power);
        rightfrontmotor.setPower(power);
        leftbackmotor.setPower(power);
        rightbackmotor.setPower(power);

        sleep((long) time);
        Stop();
    }
    public void Turn(boolean Direction) {
        // Step 1: Drive forward for 2.0 seconds at 50% power
        telemetry.addData("Status", "Turning");
        telemetry.update();
        if (Direction) {
            leftfrontmotor.setPower(-power);
            rightfrontmotor.setPower(power);
            leftbackmotor.setPower(-power);
            rightbackmotor.setPower(power);
        } else {
            leftfrontmotor.setPower(power);
            rightfrontmotor.setPower(-power);
            leftbackmotor.setPower(power);
            rightbackmotor.setPower(-power);
        }

        sleep(1500);
        Stop();
    }
    public void Stop() {
        telemetry.addData("Status", "Stop");
        telemetry.update();

        leftfrontmotor.setPower(0);
        rightfrontmotor.setPower(0);
        leftbackmotor.setPower(0);
        rightbackmotor.setPower(0);
    }
}