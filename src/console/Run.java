package console;

import model.Request;
import model.Response;

import java.util.ArrayList;

/**
 * class Run control all the application.
 * here we make object of our request, response and other class.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.29
 */

public class Run {

    /**
     * constructor of Run class
     *
     * @param string entered argument.
     */
    public Run(String[] string) {

        ArrayList<Request> requestInformation;
        Response responseInformation = new Response();
        makeRequest myMakeRequest = new makeRequest();

        // analyze input
        InputAnalyzer inputAnalyzer = new InputAnalyzer();
        requestInformation = inputAnalyzer.analyze(string);

        // make all saved request
        for (Request request : requestInformation) {

            // send req
            try {
                responseInformation = myMakeRequest.makeReq(request);
            } catch (Exception e) {
                System.err.println("Error in sending request");
                System.exit(-1);
            }

            responseInformation.print(request.isShowHeaderResponse());
        }
    }
}