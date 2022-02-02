package hu.ebanjo.ledshop.dbs.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TermekDTO {

    private Long id; // vonalkód
    private String EAN; // vonalkód
    private String itemNumber; // cikkszám
    private String manufacturerItemNumber; // gyártói Cikkszám
    private String manufacturer; // gyártó
    private String name;
    private byte[] coverPicture; // boritókép, listázáshoz, meg az első megjelenitendő.
    private List<byte[]> pictures; // többi kép.
    private BigDecimal netPrice; // net
    private double vatRate; // e.g. 0.27
    private String height;
    private String widht;
    private String depth;
    private String netWeight;
    private String grossWeight;
    private List<String> otherProperties; // e.g. 25mAh
    private List<String> categories; // termék kategóriák
    private List<String> labels; // cimkék
    private List<String> promotionLabels; // pl Új termék, népszerű, stb..
    private List<String> compatibility; // e.g. ps4, xbox360
    private String description; // leirás, hosszú string
    private String descriptionTitle; // leirás cime
    private List<String> content; // pl mi van a termékben, 1 cd meg 1 konzol vagy akármi..
    private int stock; // termék készlet, mennyi van belőle
    private int status; // státuszkód, mittomén készleten, erndelhető, kifutott stb..
    private int storeStock; //ha lesz bolt mennyi van a bolt termékében
    private List<byte[]> descriptionFile; // letölthető file-ok, vagy akár letölthető zip a driverrel
    private int recommendedAge; // ajánlott életkor
    private boolean preOrder; // előrenelés e vagy nem
    private LocalDate relaseDate; // megjelenés dátuma
    private String videoLink; // pl youtube link
    private byte[] video; // ha van sajátkészitésű videó file hozzá
    private int warranity; // hágy hónap a gari
    private int deliveryTime; // szállitási idő alap, ehhez kell hozzáadni a from/to
    private int deliveryTimeFrom; // szállitási idő tól
    private int deliveryTimeTo; // szállitási idő ig pl: szállitási idő 2-6 nap
    private List<AppCupon> cupons; // majd egy kupon osztály, benne lesz egy ár, meg egyéb dolgok hogy mennyi
                                                                    // kedvezmény van milyen kuponnal a termékre
    private int discount; // fix áras kedvezmény
    private int discountPercent; // %os kedvezmény, vagy vagy is lehet    

}
