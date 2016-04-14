package mancala;

public class GoalLogic extends CupLogic {

	public GoalLogic() {
		super();
	}

	@Override
	public void reset() {
		count = 0;
	}

	public void addToGoal(int amount) {
		count = count + amount;
	}

}
