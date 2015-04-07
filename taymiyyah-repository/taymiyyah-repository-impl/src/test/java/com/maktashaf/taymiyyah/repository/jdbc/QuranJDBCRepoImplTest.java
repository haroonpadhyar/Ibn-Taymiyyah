package com.maktashaf.taymiyyah.repository.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.jdbc.factory.ConnectionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class QuranJDBCRepoImplTest {
  QuranJDBCRepo quranJDBCRepo = new QuranJDBCRepoImpl();

  @Test
  public void shouldFindAllAr(){
    List<Quran> all = quranJDBCRepo.findAll(LocaleEnum.Ar);
    System.out.println(all.size());
    assertEquals(6236, all.size());
    assertNotNull(all.get(0).getAyahText());
    System.out.println(all.get(1).getAyahText());
  }

  @Test
  public void shouldFindAllUr(){
    List<Quran> all = quranJDBCRepo.findAll(LocaleEnum.Ur);
    System.out.println(all.size());
    assertEquals(6236, all.size());
    assertNotNull(all.get(0).getAyahText());
    System.out.println(all.get(1).getAyahText());
  }

  @Test
  public void shouldFindAllEn(){
    List<Quran> all = quranJDBCRepo.findAll(LocaleEnum.En);
    System.out.println(all.size());
    assertEquals(6236, all.size());
    assertNotNull(all.get(0).getAyahText());
    System.out.println(all.get(1).getAyahText());
  }

  @Test
  public void shouldFindAccumId(){
    Quran quran  = quranJDBCRepo.findByAccumId(2, LocaleEnum.En);
    assertNotNull(quran);
    assertNotNull(quran.getAyahText());
    System.out.println(quran.getAyahText());
  }

  @Test
  public void shouldFindAyahId(){
    Quran quran  = quranJDBCRepo.findByAyahId(2, 3, LocaleEnum.En);
    assertNotNull(quran);
    assertNotNull(quran.getAyahText());
    System.out.println(quran.getAyahText());
  }

  //////////////
  @Test
  public void testTTTT(){
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      String query = "update test.pet set owner='المفالري'";
      connection = ConnectionFactory.getInstance().getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(query);


    } catch(Exception e){
      e.printStackTrace();

      throw new RuntimeException(e);
    }finally {
      try {
        if(rs != null)
          rs.close();
        if(statement != null)
          statement.close();
        if(connection != null)
          connection.close();
      } catch(Exception ee){
        ee.printStackTrace();
      }
    }
  }

}
