/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.CorProduto;
import exemplo.jpa.ImagemProduto;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.persistence.CacheRetrieveMode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author marcosbrasil98
 */
public class ImagemCrudTest extends GenericTest {

    @Test
    public void Persistir() {
        logger.info("Executando Persistir()");
        File file = new File("/resources/imagens/camisa_azul.jpg");
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImagemProduto ip = new ImagemProduto();
        ip.setImagem(bFile);
        ip.setId(1);
        CorProduto c = new CorProduto();
        c = criarCor();
        ip.setCorImagem(c);
        System.out.println(ip.getImagem());
        em.persist(ip);
        em.flush();
        assertNotNull(ip.getImagem());

    }

    @Test
    public void Atualizar() {
        logger.info("Executando atualizar()");
        File file = new File("/resources/imagens/bone_verde.jpeg");
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer id = 2;
        ImagemProduto ip = em.find(ImagemProduto.class, id);
        ip.setImagem(bFile);

        em.flush();
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        ip = em.find(ImagemProduto.class, id, properties);
        assertEquals(bFile, ip.getImagem());
        logger.info("Atualizado");
    }

    @Test
    public void atualizarMerge() {
        File file = new File("/resources/imagens/bone_verde.jpeg");
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer id = 2;
        ImagemProduto ip = em.find(ImagemProduto.class, id);
        ip.setImagem(bFile);

        em.clear();
        em.merge(ip);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        ip = em.find(ImagemProduto.class, id, properties);
        assertEquals(bFile, ip.getImagem());

    }

    @Test
    public void remover() {
        logger.info("Executando remover()");
        ImagemProduto ip = em.find(ImagemProduto.class, 2);
        em.remove(ip);
        ImagemProduto ip1 = em.find(ImagemProduto.class, 2);
        assertNull(ip1);
    }

    private CorProduto criarCor() {
        Integer id = 1;
        CorProduto cp = em.find(CorProduto.class, id);
        return cp;
    }
}
