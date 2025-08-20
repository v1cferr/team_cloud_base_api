// 1. Para compilar:
// javac Cachorro.java CachorroCLI.java
//
// 2. Para rodar:
// java com.scenario.team.cloud.test.CachorroCLI
//
// Observações importantes:
// 1. Execute os comandos dentro da pasta onde estão os arquivos .java (src/main/java/com/scenario/team/cloud/test).
// 2. Se aparecer erro "Could not find or load main class", verifique se está no diretório correto e se o comando java inclui o pacote completo.
// 3. Se der erro de classe não encontrada, certifique-se de que ambos os arquivos foram compilados.
// 4. Se usar IDE, pode rodar direto pelo botão de execução.
// 5. Se mudar o nome do pacote ou mover o arquivo, ajuste o comando java conforme o novo caminho do pacote.
// 6. Para limpar arquivos .class antigos, use: rm *.class
// 7. Se usar Windows, os comandos são os mesmos, mas use \ ao invés de / nos caminhos.
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
