package illek.general.smart;
public class Challenge {
	
	int level;
	
	String Question;
	String[] Choices;
	
	int answer;
	
	public String[] getChoices(){ return Choices; }
	public String getQuestion() { return Question;}
	public String getTalking()
	{
		String talk = Question;
		for (int x = 0; x<Choices.length; x++)
		{
			talk+= Choices[x] + " . ";
		}
		return talk;
	}
	public int getLevel() { return level;}
	
	public Challenge(String myQuestion, String[] myChoices, int myAnswer)
	{
		Question = myQuestion;
		Choices = myChoices;
		answer = myAnswer;
	}

	public boolean guess(int myGuess){ return (myGuess == answer); }
}

	
