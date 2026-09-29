package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");

        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);

        c.manualBrakeMode.set(true);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig
            (c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-0.4017290731114666);
        c.yPodOffset.set(5.025265310692975);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(-664.6927116760721);
                Controller secondaryTranslationalForward = Controller.proportional(-245.58619604794518);
                Controller primaryTranslationalLateral = Controller.proportional(0.3562584292786662);
                Controller secondaryTranslationalLateral = Controller.proportional(0.13162797021791553);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.9321450196322392));
                c.brake.set(Controller.proportionalFeedforward(0.7923232666874033));

                c.headingFeedback.set(Controller.proportional(3.1247281783333105));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.059745864970817644, 0.010462296515785154));

                c.linearBrakeCoefficients.set(Matrix.diag(0.10232958100855305, 0.035609357446100075));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0010655207655623075, 0.0033399068272668164));

                c.maxAchievableForwardVelocity.set(48.42869115228426);
                c.maxAchievableStrafeVelocity.set(40.45235544064777);
                c.naturalForwardDeceleration.set(37.16789078161108);
                c.naturalStrafeDeceleration.set(57.28486049423174);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

}