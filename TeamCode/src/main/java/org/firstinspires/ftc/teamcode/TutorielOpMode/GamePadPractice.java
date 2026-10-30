package org.firstinspires.ftc.teamcode.TutorielOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled   // Ici pour activer programme
@TeleOp
public class GamePadPractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        // runs 50 times per second
        double speedForward = -gamepad1.left_stick_y /2.0;
        double diffXJoysticks = gamepad1.left_stick_x - gamepad1.right_stick_x;  //Défi 3
        double sumTriggers = gamepad1.left_trigger + gamepad1.right_trigger;   //Défi 4

        telemetry.addData("left X",gamepad1.left_stick_x);
        telemetry.addData("left Y", speedForward);
        telemetry.addData("right X", gamepad1.right_stick_x);  //Défi 1
        telemetry.addData("right Y", gamepad1.right_stick_y);  //Défi 1

        telemetry.addData("Difference left and right X",diffXJoysticks);  //Défi 3
        telemetry.addData("Sum Triggers", sumTriggers);   //Défi 4

        telemetry.addData("A button",gamepad1.a);
        telemetry.addData("B button", gamepad1.b);  // Défi 2

    }
    /*
    1. Add telemetry for the right joystick
    2. Add telemetry for the B button
    3. Add telemetry to report the difference between x left joystick and x right joystick
    4. Add telemetry to report the sum of the two triggers

     */

}
