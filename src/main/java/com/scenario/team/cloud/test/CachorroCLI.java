package com.scenario.team.cloud.test;

import java.util.Scanner;

public class CachorroCLI {
    public static void main(String[] args) {
        Cachorro cachorro = new Cachorro();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um comando (andar, correr, sair):");
        while (true) {
            String comando = scanner.nextLine();
            if (comando.equalsIgnoreCase("andar")) {
                cachorro.andar();
            } else if (comando.equalsIgnoreCase("correr")) {
                cachorro.correr();
            } else if (comando.equalsIgnoreCase("sair")) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Comando desconhecido. Tente novamente.");
            }
        }
        scanner.close();
    }
}
