import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import net.proteanit.sql.DbUtils;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class GestionAbsence {
	static Connection conn = Utilitaire.getConnection();
	static JTable table;
	public static void AjouterAbsence(int idEleve,boolean b,String jour ,String heureDebut,String heureFin) {
		if(JOptionPane.showConfirmDialog(GAC.App, "Confirmation d'opperation", "Gestion d'un colege",
				JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
		
			String Query = "INSERT INTO absence "
					+ "(idAbsence, jour, heureDebut,heureFin, excuse, idEleve) "
					+ "VALUES (NULL,"+ "\"" + jour + "\" ," + "\"" + heureDebut+"\" ," +"\""+ heureFin+"\" ," +b + ", "+ idEleve+")";
			System.out.println(Query);
			try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate(Query);
			JOptionPane.showMessageDialog(GAC.App, "insertion termin\u00e9 ... l'affectation reusite");
			return;
			}catch (Exception e) {
				JOptionPane.showMessageDialog(GAC.App, "L'op\u00e9ration est arr\u00e9t\u00e9e");
				return;
			}
		}
		else {
			JOptionPane.showMessageDialog(GAC.App, "L'op\u00e9ration est arr\u00e9t\u00e9e");
			return;
		}
		}
	
	public static void afficherAbsence() {
		
	}
public static void lesAbsences(String jour ,String nomClasse) {
	
		try {
			Connection conn = Utilitaire.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT eleve.idEleve, nomEleve, prenomEleve, absence.heureDebut, absence.heureFin, excuse FROM eleve, absence , classe WHERE eleve.idEleve = absence.idEleve AND classe.nomClasse = ? AND jour = ?");
			ps.setString(1, nomClasse);
			ps.setString(2, jour);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		   }
			catch(Exception exe) {
				exe.printStackTrace();
			}
	}
		
	}


