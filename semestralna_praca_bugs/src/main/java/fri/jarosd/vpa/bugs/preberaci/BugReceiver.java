package fri.jarosd.vpa.bugs.preberaci;

import fri.jarosd.vpa.bugs.datoveEntity.Bug;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BugReceiver {

    private SqlSessionFactory sqlPrikazovac;

    public BugReceiver() {
        String cestaNastavenia = "/static/mybatis/mybatis-config.xml";
        InputStream nastavenia = getClass().getClassLoader().getResourceAsStream(cestaNastavenia);
        this.sqlPrikazovac = new SqlSessionFactoryBuilder().build(nastavenia);
    }

    private ArrayList<Bug> getZoznamBugov(String bugDefinition, HashMap<String, Object> parametre) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            ArrayList<Bug> chybyZoznam = null;

            if (parametre != null) {
                chybyZoznam = new ArrayList<Bug>(relacia.selectList(bugDefinition, parametre));
            } else {
                chybyZoznam = new ArrayList<Bug>(relacia.selectList(bugDefinition));
            }

            return chybyZoznam;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public ArrayList<Bug> getZoznamVsetkychBugov() {
        return this.getZoznamBugov("BugMapper.getBugs", null);
    }

    public ArrayList<Bug> getZoznamBugovVyriesene(Boolean vyriesene) {
        HashMap<String, Object> hodnoty = new HashMap<String, Object>();
        hodnoty.put("vyriesene", vyriesene);

        return this.getZoznamBugov("BugMapper.getBugs", hodnoty);
    }

    public ArrayList<Bug> getZoznamBugovAutor(String autor, Boolean vyriesene) {
        HashMap<String, Object> hodnoty = new HashMap<String, Object>();
        hodnoty.put("autor", autor);
        hodnoty.put("vyriesene", vyriesene);

        return this.getZoznamBugov("BugMapper.getAuthorBugs", hodnoty);
    }

    public ArrayList<Bug> getZoznamBugovDolezitost(int dolezitost, Boolean vyriesene) {
        HashMap<String, Object> hodnoty = new HashMap<String, Object>();
        hodnoty.put("dolezitost", dolezitost);
        hodnoty.put("vyriesene", vyriesene);

        return this.getZoznamBugov("BugMapper.getSeverityBugs", hodnoty);
    }

    public Bug getJedenBug(int chybaID) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            HashMap<String, Object> hodnoty = new HashMap<String, Object>();
            hodnoty.put("chybaID", chybaID);

            Bug chyba = relacia.selectOne("BugMapper.getOneBug", hodnoty);
            return chyba;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public boolean pridajBug(Bug novyBug) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetPridanych = relacia.insert("BugMapper.pridajBug", novyBug);
            relacia.commit();

            return pocetPridanych == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean oznacChybuAkoVyriesenu(int cisloChyby, boolean jeVyriesena) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            if (jeVyriesena) {
                relacia.update("BugMapper.vyriesenyBug", cisloChyby);
            } else {
                relacia.update("BugMapper.nevyriesenyBug", cisloChyby);
            }

            relacia.commit();
            return true;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean aktualizujBug(Bug aktualizaciaChyby) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            relacia.update("BugMapper.modifikujBug", aktualizaciaChyby);
            relacia.commit();

            return true;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean vymazBug(int idChyby) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetOvplyvnenych = relacia.delete("BugMapper.vymazBug", idChyby);
            relacia.commit();

            return pocetOvplyvnenych == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }
}
