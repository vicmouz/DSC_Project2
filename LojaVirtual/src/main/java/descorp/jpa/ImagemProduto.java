/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;



/**
 *
 * @author marcosbrasil98
 */
@Embeddable
public class ImagemProduto implements Serializable{
   
    @Column(name = "IMAGEM_URL")
    private String imageProduto;

    public String getImageProduto() {
        return imageProduto;
    }

    public void setImageProduto(String imageProduto) {
        this.imageProduto = imageProduto;
    }
    
  }