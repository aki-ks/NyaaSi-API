package de.kaysubs.tracker.nyaasi.webscrape;

import org.jsoup.nodes.Document;

public interface Parser<T> {

    T parsePage(Document page, boolean isSukebei);

}
