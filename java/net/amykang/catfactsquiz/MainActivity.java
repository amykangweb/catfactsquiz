package net.amykang.catfactsquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the submit button is clicked.
     */
    public void submitAnswers(View view) {
        // Question 1
        RadioGroup questionOne = findViewById(R.id.fact_1_answer);
        // Question 2
        RadioGroup questionTwo = findViewById(R.id.fact_2_answer);
        // Question 3
        CheckBox questionThreeCheckBoxOne = findViewById(R.id.fact_3_checkbox_one);
        CheckBox questionThreeCheckBoxTwo = findViewById(R.id.fact_3_checkbox_two);
        CheckBox questionThreeCheckBoxThree = findViewById(R.id.fact_3_checkbox_three);
        // Question 4
        EditText questionFour = findViewById(R.id.fact_4_input);

        boolean[] checkBoxValues = checkBoxGroupAnswers(
                questionThreeCheckBoxOne,
                questionThreeCheckBoxTwo,
                questionThreeCheckBoxThree
        );

        boolean[] checkBoxAnswers = { false, true, true };

        // Check answers.
        int resultOne = radioGroupAnswer(questionOne, "True");
        int resultTwo = radioGroupAnswer(questionTwo, "30%");
        int resultThree = multipleChoiceAnswers(checkBoxValues, checkBoxAnswers);
        int resultFour = textInputAnswer(questionFour, "feline");

        int totalScore = calculateScore(
                resultOne,
                resultTwo,
                resultThree,
                resultFour
        );

        toastScore(totalScore, 4);
    }

    /**
     * Returns boolean array for checkbox answers.
     *
     * @param checkBoxOne of the question
     * @param checkBoxTwo of the question
     * @param checkBoxThree of the question
     * @return boolean array of checkbox states
     */
    public boolean[] checkBoxGroupAnswers(CheckBox checkBoxOne, CheckBox checkBoxTwo, CheckBox checkBoxThree) {

        boolean checkBoxOneIsChecked = checkBoxOne.isChecked();
        boolean checkBoxTwoIsChecked = checkBoxTwo.isChecked();
        boolean checkBoxThreeIsChecked = checkBoxThree.isChecked();

        boolean[] checkBoxValues = {
                checkBoxOneIsChecked,
                checkBoxTwoIsChecked,
                checkBoxThreeIsChecked
        };

        return checkBoxValues;
    }

    /**
     * This method checks radio button group answers.
     *
     * @param group of radio buttons
     * @param answer for question
     * @return int 1 for correct answer, 0 for incorrect answer
     */
    public int radioGroupAnswer(RadioGroup group, String answer) {
        /**
         * Reference: chinna_82 on stackoverflow
         * https://stackoverflow.com/questions/18179124/android-getting-value-from-selected-radiobutton
         */
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        if (radioButton == null) {
            return 100;
        }

        String selectedAnswer = radioButton.getText().toString();

        if (selectedAnswer.equals(answer)) {
            return 1;
        }

        return 0;
    }

    /**
     * This method checks multiple checkbox answers.
     *
     * @param checkBoxValues for the question
     * @param checkBoxAnswers for the question
     * @return int 1 for correct answer, 0 for incorrect answer
     */
    public int multipleChoiceAnswers(boolean[] checkBoxValues, boolean[] checkBoxAnswers) {
        /**
         * Reference: Peter Lawrey on stackoverflow
         * https://stackoverflow.com/questions/8777257/equals-vs-arrays-equals-in-java
         */
        if (Arrays.equals(checkBoxValues, checkBoxAnswers)) {
            return 1;
        }
        return 0;
    }

    /**
     * This method checks text input answers.
     *
     * @param text in input
     * @param answer for question
     * @return int 1 for correct answer, 0 for incorrect answer
     */
    public int textInputAnswer(EditText text, String answer) {
        /**
         * Reference: Gursel Koca on stackoverflow
         * https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
         */
        if (text.getText().toString().toLowerCase().replaceAll("\\s","").equals(answer)) {
            return 1;
        }
        return 0;
    }

    /**
     * Sum up correct answers and return.
     *
     * @param resultOne for question one
     * @param resultTwo for question two
     * @param resultThree for question three
     * @param resultFour for question four
     * @return total score for quiz
     */
    public int calculateScore(int resultOne, int resultTwo, int resultThree, int resultFour) {

        return resultOne + resultTwo + resultThree + resultFour;
    }

    /**
     * Show user their final score with Toast.
     *
     * @param score for user
     * @param total number of questions
     */
    public void toastScore(int score, int total) {

        if (score > total) {
            Toast.makeText(
                    this,
                    "You must answer all the questions.",
                    Toast.LENGTH_LONG).show();

            return;
        }

        String toastMessage = "You answered " + score + " out of ";
        toastMessage += total + " correctly!";

        Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_LONG).show();
    }
}
