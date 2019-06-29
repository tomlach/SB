package pl.lachtom;


import javafx.util.converter.LocalTimeStringConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        LocalDateTime before = LocalDateTime.now();

        File file = new File("C:/logs2/server3.log");
        List<LogsCl> readerList = new ArrayList<LogsCl>();
        List<LogsCl> errorList = new ArrayList<>();
        List<LogsCl> infoList = new ArrayList<>();
        List<LogsCl> debugList = new ArrayList<>();
        List<LogsCl> fatalList = new ArrayList<>();
        List<LogsCl> timeList = new ArrayList<>();

        LineIterator fileContents = FileUtils.lineIterator(file, "UTF-8");

        while (fileContents.hasNext()) {
            String[] line = fileContents.nextLine().replace("  ", " ").split(" ");
            if (line[0].contains("-")) {
                readerList.add(new LogsCl(LocalDate.parse(line[0]), LocalTime.parse(line[1].replace(",", ".")), line[2], line[3]));
            }
        }

        for (LogsCl fileReaderr : readerList) {
//            System.out.println(fileReaderr);
        }

        for (LogsCl errorl : readerList) {
            if (errorl.getTypeOfError().equals("ERROR")) {
                errorList.add(errorl);
            } else if (errorl.getTypeOfError().equals("INFO")) {
                infoList.add(errorl);
            } else if (errorl.getTypeOfError().equals("FATAL")) {
                fatalList.add(errorl);
            } else if (errorl.getTypeOfError().equals("DEBUG")) {
                debugList.add(errorl);
            }
        }


        LocalDateTime after = LocalDateTime.now();
        Duration diff = Duration.between(before, after);
        System.out.println(before);
        System.out.println(after);
        System.out.println("Czas potrzebny na wczytanie pliku: " + diff);
        System.out.println("Ilosc błędów ERROR " + errorList.size());
        System.out.println("Ilosc błędów INFO " + infoList.size());
        System.out.println("Ilosc błędów DEBUG " + debugList.size());
        System.out.println("Ilosc błędów FATAL " + fatalList.size());
        System.out.println("Ilosc bledow error i wyzszych: " + (errorList.size() + fatalList.size()));


        Collections.sort(readerList);


////        System.out.println(readerList.get(0).getDate() + " " + readerList.get(0).getTime() + " Pierwszy czas");
////        System.out.println(readerList.get(readerList.size()-1).getDate() + " " + readerList.get(readerList.size()-1).getTime() + " ostatni czas");
//
//        String firsTime = readerList.get(0).getDate() + " " + readerList.get(0).getTime() + " first time";
//        String lastTime = readerList.get(readerList.size()-1).getDate() + " " + readerList.get(readerList.size()-1).getTime() + " last time";
//
//
//        System.out.println(firsTime);
//        System.out.println(lastTime);

        LocalDate lastDate = readerList.get(readerList.size() - 1).getDate();
        LocalDate firstDate = readerList.get(0).getDate();
        LocalTime firstTime = readerList.get(0).getTime();
        LocalTime lastTime = readerList.get(readerList.size()-1).getTime();

        System.out.println(lastDate + " " +lastTime);
        System.out.println(firstDate + " " +firstTime);


        Period p = Period.between(firstDate, lastDate);
        long p2 = ChronoUnit.DAYS.between(firstDate, lastDate);
        System.out.println("Czas " + p.getYears() + " rok, " + p.getMonths() +
                " miesięcy, " + p.getDays() +
                " dni (" + p2 + " dni łącznie" + ")" + " " + Duration.between(firstTime, lastTime));

    }

}



