/*
 * MainWindow.java
 *
 * Created on July 26, 2007, 8:54 PM
 */

package dbox2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author  Truben
 */
public class MainWindow extends javax.swing.JFrame {
    
    public static BoxListe bl = new BoxListe();
    public static NewPreferences pref = new NewPreferences();
    BufferedImage[] images;


    //images
    Icon fileEnabled;
    Icon fileDisabled;
    Icon runEnabled;
    Icon runDisabled;
    Icon prefEnabled;
    Icon prefDisabled;
    Icon searchArrow;
    Icon searchArrowDisabled;


    
    /** Creates new form MainWindow */
    public MainWindow() {

        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/ikon.gif")).getImage());

        try {
            initComponents();
            
        }
        catch(java.lang.ClassCastException e) {
            pack();
        }

        // Genre filter menu
        /*if(true) { // A little neat hack to reduce scope
            JMenuItem m = new JMenuItem();
            m.setText("Show All");
            m.addActionListener(new Filter(this));
            searchMenu.add(m);
            searchMenu.addSeparator();
        }*/
        for(String s : pref.getGenres()) {
            JMenuItem m = new JMenuItem();
            m.setText(s);
            m.addActionListener(new Filter(this));
            searchMenu.add(m);
        }


        // Images in the list!

        // Set up images
        runEnabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/media-playback-start.png"));
        runDisabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/media-playback-start-disabled.png"));
        fileEnabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/utilities-terminal.png"));
        fileDisabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/utilities-terminal-disabled.png"));
        prefEnabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/emblem-system.png"));
        prefDisabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/emblem-system-disabled.png"));
        searchArrow = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/down-arrow.png"));
        searchArrowDisabled = new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/down-arrow-disabled.png"));




        bl = deSerialize("database.dat");
        //Preferences oldpref = deSerializePref("preferences.dat");
        pref.readConfig(Main.configFile);
        try {
            pref.writeConfig(Main.configFile);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        centerScreen();
        updateList();
        new  FileDrop( gameList, new FileDrop.Listener()
      {   public void  filesDropped( java.io.File[] files )
          {
              createNewMagicProfile(files[0].getAbsoluteFile());
          }


      });

    }

    /**
     * A method that tries to fix everything :)
     *
     * @param absoluteFile the main executable
     */
    private void createNewMagicProfile(File file) {
        if(file.isDirectory()) {
            JOptionPane.showMessageDialog(this, "You must drag the main executable, not the directory!", "You're almost there...", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            DosItem d = new DosItem();
            d.setGame(file.getName());
            d.setPath(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separatorChar)));

            File[] files = file.getParentFile().listFiles();
            for (File f : files) {
                String s = f.getName().toLowerCase();
                if(s.endsWith("ico")) {
                    d.setIcon(f.getAbsolutePath());
                }
                else if(s.endsWith("exe") || s.endsWith("bat") || s.endsWith("com")) {
                    if(s.indexOf("setup") != -1 || s.indexOf("install") != -1) {
                        d.setInstaller(f.getAbsolutePath());
                    }

                }
            }
            String[] choice = new String[3];

            choice[0] = file.getParentFile().getAbsolutePath().substring(file.getParentFile().getAbsolutePath().lastIndexOf(File.separator)+1);
            choice[1] = file.getName().substring(0,1).toUpperCase() + file.getName().substring(1, file.getName().lastIndexOf('.')).toLowerCase();
            choice[2] = "Something else...";

            String input = (String) JOptionPane.showInputDialog(
                    null, "What is the title of the application? Select one of the proposals,\n" +
                    "or select \"Something else...\" to type your own.",
                    "What's the name of the game?",
                    JOptionPane.QUESTION_MESSAGE,
                    null,choice,choice[0]);

            if(input.equals(choice[2]))
                d.setName(JOptionPane.showInputDialog(this, "Type the name of the application", choice[1], JOptionPane.QUESTION_MESSAGE));
            else
                d.setName(input);

            bl.addGame(d);
            updateList();
            }catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Something wrong happened. You have to add the application the hard way.", "Sorry...", JOptionPane.INFORMATION_MESSAGE);
            }
    }
    
    private BoxListe deSerialize(String name) {
        BoxListe s = new BoxListe();
        try {
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fis);
            try {
                s = (BoxListe)ois.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            ois.close();
        } catch (IOException ex) {
            return s;
        }
        
        return s;
    }
    
    private Preferences deSerializePref(String name) {
        Preferences s = new Preferences();
        try {
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fis);
            try {
                s = (Preferences)ois.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            ois.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Hey! Welcome to D-Box. Please check your preferences before you start! Just click on the cogwheel in the main window.", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
            return s;
        }
        
        return s;
    }
    
    
    public void skrivObjekt(String navn, Object o) {
		try {
			ObjectOutputStream output =
				new ObjectOutputStream(new FileOutputStream( navn ) );
			
			output.writeObject(o);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exportGameList();
        try {
            pref.writeConfig(Main.configFile);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    
    /**
     * Putter boksen midt paa skjermen
     */
    public void centerScreen() {
    	  Dimension dim = getToolkit().getScreenSize();
    	  Rectangle abounds = getBounds();
    	  setLocation((dim.width - abounds.width) / 2,
    	      (dim.height - abounds.height) / 2);
    }
    
    private void writeConfig(String filename, int cpucycles, boolean fullscreen, String dir, String extra, int skip){
        String ut;
        // CPU
        ut = "[CPU]\n" +
             "cycles="+cpucycles+"\n\n";
        
        ut += "[RENDER]\n" +
                "frameskip=" + skip + "\n\n";
        
        // Screen
        if(fullscreen)
            ut += "[SDL]\n" +
                  "fullscreen=true\n\n";

        // Keyboard layout
        ut += "[DOS]\n";
        ut += "keyboardlayout=" + pref.getKeyboardCode() + "\n";
        
        // Mounting
        ut += "[AUTOEXEC]\n" +
                "mount c \"" + dir + "\"\n";
        if(extra != null || extra.equals(""))
            ut += "mount d \"" + extra + "\" -t cdrom\n";


        ut+="\n";
        
        try {
            java.io.FileWriter fw = new java.io.FileWriter(filename);
            java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
            bw.write(ut);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
        
    }

    private void createIconList() {
        String[] strings = bl.getGameList();
        images = new BufferedImage[bl.getNrGames()];
        for(int i = 0; i < images.length;i++) {

            if(bl.getGame(strings[i]).getIcon().equals(""))
                try {
                    images[i] = ImageIO.read(getClass().getResource("/dbox2/img/application-x-executable.png"));
                } catch (IOException ex) {
                    System.out.println("Føkk!!!");
            }
            else
                try {
                    images[i] = ImageIO.read(getClass().getResource(bl.getGame(strings[i]).getIcon()));
                } catch (IOException ex) {
                try {
                    images[i] = ImageIO.read(getClass().getResource("/dbox2/img/application-x-executable.png"));
                } catch (IOException ex1) {
                    System.out.println("Icon file not found");
                }
            }
        }
    }
    
    private void updateList()  {
        
        
        gameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = bl.getGameList();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { if(strings[i].equals("")) return "(untitled)"; else return strings[i]; }
        });
        skrivObjekt("database.dat", bl);
    }
    
    private void updateList(String search) {
        final String s = search;
        gameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = bl.getGameList(s);
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
    }

    public void updateListGenre(String search) {
        final String s = search;
        gameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = bl.getGameListGenre(s);
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        runMenu = new javax.swing.JPopupMenu();
        mnuRun2 = new javax.swing.JMenuItem();
        mnuSetup2 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JPopupMenu();
        mnuNew2 = new javax.swing.JMenuItem();
        mnuEdit2 = new javax.swing.JMenuItem();
        mnuDelete2 = new javax.swing.JMenuItem();
        prefMenu = new javax.swing.JPopupMenu();
        mnuPreferences = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        mnuAbout = new javax.swing.JMenuItem();
        mnuWeb = new javax.swing.JMenu();
        mnuHome = new javax.swing.JMenuItem();
        mnuDosbox = new javax.swing.JMenuItem();
        searchMenu = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameList = new javax.swing.JList();
        gameList.setCellRenderer(new GameListRenderer());
        jLabel3 = new javax.swing.JLabel();
        panelControls = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblExplain = new javax.swing.JLabel();

        mnuRun2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        mnuRun2.setFont(mnuRun2.getFont().deriveFont(mnuRun2.getFont().getStyle() | java.awt.Font.BOLD));
        mnuRun2.setText("Run");
        mnuRun2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRunActionPerformed(evt);
            }
        });
        runMenu.add(mnuRun2);

        mnuSetup2.setText("Setup");
        mnuSetup2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSetupActionPerformed(evt);
            }
        });
        runMenu.add(mnuSetup2);

        mnuNew2.setText("New Game");
        mnuNew2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewActionPerformed(evt);
            }
        });
        editMenu.add(mnuNew2);

        mnuEdit2.setText("Edit Game");
        mnuEdit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditActionPerformed(evt);
            }
        });
        editMenu.add(mnuEdit2);

        mnuDelete2.setText("Delete Game");
        mnuDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeleteActionPerformed(evt);
            }
        });
        editMenu.add(mnuDelete2);

        mnuPreferences.setFont(mnuPreferences.getFont().deriveFont(mnuPreferences.getFont().getStyle() | java.awt.Font.BOLD));
        mnuPreferences.setText("Preferences");
        mnuPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPreferencesActionPerformed(evt);
            }
        });
        prefMenu.add(mnuPreferences);
        prefMenu.add(jSeparator1);

        mnuAbout.setText("D-Box version " + Main.MAJORVERSION + "." + Main.MINORVERSION + "");
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        prefMenu.add(mnuAbout);

        mnuWeb.setText("Web links");

        mnuHome.setText("D-Box' homepage");
        mnuHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHomeActionPerformed(evt);
            }
        });
        mnuWeb.add(mnuHome);

        mnuDosbox.setText("DOSBox' Homepage");
        mnuDosbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDosboxActionPerformed(evt);
            }
        });
        mnuWeb.add(mnuDosbox);

        prefMenu.add(mnuWeb);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("D-Box");

        gameList.setComponentPopupMenu(runMenu);
        gameList.setFocusCycleRoot(true);
        gameList.setNextFocusableComponent(txtSearch);
        gameList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gameListKeyPressed(evt);
            }
        });
        gameList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                gameListMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Double(evt);
            }
        });
        jScrollPane1.setViewportView(gameList);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/title.jpg"))); // NOI18N
        jLabel3.setToolTipText("");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mnuWeb, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel3, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        txtSearch.setToolTipText("Search the gamelist");
        txtSearch.setNextFocusableComponent(gameList);
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtSearchMouseExited(evt);
            }
        });
        txtSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchCaretUpdate(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lblSearch.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledText"));
        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/down-arrow-disabled.png"))); // NOI18N
        lblSearch.setLabelFor(txtSearch);
        lblSearch.setText("Search:");
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSearchMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSearchMousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/utilities-terminal-disabled.png"))); // NOI18N
        jLabel2.setToolTipText("Game information");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, editMenu, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel2, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/media-playback-start-disabled.png"))); // NOI18N
        jLabel4.setToolTipText("Run Application (right-click for more options)");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbox2/img/emblem-system-disabled.png"))); // NOI18N
        jLabel5.setToolTipText("Preferences (right-click for more options)");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, prefMenu, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel5, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });

        lblExplain.setFont(lblExplain.getFont().deriveFont(lblExplain.getFont().getSize()-2f));
        lblExplain.setForeground(java.awt.SystemColor.controlShadow);
        lblExplain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        org.jdesktop.layout.GroupLayout panelControlsLayout = new org.jdesktop.layout.GroupLayout(panelControls);
        panelControls.setLayout(panelControlsLayout);
        panelControlsLayout.setHorizontalGroup(
            panelControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelControlsLayout.createSequentialGroup()
                .add(jLabel4)
                .add(18, 18, 18)
                .add(jLabel2)
                .add(18, 18, 18)
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblExplain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblSearch)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        panelControlsLayout.setVerticalGroup(
            panelControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(lblSearch)
                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel2)
                .add(jLabel5)
                .add(lblExplain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                .addContainerGap())
            .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 638, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(panelControls, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(panelControls, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked

}//GEN-LAST:event_lblSearchMouseClicked

private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
    jLabel3MouseClicked(null);
}//GEN-LAST:event_mnuAboutActionPerformed

private void Double(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Double
    if(evt.getClickCount() == 2 && !evt.isAltDown())
        mnuRunActionPerformed(null);
}//GEN-LAST:event_Double

private void mnuRunDosBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRunDosBoxActionPerformed
    String[] par = new String[3];
        par[0] = pref.getDosBoxPath();
        par[1] = "-c";
        par[2] = "@echo Have fun! Best wishes from D-Box :)";
        
        try {
            Runtime.getRuntime().exec(par);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_mnuRunDosBoxActionPerformed

private void mnuPrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPrefsActionPerformed
    PreferencesGUI prf = new PreferencesGUI();
    prf.setModal(true);
    prf.setVisible(true);
    prf = null;
        try {
            pref.writeConfig(Main.configFile);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    gameList.repaint();
}//GEN-LAST:event_mnuPrefsActionPerformed

private void mnuSetupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSetupActionPerformed
    if(gameList.getSelectedIndex() == -1)
        return;
    if(pref.getDosBoxPath().equals("")) {
        String[] choices = {
            "Let me show you where DOSBox is!",
            "Please take me to DOSBox' homepage so I can download!",
            "Get me out of here!"
        };
        String input = (String) JOptionPane.showInputDialog(
                null, "D-Box needs DOSBox to work, but currently the path to DOSBox is set to nothing.\nIf you have DOSBox installed, please locate it for me. If not, please download and\ninstall DOSBox before continuing.\n\nPlease select your next step:",
                "Can't find DOSBox!",
                JOptionPane.QUESTION_MESSAGE,
                null,choices,choices[0]);
        if(input.equals(choices[0]))
            mnuPrefsActionPerformed(null);
        else if(input.equals(choices[1])) {
            BrowserControl.openUrl("http://www.dosbox.com/download.php?main=1");
            return;
        }
        else
            return;

    }


    DosItem di = bl.getGame((String)gameList.getSelectedValue());

    if(di.getInstaller().equals("")) {
        JOptionPane.showMessageDialog(null, "You haven't configured the setup program for " + di.getName() + ".\nIf " + di.getName() + " has a setup program, add using Edit Game>Advanced>Setup.");
        return;
    }

        writeConfig(
                    getCurrentDir() + File.separator + "dosbox.conf",
                    di.getCycles(),
                    pref.isFullScreen(),
                    di.getPath(),
                    di.getCdrom(),
                    di.getFrameskip()
                   );
        String[] par = new String[11];
        par[0] = pref.getDosBoxPath();
        par[1] = "-c";
        par[2] = "@echo Go go go!";
        par[3] = "-c";
        par[4] = "c:";
        par[5] = "-c";
        par[6] = di.getInstaller();

        if(!pref.isKeepOpen()){
            par[7] = "-c";
            par[8] = "exit";
        }
        else {
            par[7] = "-c";
            par[8] = "@echo Keep on rockin'!";
        }

        par[9] = "-conf";
        par[10] = getCurrentDir() + File.separator + "dosbox.conf";

        try {
            Runtime.getRuntime().exec(par);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_mnuSetupActionPerformed

private String getCurrentDir() {
    File dir1 = new File (".");
     try {
       return dir1.getCanonicalPath();
     }
     catch(Exception e) {
       e.printStackTrace();
       }

    return null;
}

private void mnuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRunActionPerformed
    if(gameList.getSelectedIndex() == -1)
        return;
    if(pref.getDosBoxPath().equals("")) {
        String[] choices = {
            "Let me show you where DOSBox is!",
            "Please take me to DOSBox' homepage so I can download!",
            "Get me out of here!"
        };
        String input = (String) JOptionPane.showInputDialog(
                null, "D-Box needs DOSBox to work, but currently the path to DOSBox is set to nothing.\nIf you have DOSBox installed, please locate it for me. If not, please download and\ninstall DOSBox before continuing.\n\nPlease select your next step:",
                "Can't find DOSBox!",
                JOptionPane.QUESTION_MESSAGE,
                null,choices,choices[0]);
        if(input.equals(choices[0]))
            mnuPrefsActionPerformed(null);
        else if(input.equals(choices[1])) {
            BrowserControl.openUrl("http://www.dosbox.com/download.php?main=1");
            return;
        }
        else
            return;

    }

    if(bl.getNrGames() == 0) {
        int answ = JOptionPane.showConfirmDialog(this,
                   "To make D-Box useful, you got to add a game or two. Do you want to add one now?",
                   null, JOptionPane.YES_NO_OPTION);
        if(answ == JOptionPane.YES_OPTION) {
            mnuNewActionPerformed(null);
        }
        return;

    }
    DosItem di = bl.getGame((String)gameList.getSelectedValue());
        writeConfig(
                    getCurrentDir() + File.separator + "dosbox.conf",
                    di.getCycles(),
                    pref.isFullScreen(),
                    di.getPath(),
                    di.getCdrom(),
                    di.getFrameskip()
                   );
        String[] par = new String[11];
        par[0] = pref.getDosBoxPath();
        par[1] = "-c";
        par[2] = "@echo Go go go!";
        par[3] = "-c";
        par[4] = "c:";
        par[5] = "-c";
        par[6] = di.getGame();
      
        if(!pref.isKeepOpen()){
            par[7] = "-c";
            par[8] = "exit";
        }
        else {
            par[7] = "-c";
            par[8] = "@echo Keep on rockin'!";
        }
        
        par[9] = "-conf";
        par[10] = getCurrentDir() + File.separator + "dosbox.conf";
        
        try {
            Runtime.getRuntime().exec(par);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_mnuRunActionPerformed

private void mnuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditActionPerformed
    String gm = "";
    if(gameList.getSelectedIndex() == -1)
        return;
    
    if(!((String)gameList.getSelectedValue()).equals("(untitled)"))
        gm = (String)gameList.getSelectedValue();

    ItemGUI ui = new ItemGUI(bl.removeGame(gm), this);
    ui.setVisible(true);
    ui = null;
    updateList();
}//GEN-LAST:event_mnuEditActionPerformed

private void mnuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewActionPerformed
    ItemGUI ui = new ItemGUI(this);
    ui.setVisible(true);
    ui = null;
    updateList();
}//GEN-LAST:event_mnuNewActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
    updateList(txtSearch.getText());
}//GEN-LAST:event_txtSearchKeyReleased

private void mnuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeleteActionPerformed
    if(gameList.getSelectedIndex() == -1)
        return;
    int a = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove " + gameList.getSelectedValue() + " from the list?", "Please Confirm", JOptionPane.YES_NO_OPTION);
    if(a == JOptionPane.NO_OPTION)
        return;
    String gm = "";
    if(!((String)gameList.getSelectedValue()).equals("(untitled)"))
        gm = (String)gameList.getSelectedValue();

    bl.removeGame(gm);
    updateList();
}//GEN-LAST:event_mnuDeleteActionPerformed

private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
    System.exit(0);
}//GEN-LAST:event_mnuExitActionPerformed

private void gameListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gameListKeyPressed
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        mnuRunActionPerformed(null);
    if(evt.getKeyCode() == KeyEvent.VK_DELETE || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        mnuDeleteActionPerformed(null);
}//GEN-LAST:event_gameListKeyPressed

private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
editMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());

}//GEN-LAST:event_jLabel2MousePressed

private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased

}//GEN-LAST:event_jLabel2MouseReleased

private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

}//GEN-LAST:event_jLabel2MouseClicked

private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed

    if(evt.getButton() == MouseEvent.BUTTON3) {
    runMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());
    }
}//GEN-LAST:event_jLabel4MousePressed

private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
    // TODO add your handling code here:
}//GEN-LAST:event_jLabel4MouseReleased

private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
if(evt.getButton() == MouseEvent.BUTTON1)
    mnuRunActionPerformed(null);
}//GEN-LAST:event_jLabel4MouseClicked

private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
    // TODO add your handling code here:
}//GEN-LAST:event_jLabel5MousePressed

private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
    // TODO add your handling code here:
}//GEN-LAST:event_jLabel5MouseReleased

private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
    if(evt.getButton() == MouseEvent.BUTTON1)
        mnuPrefsActionPerformed(null);
    else
        prefMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());
}//GEN-LAST:event_jLabel5MouseClicked

private void gameListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameListMouseReleased
    if(evt.getButton() == 9){//MouseEvent.BUTTON3) {
    runMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());
    }
}//GEN-LAST:event_gameListMouseReleased

private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
    AboutWindow aw = new AboutWindow(this,true);
    aw.setVisible(true);
}//GEN-LAST:event_jLabel3MouseClicked

private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
    jLabel4.setIcon(runEnabled);
    lblExplain.setText("Run Game");
}//GEN-LAST:event_jLabel4MouseEntered

private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
    jLabel2.setIcon(fileEnabled);
    lblExplain.setText("Edit Game Preferences");
}//GEN-LAST:event_jLabel2MouseEntered

private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
    jLabel4.setIcon(runDisabled);
    lblExplain.setText("");
}//GEN-LAST:event_jLabel4MouseExited

private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
    jLabel2.setIcon(fileDisabled);
    lblExplain.setText("");
}//GEN-LAST:event_jLabel2MouseExited

private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
    jLabel5.setIcon(prefEnabled);
    lblExplain.setText("Edit D-Box Preferences");
}//GEN-LAST:event_jLabel5MouseEntered

private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
    jLabel5.setIcon(prefDisabled);
    lblExplain.setText("");
}//GEN-LAST:event_jLabel5MouseExited

private void mnuPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPreferencesActionPerformed
    mnuPrefsActionPerformed(null);
}//GEN-LAST:event_mnuPreferencesActionPerformed

private void mnuHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHomeActionPerformed
   BrowserControl.openUrl("http://code.google.com/p/dbox/");
}//GEN-LAST:event_mnuHomeActionPerformed

private void mnuDosboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDosboxActionPerformed
    BrowserControl.openUrl("http://dosbox.com");
}//GEN-LAST:event_mnuDosboxActionPerformed

private void txtSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseEntered
    lblSearch.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.foreground"));
    txtSearch.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.foreground"));
}//GEN-LAST:event_txtSearchMouseEntered

private void txtSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseExited
    lblSearch.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledText"));
    txtSearch.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledText"));
}//GEN-LAST:event_txtSearchMouseExited

private void txtSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchCaretUpdate

}//GEN-LAST:event_txtSearchCaretUpdate

private void lblSearchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMousePressed
    searchMenu.show(lblSearch, 0, lblSearch.getHeight());
    txtSearch.setText("");
    txtSearchKeyReleased(null);

}//GEN-LAST:event_lblSearchMousePressed

private void lblSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseEntered
    lblSearch.setIcon(searchArrow);
    lblExplain.setText("Click to filter by genre");
}//GEN-LAST:event_lblSearchMouseEntered

private void lblSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseExited
    lblSearch.setIcon(searchArrowDisabled);
    lblExplain.setText("");
}//GEN-LAST:event_lblSearchMouseExited
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu editMenu;
    private javax.swing.JList gameList;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblExplain;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuDelete2;
    private javax.swing.JMenuItem mnuDosbox;
    private javax.swing.JMenuItem mnuEdit2;
    private javax.swing.JMenuItem mnuHome;
    private javax.swing.JMenuItem mnuNew2;
    private javax.swing.JMenuItem mnuPreferences;
    private javax.swing.JMenuItem mnuRun2;
    private javax.swing.JMenuItem mnuSetup2;
    private javax.swing.JMenu mnuWeb;
    private javax.swing.JPanel panelControls;
    private javax.swing.JPopupMenu prefMenu;
    private javax.swing.JPopupMenu runMenu;
    private javax.swing.JPopupMenu searchMenu;
    private javax.swing.JTextField txtSearch;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables


    /**
     * Exports the game list so we can get ready for our next release!
     */
    private void exportGameList() {
        FileWriter fstream = null;
        try {
            String[] games = bl.getGameList();
            String out = "## D-Box game list. Not in use yet, but next version will use it! ##\n\n";
            for (String g : games) {
                DosItem d = bl.getGame(g);
                out += "start game" + "\n" + "  name := " + d.getName() + "\n" + "  path := " + d.getPath() + "\n" + "  game := " + d.getGame() + "\n" + "  installer := " + d.getInstaller() + "\n" + "  floppy := " + d.getFloppy() + "\n" + "  cdrom := " + d.getCdrom() + "\n" + "  extra :=" + d.getExtra() + "\n" + "  icon := " + d.getIcon() + "\n" + "end game\n\n";
            }
            fstream = new FileWriter(Main.gameFile);
            BufferedWriter writer = new BufferedWriter(fstream);
            writer.write(out);
            //Close the output stream
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

class Filter implements ActionListener {

    MainWindow mw;

    Filter(MainWindow mw) {
        this.mw = mw;
    }

    public void actionPerformed(ActionEvent e) {
        String s = ((JMenuItem)e.getSource()).getText();
        if(s.equals("Show All")) s = "";
        
        mw.updateListGenre(s);
    }

}
