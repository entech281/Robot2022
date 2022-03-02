package frc.robot.logger;

/**
 * A datalogger that throws all the data away. Dont' create them
 * directly, use MatchDataLoggerFactory.
 *
 * @author dcowden
 *
 */
public class NullLogger extends DataLogger {

    public NullLogger(String name) {
        super(name);
    }

    @Override
    public void driverinfo(String key, Object value) {
    }

    @Override
    public void driverinfo(String key, double value) {
    }

    @Override
    public void driverinfo(String key, int value) {
    }

    @Override
    public void driverinfo(String key, String value) {
    }

    @Override
    public void driverinfo(String key, long value) {
    }

    @Override
    public void driverinfo(String key, boolean value) {
    }

    @Override
    public void warn(String message) {
    }

    @Override
    public void log(String key, Object value) {
    }

    @Override
    public void log(String key, double value) {
    }

    @Override
    public void log(String key, int value) {
    }

    @Override
    public void log(String key, String value) {
    }

    @Override
    public void log(String key, long value) {
    }

    @Override
    public void log(String key, boolean value) {
    }

}
