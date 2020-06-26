import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;

import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class GAC extends JFrame {

	static Connection conn = Utilitaire.getConnection();
	static JFrame App = new JFrame("Gestion d'un colege");
	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("school.png"));
	
	static String selectedClasse;
	private JTable table;
	
	static Vector<String> infosEleves () {
		Vector<String> lesInfos =new Vector<String>();
		try {
			Connection conn = Utilitaire.getConnection();
			PreparedStatement ps = conn.prepareStatement( "SELECT idEleve,prenomEleve,nomEleve FROM eleve WHERE idClasse = (SELECT idClasse FROM classe WHERE nomClasse = ?)");
			ps.setString(1, selectedClasse);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				lesInfos.add(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
			return lesInfos;
		   }
			catch(Exception exe) {
			return null;	
			}
	}
	static Vector<String> lesClasses() {
		Vector<String> lesClasses =new Vector<String>();
		try {
			Connection conn = Utilitaire.getConnection();
			String query = "SELECT nomClasse FROM classe";
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				lesClasses.add(rs.getString(1));
			}
			return lesClasses;
		   }
			catch(Exception exe) {
			return null;	
			}	
	}
	
	/*public void lesAbsences(String jour ,String nomClasse) {
		
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
	}*/

	
	public GAC() {
		
		App.setIconImage(icon.getImage());
		App.setSize(800, 500);
		App.getContentPane().setLayout(null);
		App.setVisible(true);
		App.setResizable(false);
		JMenu File = new JMenu("File");
		JMenu Help = new JMenu("Help");
		JMenu AboutUs = new JMenu("?");
		JMenuBar mbar = new JMenuBar();
		
		mbar.setBackground(Color.WHITE);
		mbar.add(File);
		mbar.add(Help);
		mbar.add(AboutUs);
		
		File.setEnabled(false);
		Help.setEnabled(true);
		AboutUs.setEnabled(false);
		
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem about = new JMenuItem("About us");
		JMenuItem help = new JMenuItem("Voir tutorial");
		JMenu coursMenu = new JMenu("cours");
		JMenu absenceMenu = new JMenu("absence");
		JMenu eleveMenu = new JMenu("eleve");
		JMenuItem Affect = new JMenuItem("Affecter un cours");
		JMenuItem ajouterAbs = new JMenuItem("Ajouter un absent");
		JMenuItem voirAbs = new JMenuItem("Voir la list d'absence");
		JMenuItem ajouterEle = new JMenuItem("Ajouter un eleve");
		JMenuItem transferEle = new JMenuItem("Transfert d'un eleve");
		
		File.add(coursMenu);
		File.add(absenceMenu);
		File.add(eleveMenu);
		File.add(exit);
		
		coursMenu.add(Affect);
		absenceMenu.add(ajouterAbs);
		absenceMenu.add(voirAbs);
		eleveMenu.add(ajouterEle);
		eleveMenu.add(transferEle);
		
		Help.add(help);
		AboutUs.add(about);
		App.setJMenuBar(mbar);
		
		// 
		// ------------------ INSTALL ALL PANELS ----------------------
		
		// Home page Panel
		JPanel Home = new JPanel();
		Home.setBounds(0, 0, 790, 450);
		Home.setVisible(true);
		GroupLayout gl_Home = new GroupLayout(Home);
		gl_Home.setHorizontalGroup(
			gl_Home.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_Home.setVerticalGroup(
			gl_Home.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		Home.setLayout(gl_Home);
		App.add(Home);
		
		// Welcome Panel
		JPanel welcome = new JPanel();
		welcome.setBounds(0, 0, 790, 450);
		welcome.setBackground(Color.WHITE);
		welcome.setVisible(false);
		GroupLayout gl_welcome = new GroupLayout(welcome);
		gl_welcome.setHorizontalGroup(
			gl_welcome.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_welcome.setVerticalGroup(
			gl_welcome.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		welcome.setLayout(gl_welcome);
		
		// Affectation Panel
		JPanel aff = new JPanel();
		aff.setBounds(0, 0, 790, 450);
		aff.setBackground(Color.WHITE);
		aff.setVisible(false);
		GroupLayout gl_aff = new GroupLayout(aff);
		gl_aff.setHorizontalGroup(
			gl_aff.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_aff.setVerticalGroup(
			gl_aff.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		aff.setLayout(gl_aff);
		
		// Absence Panel
		JPanel abs = new JPanel();
		abs.setBackground(Color.WHITE);
		abs.setBounds(0, 0, 790, 450);
		abs.setVisible(false);
		GroupLayout gl_abs = new GroupLayout(abs);
		gl_abs.setHorizontalGroup(
				gl_abs.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_abs.setVerticalGroup(
				gl_abs.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		abs.setLayout(gl_abs);
		
		// GestionEleve Panel
		JPanel GE1 = new JPanel();
		GE1.setBackground(Color.WHITE);
		GE1.setBounds(0, 0, 790, 450);
		GE1.setVisible(false);
		GroupLayout gl_GE1 = new GroupLayout(GE1);
		gl_GE1.setHorizontalGroup(
				gl_GE1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_GE1.setVerticalGroup(
				gl_GE1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		GE1.setLayout(gl_GE1);
		App.add(GE1);
		
		// AboutUs Panel

		JPanel Aboutus = new JPanel();
		Aboutus.setBounds(0, 0, 790, 450);
		Aboutus.setBackground(Color.WHITE);
		Aboutus.setVisible(false);
		GroupLayout gl_Aboutus = new GroupLayout(Aboutus);
		gl_Aboutus.setHorizontalGroup(
			gl_Aboutus.createParallelGroup(Alignment.LEADING)
				.addGap(0, 790, Short.MAX_VALUE)
		);
		gl_Aboutus.setVerticalGroup(
			gl_Aboutus.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		Aboutus.setLayout(gl_Aboutus);
		
		App.add(Aboutus);
		
		// --------------------- START HOME PAGE PANEL ----------------------
		
		JLabel homeImage = new JLabel();
		homeImage.setBounds(1,0,790,445);
//		homeImage.setIcon(new ImageIcon("home.jpg"));
		homeImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("home.jpg")));
		Home.add(homeImage);
		
		// Login interface
		
		
		JLabel titlelogin = new JLabel("Log-in ...");
		titlelogin.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 30));
		titlelogin.setBounds(100, 185, 200, 30);
		titlelogin.setForeground(Color.decode("#336699"));
		homeImage.add(titlelogin);
		
		JLabel usernameLabel = new JLabel("User Name :");
		usernameLabel.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 30));
		usernameLabel.setBounds(150, 237, 150, 30);
		usernameLabel.setForeground(Color.decode("#336699"));
		homeImage.add(usernameLabel);
		
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 30));
		passwordLabel.setBounds(150, 288, 150, 30);
		passwordLabel.setForeground(Color.decode("#336699"));
		homeImage.add(passwordLabel);
		
		
		JTextField usernameTf = new JTextField();
		usernameTf.setBounds(300, 237, 170, 30);
		homeImage.add(usernameTf);
		
	
		JPasswordField passwordTF = new JPasswordField();
		passwordTF.setBounds(300, 288, 170, 30);
		homeImage.add(passwordTF);
		
		
		JLabel signupLabel = new JLabel("Sign up");
		signupLabel.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 28));
		signupLabel.setBounds(180,360,100,30);
		signupLabel.setForeground(Color.decode("#336699"));
		signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		homeImage.add(signupLabel);
		
		
		JButton Lgo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("arrow.png")));
		Lgo.setBounds(545,236,80,80);
		Lgo.setOpaque(false);
		Lgo.setContentAreaFilled(false);
		Lgo.setBorderPainted(false);
		homeImage.add(Lgo);
		
		
		// Sing up interface
		JLabel titlesignup = new JLabel("Sign-up ...");
		titlesignup.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 30));
		titlesignup.setBounds(100, 185, 200, 30);
		titlesignup.setForeground(Color.decode("#336699"));
		titlesignup.setVisible(false);
		homeImage.add(titlesignup);
		
		JLabel SusernameL = new JLabel("User Name :");
		SusernameL.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 30));
		SusernameL.setBounds(150, 237, 150, 30);
		SusernameL.setForeground(Color.decode("#336699"));
		homeImage.add(SusernameL);
		usernameLabel.setVisible(false);
		
		JLabel passwordLabel1 = new JLabel("Password :");
		passwordLabel1.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 30));
		passwordLabel1.setBounds(150, 270, 150, 30);
		passwordLabel1.setForeground(Color.decode("#336699"));
		homeImage.add(passwordLabel1);
		passwordLabel1.setVisible(false);
		
		JLabel adminCL = new JLabel("Admin code :");
		adminCL.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 30));
		adminCL.setBounds(150, 303, 150, 30);
		adminCL.setForeground(Color.decode("#336699"));
		homeImage.add(adminCL);
		adminCL.setVisible(false);
		
		JTextField SusernameTf = new JTextField();
		SusernameTf.setBounds(300, 237, 170, 30);
		homeImage.add(SusernameTf);
		SusernameTf.setVisible(false);
	
		JPasswordField SpasswordTF = new JPasswordField();
		SpasswordTF.setBounds(300, 270, 170, 30);
		homeImage.add(SpasswordTF);
		SpasswordTF.setVisible(false);
		
		JPasswordField adminCT = new JPasswordField();
		adminCT.setBounds(300, 303, 170, 30);
		homeImage.add(adminCT);
		adminCT.setVisible(false);
		
		JButton what = new JButton(new ImageIcon(getClass().getClassLoader().getResource("help.png")));
		what.setBounds(475,303,30,30);
		what.setOpaque(false);
		what.setContentAreaFilled(false);
		what.setBorderPainted(false);
		homeImage.add(what);
		what.setVisible(false);
		
		JButton Sgo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("arrow.png")));
		Sgo.setBounds(545,236,80,80);
		Sgo.setOpaque(false);
		Sgo.setContentAreaFilled(false);
		Sgo.setBorderPainted(false);
		homeImage.add(Sgo);
		Sgo.setVisible(false);
		
		
		JLabel loginLabel = new JLabel("Log-in");
		loginLabel.setFont(new Font("Brush Script MT Italic", Font.PLAIN, 28));
		loginLabel.setBounds(180,360,100,30);
		loginLabel.setForeground(Color.decode("#336699"));
		loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		homeImage.add(loginLabel);
		loginLabel.setVisible(false);

		
		
		// --------------------- HANDLING HOME PAGE PANEL part 1 -----------------
		
		signupLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				titlesignup.setVisible(true);
				SusernameL.setVisible(true);
				passwordLabel1.setVisible(true);
				adminCL.setVisible(true);
				SusernameTf.setVisible(true);
				SpasswordTF.setVisible(true);
				adminCT.setVisible(true);
				what.setVisible(true);
				Sgo.setVisible(true);
				loginLabel.setVisible(true);
				
				titlelogin.setVisible(false);
				usernameLabel.setVisible(false);
				usernameTf.setVisible(false);
				passwordLabel.setVisible(false);
				passwordTF.setVisible(false);
				signupLabel.setVisible(false);
				Lgo.setVisible(false);
			
			}
		});
		
		loginLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				titlesignup.setVisible(false);
				SusernameL.setVisible(false);
				passwordLabel1.setVisible(false);
				adminCL.setVisible(false);
				SusernameTf.setVisible(false);
				SpasswordTF.setVisible(false);
				adminCT.setVisible(false);
				what.setVisible(false);
				Sgo.setVisible(false);
				loginLabel.setVisible(false);
				
				titlelogin.setVisible(true);
				usernameLabel.setVisible(true);
				usernameTf.setVisible(true);
				passwordLabel.setVisible(true);
				passwordTF.setVisible(true);
				signupLabel.setVisible(true);
				Lgo.setVisible(true);
			
			}
		});
		
		what.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(App, "Take the Admin code from the administration");
				
			}
		});
		// --------------------- END HOME PAGE PANEL -------------------------
				
		// --------------------- START WELCOME PANEL -----------------------
		
		JLabel wtitle1 = new JLabel("Bienvenu sur l'interface ");
		JLabel wtitle2 = new JLabel("de");
		JLabel wtitle3 = new JLabel("Gestion administrative d'un coll\u00e8ge");
		wtitle1.setFont(new Font("Nimbus Roman No9 L", Font.BOLD, 30));
		wtitle2.setFont(new Font("Nimbus Roman No9 L", Font.BOLD, 30));
		wtitle3.setFont(new Font("Nimbus Roman No9 L", Font.BOLD, 30));
		wtitle1.setBounds(235,5,350,50);
		wtitle2.setBounds(370,50,50,50);
		wtitle3.setBounds(168,95,550,50);
		welcome.add(wtitle1);
		welcome.add(wtitle2);
		welcome.add(wtitle3);
		App.getContentPane().add(welcome);
		
		JLabel nouv = new JLabel("Nouvelle :");
		nouv.setForeground(Color.BLACK);
		nouv.setFont(new Font("Liberation Serif", Font.BOLD, 24));
		nouv.setBounds(290, 160, 150, 50);
		welcome.add(nouv);
		
		JLabel naff = new JLabel("Affectation ");
		naff.setForeground(Color.GRAY);
		naff.setFont(new Font("Liberation Serif", Font.BOLD, 22));
		naff.setBounds(335, 190, 150, 50);
		naff.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcome.add(naff);
		
		JLabel nfa = new JLabel("feuille d'absence ");
		nfa.setForeground(Color.GRAY);
		nfa.setFont(new Font("Liberation Serif", Font.BOLD, 22));
		nfa.setBounds(335, 220, 210, 50);
		nfa.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcome.add(nfa);
		
		JLabel ntr = new JLabel("Gestion d'eleve");
		ntr.setForeground(Color.GRAY);
		ntr.setFont(new Font("Liberation Serif", Font.BOLD, 22));
		ntr.setBounds(335, 250, 210, 50);
		ntr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcome.add(ntr);
		
		JLabel tuto = new JLabel("Tutorial :");
		tuto.setForeground(Color.BLACK);
		tuto.setFont(new Font("Liberation Serif", Font.BOLD, 24));
		tuto.setBounds(290, 300, 150, 50);
		welcome.add(tuto);
		
		JLabel tuto1 = new JLabel("Voire le tutorial");
		tuto1.setForeground(Color.GRAY);
		tuto1.setFont(new Font("Liberation Serif", Font.BOLD, 22));
		tuto1.setBounds(335, 330, 210, 50);
		tuto1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcome.add(tuto1);
		
		
		
		// ---------------------- HANDLING WELCOME PANEL -------------------------
		naff.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				welcome.setVisible(false);
				aff.setVisible(true);
			}
		});
		nfa.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				welcome.setVisible(false);
				abs.setVisible(true);
			}
		});
		ntr.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				welcome.setVisible(false);
				GE1.setVisible(true);
			}
		});

		tuto1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File("tuto.pdf"));
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(App, "Can't read the PDF file");
					}
			}
		});
		
		// --------------------- END WELCOME PANEL ----------------------
		
		// --------------------START ABOUTUS PANEL -----------------------
		
		JLabel imagelabel = new JLabel();
		imagelabel.setBounds(1,0,790,445);
		imagelabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("AU.jpg")));
		
		Aboutus.add(imagelabel);

		JButton D1 = new JButton();
		D1.setBounds(15, 230, 165, 45);
		D1.setOpaque(false);
		D1.setContentAreaFilled(false);
		D1.setBorderPainted(false);
		
		Aboutus.add(D1);

		
		JButton D2 = new JButton();
	
		D2.setBounds(235, 230, 100, 45);
		D2.setOpaque(false);
		D2.setContentAreaFilled(false);
		D2.setBorderPainted(false);
		Aboutus.add(D2);
		
		JButton D3 = new JButton();
		
		D3.setBounds(415, 230, 120, 45);
		D3.setOpaque(false);
		D3.setContentAreaFilled(false);
		D3.setBorderPainted(false);
		Aboutus.add(D3);
		
		JButton D4 = new JButton();
		
		D4.setBounds(615, 230, 120, 45);
		D4.setOpaque(false);
		D4.setContentAreaFilled(false);
		D4.setBorderPainted(false);
		Aboutus.add(D4);
		
		JLabel D1name = new JLabel("OULED BEN TAHAR ANASS");
		D1name.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D1name.setForeground(Color.white);
		D1name.setBounds(125, 310, 300, 30);
		D1name.setVisible(false);
		imagelabel.add(D1name);
		
		JLabel D1adresse = new JLabel("CASABLANCA, MAROC");
		D1adresse.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D1adresse.setForeground(Color.white);
		D1adresse.setBounds(125, 360, 300, 30);
		D1adresse.setVisible(false);
		imagelabel.add(D1adresse);
		
		JLabel D1phone = new JLabel("0611392835");
		D1phone.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D1phone.setForeground(Color.white);
		D1phone.setBounds(485, 305, 300, 30);
		D1phone.setVisible(false);
		imagelabel.add(D1phone);
		
		JLabel D1email = new JLabel("anass.taher@gmail.com");
		D1email.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D1email.setForeground(Color.white);
		D1email.setBounds(485, 360, 300, 30);
		D1email.setVisible(false);
		imagelabel.add(D1email);
		
		JLabel D2name = new JLabel("ROCHDI AZIZ");
		D2name.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D2name.setForeground(Color.white);
		D2name.setBounds(125, 310, 300, 30);
		D2name.setVisible(false);
		imagelabel.add(D2name);
		
		JLabel D2adresse = new JLabel("SETTAT, MAROC");
		D2adresse.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D2adresse.setForeground(Color.white);
		D2adresse.setBounds(125, 360, 300, 30);
		D2adresse.setVisible(false);
		imagelabel.add(D2adresse);
		
		JLabel D2phone = new JLabel("0613534451");
		D2phone.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D2phone.setForeground(Color.white);
		D2phone.setBounds(485, 305, 300, 30);
		D2phone.setVisible(false);
		imagelabel.add(D2phone);
		
		JLabel D2email = new JLabel("aziz20rochdi@gmail.com");
		D2email.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D2email.setForeground(Color.white);
		D2email.setBounds(485, 360, 300, 30);
		D2email.setVisible(false);
		imagelabel.add(D2email);
		
		JLabel D3name = new JLabel("CAMARA ABDOULAYE");
		D3name.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D3name.setForeground(Color.white);
		D3name.setBounds(125, 310, 300, 30);
		D3name.setVisible(false);
		imagelabel.add(D3name);
		
		JLabel D3adresse = new JLabel("SETTAT, MAROC");
		D3adresse.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D3adresse.setForeground(Color.white);
		D3adresse.setBounds(125, 360, 300, 30);
		D3adresse.setVisible(false);
		imagelabel.add(D3adresse);
		
		JLabel D3phone = new JLabel("0680093841");
		D3phone.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D3phone.setForeground(Color.white);
		D3phone.setBounds(485, 305, 300, 30);
		D3phone.setVisible(false);
		imagelabel.add(D3phone);
		
		JLabel D3email = new JLabel("abdoulayecamara20004@gmail.com");
		D3email.setFont(new Font("Liberation Serif", Font.PLAIN, 18));
		D3email.setForeground(Color.white);
		D3email.setBounds(485, 360, 300, 30);
		D3email.setVisible(false);
		imagelabel.add(D3email);
		
		JLabel D4name = new JLabel("ELALJA NOOREDDIBE");
		D4name.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D4name.setForeground(Color.white);
		D4name.setBounds(125, 310, 300, 30);
		D4name.setVisible(false);
		imagelabel.add(D4name);
		
		JLabel D4adresse = new JLabel("BERCHIDE, MAROC");
		D4adresse.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D4adresse.setForeground(Color.white);
		D4adresse.setBounds(125, 360, 300, 30);
		D4adresse.setVisible(false);
		imagelabel.add(D4adresse);
		
		JLabel D4phone = new JLabel("0668035100");
		D4phone.setFont(new Font("Liberation Serif", Font.BOLD, 20));
		D4phone.setForeground(Color.white);
		D4phone.setBounds(485, 305, 300, 30);
		D4phone.setVisible(false);
		imagelabel.add(D4phone);
		
		JLabel D4email = new JLabel("noureddineelalja98@gmail.com");
		D4email.setFont(new Font("Liberation Serif", Font.PLAIN, 18));
		D4email.setForeground(Color.white);
		D4email.setBounds(485, 360, 300, 30);
		D4email.setVisible(false);
		imagelabel.add(D4email);
		
		JButton backb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back.png")));
		backb.setBounds(733, 390, 50, 50);
		backb.setBackground(Color.white);
		backb.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		backb.setOpaque(false);
		backb.setContentAreaFilled(false);
		backb.setBorderPainted(false);
		imagelabel.add(backb);
		//-----------------HANDLING ABOUTUS PANEL ------------------------
		D1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				D1name.setVisible(true);
				D1adresse.setVisible(true);
				D1phone.setVisible(true);
				D1email.setVisible(true);
				
				D2name.setVisible(false);
				D2adresse.setVisible(false);
				D2phone.setVisible(false);
				D2email.setVisible(false);
				
				D3name.setVisible(false);
				D3adresse.setVisible(false);
				D3phone.setVisible(false);
				D3email.setVisible(false);
				
				D4name.setVisible(false);
				D4adresse.setVisible(false);
				D4phone.setVisible(false);
				D4email.setVisible(false);
			}
		});
		
		D2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				D1name.setVisible(false);
				D1adresse.setVisible(false);
				D1phone.setVisible(false);
				D1email.setVisible(false);
				
				D2name.setVisible(true);
				D2adresse.setVisible(true);
				D2phone.setVisible(true);
				D2email.setVisible(true);
				
				D3name.setVisible(false);
				D3adresse.setVisible(false);
				D3phone.setVisible(false);
				D3email.setVisible(false);
				
				D4name.setVisible(false);
				D4adresse.setVisible(false);
				D4phone.setVisible(false);
				D4email.setVisible(false);
			}
		});
		
		D3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				D1name.setVisible(false);
				D1adresse.setVisible(false);
				D1phone.setVisible(false);
				D1email.setVisible(false);
				
				D2name.setVisible(false);
				D2adresse.setVisible(false);
				D2phone.setVisible(false);
				D2email.setVisible(false);
				
				D3name.setVisible(true);
				D3adresse.setVisible(true);
				D3phone.setVisible(true);
				D3email.setVisible(true);
				
				D4name.setVisible(false);
				D4adresse.setVisible(false);
				D4phone.setVisible(false);
				D4email.setVisible(false);
			}
		});
		D4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				D1name.setVisible(false);
				D1adresse.setVisible(false);
				D1phone.setVisible(false);
				D1email.setVisible(false);
				
				D2name.setVisible(false);
				D2adresse.setVisible(false);
				D2phone.setVisible(false);
				D2email.setVisible(false);
				
				D3name.setVisible(false);
				D3adresse.setVisible(false);
				D3phone.setVisible(false);
				D3email.setVisible(false);
				
				D4name.setVisible(true);
				D4adresse.setVisible(true);
				D4phone.setVisible(true);
				D4email.setVisible(true);
			}
		});
		
		backb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					abs.setVisible(false);
					aff.setVisible(false);
					GE1.setVisible(false);
					Aboutus.setVisible(false);
					welcome.setVisible(true);
			}
		});
		// ------------------- SATRT AFFECTATION PANEL ------------------
		
		JLabel titleLabel = new JLabel("Affectation");
		titleLabel.setFont(new Font("Liberation Serif", Font.BOLD, 24));
		titleLabel.setBounds(325, 10, 200, 30);
		aff.add(titleLabel);
		
		JLabel classeLabel = new JLabel("Classe :");
		classeLabel.setBounds(5, 60, 80, 30);
		classeLabel.setFont(new Font("Liberation Serif", Font.BOLD, 18));
		aff.add(classeLabel);
		
		JComboBox<String> classeCB = new JComboBox<String>();
		classeCB.addItem("");
		classeCB.setBounds(80, 60, 150, 30);
		classeCB.setBackground(Color.white);
		String classeQuery = "SELECT nomClasse FROM classe";
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(classeQuery);
			while(rs.next()) {
				classeCB.addItem(rs.getString(1));
			}
		} catch (Exception e) {}
		
		aff.add(classeCB);
		
		JLabel coursLabel = new JLabel("Cours :");
		coursLabel.setBounds(240, 60, 80, 30);
		coursLabel.setFont(new Font("Liberation Serif", Font.BOLD, 18));
		aff.add(coursLabel);
		
		JComboBox<String> coursCB = new JComboBox<String>();
		coursCB.addItem("");
		coursCB.setBounds(311, 60, 150, 30);
		coursCB.setBackground(Color.white);
		String coursQuery = "SELECT nomCours FROM cours";
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(coursQuery);
			while(rs.next())
				coursCB.addItem(rs.getString(1));
		} catch (Exception e) {}
		aff.add(coursCB);
		
		JLabel materielLabel = new JLabel("Materiels :");
		materielLabel.setBounds(480, 60, 100, 30);
		materielLabel.setFont(new Font("Liberation Serif", Font.BOLD, 18));
		aff.add(materielLabel);
		
		JComboBox<String> materielCB = new JComboBox<String>();
		materielCB.addItem("");
		String materielQury = "SELECT nomMateriel FROM materiel";
		materielCB.setBounds(578, 60, 200, 30);
		materielCB.setBackground(Color.white);
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(materielQury);
			while(rs.next())
				materielCB.addItem(rs.getString(1));
		} catch (Exception e) {}
		
		aff.add(materielCB);
		
		JLabel dateLabel = new JLabel("La date et la dur\u00e9e :");
		dateLabel.setBounds(5, 116, 174, 30);
		dateLabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		aff.add(dateLabel);
		
		JTextField dateTF = new JTextField("Ex: 2020-01-01");
		dateTF.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		dateTF.setBounds(171, 116, 170, 30);
		aff.add(dateTF);
		
		JLabel timeSartLabel = new JLabel("de :");
		timeSartLabel.setBounds(348, 116, 33, 30);
		timeSartLabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));  
		aff.add(timeSartLabel);
		
		JTextField timeSartTF = new JTextField("Ex: 08:00:00");
		timeSartTF.setFont(new Font("Liberation Serif", Font.PLAIN,17));
		timeSartTF.setBounds(392, 116, 170, 30);
		aff.add(timeSartTF);
		
		JLabel timeEndLabel = new JLabel("\u00e0 :");
		timeEndLabel.setBounds(570, 116, 33, 30);
		timeEndLabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		aff.add(timeEndLabel);
		
		JTextField timeEndTF = new JTextField("Ex: 18:00:00");
		timeEndTF.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		timeEndTF.setBounds(602, 116, 170, 30);
		aff.add(timeEndTF);
		
		JButton apply = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		apply.setBounds(640, 158, 50, 30);
		apply.setBackground(Color.white);
		apply.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		aff.add(apply);
		
		JLabel verifier = new JLabel("--- Verification des informations ---");
		verifier.setFont(new Font("Liberation Serif", Font.PLAIN, 22));
		verifier.setBounds(226, 178, 400, 30);
		aff.add(verifier);
		
		JLabel classeLabelVr = new JLabel("Classe selectionn\u00e9e : ");
		classeLabelVr.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		classeLabelVr.setBounds(10, 209, 191, 30);
		aff.add(classeLabelVr);
		
		JTextField classeTFVr = new JTextField();
		classeTFVr.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		classeTFVr.setEditable(false);
		classeTFVr.setBounds(208, 210, 323, 30);
		aff.add(classeTFVr);
		
		JLabel coursLabelVr = new JLabel("Cours selectionn\u00e9e : ");
		coursLabelVr.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		coursLabelVr.setBounds(10, 250, 191, 30);
		aff.add(coursLabelVr);

		JTextField coursTFVr = new JTextField();
		coursTFVr.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		coursTFVr.setEditable(false);
		coursTFVr.setBounds(208, 250, 323, 30);
		aff.add(coursTFVr);
		
		JLabel materielLabelVr = new JLabel("Materiels selectionn\u00e9es : ");
		materielLabelVr.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		materielLabelVr.setBounds(10, 290, 208, 30);
		aff.add(materielLabelVr);

		JTextField materielTFVr = new JTextField();
		materielTFVr.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		materielTFVr.setEditable(false);
		materielTFVr.setBounds(208, 290, 323, 30);
		aff.add(materielTFVr);
		
		JLabel dateLabelVr = new JLabel("date selectionn\u00e9e : ");
		dateLabelVr.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		dateLabelVr.setBounds(10, 330, 191, 30);
		aff.add(dateLabelVr);

		JTextField dateTFVr = new JTextField();
		dateTFVr.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		dateTFVr.setEditable(false);
		dateTFVr.setBounds(208, 330, 323, 30);
		aff.add(dateTFVr);
		
		JTextField date = new JTextField();
		JTextField starttime = new JTextField();
		JTextField endtime = new JTextField();

		JButton start = new JButton(new ImageIcon(getClass().getClassLoader().getResource("go.png")));
		start.setBounds(720,380,50,50);
		start.setBackground(Color.white);
		start.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		start.setEnabled(false);
		
		aff.add(start);
		
		JButton cancel = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back.png")));
		cancel.setBounds(10, 380, 50, 50);
		cancel.setBackground(Color.white);
		cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		aff.add(cancel);
		
		App.getContentPane().add(aff);
		
		// ----------------- HANDLING Affectaion PANEL ---------------------
		
		apply.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int done = 0;
				if(!classeCB.getSelectedItem().toString().equals("")) {
					classeTFVr.setText(classeCB.getSelectedItem().toString());
					done +=1;
					System.out.println("classe done");
				}else {
					JOptionPane.showMessageDialog(App, "la classe selectionne est vide");
				}
				if(!coursCB.getSelectedItem().toString().equals("")) {
					coursTFVr.setText(coursCB.getSelectedItem().toString());
					System.out.println("cours done");
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "le cours selectionne est vide");
				}
				if(!materielCB.getSelectedItem().toString().equals("")) {
					materielTFVr.setText(materielCB.getSelectedItem().toString());
					System.out.println("mat done");
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "les materiels selectionnes est vide");
				}
				dateTFVr.setText("le " + dateTF.getText().toString() + " de " + timeSartTF.getText().toString() + " \u00e0 " + timeEndTF.getText().toString());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String testdate = dateTF.getText().toString();
		    	 LocalDateTime now = LocalDateTime.now();
		    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    	 String D = dateTF.getText().toString();
				try {
				    Date Date = df.parse(testdate);
				    if (!df.format(Date).equals(testdate)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    	System.out.println("Erreur sur Input Date");
				    }
				    else {
				    	 if(dtf.format(now).compareTo(D) <= 0 && "2021-07-30".compareTo(D)>= 0) {
				    			date.setText(dateTF.getText().toString());
						    	System.out.println("date done");
						    	done +=1;
				    	 }else {
				    		 JOptionPane.showMessageDialog(App, "Verifier la date input");
				    	 }
				    
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    System.out.println("Erreur sur Input Date");
				}
				SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
				String testTimeStart = timeSartTF.getText().toString();
				try {
				    Date Date = tf.parse(testTimeStart);
				    if (!tf.format(Date).equals(testTimeStart)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Debut");
				    	System.out.println("Erreur sur Input Heur Debut");
				    }
				    else {
				    	starttime.setText(timeSartTF.getText().toString());
				    	System.out.println("Heur debut done");
				    	
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Debut");
				    System.out.println("Erreur sur Input Heur Debut");
				}
				
				String testTimeEnd = timeEndTF.getText().toString();
				try {
				    Date Date = tf.parse(testTimeEnd);
				    if (!tf.format(Date).equals(testTimeEnd)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Fin");
				    	System.out.println("Erreur sur Input Heur Fin");
				    }
				    else {
				    	endtime.setText(timeEndTF.getText().toString());
				    	System.out.println("Heur fin done");
		
				    }
				   
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Fin");
				    System.out.println("Erreur sur Input Heur Fin");
				}
				if(testTimeStart.compareTo(testTimeEnd) < 0) {
					done +=1;
					
				}else {
					JOptionPane.showMessageDialog(App, "Verifier les heurs input");
				}
			if (done == 5) {
				start.setEnabled(true);
				System.out.println("done = " +done);
			}else {
			done = 0;
			start.setEnabled(false);
			System.out.println("done = " + done);
			}
			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int idClasse = Affectation.getIdClasse(classeTFVr.getText().toString());
				int idCours = Affectation.getIdCours(coursTFVr.getText().toString());
				int idMateriel = Affectation.getIdMateriel(materielTFVr.getText().toString());
				String jour = date.getText().toString();
				String heurDebut = starttime.getText().toString();
				String heurFin = endtime.getText().toString();
				try{
					Affectation.affectSalle(idClasse, idCours, idMateriel, jour, heurDebut, heurFin);
				}catch (Exception e) {System.out.println("error in affectation methode");}
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					abs.setVisible(false);
					aff.setVisible(false);
					GE1.setVisible(false);
					Aboutus.setVisible(false);
					welcome.setVisible(true);
			}
		});
		
		// ------------------ END AFFECTATION PANEL -----------------------
		
		// -------------------START ABSENCE PANEL --------------------------
		
		// ---------------------- part 1 --------------------
		
		JRadioButton jrajouter = new JRadioButton("Ajouter un absent");
		jrajouter.setBounds(5,10,250,30);
		jrajouter.setBackground(Color.white);
		jrajouter.setSelected(true);
		abs.add(jrajouter);
		
		JLabel lblNewLabel = new JLabel("Classe :");
		lblNewLabel.setBounds(10, 40, 69, 23);
		lblNewLabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel);
		
		JLabel lblNewLabel_6 = new JLabel("Eleve :");
		lblNewLabel_6.setBounds(215, 40, 69, 20);
		lblNewLabel_6.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_6);
		
		JComboBox<String> classeCB1 = new JComboBox<>();
		classeCB1.setBounds(89, 40, 94, 25);
		classeCB1.setBackground(Color.white);
		abs.add(classeCB1);
		
		JComboBox<String> eleveCB1 = new JComboBox<>();
		eleveCB1.setBounds(278, 40, 250, 25);
		eleveCB1.setBackground(Color.white);
		abs.add(eleveCB1);
		
		JLabel lblNewLabel_2 = new JLabel("Excuse :");
		lblNewLabel_2.setBounds(560, 40, 100, 30);
		lblNewLabel_2.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_2);
		
		JComboBox<String> excuseCB = new JComboBox<String>();
		excuseCB.setBounds(650, 40, 100, 30);
		excuseCB.setFont(new Font("Liberation Serif", Font.PLAIN, 16));
		excuseCB.setModel(new DefaultComboBoxModel<String>(new String[] {"Oui", "Non"}));
		excuseCB.setBackground(Color.white);
		abs.add(excuseCB);
		
		JLabel lblNewLabel_1 = new JLabel("Date :");
		lblNewLabel_1.setBounds(10, 80, 50, 30);
		lblNewLabel_1.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_1);
		
		JTextField jourTF = new JTextField("Ex: 2020-01-01");
		jourTF.setFont(new Font("Liberation Serif", Font.PLAIN, 18));
		jourTF.setBounds(80, 80, 150, 30);;
		abs.add(jourTF);
		
		JLabel lblNewLabel_3 = new JLabel("Absent de :");
		lblNewLabel_3.setBounds(250, 80, 100, 30);
		lblNewLabel_3.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_3);
		
		JTextField heureDTF = new JTextField("Ex: 08:00:00");
		heureDTF.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		heureDTF.setBounds(360, 80, 150, 30);
		abs.add(heureDTF);
		
		JLabel lblNewLabel_4 = new JLabel("	\u00e0 :");
		lblNewLabel_4.setBounds(515, 80, 30, 30);
		lblNewLabel_4.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_4);
		
		JTextField heureFTF = new JTextField("Ex: 12:00:00");
		heureFTF.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		heureFTF.setBounds(555, 80, 150, 30);
		abs.add(heureFTF);
	
		JButton applyB1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		applyB1.setBounds(710, 80, 50, 30);
		applyB1.setBackground(Color.white);
		applyB1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		abs.add(applyB1);
		
		// -------------------------- part 2 ------------------
		
		JRadioButton jrafficher = new JRadioButton("Afficher les abscences d'un classe");
		jrafficher.setBackground(Color.white);
		jrafficher.setBounds(5, 135, 400, 30);
		abs.add(jrafficher);
		
		JLabel lblNewLabel_5 = new JLabel("Date :");
		lblNewLabel_5.setBounds(110, 165, 60, 30);
		lblNewLabel_5.setFont(new Font("Liberation Serif", Font.BOLD, 14));
		abs.add(lblNewLabel_5);
		
		JTextField jour2 = new JTextField("Ex: 2020-01-01");
		jour2.setBounds(180, 165, 146, 30);
		jour2.setFont(new Font("Liberation Serif", Font.PLAIN, 17));
		jour2.setEnabled(false);
		abs.add(jour2);
		
		JLabel lblNewLabel_7 = new JLabel("Classe :");
		lblNewLabel_7.setBounds(405, 165, 70, 30);
		lblNewLabel_7.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		abs.add(lblNewLabel_7);
		
		JComboBox<String> classeCB2 = new JComboBox<>();
		classeCB2.setBounds(500, 165, 130, 30);
		classeCB2.setBackground(Color.white);
		classeCB2.setModel(new DefaultComboBoxModel<String>(lesClasses()));
		classeCB2.setEnabled(false);
		abs.add(classeCB2);
		

		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30, 210, 730, 175);
		
		abs.add(scrollPane);

		table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
		JButton applyB2 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		applyB2.setBounds(650, 165, 50, 30);
		applyB2.setBackground(Color.white);
		applyB2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		applyB2.setEnabled(false);
		abs.add(applyB2);
		
		JButton validerB = new JButton(new ImageIcon(getClass().getClassLoader().getResource("go.png")));
		validerB.setBounds(725, 390, 50, 50);
		validerB.setBackground(Color.white);
		validerB.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		validerB.setEnabled(false);
		abs.add(validerB);
		
		JButton cancelB = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back.png")));
		cancelB.setBounds(10, 390, 50, 50);
		cancelB.setBackground(Color.white);
		cancelB.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		abs.add(cancelB);
		
		App.getContentPane().add(abs);
		
	// -------------------- HANDLING ABSENCE PANEL -----------------------
		
		classeCB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				selectedClasse=classeCB1.getSelectedItem().toString();
				eleveCB1.removeAllItems();
				for(int i =0;i<infosEleves().size();i++) {
					eleveCB1.addItem((infosEleves().elementAt(i).toString()));
				}
			}
		});
		classeCB1.setModel(new DefaultComboBoxModel<String>(lesClasses()));
		eleveCB1.setModel(new DefaultComboBoxModel<String>(infosEleves()));
		
		ButtonGroup groupabs = new ButtonGroup();
		groupabs.add(jrajouter);
		groupabs.add(jrafficher);
	
		jrajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jrajouter.isSelected()) {
					classeCB1.setEnabled(true);
					eleveCB1.setEnabled(true);
					excuseCB.setEnabled(true);
					jourTF.setEnabled(true);
					heureDTF.setEnabled(true);
					heureFTF.setEnabled(true);
					applyB1.setEnabled(true);
					
					jour2.setEnabled(false);
					classeCB2.setEnabled(false);
					table.setEnabled(false);
					applyB2.setEnabled(false);
					validerB.setEnabled(false);
				}
				
			}
		});
		jrafficher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jrafficher.isSelected()) {
					classeCB1.setEnabled(false);
					eleveCB1.setEnabled(false);
					excuseCB.setEnabled(false);
					jourTF.setEnabled(false);
					heureDTF.setEnabled(false);
					heureFTF.setEnabled(false);
					applyB1.setEnabled(false);
					
					jour2.setEnabled(true);
					classeCB2.setEnabled(true);
					table.setEnabled(true);
					applyB2.setEnabled(true);
					validerB.setEnabled(false);
				}
				
			}
		});
		
		// -------- adding verification text fields --------
		JTextField eleveCBS = new JTextField();
		JTextField jourTFS = new JTextField();
		JTextField heureDTFS = new JTextField();
		JTextField heureFTFS = new JTextField();
		JTextField jour2S = new JTextField();
		
		//--------------------------------------------------
		applyB1.addActionListener(new ActionListener() {
			int done = 0;
			
			public void actionPerformed(ActionEvent arg0) {
				eleveCBS.setText(eleveCB1.getSelectedItem().toString());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String jourS = jourTF.getText().toString();
				try {
				    Date Date = df.parse(jourS);
				    if (!df.format(Date).equals(jourS)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    	System.out.println("Erreur sur Input Date");
				    }
				    else {
				    	jourTFS.setText(jourS);
				    	done +=1;
				    	System.out.println("date done");
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    System.out.println("Erreur sur Input Date");
				}
				SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
				String heureDS = heureDTF.getText().toString();
				try {
				    Date Date = tf.parse(heureDS);
				    if (!tf.format(Date).equals(heureDS)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Debut");
				    	System.out.println("Erreur sur Input Heur Debut");
				    }
				    else {
				    	heureDTFS.setText(heureDS);
				    	done +=1;
				    	System.out.println("Heur debut done");
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Debut");
				    System.out.println("Erreur sur Input Heur Debut");
				}
				String heureFS = heureFTF.getText().toString();
				try {
				    Date Date = tf.parse(heureFS);
				    if (!tf.format(Date).equals(heureFS)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Fin");
				    	System.out.println("Erreur sur Input Heur Fin");
				    }
				    else {
				    	heureFTFS.setText(heureFS);
				    	done +=1;
				    	System.out.println("Heur fin done");
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Heur Fin");
				    System.out.println("Erreur sur Input Heur Fin");
				}
			if(done == 3) {
				validerB.setEnabled(true);
				done = 0;
			}else {
				done = 0;
				validerB.setEnabled(false);
			}
			}
		});
		applyB2.addActionListener(new ActionListener() {
			
			int done = 0;
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String jourS2 = jour2.getText().toString();
				try {
				    Date Date = df.parse(jourS2);
				    if (!df.format(Date).equals(jourS2)){
				    	JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    	System.out.println("Erreur sur Input Date");
				    }
				    else {
				    	jour2S.setText(jourS2);
				    	done +=1;
				    	System.out.println("date done");
				    }
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(App, "Erreur sur Input Date");
				    System.out.println("Erreur sur Input Date");
				}
				if(done == 1) {
					done = 0;
					validerB.setEnabled(true);
				}else {
					done = 0;
					validerB.setEnabled(false);
				}
			}
		});
	
		validerB.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				if(jrajouter.isSelected()) {
					eleveCBS.setText(eleveCB1.getSelectedItem().toString());
					String s = eleveCBS.getText().toString();
					String r ="";
					for(char c:s.toCharArray()){
						if(c==' ') {
							break;
						}
						r += c;
					}
					int idEleve =Integer.parseInt(r);
					boolean excuse;
					if(excuseCB.getSelectedItem().equals("Oui") )
						excuse = true;
					else 
						excuse = false;
					String jour = jourTFS.getText().toString();
					String heurD = heureDTFS.getText().toString();
					String heurF = heureFTFS.getText().toString();
					
					

					//get les absences deja existes pour ne pas repeter la meme absence deux fois 
					
					boolean existe;
					existe=false;
					boolean hasCours=true;
					
					String timeFormat = "HH:mm:ss";
					SimpleDateFormat tf = new SimpleDateFormat(timeFormat);
					
					try {
						Connection conn = Utilitaire.getConnection();
						PreparedStatement ps = conn.prepareStatement( "SELECT * FROM absence WHERE idEleve = ? AND jour = ?");
						ps.setInt(1, idEleve);
						ps.setString(2, jour);
						ResultSet rs = ps.executeQuery();
						while(rs.next()) {
					
						if ((tf.parse(rs.getString(3)).before(tf.parse(heurD)) || tf.parse(rs.getString(3)).equals(tf.parse(heurD))) && (tf.parse(rs.getString(4)).after(tf.parse(heurF))|| tf.parse(rs.getString(3)).equals(tf.parse(heurD)))) {
								existe=true;
								break;
							}
						else if((tf.parse(rs.getString(3)).after(tf.parse(heurD)) || tf.parse(rs.getString(3)).equals(tf.parse(heurD))) && (tf.parse(rs.getString(3)).before(tf.parse(heurF)))){
							existe=true;
							break;
						}
						else if((tf.parse(rs.getString(4)).after(tf.parse(heurD))) && (tf.parse(rs.getString(3)).before(tf.parse(heurF))|| tf.parse(rs.getString(4)).equals(tf.parse(heurF)))){
							existe=true;
							break;
						}
						}
						PreparedStatement ps1 = conn.prepareStatement( "SELECT DISTINCT idClasse FROM affectation WHERE jour = ? AND heurDebut<=? AND heurFin>=? AND idClasse =(SELECT idClasse FROM eleve WHERE idEleve=?)");
						ps1.setString(1, jour);
						ps1.setString(2, heurD);
						ps1.setString(3, heurF);
						ps1.setInt(4, idEleve);
						ResultSet rs1 = ps1.executeQuery();
						if(!rs1.absolute(1))
							hasCours=false;
					   }
						catch(Exception exe) {	
						}
					
					if(!existe && hasCours)
					GestionAbsence.AjouterAbsence(idEleve, excuse, jour, heurD, heurF);
					else if(!hasCours)
					{
						JOptionPane.showMessageDialog(App, "cette classe n'pas de cours");
					}
					else if(existe)
					{
						JOptionPane.showMessageDialog(App, "cette absenece deja existe");
					}
					
					try {
						Connection conn = Utilitaire.getConnection();
						PreparedStatement ps = conn.prepareStatement( "SELECT * FROM absence WHERE idEleve = ? AND jour = ?");
						ps.setInt(1, idEleve);
						ps.setString(2, jour);
						ResultSet rs = ps.executeQuery();
						while(rs.next()) {
					
						if ((tf.parse(rs.getString(3)).before(tf.parse(heurD)) || tf.parse(rs.getString(3)).equals(tf.parse(heurD))) && (tf.parse(rs.getString(4)).after(tf.parse(heurF))|| tf.parse(rs.getString(3)).equals(tf.parse(heurD)))) {
								existe=true;
								break;
							}
						else if((tf.parse(rs.getString(3)).after(tf.parse(heurD)) || tf.parse(rs.getString(3)).equals(tf.parse(heurD))) && (tf.parse(rs.getString(3)).before(tf.parse(heurF)))){
							existe=true;
							break;
						}
						else if((tf.parse(rs.getString(4)).after(tf.parse(heurD))) && (tf.parse(rs.getString(3)).before(tf.parse(heurF))|| tf.parse(rs.getString(4)).equals(tf.parse(heurF)))){
							existe=true;
							break;
						}
						}
						PreparedStatement ps1 = conn.prepareStatement( "SELECT DISTINCT idClasse FROM affectation WHERE jour = ? AND heurDebut<=? AND heurFin>=? AND idClasse =(SELECT idClasse FROM eleve WHERE idEleve=?)");
						ps1.setString(1, jour);
						ps1.setString(2, heurD);
						ps1.setString(3, heurF);
						ps1.setInt(4, idEleve);
						ResultSet rs1 = ps1.executeQuery();
						if(!rs1.absolute(1))
							hasCours=false;
					   }
						catch(Exception exe) {	
						}
					
				
				}
				if(jrafficher.isSelected()) {
					String nomClasse =classeCB2.getSelectedItem().toString();
					String jour = (String)jour2.getText();
					GestionAbsence.lesAbsences(jour,nomClasse);
					}
				
			}
		});
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					abs.setVisible(false);
					aff.setVisible(false);
					GE1.setVisible(false);
					Aboutus.setVisible(false);
					welcome.setVisible(true);
			}
		});
				
		// ----------------------------- END ABSENCE PANEL --------------------------------------------
		
		// --------------------------START GESTION D'ELEVE PANEL --------------------------------------
		
		// ---------------- part 1 --------------------
		
		JRadioButton addEleveRB = new JRadioButton("Ajouter un Eleve", true);
		addEleveRB.setBounds(5,10,250,30);
		addEleveRB.setBackground(Color.white);
		GE1.add(addEleveRB);
		
		JLabel dataEleve = new JLabel("les donnes de l'eleve:");
		dataEleve.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		dataEleve.setBounds(5,45,250,30);
		GE1.add(dataEleve);
		
		JLabel nomELabel = new JLabel("nom :");
		nomELabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		nomELabel.setBounds(20,80,100,30);
		GE1.add(nomELabel);
		
		JTextField nomETF = new JTextField();
		nomETF.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		nomETF.setBounds(75,80,150,30);
		GE1.add(nomETF);
		
		JLabel prenomELabel = new JLabel("prenom :");
		prenomELabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		prenomELabel.setBounds(240,80,100,30);
		GE1.add(prenomELabel);
		
		JTextField prenomETF = new JTextField();
		prenomETF.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		prenomETF.setBounds(320,80,150,30);
		GE1.add(prenomETF);
		
		JLabel adressELabel = new JLabel("adresse :");
		adressELabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		adressELabel.setBounds(480,80,100,30);
		GE1.add(adressELabel);
		
		JTextField adrssETF = new JTextField();
		adrssETF.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		adrssETF.setBounds(560,80,220,30);
		GE1.add(adrssETF);

		JLabel numELabel = new JLabel("numero de telephone des parents :");
		numELabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		numELabel.setBounds(20,115,300,30);
		GE1.add(numELabel);
		
		JTextField nummETF = new JTextField();
		nummETF.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		nummETF.setBounds(320,115,150,30);
		GE1.add(nummETF);
		
		JLabel classe1Label = new JLabel("Classe :");
		classe1Label.setBounds(480,115,100,30);
		classe1Label.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		GE1.add(classe1Label);
	
		JComboBox<String> classe1CB = new JComboBox<String>();
		classe1CB.addItem("");
		classe1CB.setBounds(560, 115, 150, 30);
		classe1CB.setBackground(Color.white);
		try {
			classeQuery = "SELECT nomClasse FROM classe";
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(classeQuery);
			while(rs.next()) {
				classe1CB.addItem(rs.getString(1));
			}
		} catch (Exception e) {}
		
		GE1.add(classe1CB);
		
		JButton apply0Classe = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		apply0Classe.setBounds(715, 115, 50, 30);
		apply0Classe.setBackground(Color.white);
		apply0Classe.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		GE1.add(apply0Classe);
		

		
		//-----------------------------PART 2 ---------------------------------
		
		JRadioButton tranEleveRB = new JRadioButton("Transfert d'un eleve");
		tranEleveRB.setBounds(5,150,250,30);
		tranEleveRB.setBackground(Color.white);
		GE1.add(tranEleveRB);
		
		JLabel classe2Label = new JLabel("Classe :");
		classe2Label.setBounds(20,180,100,30);
		classe2Label.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		GE1.add(classe2Label);
		
		JComboBox<String> classe2CB = new JComboBox<String>();
		classe2CB.addItem("");
		classe2CB.setBounds(100, 180, 200, 30);
		classe2CB.setBackground(Color.white);
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(classeQuery);
			while(rs.next()) {
				classe2CB.addItem(rs.getString(1));
			}
		} catch (Exception e) {}
		
		GE1.add(classe2CB);

		JTextField a = new JTextField();
		JTextField b = new JTextField();
		JTextField d = new JTextField();
		JTextField idEleveTF = new JTextField();
		
		JButton applyClasse = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		applyClasse.setBounds(310, 180, 50, 30);
		applyClasse.setBackground(Color.white);
		applyClasse.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		GE1.add(applyClasse);

		
		JLabel eleveLabel = new JLabel("Eleve :");
		eleveLabel.setBounds(20,220,100,30);
		eleveLabel.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		GE1.add(eleveLabel);
		
		JComboBox<String> eleveCB = new JComboBox<String>();
		
		eleveCB.setBounds(100, 220, 200, 30);
		eleveCB.setBackground(Color.white);
		GE1.add(eleveCB);
		
		JButton apply1Classe = new JButton(new ImageIcon(getClass().getClassLoader().getResource("correct.png")));
		apply1Classe.setBounds(310, 220, 50, 30);
		apply1Classe.setBackground(Color.white);
		apply1Classe.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		GE1.add(apply1Classe);
		
		JLabel tranVer = new JLabel("Transfer vers :");
		tranVer.setBounds(20,260,200,30);
		tranVer.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		GE1.add(tranVer);
		
		JLabel classe3Label = new JLabel("Classe :");
		classe3Label.setBounds(20,300,100,30);
		classe3Label.setFont(new Font("Liberation Serif", Font.BOLD, 17));
		GE1.add(classe3Label);
		
		JComboBox<String> classe3CB = new JComboBox<String>();
		classe3CB.addItem("");
		classe3CB.setBounds(100, 300, 200, 30);
		classe3CB.setBackground(Color.white);
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(classeQuery);
			while(rs.next()) {
				classe3CB.addItem(rs.getString(1));
			}
		} catch (Exception e) {}
		
		GE1.add(classe3CB);
		

		
		JButton startGE = new JButton(new ImageIcon(getClass().getClassLoader().getResource("go.png")));
		startGE.setBounds(720,390,50,50);
		startGE.setBackground(Color.white);
		startGE.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		startGE.setEnabled(false);
		GE1.add(startGE);
		
		JButton cancelGE = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back.png")));
		cancelGE.setBounds(10, 390, 50, 50);
		cancelGE.setBackground(Color.white);
		cancelGE.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		GE1.add(cancelGE);
		App.getContentPane().add(GE1);
		

		
		// ---------------- HANDLING GESTION D'ELEVE -------------------
		
		// ------------- Verification Text Fields -------------------
		JTextField c = new JTextField();
		JTextField nomVR = new JTextField();
		JTextField prenomVR = new JTextField();
		JTextField adressVR = new JTextField();
		JTextField teleVR = new JTextField();
		
		// ------------------------------------------
		//----------------------- HANDLING PART 1 -------------------------
		apply0Classe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int done = 0;
				if(!classe1CB.getSelectedItem().toString().equals("")) {
					c.setText(classe1CB.getSelectedItem().toString());
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "Erreur la classe selectionne est vide");
				}
				
				if(!nomETF.getText().toString().equals("")) {
					nomVR.setText(nomETF.getText().toString());
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "Erreur Input nom est vide");
				}
				
				if(!prenomETF.getText().toString().equals("")) {
					prenomVR.setText(prenomETF.getText().toString());
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "Erreur Input prenom est vide");
				}
				if(!adrssETF.getText().toString().equals("")) {
					adressVR.setText(adrssETF.getText().toString());
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "Erreur Input adresse est vide");
				}
				if(!nummETF.getText().toString().equals("")) {
					teleVR.setText(nummETF.getText().toString());
					done +=1;
				}else {
					JOptionPane.showMessageDialog(App, "Erreur Input telephone est vide");
				}
				if(done == 5) {
					startGE.setEnabled(true);
					done = 0;
				}else {
					startGE.setEnabled(false);
					done = 0;
				}

			}
		});
		
		// --------------------------HANDLING PART 2 --------------------------
		

		applyClasse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				a.setText(classe2CB.getSelectedItem().toString());
				eleveCB.removeAllItems();
				
				try {
					eleveCB.addItem("");
					String eleveQuery = "SELECT idEleve, nomEleve, prenomEleve FROM eleve WHERE idClasse = "  +Affectation.getIdClasse(a.getText().toString());
					Statement stm = (Statement) conn.createStatement();
					ResultSet rs = stm.executeQuery(eleveQuery);
					while(rs.next()) {
						eleveCB.addItem(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
					}
				} catch (Exception e) {}
			}
		});
		
		apply1Classe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				b.setText(eleveCB.getSelectedItem().toString());
				String x = b.getText().toString();
				String r = "";
				for (char c : x.toCharArray()) {
					if(c == ' ') {
						break;
					}
					r += c;
				}
				idEleveTF.setText(r.toString());
				System.out.println(r);
			}
		});
	
		classe2CB.setEnabled(false);
		applyClasse.setEnabled(false);
		tranEleveRB.setSelected(false);
		eleveCB.setEnabled(false);
		apply1Classe.setEnabled(false);
		classe3CB.setEnabled(false);
	
		ButtonGroup bg = new ButtonGroup();
		bg.add(addEleveRB);
		bg.add(tranEleveRB);
		

		
		addEleveRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(addEleveRB.isSelected()) {
					
					nomETF.setEnabled(true);
					prenomETF.setEnabled(true);
					adrssETF.setEnabled(true);
					nummETF.setEnabled(true);
					classe1CB.setEnabled(true);
					apply0Classe.setEnabled(true);
					startGE.setEnabled(false);
					
					classe2CB.setEnabled(false);
					applyClasse.setEnabled(false);
					tranEleveRB.setSelected(false);
					eleveCB.setEnabled(false);
					apply1Classe.setEnabled(false);
					classe3CB.setEnabled(false);
					
				}
				
			}
		});
		tranEleveRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tranEleveRB.isSelected()) {
					addEleveRB.setSelected(false);
					nomETF.setEnabled(false);
					prenomETF.setEnabled(false);
					adrssETF.setEnabled(false);
					nummETF.setEnabled(false);
					classe1CB.setEnabled(false);
					apply0Classe.setEnabled(false);
					
					classe2CB.setEnabled(true);
					applyClasse.setEnabled(true);
					tranEleveRB.setSelected(true);
					eleveCB.setEnabled(true);
					apply1Classe.setEnabled(true);
					classe3CB.setEnabled(true);
					
					startGE.setEnabled(true);
				}
				
			}
		});
		
		
		startGE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try{
					if(addEleveRB.isSelected()) {
						System.out.println("start Button clicked in addeleve");
						String nomEleve = nomVR.getText().toString();
						String prenomEleve = prenomVR.getText().toString();
						String adresseEleve = adressVR.getText().toString();
						String teleEleve = teleVR.getText().toString();
						int idClasse = Affectation.getIdClasse(c.getText().toString());
					GestioEleve.ajouterEleve(nomEleve, prenomEleve, adresseEleve, teleEleve, idClasse);
					}
					else if(tranEleveRB.isSelected()) {
						System.out.println("start Button clicked in transfer eleve");
						int newIdClasse = Affectation.getIdClasse(classe3CB.getSelectedItem().toString());
						System.out.println("id eleve : "+idEleveTF.getText().toString());
						int idEleve =Integer.parseInt(idEleveTF.getText().toString());
						System.out.println(idEleve);
						GestioEleve.transferEleve(idEleve, newIdClasse);
					}
				}catch (Exception e) {}
				
				
			}
		});
		cancelGE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					abs.setVisible(false);
					aff.setVisible(false);
					GE1.setVisible(false);
					Aboutus.setVisible(false);
					welcome.setVisible(true);
			}
		});
	
	// ------------------------ HANDLING MENUBAR ----------------------------
		
	
		ajouterEle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aff.setVisible(false);
				welcome.setVisible(false);
				abs.setVisible(false);
				GE1.setVisible(true);
				Aboutus.setVisible(false);
				
				addEleveRB.setSelected(true);
				nomETF.setEnabled(true);
				prenomETF.setEnabled(true);
				adrssETF.setEnabled(true);
				nummETF.setEnabled(true);
				classe1CB.setEnabled(true);
				apply0Classe.setEnabled(true);
				
				classe2CB.setEnabled(false);
				applyClasse.setEnabled(false);
				tranEleveRB.setSelected(false);
				eleveCB.setEnabled(false);
				apply1Classe.setEnabled(false);
				classe3CB.setEnabled(false);
				
				startGE.setEnabled(false);
				
				
			}
		});
		transferEle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aff.setVisible(false);
				welcome.setVisible(false);
				abs.setVisible(false);
				GE1.setVisible(true);
				tranEleveRB.setSelected(true);
				Aboutus.setVisible(false);
				
				addEleveRB.setSelected(false);
				nomETF.setEnabled(false);
				prenomETF.setEnabled(false);
				adrssETF.setEnabled(false);
				nummETF.setEnabled(false);
				classe1CB.setEnabled(false);
				apply0Classe.setEnabled(false);
				
				classe2CB.setEnabled(true);
				applyClasse.setEnabled(true);
				tranEleveRB.setSelected(true);
				eleveCB.setEnabled(true);
				apply1Classe.setEnabled(true);
				classe3CB.setEnabled(true);
			
				startGE.setEnabled(true);
			}
		});
		Affect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aff.setVisible(true);
				welcome.setVisible(false);
				abs.setVisible(false);
				GE1.setVisible(false);
				Aboutus.setVisible(false);
			}
		});
		ajouterAbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aff.setVisible(false);
				welcome.setVisible(false);
				abs.setVisible(true);
				GE1.setVisible(false);
				jrajouter.setSelected(true);
				Aboutus.setVisible(false);
				
				classeCB1.setEnabled(true);
				eleveCB1.setEnabled(true);
				excuseCB.setEnabled(true);
				jourTF.setEnabled(true);
				heureDTF.setEnabled(true);
				heureFTF.setEnabled(true);
				applyB1.setEnabled(true);
				
				jour2.setEnabled(false);
				classeCB2.setEnabled(false);
				table.setEnabled(false);
				applyB2.setEnabled(false);
				validerB.setEnabled(false);
				
			}
		});
		voirAbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aff.setVisible(false);
				welcome.setVisible(false);
				abs.setVisible(true);
				GE1.setVisible(false);
				jrafficher.setSelected(true);
				Aboutus.setVisible(false);
				
				classeCB1.setEnabled(false);
				eleveCB1.setEnabled(false);
				excuseCB.setEnabled(false);
				jourTF.setEnabled(false);
				heureDTF.setEnabled(false);
				heureFTF.setEnabled(false);
				applyB1.setEnabled(false);
				
				jour2.setEnabled(true);
				classeCB2.setEnabled(true);
				table.setEnabled(true);
				applyB2.setEnabled(true);
				validerB.setEnabled(false);
			}
		});
		
		about.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Aboutus.setVisible(true);
				abs.setVisible(false);
				aff.setVisible(false);
				GE1.setVisible(false);
				welcome.setVisible(false);
				
				
				
			}
		});
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				Desktop.getDesktop().open(new File("tuto.pdf"));
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(App, "Can't read the PDF file");
				}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 if (JOptionPane.showConfirmDialog( App,"Fermer l'application ?","Gestion d'un colege",
				            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				            System.exit(0);
			}
		});
		
		// --------------------- HANDLING LOGIN PAGE part 2 -----------------------------------
		
		Lgo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameTf.getText().toString();
				String password = String.valueOf(passwordTF.getPassword());
				
				if(Login.login(username, password)) {
					Home.setVisible(false);
					welcome.setVisible(true);
					File.setEnabled(true);
					AboutUs.setEnabled(true);
					Help.setEnabled(true);
				
			}
			}
		});
		
		Sgo.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = SusernameTf.getText().toString();
				String password = String.valueOf(SpasswordTF.getPassword());
				String adminCode = String.valueOf(adminCT.getPassword());
				if(Login.signUp(username, password, adminCode)) {
					SusernameL.setVisible(false);
					passwordLabel1.setVisible(false);
					adminCL.setVisible(false);
					SusernameTf.setVisible(false);
					SpasswordTF.setVisible(false);
					adminCT.setVisible(false);
					what.setVisible(false);
					Sgo.setVisible(false);
					loginLabel.setVisible(false);
					
					usernameLabel.setVisible(true);
					usernameTf.setVisible(true);
					passwordLabel.setVisible(true);
					passwordTF.setVisible(true);
					signupLabel.setVisible(true);
					Lgo.setVisible(true);
				}
				
			}
		});
		
		
	usernameTf.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent arg0) {
		
			
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}
		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String username = usernameTf.getText().toString();
				String password = String.valueOf(passwordTF.getPassword());
				
				if(Login.login(username, password)) {
					Home.setVisible(false);
					welcome.setVisible(true);
					File.setEnabled(true);
					AboutUs.setEnabled(true);
					Help.setEnabled(true);
			}
			}
		
		}
	});
	passwordTF.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent arg0) {
		
			
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}
		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String username = usernameTf.getText().toString();
				String password = String.valueOf(passwordTF.getPassword());
				
				if(Login.login(username, password)) {
					Home.setVisible(false);
					welcome.setVisible(true);
					File.setEnabled(true);
					AboutUs.setEnabled(true);
					Help.setEnabled(true);
			}
			}
		
		}
	});
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	
}
