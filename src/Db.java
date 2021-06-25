import com.google.exchange.Client;
import com.google.exchange.Doctor;
import com.google.exchange.Service;
import com.google.exchange.Timetable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Db {
    static final String DB_URL = "jdbc:postgresql://localhost/clinik_db";
    static final String USER = "postgres";
    static final String PASS = "1111";
    //    String url = "jdbc:postgresql://localhost/clinik_db?user=postgres&password=1111";
    Connection connection;

    public Db() {
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //сохранение клиента
    public int saveClient(Client clientNew) {
        System.out.println("Db saveClient start");
        int id = 0;
        String name = clientNew.getName();
        String surname = clientNew.getSurname();
        String phone = clientNew.getPhone();
        String patronymic = clientNew.getPatronymic();

        String insert = "INSERT INTO client (name, patronymic, surname, phone) VALUES ('"
                + name + "', '" + patronymic + "', '" + surname + "', '" + phone + "')";

        //добавить проверку на существования клиента
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(insert);
            System.out.println("Клиент добавлен");
            Client client = findClient(phone);
            id = client.getId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    //поиск клиента по телефону
    public Client findClient(String phoneClient) {
        System.out.println("find client start");
        System.out.println(phoneClient);
        String select = "SELECT * FROM client WHERE phone like '%" + phoneClient + "'";
        Client client = null;
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(select)
        ) {
            if (resultSet.next()) {
                System.out.println("клиент найден");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String patronymic = resultSet.getString("patronymic");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getString("phone");
                client = new Client(id, name, patronymic, surname, phone);
            } else System.out.println("клиент не найден");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("findClient finish");
        return client;
    }

    //вытаскиваем список врачей
    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = null;
        String getDoctor = "SELECT * FROM doctor";

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(getDoctor)
        ) {
            doctors = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String patronymic = resultSet.getString("patronymic");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getString("phone");
                String position = resultSet.getString("position");
                String category = resultSet.getString("category");
                String workingHours = resultSet.getString("workingHours");
                doctors.add(new Doctor(name, patronymic, surname, phone, position, category, workingHours));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return doctors;
    }

    //прайс
    public ArrayList<Service> getPrice() {
        ArrayList<Service> services = null;
        String getPrice = "SELECT * FROM service";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(getPrice)
        ) {
            services = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int id_category = resultSet.getInt("id_category");
                services.add(new Service(id, category, name, price, id_category));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return services;
    }

    //запись на прием
    public void saveTimetable(Timetable timetable) {
        String data = timetable.getData();
        String time = timetable.getTime();
        int id_client = timetable.getId_client();
        int id_doctor = timetable.getId_doctor();
        int id_service = timetable.getId_service();

        String saveTimetable = "INSERT INTO timetable (data, time, id_client, id_doctor, id_service) VALUES ('"
                + data + "', '" + time + "', '" + id_client + "', '" + id_doctor + "', '" + id_service + "')";
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(saveTimetable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Timetable> getTimetable(int id) {
        ArrayList<Timetable> timetables = null;
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int year = calendar.get(Calendar.YEAR);
        String select = "SELECT * FROM timetable where id_client = " + id
                + "and EXTRACT(MONTH FROM data) = " + month
                + "and EXTRACT(YEAR FROM data) = " + year
                + "order by data, time DESC";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(select)
        ) {
            timetables = new ArrayList<>();
            while (resultSet.next()) {
                String data = resultSet.getString("data");
                String time = resultSet.getString("time");
                int id_client = resultSet.getInt("id_client");
                int id_doctor = resultSet.getInt("id_doctor");
                int id_service = resultSet.getInt("id_service");
                timetables.add(new Timetable(data, time, id_client, id_doctor, id_service));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("getTimetable " + timetables.size());
        return timetables;
    }

    //вытаскиваем имя докторов
    public ArrayList<String> getAllDocName() {
        ArrayList<String> doc = null;
        String qweryAllDoc = "SELECT name, patronymic, surname FROM doctor";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(qweryAllDoc)
        ) {
            doc = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String patronymic = resultSet.getString("patronymic");
                String surname = resultSet.getString("surname");
                String fullName = name + " " + patronymic + " " + surname;
                doc.add(fullName);
            }
            System.out.println(doc.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doc;
    }

    //вытаскиваем категории из услуг
    public ArrayList<String> getCategoryService() {
        ArrayList<String> allCategory = null;
        String qweryCategory = "SELECT DISTINCT id_category, category FROM service";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(qweryCategory)
        ) {
            allCategory = new ArrayList<>();
            while (resultSet.next()) {
                int id_category = resultSet.getInt("id_category");
                String category = resultSet.getString("category");
                allCategory.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return allCategory;
    }
}