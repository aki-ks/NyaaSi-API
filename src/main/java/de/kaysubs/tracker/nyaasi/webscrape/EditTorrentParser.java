package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.model.EditTorrentRequest;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.util.List;

public class EditTorrentParser implements Parser<EditTorrentRequest> {
    @Override
    public EditTorrentRequest parsePage(Document page, boolean isSukebei) {
        List<Connection.KeyVal> form = ((FormElement)page.selectFirst("form[method=POST][enctype=multipart/form-data]")).formData();

        return new EditTorrentRequest(
                ParseUtils.getFormValue(form, "display_name"),
                ParseUtils.parseSubCategory(ParseUtils.getFormValue(form, "category"), false, isSukebei),
                ParseUtils.getFormValue(form, "information"),
                ParseUtils.getFormValue(form, "description"),
                ParseUtils.contains(form, "is_anonymous", "y"),
                ParseUtils.contains(form, "is_hidden", "y"),
                ParseUtils.contains(form, "is_remake", "y"),
                ParseUtils.contains(form, "is_complete", "y")
        );
    }

}
