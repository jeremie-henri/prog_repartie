import java.io.*;
import java.net.Socket;

public class tp4 {
    private String serveur;
    private int port;
    private String fileName;

    public tp4(String serveurFTP, int port, String fileName) {
        this.serveur = serveurFTP;
        this.port = port;
        this.fileName = fileName;
    }

    public void recupererFichier() throws IOException {
        Socket socket = new Socket(serveur, port);

        InputStream input = socket.getInputStream();

        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(fileName);
        out.newLine();
        out.flush();

        byte[] buf = new byte[512];
        int nbOctets;

        while ((nbOctets = input.read(buf)) != -1) {
            fileOutputStream.write(buf, 0, nbOctets);
        }

        fileOutputStream.close();
        input.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        tp4 client = new tp4("10.203.9.177", 50000, "image.jpg");
        client.recupererFichier();
    }
}