
package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Test Heading Pinpoint")
public class TestHeadingPinpoint extends OpMode {

    private GoBildaPinpointDriver pinpoint;

    @Override
    public void init() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(7.6, 12.8, DistanceUnit.CM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.REVERSED
        );

        pinpoint.resetPosAndIMU();
    }

    @Override
    public void loop() {
        pinpoint.update();

        telemetry.addData("Heading (degres)",pinpoint.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Heading (radians)",pinpoint.getHeading(AngleUnit.RADIANS));
        telemetry.addData("PosX",pinpoint.getPosX(DistanceUnit.CM));
        telemetry.addData("PosX",pinpoint.getPosY(DistanceUnit.CM));

        telemetry.update();
    }
}

