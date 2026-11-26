import controller.PlanejamentoController;
import view.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal(new PlanejamentoController()).setVisible(true));
        System.out.println("Hello World");
    }
}
