package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import dao.AluguelDAO;
import entidades.Aluguel;
import entidades.Cliente;

public class AluguelDAOImpl implements AluguelDAO {
	
	java.util.Date data;

	
	public void insert(Connection conn, Aluguel aluguel ) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("INSERT INTO en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) VALUES (?, ?, ?, ?);");
        										//		+	"INSERT INTO re_aluguel_filme (id_filme,id_aluguel) VALUES (?, ?)"
        
        Integer idAluguel = this.getNextId(conn);
        data = aluguel.getDataAluguel();
        java.sql.Date dataSql = new java.sql.Date(data.getTime());
        
        
        myStmt.setInt(1, idAluguel);
        myStmt.setInt(2, aluguel.getCliente().getIdCliente());
        myStmt.setDate(3, (Date) dataSql);       
        myStmt.setFloat(4, aluguel.getValor());
        myStmt.execute();
        conn.commit();
     }

	public Integer getNextId(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_cliente')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

	public void edit(Connection conn, Aluguel aluguel) throws Exception {
    	
        PreparedStatement myStmt = conn.prepareStatement("update en_aluguel set (id_cliente, data_aluguel, valor) = (?, ?, ?) where id_aluguel = (?);");

        data = aluguel.getDataAluguel();
        java.sql.Date dataSql = new java.sql.Date(data.getTime());
        
        myStmt.setInt(1, aluguel.getCliente().getIdCliente());
        myStmt.setDate(2, (Date) dataSql);
        myStmt.setDouble(3, aluguel.getValor());
        myStmt.setInt(4, aluguel.getIdAluguel());
        myStmt.execute();
        conn.commit();
        
    }

	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = ?;");

		myStmt.setInt(1, aluguel.getIdAluguel());
		myStmt.execute();
		conn.commit();

	}

	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }
        
        Aluguel aluguel = new Aluguel();
        Cliente cliente = aluguel.getCliente();

        
        
        int idCliente = myRs.getInt("id_cliente");
        System.out.println(idCliente);
        
        idAluguel = myRs.getInt("id_aluguel");
        Date data = myRs.getDate("data_aluguel");
        float valor = myRs.getFloat("valor");
  
        return new Aluguel(idAluguel, null, cliente, data, valor);
    }

	public Collection<Aluguel> list(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel order by id_aluguel");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Aluguel> items = new ArrayList<>();

        while (myRs.next()) {
        	Integer idAluguel = myRs.getInt("id_aluguel");
        	//Não consegui atribui o valor do id para clase cliente
        	//Integer idCliente = myRs.getInt("id_cliente");
            Date data_aluguel = myRs.getDate("data_aluguel");
            float valor = myRs.getFloat("valor");
            
            items.add(new Aluguel(idAluguel, null, null, data_aluguel, valor));
        }
        return items;
    }

};
