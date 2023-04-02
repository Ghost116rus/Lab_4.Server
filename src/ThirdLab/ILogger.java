package ThirdLab;

import java.io.*;


public interface ILogger {
    String defaultLogPath = "log.txt";
    void LogEvent(String data);
}
