package org.firstinspires.ftc.teamcode.ExempleProgram.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class pinpointCharlou {

    GoBildaPinpointDriver pinpoint;

    Pose2D pos = new Pose2D(
            DistanceUnit.CM,
            0,
            0,
            AngleUnit.DEGREES ,
            0);

    public void init(HardwareMap hardwareMap) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        pinpoint.setOffsets(0.839601351520208, -6.711113396592028, DistanceUnit.INCH); // Millimètres, pas centimètres
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
        pinpoint.setPosition(pos);
        pinpoint.update();

    }

    /*
    public double curPosX() {
        return(pos.getX(DistanceUnit.CM));
    } // Mais "pos" reste à 0,0,0
    public double curPosY() {
        return(pos.getY(DistanceUnit.CM));
    }

     */

    // At one point, we might need the curPosition

    public double curVelX() {
        return (pinpoint.getVelX(DistanceUnit.CM));
    }

    public double curVelY() {
        return (pinpoint.getVelY(DistanceUnit.CM));
    }

    public double curHeadDeg() {
        pinpoint.update();
        return(pinpoint.getHeading(AngleUnit.DEGREES));
    }

    public double curHeadRad() {
        pinpoint.update();
        return(pinpoint.getHeading(AngleUnit.RADIANS));
    }

    public void setOffsets(double v, double v1, DistanceUnit distanceUnit) {
    }

    public void updatePinpoint() {    //Pour mettre le pinpoint à jour dans l'OpMode
        pinpoint.update();
    }


}