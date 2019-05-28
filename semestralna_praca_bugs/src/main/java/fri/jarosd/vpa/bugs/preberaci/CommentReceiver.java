package fri.jarosd.vpa.bugs.preberaci;

import fri.jarosd.vpa.bugs.datoveEntity.Komentar;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;

public class CommentReceiver {

    private SqlSessionFactory sqlPrikazovac;

    public CommentReceiver() {
        String cestaNastavenia = "/static/mybatis/mybatis-config.xml";
        InputStream nastavenia = getClass().getClassLoader().getResourceAsStream(cestaNastavenia);
        this.sqlPrikazovac = new SqlSessionFactoryBuilder().build(nastavenia);
    }

    public ArrayList<Komentar> getKomentare(int idChyby) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            ArrayList<Komentar> zoznamKomentarov = new ArrayList<Komentar>(relacia.selectList("CommentMapper.getKomentare", idChyby));
            return zoznamKomentarov;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public Komentar getKomentar(int idKomentara) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            Komentar komentar = relacia.selectOne("CommentMapper.getKomentar", idKomentara);
            return komentar;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public boolean pridajKomentar(Komentar novyKomentar) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            relacia.insert("CommentMapper.pridajKomentar", novyKomentar);
            relacia.commit();

            return true;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean aktualizujKomentar(Komentar komentarNaAktualizaciu) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetUpravenych = relacia.update("CommentMapper.aktualizujKomentar", komentarNaAktualizaciu);
            relacia.commit();

            return pocetUpravenych == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean vymazKomentar(int idKomentar) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetVymazanych = relacia.delete("CommentMapper.vymazKomentar", idKomentar);
            relacia.commit();

            return pocetVymazanych == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }
}
