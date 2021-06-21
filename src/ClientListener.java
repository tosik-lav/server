import com.google.exchange.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientListener implements Runnable {

    private Socket socket;
    private Db db;

    public ClientListener(Socket socket) {
        this.socket = socket;
        db = new Db();
    }

    @Override
    public void run() {
        try (
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                Scanner scanner = new Scanner(socket.getInputStream())
        ) {
            while (scanner.hasNextLine()) {

                String key = scanner.nextLine();
                System.out.println("scanner key");
                Gson gson = new Gson();
                String json ;
                String jsonDoc;
                String jsonService;

                switch (key) {
                    case Constanta.KEY_ADD_CLIENT: //вставить клиента
                        System.out.println("add client");
                        json = scanner.nextLine();

                        Client client = gson.fromJson(json, Client.class);

                        int id = db.saveClient(client);

                        writer.println(id);
                        writer.flush();

                        System.out.println("finish");
                        break;
                    case Constanta.KEY_FIND_CLIENT:
                        System.out.println("find client");

                        String phone = scanner.nextLine();
                        Client clientPhone = db.findClient(phone);

                        json = gson.toJson(clientPhone);
                        System.out.println(json);
                        writer.println(json);
                        writer.flush();
                        break;
                    case Constanta.KEY_GET_DOCTORS: //расписание докторов

                        ArrayList<Doctor> doctors = db.getDoctors();

                        jsonDoc = gson.toJson(doctors);

                        writer.println(jsonDoc);
                        writer.flush();

                        ArrayList<String> allNameService = db.getCategoryService();
                        jsonService = gson.toJson(allNameService);

                        writer.println(jsonService);
                        writer.flush();

                        break;
                    case Constanta.KEY_PRICE: // цены на услуги
                        ArrayList<Service> prices = db.getPrice();
                        System.out.println(prices.size());

                        jsonService = gson.toJson(prices);

                        writer.println(jsonService);
                        writer.flush();
                        System.out.println("KEY_PRICE finish");
                        break;
                    case Constanta.KEY_TIMETABLE: // сохранить запись на прием
                        json = scanner.nextLine();
                        System.out.println("json " + json);

                        Timetable timetable = gson.fromJson(json, Timetable.class);

                        db.saveTimetable(timetable);
                        System.out.println("saveTimetable finish");
                        break;
                    case Constanta.KEY_GET_DOC_SERVICE:
                        System.out.println("KEY_GET_DOC_SERVICE start");
                        ArrayList<String> fullName = db.getAllDocName();

                        jsonDoc = gson.toJson(fullName);

                        writer.println(jsonDoc);
                        writer.flush();

                        ArrayList<String> allCategory = db.getCategoryService();
                        jsonService = gson.toJson(allCategory);

                        writer.println(jsonService);
                        writer.flush();
                        System.out.println("KEY_GET_DOC_SERVICE finish");
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
