import controller.Controller;
import model.Model;

public class App {

	public static void main(String[] args) {		
		Model model = new Model();
		//View view = new View(model);
		new Controller(model);
	}
}
