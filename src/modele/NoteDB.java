/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Anthony
 */
public class NoteDB extends Note implements CRUD {

    protected static Connection dbConnect = null;

    public NoteDB() {
        super();
    }

    public NoteDB(int id_note) {
        super(id_note);
    }

    public NoteDB(String titre, String contenu, Date date_note, int id_carnet, int id_categorie) {
        super(titre, contenu, date_note, id_carnet, id_categorie);
    }

    public NoteDB(int id_note, String titre, String contenu, Date date_note, int id_carnet, int id_categorie) {
        super(id_note, titre, contenu, date_note, id_carnet, id_categorie);
    }

    public static void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    @Override
	public void create() throws Exception {
        CallableStatement cstmt = null;
        try {
            String query1 = "call createNote(?,?,?,?,?)";
            String query2 = "select note_seq.currval from dual";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
            pstm1.setString(1, titre);
            pstm1.setString(2, contenu);
            pstm1.setDate(3, date_note);
            pstm1.setInt(4, id_carnet);
            pstm1.setInt(5, id_categorie);
            int nl = pstm1.executeUpdate();
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int nc = rs.getInt(1);
                id_note = nc;
            } else {
                System.out.println("Erreur de l'ajout");
            }

        } catch (Exception e) {
            throw new Exception("Erreur de cr�ation " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
	public void read() throws Exception {

        CallableStatement cstmt = null;
        try {
            boolean trouve = false;
            String query1 = "SELECT * FROM note WHERE id_note = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, id_note);
            ResultSet rs = pstm1.executeQuery();
            if (rs.next()) {
                trouve = true;
                id_note = rs.getInt("ID_NOTE");
                titre = rs.getString("TITRE");
                contenu = rs.getString("CONTENU");
                id_carnet = rs.getInt("ID_CARNET");
                id_categorie = rs.getInt("ID_CATEGORIE");
            }
            if (!trouve) {
                id_note = -1;
                throw new Exception("numero inconnu dans la table !");
            }
        } catch (Exception e) {

            throw new Exception("Erreur de lecture " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     *
     * @throws Exception erreur de mise � jour
     */
    @Override
	public void update() throws Exception {
        CallableStatement cstmt = null;

        try {
            String query1 = "call UpdateNote(?,?,?,?,?,?)";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, id_note);
            pstm1.setString(2, titre);
            pstm1.setString(3, contenu);
            pstm1.setDate(4, date_note);
            pstm1.setInt(5, id_carnet);
            pstm1.setInt(6, id_categorie);
            int nl = pstm1.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur de mise � jour : " + e.getMessage());
        } finally {//effectu� dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
	public void delete() throws Exception {

        CallableStatement cstmt = null;
        try {
            String query1 = "call DeleteNote(?)";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, id_note);
            int nl = pstm1.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur d'effacement : " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public static ArrayList<NoteDB> getCarnet(int var) throws Exception {
        ArrayList<NoteDB> list = new ArrayList();
        CallableStatement cstmt = null;
        try {
            boolean trouve = false;
            String query1 = "select * from note where id_carnet = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, var);
            ResultSet rs = pstm1.executeQuery();
            while (rs.next()) {
                trouve = true;
                list.add(new NoteDB(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6)));
            }
            if (!trouve) {
                return null;
            } else {
                return list;
            }
        } catch (Exception e) {
            throw new Exception("Erreur: " + e.getMessage());
        } finally {//effectué dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }

    }

}
