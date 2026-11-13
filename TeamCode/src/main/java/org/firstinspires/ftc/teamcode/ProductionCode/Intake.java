package org.firstinspires.ftc.teamcode.ProductionCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {

    private DcMotor motor = null;
    private final String nameofmotor;

    double power = 0;
    /**
     * Initializes the motor from the hardware map safely.
     * @param hardwareMap HardwareMap passed from the OpMode
     * @param motorName Configuration name set on the Driver Station (e.g. "Intakemotor")
     */
    public Intake(HardwareMap hardwareMap, String motorName) {
        this.nameofmotor = motorName;
        try {
            motor = hardwareMap.get(DcMotor.class, nameofmotor);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Exception e) {
            motor = null; // Motor name not found in configuration
        }
    }

    /**
     * Sets direct power to the motor.
     * @param power Power level from -1.0 to 1.0
     */
    public void setPower(double power) {
        if (motor != null) {
            motor.setPower(power);
        }
    }

    /**
     * Controls the motor using single gamepad inputs.
     * @param gamepad Gamepad instance (e.g. gamepad1 or gamepad2)
     * @param telemetry Telemetry instance to display status
     */
    public void update(Gamepad gamepad, Telemetry telemetry) {
        if (motor == null) {
            if (telemetry != null) {
                telemetry.addData(nameofmotor, "NOT FOUND in robot config!");
            }
            return;
        }

        if (gamepad == null) {
            return;
        }

        if (gamepad.a) {
            power = -0.5;
            if (telemetry != null) telemetry.addData("Intake_Power", power);
        } else if (gamepad.x) {
            power = 0.5;
            if (telemetry != null) telemetry.addData("Intake_Power", power);
        } else {
            power = 0;
            if (telemetry != null) telemetry.addData("Intake_Off", power);
        }
        setPower(power);
    }

    /**
     * Overload for dual-gamepad control: checks gamepad2 (Operator) first, falls back to gamepad1 (Driver).
     */
    public void update(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        if (gamepad2 != null && (gamepad2.a || gamepad2.x)) {
            update(gamepad2, telemetry);
        } else {
            update(gamepad1, telemetry);
        }
    }
}
