package model;

import java.io.*;
import java.nio.FloatBuffer;

public class Save {

    public Save(){

    }

    public void save(String name, String group, Request requestInformation) {

        String path = "requests/" + group;

        if (new File(path).exists()) {
            try {
                File file = new File(path + "/" + name);
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(requestInformation);
                System.out.println("Request saved.");

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Impossible to save request");
            }
        } else
            System.err.println("there is no group with this name to save");
    }

    public String[][] list() {

        File[] files = new File("requests").listFiles();

        int n = files.length;
        String[][] groups = new String[n][10];

        if (files != null)
            for (int i = 0; i < n; i++) {
                groups[i][0] = files[i].getName();
                String path = "requests/" + groups[i][0];

                File[] newfiles = new File(path).listFiles();
                for (int j = 0; j < newfiles.length; j++) {
                    groups[i][j + 1] = newfiles[j].getName();
                }
            }

        return groups;
    }

    public Request load(String fileAddress) {

        Request requestInformation = new Request();

        try {
            File file = new File(fileAddress);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            requestInformation = (Request) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Impossible to load request");
        }

        return requestInformation;
    }

    public void createGroup(String str) {

        String path = "requests/" + str;
        boolean isSuccessful = new File(path).mkdirs();
        System.out.println("Creating directory is successful: " + isSuccessful);

    }

    public Request fire(String group, String name){

        Request requestInformation = new Request();
        String path = "requests/" + group;

        if (new File(path).exists()) {
            requestInformation = load(path + "/" + name);

        } else
            System.err.println("there is no group with this name to fire");

        return requestInformation;
    }

}