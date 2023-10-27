package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tests.app.Test;
import tests.app.Tests;
import tests.app.Value;
import tests.app.Values;

import java.io.*;
import java.util.*;

public class Main2 {
    public static Map<Integer, String> VALUES_MAP = new HashMap<>();
    public static Gson gson;

    public static void main(String[] args) {
        // Жестко заданные пути к файлам
        String testJsonPath = "tests.json";
        String valueJsonPath = "values.json";
        String reportJsonPath = "reports/report.json";

        try (Reader testsReader = new FileReader(testJsonPath);
             Reader valuesReader = new FileReader(valueJsonPath);
             PrintWriter out = new PrintWriter(new FileWriter(reportJsonPath))){

            gson = new Gson();
            Values values = gson.fromJson(valuesReader, Values.class);
            for (Value vl : values.getValues()) {
                VALUES_MAP.put(vl.getId(), vl.getValue());
            }
            Tests tests = gson.fromJson(testsReader, Tests.class);
            for (Test ts : tests.getTests()) {
                setValues(ts);
                gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(ts);
                out.write(jsonString);
            }
            System.out.println("\n" + "Файл успешно сохранен в папке: " + reportJsonPath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setValues(Test test) {
        for (Integer id : Main.VALUES_MAP.keySet()) {
            if (test.getId() == id) {
                test.setValue(Main.VALUES_MAP.get(id));
            }
            if (test.getValues() != null) {
                for (Test child : test.getValues()) {
                    setValues(child);
                }
            }
        }
    }
}