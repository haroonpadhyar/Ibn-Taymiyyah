package com.maktashaf.taymiyyah.analysis.generator;

import com.maktashaf.taymiyyah.analysis.generator.Quran.QuranIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.arabic.ArabicJalalaynIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.arabic.ArabicMuyassarIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishAhmedAliIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishAhmedRazaKhanIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishArberryIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishDaryabadiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishHilaliIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishItaniIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishMaududiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishMubarakpuriIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishPickthallIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishQaraiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishQaribullahIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishSahihIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishSarwarIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishShakirIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishWahiduddinIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.english.EnglishYousufAliIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduAhmedAliIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduAhmedRazaKhanIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduJalandhryIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduJawadiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduJunagarhiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduMaududiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduNajafiIndexGenerator;
import com.maktashaf.taymiyyah.analysis.generator.translation.urdu.UrduQadriIndexGenerator;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Haroon Anwar Padhyar
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    QuranIndexGenerator.class,
    ArabicJalalaynIndexGenerator.class,
    ArabicMuyassarIndexGenerator.class,
    UrduAhmedAliIndexGenerator.class,
    UrduAhmedRazaKhanIndexGenerator.class,
    UrduJalandhryIndexGenerator.class,
    UrduJawadiIndexGenerator.class,
    UrduJunagarhiIndexGenerator.class,
    UrduMaududiIndexGenerator.class,
    UrduNajafiIndexGenerator.class,
    UrduQadriIndexGenerator.class,
    EnglishAhmedAliIndexGenerator.class,
    EnglishAhmedRazaKhanIndexGenerator.class,
    EnglishArberryIndexGenerator.class,
    EnglishDaryabadiIndexGenerator.class,
    EnglishHilaliIndexGenerator.class,
    EnglishItaniIndexGenerator.class,
    EnglishMaududiIndexGenerator.class,
    EnglishMubarakpuriIndexGenerator.class,
    EnglishPickthallIndexGenerator.class,
    EnglishQaraiIndexGenerator.class,
    EnglishQaribullahIndexGenerator.class,
    EnglishSahihIndexGenerator.class,
    EnglishSarwarIndexGenerator.class,
    EnglishShakirIndexGenerator.class,
    EnglishWahiduddinIndexGenerator.class,
    EnglishYousufAliIndexGenerator.class
})
public class IndexGeneratorSuite {
}
