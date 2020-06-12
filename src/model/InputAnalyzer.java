/*

package model;

import java.util.ArrayList;

public class InputAnalyzer {

    public InputAnalyzer(){

    }

    public ArrayList<Request> analyze(String[] string) {

        Request requestInformation = new Request();
        ArrayList<Request> requestInfo = new ArrayList<Request>();
        Save save = new Save();

        for (int j = 0; j < string.length; j++) {

            if (string[j].equals("-h") || string[j].equals("--help")) {
                printHelp();
                System.exit(0);
            }

            if (string[j].equals("-i"))
                requestInformation.setShowHeaderResponse(true);

            if (string[j].equals("-u") || string[j].equals("--url"))
                requestInformation.setUrl(string[j + 1]);

            if (string[j].equals("-M") || string[j].equals("--method"))
                requestInformation.setMethod(string[j + 1]);

            if (string[j].equals("-H") || string[j].equals("--headers"))
                requestInformation.setHeaders(string[j + 1]);

            if (string[j].equals("-d") || string[j].equals("--data")) {
                requestInformation.setBodyMethod("--data");
                requestInformation.setBody(string[j + 1]);
            }

            if (string[j].equals("--upload")) {
                requestInformation.setBodyMethod("--upload");
                requestInformation.setFileLoadAddress(string[j + 1]);
            }

            if (string[j].equals("-O") || string[j].equals("--output")) {
                if (string.length == (j + 1))
                    requestInformation.setResponseFileAddress("-");
                else
                    requestInformation.setResponseFileAddress(string[j + 1]);
            }

            if (string[j].equals("--create")) {
                save.createGroup(string[j + 1]);
                System.exit(0);
            }

            if (string[j].equals("--list")) {
                if (string.length == (j + 1))
                    save.list("-");
                else
                    save.list(string[j + 1]);
                System.exit(0);
            }

            if (string[j].equals("--fire")) {

                int i = 0;
                while (string.length != j + 2 + i) {
                    if (string[j + 2 + i].charAt(0) != '-') {
                        requestInformation = save.fire(string[j + 1], string[j + 2 + i]);
                        requestInfo.add(requestInformation);
                    }
                    i++;
                }
                return requestInfo;
            }
        }

        for (int j = 0; j < string.length; j++)
            if (string[j].equals("-S") || string[j].equals("--save"))
                save.save(string[j + 1], string[j + 2], requestInformation);

        requestInfo.add(requestInformation);
        return requestInfo;
    }


    public void printHelp() {

        System.out.println("Help ");
        System.out.println("-u <>, --url <> \t : set url \t\t\t\t\t\t\t\t ,[--url http://www.google.com]");
        System.out.println("-i   , \t\t\t\t : show response headers");
        System.out.println("-M <>, --method <> \t : set method \t\t\t\t\t\t\t ,[--method GET]");
        System.out.println("-H <>, --header <> \t : set headers \t\t\t\t\t\t\t ,[--header \"key1:value1&key2:value2\"]");
        System.out.println("-d <>, --data <> \t : set message body in form-data \t\t ,[--data \"key1:value1&key2:value2\"]");
        System.out.println("     , --upload <> \t : set message body with uploaded file \t ,[--upload E:\\MidTermTest\\note.txt]");
        System.out.println("-O <>, --output <> \t : save response body in entered file \t ,[--output E:\\MidTermTest\\note.png]" +
                "\n \t\t\t\t\t   if you not enter file name, it will save in file with default name");
        System.out.println("     , --create <> \t : create a new request group \t\t\t ,[--create group1]");
        System.out.println("-S <>, --save <> \t : save a request in the group \t\t\t ,[--save req1 group1]");
        System.out.println("     , --list <> \t : show saved requests in the group \t ,[--list group1]" +
                "\n \t\t\t\t\t   if you not enter group request name, it will show all the groups");
        System.out.println("     , --fire <> \t : send a saved request from a group\t ,[--fire group1 req1]");
    }
}

 */