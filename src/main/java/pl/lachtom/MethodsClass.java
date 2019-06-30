package pl.lachtom;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MethodsClass {

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

    public void readFile() {
        LocalDateTime before = LocalDateTime.now();
        while (fileContents.hasNext()) {
            String[] line = fileContents.nextLine().replace("  ", " ").split(" ");
            if (line[0].contains("-")) {
                readerList.add(new LogsCl(LocalDate.parse(line[0]), LocalTime.parse(line[1].replace(",", ".")), line[2], line[3]));
            }
        }
        for (LogsCl fileReaderr : readerList) {
//            System.out.println(fileReaderr);
        }
        LocalDateTime after = LocalDateTime.now();
        Duration diff = Duration.between(before, after);
        System.out.println("Time need to readed file: " + diff);
    }


    public void typeOfLogs() {
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
        System.out.println("ERROR LOGS: " + errorList.size());
        System.out.println("INFO LOGS " + infoList.size());
        System.out.println("DEBUG LOGS " + debugList.size());
        System.out.println("FATAL LOGS " + fatalList.size());
        System.out.println("ERROR LOGS OR HIGHER: " + (errorList.size() + fatalList.size()) + " / EveryLogs: " + readerList.size());
    }

    public void whatTimeLogs() {
        Collections.sort(readerList);
        LocalDate lastDate = readerList.get(readerList.size() - 1).getDate();
        LocalDate firstDate = readerList.get(0).getDate();
        LocalTime firstTime = readerList.get(0).getTime();
        LocalTime lastTime = readerList.get(readerList.size() - 1).getTime();
//        System.out.println(lastDate + " " + lastTime);
//        System.out.println(firstDate + " " + firstTime);
        Period p = Period.between(firstDate, lastDate);
        long p2 = ChronoUnit.DAYS.between(firstDate, lastDate);
        System.out.println("Time between first and last logs: " + p.getYears() + " year, " + p.getMonths() +
                " months, " + p.getDays() +
                " days (" + p2 + " days total" + ")" + " " + Duration.between(firstTime, lastTime));
    }

    public void uniqueLogs() {
        HashSet hashSet = new HashSet();

        for (LogsCl logsCl : readerList) {
            if (!logsCl.getText().isEmpty()) {
                hashSet.add(logsCl.getText());
            }
        }
        System.out.println(hashSet.size() + " Unique library");
    }


    public MethodsClass() throws IOException {
    }


}

