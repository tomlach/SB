package pl.lachtom;


import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {

        MethodsClass methodsClass = new MethodsClass();
        FolderSearch folderSearch = new FolderSearch();

        folderSearch.searchFolder();

        methodsClass.readFile();
        methodsClass.whatTimeLogs();
        methodsClass.typeOfLogs();
        methodsClass.uniqueLogs();

    }
}












