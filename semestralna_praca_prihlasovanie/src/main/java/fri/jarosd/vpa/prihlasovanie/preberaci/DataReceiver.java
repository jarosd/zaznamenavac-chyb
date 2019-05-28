package fri.jarosd.vpa.prihlasovanie.preberaci;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fri.jarosd.vpa.prihlasovanie.datoveEntity.Pouzivatel;

import fri.jarosd.vpa.prihlasovanie.datoveEntity.PouzivatelZmenEmail;
import fri.jarosd.vpa.prihlasovanie.datoveEntity.PouzivatelZmenHeslo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class DataReceiver {

    private SqlSessionFactory sqlPrikazovac;

    public DataReceiver() {
        String cestaNastavenia = "/static/mybatis/mybatis-config.xml";
        InputStream nastavenia = getClass().getClassLoader().getResourceAsStream(cestaNastavenia);
        this.sqlPrikazovac = new SqlSessionFactoryBuilder().build(nastavenia);
    }

    public boolean skontrolujApiKluc(String apiKluc) {
        if (apiKluc != null) {
            try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
                int pocetZaznamov = relacia.selectOne("PouzivatelMapper.overApi", apiKluc);
                return pocetZaznamov == 1;
            } catch (Exception vynimka) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean registrujPouzivatela(Pouzivatel novyPouzivatel) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            // na server musi prist heslo uz zakryptovane!!!
            int pocetVlozenychRiadkov = relacia.insert("PouzivatelMapper.registrujPouzivatela", novyPouzivatel);
            relacia.commit();

            return pocetVlozenychRiadkov == 1;
        } catch (Exception vynimka) {
            return false;
        }
    }

    public Pouzivatel prihlasPouzivatela(String nickPouzivatela, String hesloOdPouzivatela) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            Pouzivatel pozadujuciPrihlasenie = relacia.selectOne("PouzivatelMapper.getHeslo", nickPouzivatela);

            BCrypt.Result overenie = BCrypt.verifyer().verify(hesloOdPouzivatela.toCharArray(), pozadujuciPrihlasenie.getHeslo());
            // nastavenie pre session musí byť vykonané na frontende (odhlasenie to isté)
            if (overenie.verified) {
                return pozadujuciPrihlasenie;
            } else {
                return null;
            }
        } catch (Exception vynimka) {
            return null;
        }
    }

    public Pouzivatel getInfoPouzivatel(String nick) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            Pouzivatel pouzivatel = relacia.selectOne("PouzivatelMapper.getInformaciePouzivatela", nick);
            return pouzivatel;
        } catch (Exception vynimka) {
            return null;
        }
    }

    public boolean jeNickObsadeny(String nick) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            int pocetNajdenychNickov = relacia.selectOne("PouzivatelMapper.skontrolujNick", nick);
            return pocetNajdenychNickov > 0;
        } catch (Exception vynimka) {
            return true;
        }
    }

    public boolean zmenEmail(PouzivatelZmenEmail pouzivatelEmail) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            boolean mamPravo = this.skontrolujApiKluc(pouzivatelEmail.getApiKey());

            if (mamPravo) {
                int pocetOvplyvnenych = relacia.update("PouzivatelMapper.aktualizujEmail", pouzivatelEmail);
                relacia.commit();

                return pocetOvplyvnenych == 1;
            } else {
                return false;
            }
        } catch (Exception vynimka) {
            return false;
        }
    }

    public boolean zmenHeslo(PouzivatelZmenHeslo zmenaHeslaUdaje) {
        try (SqlSession relacia = this.sqlPrikazovac.openSession()) {
            boolean mamPravo = this.skontrolujApiKluc(zmenaHeslaUdaje.getApiKey());

            if (mamPravo) {
                Pouzivatel pozadujuciZmenu = relacia.selectOne("PouzivatelMapper.getHeslo", zmenaHeslaUdaje.getNick());

                BCrypt.Result overenie = BCrypt.verifyer().verify(zmenaHeslaUdaje.getStareHeslo().toCharArray(), pozadujuciZmenu.getHeslo());
                // nastavenie pre session musí byť vykonané na frontende (odhlasenie to isté)
                if (overenie.verified) {
                    int pocetOvplyvnenych = relacia.update("PouzivatelMapper.aktualizujHeslo", zmenaHeslaUdaje);
                    relacia.commit();

                    return pocetOvplyvnenych == 1;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception vynimka) {
            return false;
        }
    }
}
