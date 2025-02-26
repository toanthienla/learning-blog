/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Option;
import model.Question;
import model.Quiz;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Asus
 */
public class JsonParser {

    public static Quiz parse(File file) throws Exception {
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jo = (JSONObject) obj;

        Quiz quiz = new Quiz();
        quiz.setId(((Long) jo.get("quiz_id")).intValue());
        quiz.setTitle((String) jo.get("title"));

        JSONArray questionsArr = (JSONArray) jo.get("questions");
        for (Iterator quesIt = questionsArr.iterator(); quesIt.hasNext();) {
            Question question = new Question();
            JSONObject quesObj = (JSONObject) quesIt.next();
            question.setId(((Long) quesObj.get("id")).intValue());
            question.setTitle((String) quesObj.get("title"));
            question.setPoint(((Long) quesObj.get("points")).intValue());

            for (Iterator optionIt = ((JSONArray) quesObj.get("options")).iterator(); optionIt.hasNext();) {
                Option option = new Option();
                JSONObject optnObj = (JSONObject) optionIt.next();
                option.setId(((String) optnObj.get("id")).charAt(0));
                option.setContent((String) optnObj.get("text"));
                option.setIsCorrect((boolean) optnObj.get("correct"));
                option.setFeedback((String) optnObj.get("feedback"));

                question.addOption(option);
            }

            quiz.addQuestion(question);
        }

        return quiz;
    }

    public static void main(String[] args) {
        try {
            System.out.println(JsonParser.parse(Util.getResource("quiz.json")));
        } catch (Exception ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
