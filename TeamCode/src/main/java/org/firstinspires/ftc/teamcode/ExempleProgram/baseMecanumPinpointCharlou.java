package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class baseMecanumPinpointCharlou {

    pinpointCharlou pinpoint = new pinpointCharlou();

    private DcMotor frontLeft, backLeft, frontRight, backRight;

    /* ------------------------------------- INIT ------------------------------------- */
    public void init(HardwareMap hardwareMap) {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pinpoint.init(hardwareMap);

    }

    /* ------------------------------------ DRIVE ------------------------------------- */
    public void drive(double forward, double strafe, double rotate) {

        // double headCorrect = straightLine.headingCorrection();

        double frontLeftPower = forward + strafe + rotate;  //Formules qui gèrent le power aux roues.
        double backLeftPower = forward - strafe + rotate;    // 1. Ajuster avance/recule en inversant les moteurs au besoin
        double frontRightPower = forward - strafe - rotate;  // 2. Tester le strafe et modifier le signe +/- devant strafe dans les formules au besoin
        double backRightPower = forward + strafe - rotate;   // 3. Tester le rotate et modifier le signe +/- devant rotate dans les formules au besoin

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower / maxPower));
        backRight.setPower(maxSpeed * (backRightPower / maxPower));

    }

    /* -------------------------------- Drive Field Centric --------------------------- */
    public void driveFieldRelative(double forward, double strafe, double rotate) {

        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);
        //double heading = odo.curHead();

        theta = AngleUnit.normalizeRadians(theta -
                pinpoint.curHeadRad());


        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate);

    }

    /* ------------------------ Current Heading in Deg and Rad ------------------------ */
    public double curHeadDeg() {
        return(pinpoint.curHeadDeg());
    }

    public double curHeadRad() {
        return(pinpoint.curHeadRad());
    }



}