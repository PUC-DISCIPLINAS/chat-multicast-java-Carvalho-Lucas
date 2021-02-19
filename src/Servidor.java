import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) {
		ServerSocket servidor = null;
		
		try {
			System.out.println("Servidor Iniciado");
			servidor = new ServerSocket(9999);
			System.out.println("Servidor started!");
			 
			while(true) {
				Socket cliente = servidor.accept();
				new GerenciadorCliente(cliente);
			}
			
		} catch (IOException e) {
			System.err.println("Porta nao funciona!!");
			
			try { 
				if(servidor != null)
				servidor.close();
			} catch (IOException e1) {
				e.printStackTrace();	
			}
		
		}
		
	}
}
