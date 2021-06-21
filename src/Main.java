

import com.google.exchange.Doctor;
import com.google.exchange.Service;
import com.google.exchange.Timetable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        //для проверки работы сервера - работает
//        Db db = new Db();
        //создание клиента
//        db.saveClient(new Client(
//                "Ivan",
//                "Ivanovich",
//                "Ivanov",
//                "33333"));

//        db.findClient("663335496");
//        System.out.println(db.findClient("663335496"));
//
        //список врачей
//        ArrayList<Doctor> doctors = db.getDoctors();
//        doctors.forEach(System.out::println);
//        String [] docName = new String[doctors.size()];
//        System.out.println(doctors.size());
//        int i =0;
//        for(Doctor doctor :doctors){
//            String name = doctor.getName();
//            String patronymic = doctor.getPatronymic();
//            String surname = doctor.getSurname();
//            String fullName = name + " " + patronymic + " " + surname;
//            System.out.println(fullName);
//
//                docName [i] = Arrays.toString(new String[]{fullName});
//            i++;
//            System.out.println(i);
//        }
//        System.out.println("name doctors");
//        System.out.println(Arrays.toString(docName));

        //цены на услуги
//        ArrayList<Service> services = db.getPrice();
//        System.out.println(services.size());
//        services.forEach(System.out::println);//выводит одну запись!! ???

        //запись к врачу  проверить
//        db.saveTimetable(new Timetable("10.06.2021", "11:30", 3, 1, 1));

        //вытаскивание докторов и категорий услуг
//        ArrayList<String> allDocName =db.getAllDocName();
//        System.out.println(allDocName);
//        allDocName.forEach(System.out::println);

//        ArrayList<String> allCategory = db.getCategoryService();
//        allCategory.forEach(System.out::println);

    }
}
