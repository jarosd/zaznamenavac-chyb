package fri.jarosd.vpa.bugs.preberaci;

import fri.jarosd.vpa.bugs.datoveEntity.Dolezitost;
import fri.jarosd.vpa.bugs.datoveEntity.ZmenyPortal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class OtherReceiver {

    private SqlSessionFactory sqlPrikazovac;

    public OtherReceiver() {
        String cestaNastavenia = "/static/mybatis/mybatis-config.xml";
        InputStream nastavenia = getClass().getClassLoader().getResourceAsStream(cestaNastavenia);
        this.sqlPrikazovac = new SqlSessionFactoryBuilder().build(nastavenia);
    }

    public ArrayList<ZmenyPortal> getZoznamZmienPortal(String pouzivatel, Integer preskoc, Integer zobraz) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            HashMap<String, Object> dataPreSQL = new HashMap<String, Object>();
            dataPreSQL.put("pouzivatel", pouzivatel);
            dataPreSQL.put("preskoc", preskoc);
            dataPreSQL.put("zobraz", zobraz);

            ArrayList<ZmenyPortal> zmenyPortal = new ArrayList<ZmenyPortal>(relacia.selectList("DalsiePrikazyMapper.getZmeny", dataPreSQL));

            return zmenyPortal;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public Integer getZmenyPocet(String pouzivatel) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            HashMap<String, Object> dataPreSQL = new HashMap<String, Object>();
            dataPreSQL.put("pouzivatel", pouzivatel);

            Integer pocet = relacia.selectOne("DalsiePrikazyMapper.getZmenyPocet", dataPreSQL);
            return pocet;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public ArrayList<Dolezitost> getZoznamDolezitosti() {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            ArrayList<Dolezitost> dolezitost = new ArrayList<Dolezitost>(relacia.selectList("DalsiePrikazyMapper.getDolezitosti"));

            return dolezitost;
        } catch (Exception vynimka) {
            return null;
        }
    }
}
