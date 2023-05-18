package com.example.kalkulacka;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class HelloController {

    // Pomocné proměnné
    int i = 0; int pomocnyINT = 0;
    String pomocnyZnak1 = ""; String pomocnyZnak2 = ""; String pomocnyText1 = ""; String pomocnyText2 = "";

    // Pomocné proměnné pro operace se vstupem a textem
    String prvniCislo = "0";
    boolean carka = false; boolean zapor = false;
    int poziceCarky = 0;

    // Pomocné proměnné pro matematické operace
    boolean scitani = false; boolean odcitani = false; boolean nasobeni = false; boolean deleni = false;
    boolean aktivaceMatematickeOperace = false; boolean provedenyVypocet = false; boolean rovnaSe = false;
    boolean chyba = false;
    BigDecimal pomocneCisloNUM = new BigDecimal("0");

    // Hlavní proměnné pro matematické operace
    BigDecimal prvniCisloNUM = new BigDecimal("0");  BigDecimal druheCisloNUM = new BigDecimal("0");

    // Pomcná proměnná pro paměť M
    boolean aktivaceMRC = false;

    // Hlavní proměnné pro paměť M
    BigDecimal pametBigDoubleM = new BigDecimal("0"); BigDecimal PomocnyBigDoublePametiM = new BigDecimal("0");

    // boolean aktivaceMRC = false;



    public void NacitaniZnakuZtlacitekKalkulacky() {

        aktivaceMRC = false;

        pomocnyText2 = prvniCislo;
        if (zapor == false && prvniCislo.length() < 11) {
            prvniCislo = prvniCislo + pomocnyText1; pomocnyText2 = prvniCislo;
        }
        if (zapor == true) {
            if (prvniCislo.length() < 10) {
                prvniCislo = prvniCislo + pomocnyText1; pomocnyText2 = "-" + prvniCislo;
            }
                else {pomocnyText2 = "-" + prvniCislo;}
        }
        Label1.setText(pomocnyText2);
        pomocnyText1 = ""; pomocnyText2 = "";
        if (aktivaceMatematickeOperace == true) {rovnaSe = true;}
    }

    public void CisteniPocatecniNuly() {
        if (prvniCislo.length() == 1) {
            if (Integer.parseInt(prvniCislo) == 0) {prvniCislo = "";}
        }
    }

    public void VymenaTecekCarek() {
        for (i = 0; i < pomocnyText2.length(); i++) {
            pomocnyZnak1 = pomocnyText2.substring(i, i + 1);
            if (pomocnyText2.charAt(i) == (char) pomocnyINT) {pomocnyZnak1 = pomocnyZnak2;}
            pomocnyText1 = pomocnyText1 + pomocnyZnak1;
        }
    }

    public void DeaktiktivaceMatematickychOpearci() {
        scitani = false; odcitani = false; nasobeni = false; deleni = false;
    }

    public void CastecneCisteniPromennych() {
        pomocnyText1 = ""; aktivaceMRC = false; carka = false;
    }

    public void NacteniDisplaye() {
        // Výměna desetinné čárky u String čísla z Čarky na Tečku
        pomocnyText2 = Label1.getText(); pomocnyText1 = ""; pomocnyZnak2 = "."; pomocnyINT = 44;
        VymenaTecekCarek();
        // KONEC Výměny desetinné čárky u String čísla z Čarky na Tečku
        prvniCisloNUM = new BigDecimal(pomocnyText1);
    }

    public void NacteniDisplayeAvypocet() {
        NacteniDisplaye();
        // Provedení příslušné matematické operace
        if (scitani == true) {druheCisloNUM = druheCisloNUM.add(prvniCisloNUM).stripTrailingZeros();}
        if (odcitani == true) {druheCisloNUM = druheCisloNUM.subtract(prvniCisloNUM).stripTrailingZeros();}
        if (nasobeni == true) {druheCisloNUM = druheCisloNUM.multiply(prvniCisloNUM).stripTrailingZeros();}
        if (deleni == true && prvniCisloNUM.compareTo(new BigDecimal("0")) == 0) {
            Label1.setText("CHYBA!!!"); Label2.setText(""); Label3.setText("Dělit nulou není možné, stiskni C.");
            chyba = true;
        }
        if (deleni == true && prvniCisloNUM.compareTo(new BigDecimal("0")) != 0) {
            druheCisloNUM = druheCisloNUM.divide(prvniCisloNUM, 12, RoundingMode.CEILING).stripTrailingZeros();
        }
        // KONECProvedení příslušné matematické operace
        prvniCisloNUM = new BigDecimal("0");
        // rovnaSe = false; provedenyVypocet = true;
        KontrolaPreteceniDisplaye();
    }

    public void KontrolaPreteceniDisplaye() {
        if (druheCisloNUM.compareTo(new BigDecimal("99999999999")) > 0) {
            Label1.setText("CHYBA!!!"); Label2.setText(""); Label3.setText("Výsledek je příliš velký, stiskni C.");
            chyba = true;
        }
        if (druheCisloNUM.compareTo(new BigDecimal("-9999999999")) < 0) {
            Label1.setText("CHYBA!!!"); Label2.setText(""); Label3.setText("Výsledek je příliš malý, stiskni C.");
            chyba = true;
        }
    }

    public void KontrolaPreteceniPameti() {
        if (pametBigDoubleM.compareTo(new BigDecimal("99999999999")) > 0
                || pametBigDoubleM.compareTo(new BigDecimal("-9999999999")) < 0) {
            Label1.setText("CHYBA!!!"); Label2.setText(""); Label3.setText("Přeplnění paměti, stiskni C.");
            chyba = true;
        }
    }

    public void ZaokrouhleniNaPocetMistDisplaye() {
        if (pomocneCisloNUM.toString().length() > 11) {
            for (i = pomocneCisloNUM.toString().length(); i > 0; i--) {
                if (pomocneCisloNUM.toString().length() > 11) {
                    pomocneCisloNUM = pomocneCisloNUM.setScale(i - 1, RoundingMode.HALF_UP);
                }
            }
        }
    }

    public void BigDoubleNaStringZobrazeni() {
        pomocneCisloNUM = druheCisloNUM;
        ZaokrouhleniNaPocetMistDisplaye();
        druheCisloNUM = pomocneCisloNUM;
        // Odstranění chyby při převodu z Big Double na String,
        // kdy se převádí v Hexadecimálu a Odstranění přebytčených nul a čárky
        pomocnyText2 = String.format("%.10f", druheCisloNUM);
        // BigDouble bd = new BigDouble(3.14159265359);
        // String str = bd.toString(4);
        //System.out.println(str); // vypíše "3.1416"
        pomocnyText2 = pomocnyText2.replaceAll("0*$", "").replaceAll("\\,$", "");
        //První řádek vytvoří pomocnyText2 s hodnotou na 10 desetinných míst. Druhý řádek používá regular expression 0*$
        // pro odstranění všech nul na konci řetězce. Třetí řádek používá regular expression \\.$ pro odstranění
        // desetinné tečky na konci řetězce (pokud byla řetězec celé číslo). Nakonec se vypíše pomocnyText2,
        // který bude mít hodnotu "30,101".
        // KONEC Odstranění chyby při převodu z Big Double na String,
        // kdy se převádí v Hexadecimálu a Odstranění přebytčených nul a čárky

        // Výměna desetinné čárky u String čísla z Tečky na Čárku
        pomocnyText1 = ""; pomocnyZnak2 = ","; pomocnyINT = 46;
        VymenaTecekCarek();
        // KONEC Výměny desetinné čárky u String čísla z Tečky na Čárku
        Label1.setText(pomocnyText1);
        // Zjištění zda je výsledek kladný či záporný a nastavení hodnot na pozadí pro správných běh programu
        if (druheCisloNUM.compareTo(new BigDecimal("0")) >= 0) {zapor = false; prvniCislo = pomocnyText1;}
            else {
                zapor = true;
                prvniCisloNUM = druheCisloNUM.multiply(new BigDecimal("-1"));
                prvniCislo = prvniCisloNUM.toString();
            }
        // KONEC Zjištění zda je výsledek kladný či záporný a nastavení hodnot na pozadí pro správných běh programu
    }

    public void OhlidaniSituacePromennychPoVypoctuProCisla() {
        if (provedenyVypocet == true) {
            CastecneCisteniPromennych(); prvniCislo = "0"; zapor = false; provedenyVypocet = false;
        }
    }

    public void OhlidaniSituacePromennychPoVypoctuProOperace() {
        NacteniDisplaye(); druheCisloNUM = prvniCisloNUM;
        CastecneCisteniPromennych(); prvniCislo = "0"; zapor = false; provedenyVypocet = false; rovnaSe = false;
        aktivaceMatematickeOperace = true;
    }

    public void LogikaProMatematickeOperace() {
        if (aktivaceMatematickeOperace == false && rovnaSe == false) {
            NacteniDisplaye(); CastecneCisteniPromennych();
            prvniCislo = "0"; zapor = false; druheCisloNUM = prvniCisloNUM;
        }
        else {
            if (rovnaSe == true) {
                NacteniDisplayeAvypocet();
                if (chyba == false) {
                    BigDoubleNaStringZobrazeni(); CastecneCisteniPromennych();
                    rovnaSe = false; provedenyVypocet = true;
                }
            }
        }
        DeaktiktivaceMatematickychOpearci(); aktivaceMatematickeOperace = true;
    }

    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Label Label3;

    @FXML
    protected void onHelloButtonClick0() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "0";
            if (prvniCislo.length() == 1) {
                if (Integer.parseInt(prvniCislo) == 0) {pomocnyText1 = "";}
            }
            NacitaniZnakuZtlacitekKalkulacky();
        }
    }
    @FXML
    protected void onHelloButtonClick1() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "1"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick2() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "2"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick3() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "3"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick4() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "4"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick5() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "5"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick6() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "6"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick7() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "7"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick8() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "8"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClick9() {
        if (chyba == false) {pomocnyText1 = "9";
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "9"; CisteniPocatecniNuly(); NacitaniZnakuZtlacitekKalkulacky();}
    }
    @FXML
    protected void onHelloButtonClickCARKA() {
        if (chyba == false) {
            OhlidaniSituacePromennychPoVypoctuProCisla();
            pomocnyText1 = "";
            if (carka == false) {
                carka = true; pomocnyText1 = ",";
                if (Integer.parseInt(prvniCislo) == 0) {prvniCislo = "0";}
                    else {poziceCarky = prvniCislo.length() + 1;}
            }
            NacitaniZnakuZtlacitekKalkulacky();
        }
    }
    @FXML
    public void onHelloButtonClickPlusMinus() {
        if (chyba == false) {
            if (zapor == false) {zapor = true;} else zapor = false; NacitaniZnakuZtlacitekKalkulacky();
        }
    }
    @FXML
    public void onHelloButtonClickPLUS() {
        if (chyba == false) {
            if (provedenyVypocet == true) {OhlidaniSituacePromennychPoVypoctuProOperace(); scitani = true;}
            Label2.setText("+"); LogikaProMatematickeOperace(); scitani = true;
        }
    }
    @FXML
    public void onHelloButtonClickMIUNUS() {
        if (chyba == false) {
            if (provedenyVypocet == true) {OhlidaniSituacePromennychPoVypoctuProOperace(); odcitani = true;}
            Label2.setText("-"); LogikaProMatematickeOperace(); odcitani = true;
        }
    }
    @FXML
    public void onHelloButtonClickKRAT() {
        if (chyba == false) {
            if (provedenyVypocet == true) {OhlidaniSituacePromennychPoVypoctuProOperace(); nasobeni = true;}
            Label2.setText("x"); LogikaProMatematickeOperace(); nasobeni = true;
        }
    }
    @FXML
    public void onHelloButtonClickDELENO() {
        if (chyba == false) {
            if (provedenyVypocet == true) {OhlidaniSituacePromennychPoVypoctuProOperace(); deleni = true;}
            Label2.setText("÷"); LogikaProMatematickeOperace(); deleni = true;
        }
    }
    @FXML
    public void onHelloButtonClickROVNAse() {
        if (chyba == false) {
            if (provedenyVypocet == true) {
                Label2.setText("="); provedenyVypocet = false;
                CastecneCisteniPromennych(); DeaktiktivaceMatematickychOpearci();
                rovnaSe = false; aktivaceMatematickeOperace = false; prvniCislo = "0"; zapor = false;
            }
            if (rovnaSe == true) {
                Label2.setText("=");
                NacteniDisplayeAvypocet();
                if (chyba == false) {
                    BigDoubleNaStringZobrazeni(); CastecneCisteniPromennych(); DeaktiktivaceMatematickychOpearci();
                    rovnaSe = false; aktivaceMatematickeOperace = false; provedenyVypocet = true;
                }
            }
        }
    }
    @FXML
    public void onHelloButtonClickMplus() {
        if (chyba == false) {
            Label3.setText("M"); Label2.setText(""); prvniCislo = "0"; zapor = false; carka = false;
            if (rovnaSe == true) {
                NacteniDisplayeAvypocet(); BigDoubleNaStringZobrazeni(); ;
                pametBigDoubleM = pametBigDoubleM.add(druheCisloNUM);
            }
            if (rovnaSe == false) {
                NacteniDisplaye();
                pametBigDoubleM = pametBigDoubleM.add(prvniCisloNUM);
            }
            KontrolaPreteceniPameti();
            if (pametBigDoubleM.compareTo(BigDecimal.ZERO) == 0) {Label3.setText("");}
            }
    }
    @FXML
    public void onHelloButtonClickMminus() {
        if (chyba == false) {
            Label3.setText("M"); Label2.setText(""); prvniCislo = "0"; zapor = false; carka = false;
            if (rovnaSe == true) {
                NacteniDisplayeAvypocet(); BigDoubleNaStringZobrazeni(); ;
                pametBigDoubleM = pametBigDoubleM.subtract(druheCisloNUM);
            }
            if (rovnaSe == false) {
                NacteniDisplaye();
                pametBigDoubleM = pametBigDoubleM.subtract(prvniCisloNUM);
            }
            KontrolaPreteceniPameti();
            if (pametBigDoubleM.compareTo(BigDecimal.ZERO) == 0) {Label3.setText("");}
        }
    }
    @FXML
    public void onHelloButtonClickMRC() {
        if (chyba == false) {
            pomocneCisloNUM = pametBigDoubleM;
            ZaokrouhleniNaPocetMistDisplaye();
            pametBigDoubleM = pomocneCisloNUM;
            pomocnyText2 = String.format("%.10f", pametBigDoubleM);
            pomocnyText2 = pomocnyText2.replaceAll("0*$", "").replaceAll("\\,$", "");
            pomocnyText1 = ""; pomocnyZnak2 = ","; pomocnyINT = 46;
            VymenaTecekCarek();
            Label1.setText(pomocnyText1);
            if (aktivaceMRC == true) {
                aktivaceMRC = false; Label3.setText(""); pametBigDoubleM = new BigDecimal("0");
            }
                else aktivaceMRC = true;
                pomocnyText1="";
        }
    }
    @FXML
    public void onHelloButtonClickC() {
        DeaktiktivaceMatematickychOpearci(); CastecneCisteniPromennych(); chyba = false; rovnaSe = false; zapor = false;
        aktivaceMatematickeOperace = false; Label1.setText("0"); Label2.setText(""); Label3.setText("");
        druheCisloNUM = new BigDecimal("0"); prvniCisloNUM = new BigDecimal("0");
        if (pametBigDoubleM.compareTo(BigDecimal.ZERO) != 0) {Label3.setText("M");}
        prvniCislo = "0"; provedenyVypocet = false;
    }


    //    SYNTAXE A TAK DÁL PRO BUDOUCÍ PROGRAMOVÁNÍ
    //    {pomocnyZnak2 = prvniCislo.substring(1, pomocneCislo1)
    //    Label1.setText(Double.toString(druheCisloNUM));
    //    pomocneCislo1 = Integer.parseInt(pomocnyText1);
    //    pomocneCislo2 = Long.parseLong(pomocnyText2);
    //BigDecimal num1 = new BigDecimal("123.45");
    //BigDecimal num2 = new BigDecimal("67.89");
    //BigDecimal sum = num1.add(num2);
    //if (druheCisloNUM.compareTo(BigDecimal.ZERO) < 0) {
    //BigDecimal number = new BigDecimal("3.1415926535"); BigDecimal roundedNumber = number.setScale(3, RoundingMode.HALF_UP);
    //BigDecimal d = new BigDecimal("12.3456789");
    //BigDecimal rounded = d.setScale(2, RoundingMode.HALF_UP);
    //System.out.println(rounded); // vypíše "12.35"
    // druheCisloNUM = druheCisloNUM.divide(prvniCisloNUM, 12, RoundingMode.CEILING);
    // druheCisloNUM = druheCisloNUM.divide(prvniCisloNUM, 12, RoundingMode.CEILING).stripTrailingZeros();

    //Můžete použít tuto funkci:
    //String pomocnyText2 = String.format("%.15f", druheCisloNUM);
    //Tato funkce převede hodnotu proměnné Big Double na řetězec s fiksním počtem desetinných míst (15 v tomto případě). Tím zajistíte, že se číslo vždy převede v decimálním tvaru.

    //            Label1.setText(pametBigDoubleM.toString().replace(".", ","));
    // Jakým způsobem v JAVA mohu opravit řádek programu, který do Label1 vkládá hodnotu proměnné Big Double,
    // která se jmenuje "pametBigDoubleM" který vypadá takto: Label1.setText(pametBigDoubleM.toString());
    // aby se mi v "Label1" zobrazila hodnota proměnné "pametBigDoubleM", ale NE s desetinnou tečkou,
    // ale aby se tato tečka zobrazila jako čárka. Jestli je to možné, prosím o úpravu tohoto řádku
    // v programovacím jazyce JAVA a jeho zobrazení včetně popisu atributů a tak dále. Děkuji.
// Odpověď Chat GPT
    // Label1.setText(pametBigDoubleM.toString().replace(".", ","));
    // Metoda "replace()" nahradí v řetězci "." (desetinnou tečku) za "," (čárku).

}