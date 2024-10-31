package com.aluracursos.conversordemonedas.main;

import com.aluracursos.conversordemonedas.api.ExchangeAPI;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeAPI exchangeAPI = new ExchangeAPI();

        // Menú principal usando un String multilínea
        String menu = """
                ******************************************************************************
                ¡Sea bienvenid@ al Conversor de Moneda!
                          \s
                1) Seleccionar moneda!
                2) Salir
               \s
                Elija una opción válida:\s

                ******************************************************************************
               \s""";

        while (true) {
            // Mostrar el menú principal
            System.out.println(menu);

            int option = scanner.nextInt();

            if (option == 1) {
                // Menú para seleccionar moneda de origen
                String fromCurrency = seleccionarMoneda(scanner, "origen");
                if (fromCurrency.equals("SALIR")) {
                    System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                    break;
                }

                // Menú para seleccionar moneda de destino
                String toCurrency = seleccionarMoneda(scanner, "destino");
                if (toCurrency.equals("VOLVER")) {
                    continue;  // Vuelve al menú principal
                } else if (toCurrency.equals("SALIR")) {
                    System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                    break;
                }

                // Pedir cantidad y realizar la conversión
                try {
                    System.out.print("Ingrese la cantidad a convertir: ");
                    double amount = scanner.nextDouble();

                    double exchangeRate = exchangeAPI.getExchangeRate(fromCurrency, toCurrency);
                    double convertedAmount = amount * exchangeRate;

                    System.out.println(amount + " " + fromCurrency + " equivale a " + convertedAmount + " " + toCurrency);

                } catch (Exception e) {
                    System.out.println("Error al realizar la conversión: " + e.getMessage());
                }

            } else if (option == 2) {
                System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                break;
            } else {
                System.out.println("Opción inválida. Por favor, elija una opción válida.");
            }
        }
    }

    // Metodo para seleccionar la moneda con los menús
    private static String seleccionarMoneda(Scanner scanner, String tipo) {

        // Submenú de selección de moneda utilizando String multilínea
        String subMenu = """
                Elige la moneda de %s:
                1) COP
                2) USD
                3) EUR
                4) ARG
                5) JPY
                6) MXN
                7) KRW
                8) GBP
                9) CAD
                """;

        if (tipo.equals("origen")) {
            subMenu += "10) Salir\n";
        } else {
            subMenu += """
                10) Volver
                11) Salir
                """;
        }

        while (true) {
            // Mostrar el submenú con el tipo de moneda (origen/destino)
            System.out.printf(subMenu, tipo);

            int choice = scanner.nextInt();
            switch (choice) {
                case 1: return "COP";
                case 2: return "USD";
                case 3: return "EUR";
                case 4: return "ARG";
                case 5: return "JPY";
                case 6: return "MXN";
                case 7: return "KRW";
                case 8: return "GBP";
                case 9: return "CAD";
                case 10:
                    if (tipo.equals("origen")) {
                        return "SALIR";
                    } else {
                        return "VOLVER";
                    }
                case 11:
                    if (tipo.equals("destino")) {
                        return "SALIR";
                    }
                    break;
                default:
                    System.out.println("Opción inválida. Elija nuevamente.");
            }
        }
    }
}