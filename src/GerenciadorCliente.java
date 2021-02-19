import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorCliente extends Thread {

	private Socket cliente;
	private String nomeCliente;
	private BufferedReader leitor;
	private PrintWriter escritor;
	private static final Map<String, GerenciadorCliente> clientes = new HashMap<String, GerenciadorCliente>();

	// Comunicação entre cliente/servidor - cliente/cliente

	public GerenciadorCliente(Socket cliente) {
		this.cliente = cliente;
		start();
	}

	// Gerencia cv entre clientes
	@Override
	public void run() {
		try {
			leitor = new BufferedReader(new java.io.InputStreamReader(cliente.getInputStream()));
			escritor = new PrintWriter(cliente.getOutputStream(), true);
			efetuarLogin();
			String msg;

			while (true) {
				msg = leitor.readLine();
				if (msg.equalsIgnoreCase(Comandos.SAIR)) {
					this.cliente.close();
					Thread.sleep(500);
				} else if (msg.startsWith(Comandos.MENSAGEM)) {
					String nomeDestinatario = msg.substring(Comandos.MENSAGEM.length(), msg.length());
					System.out.println("enviado para " + nomeDestinatario);
					GerenciadorCliente destinatario = clientes.get(msg.subSequence(5, msg.length()));
					if (destinatario == null) {
						escritor.println("O cliente informado nao existe!");
					} else {
//					escritor.println("Digite uma mensagem para" + destinatario.getNomeCliente());
						destinatario.getEscritor().println(this.nomeCliente + " disse: " + leitor.readLine());
					}
					// lista o nome dos participantes do chat
				} else if (msg.equals(Comandos.LISTA_USUARIOS)) {
					atualizarListaUsuarios(this);
				} else {
					escritor.println(this.nomeCliente + ", Voce disse: " + msg);
				}
			}

		} catch (IOException e) {
			System.err.println("Cliente fechou conexao");
			clientes.remove(this.nomeCliente);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void efetuarLogin() throws IOException {

		while(true) {
		escritor.println(Comandos.LOGIN);
		this.nomeCliente = leitor.readLine().toLowerCase().replaceAll(",", "");
		if(this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()){
			escritor.println(Comandos.LOGIN_NEGADO);
		}else if (clientes.containsKey(this.nomeCliente)) {
			escritor.println(Comandos.LOGIN_NEGADO);
		}else {
			escritor.println(Comandos.LOGIN_ACEITO);
			escritor.println("Ola " + this.nomeCliente);
			clientes.put(this.nomeCliente, this);
			for (String cliente : clientes.keySet()) {
				atualizarListaUsuarios(clientes.get(cliente));
				}
				break;
			}
		}
	}

	private void atualizarListaUsuarios(GerenciadorCliente cliente) {
		StringBuffer str = new StringBuffer();
		for (String c : clientes.keySet()) {
			if(cliente.getNomeCliente().equals(c))
				continue;
			
			str.append(c);
			str.append(",");
		}
		if(str.length() > 0 )
		str.delete(str.length() - 1, str.length());
		cliente.getEscritor().println(Comandos.LISTA_USUARIOS);
		cliente.getEscritor().println(str.toString());
	}

	public PrintWriter getEscritor() {
		return escritor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}
//	public BufferedReader getLeitor() {
//		return leitor;
//	}
}
