import ThirdLab.MyMath;
import ThirdLab.Watcher;
import ThirdLab.WorkWithConsole;
import ThirdLab.WorkWithFile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Main {
    private static WorkWithConsole _console;
    private static WorkWithFile _filePrinter;
    public static final int LENGTH_PACKET = 30;
    public static final String HOST = "localhost";
    public static int PORT;
    public static byte[] answer;

    public static void main(String[] args) {

        DatagramSocket servSocket = null;
        DatagramPacket datagram;
        InetAddress clientAddr;

        PORT = Integer.parseInt(args[0]);

        _console = new WorkWithConsole();
        _console.OutputInConsole("Enter the path to the logFile:");
        var path = _console.Input();
        _filePrinter = new WorkWithFile(path);
        Watcher watcherWhoWriteInFile = new Watcher(_filePrinter);
        _console.addObserver(watcherWhoWriteInFile);

        int clientPort;
        byte[] data;
        try{
            servSocket = new DatagramSocket(PORT);
        }catch(SocketException e){
            System.err.println("Не удаётся открыть сокет : " + e.toString());
        }
        while(true){
            try{
                // ------------------------------------------------------------
                //очень... долгий цикл
                //приём данных от клиента
                // ------------------------------------------------------------

                data = new byte[LENGTH_PACKET];
                datagram = new DatagramPacket(data, data.length);
                servSocket.receive(datagram);
                _console.OutputInConsole("Get from client: " + (new String(datagram.getData())).trim() + " address - " + datagram.getPort());

                var result = MyMath.start((new String(datagram.getData())).trim());

                _console.OutputInConsole("Computing for " + datagram.getPort() + " result: " + result);

                answer = result.getBytes();

                // ------------------------------------------------------------
                //отправка данных клиенту
                //адрес и порт можно вычислить из предыдущей сессии приёма, через
                //объект класс DatagramPacket - datagram
                // ------------------------------------------------------------
                clientAddr = datagram.getAddress();
                clientPort = datagram.getPort();

                // ------------------------------------------------------------
                //приписывание к полученному сообщению текста " приписка от сервера" и отправка
                //результата обратно клиенту
                // ------------------------------------------------------------
                datagram.setData(answer);
                data = answer;
                datagram = new DatagramPacket(data, data.length, clientAddr, clientPort);
                servSocket.send(datagram);
            }catch(IOException e){
                System.err.println("io исключение : " + e.toString());
            }
        }
    }
}