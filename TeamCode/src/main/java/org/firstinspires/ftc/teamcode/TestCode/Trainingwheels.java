package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Trainingwheels {
    private final Telemetry telemetry;
    private final DcMotor leftfrontmotor;
    private final DcMotor rightfrontmotor;
    private final DcMotor leftbackmotor;
    private final DcMotor rightbackmotor;

    public Trainingwheels(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        // Name must match your Driver Station configuration
        leftfrontmotor = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightfrontmotor = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftbackmotor = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightbackmotor = hardwareMap.get(DcMotor.class, "rightBackDrive");
    }

    public void update(Gamepad gamepad1) {
        double yleftstickPower = gamepad1.left_stick_y * .5;
        // Fixed inversion
        double xleftstickPower = gamepad1.left_stick_x * .5;
        double xrightstickPower = -gamepad1.right_stick_x * .5;

        if (gamepad1.right_bumper) {
            xrightstickPower = xrightstickPower * 2;
            yleftstickPower = yleftstickPower * 2;
            xleftstickPower = xleftstickPower * 2;
        }

        leftfrontmotor.setPower(yleftstickPower + xrightstickPower - xleftstickPower);
        rightfrontmotor.setPower(-yleftstickPower + xrightstickPower - xleftstickPower);
        leftbackmotor.setPower(yleftstickPower + xrightstickPower + xleftstickPower);
        rightbackmotor.setPower(-yleftstickPower + xrightstickPower + xleftstickPower);

        // Telemetry to view raw joystick readings on the Driver Station
        if (telemetry != null) {
            telemetry.addData("Raw Left Stick Y", gamepad1.left_stick_y);
            telemetry.addData("Raw Left Stick X", gamepad1.left_stick_x);
            telemetry.addData("Raw Right Stick X", gamepad1.right_stick_x);
            telemetry.addData("Boost", gamepad1.right_bumper);
        }
    }
}
