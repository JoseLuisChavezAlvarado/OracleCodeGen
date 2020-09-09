package code_generator.documents;

import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

/**
 *
 * @author Jose Luis Ch.
 */
public class DocumentGeneratorTools {

    protected static void createTittle(XWPFDocument document, String text, boolean pageBreak) {
        XWPFParagraph fParagraph = document.createParagraph();
        XWPFRun fRun = fParagraph.createRun();
        fParagraph.setPageBreak(pageBreak);
        fRun.setColor("470a68");
        fRun.setFontSize(18);
        fRun.setText(text);
        setSingleLineSpacing(fParagraph);
    }

    protected static void createSubTittle(XWPFDocument document, String text, boolean pageBreak) {
        XWPFParagraph fParagraph = document.createParagraph();
        XWPFRun fRun = fParagraph.createRun();
        fParagraph.setPageBreak(pageBreak);
        fRun.setColor("470a68");
        fRun.setFontSize(14);
        fRun.setText(text);
        setSingleLineSpacing(fParagraph);
    }

    protected static void createParagraph(XWPFDocument document, String text) {
        XWPFParagraph fParagraph = document.createParagraph();
        XWPFRun fRun = fParagraph.createRun();
        setSingleLineSpacing(fParagraph);
        fRun.setColor("2d2d2d");
        fRun.setFontSize(11);
        fRun.setText(text);
    }

    protected static void setSingleLineSpacing(XWPFParagraph para) {
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) {
            ppr = para.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }

}
