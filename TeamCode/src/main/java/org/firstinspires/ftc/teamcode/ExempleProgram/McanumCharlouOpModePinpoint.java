package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
public class McanumCharlouOpModePinpoint extends OpMode {

    baseMecanumPinpointCharlou drive = new baseMecanumPinpointCharlou();

    double forward, strafe, rotate;
    boolean fieldOriented = true;

    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.pinpoint.updatePinpoint();

        forward = (gamepad1.right_trigger - gamepad1.left_trigger); // enlever le commentaire et mettre devant l'autre forward pour contrôler avec les triggers ou le joystick
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.startWasPressed()){
        fieldOriented = !fieldOriented;
        }

        if (fieldOriented) {
            drive.driveFieldRelative(forward, strafe, rotate);  // Méthode à utiliser si Field Oriented
        } else {
            drive.drive(forward, strafe, rotate);       //Méthode à utiliser si robot Oriented
        }

        telemetry.addData("Heading (deg)", drive.curHeadDeg());
        telemetry.addData("Heading (rad)", drive.curHeadRad());
        telemetry.addData("FieldOriented", fieldOriented);


    }
}
