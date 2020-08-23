package dao.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import dao.FilmeDAO;
import entidades.Filme;
import java.sql.Date;

public class FilmeDAOImpl implements FilmeDAO{
	
	java.util.Date data;
	
	public void insert(Connection conn, Filme filme) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?);");

        Integer idFilme = this.getNextId(conn);
         
        data =  filme.getDataLancamento();
     
        java.sql.Date dataSql = new java.sql.Date(data.getTime());
        
        myStmt.setInt(1, idFilme);
        myStmt.setDate(2, (Date) dataSql);
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());

        myStmt.execute();
        conn.commit();


        filme.setIdFilme(idFilme);

    }
		
	public Integer getNextId(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

	public void edit(Connection conn, Filme filme) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("update en_filme set (data_lancamento, nome, descricao) = (?, ?, ?) where id_filme = ?");

        data =  filme.getDataLancamento();
        
        java.sql.Date dataSql = new java.sql.Date(data.getTime());
        
        myStmt.setDate(1, (Date) dataSql);
        myStmt.setString(2, filme.getNome());
        myStmt.setString(3, filme.getDescricao());
        myStmt.setInt(4, filme.getIdFilme());

        myStmt.execute();
        conn.commit();
	}

	public void delete(Connection conn, Integer idFilme) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_filme = ?; \r\n" 
        												+"delete from en_filme where id_filme = ?;");

        myStmt.setInt(1, idFilme);
        myStmt.setInt(2, idFilme);
        myStmt.execute();
        conn.commit();

    }


	public Filme find(Connection conn, Integer idFilme) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select * from en_filme where id_filme = ?");

        myStmt.setInt(1, idFilme);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }

        String nome = myRs.getString("nome");
        String descricao = myRs.getString("descricao");
        Date data = myRs.getDate("data_lancamento");
        return new Filme(idFilme, data, nome, descricao);
    }

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select * from en_filme order by id_filme");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        while (myRs.next()) {
        	Integer idFilme = myRs.getInt("id_filme");
            String nome = myRs.getString("nome");
            String descricao = myRs.getString("descricao");
            Date data = myRs.getDate("data_lancamento");

            items.add(new Filme(idFilme, data, nome, descricao));
        }
        return items;
    }

}
