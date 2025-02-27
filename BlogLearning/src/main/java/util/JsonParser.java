/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import model.Option;
import model.Question;
import model.Quiz;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * JSON Parser class. Parse the JSON file (refer to resources/quiz.json for JSON
 * structure) and convert it into Quiz object
 *
 * @author Asus
 */
public class JsonParser {

    /**
     * Static method to parse the JSON file into Quiz object.
     *
     * @param file - The java.io.File object corresponding to the JSON file
     * @return - The Quiz object after parsing
     * @throws java.io.FileNotFoundException - throws FileNotFoundException we
     * can't find the JSON file
     * @throws org.json.simple.parser.ParseException - throws ParseException if
     * fail to parse JSON. Mostly because the JSON is in invalid format
     */
    public static Quiz parse(File file) throws FileNotFoundException, IOException, ParseException {
        //Parse JSON file
        Object obj = new JSONParser().parse(new FileReader(file));
        
        //Create JSONObject of quiz and get its attributes
        JSONObject quizObj = (JSONObject) obj;

        Quiz quiz = new Quiz();
        quiz.setId(((Long) quizObj.get("quiz_id")).intValue());
        quiz.setTitle((String) quizObj.get("title"));

        //Get the array of questions and process it
        JSONArray questionsArr = (JSONArray) quizObj.get("questions");
        for (Iterator quesIt = questionsArr.iterator(); quesIt.hasNext();) {
            //Get each question attributes
            Question question = new Question();
            JSONObject quesObj = (JSONObject) quesIt.next();
            question.setId(((Long) quesObj.get("id")).intValue());
            question.setTitle((String) quesObj.get("title"));
            question.setPoint(((Long) quesObj.get("points")).intValue());

            //Get each option of each question
            for (Iterator optionIt = ((JSONArray) quesObj.get("options")).iterator(); optionIt.hasNext();) {
                //Get option attributes
                Option option = new Option();
                JSONObject optnObj = (JSONObject) optionIt.next();
                option.setId(((String) optnObj.get("id")).charAt(0));
                option.setContent((String) optnObj.get("text"));
                option.setIsCorrect((boolean) optnObj.get("correct"));
                option.setFeedback((String) optnObj.get("feedback"));

                //Add option into question object
                question.addOption(option);
            }

            //Add question to quiz object
            quiz.addQuestion(question);
        }

        return quiz;
    }

    public static void main(String[] args) {
        try {
            System.out.println(JsonParser.parse(Util.getResource("quiz.json")));
        } catch (IOException | URISyntaxException | ParseException ex) {
            Util.logError(String.format("Error parsing JSON:\n%s\n---\n", ex.getMessage()));
        }
    }
}
