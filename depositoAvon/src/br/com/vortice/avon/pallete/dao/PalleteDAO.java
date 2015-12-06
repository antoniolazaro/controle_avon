package br.com.vortice.avon.pallete.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;

public class PalleteDAO extends DAOAb {
	
	public List<PalleteModel> selectAll() throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = "SELECT pallete.codigo,pallete.identificacao from pallete order by pallete.identificacao";
        try {
        	stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public void insert(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into pallete values(?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, palleteModel.getCodigo());
        	stmt.setString(2, palleteModel.getIdentificacao());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void delete(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from pallete where codigo = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, palleteModel.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void deleteProdutosFromPallete(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where codigo_pallete = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, palleteModel.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void liberaPosicao(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where exists (select 1 from pallete where identificacao = ? and pallete.codigo = produto_pallete.codigo_pallete)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, palleteModel.getIdentificacao());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public List<PalleteModel> selectAllPalleteByProductFSCCode(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao from pallete");
        sql.append(" WHERE ");
        sql.append(" exists ");
        sql.append(" (select produto_pallete.codigo_pallete,produto.fsc_code,count(*) from produto,produto_pallete ");
        sql.append(" where produto.fsc_code = ? and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" and pallete.codigo = produto_pallete.codigo_pallete ");
        sql.append(" group by produto_pallete.codigo_pallete,produto.fsc_code ");
        sql.append(" having count(*) < 5) ");
        sql.append(" order by pallete.identificacao ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, produtoModel.getFscCode());
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public List<PalleteModel> selectAllPalleteByProductBarCode(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao from pallete");
        sql.append(" WHERE ");
        sql.append(" exists ");
        sql.append(" (select produto_pallete.codigo_pallete,produto.codigo_barras,count(*) from produto,produto_pallete ");
        sql.append(" where produto.codigo_barras = ? and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" and pallete.codigo = produto_pallete.codigo_pallete ");
        sql.append(" group by produto_pallete.codigo_pallete,produto.codigo_barras ");
        sql.append(" having count(*) < 5) ");
        sql.append(" order by pallete.identificacao ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, produtoModel.getCodigoBarras());
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public List<PalleteModel> selectEmptyPallete(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao from pallete");
        sql.append(" WHERE ");
        sql.append(" not exists ");
        sql.append(" (select 1 from produto_pallete,produto ");
        sql.append(" where pallete.codigo = produto_pallete.codigo_pallete ");
        sql.append(" and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" and (produto.codigo_barras = ? or produto.fsc_code = ?)) ");
        sql.append(" order by pallete.identificacao ");
        
        try {
        
        	stmt = connection.prepareStatement(sql.toString());
        	
        	stmt.setString(1, produtoModel.getCodigoBarras());
        	stmt.setString(2, produtoModel.getFscCode());
        	
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public PalleteModel selectPalleteByIdentificacao(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao from pallete");
        sql.append(" WHERE ");
        sql.append(" identificacao = ? ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, palleteModel.getIdentificacao());
            rs = stmt.executeQuery();
            if (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	return palleteNovo;
            }
            return null;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
}
