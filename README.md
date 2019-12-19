# Alfred

FRC 2019 Robot Code

## Subsystems

- Tail and Abom
  - These are used in the climb at the end of the match
  - The Abom is a suction cup that attaches to the platform
  - After the Abom is attached, the tail lifts the robot up for a climb.

- Floop
  - Grabs hatch panels

- Rollers
  - Used for acquiring and ejecting cargo

- Lift
  - Used to lift cargo to various levels of the rocket

- Drivetrain
  - Used in the motion of the robot

## Testing details

- The gyro is slanted at an angle, making it slightly harder to get measurements.  Several methods to fix this can be found in the branch ds/gyroMath

## Drivetrain Motor 
Variable | Port
-|-
LEFT_TOP_MOTOR_PORT | 2
LEFT_MIDDLE_MOTOR_PORT | 3
LEFT_BOTTOM_MOTOR_PORT | 1
RIGHT_TOP_MOTOR_PORT | 6
RIGHT_MIDDLE_MOTOR_PORT | 7
RIGHT_BOTTOM_MOTOR_PORT | 5
GEAR_SHIFT_CHANNEL | 0

## Drivetrain Encoder 
Variable | Port
-|-
DRIVETRAIN_LEFT_ENCODER_CHANNEL_A | 2
DRIVETRAIN_LEFT_ENCODER_CHANNEL_B | 3
DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A | 0
DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B | 1

## Tail and Abom 
Variable | Port
-|-
RATCHET_DOUBLE_SOLENOID_FORWARD_PORT | 2
RATCHET_DOUBLE_SOLENOID_REVERSE_PORT | 3
RATCHET_SINGLE_SOLENOID_PORT | 1
TAIL_MOTOR_PORT | 4
ABOM_SOLENOID_PORT | 6

## Lift 
Variable | Port
-|-
LIFT_MASTER_TALON_MOTOR_PORT | 8
LIFT_FOLLOWER_VICTOR_MOTOR_PORT | 9
LIFT_TILT_SOLENOID_FORWARD_PORT | 5
LIFT_TILT_SOLENOID_REVERSE_PORT | 7
LIFT_BRAKE_SOLENOID_PORT | 6
LIFT_BOTTOM_LIMIT_SENSOR_PORT | 5

## Floop Solenoid 
Variable | Port
-|-
FLOOP_CHANNEL | 4

## Pusher Solenoid 
Variable | Port
-|-
PUSHER_CHANNEL | 0

## Rollers Motor 
Variable | Port
-|-
ROLLER_MOTOR_PORT | 10

## IR Sensor 
Variable | Port
-|-
IR_SENSOR_PORT | 9

## IR Sensor 
Variable | Port
-|-
LED_CHANNEL | 0

## Gamepad 
Variable | Port
-|-
DRIVER_GAMEPAD_PORT | 0
OPERATOR_GAMEPAD_PORT | 1
