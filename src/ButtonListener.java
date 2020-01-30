import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
	
	private String searchText = "";
	
	public ButtonListener(String searchText) {
		this.searchText = searchText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(searchText);
	}

}
