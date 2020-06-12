/*

package model;

import java.io.IOException;
import java.util.ArrayList;

public class Run {

    public Run(String[] string) {

        ArrayList<Request> requestinformation;
        Response responseInformation = new Response();
        MakeRequest myMakeRequest = new MakeRequest();

        // analyze input
        InputAnalyzer inputAnalyzer = new InputAnalyzer();
        requestinformation = inputAnalyzer.analyze(string);

        for (Request request : requestinformation) {

            // send req
            try {
                responseInformation = myMakeRequest.makeReq(request);
            } catch (IOException e) {
//            e.printStackTrace();
                System.err.println("Error");
            }

            responseInformation.print(request.isShowHeaderResponse());

//        if (saveResponseFlag)
//            System.out.println("\nresponse body saved in entered path...");

        }
    }
}


 */