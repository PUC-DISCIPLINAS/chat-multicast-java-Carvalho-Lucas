import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;

public class ClienteSwing extends JFrame {

	private static final long serialVersionUID = 2910040664567581620L;
	private JTextArea taEditor = new JTextArea("Digite sua mensagem");
	private JTextArea taVisor = new JTextArea();
	private JList liUsuarios = new JList();
	private PrintWriter escritor;
	private BufferedReader leitor;
	private JScrollPane ScrollTaVisor = new JScrollPane(taVisor);

	public ClienteSwing() {

		setTitle("Bem vindo ao Chat!");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		liUsuarios.setBackground(Color.lightGray);
		taEditor.setBackground(Color.gray);

		taEditor.setPreferredSize(new Dimension(400, 40));
//		taVisor.setPreferredSize(new Dimension(350, 100));
		taVisor.setEditable(false);

		liUsuarios.setPreferredSize(new Dimension(100, 140));

		add(taEditor, BorderLayout.SOUTH);
		add(ScrollTaVisor, BorderLayout.CENTER);
		add(new JScrollPane(liUsuarios), BorderLayout.WEST);

		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		String[] usuarios = new String[] { "Lucas", "Luiz", "Joao", "Jose" };
		listaDeUsuarios(usuarios);
	}

	private void listaDeUsuarios(String[] usuarios) {
		DefaultListModel modelo = new DefaultListModel();
		liUsuarios.setModel(modelo);
		for (String usuario : usuarios) {
			modelo.addElement(usuario);
		}
		// liUsuarios.getModel().
	}

	private void iniciarEscritor() {
		taEditor.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// taEditor.getText();
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					// escreve mensagem para o servidor
					if (taVisor.getText().isEmpty()) {
						return;
					}
					Object usuario = liUsuarios.getSelectedValue();
					if (usuario != null) {
						
						taVisor.append("Eu: ");
						taVisor.append(taEditor.getText());
						taVisor.append("\n");

						escritor.println(Comandos.MENSAGEM + usuario);
						escritor.println(taEditor.getText());

						// limpar editor
						taEditor.setText("");
						e.consume();
						
					} else {
						if (taVisor.getText().equalsIgnoreCase(Comandos.SAIR)) {
							System.exit(0);
						}
						JOptionPane.showMessageDialog(ClienteSwing.this, "Selecione um usuario");
						return;
					}
				}
			}
		});
	}

	public void iniciarChat() {
		try {
			final Socket cliente = new Socket("127.0.0.1", 9999);
			escritor = new PrintWriter(cliente.getOutputStream(), true);
			leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

		} catch (UnknownHostException e) {
			System.out.println("O endereço passado eh invalido!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("O servidor esta fora do ar :(");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ClienteSwing cliente = new ClienteSwing();
		cliente.iniciarChat();
		cliente.iniciarEscritor();
		cliente.IniciarLeitor();
	}

	private void atualizarListaUsuarios() {
		escritor.println(Comandos.LISTA_USUARIOS);
	}

	private void IniciarLeitor() {
		// le mensagem do servidor
		try {
			while (true) {
				String mensagem = leitor.readLine();
				if (mensagem == null || mensagem.isEmpty())
					continue;

				// recebe texto
				if (mensagem.equals(Comandos.LISTA_USUARIOS)) {
					String[] usuarios = leitor.readLine().split(",");
					listaDeUsuarios(usuarios);
				} else if (mensagem.equals(Comandos.LOGIN)) {
					String login = JOptionPane.showInputDialog("Qual o seu login ?");
					escritor.println(login);
				} else if (mensagem.equals(Comandos.LOGIN_NEGADO)) {
					JOptionPane.showMessageDialog(ClienteSwing.this,"Login invalido!!!");
				} else if (mensagem.equals(Comandos.LOGIN_ACEITO)) {
					atualizarListaUsuarios();
				} else {
					taVisor.append(mensagem);
					taVisor.append("\n");
					taVisor.setCaretPosition(taVisor.getDocument().getLength());
				}
			}
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler a mensagem do servidor!");
			e.printStackTrace();
		}
	}

	private DefaultListModel getListaUsuarios() {
		return (DefaultListModel) liUsuarios.getModel();
	}

}
