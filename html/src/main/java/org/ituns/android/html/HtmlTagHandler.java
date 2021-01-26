package org.ituns.android.html;

import android.text.Editable;
import android.text.Html;

import org.ituns.android.html.tags.FontTag;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.util.concurrent.atomic.AtomicInteger;

class HtmlTagHandler implements Html.TagHandler, ContentHandler {
    private static final String HTML = "html";
    private static final String FONT = "font";

    private FontTag fontTag;
    private AtomicInteger counter;
    private Editable originalEditable;
    private XMLReader originalXmlReader;
    private ContentHandler originalContentHandler;

    public HtmlTagHandler() {
        fontTag = new FontTag();
        counter = new AtomicInteger(0);
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(opening) {
            if(tag.equalsIgnoreCase(HTML)) {
                startHandleHtmlTag(tag, output, xmlReader);
            }
        } else {
            if(tag.equalsIgnoreCase(HTML)) {
                endHandleHtmlTag(tag, output, xmlReader);
            }
        }
    }

    private void startHandleHtmlTag(String tag, Editable output, XMLReader xmlReader) {
        if(counter.getAndIncrement() == 0 && xmlReader != null) {
            originalEditable = output;
            originalXmlReader = xmlReader;
            originalContentHandler = xmlReader.getContentHandler();
            xmlReader.setContentHandler(this);
        }
    }

    private void endHandleHtmlTag(String tag, Editable output, XMLReader xmlReader) {
        if(counter.decrementAndGet() == 0 && xmlReader != null) {
            xmlReader.setContentHandler(originalContentHandler);
            originalContentHandler = null;
            originalXmlReader = null;
            originalEditable = null;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(localName.equalsIgnoreCase(HTML)) {
            startHandleHtmlTag(localName, originalEditable, originalXmlReader);
        } else if(localName.equalsIgnoreCase(FONT)) {
            if(!fontTag.startHandleTag(originalEditable, atts)) {
                originalStartElement(uri, localName, qName, atts);
            }
        } else {
            originalStartElement(uri, localName, qName, atts);
        }
    }

    public void originalStartElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.startElement(uri, localName,  qName, atts);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase(HTML)) {
            endHandleHtmlTag(localName, originalEditable, originalXmlReader);
        } else if(localName.equalsIgnoreCase(FONT)) {
            if(!fontTag.endHandleTag(originalEditable)) {
                originalEndElement(uri, localName, qName);
            }
        } else {
            originalEndElement(uri, localName, qName);
        }
    }

    public void originalEndElement(String uri, String localName, String qName) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.endElement(uri, localName,  qName);
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.startDocument();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.endDocument();
        }
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.startPrefixMapping(prefix, uri);
        }
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.endPrefixMapping(prefix);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.characters(ch, start, length);
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.ignorableWhitespace(ch, start, length);
        }
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.processingInstruction(target, data);
        }
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        ContentHandler contentHandler = originalContentHandler;
        if(contentHandler != null) {
            contentHandler.skippedEntity(name);
        }
    }
}
