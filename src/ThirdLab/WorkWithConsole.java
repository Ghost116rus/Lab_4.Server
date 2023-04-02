package ThirdLab;

import java.util.*;

public class WorkWithConsole extends Observable implements ILogger {
    private final Scanner sc;
    private String dialogBeforeLogOpen;

    public WorkWithConsole()
    {
        sc = new Scanner(System.in);
    }

    public void OutputInConsole(Object obj)
    {
        setChanged();
        notifyObservers(obj);
        System.out.println(obj);
    }

    public String Input()
    {
        return sc.nextLine();
    }

    @Override
    public void LogEvent(String data) {
        OutputInConsole("Событие вызвано, но наблюдатель-то консоль - " + data);
    }

    private String InfiCycle()
    {
        String s1;
        while (true)
        {
            s1 = sc.nextLine();
            dialogBeforeLogOpen += s1;
            if (Objects.equals(s1, "0") || Objects.equals(s1, "1")){ return s1; }
            s1 = "Неправильный ввод - нужно ввести либо 0, либо 1\n";
            System.out.print(s1);
            dialogBeforeLogOpen += s1;
        }
    }

    public Map<String,String> UserInterface()
    {
        Map<String,String> results = new HashMap<String,String>();
        String temp;
        String array;

        temp = "Введите путь к файлу журнала: ";
        System.out.print(temp);
        dialogBeforeLogOpen = temp;
        temp = sc.nextLine();

        results.put("logFilePath", temp);

        dialogBeforeLogOpen += "Вы хотите ввести исходный массив в консоли или считать данные из файла? 0 - консоль, 1 - файл\n";
        System.out.print(dialogBeforeLogOpen);
        String aboutInput = InfiCycle();


        if (Objects.equals(aboutInput, "0"))
        {
            temp = "Вводите массив: ";
            System.out.print(temp);
            array = sc.nextLine();
        }
        else
        {
            temp = "Введите путь к файлу: ";
            System.out.print(temp);
            array = WorkWithFile.GetData(sc.nextLine());
        }
        dialogBeforeLogOpen += temp;

        results.put("array", array);



        results.put("dialogBeforeLogOpen", dialogBeforeLogOpen);

        return results;
    }

}
