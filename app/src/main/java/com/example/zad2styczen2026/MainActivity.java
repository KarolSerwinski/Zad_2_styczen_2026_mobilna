package com.example.zad2styczen2026;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = -1;
    private Button nextBtn;
    private RadioGroup answersRadioGroup;
    private TextView questionTV;
    private int points = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nextBtn = findViewById(R.id.nextBtn);
        answersRadioGroup = findViewById(R.id.answers);
        setQuestions();
        nextBtn.setOnClickListener( new ClickHandler() );
        questionTV = findViewById(R.id.questionTV);
        imageView = findViewById(R.id.image);
        setNextQuestion();
    }

    private void setQuestions() {
        questions.add(new Question (
                "Które to schronisko?",
                new String[]{"Na Rysiance.", "Na Wielkiej Raczy.", "Na Wielkiej Rycerzowej."},
                1, R.drawable.zad1 ));
        questions.add(new Question(
                "Zwierzę na zdjęciu to",
                new String[]{"Owczarek.", "Wilk", "Kozica"},
                0, R.drawable.zad2 ));
        questions.add(new Question(
                "W oddali są widoczne",
                new String[]{"Himalaje", "Alpy", "Tatry"},
                2, R.drawable.zad3));
    }

    public void setNextQuestion() {
        currentQuestionIndex = currentQuestionIndex + 1 >= questions.size() ?
                                               0 : ++currentQuestionIndex;
        ArrayList<RadioButton> answerButtons = getRadioButtons(answersRadioGroup);
        Question q = questions.get(currentQuestionIndex);
        String[] answerList = q.getAnswers();
        for (int i = 0; i < answerButtons.size(); i++)
            answerButtons.get(i).setText(answerList[i]);
        answersRadioGroup.clearCheck();
        questionTV.setText(q.getQuestion());
        imageView.setImageResource(q.getDrawable());
    }

    private ArrayList<RadioButton> getRadioButtons(RadioGroup rg) {
        ArrayList<RadioButton> buttonList = new ArrayList<>();
        for (int i = 0; i < rg.getChildCount(); i++) {
            if (rg.getChildAt(i) instanceof RadioButton)
                buttonList.add( (RadioButton) rg.getChildAt(i) );
        }
        return buttonList;
    }

    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Question question = questions.get(currentQuestionIndex);
            int checkedIndex = answersRadioGroup.getCheckedRadioButtonId();
            if (question.isCorrect(checkedIndex))
                points++;
            setNextQuestion();
        }
    }

    private class Question {
        private String[] answers;
        private String question = "";
        private int correctAnsIndex;
        private int imgDrawable;
        public Question(String question, String[] answers, int correctAnsIndex, int imgDrawable) {
            this.answers = answers;
            this.question = question;
            this.correctAnsIndex = correctAnsIndex;
            this.imgDrawable = imgDrawable;
        }

        public String getQuestion() {
            return this.question;
        }

        public boolean isCorrect(int checkedIndex) {
            return this.correctAnsIndex == checkedIndex - 1;
        }

        public String getAnswer(int index) {
            return this.answers[index];
        }

        public String[] getAnswers() {
            return this.answers;
        }

        public int getDrawable() {
            return this.imgDrawable;
        }
    }
}