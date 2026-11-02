package org.firstinspires.ftc.teamcode.TestCode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LogitechAprilTagVision {

    private final AprilTagProcessor aprilTag;
    private final VisionPortal visionPortal;

    public LogitechAprilTagVision(HardwareMap hardwareMap) {
        AprilTagProcessor tempProcessor = null;
        VisionPortal tempPortal = null;
        try {
            tempProcessor = new AprilTagProcessor.Builder()
                    .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary()) // Automatically loads current season tags
                    .build();

            // Set up camera
            tempPortal = new VisionPortal.Builder()
                    // Specify Camera (logitech)
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    // Add April Tag Processor
                    .addProcessor(tempProcessor)
                    // Set Camera Resolution
                    .setCameraResolution(new Size(640, 480))
                    // Set format
                    .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                    // Conclude
                    .build();
        } catch (Exception e) {
            tempProcessor = null;
            tempPortal = null;
        }

        aprilTag = tempProcessor;
        visionPortal = tempPortal;
    }

    public List<AprilTagDetection> getDetections() {
        if (aprilTag == null) {
            return Collections.emptyList();
        }
        return aprilTag.getDetections();
    }

    public void updateTelemetry(Telemetry telemetry) {
        if (visionPortal == null || aprilTag == null) {
            telemetry.addLine("Webcam 1: NOT FOUND (Check Camera & Config)");
            return;
        }

        List<AprilTagDetection> currentDetections = getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        if (currentDetections.isEmpty()) {
            telemetry.addLine("No AprilTags visible");
            return;
        }

        // Loop through each detected tag
        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
                if (singleDet.metadata != null) {
                    telemetry.addLine(String.format(Locale.US, "\nTag ID: %d (%s)", singleDet.id, singleDet.metadata.name));
                } else {
                    telemetry.addLine(String.format(Locale.US, "\nUnknown Tag ID: %d", singleDet.id));
                }
            } else if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if (clusterDet.metadata != null) {
                    telemetry.addLine(String.format(Locale.US, "\nTag Cluster (%s)", clusterDet.metadata.name));
                } else {
                    telemetry.addLine("\nTag Cluster (Unknown)");
                }
            }

            if (detection.ftcPose != null) {
                telemetry.addLine(String.format(Locale.US, "X: %.2f in, Y: %.2f in, Z: %.2f in",
                        detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format(Locale.US, "Yaw: %.2f°, Pitch: %.2f°, Roll: %.2f°",
                        detection.ftcPose.yaw, detection.ftcPose.pitch, detection.ftcPose.roll));
            }
        }
    }

    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
