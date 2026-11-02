package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;

public class BasicMotor {

    private DcMotor motor = null;
    private final String motorName;
    private boolean ismotoron = false;

    double power = 0;
    /**
     * Initializes the motor from the hardware map safely.
     * @param hardwareMap HardwareMap passed from the OpMode
     * @param motorName Configuration name set on the Driver Station (e.g. "Intakemotor")
     */
    public BasicMotor(HardwareMap hardwareMap, String motorName) {
        this.motorName = motorName;
        try {
            motor = hardwareMap.get(DcMotor.class, motorName);
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
     * Controls the motor using gamepad triggers and logs telemetry.
     * Checks Gamepad 2 first; if idle, falls back to Gamepad 1 triggers.
     * @param gamepad1 Gamepad 1 (Driver 1)
     * @param gamepad2 Gamepad 2 (Driver 2)
     * @param telemetry Telemetry instance to display status
     */
    public void update(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        if (motor == null) {
            if (telemetry != null) {
                telemetry.addData(motorName, "NOT FOUND in robot config!");
            }
            return;
        }


        if (gamepad1.a){
            if (ismotoron == false) {
                power = 1;
                ismotoron = true;
            }
            else {
                power = 0;
                ismotoron = false;
            }
        }
        setPower(power);

        if (telemetry != null) {
            telemetry.addData(motorName, ismotoron);
        }
    }

    /**
     * Overload for single-gamepad control.
     */
    public void update(Gamepad gamepad, Telemetry telemetry) {
        update(gamepad, null, telemetry);
    }
}
