package model;
import java.io.*;

public class ArquivoPersistencia implements Persistencia {
    private final String caminho;
    public ArquivoPersistencia(String caminho) { this.caminho = caminho; }
    @Override public void salvar(Planejamento p) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(p);
        }
    }
    @Override public Planejamento carregar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho))) {
            Object obj = in.readObject();
            if (!(obj instanceof Planejamento)) throw new IOException("Arquivo não contém um planejamento válido.");
            return (Planejamento) obj;
        }
    }
}
