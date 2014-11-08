package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategorieDB extends Categorie implements CRUD{
    protected static Connection dbConnect=null;

	
	  public CategorieDB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategorieDB(int id_categorie, String label, String couleur) {
		super(id_categorie, label, couleur);
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<UserDB> read_all() throws Exception{
		    CallableStatement cstmt=null;
		    ArrayList<UserDB> plusieurs=new ArrayList<UserDB>();
		        try{
		             String query1="SELECT * FROM categorie";
		             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
		             ResultSet rs= pstm1.executeQuery();
		             while(rs.next()){
		                 int numeroTmp = rs.getInt("ID_USER");
		                 String pseudoTmp = rs.getString("PSEUDO");
		                 String mailTmp = rs.getString("MAIL");
		                 String passwordTmp = rs.getString("PASSWORD");
		                 UserDB temp = new UserDB(numeroTmp, pseudoTmp,mailTmp,passwordTmp);
		                 plusieurs.add(temp);
		             }
		            return plusieurs;
		        }
			catch(Exception e){
		             
		                throw new Exception("Erreur de lecture "+e.getMessage());
		             }
		        finally{
		            try{
		              cstmt.close();
		            }
		            catch (Exception e){}
		        }
		}

	@Override
	public void create() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
