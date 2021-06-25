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
                Gson gson = new Gson();
                String json;
                String jsonDoc;
                String jsonService;

                switch (key) {
                    case Constanta.KEY_ADD_CLIENT: //вставить клиента
                        json = scanner.nextLine();

                        Client client = gson.fromJson(json, Client.class);

                        int id = db.saveClient(client);

                        writer.println(id);
                        writer.flush();
                        break;
                    case Constanta.KEY_FIND_CLIENT:
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

                        jsonService = gson.toJson(prices);

                        writer.println(jsonService);
                        writer.flush();
                        break;
                    case Constanta.KEY_ADD_TIMETABLE: // сохранить запись на прием
                        json = scanner.nextLine();
                         System.out.println("json " + json);

                            Timetable timetable = gson.fromJson(json, Timetable.class);

                            db.saveTimetable(timetable);

                        break;
                    case Constanta.KEY_GET_DOC_SERVICE:
                        ArrayList<String> fullName = db.getAllDocName();

                        jsonDoc = gson.toJson(fullName);

                        writer.println(jsonDoc);
                        writer.flush();

                        ArrayList<String> allCategory = db.getCategoryService();
                        jsonService = gson.toJson(allCategory);

                        writer.println(jsonService);
                        writer.flush();
                        break;
                    case Constanta.KEY_GET_TIMETABLE:
                        System.out.println("KEY_GET_TIMETABLE");
                        int idCl = scanner.nextInt();
                        System.out.println("idCl " + idCl);

                        ArrayList<Timetable> timetables = db.getTimetable(idCl);

                        String jsonTime = gson.toJson(timetables);
                        System.out.println("jsonTime");
                        writer.println(jsonTime);
                        writer.flush();
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
