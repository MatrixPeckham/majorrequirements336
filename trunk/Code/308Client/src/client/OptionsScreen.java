package client;
/**
 * i'm not even sure what this screen is for
 * but it's in the diagram so i'm adding it
 * @author Bill
 *
 */
public class OptionsScreen extends Screen {
	
	/**
	 * serial version ID that eclispe wants in swing classes
	 */
	private static final long serialVersionUID = -5513448923246855254L;
	/**
	 * constructor
	 * @param gui passes to superclass of Screen
	 * @param i don't know, i don't remember talking about an options screen
	 */
	public OptionsScreen(ClientGUI gui, int i) {
		super(gui);
	}

	@Override
	public void getScreen(Object fillWith) {
		// TODO Auto-generated method stub

	}

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
