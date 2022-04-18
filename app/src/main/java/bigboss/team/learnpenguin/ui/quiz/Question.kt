package bigboss.team.learnpenguin.ui.quiz

object Question {

    fun getQuestion(): ArrayList<QuizQuestion> {
        val questionList = ArrayList<QuizQuestion>()

        val question1 = QuizQuestion(
            1,
            "Question 1",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question1)

        val question2 = QuizQuestion(
            2,
            "Question 2",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question2)

        val question3 = QuizQuestion(
            3,
            "Question 3",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question3)

        val question4 = QuizQuestion(
            4,
            "Question 4",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question4)

        val question5 = QuizQuestion(
            5,
            "Question 5",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question5)

        val question6 = QuizQuestion(
            6,
            "Question 6",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question6)

        val question7 = QuizQuestion(
            7,
            "Question 7",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question7)

        val question8 = QuizQuestion(
            8,
            "Question 8",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question8)


        val question9 = QuizQuestion(
            9,
            "Question 9",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question9)

        val question10 = QuizQuestion(
            10,
            "Question 10",
            "A",
            "B",
            "C",
            "D",
            2
        )

        questionList.add(question10)

        return questionList

    }
}