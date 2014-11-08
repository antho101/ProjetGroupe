package modele;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class NoteDB extends Note implements CRUD {

    protected static Connection dbConnect=null;
    
public NoteDB() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NoteDB(int id_note, String contenu, Date date_note, int id_carnet,
		int id_categorie) {
	super(id_note, contenu, date_note, id_carnet, id_categorie);
	// TODO Auto-generated constructor stub
}

	public NoteDB(int id_note, String titre, String contenu, Date date_note,
			int id_carnet, int id_categorie) {
		super(id_note, titre, contenu, date_note, id_carnet, id_categorie);
		// TODO Auto-generated constructor stub
	}

	public NoteDB(int id_note) {
		super(id_note);
		// TODO Auto-generated constructor stub
	}

/**
   * méthode statique permettant de partager la connexion entre toutes les instances de
   * ChambreDB
   * @param nouvdbConnect connexion à la base de données
   */
   public static void setConnection(Connection nouvdbConnect) {
      dbConnect=nouvdbConnect;
   }

  /**
   * enregistrement d'une nouvelle chambre dans la base de données
   * @throws Exception erreur de création
   */
   public void create() throws Exception{
        CallableStatement   cstmt=null;
       try{
            String query1="INSERT INTO note(id_note, titre, contenu, date_note, id_carnet,id_categorie) VALUES(?,?,?,?,?,?)";
            //String query2="select client_seq.currval from dual" ;
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1,id_note);
            pstm1.setString(2,titre);
            pstm1.setString(3,contenu);
            pstm1.setDate(4,date_note);
            pstm1.setInt(5,id_carnet);
            pstm1.setInt(6,id_categorie);
            int nl = pstm1.executeUpdate();
            
       }
       catch(Exception e ){
                throw new Exception("Erreur de création "+e.getMessage());
             }
       finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
       }
   
/**
 * récupération des données d'une chambre sur base de son numero
 * @throws Exception code inconnu
 */
   public static ArrayList<NoteDB> read_all() throws Exception{
    CallableStatement cstmt=null;
    ArrayList<NoteDB> plusieurs=new ArrayList<NoteDB>();
        try{
             String query1="SELECT * FROM note where id_carnet= ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             ResultSet rs= pstm1.executeQuery();
             while(rs.next()){
                 int id_noteTmp = rs.getInt("ID_NOTE");
                 String titreTmp = rs.getString("TITRE");
                 String contenuTmp = rs.getString("CONTENU");
                 Date dateTmp = rs.getDate("DATE_NOTE");
                 int id_carnetTmp = rs.getInt("ID_CARNET");
                 int id_categorieTmp = rs.getInt("ID_CATEGORIE");
                 NoteDB temp = new NoteDB(id_noteTmp, titreTmp,contenuTmp,dateTmp,id_carnetTmp,id_categorieTmp);
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
   public void read(int numeroTmp)throws Exception{
       
        CallableStatement cstmt=null;
        try{
            boolean trouve=false;
             String query1="SELECT * FROM note WHERE id_note = ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             pstm1.setInt(1,numeroTmp);
             ResultSet rs= pstm1.executeQuery();
             if(rs.next()){
                 trouve = true;
                 id_note = rs.getInt("ID_NOTE");
                 titre = rs.getString("TITRE");
                 contenu = rs.getString("CONTENU");
                 id_carnet = rs.getInt("ID_CARNET");
                 id_categorie = rs.getInt("ID_CATEGORIE");
             }
             if(!trouve)throw new Exception("numero inconnu dans la table !");
        }
	catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{//effectué dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }
/**
 * méthode statique permettant de récupérer toutes les chambre portant un certain numero
 * @param rechNumero numero recherché
 * @return liste des chambres
 * @throws Exception nom inconnu
 */
   public static ArrayList<NoteDB> rechNumero(String titre)throws Exception{
	    ArrayList<NoteDB> plusieurs=new ArrayList<NoteDB>();
	    CallableStatement cstmt=null;
	    try{
                boolean trouve=false;
                String query1="SELECT * FROM note WHERE titre = ?";
                PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
                pstm1.setString(2,titre);
                ResultSet rs= pstm1.executeQuery();
                if(rs.next()){
                    trouve = true;
                    int id_noteTmp=rs.getInt("ID_NOTE");
                    String cotenuTmp=rs.getString("CONTENU");
                    Date DateTmp=rs.getDate("DATE_NOTE");
                    int id_carnetTmp=rs.getInt("ID_CARNET");
                    int id_categorieTmp=rs.getInt("ID_CATEGORIE");
                    plusieurs.add(new NoteDB(id_noteTmp, cotenuTmp, DateTmp,id_carnetTmp,id_categorieTmp ));
                }
                
              if(!trouve)throw new Exception("nom inconnu");
              else return plusieurs;
	     }
	     catch(Exception e){
		
                throw new Exception("maj echoué");
             }
            finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }
	
     /**
      * mise à jour des données de la chambre sur base de son numero
      * @throws Exception erreur de mise à jour
      */

    public void update() throws Exception{
        CallableStatement cstmt=null;

    try{
            String query1="UPDATE note SET titre = ?, contenu = ?,date_note = ?,id_carnet = ?,id_categorie = ? WHERE id_note = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1,id_note);
            pstm1.setString(2,titre);
            pstm1.setString(3,contenu);
            pstm1.setDate(4,date_note);
            pstm1.setInt(5,id_carnet);
            pstm1.setInt(6,id_categorie);
            int nl = pstm1.executeUpdate();
            if(nl == 1)
            {
                System.out.println("La ligne a bien été mise a jour !");
            }else{
                System.out.println("Aucune ligne n'a été mise a jour !");
            }
            
    }

	  catch(Exception e){
	  	
                throw new Exception("Erreur de mise à jour : "+e.getMessage());
             }
          finally{//effectué dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
    }
 
 /**
  * effacement de la chambre sur base de son numero
  * @throws Exception erreur d'effacement
  */
    public void delete()throws Exception{
	
           CallableStatement cstmt =null;
	   try{
               String query1="DELETE note WHERE id_note = ?";
               PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
               pstm1.setInt(1,id_note);
               int nl = pstm1.executeUpdate();
               if(nl == 1)
               {
                   System.out.println("La ligne a bien été suprimé !");
               }else{
                   System.out.println("Aucune ligne n'a été suprimé !");
               }
             
	   }	
	   catch (Exception e){
	     	
                throw new Exception("Erreur d'effacement : "+e.getMessage());
             }
           finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
          }
   	}

    @Override
    public void read() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}


