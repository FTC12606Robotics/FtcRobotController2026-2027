package org.firstinspires.ftc.teamcode.ProductionCode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IronCross {
    private CRServo servo = null;
    private final String servoName;

    // Defined target speeds/powers
    public static final double POWER_FORWARD = -0.5;
    public static final double POWER_REVERSE = 0.5;
    public static final double POWER_STOP = 0.0;

    // Initialize
    public IronCross(HardwareMap hardwareMap, String servoName) {
        this.servoName = servoName;
        try {
            servo = hardwareMap.get(CRServo.class, servoName);
            servo.setPower(POWER_STOP); // Stop initial movement
        } catch (Exception e) {
            servo = null;
        }
    }

    // Move servo while bumpers are held down
    public void update(Gamepad gamepad, Telemetry telemetry) {
        if (servo == null) {
            if (telemetry != null) {
                telemetry.addData(servoName, "NOT FOUND in robot config!");
            }
            return;
        }

        if (gamepad == null) {
            return;
        }

        if (gamepad.right_bumper) {
            servo.setPower(POWER_FORWARD);
            telemetry.addData(servoName, "FORWARD");
        } else if (gamepad.left_bumper) {
            servo.setPower(POWER_REVERSE);
            telemetry.addData(servoName, "REVERSE");
        } else {
            servo.setPower(POWER_STOP);
            telemetry.addData(servoName, "STOPPED");
        }
    }

    /**
     * Overload for dual-gamepad control: checks gamepad2 (Operator) first, falls back to gamepad1 (Driver).
     */
    public void update(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        if (gamepad2 != null && (gamepad2.right_bumper || gamepad2.left_bumper)) {
            update(gamepad2, telemetry);
        } else {
            update(gamepad1, telemetry);
        }
    }
}
