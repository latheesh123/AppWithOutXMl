package latheesh.com.appwithoutxml.data;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import latheesh.com.appwithoutxml.model.DataFeed;

public class RssFeedParser {

    private static final int TAG_ITEM_TITLE=1;
    private static final int TAG_IMAGE_MEDIA_CONTENT=2;
    private static final int TAG_ITEM_DESCRIPTION=3;
    private static final int TAG_ITEM_PUB_DATE=4;
    private static final int TAG_ITEM_LINK=5;

    private static final String ns = null;


    private String feedTitle;


    public synchronized List<DataFeed> parse(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }


    private List<DataFeed> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        List<DataFeed> items = new ArrayList<>();

        // Search for <rss> tags. These wrap the beginning/end of an Atom document.
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals("channel")) {
                items = readChannel(parser);
            } else {
                skip(parser);
            }
        }

        return items;
    }


    private List<DataFeed> readChannel(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        List<DataFeed> items = new ArrayList<>();

        // Search for <channel> tags. These wrap the beginning/end of an Atom document.
        parser.require(XmlPullParser.START_TAG, ns, "channel");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch (name) {
                case "title":

                    String title = readTitle(parser);
                    if (title != null) {
                        setFeedTitle(title);
                    } else {
                        setFeedTitle("Research & Insights");
                    }
                    break;
                case "item":
                    items.add(readItem(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        return items;
    }


    private DataFeed readItem(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        String title = null;
        String media_content = null;
        String description = null;
        String pubDate = null;
        String link = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "title":
                    title = readTag(parser, TAG_ITEM_TITLE);
                    break;
                case "media:content":
                    String url = readTag(parser, TAG_IMAGE_MEDIA_CONTENT);
                    if (url != null) {
                        media_content = url;
                    }
                    break;
                case "description":
                    description = readTag(parser, TAG_ITEM_DESCRIPTION);
                    break;
                case "pubDate":
                    pubDate = readTag(parser, TAG_ITEM_PUB_DATE);
                    break;
                case "link":
                    link = readTag(parser, TAG_ITEM_LINK);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        return new DataFeed(title, media_content, description, pubDate, link);
    }


    private String readTag(XmlPullParser parser, int tagType) throws IOException, XmlPullParserException {
        switch (tagType) {
            case TAG_ITEM_TITLE:
                return readBasicTag(parser, "title");
            case TAG_IMAGE_MEDIA_CONTENT:
                return readUrlFromMediaContent(parser);
            case TAG_ITEM_DESCRIPTION:
                return readBasicTag(parser, "description");
            case TAG_ITEM_PUB_DATE:
                return readBasicTag(parser, "pubDate");
            case TAG_ITEM_LINK:
                return readBasicTag(parser, "link");
            default:
                throw new IllegalArgumentException("Unknown tag type: " + tagType);
        }
    }


    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "title");

        return readTag(parser, TAG_ITEM_TITLE);
    }


    private String readBasicTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private String readUrlFromMediaContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:content");

        String url = parser.getAttributeValue(null, "url");

        while (true) {
            if (parser.nextTag() == XmlPullParser.END_TAG) {
                // Intentionally break; consumes any remaining sub-tags.
                break;
            }
        }

        return url;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

}
