package ThirdLab;

import java.io.*;
import java.util.*;


public class WorkWithFile extends Observable implements ILogger {
    private final String _path;
    private File log;
    public String getPath() {return _path; }

    public WorkWithFile()
    {
        this(defaultLogPath);
    }
    public WorkWithFile(String path)
    {
        _path = path;
        CheckLogFile();
    }
    private void CheckLogFile()
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
                        if (s.contains("LogFile: "))
                        {
                            s = s.replace("LogFile: ","");
                            sb.append(s);
                            break;
                        }
                    }
                }finally{br.close();}
            }catch(IOException e){throw new RuntimeException();}
        }
        var str = sb.toString();
        if (str.equals(""))  { str = "log.txt"; }

        return (str);//в sb.toString() хранится текст файла
    }

    @Override
    public void LogEvent(String data) {
        try(FileWriter writer = new FileWriter(_path, true))
        {
            // запись всей строки
            String text = ( new Date()) + " " + data + "\n";
            writer.write(text);

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
