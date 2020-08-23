package app;

import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        String nome, descricao;
		java.util.Date data;
        int Entrada, Option, IdCliente, idFilme, IdAluguel;
        float Valor;            

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:3001/db1", "postgres", "admin");
            conn.setAutoCommit(false);

            //Demonstrar o funcionamento aqui
           Entrada = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da ação de deseja realizar \n"
            		+"1- Gerenciamento de Cliente\n"
            		+"2- Gerenciamento de Filmes\n"
            		+"3- Gerenciamento de Aluguéis\n"));
            
            if(Entrada == 1) {
	            Option = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da ação de deseja realizar \n"
	            		+ "1-Inserir\n"
	            		+ "2-Atualizar \n"
	            		+ "3-Deletar \n"
	            		+ "4-Procurar \n"
	            		+ "5-Listar \n"));
	       
	            ClienteDAOImpl ClienteImpl = new ClienteDAOImpl();
	            Cliente Cliente = new Cliente();
	            	            
	            switch (Option) {
				case 1:
					nome = JOptionPane.showInputDialog("Digite o nome do Cliente a ser cadastrado");
					Cliente.setNome(nome);
	            	ClienteImpl.insert(conn , Cliente);
	            	JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
					break;       
					
				case 2:
					IdCliente = Integer.parseInt( JOptionPane.showInputDialog("Digite o id do Cliente que deseja alterar"));
					nome = JOptionPane.showInputDialog("Digite o novo nome do Cliente");
					Cliente.setIdCliente(IdCliente);
					Cliente.setNome(nome);
					ClienteImpl.edit(conn , Cliente);
					JOptionPane.showMessageDialog(null, "Nome alterado com sucesso!");
					break;
					
				case 3:
					IdCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o número id do Cliente a ser deletado"));
	            	Cliente.setIdCliente(IdCliente);
	            	ClienteImpl.delete(conn, IdCliente);
	            	JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
	            	break;

				case 4:
					IdCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o número do id do Cliente que deseja localizar"));
					Cliente.setIdCliente(IdCliente);
					JOptionPane.showMessageDialog(null, ClienteImpl.find(conn, IdCliente));
					break;
					
				case 5:
					JOptionPane.showMessageDialog(null,ClienteImpl.list(conn));
					break;
	            }
	            
	            	            
           }else if (Entrada == 2) {
	            Option = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da ação de deseja realizar \n"
	            		+ "1-Inserir\n"
	            		+ "2-Atualizar \n"
	            		+ "3-Deletar \n"
	            		+ "4-Listar \n"
	            		+ "5-Procurar \n"));
	       
	            FilmeDAOImpl filmeImpl = new FilmeDAOImpl();
	            Filme Filme = new Filme();
	            
	            switch (Option) {
				case 1:
					nome = JOptionPane.showInputDialog("Digite o nome do Filme a ser cadastrado");
					data = new SimpleDateFormat("dd/MM/yyyy").parse((JOptionPane.showInputDialog("Digite a data de lançamento do Filme a ser cadastrado")));
					descricao = JOptionPane.showInputDialog("Digite o nome do Filme a ser cadastrado");
					
					Filme.setDescricao(descricao);
					Filme.setDataLancamento(data);
					Filme.setNome(nome);
					filmeImpl.insert(conn , Filme);
					
	            	JOptionPane.showMessageDialog(null, "Filme cadastrado com sucesso!");
					break;
					
				case 2:
					idFilme = Integer.parseInt( JOptionPane.showInputDialog("Digite o id do Filme que deseja alterar"));
					nome = JOptionPane.showInputDialog("Digite o novo nome do Filme");
					descricao = JOptionPane.showInputDialog("Digite a nova descrição do filme");
					data = new SimpleDateFormat("dd/MM/yyyy").parse((JOptionPane.showInputDialog("Digite a data de lançamento do Filme a ser alterado")));
					
					Filme.setDataLancamento(data);
					Filme.setDescricao(descricao);
					Filme.setIdFilme(idFilme);
					Filme.setNome(nome);
					filmeImpl.edit(conn , Filme);
					JOptionPane.showMessageDialog(null, "Filme alterado com sucesso!");
					break;
					
				case 3:
					idFilme = Integer.parseInt(JOptionPane.showInputDialog("Digite o número id do Filme a ser deletado"));
					Filme.setIdFilme(idFilme);
	            	filmeImpl.delete(conn, idFilme);
	            	JOptionPane.showMessageDialog(null, "Filme deletado com sucesso!");
	            	break;
	            	
				case 4:
					idFilme = Integer.parseInt(JOptionPane.showInputDialog("Digite o número do id do Filme que deseja localizar"));
					Filme.setIdFilme(idFilme);
					JOptionPane.showMessageDialog(null, filmeImpl.find(conn, idFilme));
					break;
					
				case 5:
					JOptionPane.showMessageDialog(null,filmeImpl.list(conn));
					break;

	            }
           }
           
           else if (Entrada == 3){
        	   
        	   Cliente cliente = new Cliente();
        	   Aluguel aluguel = new Aluguel();
        	   AluguelDAOImpl aluguelImpl = new AluguelDAOImpl();
        	          	   
	            Option = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da ação de deseja realizar \n"
	            		+ "1-Inserir\n"
	            		+ "2-Atualizar \n"
	            		+ "3-Deletar \n"
	            		+ "4-Listar \n"
	            		+ "5-Procurar \n"));
        	   
	            switch (Option) {
				case 1:
					IdCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente que deseja realizar o aluguel"));
					data = new SimpleDateFormat("dd/MM/yyyy").parse((JOptionPane.showInputDialog("Digite a data de locação do filme")));
					Valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do aluguel"));
					
					aluguel.setCliente(cliente);
					cliente.setIdCliente(IdCliente);
					aluguel.setDataAluguel(data);
					aluguel.setValor(Valor);
					aluguelImpl.insert(conn , aluguel);
					
	            	JOptionPane.showMessageDialog(null, "Aluguel cadastrado com sucesso!");
					break;
	            
				case 2:
					
					IdAluguel = Integer.parseInt( JOptionPane.showInputDialog("Digite o id do Aluguel que deseja alterar"));
					IdCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente que realizou o aluguel"));
					data = new SimpleDateFormat("dd/MM/yyyy").parse((JOptionPane.showInputDialog("Digite a data de locação do filme")));
					Valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do aluguel"));
					
					aluguel.setCliente(cliente);
					cliente.setIdCliente(IdCliente);
					aluguel.setIdAluguel(IdAluguel);
					aluguel.setDataAluguel(data);
					aluguel.setValor(Valor);
					aluguelImpl.edit(conn , aluguel);
					
					JOptionPane.showMessageDialog(null, "Aluguel alterado com sucesso!");
					break;	
					
				case 3:
					IdAluguel = Integer.parseInt(JOptionPane.showInputDialog("Digite o número id do Aluguel a ser deletado"));
					aluguel.setIdAluguel(IdAluguel);
					aluguelImpl.delete(conn , aluguel);
	            	JOptionPane.showMessageDialog(null, "Filme deletado com sucesso!");
	            	break;
	            	
				case 4:
					IdAluguel = Integer.parseInt(JOptionPane.showInputDialog("Digite o número do id do Aluguel que deseja localizar"));
					aluguel.setIdAluguel(IdAluguel);
					cliente.setIdCliente(17);
					aluguel.setCliente(cliente);
					JOptionPane.showMessageDialog(null, aluguelImpl.find(conn , IdAluguel));
					break;	
					
				case 5:
					JOptionPane.showMessageDialog(null,aluguelImpl.list(conn));
					break;
	            }	
	            
           }else {
        	   JOptionPane.showMessageDialog(null,"Digite um número Válido");
        	   };        
                     
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
    }
}

