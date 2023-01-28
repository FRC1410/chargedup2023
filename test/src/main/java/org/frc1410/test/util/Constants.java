package org.frc1410.test.util;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

import java.util.ArrayList;
import java.util.List;

public interface Constants {
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double KS = 0.67; // SysID: 0.55
    double KV = 2.67; // SysID: 2.15
    double KA = 0.34; // SysID: 0.30

    double KS_SLOW = 0.55;
    double KV_SLOW = 2.15;
    double KA_SLOW = 0.30;

    double GEARING = (11.0 / 62) * (24.0 / 54);
    double METERS_PER_REVOLUTION = .478778;
    double ENCODER_CONSTANT = GEARING * (1. / 2048.) * METERS_PER_REVOLUTION * 1.053;
    double TRACKWIDTH = 0.615; //Meters
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

    // AprilTags
    List<Integer> RED_TAGS = new ArrayList<>(List.of(1, 2, 3, 4));
    List<Integer> BLUE_TAGS = new ArrayList<>(List.of(5, 6, 7, 8));

    AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(List.of(
            new AprilTag(1, new Pose3d(15.513558, Units.inchesToMeters(273.5), 0.462788, new Rotation3d(new Quaternion(0, 0, 0, 1)))),
            new AprilTag(2, new Pose3d(15.513558, Units.inchesToMeters(207.5), 0.462788, new Rotation3d(new Quaternion(0, 0, 0, 1)))),
            new AprilTag(3, new Pose3d(15.513558, Units.inchesToMeters(141.5), 0.462788, new Rotation3d(new Quaternion(0, 0, 0, 1)))),
            new AprilTag(4, new Pose3d(16.178784, Units.inchesToMeters(50), 0.695452, new Rotation3d(new Quaternion(0, 0, 0, 1)))),
            new AprilTag(5, new Pose3d(0.36195, Units.inchesToMeters(50), 0.695452, new Rotation3d(new Quaternion(1, 0, 0, 0)))),
            new AprilTag(6, new Pose3d(1.02743, Units.inchesToMeters(141.5), 0.462788, new Rotation3d(new Quaternion(1, 0, 0, 0)))),
            new AprilTag(7, new Pose3d(1.02743, Units.inchesToMeters(207.5), 0.462788, new Rotation3d(new Quaternion(1, 0, 0, 0)))),
            new AprilTag(8, new Pose3d(1.02743, Units.inchesToMeters(273.5), 0.462788, new Rotation3d(new Quaternion(1, 0, 0, 0))))
            ), 16.53, 8.01);
}
