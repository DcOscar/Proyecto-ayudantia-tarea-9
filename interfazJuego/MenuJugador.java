/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazJuego;

import ventana.*;
import javax.swing.*;
import java.awt.event.*;
import infinitygame.*;


public class MenuJugador extends Ventana implements ItemListener {

    private int contador, turno;
    private final InfinityGame game;
    private JComboBox opcionJugador;
    private JButton botonTablero, botonAceptar;
    private JLabel textoEncabezado;

    public MenuJugador(InfinityGame game, int contador, int turno) {
        super("Infinity Game", 500, 400);
        this.contador = contador;
        this.game = game;
        this.turno = turno;
        generarElementosVentana();

    }

    private void generarElementosVentana() {
        generarJComboBox();
        generarBotonTablero();
        generarBotonAceptar();
        generarTextoEncabezado();

    }

    private void generarTextoEncabezado() {
        String textoEncabezado = "Turno n°" + this.turno + "  jugador " + game.getNombreJugador(this.contador);
        super.generarJLabelEncabezado(this.textoEncabezado, textoEncabezado, 70, 10, 300, 20);
    }

    private void generarJComboBox() {
        this.opcionJugador = new JComboBox();
        this.opcionJugador.addItem("Hacer jugada");
        this.opcionJugador.addItem("Lanzar dados");
        this.opcionJugador.addItem("Meditar");
        this.opcionJugador.addItem("Habilidad especial");
        this.opcionJugador.setSelectedIndex(0);
        this.opcionJugador.setBounds(20, 100, 200, 20);
        this.add(this.opcionJugador);
        // this.opcionJugador.addItemListener(this);
    }

    private void generarBotonTablero() {
        String textoBoton = "Ver tablero";
        this.botonTablero = super.generarBoton(textoBoton, 300, 100, 100, 20);
        this.add(this.botonTablero);
        this.botonTablero.addActionListener(this);
    }

    private void generarBotonAceptar() {
        String textoBoton = "Aceptar";
        this.botonAceptar = super.generarBoton(textoBoton, 300, 300, 100, 20);
        this.add(this.botonAceptar);
        this.botonAceptar.addActionListener(this);

    }

    private void verTablero() {
        JOptionPane.showMessageDialog(this, "El tablero es: " + this.game.getCasillas());
    }

    private void accionBotonAceptar() {
        int cantidadJugadores = game.getCantidadJugadores();
        cantidadJugadores--;
        if (this.contador == cantidadJugadores) {
            this.contador = 0;
            this.turno++;
            MenuJugador menu = new MenuJugador(this.game, this.contador, this.turno);
            this.dispose();

        } else {
            contador++;
            MenuJugador menu = new MenuJugador(this.game, this.contador, this.turno);
            this.dispose();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.botonTablero) {
            verTablero();

        }
        if (e.getSource() == this.botonAceptar) {
            accionBotonAceptar();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.opcionJugador) {

            int indice = this.opcionJugador.getSelectedIndex();
            switch (indice) {
                case 0:

                    this.dispose();
                    break;
                case 1:

                    this.dispose();
                    break;
                case 2:

                    this.dispose();
                    break;

            }
        }
    }
}
