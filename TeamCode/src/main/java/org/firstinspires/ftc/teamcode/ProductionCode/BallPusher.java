package org.firstinspires.ftc.teamcode.ProductionCode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BallPusher {
    private Servo servo = null;
    private final String servoName;

    // Defined target positions (0.0 to 1.0)
    public static final double POSITION_RETRACTED = 0.0;
    public static final double POSITION_EXTENDED = 1; // Adjust angle (0.5 = 90 deg, 1.0 = 180 deg)

    // Initialize
    public BallPusher(HardwareMap hardwareMap, String servoName) {
        this.servoName = servoName;
        try {
            servo = hardwareMap.get(Servo.class, servoName);
            servo.setPosition(POSITION_RETRACTED); // Set initial position
        } catch (Exception e) {
            servo = null;
        }
    }

    // Move servo position while button is held down
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

        if (gamepad.dpad_right) {
            servo.setPosition(POSITION_EXTENDED);
            if (telemetry != null) telemetry.addData(servoName, "EXTENDED");
        } else {
            servo.setPosition(POSITION_RETRACTED);
            if (telemetry != null) telemetry.addData(servoName, "RETRACTED");
        }
    }

    /**
     * Overload for dual-gamepad control: checks gamepad2 (Operator) first, falls back to gamepad1 (Driver).
     */
    public void update(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        if (gamepad2 != null && gamepad2.dpad_right) {
            update(gamepad2, telemetry);
        } else {
            update(gamepad1, telemetry);
        }
    }
}
