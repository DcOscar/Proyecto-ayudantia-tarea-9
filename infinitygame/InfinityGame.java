/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infinitygame;

import java.util.*;

public class InfinityGame {

    private ArrayList<Jugador> jugadores;
    private ArrayList<Character> casillas;
    private int largoTablero, cantidadJugadores;
    private Guardian guardian;

    public InfinityGame() {

        this.jugadores = new ArrayList<Jugador>();
    }

    public int lanzarDados() {
        int valorDados = 0;
        for (int contador = 0; contador < 2; contador++) {
            Dado dado = new Dado();
            valorDados += dado.getValorDado();
        }

        return valorDados;
    }

    public void crearGuardian() {
        this.guardian = new Guardian(this.jugadores);

    }

    private void meditar(int indice, int largoTablero) {
        int mover;
        int cantidadMeditar;
        cantidadMeditar = this.jugadores.get(indice).getMeditar();
        if (cantidadMeditar == 0) {
            this.jugadores.get(indice).setSalud(-1);
            System.out.println("No le quedan meditaciones");
        } else {
            do {
                System.out.println("Puede moverse dentro de 3 casillas a la redonda, indique cuantas casillas se movera");
                mover = leerDato();
            } while (mover > 3 || mover < -3);
            this.jugadores.get(indice).setMeditar(mover);
            this.jugadores.get(indice).setSalud(1);
        }
        extremosPosiciones(indice, largoTablero);
        extremosSalud();
    }

    private void casillaFinal(int indice, int largoTablero, int turno) {
        int posicion;
        posicion = this.jugadores.get(indice).getPosicion();
        if (posicion == largoTablero) {
            System.out.println("Ganó el jugador n°" + indice + 1 + " en el turno " + turno);
        }
        System.exit(0);

    }

    public void agregarJugadorMago(String nombre) {
        this.jugadores.add(new Mago(nombre));
    }

    public void agregarJugadorGuerrero(String nombre) {
        this.jugadores.add(new Guerrero(nombre));

    }

    public String getNombreJugador(int indice) {
        return this.jugadores.get(indice).getNombre();

    }

    private void casillaDesafio(int indice, int largoTablero) {
        int posibilidad = (int) (Math.random() * 2); //"0" se puede avanzar o retroceder, "1" gana o pierde vida
        int azar = (int) (Math.random() * 2); // 0= perder vida o retroceder, 1 ganar vida o avanzar
        int obtenerSalud;
        int perderSalud;
        int retroceder;
        int avanzar;
        System.out.println("Entro en una casilla desafio");
        if (posibilidad == 0) {
            avanzar = (int) (Math.random() * 5 + 1);
            retroceder = -avanzar;
            if (azar == 0) {
                this.jugadores.get(indice).setPosicion(retroceder);

            } else if (azar == 1) {
                this.jugadores.get(indice).setPosicion(avanzar);
            }
        } else if (posibilidad == 1) {
            obtenerSalud = (int) (Math.random() * 4 + 1);
            perderSalud = -obtenerSalud;
            if (azar == 0) {
                for (int i = 0; i < this.jugadores.size(); i++) {
                    this.jugadores.get(i).setSalud(perderSalud);
                }
                this.jugadores.get(indice).setSalud(obtenerSalud);
            } else if (azar == 1) {
                for (int i = 0; i < this.jugadores.size(); i++) {
                    this.jugadores.get(i).setSalud(obtenerSalud);
                }
                this.jugadores.get(indice).setSalud(perderSalud);
            }

        }
        extremosSalud();
        extremosPosiciones(indice, largoTablero);
    }

    private void extremosSalud() {
        int saludMax;
        for (int i = 0; i < this.jugadores.size(); i++) {
            if (this.jugadores.get(i).getSalud() > this.jugadores.get(i).getSaludMax()) {
                saludMax = this.jugadores.get(i).getSaludMax();
                this.jugadores.get(i).setSaludExtremos(saludMax);
            }

            if (this.jugadores.get(i).getSalud() < 0) {

                this.jugadores.get(i).setSaludExtremos(0);
                System.out.println("El jugador n°" + i + 1 + "a perdido todas sus vidas, por lo tanto dejará de jugar");
                this.jugadores.remove(i);
            }
        }
    }

    private void extremosPosiciones(int indice, int largoTablero) {

        if (this.jugadores.get(indice).getPosicion() > largoTablero) {
            this.jugadores.get(indice).setPosicionExtremos(largoTablero);
        }

        if (this.jugadores.get(indice).getPosicion() < 0) {
            this.jugadores.get(indice).setPosicionExtremos(0);
        }
    }

    public void casillaPortal(ArrayList<Character> casillas, int largoTablero, int indice, int posicion) {
        ArrayList<Integer> posicionPortales = new ArrayList<Integer>();
        int portalAzar;
        int aux = -1;
        System.out.println("Entro en una casilla portal");
        for (int i = 0; i < largoTablero; i++) {
            if (casillas.get(i).equals('p')) {  //cada vez que encuentra una casilla portal, agrega su posicion a un array
                posicionPortales.add(i);
                aux++;
                if (i == posicion) {
                    posicionPortales.remove(aux);
                    aux--;
                }
            }   // se elimina la posicion actual, en la suposicion de que es una casilla portal 
        }
        portalAzar = (int) (Math.random() * posicionPortales.size());
        this.jugadores.get(indice).setPosicionExtremos(posicionPortales.get(portalAzar)); //se cambia de posicion a un portal aleatorio
        System.out.println("Se ha transportado a la casilla n°" + (this.jugadores.get(indice).getPosicion() + 1));

    }

    public ArrayList getCasillas() {
        return this.casillas;
    }

    public void casillaSalud(int indice) {
        int azar = (int) (Math.random() * 2);
        int obtenerSalud = (int) (Math.random() * 3 + 1);
        int perderSalud = -obtenerSalud;
        System.out.println("Entro en una casilla salud");
        if (azar == 0) {
            this.jugadores.get(indice).setSalud(obtenerSalud); //obtiene entre 1 y 3 de vida
        } else {
            this.jugadores.get(indice).setSalud(perderSalud);

        }
        extremosSalud();
    }

    private int leerDato() {
        int verificarDato;
        Scanner leer = new Scanner(System.in);
        do {
            try {
                System.out.println("Ingrese un número válido");
                verificarDato = leer.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("error, ingresa nuevamente un dato");
                leer.nextLine();
            }
        } while (true);

        return verificarDato;
    }

    private void elegirJugada(int indice, int largoTablero, Reliquia reliquia, Guardian guardian, int turno) {

        int opcion;
        int mover;
        System.out.println("1-Lanzar dados");
        System.out.println("2-Meditar");
        System.out.println("3- Habilidad especial");
        System.out.println("Elija una opción");
        guardian.furiaGuardian(jugadores);
        do {
            opcion = leerDato();
        } while (opcion < 0 && opcion > 4);
        if (opcion == 1) {
            mover = lanzarDados();
            if (mover == 12) {
                guardian.lanzarDadosGuardian(jugadores, indice, 3, turno);
            } else {
                guardian.lanzarDadosGuardian(jugadores, indice, 1, turno);
            }
            this.jugadores.get(indice).setPosicion(mover);
            extremosPosiciones(indice, largoTablero);
            reliquia.reliquiaDados(jugadores, indice, mover);
        } else if (opcion == 2) {
            meditar(indice, largoTablero);
        } else if (opcion == 3) {
            String clase = this.jugadores.get(indice).getTipo();
            if (clase.equals("Guerrero")) {
                Guerrero guerrero = (Guerrero) this.jugadores.get(indice);
                guerrero.enfurecerse(this.jugadores);
            } else {
                Mago mago = (Mago) this.jugadores.get(indice);
                mago.concentracion(guardian);
            }

        }
        this.jugadores.get(indice).estadoJugador();

    }

    private void casillaOpcion(ArrayList<Character> casillas, int indice, int largoTablero, int posicion, Reliquia reliquia, int turno) {

        if (posicion >= casillas.size()) {
            posicion = casillas.size();
        }

        switch (casillas.get(posicion)) {
            case 'p':
                casillaPortal(casillas, largoTablero, indice, posicion);
                break;
            case 's':
                casillaSalud(indice);
                reliquia.reliquiaSalud(this.jugadores, indice);
                break;
            case 'd':
                casillaDesafio(indice, largoTablero);
                break;
            case 'b':
                System.out.println("Entro en una casilla en blanco");
                break;
            case 'I':
                System.out.println("Está en la casilla inicial");
                break;
            case 'F':
                casillaFinal(indice, largoTablero, turno);
                break;

        }

    }

    public int getCantidadJugadores() {
       
        return this.cantidadJugadores;
    }

    public boolean validadDatosInicialesJuego(int cantJugadores, int tamañoTablero) {

        if (cantJugadores >= 1 && tamañoTablero >= 20) {
            this.cantidadJugadores = cantJugadores;
            this.largoTablero = tamañoTablero;
            crearTablero();
            return true;
        } else {
            return false;
        }

    }

    public void crearTablero() {
        Tablero tablero = new Tablero(this.largoTablero);
        tablero.generarTablero();
        this.casillas = tablero.getCasillas();
    }

}
