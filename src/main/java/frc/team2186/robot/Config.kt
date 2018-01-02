package frc.team2186.robot

object Config {
    object Drive {
        const val LEFT_MASTER = 0
        const val LEFT_SLAVE = 1

        const val RIGHT_MASTER = 2
        const val RIGHT_SLAVE = 3
    }

    object Jetson {
        const val IP = "10.21.86.50"
        const val PORT = 5800
        var ENABLED = false
    }

    object LEDS {
        const val IP = "10.21.86.45"
        const val PORT = 42069
    }

    object Controls {
        const val LEFT_JOYSTICK = 0
        const val RIGHT_JOYSTICK = 1
    }

    object PID {
        object VelocityPID {
            const val P = 1.0
            const val I = 0.0
            const val D = 0.0
        }

        object LeftDrive {
            const val P = 1.0
            const val I = 0.0
            const val D = 0.0
            const val F = 0.1
        }

        object RightDrive {
            const val P = 1.0
            const val I = 0.0
            const val D = 0.0
            const val F = 0.1
        }
    }
}