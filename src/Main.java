import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello world");
		//1. Create the frame.
		JFrame frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
		//...create emptyLabel...

		//4. Size the frame.
		frame.pack();

		//5. Show it.
		frame.setVisible(true);
		
	}

}
