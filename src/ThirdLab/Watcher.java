package ThirdLab;
import java.util.*;

public class Watcher implements Observer {

    private final ILogger logger;

    public Watcher(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.LogEvent(arg.toString());
    }
}
