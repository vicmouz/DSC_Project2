/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.validador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ALUNO
 */
public class ValidadorPais implements ConstraintValidator<ValidaPais, String>{
    private List<String> paises;
    
    @Override
    public void initialize(ValidaPais validaPais) {
        this.paises = new ArrayList<>();
        this.leitor();
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : paises.contains(valor);
    }

    private void leitor() {
        Scanner src = new Scanner("./src/main/resources/arquivos/siglaPaises.txt");
        int count = 0;
        String linha = src.nextLine();
        
        try{
            FileReader arq = new FileReader(linha);
            BufferedReader lerArq = new BufferedReader(arq);
            
            String pais = lerArq.readLine(); //lê a linha até encontrar o valor "null", ou seja, o final do arquivo
            pais = pais.substring(0, pais.indexOf("-"));
            paises.add(pais);
            count++;
            while (pais != null && count < 249) {
                pais = lerArq.readLine(); 
                pais = pais.substring(0, pais.indexOf("-"));
                count++;
                paises.add(pais);
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s. \n", e.getMessage());
        }
    }
    
    
}
