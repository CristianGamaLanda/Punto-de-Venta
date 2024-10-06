
package Pruebas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Codigojswing extends JFrame implements ActionListener{
    
    private JTextField TUsuario, TContra;
    private JButton Inicio;
    
    public Codigojswing(){
        
        this.setTitle("Inicio de Sesión");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setSize(120, 180);
        this.setType(Type.UTILITY);
        
       
        TUsuario= new JTextField();
        TContra= new JTextField();
        
        TUsuario.setPreferredSize(new Dimension(120,20));
        TContra.setPreferredSize(new Dimension(120,20));
        
        JLabel LUsuario = new JLabel("Usuario");
        JLabel LContra = new JLabel("Contraseña");
        
        //LUsuario.setText("Usuario");
        LUsuario.setVerticalTextPosition(JLabel.TOP);
        //LContra.setText("Contraseña");
        //LContra.setVerticalTextPosition(JLabel.CENTER);
        
        Inicio = new JButton("Iniciar Sesión");
        Inicio.addActionListener(this);
        
        this.add(LUsuario);
        this.add(TUsuario);
        this.add(LContra);
        this.add(TContra);
        this.add(Inicio);
        //this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         //To change body of generated methods, choose Tools | Templates.
         if(ae.getSource()==Inicio){
             System.out.println("Hola "+ TUsuario.getText());
         }
    }
    
}
