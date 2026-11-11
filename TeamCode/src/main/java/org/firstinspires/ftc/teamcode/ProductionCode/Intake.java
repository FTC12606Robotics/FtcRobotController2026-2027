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
     * Controls the motor using gamepad triggers and logs telemetry.
     * Checks Gamepad 2 first; if idle, falls back to Gamepad 1 triggers.
     * @param gamepad1 Gamepad 1 (Driver 1)
     * @param gamepad2 Gamepad 2 (Driver 2)
     * @param telemetry Telemetry instance to display status
     */
    public void update(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        if (motor == null) {
            if (telemetry != null) {
                telemetry.addData(nameofmotor, "NOT FOUND in robot config!");
            }
            return;
        }


        if (gamepad1.a){
            power = -.5;
            telemetry.addData("Intake_Power", power);
        }
        else {
            power = 0;
            telemetry.addData("Intake_Off", power);
        }
        setPower(power);

        if (telemetry != null) {

        }
    }

    /**
     * Overload for single-gamepad control.
     */
    public void update(Gamepad gamepad, Telemetry telemetry) {
        update(gamepad, null, telemetry);
    }
}
