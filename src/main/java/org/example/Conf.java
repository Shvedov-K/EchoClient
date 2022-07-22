package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Conf {

    private static Conf conf;
    private HashMap<String, String> confs;

    private Conf() {
        confs = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/xtend/IdeaProjects/EchoClient/src/main/resources/app.config"))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                String key = line.substring(0, line.indexOf('='));
                String value = line.substring(line.indexOf('=') + 1, line.length());
                confs.put(key, value);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Conf GetInstance(){
        if (conf == null) {
            conf = new Conf();
        }
        return conf;
    }

    public Map<String, String> GetConfs() {
        return confs;
    }
}
