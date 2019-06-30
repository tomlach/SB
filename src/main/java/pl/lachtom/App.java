package pl.lachtom;


import javafx.util.converter.LocalTimeStringConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import sun.rmi.runtime.Log;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        LocalDateTime before = LocalDateTime.now();

        FolderSearch folderSearch = new FolderSearch();

        Arrays.sort(folderSearch.files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(
                        f1.lastModified());
            }
        });

        for (int index = 0; index < folderSearch.files.length; index++) {
            System.out.println((index + 1) + " lastModified file: ");
            System.out.println(folderSearch.files[index].getName());
        }
        File file = new File("C:/logs2/server.log");
        List<LogsCl> readerList = new ArrayList<>();
        List<LogsCl> errorList = new ArrayList<>();
        List<LogsCl> infoList = new ArrayList<>();
        List<LogsCl> debugList = new ArrayList<>();
        List<LogsCl> fatalList = new ArrayList<>();
        List<LogsCl> timeList = new ArrayList<>();
        List<LogsCl> textList = new ArrayList<>();
        List<LogsCl> otherList = new ArrayList<>();
        List<LogsCl> onlyOneList = new ArrayList<>();

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
        System.out.println("Czas start: " + before);
        System.out.println("Czas stop: " + after);
        System.out.println("Czas potrzebny na wczytanie pliku: " + diff);
        System.out.println("Ilosc logów ERROR " + errorList.size());
        System.out.println("Ilosc logów INFO " + infoList.size());
        System.out.println("Ilosc logów DEBUG " + debugList.size());
        System.out.println("Ilosc logów FATAL " + fatalList.size());
        System.out.println("Ilosc logów error i wyzszych: " + (errorList.size() + fatalList.size()) + " /wszystkich logów: " + readerList.size());

        Collections.sort(readerList);

        LocalDate lastDate = readerList.get(readerList.size() - 1).getDate();
        LocalDate firstDate = readerList.get(0).getDate();
        LocalTime firstTime = readerList.get(0).getTime();
        LocalTime lastTime = readerList.get(readerList.size() - 1).getTime();

        System.out.println(lastDate + " " + lastTime);
        System.out.println(firstDate + " " + firstTime);


        Period p = Period.between(firstDate, lastDate);
        long p2 = ChronoUnit.DAYS.between(firstDate, lastDate);
        System.out.println("Czas pomiędzy ostatnim i pierwszym logiem: " + p.getYears() + " rok, " + p.getMonths() +
                " miesięcy, " + p.getDays() +
                " dni (" + p2 + " dni łącznie" + ")" + " " + Duration.between(firstTime, lastTime));

        HashSet hashSet = new HashSet();

        for(LogsCl logsCl : readerList){
            if(!logsCl.getText().isEmpty()){
                hashSet.add(logsCl.getText());
            }

        }
        System.out.println(hashSet.size() + " ilosc unikalmych bibliotek, bez powtorzen");


    }
}












