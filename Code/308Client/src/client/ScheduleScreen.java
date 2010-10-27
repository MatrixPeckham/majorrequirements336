package client;
/**
 * Screen to view generated schedule
 * @author Bill
 *
 */
public class ScheduleScreen extends Screen {

	/**
	 * serial version ID that eclipse wants in swing classes
	 */
	private static final long serialVersionUID = 6345886273148921957L;

	/**
	 * Takes a ClientGUI for the super class
	 * should take a schedule object, but that doesn't
	 * exist at the time of writing this. 
	 * @param gui GUI for Screen
	 * TODO add schedule parameter because Diagram says to
	 * not sure it needs it though
	 */
	public ScheduleScreen(ClientGUI gui) {
		super(gui);
	}

	@Override
	public void getScreen() {
		// TODO Auto-generated method stub

	}

}
