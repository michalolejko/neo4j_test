package pack;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    static Scanner scanner;
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {

        configuration = new Configuration.Builder().uri("bolt://localhost:7687").credentials("neo4j", "123qwe").build();
        sessionFactory = new SessionFactory(configuration, "pack");
        session = sessionFactory.openSession();
        session.purgeDatabase();

        scanner = new Scanner(System.in);

        while (true) {
            showAllRecords();
            pressEnter();
            showMenu();
            switch (scanner.nextInt()) {
                case 0:
                    sessionFactory.close();
                    System.out.println("Zakonczono");
                    return;
                case 1:
                    saveRecord();
                    break;
                case 2:
                    updateRecord();
                    break;
                case 3:
                    deleteRecord();
                    break;
                case 4:
                    getRecordById();
                    break;
                case 5:
                    gerRecordByStatement();
                    break;
                case 6:
                    processing();
                    break;
            }
        }
    }

    private static void processing() {
        System.out.println("Wartosc wszystkich mandatow rosnie o 100:");
        TicketService ticketService = new TicketService(session);
        for(Ticket tick : ticketService.readAll()){
            Integer oldValue = (Integer.parseInt(tick.getValue())+100);
            tick.setValue(oldValue.toString());
            ticketService.createOrUpdate(tick);
        }
    }

    private static void gerRecordByStatement() {
        System.out.println("Mandaty powyzej 300zl:");
        TicketService ticketService = new TicketService(session);
        for(Ticket tick : ticketService.readAll())
            if(Integer.parseInt(tick.getValue()) > 300)
                System.out.println(tick);
    }

    private static void getRecordById() {
        showAllRecords();
    }

    private static void deleteRecord() {
        TicketService ticketService = new TicketService(session);
        showAllRecords();
        System.out.println("Podaj id do aktualizacji: ");
        ticketService.delete(scanner.nextLong());
    }

    private static void updateRecord() {
        showAllRecords();
        System.out.println("Podaj id do aktualizacji: ");
        Long id = scanner.nextLong();
        System.out.println("Podaj zaktualizowana wysokosc mandatu: ");
        scanner.nextLine();
        String ticketValue = scanner.nextLine();
        TicketService ticketService = new TicketService(session);
        Ticket updTick = ticketService.read(id);
        updTick.setValue(ticketValue);
        ticketService.createOrUpdate(updTick);
    }

    private static void saveRecord() {
        scanner.nextLine();
        System.out.println("Podaj rejestracje: ");
        String reg = scanner.nextLine().toUpperCase();
        System.out.println("Podaj wysokosc mandatu: ");
        String ticketValue = scanner.nextLine();
        String strDate = new SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().getTime());
        Ticket newTick = new Ticket(reg,ticketValue,strDate);
        System.out.println("New ticket: " + newTick);
        TicketService ticketService = new TicketService(session);
        ticketService.createOrUpdate(newTick);
    }

    private static void showAllRecords() {
        System.out.println("Wszystkie mandaty:");
        TicketService ticketService = new TicketService(session);
        for(Ticket tick : ticketService.readAll())
            System.out.println(tick);
    }

    private static void pressEnter() {
        System.out.println("Wcisnij enter, aby kontynuowac...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.print("\n2) Biuro turystyczne (Neo4j - 2)policja)\n\nWybierz operacje:\n" +
                "1.Zapisywanie\n2.Aktualizowanie\n3.Kasowanie\n4.Pobieranie wszystich mandatow\n5.Pobieranie mandatow powyzej 300zl\n" +
                "6.Przetwarzanie(wszytskie mandaty += 100zł)\n0.Zakoncz\n\nWpisz cyfre i zatwierdz enterem: ");
    }
}
