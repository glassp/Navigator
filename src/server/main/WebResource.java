package server.main;

/**
 * Diese Klasse beinhaltet Ressourcen, die bei der Dateiauflistung und
 * bei Fehlerseien angezeigt werden:
 * CSS, Header, Footer
 * <p>
 */
class WebResource {

    private static final String STYLE =
            "html * { font-family: sans-serif;!important; }" +
                    "table { border-collapse: collapse; margin-bottom: 1em; }" +
                    "th { background: #70a0b2; color: #fff; }" +
                    "td, tbody th { border: 1px solid #e1e1e1; font-size: 15px; padding: .5em .3em; }" +
                    "tr:hover td { background: #e9edf1 }" +
                    "td.center { text-align: center; }";

    /**
     * html code for directory output
     *
     * @param title   title of the page
     * @param content the content
     * @return html code
     */
    static String getDirectoryTemplate(String title, String content) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<style>" + STYLE + "</style>" +
                "<title>" + title + "</title>" +
                "</head>" +
                "<body>" +
                "<h1>" + title + "</h1>" +
                content +
                "</body>" +
                "</html>";
    }

    /**
     * html code for error landing page
     *
     * @param error the error
     * @return html code
     */
    static String getErrorTemplate(String error) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>" + error + "</title>" +
                "</head>" +
                "<body>" +
                "<h1>" + error + "</h1>" +
                "</body>" +
                "</html>";
    }
}
