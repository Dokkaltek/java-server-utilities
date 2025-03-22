package com.github.dokkaltek.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Http header media type constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderMediaTypes {
    // Application types
    public static final String ABIWORD = "application/x-abiword";
    public static final String ARC = "application/x-freearc";
    public static final String AZW = "application/vnd.amazon.ebook";
    public static final String BINARY = "application/octet-stream";
    public static final String BZ = "application/x-bzip";
    public static final String BZ2 = "application/x-bzip2";
    public static final String CDA = "application/x-cdf";
    public static final String CSH = "application/x-csh";
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String EOT = "application/vnd.ms-fontobject";
    public static final String EPUB = "application/epub+zip";
    public static final String GZIP = "application/gzip";
    public static final String GZIP_X = "application/x-gzip";
    public static final String JAR = "application/java-archive";
    public static final String JSON = "application/json";
    public static final String JSON_LD = "application/ld+json";
    public static final String MPKG = "application/vnd.apple.installer+xml";
    public static final String ODP = "application/vnd.oasis.opendocument.presentation";
    public static final String ODS = "application/vnd.oasis.opendocument.spreadsheet";
    public static final String ODT = "application/vnd.oasis.opendocument.text";
    public static final String OGX = "application/ogg";
    public static final String PDF = "application/pdf";
    public static final String PHP = "application/x-httpd-php";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String RAR = "application/vnd.rar";
    public static final String RTF = "application/rtf";
    public static final String SH = "application/x-sh";
    public static final String TAR = "application/x-tar";
    public static final String VSD = "application/vnd.visio";
    public static final String XHTML = "application/xhtml+xml";
    public static final String XLS = "application/vnd.ms-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String XML = "application/xml";
    public static final String XUL = "application/vnd.mozilla.xul+xml";
    public static final String ZIP = "application/zip";
    public static final String X_ZIP_COMPRESSED = "application/x-zip-compressed";
    public static final String ZIP_7Z = "application/x-7z-compressed";

    // Text types
    public static final String CSV = "text/csv";
    public static final String CSS = "text/css";
    public static final String ICS = "text/calendar";
    public static final String JS = "text/javascript";
    public static final String HTML = "text/html";
    public static final String TEXT = "text/plain";

    // Image types
    public static final String AVIF = "image/avif";
    public static final String APNG = "image/apng";
    public static final String BMP = "image/bmp";
    public static final String GIF = "image/gif";
    public static final String ICO = "image/vnd.microsoft.icon";
    public static final String JPG = "image/jpeg";
    public static final String PNG = "image/png";
    public static final String WEBP = "image/webp";
    public static final String TIFF = "image/tiff";
    public static final String SVG = "image/svg+xml";

    // Video types
    public static final String AVI = "video/x-msvideo";
    public static final String MP4 = "video/mp4";
    public static final String MPEG = "video/mpeg";
    public static final String WEBM = "video/webm";
    public static final String TS = "video/mp2t";
    public static final String VIDEO_3GP = "video/3gpp";
    public static final String VIDEO_3G2 = "video/3gpp2";

    // Audio types
    public static final String AAC = "audio/aac";
    public static final String MID = "audio/midi";
    public static final String MIDI = "audio/x-midi";
    public static final String MP3 = "audio/mpeg";
    public static final String OGG = "audio/ogg";
    public static final String WAV = "audio/wav";
    public static final String AUDIO_3GP = "audio/3gpp";

    // Font types
    public static final String OTF = "font/otf";
    public static final String TTF = "font/ttf";
    public static final String WOFF = "font/woff";
    public static final String WOFF2 = "font/woff2";
}
