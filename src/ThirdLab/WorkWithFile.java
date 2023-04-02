package ThirdLab;

import java.io.*;
import java.util.*;


public class WorkWithFile extends Observable implements ILogger {
    private final String _path;
    private File log;
    public String getPath() {return _path; }
    private final PrintWriter pw;

    public WorkWithFile()
    {
        this(defaultLogPath);
    }
    public WorkWithFile(String path)
    {
        _path = path;
        OpenLogFile();
        try{
            pw = new PrintWriter(log.getAbsoluteFile());
        }
        catch(IOException e){throw new RuntimeException();}

    }

    public void EndWork()
    {
        this.LogEvent("Завершение работы\n");
        pw.close();
    }

    private void OpenLogFile()
    {
        log = new File(_path);
        try{
            if(!log.exists()) log.createNewFile();
        }catch(IOException e){throw new RuntimeException();}
    }

    public static String GetData(String path)
    {
        var file = new File(path);
        //Чтение из файла -------------------------------------------------------
        StringBuilder sb = new StringBuilder();
        if(file.exists()){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                try{
                    String s = "";
                    while((s = br.readLine())!=null){//построчное чтение
                        if (s.contains("Array: "))
                        {
                            s = s.replace("Array: ","");
                            sb.append(s);
                            break;
                        }
                    }
                }finally{br.close();}
            }catch(IOException e){throw new RuntimeException();}
        }
        return (sb.toString());//в sb.toString() хранится текст файла
    }

    @Override
    public void LogEvent(String data) {
        pw.println(data);
    }
}
