package model;

import java.io.*;

/**
 * this class do all the operation about save; like save, load, list, fire.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.29
 */

public class Save {

    /**
     * save a request with entered name in entered group.
     * @param name name of request to save
     * @param group group of request to save
     * @param requestInformation information of request
     */
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

    /**
     * show list of saved group and requests in them
     * @return group list
     */
    public String[][] GUIList() {

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

    /**
     * read requests from file
     * @param fileAddress address of file
     * @return loaded request
     */
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

    /**
     * create new group for saving request.
     * @param group name of group
     */
    public void createGroup(String group) {

        String path = "requests/" + group;
        boolean isSuccessful = new File(path).mkdirs();
        System.out.println("Creating directory is successful: " + isSuccessful);

    }


    /**
     * make request from saved information.
     * @param group name of group
     * @param name name of saved request
     * @return request information
     */
    public Request fire(String group, String name){

        Request requestInformation = new Request();
        String path = "requests/" + group;

        if (new File(path).exists()) {
            requestInformation = load(path + "/" + name);

        } else
            System.err.println("there is no group with this name to fire");

        return requestInformation;
    }

    /**
     * show list of saved group and requests in them
     * @param group name of group to show its requests
     */
    public void consoleList(String group) {

        if (group.charAt(0) != '-') {

            String path = "requests/" + group;

            if (new File(path).exists()) {

                File[] files = new File(path).listFiles();
                if (files != null) {
                    Request requestInformation = new Request();
                    for (int i = 0; i < files.length; i++) {
                        System.out.print(i + 1 + ". name: " + files[i].getName());
                        requestInformation = load(path + "/" + files[i].getName());
                        requestInformation.print();
                    }
                }

            } else
                System.err.println("There is no group with this name to load");

        } else {

            File[] files = new File("requests").listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++)
                    System.out.println(i + 1 + ". " + files[i].getName());
            } else
                System.out.println("there is not any group for requests");
        }
    }

}