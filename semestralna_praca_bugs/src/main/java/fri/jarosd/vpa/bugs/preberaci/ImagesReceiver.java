package fri.jarosd.vpa.bugs.preberaci;

import fri.jarosd.vpa.bugs.datoveEntity.Obrazok;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;

public class ImagesReceiver {

    private String kamUlozitAdresar;
    private SqlSessionFactory sqlPrikazovac;

    public ImagesReceiver(Environment environment) {
        String cestaNastavenia = "/static/mybatis/mybatis-config.xml";
        InputStream nastavenia = getClass().getClassLoader().getResourceAsStream(cestaNastavenia);

        this.sqlPrikazovac = new SqlSessionFactoryBuilder().build(nastavenia);
        this.kamUlozitAdresar = environment.getProperty("file.uploadDir");
    }

    public ArrayList<Obrazok> getObrazkyKuChybe(int idChyby) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            ArrayList<Obrazok> chybyZoznam = new ArrayList<Obrazok>(relacia.selectList("ImageMapper.obrazkyChyby", idChyby));

            return chybyZoznam;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public boolean pridajObrazok(Obrazok novyObrazok) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            relacia.insert("ImageMapper.pridajObrazok", novyObrazok);
            relacia.commit();

            return true;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean odstranObrazok(int idZaznamu) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetVymazanych = relacia.delete("ImageMapper.odstranObrazok", idZaznamu);
            relacia.commit();

            return pocetVymazanych == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }

    private Resource getObrazok(String nazovObrazku) {
        Path cestaSubor = Paths.get(this.kamUlozitAdresar).resolve(nazovObrazku).normalize();

        try {
            Resource resource = new UrlResource(cestaSubor.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException vynimka) {
            return null;
        }
    }

    private boolean existujeObrazok(String nazovObrazku) {
        Path cestaSubor = Paths.get(this.kamUlozitAdresar).resolve(nazovObrazku).normalize();

        try {
            Resource resource = new UrlResource(cestaSubor.toUri());
            return resource.exists();
        } catch (MalformedURLException vynimka) {
            return true;
        }
    }

    private Obrazok getInfoObrazok(int idObrazku) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            Obrazok obrazok = relacia.selectOne("ImageMapper.konkretnyObrazok", idObrazku);
            return obrazok;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public String uploadujObrazok(String autor, MultipartFile subor) {
        // očisti nepovolené veci z nahraného súboru
        if (this.kamUlozitAdresar != null) {
            String novyNazovSuboru = autor + "_" + new Date().getTime() + "_" + StringUtils.cleanPath(subor.getOriginalFilename());
            Path kamUlozit = Paths.get(this.kamUlozitAdresar).toAbsolutePath().normalize();

            try {
                Path umiestnenie = kamUlozit.resolve(novyNazovSuboru);
                Files.copy(subor.getInputStream(), umiestnenie, StandardCopyOption.REPLACE_EXISTING);

                return novyNazovSuboru;
            } catch (IOException vynimka) {
                return "";
            }
        } else {
            return "";
        }
    }

    public Resource getObrazokZobraz(int idObrazku) {
        Obrazok obrazok = this.getInfoObrazok(idObrazku);

        if (obrazok != null) {
            return this.getObrazok(obrazok.getCestaObrazok());
        } else {
            return null;
        }
    }

    public boolean vymazObrazokZoServera(int idObrazku) {
        Obrazok obrazok = this.getInfoObrazok(idObrazku);

        if (obrazok != null) {
            String nazovObrazku = obrazok.getCestaObrazok();
            Path cestaSubor = Paths.get(this.kamUlozitAdresar).resolve(nazovObrazku).normalize();
            File subor = cestaSubor.toFile();

            return subor.delete();
        } else {
            return false;
        }
    }

}
