package org.firstinspires.ftc.teamcode.TutorielOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled // Mettre en commentaire pour activer programme
@TeleOp
public class VariablePractice extends OpMode {
    @Override
    public void init() {
        int teamNumber = 18013;
        double motorSpeed = 0.75;
        boolean clawClosed = true;
        String teamName = "Cuivre & Or";    //Pour le défi 1
        int motorAngle = 90;                //Pour le défi 2

        telemetry.addData("Team Number",teamNumber);
        telemetry.addData("Motor Speed",motorSpeed);
        telemetry.addData("claw closed",clawClosed);
        telemetry.addData("Team name",teamName);   //Pour le défi 1
        telemetry.addData("Motor angle", motorAngle);  //Pour le défi 2
    }

    @Override
    public void loop() {
    }
}
    /*
    1. Change the string variable "name" to your team name
    2. Create an int called "motorAngle" and store an angle between 0 and 180 degrees. Display in your init method
     */
