package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.model.MainCategory;
import de.kaysubs.tracker.nyaasi.model.SubCategory;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import de.kaysubs.tracker.nyaasi.model.DataSize;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {

    private final static Pattern CATEGORY_URL_PATTERN = Pattern.compile("/\\?c=([0-9])_([0-9])");
    private final static Pattern CATEGORY_ID_PATTERN = Pattern.compile("([0-9])_([0-9])");

    public static DataSize parseDataSize(String string) {
        String[] split = string.split(" ");
        if(split.length != 2)
            throw new WebScrapeException("Cannot parse data size");

        DataSize.DataUnit unit = parseDataUnit(split[1]);

        BigDecimal size = new BigDecimal(split[0]);

        while(size.stripTrailingZeros().scale() > 0) {
            if(unit == DataSize.DataUnit.BYTE)
                throw new WebScrapeException("Cannot parse fractional byte size " + string);

            unit = DataSize.DataUnit.values()[unit.ordinal() - 1];
            size = size.movePointRight(3);
        }

        return new DataSize(size.intValueExact(), unit);
    }

    private static DataSize.DataUnit parseDataUnit(String unitName) {
        for(DataSize.DataUnit unit : DataSize.DataUnit.values())
            if(unit.getUnitName().equalsIgnoreCase(unitName))
                return unit;

        throw new WebScrapeException("Unknown unit size \"" + unitName + "\"");
    }

    public static Date parseTimeStamp(String timestamp) {
        return new Date(Long.parseLong(timestamp) * 1000L);
    }

    public static SubCategory parseSubCategory(String url, boolean isUrl, boolean isSukebei) {
        Pattern pattern = isUrl ? CATEGORY_URL_PATTERN : CATEGORY_ID_PATTERN;
        Matcher matcher = pattern.matcher(url);

        if(matcher.matches()) {
            int categoryId = Integer.parseInt(matcher.group(1));
            int subcategoryId = Integer.parseInt(matcher.group(2));

            MainCategory mainCategory = isSukebei ?
                    MainCategory.Sukebei.fromId(categoryId) :
                    MainCategory.Nyaa.fromId(categoryId);

            return mainCategory.getSubcategoryFromId(subcategoryId);
        } else {
            throw new WebScrapeException("Cannot parse category url");
        }
    }

    public static String getFormValue(List<Connection.KeyVal> vals, String key) {
        return vals.stream()
                .filter(val -> val.key().equals(key)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expected form value for key \"" + key + "\""))
                .value();
    }

    public static boolean contains(List<Connection.KeyVal> vals, String key, String value) {
        return vals.stream().anyMatch(val ->
                val.key().equals(key) &&
                val.value().equals(value));
    }

    public static String getCsrfToken(Element e) {
        Elements csrfToken = e
                .select("form[method=POST]")
                .select("input#csrf_token");

        if(csrfToken.hasAttr("value")) {
            return csrfToken.attr("value");
        } else {
            throw new WebScrapeException("Cannot extract csrf token");
        }
    }

}
