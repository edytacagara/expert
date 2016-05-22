/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.util.Optional;
import java.util.logging.Logger;
import pl.expert.utils.InputDialog;
import pl.expert.utils.input.Input;

/**
 *
 * @author Mariusz Batyra
 */
public class UserAnswerReader {

    public static final String NUMBER_QUESTION = "Wprowadź wartość liczbową";
    public static final String TEXT_QUESTION = "Wprowadź wartość tekstową";
    public static final String BOOLEAN_QUESTION = "Zdecyduj prawda czy fałsz";
    private static final Logger LOG = Logger.getLogger(UserAnswerReader.class.getName());
//    public static BigDecimal readBigDecimal(String question) {
//        inputDialog.setInputType(InputType.DOUBLE);
//        inputDialog.setTitle("Podaj liczbe");
//        inputDialog.setQuestion(question);
//        Optional<Input> result = inputDialog.showAndWait();
//        return (BigDecimal) result.get().getValue();
//        
//    }
//
//    public static boolean readBoolean(String question) {
//        System.out.println("Question (logical value y/n): " + question + " ?");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            String str = bufferedReader.readLine();
//            return "y".equalsIgnoreCase(str);
//        } catch (Exception e) {
//            LOG.log(Level.SEVERE, "Error while input reading! {0}", e.getMessage());
//            return false;
//        }
//    }
    
    public static<T> T getAnswear(String question, String title, Class<?> type){
        InputDialog<T> inputDialog =  InputDialog.create(type);
        inputDialog.setTitle(title);
        inputDialog.setQuestion(question);
        Optional<Input<T>> result = inputDialog.showAndWait();
        return result.get().getValue();
    }
}
