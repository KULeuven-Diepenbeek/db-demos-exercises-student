package org.example;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        lightCouch();
    }

    public static void test1() throws IOException {
        var db = new HashMap<String, Object>();
        db.put("joske", new Student("Joske", 11));

        var file = new File("database.db");
        var f = new FileOutputStream(file);
        var s = new ObjectOutputStream(f);
        s.writeObject(db);
        s.close();
    }

    public static void test2() throws IOException, ClassNotFoundException {
        var s = new ObjectInputStream(new FileInputStream("database.db"));
        Map<String, Object> map = (Map<String, Object>) s.readObject();
        s.close();
        Student joske = (Student) map.get("joske");
        System.out.println(joske.getName());
    }

    public static void lightCouch(){
        CouchDbClient dbClient = new CouchDbClient();
        Student joske = new Student("Joske",6);
        Response response = dbClient.save(joske);

        Student test = dbClient.find(Student.class, "56f7da58dfa74d79ab26e375f5bc153a");
        System.out.println(test.getName());
    }
}

