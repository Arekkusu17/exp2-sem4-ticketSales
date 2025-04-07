/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ticketsales;
import java.util.Arrays;
import java.util.Scanner; 

/**
 *
 * @author arekk
 */
public class TicketSales {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Creamos dinamicamente las zonas de asiento, para nuestro caso el total de asistentes será 60
        String[] zoneA = new String[20];
        String[] zoneB = new String[20];
        String[] zoneC = new String[20];

        // Se definen los valores constantes como son precios de las entradas para cada zona, y la totalidad de asientos
        final int priceA = 30000;
        final int priceB = 25000;
        final int priceC = 20000;
        final int totalSeats = 60;
        
        int seatPrice = 0;

        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
            if (seatNumber <= 20) {
                zoneA[seatNumber - 1] = "A" + seatNumber;
            } else if (seatNumber <= 40) {
                zoneB[seatNumber - 21] = "B" + seatNumber;
            } else {
                zoneC[seatNumber - 41] = "C" + seatNumber;
            }
        }

        //Opciones del Menú
        String[] menu = {
            "1.- Comprar Entrada", 
            "2.- Salir"
        };

        int opcion;
        boolean exitMenu = false;

        // Bucle principal del menú
        do {

            System.out.println("\n=== Menú Principal ===");

            // Imprimir las opciones del menú
            for (int i = 0; i < menu.length; i++) {
                System.out.println(menu[i]);
            }

            System.out.println("\nSeleccione una opción:");
            opcion = scanner.nextInt();
            scanner.nextLine();

            // Lógica según la opción seleccionada
            switch (opcion) {
                // Lógica para la opción "Comprar Entrada"
                case 1:
                    // Manejo de compras múltiples por ejecución.
                    boolean isBuying = true;

                    while (isBuying) {
                        System.out.println("¡Has seleccionado comprar una entrada!");
                        
                        //Se imprimen las zonas y asientos disponibles
                        System.out.println("Zone A: " + Arrays.toString(zoneA));
                        System.out.println("Zone B: " + Arrays.toString(zoneB));
                        System.out.println("Zone C: " + Arrays.toString(zoneC));
                        
                        boolean seatFound = false;
                        String selectedSeat = "";
                        
                        //Se valida que el asiento ingresado por el usuario exista y no esté ocupado
                        // Se utiliza un bucle while para seguir pidiendo el asiento hasta que se encuentre uno válido
                        while(!seatFound){
                            System.out.print("Ingresa el asiento que deseas comprar:  ");
                            selectedSeat = scanner.nextLine().toUpperCase();
                            
                            if (findAndReserveSeat(zoneA, selectedSeat, priceA)) {
                                seatFound = true;
                                seatPrice = priceA;
                            } else if (findAndReserveSeat(zoneB, selectedSeat, priceB)) {
                                seatFound = true;
                                seatPrice = priceB;
                            } else if (findAndReserveSeat(zoneC, selectedSeat, priceC)) {
                                seatFound = true;
                                seatPrice = priceC;
                            } else {
                                System.out.println("\nEl asiento ingresado no existe o ya está ocupado. Por favor, intenta nuevamente.");
                            }

                        }
                        
                
                        int age = 0;
                        int discount;
                        int discountedPrice;

                        boolean validAge = false;

                        // Se utiliza un bucle while para seguir pidiendo la edad hasta que se ingrese un valor válido
                        while (!validAge) {
                            System.out.print("Ingresa tu edad: ");
                            if (scanner.hasNextInt()) {
                                age = scanner.nextInt();
                                scanner.nextLine(); 

                                if (age > 0) {
                                    validAge = true; // Edad válida
                                } else {
                                    System.out.println("\nEdad no válida. Debe ser un número entero mayor a cero.");
                                }
                            } else {
                                System.out.println("\nEdad no válida. Por favor, ingresa un número entero mayor a cero.");
                                scanner.nextLine(); // Clear the invalid input
                            }
                        }

                        // Aplicar descuentos según la edad
                        if (age < 18) {
                            discount = 10;
                            System.out.println("Felicidades, obtienes un descuento del 10% por ser estudiante.");
                        } else if (age >= 65) {
                            discount = 15;
                            System.out.println("Felicidades, obtienes un descuento del 15% por pertenecer a la tercera edad.");
                        } else {
                            discount = 0;
                            System.out.println("No hay descuento por edad.");
                        }

                        discountedPrice = seatPrice * (100 - discount) / 100;

                        // Mostrar resumen de la compra
                        System.out.println("=====================");
                        System.out.println("Resumen de la compra:");
                        System.out.println("=====================");
                        System.out.println("Ubicación del asiento: " + selectedSeat);
                        System.out.println("Precio base de la entrada: $" + seatPrice);
                        System.out.println("Descuento aplicado: " + discount + "%");
                        System.out.println("Precio final a pagar: $" + discountedPrice);


                        String continueBuying;
                        
                        do {
                            System.out.print("¿Deseas comprar otra entrada? (S/N): ");
                            continueBuying = scanner.nextLine().trim().toUpperCase();
                            if (!continueBuying.equals("S") && !continueBuying.equals("N")) {
                                System.out.println("Opción no reconocida. Por favor ingresa 'S' para continuar o 'N' para salir.");
                            }
                        } while (!continueBuying.equals("S") && !continueBuying.equals("N"));

                        if (continueBuying.equals("N")) {
                            exitMenu = true;
                            isBuying = false;
                            System.out.println("¡Gracias por utilizar el sistema! Hasta luego.");
                        }
                    }   
                    break;
                case 2:
                    // Lógica para la opción "Salir"
                    exitMenu = true;
                    System.out.println("¡Gracias por utilizar el sistema! Hasta luego.");
                    break;

                default:
                    // En caso de una opción inválida
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
                    break;
            }
        } while (!exitMenu);  // El bucle continuará hasta que el usuario seleccione la opción de salir o no desee comprar más entradas

        // Cerrar el scanner
        scanner.close();
        
    }

    // Método para buscar y seleccionar el asiento en una zona del teatro
    public static boolean findAndReserveSeat(String[] zone, String selectedSeat, int price) {
        for (int i = 0; i < zone.length; i++) {
            if (zone[i] != null && zone[i].equals(selectedSeat)) {
                if (zone[i].equals("X")) {
                    // Manejo del asiento ocupado
                    return false; 
                } else {
                    //Reservar el asiento
                    zone[i] = "X"; 
                    System.out.println("Asiento " + selectedSeat + " seleccionado.");
                    System.out.println("El precio base de la entrada es: $" + price);
                    return true; 
                }
            }
        }
        // Si el asiento no se encuentra en ninguna zona.
        return false; // Seat not found
    }
}
